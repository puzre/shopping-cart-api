package org.puzre.dao;

import org.puzre.dao.exception.DaoException;

import java.util.List;

public interface IRelationDao<T, K> {

    List<T> getAllRelationById(K id) throws DaoException;

}
