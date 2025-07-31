package org.puzre.dao.mysql;

import org.puzre.dao.exception.DaoException;
import org.puzre.dao.IUserDao;
import org.puzre.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        PreparedStatement preparedStatement;
        try {

            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPhone());

            if (preparedStatement.executeUpdate() == 0) {
                throw new DaoException("Exception in ExecuteUpdate");
            }

        } catch (SQLException e) {
            throw new DaoException("SQL Dao Exception", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception in connection close", e);
                }
            }
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User getByIds(Long pId, Long sId) {
        return null;
    }
}
