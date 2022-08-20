package com.jsoft.morning;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * mysql初级 + JDBC
 * 22号web前端一周的时间。JQuery，Vue
 * 29开始，JavaEE，java企业级开发阶段。
 * 到十一前，一个项目 + 框架的知识
 * 十一之后，框架的项目，分布式 + 微服务，十月下旬。
 *
 * 底层原理，源码解析，执行流程，JVM，数据结构，算法，mysql高级
 *
 * 数据库连接池：
 * connection是一种稀有资源，一个连接建立就创造了一个资源。
 * QQ连上了，我的QQ和腾讯的服务器建立了一个连接，有代价，同时在线人数很多，
 * 有可能导致服务器崩溃。
 *
 * 第一种方案：我一个人玩
 * 第二种方案：把服务器的人数限定一下，最多不超过10000人，
 * 第10001个人上线，排队。
 *
 * JDBC使用数据库连接的必要性。
 * 在使用基于web程序的数据库连接，
 * 1、在主程序中建立连接
 * 2、执行SQL
 * 3、断开连接
 *
 * 所有的JDBC连接通过DriverManager.getConnection。
 * 用完的连接不要被垃圾回收，能够重复使用
 *
 * “池化思想”
 *
 * 每次去初始化一个连接池，连接池中会有很多个连接等待被使用。
 * 使用完连接之后，不需要关闭连接，只需要把连接还回到连接池，
 * 还回到连接池的操作不需要我们手动控制。
 *
 * 初始化连接池，10条连接，来了20个请求，10个请求就直接拿10条连接去办事。
 * 剩下的10个请求，再向服务器申请连接数。
 *
 * 设置一些属性：最大等待时间。
 *
 * （1）C3P0，2代数据库连接池，太老了，不学
 * （2）DBCP，2代数据库连接池，太老了，不学
 * （3）Druid（德鲁伊）数据库连接池，最好用的连接池。
 *      阿里巴巴开源平台上的一个数据库连接池实现，整合了C3P0和DBCP各自的优点
 *      加入了日志监控，可以监控sql语句的执行情况。
 * （4）Hikari（光），目前最快的连接池。springboot默认的连接池。
 *
 *
 * 必须有对应的属性文件
 * .properties
 * 约定 > 配置 > 编码
 */
public class Ch01 {

    @Test
    public void test01() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.load(Ch01.class.getClassLoader().getResourceAsStream("druid.properties"));

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.configFromPropety(properties);

        System.out.println(druidDataSource.getConnection());
        System.out.println(druidDataSource.getCreateCount());

    }

}
