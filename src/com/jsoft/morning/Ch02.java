package com.jsoft.morning;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Ch02 {

    @Test
    public void test01() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(Ch01.class.getClassLoader().getResourceAsStream("hikari.properties"));

        HikariConfig hikariConfig = new HikariConfig(properties);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        System.out.println(hikariDataSource.getConnection());
    }
}
