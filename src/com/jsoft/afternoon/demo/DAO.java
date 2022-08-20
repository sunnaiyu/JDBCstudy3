package com.jsoft.afternoon.demo;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    /**
     * 更新
     * @return
     */
    int update(String sql,Object ... args) throws Exception;

    /**
     * 通用的查询所有
     */
    List<T> getForList(String sql,Object... args) throws Exception;

    /**
     * 通用的查询单个
     */
    T get(String sql,Object...args) throws Exception;

    /**
     * 查询某一个列的值，统计
     */
    <E> E getForValue(String sql,Object ... args) throws SQLException;

}
