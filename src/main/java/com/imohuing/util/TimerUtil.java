package com.imohuing.util;

import com.imohuing.frame.IpSettingFrame;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author Yangws
 * @Date 2022-11-29 16:23
 * @Description
 * @Version 1.0.0
 **/
public class TimerUtil {

    private static Timer timer;

    /**
     * 3秒之后将消息隐藏
     */
    public static void hidingMessage(){
        Timer hidingMessageTimer = getTimerInstance();
        hidingMessageTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                IpSettingFrame.messageLabel.setVisible(false);
            }
        },1000*3);
    }

    /**
     * 10秒之后重新获取当前主机的IP地址并将该IP地址赋值给状态栏
     */
    public static void setCurrentIpAddr(){
        Timer getCurrentIpAddrTimer = getTimerInstance();
        getCurrentIpAddrTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                String ipAddr = "127.0.0.1";
                try{
                    ipAddr = Inet4Address.getLocalHost().getHostAddress();
                }catch(UnknownHostException exception){
                    exception.printStackTrace();
                }
                String statusText = "当前IP地址为：" + ipAddr;
                IpSettingFrame.currentIpLabel.setText(statusText);
            }
        },1000*10);
    }

    /**
     * 拿到timer实例
     * @return
     */
    public static Timer getTimerInstance(){
        if(null == timer){
            timer = new Timer();
        }
        return timer;
    }

}
