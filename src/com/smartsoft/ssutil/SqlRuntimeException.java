package com.smartsoft.ssutil;

import java.sql.SQLException;

public class SqlRuntimeException extends RuntimeException{

    public SqlRuntimeException(SQLException cause) {
        super(cause);
    }

    public SqlRuntimeException(String message, SQLException cause) {
        super(message,cause);
    }

    public SQLException getSQLException(){
        return (SQLException) getCause();
    }
    
}
