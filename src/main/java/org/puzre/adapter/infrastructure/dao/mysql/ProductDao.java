package org.puzre.adapter.infrastructure.dao.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.puzre.adapter.infrastructure.dao.entity.ProductEntity;
import org.puzre.adapter.infrastructure.dao.util.DaoUtils;
import org.puzre.application.port.dao.IProductDao;
import org.puzre.adapter.infrastructure.dao.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ProductDao implements IProductDao {

    private final DaoUtils daoUtils;

    @Override
    public void insert(ProductEntity productEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String INSERT = "INSERT INTO products(id, name, price) VALUES (?, ?, ?)";
            preparedStatement = daoUtils.getConnection().prepareStatement(INSERT);
            preparedStatement.setString(1, productEntity.getName());
            preparedStatement.setBigDecimal(2, productEntity.getPrice());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception trying to insert a product");


        } catch (SQLException e) {
            throw new DaoException("Exception trying to insert a product", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public void update(ProductEntity productEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String UPDATE = "UPDATE products SET name = ?, price = ? WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(UPDATE);

            preparedStatement.setString(1, productEntity.getName());
            preparedStatement.setBigDecimal(2, productEntity.getPrice());
            preparedStatement.setLong(3, productEntity.getId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception trying to update a product");

        } catch (SQLException e) {
            throw new DaoException("Exception trying to update a product", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public void delete(ProductEntity productEntity) throws DaoException {

        PreparedStatement preparedStatement = null;

        try {

            String DELETE = "DELETE FROM products WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(DELETE);
            preparedStatement.setLong(1, productEntity.getId());

            if (preparedStatement.executeUpdate() == 0) throw new DaoException("Exception trying to delete a product");

        } catch (SQLException e) {
            throw new DaoException("Exception trying to delete a product", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public List<ProductEntity> list() throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProductEntity> productEntityList = new ArrayList<>();

        try {

            String GET_ALL = "SELECT id, name, price FROM products";
            preparedStatement = daoUtils.getConnection().prepareStatement(GET_ALL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productEntityList.add(ProductEntity.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getBigDecimal("price"))
                        .build());
            }

        } catch (SQLException e) {
            throw new DaoException("Exception trying to list all products", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
            daoUtils.closeResultSet(resultSet);
        }

        return productEntityList;
    }

    @Override
    public ProductEntity findById(Long id) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ProductEntity productEntity;

        try {

            String FIND_BY_ID = "SELECT id, name, price FROM products WHERE id = ?";
            preparedStatement = daoUtils.getConnection().prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                productEntity = ProductEntity.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getBigDecimal("price"))
                        .build();
            else throw new DaoException("Exception trying to find an specific product");

        } catch (SQLException e) {
            throw new DaoException("Exception trying to find an specific product", e);
        } finally {
            daoUtils.closePreparedStatement(preparedStatement);
            daoUtils.closeResultSet(resultSet);
        }

        return productEntity;
    }

}
