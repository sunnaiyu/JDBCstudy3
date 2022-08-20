package com.jsoft.morning.demo2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * 约束
 * 约定
 */
public interface IBaseDao<T> {

    /**
     * 获取连接的方法
     */
    Connection getConnection();

    /**
     * 关闭资源
     */
    void closeAll(Statement statement, ResultSet resultSet);

    /**
     * 通用的保存
     */
    void save(Object object);

    /**
     * 通用的查询所有
     */
    List<T> findAll(Class clazz);

    /**
     * 通用的更新的方法
     */
    void update(Object obj,String fieldName,Object fieldValue);

    /**
     * 通用的删除
     */
    void delete(Class clazz,String fieldName,Object fieldValue);


    /**
     * 查询单条数据
     */
    T findOne(Class clazz,String fieldName,Object fieldValue);
}
