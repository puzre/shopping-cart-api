package org.puzre.dao;

import org.puzre.model.Order;
import org.puzre.model.Product;

public interface IOrderDao extends IDao<Order, Long>, IRelationDao<Product, Long> {
}
