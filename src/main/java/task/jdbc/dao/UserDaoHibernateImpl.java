package task.jdbc.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import task.jdbc.model.User;
import task.jdbc.util.Util;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao{
    Transaction transaction = null;
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (Id INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(20), LASTNAME VARCHAR(20), AGE INT)";
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            System.out.println("Table was created");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            System.out.println("Table was dropped");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User saved");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User userForDelete = session.get(User.class, id);
            session.delete(userForDelete);
            System.out.println("User deleted");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()){
            return session.createQuery("from User", User.class).list();
        }
    }
    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            System.out.println("Table was cleaned");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
