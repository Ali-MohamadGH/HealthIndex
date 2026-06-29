package com.limeesodaa.healthindex.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.limeesodaa.healthindex.database.DatabaseManager;
import com.limeesodaa.healthindex.model.InspectionMeasurement;

public class InspectionRepository {

    public void save(
            InspectionMeasurement measurement)
            throws SQLException {

        String sql = """
        INSERT OR REPLACE INTO
        inspection_measurement(

                equipment_id,
                inspection_date,
                measurement_name,
                code_group,
                measurement_value
        )
        VALUES(?,?,?,?,?)
        """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

                statement.setString(
                        1,
                        measurement.equipmentId());

                statement.setString(
                        2,
                        measurement.inspectionDate()
                                .toString());

                statement.setString(
                        3,
                        measurement.measurementName());

                statement.setString(
                        4,
                        measurement.codeGroup());

                statement.setDouble(
                        5,
                        measurement.rawValue());

        
            statement.executeUpdate();
        }
    }

    public List<InspectionMeasurement>
    findAll()
            throws SQLException {

        List<InspectionMeasurement> results =
                new ArrayList<>();

        String sql =
                "SELECT * FROM inspection_measurement";

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                Statement statement =
                        connection.createStatement();

                ResultSet rs =
                        statement.executeQuery(sql)
        ) {

            while (rs.next()) {

                results.add(
        new InspectionMeasurement(

                rs.getString(
                        "equipment_id"),

                LocalDate.parse(
                        rs.getString(
                                "inspection_date")),

                rs.getString(
                        "measurement_name"),

                rs.getString(
                        "code_group"),

                rs.getDouble(
                        "measurement_value")
        ));
            }
        }

        return results;
    }

    public void deleteAll()
            throws SQLException {

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                Statement statement =
                        connection.createStatement()
        ) {

            statement.executeUpdate(
                    "DELETE FROM inspection_measurement");
        }
    }
}