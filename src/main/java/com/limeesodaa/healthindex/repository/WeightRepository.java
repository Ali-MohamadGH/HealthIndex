package com.limeesodaa.healthindex.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.limeesodaa.healthindex.database.DatabaseManager;
import com.limeesodaa.healthindex.model.WeightRule;

public class WeightRepository {

    public void save(
            WeightRule rule)
            throws SQLException {

        String sql = """
            INSERT INTO zhyd_hlth_weight(
                category,
                category_set,
                category_weight,
                set_weight
            )
            VALUES(?,?,?,?)
            """;

        try (
                Connection connection
                = DatabaseManager.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    rule.category());

            statement.setString(
                    2,
                    rule.categorySet());

            statement.setDouble(
                    3,
                    rule.categoryWeight());

            statement.setDouble(
                    4,
                    rule.setWeight());

            statement.executeUpdate();
        }
    }

    public List<WeightRule> findAll()
            throws SQLException {

        List<WeightRule> results
                = new ArrayList<>();

        String sql
                = "SELECT * FROM zhyd_hlth_weight";

        try (
                Connection connection
                = DatabaseManager.getConnection(); Statement statement
                = connection.createStatement(); ResultSet rs
                = statement.executeQuery(sql)) {

            while (rs.next()) {

                results.add(
                        new WeightRule(
                                rs.getString(
                                        "category"),
                                rs.getString(
                                        "category_set"),
                                rs.getDouble(
                                        "category_weight"),
                                rs.getDouble(
                                        "set_weight")
                        ));
            }
        }

        return results;
    }

    public void deleteAll()
            throws SQLException {

        try (
                Connection connection
                = DatabaseManager.getConnection(); Statement statement
                = connection.createStatement()) {

            statement.executeUpdate(
                    "DELETE FROM zhyd_hlth_weight");
        }
    }
}
