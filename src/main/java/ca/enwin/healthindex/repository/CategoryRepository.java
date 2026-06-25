package ca.enwin.healthindex.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ca.enwin.healthindex.database.DatabaseManager;
import ca.enwin.healthindex.model.CategoryRule;

public class CategoryRepository {

    public void save(CategoryRule rule)
            throws SQLException {

        String sql = """
            INSERT INTO zhyd_hlth_categ (
                measurement_name,
                category,
                category_set,
                maximum_value,
                gateway_test_value,
                gateway_fail
                
            )
            VALUES (?,?,?,?,?,?)
            """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    rule.measurementName());

            statement.setString(
                    2,
                    rule.category());

            statement.setString(
                    3,
                    rule.categorySet());

            statement.setDouble(
                    4,
                    rule.maximumValue());

            statement.setDouble(
                    5,
                    rule.gatewayTestValue());
                statement.setBoolean(6, rule.gatewayFail());
            statement.executeUpdate();
        }
    }

    public List<CategoryRule> findAll()
            throws SQLException {

        List<CategoryRule> results =
                new ArrayList<>();

        String sql =
                "SELECT * FROM zhyd_hlth_categ";

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
                        new CategoryRule(

                                rs.getString(
                                        "measurement_name"),

                                rs.getString(
                                        "category"),

                                rs.getString(
                                        "category_set"),

                                rs.getDouble(
                                        "maximum_value"),
                                
                                rs.getDouble("gateway_test_value"),
                                rs.getBoolean(
                                        "gateway_fail")
                                        
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
                    "DELETE FROM zhyd_hlth_categ");
        }
    }
}