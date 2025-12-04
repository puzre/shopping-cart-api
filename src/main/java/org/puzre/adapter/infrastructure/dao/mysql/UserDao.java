package org.puzre.adapter.infrastructure.dao.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.puzre.adapter.infrastructure.dao.entity.UserEntity;
import org.puzre.adapter.infrastructure.dao.exception.DaoException;
import org.puzre.adapter.infrastructure.dao.util.DaoUtils;
import org.puzre.application.port.dao.IUserDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class UserDao implements IUserDao {

    private final DaoUtils daoUtils;

    @Override
    public void insert(UserEntity userEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String INSERT = "INSERT INTO users(id, name, phone) VALUES (?, ?, ?)";
            preparedStatement = daoUtils.getConnection().prepareStatement(INSERT);

            preparedStatement.setLong(1, userEntity.getId());
            preparedStatement.setString(2, userEntity.getName());
            preparedStatement.setString(3, userEntity.getPhone());

            if (preparedStatement.executeUpdate() == 0)
                throw new DaoException("Exception in insert");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public void update(UserEntity userEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String UPDATE = "UPDATE users SET name = ?, phone = ? WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(UPDATE);

            preparedStatement.setString(1, userEntity.getName());
            preparedStatement.setString(2, userEntity.getPhone());

            if (preparedStatement.executeUpdate() == 0)
                throw new DaoException("Exception in update");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }

    }

    @Override
    public void delete(UserEntity userEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String DELETE = "DELETE FROM users WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(DELETE);

            preparedStatement.setLong(1, userEntity.getId());

            if (preparedStatement.executeUpdate() == 0)
                throw new DaoException("Exception in delete");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao Exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }

    }

    @Override
    public List<UserEntity> list() throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<UserEntity> userEntityList = new ArrayList<>();

        try {

            String GET_ALL = "SELECT id, name, phone FROM users";
            preparedStatement = daoUtils.getConnection().prepareStatement(GET_ALL);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userEntityList.add(
                        UserEntity.builder()
                                .id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .phone(resultSet.getString("phone"))
                                .build()
                );
            }

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
            daoUtils.closeResultSet(resultSet);
        }

        return userEntityList;

    }

    @Override
    public UserEntity findById(Long id) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserEntity userEntity;

        try {

            String FIND_BY_ID = "SELECT id, name, phone FROM users WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(FIND_BY_ID);

            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                userEntity = UserEntity.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .phone(resultSet.getString("phone"))
                        .build();
            else
                throw new DaoException("User not found");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
            daoUtils.closeResultSet(resultSet);
        }

        return userEntity;
    }

}
