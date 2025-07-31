package org.puzre.dao.mysql;

import org.puzre.dao.IOrdersWithProductsDao;
import org.puzre.model.OrdersWithProducts;

import java.util.List;

public class OrdersWithProductsDao implements IOrdersWithProductsDao {
    @Override
    public void insert(OrdersWithProducts ordersWithProducts) {

    }

    @Override
    public void update(OrdersWithProducts ordersWithProducts) {

    }

    @Override
    public void delete(OrdersWithProducts ordersWithProducts) {

    }

    @Override
    public List<OrdersWithProducts> getAll() {
        return List.of();
    }

    @Override
    public OrdersWithProducts getById(Long id) {
        return null;
    }

    @Override
    public OrdersWithProducts getByIds(Long pId, Long sId) {
        return null;
    }
}
