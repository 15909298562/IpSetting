package com.imohuing.datasource;

import com.imohuing.datasource.module.SettingHistory;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author Yangws
 * @Date 2022-11-16 23:59
 * @Description
 * @Version 1.0.0
 **/
public class DataSourceHelper {

    /**
     * 新增历史记录
     */
    public static boolean add(SettingHistory history){
        Connection connection;
        boolean result;
        try {
            connection = DataSourceFactory.getConnection();
            String sql = "insert into setting_history values(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,UUID.randomUUID().toString().replace("-",""));
            preparedStatement.setString(2,history.getName());
            preparedStatement.setString(3,history.getIp());
            preparedStatement.setString(4,history.getMask());
            preparedStatement.setString(5,history.getAdapter());
            preparedStatement.setString(6,history.getGateway());
            preparedStatement.setString(7, history.getDns());
            preparedStatement.setTimestamp(8,new Timestamp(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli()));
            result = preparedStatement.execute();
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 编辑历史记录
     */
    public static int edit(SettingHistory history){
        Connection connection;
        int result;
        try {
            connection = DataSourceFactory.getConnection();
            String sql = "update setting_history set name=?,ip=?,mask=?,adapter=?,gateway=?,dns=? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,history.getName());
            preparedStatement.setString(2,history.getIp());
            preparedStatement.setString(3,history.getMask());
            preparedStatement.setString(4,history.getAdapter());
            preparedStatement.setString(5,history.getGateway());
            preparedStatement.setString(6, history.getDns());
            preparedStatement.setString(7, history.getId());
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 更新最后使用时间
     */
    public static int updateLastUsedTime(String id){
        Connection connection;
        int result;
        try {
            connection = DataSourceFactory.getConnection();
            String sql = "update setting_history set last_used_time = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1,new Timestamp(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli()));
            preparedStatement.setString(2,id);
            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 删除历史记录
     */
    public static boolean delete(String id){
        Connection connection;
        boolean result;
        try {
            connection = DataSourceFactory.getConnection();
            String sql = "delete from setting_history where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            result = preparedStatement.execute();
            preparedStatement.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 查询全部历史记录
     */
    public static List<SettingHistory> select(){
        List<SettingHistory> list = new ArrayList<>();
        Connection connection;
        try {
            connection = DataSourceFactory.getConnection();
            String sql = "select * from setting_history order by last_used_time desc";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                SettingHistory history = new SettingHistory();
                history.setId(resultSet.getString("id"));
                history.setName(resultSet.getString("name"));
                history.setIp(resultSet.getString("ip"));
                history.setMask(resultSet.getString("mask"));
                history.setAdapter(resultSet.getString("adapter"));
                history.setGateway(resultSet.getString("gateway"));
                history.setDns(resultSet.getString("dns"));
                history.setLastUsedTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getTimestamp("last_used_time").getTime()),ZoneOffset.ofHours(8)));
                list.add(history);
            }
            statement.close();
            resultSet.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

}
