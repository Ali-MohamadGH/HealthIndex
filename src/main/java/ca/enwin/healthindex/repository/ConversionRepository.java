package ca.enwin.healthindex.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ca.enwin.healthindex.database.DatabaseManager;
import ca.enwin.healthindex.model.ConversionRule;

public class ConversionRepository {

    public void save(
            ConversionRule rule)
            throws SQLException {

        String sql = """
                INSERT INTO zhyd_hlth_conv
                (
                    code_group,
                    source_value,
                    converted_value
                )
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    rule.codeGroup());

            statement.setDouble(
                    2,
                    rule.sourceValue());

            statement.setDouble(
                    3,
                    rule.convertedValue());

            statement.executeUpdate();
        }
    }

    public List<ConversionRule> findAll()
            throws SQLException {

        List<ConversionRule>
                rules =
                new ArrayList<>();

        String sql =
                """
                SELECT
                    code_group,
                    source_value,
                    converted_value
                FROM zhyd_hlth_conv
                ORDER BY
                    code_group,
                    source_value
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql);

                ResultSet rs =
                        statement.executeQuery()
        ) {

            while (rs.next()) {

                rules.add(
                        new ConversionRule(

                                rs.getString(
                                        "code_group"),

                                rs.getDouble(
                                        "source_value"),

                                rs.getDouble(
                                        "converted_value")
                        ));
            }
        }

        return rules;
    }

    public void deleteAll()
            throws SQLException {

        String sql =
                "DELETE FROM zhyd_hlth_conv";

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql)
        ) {

            statement.executeUpdate();
        }
    }
}