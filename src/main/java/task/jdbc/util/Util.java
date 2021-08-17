package task.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import task.jdbc.model.User;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/news";
    private static final String userName = "root";
    private static final String password = "1234";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("Connection is null");
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                properties.put(Environment.URL, url);
                properties.put(Environment.USER, userName);
                properties.put(Environment.PASS, password);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.HBM2DDL_AUTO, "create-drop");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                Configuration cf = new Configuration().setProperties(properties).addAnnotatedClass(User.class);
                ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(cf.getProperties()).build();
                sessionFactory = cf.buildSessionFactory(sr);
            } catch (Exception e) {
                System.out.println("При построении SessionFactory произошла ошибка");
            }
        }
        return sessionFactory;
    }
}
