package ua.maestr0.crud.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final Dotenv dotenv = Dotenv.configure().directory("config").load();
    private static final HikariDataSource HIKARI_DATA_SOURCE;

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    static {
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(URL, USER, PASSWORD)
                    .locations("classpath:db/migration")
                    .load();
            flyway.migrate();

            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(URL);
            hikariConfig.setUsername(USER);
            hikariConfig.setPassword(PASSWORD);

            HIKARI_DATA_SOURCE = new HikariDataSource(hikariConfig);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize HikariDataSource", e);
        }
    }

    private DataSource() {

    }

    public static Connection getConnection() throws SQLException {
        return HIKARI_DATA_SOURCE.getConnection();
    }
}