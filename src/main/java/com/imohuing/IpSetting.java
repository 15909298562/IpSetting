package com.imohuing;

import com.imohuing.datasource.DataSourceFactory;
import com.imohuing.frame.IpSettingFrame;

import java.net.MalformedURLException;
import java.sql.SQLException;

/**
 * @Author Yangws
 * @Date 2022-11-17 15:41
 * @Description
 * @Version 1.0.0
 **/
public class IpSetting {

    public static void main(String[] args) throws MalformedURLException {
        try{
            DataSourceFactory.init();
        }catch (SQLException e) {
            System.out.println("初始化h2失败，h2数据库连接可能被占用！");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("初始化h2失败，加载h2驱动失败！");
            throw new RuntimeException(e);
        }
        System.out.println();
        IpSettingFrame.getInstance();
    }

}
