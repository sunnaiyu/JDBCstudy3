package com.jsoft.afternoon;

import com.jsoft.morning.demo2.Teacher;
import com.jsoft.util.BaseDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * 需要引入一个依赖jar包
 * DBUtils
 */
public class Ch01 {

    @Test
    public void test03() throws SQLException {

        QueryRunner runner = new QueryRunner(BaseDao.DATA_SOURCE);
        int i = runner.update("update teacher set name = ? where id = ?", "mmm", 6);
        System.out.println(i);

    }

    /**
     * 查询一个记录
     */
    @Test
    public void test02() throws SQLException {
        QueryRunner runner = new QueryRunner(BaseDao.DATA_SOURCE);
        Teacher teacher = runner.query("select * from teacher where id = ?", new BeanHandler<>(Teacher.class), 1);
        System.out.println(teacher);
    }

    /**
     * 查询多个记录
     * @throws SQLException
     */
    @Test
    public void test01() throws SQLException {
        // 要使用DBUtils使用的是一个类
        // 传入的是一个数据源DataSource，不是一个Connection
        QueryRunner runner = new QueryRunner(BaseDao.DATA_SOURCE);
        // 查询多个记录
        List<Teacher> teachers = runner.query("select * from teacher", new BeanListHandler<>(Teacher.class));
        System.out.println(teachers);
    }

}
