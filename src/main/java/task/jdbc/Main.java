package task.jdbc;

import task.jdbc.service.UserService;
import task.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Zhalaldin","Toichubaev", (byte) 20);
        userService.saveUser("Igor","Bogdan", (byte) 22);
        userService.saveUser("Anton","Pavlovich", (byte) 24);
        userService.saveUser("Elena","Sherbakova", (byte) 23);
        userService.getAllUsers().forEach(e -> System.out.println("User: id " + e.getId() + " name:" + e.getName()));
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
