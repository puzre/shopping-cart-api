package org.puzre.dao.mysql;

import org.puzre.dao.exception.DaoException;
import org.puzre.dao.IUserDao;
import org.puzre.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {

    final String INSERT = "INSERT INTO users(id, name, phone) VALUES (?, ?, ?)";
    final String UPDATE = "UPDATE users SET name = ?, phone = ? WHERE id = ?";
    final String DELETE = "DELETE FROM users WHERE id = ?";
    final String GET_ALL = "SELECT id, name, phone FROM users";
    final String FIND_BY_ID = "SELECT id, name, phone FROM users WHERE id = ?";

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(User user) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(INSERT);

            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPhone());

            if (preparedStatement.executeUpdate() == 0)
                throw new DaoException("Exception in insert");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception closing preparedStatement");
                }
        }
    }

    @Override
    public void update(User user) throws DaoException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone());

            if (preparedStatement.executeUpdate() == 0)
                throw new DaoException("Exception in update");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        }

    }

    @Override
    public void delete(User user) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(DELETE);

            preparedStatement.setLong(1, user.getId());

            if (preparedStatement.executeUpdate() == 0)
                throw new DaoException("Exception in delete");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao Exception", e);
        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception preparedStatement close", e);
                }

        }

    }

    @Override
    public List<User> getAll() throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        try {

            preparedStatement = connection.prepareStatement(GET_ALL);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userList.add(
                        User.builder()
                                .id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .phone(resultSet.getString("phone"))
                                .build()
                );
            }

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception resultSet close", e);
                }

            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception preparedStatement close", e);
                }

        }

        return userList;

    }

    @Override
    public User findById(Long id) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;

        try {

            preparedStatement = connection.prepareStatement(FIND_BY_ID);

            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                user = User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .phone(resultSet.getString("phone"))
                        .build();
            else
                throw new DaoException("User not found");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception connection close");
                }

            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception resultSet close");
                }

        }

        return user;
    }

}
