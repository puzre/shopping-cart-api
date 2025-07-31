package org.puzre.dao.mysql;

import org.puzre.dao.IOrderDao;
import org.puzre.model.Order;

import java.util.List;

public class OrderDao implements IOrderDao {
    @Override
    public void insert(Order order) {

    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(Order order) {

    }

    @Override
    public List<Order> getAll() {
        return List.of();
    }

    @Override
    public Order getById(Long id) {
        return null;
    }

    @Override
    public Order getByIds(Long pId, Long sId) {
        return null;
    }
}
