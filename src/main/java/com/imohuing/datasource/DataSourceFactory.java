package com.imohuing.datasource;

import java.sql.*;

/**
 * @Author Yangws
 * @Date 2022-11-16 23:54
 * @Description
 * @Version 1.0.0
 **/
public class DataSourceFactory {

    private static Connection connection;

    /**
     * 获取数据库连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if(null == connection){
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/test");
        }
        return connection;
    }

    /**
     * 关闭数据库连接
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        if(null != connection){
            connection.close();
        }
    }

    /**
     * 初始化历史记录表
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void init() throws SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        Statement stat = conn.createStatement();
        String createTableSql = "create table if not exists setting_history(\n" +
                "id varchar(32) primary key not null COMMENT '主键',\n" +
                "name varchar(50) not null COMMENT '名称',\n" +
                "ip varchar(15) not null COMMENT 'IP地址',\n" +
                "mask varchar(15) not null COMMENT '子网掩码',\n" +
                "adapter varchar(100) not null COMMENT '网络适配器',\n" +
                "gateway varchar(15) not null COMMENT '网关地址',\n" +
                "dns varchar(15) not null COMMENT 'DNS服务器地址',\n" +
                "last_used_time timestamp  not null COMMENT '最后使用时间'\n" +
                ")";
        String tableCommentSql = "comment on table setting_history is '历史记录'";
        stat.execute(createTableSql);
        stat.execute(tableCommentSql);
        stat.close();
    }

}
