package core.jdbc;

import jwp.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper {
    User mapRow(ResultSet rs) throws SQLException;
}
