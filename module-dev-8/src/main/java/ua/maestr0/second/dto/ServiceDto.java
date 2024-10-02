package ua.maestr0.second.dto;

import ua.maestr0.second.anotation.Column;
import ua.maestr0.second.anotation.Id;
import ua.maestr0.second.config.DataSource;
import ua.maestr0.second.util.SqlGenerator;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class ServiceDto {
    private SqlGenerator sqlGenerator = new SqlGenerator();

    private <T> T mapResultSetToObject(ResultSet resultSet, Class<T> type) {
        T emptyObject = null;
        try {
            emptyObject = type.getConstructor().newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(emptyObject, resultSet.getObject(resolveColumnLabel(field)));
                field.setAccessible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return emptyObject;
    }

    private String resolveColumnLabel(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .orElseGet(field::getName);
    }

    private Long getNextId(Class<?> type) {
        String query = sqlGenerator.generateId(type);

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getObject(1, Long.class);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T findById(Long id, Class<T> type) {
        String query = sqlGenerator.generateSelectByIdQuery(type);

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return mapResultSetToObject(resultSet, type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> findAll(Class<T> type) {
        String query = sqlGenerator.generateSelectAllQuery(type);
        List<T> result = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(mapResultSetToObject(resultSet, type));
            }

            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return List.of();
        }
    }

    public <T> boolean delete(Long id, Class<T> type) {
        String query = sqlGenerator.generateDeleteQuery(type);

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public <T> boolean create(T entity, Class<T> type) {
        String query = sqlGenerator.generateCreateQuery(type);

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            Field[] declaredFields = entity.getClass().getDeclaredFields();

            for (int i = 1; i <= declaredFields.length; i++) {
                Field field = declaredFields[i - 1];
                field.setAccessible(true);
                if(field.isAnnotationPresent(Id.class)) {
                    field.set(entity, getNextId(type));
                }
                Object value = field.get(entity);
                preparedStatement.setObject(i, value);
                field.setAccessible(false);
            }

            return preparedStatement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T> boolean update(Long id, Map<String, Object> fieldsToUpdate, Class<T> type) {
        String query = sqlGenerator.generateUpdateQuery(
                type,
                new ArrayList<>(fieldsToUpdate.keySet()));

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int paramIndex = 1;

            for (Iterator<String> iterator = fieldsToUpdate.keySet().iterator(); iterator.hasNext(); ) {
                String fieldName = iterator.next();
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = fieldsToUpdate.get(fieldName);
                preparedStatement.setObject(paramIndex++, value);
                field.setAccessible(false);
            }

            preparedStatement.setObject(paramIndex, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
