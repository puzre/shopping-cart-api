package org.puzre.adapter.infrastructure.dao.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.puzre.adapter.infrastructure.dao.entity.OrderEntity;
import org.puzre.adapter.infrastructure.dao.util.DaoUtils;
import org.puzre.application.port.dao.IOrderDao;
import org.puzre.adapter.infrastructure.dao.exception.DaoException;
import org.puzre.model.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderDao implements IOrderDao {

    private final String GET_RELATION_BY_ID = "SELECT p.id, p.name, p.price FROM orders_products op INNER JOIN products p WHERE op.order_id = ?";

    private final DaoUtils daoUtils;

    @Override
    public void insert(OrderEntity orderEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String INSERT = "INSERT INTO orders(status, total, user_id) VALUES (?, ?, ?)";
            preparedStatement = daoUtils.getConnection().prepareStatement(INSERT);

            preparedStatement.setString(1, orderEntity.getStatus().toString());
            preparedStatement.setBigDecimal(2, orderEntity.getTotal());
            preparedStatement.setLong(3, orderEntity.getUserId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in insert");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }

    }

    @Override
    public void update(OrderEntity orderEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String UPDATE = "UPDATE orders SET status = ?, total = ?, user_id = ?, WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(UPDATE);

            preparedStatement.setString(1, orderEntity.getStatus().toString());
            preparedStatement.setBigDecimal(2, orderEntity.getTotal());
            preparedStatement.setLong(3, orderEntity.getUserId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in update");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao Exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }

    }

    @Override
    public void delete(OrderEntity orderEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String DELETE = "DELETE FROM orders WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(DELETE);

            preparedStatement.setLong(1, orderEntity.getId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in delete");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }

    }

    @Override
    public List<OrderEntity> getAll() throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OrderEntity> orderEntityList = new ArrayList<>();

        try {

            String GET_ALL = "SELECT id, status, total, user_id FROM orders";
            preparedStatement = daoUtils.getConnection().prepareStatement(GET_ALL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orderEntityList.add(OrderEntity.builder()
                        .id(resultSet.getLong("id"))
                        .status(Status.valueOf(resultSet.getString("status")))
                        .total(resultSet.getBigDecimal("total"))
                        .userId(resultSet.getLong("user_id")).build());
            }

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
            daoUtils.closeResultSet(resultSet);
        }

        return orderEntityList;
    }

    @Override
    public OrderEntity findById(Long id) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        OrderEntity orderEntity;

        try {

            String FIND_BY_ID = "SELECT id, status, total, user_id FROM orders WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(FIND_BY_ID);

            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                orderEntity = OrderEntity.builder()
                        .id(resultSet.getLong("id"))
                        .status(Status.valueOf(resultSet.getString("status")))
                        .total(resultSet.getBigDecimal("total"))
                        .userId(resultSet.getLong("user_id")).build();

            else throw new DaoException("Order not found");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
            daoUtils.closeResultSet(resultSet);
        }

        return orderEntity;
    }
}
