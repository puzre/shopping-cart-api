package org.puzre.dao.mysql;

import org.puzre.dao.IProductDao;
import org.puzre.dao.exception.DaoException;
import org.puzre.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IProductDao {

    final String INSERT = "INSERT INTO products(id, name, price) VALUES (?, ?, ?)";
    final String UPDATE = "UPDATE products SET name = ?, price = ? WHERE id = ?";
    final String DELETE = "DELETE FROM products WHERE id = ?";
    final String GET_ALL = "SELECT id, name, price FROM products";
    final String FIND_BY_ID = "SELECT id, name, price FROM products WHERE id = ?";

    private final Connection connection;

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product product) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in insert");


        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception preparedStatement close", e);
                }
            }
        }

    }

    @Override
    public void update(Product product) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(UPDATE);

            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in update");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException("Exception preparedStatement close");
            }
        }

    }

    @Override
    public void delete(Product product) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, product.getId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception in delete");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException("Exception preparedStatement close");
            }
        }

    }

    @Override
    public List<Product> getAll() throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Product> productList = new ArrayList<>();

        try {

            preparedStatement = connection.prepareStatement(GET_ALL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productList.add(Product.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getBigDecimal("price"))
                        .build());
            }

        } catch (SQLException e) {
            throw new DaoException("SQL Dao Exception", e);
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException("Exception closing preparedStatement", e);
            }
            if (resultSet != null) try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DaoException("Exception closing resultSet", e);
            }
        }

        return productList;
    }

    @Override
    public Product findById(Long id) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product;

        try {

            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                product = Product.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getBigDecimal("price"))
                        .build();
            else throw new DaoException("Product not found");

        } catch (SQLException e) {
            throw new DaoException("SQL Dao exception", e);
        } finally {
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException("Exception closing preparedStatement", e);
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    throw new DaoException("Exception closing resulSet", e);
                }
            }
        }

        return product;
    }

}
