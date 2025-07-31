package org.puzre.dao.mysql;

import org.puzre.dao.IProductDao;
import org.puzre.model.Product;

import java.util.List;

public class ProductDao implements IProductDao {
    @Override
    public void insert(Product product) {

    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Product product) {

    }

    @Override
    public List<Product> getAll() {
        return List.of();
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Product getByIds(Long pId, Long sId) {
        return null;
    }
}
