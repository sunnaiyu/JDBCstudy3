package com.jsoft.afternoon.demo;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.jsoft.morning.Ch01;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class JDBCUtil {

    private static final DataSource DATA_SOURCE;

    static {
        Properties properties = new Properties();
        try {
            properties.load(Ch01.class.getClassLoader().getResourceAsStream("druid.properties"));
            DATA_SOURCE = DruidDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }

}
