package com.imohuing.listener;

import com.imohuing.frame.IpSettingFrame;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @Author Yangws
 * @Date 2022-11-12 11:56
 * @Description
 * @Version 1.0.0
 **/
public class IpInfoFocusListener implements FocusListener {
    @Override
    public void focusGained(FocusEvent e) {
        if(e.getSource() == IpSettingFrame.nameTextField && IpSettingFrame.nameTextField.getText().equals("给这个ip地址取一个方便记忆的名称")){
            IpSettingFrame.nameTextField.setText("");
            IpSettingFrame.nameTextField.setForeground(Color.black);
            IpSettingFrame.nameRequiredLabel.setVisible(false);
        }else if(e.getSource() == IpSettingFrame.adapterTextField && IpSettingFrame.adapterTextField.getText().equals("网络适配器的名称，例如：WLAN、蓝牙网络链接")){
            IpSettingFrame.adapterTextField.setText("");
            IpSettingFrame.adapterTextField.setForeground(Color.black);
            IpSettingFrame.adapterRequiredLabel.setVisible(false);
        }else if(e.getSource() == IpSettingFrame.ipTextField && IpSettingFrame.ipTextField.getText().equals("例如：192.168.110.243")){
            IpSettingFrame.ipTextField.setText("");
            IpSettingFrame.ipTextField.setForeground(Color.black);
            IpSettingFrame.ipRequiredLabel.setVisible(false);
        }else if(e.getSource() == IpSettingFrame.maskTextField && IpSettingFrame.maskTextField.getText().equals("例如：255.255.255.0")){
            IpSettingFrame.maskTextField.setText("");
            IpSettingFrame.maskTextField.setForeground(Color.black);
            IpSettingFrame.maskRequiredLabel.setVisible(false);
        }else if(e.getSource() == IpSettingFrame.gatewayTextField && IpSettingFrame.gatewayTextField.getText().equals("例如：192.168.110.1")){
            IpSettingFrame.gatewayTextField.setText("");
            IpSettingFrame.gatewayTextField.setForeground(Color.black);
            IpSettingFrame.gatewayRequiredLabel.setVisible(false);
        }else if(e.getSource() == IpSettingFrame.dnsTextField && IpSettingFrame.dnsTextField.getText().equals("例如：192.168.110.1")){
            IpSettingFrame.dnsTextField.setText("");
            IpSettingFrame.dnsTextField.setForeground(Color.black);
            IpSettingFrame.dnsRequiredLabel.setVisible(false);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource() == IpSettingFrame.nameTextField && IpSettingFrame.nameTextField.getText().equals("")){
            IpSettingFrame.nameTextField.setText("给这个ip地址取一个方便记忆的名称");
            IpSettingFrame.nameTextField.setForeground(new Color(0xD7D7D7));
            IpSettingFrame.nameRequiredLabel.setText("名称不能为空");
            IpSettingFrame.nameRequiredLabel.setVisible(true);
        }else if(e.getSource() == IpSettingFrame.adapterTextField && IpSettingFrame.adapterTextField.getText().equals("")){
            IpSettingFrame.adapterTextField.setText("例如：WLAN、本地连接");
            IpSettingFrame.adapterTextField.setForeground(new Color(0xD7D7D7));
            IpSettingFrame.adapterRequiredLabel.setText("网络适配器不能为空");
            IpSettingFrame.adapterRequiredLabel.setVisible(true);
        }else if(e.getSource() == IpSettingFrame.ipTextField && IpSettingFrame.ipTextField.getText().equals("")){
            IpSettingFrame.ipTextField.setText("例如：192.168.110.243");
            IpSettingFrame.ipTextField.setForeground(new Color(0xD7D7D7));
            IpSettingFrame.ipRequiredLabel.setText("ip地址不能为空");
            IpSettingFrame.ipRequiredLabel.setVisible(true);
        }else if(e.getSource() == IpSettingFrame.maskTextField && IpSettingFrame.maskTextField.getText().equals("")){
            IpSettingFrame.maskTextField.setText("例如：255.255.255.0");
            IpSettingFrame.maskTextField.setForeground(new Color(0xD7D7D7));
            IpSettingFrame.maskRequiredLabel.setText("子网掩码不能为空");
            IpSettingFrame.maskRequiredLabel.setVisible(true);
        }else if(e.getSource() == IpSettingFrame.gatewayTextField && IpSettingFrame.gatewayTextField.getText().equals("")){
            IpSettingFrame.gatewayTextField.setText("例如：192.168.110.1");
            IpSettingFrame.gatewayTextField.setForeground(new Color(0xD7D7D7));
            IpSettingFrame.gatewayRequiredLabel.setText("网关地址不能为空");
            IpSettingFrame.gatewayRequiredLabel.setVisible(true);
        }else if(e.getSource() == IpSettingFrame.dnsTextField && IpSettingFrame.dnsTextField.getText().equals("")){
            IpSettingFrame.dnsTextField.setText("例如：192.168.110.1");
            IpSettingFrame.dnsTextField.setForeground(new Color(0xD7D7D7));
            IpSettingFrame.dnsRequiredLabel.setText("dns地址不能为空");
            IpSettingFrame.dnsRequiredLabel.setVisible(true);
        }

    }
}
