package ua.maestr0.crud.util;

import ua.maestr0.crud.anotation.Column;
import ua.maestr0.crud.anotation.Id;
import ua.maestr0.crud.anotation.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SqlGenerator {
    private static final String SELECT_FROM_TABLE_BY_ID = "SELECT * FROM %s WHERE %s = ?";

    private static final String DELETE_FROM_TABLE_BY_ID = "DELETE FROM %s WHERE %s = ?";

    private static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM %s";

    private static final String INSERT_IN_TABLE = "INSERT INTO %s (%s) VALUES (%s)";

    private static final String UPDATE_IN_TABLE_BY_ID = "UPDATE %s SET %s WHERE %s = ?";

    private static final String GET_LAST_ID_FROM_TABLE = "SELECT nextval('%s_id_seq')";


    private String resolveTableName(Class<?> type) {
        return Optional.ofNullable(type.getAnnotation(Table.class))
                .map(Table::name)
                .orElseGet(() -> type.getSimpleName().toLowerCase());
    }

    private String resolveFieldNames(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .map(this::resolveColumnLabel)
                .collect(Collectors.joining(", "));
    }

    private String resolveColumnLabel(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .orElseGet(field::getName);
    }

    private String resolvePlaceholders(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .map(f -> "?")
                .collect(Collectors.joining(", "));
    }

    private String resolveIdName(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(this::resolveColumnLabel)
                .findAny()
                .orElseThrow();
    }

    public String generateSelectByIdQuery(Class<?> type) {
        String tableName = resolveTableName(type);
        String idName = resolveIdName(type);
        return SELECT_FROM_TABLE_BY_ID.formatted(tableName, idName);
    }

    public String generateCreateQuery(Class<?> type) {
        String tableName = resolveTableName(type);
        String fieldNames = resolveFieldNames(type);
        String placeholders = resolvePlaceholders(type);
        return INSERT_IN_TABLE.formatted(tableName, fieldNames, placeholders);
    }

    public String generateUpdateQuery(Class<?> type, List<String> fieldsToUpdate) {
        String tableName = resolveTableName(type);
        String idName = resolveIdName(type);
        String setClause = fieldsToUpdate.stream()
                .map(fieldName -> {
                    try {
                        Field field = type.getDeclaredField(fieldName);
                        String columnName = resolveColumnLabel(field);
                        return columnName + " = ?";
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException("Field '" + fieldName + "' not found in " + type.getName(), e);
                    }
                })
                .collect(Collectors.joining(", "));
        return UPDATE_IN_TABLE_BY_ID.formatted(tableName, setClause, idName);
    }

    public String generateDeleteQuery(Class<?> type) {
        String tableName = resolveTableName(type);
        String idName = resolveIdName(type);
        return DELETE_FROM_TABLE_BY_ID.formatted(tableName, idName);
    }

    public String generateSelectAllQuery(Class<?> type) {
        String tableName = resolveTableName(type);
        return SELECT_ALL_FROM_TABLE.formatted(tableName);
    }

    public String generateId(Class<?> type) {
        String tableName = resolveTableName(type);
        return GET_LAST_ID_FROM_TABLE.formatted(tableName);
    }
}
