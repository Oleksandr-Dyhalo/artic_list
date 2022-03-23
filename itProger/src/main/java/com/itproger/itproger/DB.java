package com.itproger.itproger;

import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3307";
    private final String DB_NAME = "itproger_java";
    private final String LOGIN = "root";
    private final String PASSWORD = "root";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASSWORD);
        return dbConn;
    }

    public void isConnected() throws ClassNotFoundException, SQLException {

        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean isExistsUser(String login) {
        String sql = "SELECT `id` FROM `users` WHERE `login` = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);

            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void regUser(String login, String email, String password) {

        String sql = "INSERT INTO `users` (login, email, password) VALUES(?, ?, ?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, email);
            prSt.setString(3, password);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String login, String pass) {

        String sql = "SELECT `id` FROM `users` WHERE `login` = ? AND `password` = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            prSt.setString(2, pass);

            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void resetData(String login, String email, String pass, String oldLogin) {

        String sql = "UPDATE `users` SET `login` = ?, `email` = ?, `password` = ? " +
                "WHERE `users`.`login` = ?";

        String userToChange = oldLogin;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, login);
            if (email.length() != 0) {
                prSt.setString(2, email);
            } else {
                String sql2 = "SELECT `email` FROM `users` WHERE `users`.`login` = ?";
                PreparedStatement prSt2 = getDbConnection().prepareStatement(sql2);
                prSt2.setString(1, oldLogin);
                ResultSet res = prSt2.executeQuery();
                String emailToCopy = null;
                while (res.next()) {
                    emailToCopy = res.getString("email");
                }
                String sql3 = "UPDATE `users` SET `email` = ? WHERE `users`.`login` = ?";
                PreparedStatement prSt3 = getDbConnection().prepareStatement(sql3);
                prSt3.setString(1, emailToCopy);
                prSt3.setString(2, oldLogin);
                prSt3.executeUpdate();
                prSt.setString(2, emailToCopy);
            }
            prSt.setString(3, pass);
            prSt.setString(4, userToChange);
            prSt.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getArticles() {

        String sql = "SELECT `id`, `title`, `intro`, `text` FROM `articles`";
        Statement statement;
        try {
            statement = getDbConnection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getArticleToRead(String id) {

        String sql = "SELECT `id`, `title`, `text` FROM `articles` WHERE `articles`.`id` = ?";
        PreparedStatement prSt;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, id);

            return prSt.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addArticle(String title, String intro, String full_text) {

        String sql = "INSERT INTO `articles` (title, intro, text) VALUES(?, ?, ?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, title);
            prSt.setString(2, intro);
            prSt.setString(3, full_text);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
// jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC