package org.puzre.dao;

import org.puzre.dao.exception.DaoException;

import java.util.List;

public interface IDao<T, K> {

    void insert(T t) throws DaoException;
    void update(T t) throws DaoException;
    void delete(T t) throws DaoException;
    List<T> getAll() throws DaoException;
    T findById(K id) throws DaoException;
    

}
