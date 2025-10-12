package org.puzre.adapter.infrastructure.dao.util;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.puzre.adapter.infrastructure.dao.exception.DaoException;

import java.sql.*;

@ApplicationScoped
@RequiredArgsConstructor
public class DaoUtils {

    public Connection getConnection() throws DaoException {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("", "", "");
        } catch (SQLException e) {
            throw new DaoException("Exception trying to get a db connection", e);
        }

        return connection;
    }

    public void closePreparedStatement(PreparedStatement preparedStatement) throws DaoException {
        if (preparedStatement != null)
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DaoException("Exception closing preparedStatement", e);
            }
    }

    public void closeResultSet(ResultSet resultSet) throws DaoException {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DaoException("Exception closing resultSet", e);
            }
    }

}
