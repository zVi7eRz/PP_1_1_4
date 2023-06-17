package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String sqlTable = "CREATE TABLE users (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age TINYINT, PRIMARY KEY ( id ))";
    private static final String deleteUserId = "DELETE FROM users WHERE id = ?";
    private static final String addUser = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES FROM test LIKE 'users'")) {
            if (resultSet.next()) {
                System.out.println("Table exists");
            } else {
                connection.createStatement().executeUpdate(sqlTable);
                System.out.println("Table create");
            }
        } catch (SQLException e) {
        }


    }

    public void dropUsersTable() {
        try {
            connection.createStatement().executeUpdate("DROP TABLES users");
        } catch (SQLException e) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prepared = connection.prepareStatement(addUser)) {
            prepared.setString(1, name);
            prepared.setString(2, lastName);
            prepared.setByte(3, age);
            prepared.execute();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {

        }

    }

    public void removeUserById(long id) {
        try (PreparedStatement prepared = connection.prepareStatement(deleteUserId);) {
            prepared.setLong(1, id);
            prepared.executeUpdate();
            System.out.printf("User %s deleted \n", id);
        } catch (SQLException e) {
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {

        }
        return userList;
    }

    public void cleanUsersTable() {
        try {
            connection.createStatement().executeUpdate("TRUNCATE test.users");
        } catch (SQLException e) {

        }

    }
}
