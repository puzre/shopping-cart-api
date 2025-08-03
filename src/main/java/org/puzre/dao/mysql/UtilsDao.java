package org.puzre.dao.mysql;

import org.puzre.dao.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilsDao {

    public static void closePreparedStatement(PreparedStatement preparedStatement) throws DaoException {
        if (preparedStatement != null)
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException("Exception closing preparedStatement", e);
            }
    }

    public static void closeResultSet(ResultSet resultSet) throws DaoException {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DaoException("Exception closing resultSet", e);
            }
    }

}
