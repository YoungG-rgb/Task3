package task.jdbc.dao;

import task.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers() throws SQLException;

    void cleanUsersTable();
}
