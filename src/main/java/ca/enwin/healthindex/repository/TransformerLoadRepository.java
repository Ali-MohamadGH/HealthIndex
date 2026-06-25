package ca.enwin.healthindex.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ca.enwin.healthindex.database.DatabaseManager;
import ca.enwin.healthindex.model.TransformerLoad;

public class TransformerLoadRepository {

    public void save(
            TransformerLoad load)
            throws SQLException {

        String sql = """
            INSERT INTO zhyd_xfrmr_load(
                equipment_id,
                load_date,
                loading_score
            )
            VALUES(?,?,?)
            """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    load.equipmentId());

            statement.setString(
                    2,
                    load.loadDate().toString());

            statement.setDouble(
                    3,
                    load.loadingScore());

            statement.executeUpdate();
        }
    }

    public List<TransformerLoad> findAll()
            throws SQLException {

        List<TransformerLoad> results =
                new ArrayList<>();

        String sql =
                "SELECT * FROM zhyd_xfrmr_load";

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
                        new TransformerLoad(

                                rs.getString(
                                        "equipment_id"),

                                LocalDate.parse(
                                        rs.getString(
                                                "load_date")),

                                rs.getDouble(
                                        "loading_score")
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
                    "DELETE FROM zhyd_xfrmr_load");
        }
    }
}