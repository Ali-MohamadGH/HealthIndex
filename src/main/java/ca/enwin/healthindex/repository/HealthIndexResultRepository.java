package ca.enwin.healthindex.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.enwin.healthindex.database.DatabaseManager;
import ca.enwin.healthindex.model.HealthIndexResult;

public class HealthIndexResultRepository {

    public void save(
            HealthIndexResult result)
            throws SQLException {

        try (
                Connection connection =
                        DatabaseManager.getConnection()
        ) {

            connection.setAutoCommit(
                    false);

            try {

                saveResult(
                        connection,
                        result);

                saveCategoryScores(
                        connection,
                        result);

                connection.commit();
            }

            catch (Exception ex) {

                connection.rollback();

                throw ex;
            }
        }
    }

    private void saveResult(

            Connection connection,

            HealthIndexResult result)

            throws SQLException {

        String sql = """
            INSERT OR REPLACE INTO
            health_index_result(

                equipment_id,
                inspection_date,
                health_index,
                gateway_fail
            )
            VALUES(?,?,?,?)
            """;

        try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql)
        ) {

            statement.setString(
                    1,
                    result.equipmentId());

            statement.setString(
                    2,
                    result.inspectionDate()
                            .toString());

            statement.setDouble(
                    3,
                    result.healthIndex());

            statement.setBoolean(
                    4,
                    result.gatewayFail());

            statement.executeUpdate();
        }
    }

    private void saveCategoryScores(

            Connection connection,

            HealthIndexResult result)

            throws SQLException {

        String deleteSql = """
            DELETE FROM
            health_index_category_score

            WHERE equipment_id = ?
            AND inspection_date = ?
            """;

        try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                deleteSql)
        ) {

            statement.setString(
                    1,
                    result.equipmentId());

            statement.setString(
                    2,
                    result.inspectionDate()
                            .toString());

            statement.executeUpdate();
        }

        String insertSql = """
            INSERT INTO
            health_index_category_score(

                equipment_id,
                inspection_date,
                category,
                score
            )
            VALUES(?,?,?,?)
            """;

        try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                insertSql)
        ) {

            for (var entry :
                    result.categoryScores()
                            .entrySet()) {

                statement.setString(
                        1,
                        result.equipmentId());

                statement.setString(
                        2,
                        result.inspectionDate()
                                .toString());

                statement.setString(
                        3,
                        entry.getKey());

                statement.setDouble(
                        4,
                        entry.getValue());

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    public List<HealthIndexResult>
    findAll()
            throws SQLException {

        List<HealthIndexResult> results =
                new ArrayList<>();

        String sql =
                "SELECT * FROM health_index_result";

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                Statement statement =
                        connection.createStatement();

                ResultSet rs =
                        statement.executeQuery(
                                sql)
        ) {

            while (rs.next()) {

                String equipmentId =

                        rs.getString(
                                "equipment_id");

                LocalDate inspectionDate =

                        LocalDate.parse(

                                rs.getString(
                                        "inspection_date"));

                results.add(

                        new HealthIndexResult(

                                equipmentId,

                                inspectionDate,

                                rs.getDouble(
                                        "health_index"),

                                loadCategoryScores(

                                        connection,

                                        equipmentId,

                                        inspectionDate),

                                rs.getBoolean(
                                        "gateway_fail")));
            }
        }

        return results;
    }

    private Map<String, Double>
    loadCategoryScores(

            Connection connection,

            String equipmentId,

            LocalDate inspectionDate)

            throws SQLException {

        Map<String, Double> scores =
                new HashMap<>();

        String sql = """
            SELECT
                category,
                score
            FROM
                health_index_category_score
            WHERE
                equipment_id = ?
            AND
                inspection_date = ?
            """;

        try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql)
        ) {

            statement.setString(
                    1,
                    equipmentId);

            statement.setString(
                    2,
                    inspectionDate
                            .toString());

            try (
                    ResultSet rs =
                            statement.executeQuery()
            ) {

                while (rs.next()) {

                    scores.put(

                            rs.getString(
                                    "category"),

                            rs.getDouble(
                                    "score"));
                }
            }
        }

        return scores;
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
                    "DELETE FROM health_index_category_score");

            statement.executeUpdate(
                    "DELETE FROM health_index_result");
        }
    }
}