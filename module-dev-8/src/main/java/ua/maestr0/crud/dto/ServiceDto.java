package ua.maestr0.crud.dto;

import ua.maestr0.crud.anotation.Column;
import ua.maestr0.crud.anotation.Id;
import ua.maestr0.crud.config.DataSource;
import ua.maestr0.crud.util.SqlGenerator;

import java.lang.reflect.Field;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static ua.maestr0.crud.util.Validator.validateEntity;

public class ServiceDto {
    private final SqlGenerator sqlGenerator = new SqlGenerator();

    private <T> T mapResultSetToObject(ResultSet resultSet, Class<T> type) {
        T emptyObject = createInstance(type);
        for (Field field : type.getDeclaredFields()) {
            setFieldValueFromResultSet(emptyObject, field, resultSet);
        }
        return emptyObject;
    }

    private <T> T createInstance(Class<T> type) {
        try {
            return type.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Не вдалось створити екземпляр класу " + type.getName(), e);
        }
    }

    private void setFieldValueFromResultSet(Object object, Field field, ResultSet resultSet) {
        field.setAccessible(true);
        try {
            Object value = getFieldValueFromResultSet(resultSet, field);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException("Не вдалось встановити значення поля " + field.getName(), e);
        } finally {
            field.setAccessible(false);
        }
    }

    private Object getFieldValueFromResultSet(ResultSet resultSet, Field field) throws SQLException {
        String columnLabel = resolveColumnLabel(field);
        Class<?> fieldType = field.getType();

        if (fieldType.equals(LocalDateTime.class)) {
            Timestamp timestamp = resultSet.getTimestamp(columnLabel);
            return timestamp != null ? timestamp.toLocalDateTime() : null;
        } else if (fieldType.equals(LocalDate.class)) {
            Date date = resultSet.getDate(columnLabel);
            return date != null ? date.toLocalDate() : null;
        } else if (fieldType.equals(LocalTime.class)) {
            Time time = resultSet.getTime(columnLabel);
            return time != null ? time.toLocalTime() : null;
        } else {
            return resultSet.getObject(columnLabel);
        }
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

    public <T> boolean create(T entity) {
        validateEntity(entity);
        String query = sqlGenerator.generateCreateQuery(entity.getClass());

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            Field[] declaredFields = entity.getClass().getDeclaredFields();

            for (int i = 1; i <= declaredFields.length; i++) {
                Field field = declaredFields[i - 1];
                field.setAccessible(true);
                if(field.isAnnotationPresent(Id.class)) {
                    field.set(entity, getNextId(entity.getClass()));
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

            for (String fieldName : fieldsToUpdate.keySet()) {
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
