package org.puzre.dao;

import org.puzre.dao.mysql.OrderDao;
import org.puzre.dao.mysql.ProductDao;
import org.puzre.dao.mysql.UserDao;

public interface IManagerDao {

    OrderDao getOrderDao();
    ProductDao getProductDao();
    UserDao getUserDao();

}
