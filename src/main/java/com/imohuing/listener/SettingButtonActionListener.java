package com.imohuing.listener;

import com.imohuing.frame.HistoryModel;
import com.imohuing.frame.IpSettingFrame;
import com.imohuing.datasource.DataSourceHelper;
import com.imohuing.datasource.module.SettingHistory;
import com.imohuing.util.BatUtil;
import com.imohuing.util.IpValidUtil;
import com.imohuing.util.TimerUtil;
import org.h2.util.StringUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author Yangws
 * @Date 2022-11-12 14:35
 * @Description
 * @Version 1.0.0
 **/
public class SettingButtonActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("AUTO")){
            IpSettingFrame.loadingLabel.setVisible(true);
            String batPath = IpSettingFrame.DynamicIpUrl;
            String result = BatUtil.runBat(batPath);
            IpSettingFrame.loadingLabel.setVisible(false);
            if(result.contains("success")){
                IpSettingFrame.messageLabel.setText("执行成功");
                IpSettingFrame.messageLabel.setVisible(true);
                IpSettingFrame.historyTable.setValueAt(true,0,IpSettingFrame.checkBoxColumn);
                TimerUtil.hidingMessage();
            }
        }else if(e.getActionCommand().equals("MANUAL")){
            int selectedRow = IpSettingFrame.historyTable.getSelectedRow();
            HistoryModel historyModel = (HistoryModel) IpSettingFrame.historyTable.getModel();
            String selectedId = historyModel.getSelectedRowId(selectedRow);

            String name = IpSettingFrame.nameTextField.getText();
            String adapter = IpSettingFrame.adapterTextField.getText();
            String ip = IpSettingFrame.ipTextField.getText();
            String mask = IpSettingFrame.maskTextField.getText();
            String gateway = IpSettingFrame.gatewayTextField.getText();
            String dns = IpSettingFrame.dnsTextField.getText();

            if("给这个ip地址取一个方便记忆的名称".equals(name) && "例如：192.168.110.243".equals(ip) && "例如：192.168.110.1".equals(gateway) && "例如：192.168.110.1".equals(dns)){
                return;
            }

            boolean isCheckedPass = true;

            if("".equals(name)){
                IpSettingFrame.nameRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }
            if("".equals(adapter)){
                IpSettingFrame.adapterRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }
            if("".equals(ip)){
                IpSettingFrame.ipRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }
            if("".equals(mask)){
                IpSettingFrame.maskRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }
            if("".equals(gateway)){
                IpSettingFrame.gatewayRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }
            if("".equals(dns)){
                IpSettingFrame.dnsRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }

            //校验数据格式
            if(!StringUtils.isNullOrEmpty(ip) && !IpValidUtil.valid(ip)){
                IpSettingFrame.ipRequiredLabel.setText("IP地址格式不正确");
                IpSettingFrame.ipRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }
            if(!StringUtils.isNullOrEmpty(mask) && !IpValidUtil.valid(mask)){
                IpSettingFrame.maskRequiredLabel.setText("子网掩码格式不正确");
                IpSettingFrame.maskRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }
            if(!StringUtils.isNullOrEmpty(gateway) && !IpValidUtil.valid(gateway)){
                IpSettingFrame.gatewayRequiredLabel.setText("网关地址格式不正确");
                IpSettingFrame.gatewayRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }
            if(!StringUtils.isNullOrEmpty(dns) && !IpValidUtil.valid(dns)){
                IpSettingFrame.dnsRequiredLabel.setText("DNS地址格式不正确");
                IpSettingFrame.dnsRequiredLabel.setVisible(true);
                isCheckedPass = false;
            }

            if(!isCheckedPass){
                return;
            }

            SettingHistory history = new SettingHistory();
            history.setDns(dns);
            history.setGateway(gateway);
            history.setName(name);
            history.setIp(ip);
            history.setMask(mask);
            history.setAdapter(adapter);

            boolean isExists = historyModel.selectIsExists(history);
            if(!isExists){
                DataSourceHelper.add(history);
                //刷新历史记录
                historyModel.reloadData();
                historyModel.fireTableDataChanged();

                if(historyModel.getRowCount() > 0){
                    IpSettingFrame.historyTable.setValueAt(true,0,0);
                    String newName = (String) IpSettingFrame.historyTable.getValueAt(0,1);
                    IpSettingFrame.nameTextField.setText(newName);
                    IpSettingFrame.nameTextField.setForeground(Color.black);
                    String newAdapter = (String) IpSettingFrame.historyTable.getValueAt(0,2);
                    IpSettingFrame.adapterTextField.setText(newAdapter);
                    IpSettingFrame.adapterTextField.setForeground(Color.black);
                    String newIp = (String) IpSettingFrame.historyTable.getValueAt(0,3);
                    IpSettingFrame.ipTextField.setText(newIp);
                    IpSettingFrame.ipTextField.setForeground(Color.black);
                    String newMask = (String) IpSettingFrame.historyTable.getValueAt(0,4);
                    IpSettingFrame.maskTextField.setText(newMask);
                    IpSettingFrame.maskTextField.setForeground(Color.black);
                    String newGateway = (String) IpSettingFrame.historyTable.getValueAt(0,5);
                    IpSettingFrame.gatewayTextField.setText(newGateway);
                    IpSettingFrame.gatewayTextField.setForeground(Color.black);
                    String nwDns = (String) IpSettingFrame.historyTable.getValueAt(0,6);
                    IpSettingFrame.dnsTextField.setText(nwDns);
                    IpSettingFrame.dnsTextField.setForeground(Color.black);

                    IpSettingFrame.nameRequiredLabel.setVisible(false);
                    IpSettingFrame.adapterRequiredLabel.setVisible(false);
                    IpSettingFrame.ipRequiredLabel.setVisible(false);
                    IpSettingFrame.maskRequiredLabel.setVisible(false);
                    IpSettingFrame.gatewayRequiredLabel.setVisible(false);
                    IpSettingFrame.dnsRequiredLabel.setVisible(false);
                }
            }else{
                DataSourceHelper.updateLastUsedTime(selectedId);
                //刷新历史记录
                historyModel.reloadData();
                historyModel.fireTableDataChanged();
            }
            String batPath = IpSettingFrame.StaticIpUrl;
            String result = BatUtil.runBat(batPath,adapter,ip,mask,gateway,dns);
            IpSettingFrame.loadingLabel.setVisible(false);
            if(result.contains("success")){
                IpSettingFrame.messageLabel.setText("执行成功");
                IpSettingFrame.messageLabel.setVisible(true);
                IpSettingFrame.historyTable.setValueAt(true,0,IpSettingFrame.checkBoxColumn);
                TimerUtil.hidingMessage();
            }
        }
        TimerUtil.setCurrentIpAddr();
    }

}
