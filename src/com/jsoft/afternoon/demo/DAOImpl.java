package com.jsoft.afternoon.demo;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

public class DAOImpl<T> implements DAO<T> {

    private QueryRunner runner = null;
    private Class<T> type;

    /**
     * 这个构造器中在做的事：
     *  为了获取Class<T> type = Teacher.class
     */
    public DAOImpl() {
        runner = new QueryRunner(JDBCUtil.getDataSource());
        // 获得当前类的带有泛型类型的父类（运行期this其实是DAOImpl的某个子类）
        ParameterizedType ptClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        type = (Class<T>) ptClass.getActualTypeArguments()[0];
    }

    @Override
    public int update(String sql, Object... args) throws Exception {
        return runner.update(sql,args);
    }

    @Override
    public List<T> getForList(String sql, Object... args) throws Exception {
        return runner.query(sql,new BeanListHandler<>(type),args);
    }

    @Override
    public T get(String sql, Object... args) throws Exception {
        return runner.query(sql,new BeanHandler<>(type),args);
    }

    @Override
    public <E> E getForValue(String sql, Object... args) throws SQLException {

        return (E) runner.query(sql,new ScalarHandler<>(),args);
    }
}
