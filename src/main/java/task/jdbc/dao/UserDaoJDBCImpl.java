package task.jdbc.dao;

import task.jdbc.model.User;
import task.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private int countId = 1;
    List<User> users = new ArrayList<>();
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        final String CREATE = "CREATE TABLE IF NOT EXISTS users (Id INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(20), LASTNAME VARCHAR(20), AGE INT)";
        try {
            connection.createStatement().executeUpdate(CREATE);
            System.out.println("Database has been created!");
        } catch (SQLException m) {
            System.out.println("При создании таблицы произошла ошибка");
        }
    }
    public void dropUsersTable() {
        try {
            connection.createStatement().executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("Table was dropped");
        } catch (SQLException c) {
            System.out.println("При удалении таблицы произошла ошибка");
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        final String INSERT_NEW = "INSERT INTO users VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_NEW);
            preparedStatement.setInt(1, countId++);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4, age);
            preparedStatement.execute();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("    При добавлении Users произошла ошибка.\n1)Возможно user с таким id уже сущетсвует. 2)Возможно нет БД");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("При закрытии соединения произошла ошибка");
                }
            }
        }
    }
    public void removeUserById(long id) {
        final String DELETE = "DELETE FROM users WHERE id = ";
        try {
            connection.createStatement().executeUpdate(DELETE + id);
            System.out.println("User was deleted");
        } catch (SQLException b) {
            System.out.println("При удалении пользователя из таблицы произошла ошибка");
        }
    }
    public List<User> getAllUsers() {
        final String GET_ALL = "SELECT * FROM users";
        ResultSet resultSet = null;
        try {
            resultSet = connection.createStatement().executeQuery(GET_ALL);
            while (resultSet.next()) {    // пока есть строка, состоящая из (id, name, lastName, age)
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                users.add(user);
            }
        } catch (SQLException s) {
            System.out.println("При получении пользователей из БД произошла ошибка.\nВозможно нет БД");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.out.println("При закрытии соединения произошла ошибка");
                }
            }
        }
        return users;
    }
    public void cleanUsersTable() {
        final String DELETE = "DELETE FROM users";
        try {
            connection.createStatement().executeUpdate(DELETE);
            System.out.println("Table was cleaned");
        } catch (SQLException b) {
            System.out.println("При удалении пользователей произошла ошибка");
        }
    }
}
