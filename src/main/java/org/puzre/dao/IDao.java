package org.puzre.dao;

import org.puzre.dao.exception.DaoException;

import java.util.List;

public interface IDao<T, K> {

    void insert(T t) throws DaoException;
    void update(T t);
    void delete(T t);
    List<T> getAll();
    T getById(K id);
    T getByIds(K pId, K sId);

}
