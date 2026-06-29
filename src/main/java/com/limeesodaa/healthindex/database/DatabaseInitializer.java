package com.limeesodaa.healthindex.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public void initialize()
            throws SQLException {

        try (Connection connection =
                     DatabaseManager.getConnection();

             Statement statement =
                     connection.createStatement()) {

            createCategoryTable(statement);

            createWeightTable(statement);

            createConversionTable(statement);

            createTransformerLoadTable(statement);

            createInspectionMeasurementTable(statement);

            createHealthIndexResultTable(statement);
        }
    }

    private void createCategoryTable(
            Statement statement)
            throws SQLException {

        statement.execute("""
                
                CREATE TABLE IF NOT EXISTS
                zhyd_hlth_categ (

                    id INTEGER
                        PRIMARY KEY AUTOINCREMENT,

                    measurement_name TEXT
                        NOT NULL UNIQUE,

                    category TEXT
                        NOT NULL,

                    category_set TEXT
                        NOT NULL,

                    maximum_value REAL
                        NOT NULL,
                    gateway_test_value REAL
                        NOT NULL DEFAULT 0,

                    gateway_fail INTEGER
                        NOT NULL DEFAULT 0

                        
                    

                )
                
                """);
    }

    private void createWeightTable(
            Statement statement)
            throws SQLException {

        statement.execute("""

                CREATE TABLE IF NOT EXISTS
                zhyd_hlth_weight (

                    id INTEGER
                        PRIMARY KEY AUTOINCREMENT,

                    category TEXT
                        NOT NULL,

                    category_set TEXT,

                    category_weight REAL,

                    set_weight REAL
                )

                """);
    }

    private void createTransformerLoadTable(
            Statement statement)
            throws SQLException {

        statement.execute("""

                CREATE TABLE IF NOT EXISTS
                zhyd_xfrmr_load (

                    equipment_id TEXT
                        NOT NULL,

                    load_date TEXT
                        NOT NULL,

                    loading_score REAL
                        NOT NULL,

                    PRIMARY KEY(
                        equipment_id,
                        load_date
                    )
                )

                """);
    }

    private void createInspectionMeasurementTable(
            Statement statement)
            throws SQLException {

        statement.execute("""

                CREATE TABLE IF NOT EXISTS
                inspection_measurement (

                    
                    equipment_id TEXT
                        NOT NULL,

                    inspection_date TEXT
                        NOT NULL,

                    measurement_name TEXT
                        NOT NULL,

                    code_group TEXT
                        NOT NULL,

                    measurement_value REAL
                        NOT NULL,

                    PRIMARY KEY (

                        equipment_id,

                        inspection_date,

                        measurement_name
                    )
                )

                """);
    }

    private void createHealthIndexResultTable(
        Statement statement)
        throws SQLException {

    statement.execute("""

            CREATE TABLE IF NOT EXISTS
            health_index_result (

                equipment_id TEXT
                    NOT NULL,

                inspection_date TEXT
                    NOT NULL,

                health_index REAL
                    NOT NULL,

                gateway_fail INTEGER
                    NOT NULL,

                PRIMARY KEY(
                    equipment_id,
                    inspection_date
                )
            )

            """);

    statement.execute("""

            CREATE TABLE IF NOT EXISTS
            health_index_category_score (

                equipment_id TEXT
                    NOT NULL,

                inspection_date TEXT
                    NOT NULL,

                category TEXT
                    NOT NULL,

                score REAL
                    NOT NULL,

                PRIMARY KEY (

                    equipment_id,

                    inspection_date,

                    category
                )
            )

            """);
    }

    private void createConversionTable(
        Statement statement)
        throws SQLException {

    statement.execute("""

            CREATE TABLE IF NOT EXISTS
            zhyd_hlth_conv (

                id INTEGER
                    PRIMARY KEY AUTOINCREMENT,

                code_group TEXT
                    NOT NULL,

                source_value REAL
                    NOT NULL,

                converted_value REAL
                    NOT NULL
            )

            """);
    }
}