package com.imohuing.listener;

import com.imohuing.datasource.DataSourceFactory;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * @Author Yangws
 * @Date 2022-11-17 16:41
 * @Description
 * @Version 1.0.0
 **/
public class IpSettingFrameListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            DataSourceFactory.closeConnection();
        } catch (SQLException ex) {
            System.out.println("关闭h2连接失败！");
            throw new RuntimeException(ex);
        }
        super.windowClosing(e);
    }
}
