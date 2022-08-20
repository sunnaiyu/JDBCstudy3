package com.jsoft.morning.demo2;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.jsoft.morning.Ch01;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

/**
 * 约定：
 * 1、表名和类名必须相同
 * 2、表的字段名和类的属性名必须相同
 *
 * @param <T> 泛型是要操作的类
 */

public class BaseDaoImpl<T> implements IBaseDao<T> {

    private static final DataSource DATA_SOURCE;

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

    @Override
    public Connection getConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeAll(Statement stmt, ResultSet rs) {
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

    /**
     * 通用的保存方法
     * @param object 传入一个要保存的对象
     */
    @Override
    public void save(Object object) {
        // insert into user(id,username,password) values (?,?,?)
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        // 拼接出一个insert语句
        StringBuilder strb = new StringBuilder("insert into ");
        // insert into user
        String[] split = clazz.getName().split("\\.");
        strb.append(split[split.length - 1]);
        strb.append(" (");
        for (Field field : fields) {
            strb.append(field.getName().toLowerCase()).append(",");
        }
        // insert into user (id,username,password
        strb.deleteCharAt(strb.length() - 1);
        strb.append(") values (");
        for (Field field : fields) {
            strb.append("?,");
        }
        strb.deleteCharAt(strb.length() - 1);
        strb.append(")");

        PreparedStatement pstmt = null;

        try {
            Connection conn = DATA_SOURCE.getConnection();
            pstmt = conn.prepareStatement(strb.toString());

            // 给?赋值
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                pstmt.setObject(i+1,fields[i].get(object));
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(pstmt,null);
        }
    }

    /**
     * 通用的查询所有的方法
     * @param clazz 要操作的对象.class类型
     * @return
     */
    @Override
    public List<T> findAll(Class clazz) {
        // 拼sql
        // select id,username,password from user
        // 其中id,username,password可变的他们都是一个类的属性
        List<T> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 利用反射获取属性名
        Field[] fields = clazz.getDeclaredFields();
        // 拼装sql语句，拼字符串
        StringBuilder fieldStr = new StringBuilder();
        fieldStr.append("select ");
        for (Field field : fields) {
            // id,username,password,
            fieldStr.append(field.getName().toLowerCase()).append(",");
        }
        // select id,username,password
        fieldStr.deleteCharAt(fieldStr.length() - 1);
        fieldStr.append(" from ");
        // select id,username,password from
        //
        String clazzName = clazz.getName().toLowerCase();
        System.out.println(clazzName + "--------------------");
        String[] split = clazzName.split("\\.");
        fieldStr.append(split[split.length - 1]);
        // select id,username,password from user
        Connection conn = getConnection();
        try {
            pstmt = conn.prepareStatement(fieldStr.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                // 1. 创建对象
                Object obj = clazz.getDeclaredConstructor().newInstance();
                for (Field field : fields) {
                    Object value = rs.getObject(field.getName());
                    // 访问私有化的结构
                    field.setAccessible(true);
                    // 利用反射给属性赋值，赋不上值
                    // 因为属性一定是private
                    field.set(obj,value);
                }
                list.add((T) obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(pstmt,rs);
        }

        return list;
    }

    /**
     * 通用的修改
     * @param obj 要修改的对象
     * @param fieldName 根据什么去修改数据 id
     * @param fieldValue 根据条件的值 1
     */
    @Override
    public void update(Object obj, String fieldName, Object fieldValue) {
        PreparedStatement pstmt = null;

        Class clazz = obj.getClass();

        // 拼接出一个update语句
        // update user set
        StringBuilder strb = new StringBuilder("update " + clazz.getName().toLowerCase().substring(clazz.getName().lastIndexOf(".") + 1) + " set ");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // update user set username = ?,password = ?,
            strb.append(field.getName()).append(" = ").append("?").append(",");
        }
        strb.deleteCharAt(strb.length() - 1);
        // update user set username = ?,password = ?
        strb.append(" where ").append(fieldName).append("=").append(fieldValue);
//        System.out.println(strb.toString());
        try {
            Connection conn = DATA_SOURCE.getConnection();
            pstmt = conn.prepareStatement(strb.toString());
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                pstmt.setObject(i+1,fields[i].get(obj));
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(pstmt,null);
        }
    }

    /**
     * 通用的删除
     * @param clazz 要删除的类.class
     * @param fieldName 根据什么去删除 id
     * @param fieldValue 根据的条件的值 1
     */
    @Override
    public void delete(Class clazz, String fieldName, Object fieldValue) {
        // 拼接一个delete语句
        PreparedStatement pstmt = null;
        StringBuilder sql = new StringBuilder("delete from ");
        // delete from user
        sql.append(clazz.getName().toLowerCase().substring(clazz.getName().lastIndexOf(".") + 1));
        sql.append(" where ").append(fieldName).append(" = ?");
        try {
            Connection conn = DATA_SOURCE.getConnection();
            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setObject(1,fieldValue);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeAll(pstmt,null);
        }
    }

    /**
     * 查询某一条记录
     * @param clazz 要查询的类.class
     * @param fieldName 根据什么去查询 id
     * @param fieldValue 查询的条件的值 1
     * @return
     */
    @Override
    public T findOne(Class clazz, String fieldName, Object fieldValue) {
        T t = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 拼接一个select语句
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder strb = new StringBuilder();
        strb.append("select ");
        for (Field field : fields) {
            strb.append(field.getName().toLowerCase()).append(",");
        }
        strb.deleteCharAt(strb.length() - 1);
        // select id,username,password
        strb.append(" from ");
        strb.append(clazz.getName().toLowerCase().substring(clazz.getName().lastIndexOf(".") + 1));
        // select id,username,password from user
        strb.append(" where ");
        strb.append(fieldName).append("= ?");
        // select id,username,password from user where id = ?
        try {
            Connection conn = DATA_SOURCE.getConnection();
            pstmt = conn.prepareStatement(strb.toString());
            pstmt.setObject(1,fieldValue);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Object o = clazz.getDeclaredConstructor().newInstance();
                for (Field field : fields) {
                    Object value = rs.getObject(field.getName());
                    field.setAccessible(true);
                    field.set(o,value);
                }
                t = (T) o;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return t;
    }
}
