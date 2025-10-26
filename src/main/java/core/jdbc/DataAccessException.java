package core.jdbc;

import java.sql.SQLException;

public class DataAccessException extends RuntimeException {

    public DataAccessException(SQLException e) {
        super(e);
    }

    public DataAccessException(String message, SQLException e) {
        super(message, e);
    }
}
