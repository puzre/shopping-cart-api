package org.puzre.dao.mysql;

import org.puzre.dao.IManagerDao;

import javax.sql.DataSource;

public class ManagerDao implements IManagerDao {

    private static ManagerDao instance;

    private final DataSource dataSource;

    private OrderDao orderDao;
    private ProductDao productDao;
    private UserDao userDao;

    public ManagerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ManagerDao getInstance(DataSource dataSource) {
        if (instance == null)
            return new ManagerDao(dataSource);
        return instance;
    }

    @Override
    public OrderDao getOrderDao() {
        if (this.orderDao == null)
            return new OrderDao(this.dataSource);
        return this.orderDao;
    }

    @Override
    public ProductDao getProductDao() {
        if (this.productDao == null)
            return new ProductDao(this.dataSource);
        return this.productDao;
    }

    @Override
    public UserDao getUserDao() {
        if (this.userDao == null)
            return new UserDao(this.dataSource);
        return this.userDao;
    }
}
