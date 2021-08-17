package task.jdbc.service;

import task.jdbc.dao.UserDao;
import task.jdbc.dao.UserDaoHibernateImpl;
import task.jdbc.dao.UserDaoJDBCImpl;
import task.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoHibernateImpl();
    public void createUsersTable() {
        userDao.createUsersTable();
    }
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
