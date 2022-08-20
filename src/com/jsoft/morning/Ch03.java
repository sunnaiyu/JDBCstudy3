package com.jsoft.morning;

import com.jsoft.util.BaseDao;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class Ch03 {

    @Test
    public void test01() {
        try {
            Connection connection = BaseDao.DATA_SOURCE.getConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
