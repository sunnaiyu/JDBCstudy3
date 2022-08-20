package com.jsoft.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.jsoft.morning.Ch01;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

public class BaseDao {

    public static final DataSource DATA_SOURCE;

    static {
        Properties properties = new Properties();
        try {
            properties.load(Ch01.class.getClassLoader().getResourceAsStream("druid.properties"));
            // 创建德鲁伊的数据源
            DATA_SOURCE = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void release(Statement stmt, ResultSet rs) {
        if(Objects.nonNull(stmt)) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(Objects.nonNull(rs)){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
