package org.puzre.dao.mysql;

import org.puzre.dao.IOrderDao;
import org.puzre.dao.exception.DaoException;
import org.puzre.model.Order;
import org.puzre.model.Product;
import org.puzre.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements IOrderDao {

    private final String INSERT = "INSERT INTO orders(status, total, user_id) VALUES (?, ?, ?)";
    private final String UPDATE = "UPDATE orders SET status = ?, total = ?, user_id = ?, WHERE id = ?";
    private final String DELETE = "DELETE FROM orders WHERE id = ?";
    private final String GET_ALL = "SELECT id, status, total, user_id FROM orders";
    private final String FIND_BY_ID = "SELECT id, status, total, user_id FROM orders WHERE id = ?";
    private final String GET_RELATION_BY_ID = "SELECT p.id, p.name, p.price FROM orders_products op INNER JOIN products p WHERE op.order_id = ?";

    private final Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Order order) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(INSERT);

            preparedStatement.setString(1, order.getStatus().toString());
            preparedStatement.setBigDecimal(2, order.getTotal());
            preparedStatement.setLong(3, order.getUserId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in insert");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            UtilsDao.closePreparedStatement(preparedStatement);
        }

    }

    @Override
    public void update(Order order) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(UPDATE);

            preparedStatement.setString(1, order.getStatus().toString());
            preparedStatement.setBigDecimal(2, order.getTotal());
            preparedStatement.setLong(3, order.getUserId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in update");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao Exception", e);
        } finally {
            UtilsDao.closePreparedStatement(preparedStatement);
        }

    }

    @Override
    public void delete(Order order) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(DELETE);

            preparedStatement.setLong(1, order.getId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in delete");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            UtilsDao.closePreparedStatement(preparedStatement);
        }

    }

    @Override
    public List<Order> getAll() throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Order> orderList = new ArrayList<>();

        try {

            preparedStatement = connection.prepareStatement(GET_ALL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orderList.add(Order.builder()
                        .id(resultSet.getLong("id"))
                        .status(Status.valueOf(resultSet.getString("status")))
                        .total(resultSet.getBigDecimal("total"))
                        .userId(resultSet.getLong("user_id")).build());
            }

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            UtilsDao.closePreparedStatement(preparedStatement);
            UtilsDao.closeResultSet(resultSet);
        }

        return orderList;
    }

    @Override
    public Order findById(Long id) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Order order;

        try {

            preparedStatement = connection.prepareStatement(FIND_BY_ID);

            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                order = Order.builder()
                        .id(resultSet.getLong("id"))
                        .status(Status.valueOf(resultSet.getString("status")))
                        .total(resultSet.getBigDecimal("total"))
                        .userId(resultSet.getLong("user_id")).build();

            else throw new DaoException("Order not found");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            UtilsDao.closePreparedStatement(preparedStatement);
            UtilsDao.closeResultSet(resultSet);
        }

        return order;
    }

    @Override
    public List<Product> getAllRelationById(Long id) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();

        try {

            preparedStatement = connection.prepareStatement(GET_RELATION_BY_ID);

            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productList.add(
                        Product.builder()
                                .id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .price(resultSet.getBigDecimal("price"))
                                .build()
                );
            }

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            UtilsDao.closePreparedStatement(preparedStatement);
            UtilsDao.closeResultSet(resultSet);
        }

        return productList;
    }
}
