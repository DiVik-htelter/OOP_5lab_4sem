package com.example.demo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class lab5_mysql {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3307/laba5_OOP";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public List<Rep> getAll() throws SQLException {
        List<Rep> reps = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM rep";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Rep rep = new Rep(
                        rs.getString("subject"),
                        rs.getString("name"),
                        rs.getString("second_name"),
                        rs.getInt("exp"),
                        rs.getInt("inn"),
                        rs.getInt("id")
                );
                reps.add(rep);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return reps;
    }

    public void add(Rep rep) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "INSERT INTO rep (subject, name, second_name, exp, inn) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, rep.getSubject());
            preparedStatement.setString(2, rep.getName());
            preparedStatement.setString(3, rep.getSecond_name());
            preparedStatement.setInt(4, rep.getExp());
            preparedStatement.setInt(5, rep.getInn());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (conn != null) conn.close();
        }
    }

    public void delete(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "DELETE FROM rep WHERE id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (conn != null) conn.close();
        }
    }

    public void update(Rep rep) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            String sql = "UPDATE rep SET subject = ?, name = ?, second_name = ?, exp = ?, inn = ? WHERE id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, rep.getSubject());
            preparedStatement.setString(2, rep.getName());
            preparedStatement.setString(3, rep.getSecond_name());
            preparedStatement.setInt(4, rep.getExp());
            preparedStatement.setInt(5, rep.getInn());
            preparedStatement.setInt(6, rep.getId());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (conn != null) conn.close();
        }
    }
}