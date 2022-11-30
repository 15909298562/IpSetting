package com.imohuing.listener;

import com.imohuing.editor.TableDefaultEditor;
import com.imohuing.frame.HistoryModel;
import com.imohuing.frame.IpSettingFrame;
import com.imohuing.datasource.DataSourceHelper;
import com.imohuing.datasource.module.SettingHistory;
import com.imohuing.editor.OperationEditor;
import com.imohuing.renderer.TableDefaultRenderer;
import com.imohuing.util.BatUtil;
import com.imohuing.util.IpUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

/**
 * @Author Yangws
 * @Date 2022-11-12 14:35
 * @Description
 * @Version 1.0.0
 **/
public class OperationActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

        /**
         * 设置选中行的状态
         */
        int selectedRow = IpSettingFrame.historyTable.getSelectedRow();
        HistoryModel historyModel = (HistoryModel) IpSettingFrame.historyTable.getModel();
        boolean isFull = (boolean) IpSettingFrame.historyTable.getValueAt(selectedRow,IpSettingFrame.checkBoxColumn);
        int rowCount = IpSettingFrame.historyTable.getRowCount();
        if(!isFull){
            for(int i=0;i<rowCount;i++){
                if(i == selectedRow){
                    IpSettingFrame.historyTable.setValueAt(true,i,IpSettingFrame.checkBoxColumn);
                }else{
                    IpSettingFrame.historyTable.setValueAt(false,i,IpSettingFrame.checkBoxColumn);
                    IpSettingFrame.historyTable.setValueAt(false,i,IpSettingFrame.operationColumn);
                }
            }
        }

        if(e.getActionCommand().equals("EDIT")){

            for(int i=1;i<IpSettingFrame.historyTable.getColumnCount()-1;i++){
                TableDefaultEditor tableDefaultEditor = (TableDefaultEditor) IpSettingFrame.historyTable.getColumnModel().getColumn(i).getCellEditor();
                tableDefaultEditor.resetUserInputValue();
            }

            IpSettingFrame.historyTable.getModel().setValueAt(true,selectedRow,IpSettingFrame.operationColumn);
            OperationEditor operationEditor = (OperationEditor) IpSettingFrame.historyTable.getCellEditor(selectedRow,IpSettingFrame.operationColumn);
            operationEditor.setOperationPanelBackground(new Color(0x81D3F8));

            operationEditor.setEditButtonEnabled(false);
            operationEditor.setdeleteButtonVisible(false);
            operationEditor.setSaveButtonVisible(true);

            IpSettingFrame.nameTextField.setText((String) IpSettingFrame.historyTable.getValueAt(selectedRow,1));
            IpSettingFrame.adapterTextField.setText((String) IpSettingFrame.historyTable.getValueAt(selectedRow,2));
            IpSettingFrame.ipTextField.setText((String) IpSettingFrame.historyTable.getValueAt(selectedRow,3));
            IpSettingFrame.maskTextField.setText((String) IpSettingFrame.historyTable.getValueAt(selectedRow,4));
            IpSettingFrame.gatewayTextField.setText((String) IpSettingFrame.historyTable.getValueAt(selectedRow,5));
            IpSettingFrame.dnsTextField.setText((String) IpSettingFrame.historyTable.getValueAt(selectedRow,6));

            IpSettingFrame.nameTextField.setForeground(Color.black);
            IpSettingFrame.adapterTextField.setForeground(Color.black);
            IpSettingFrame.ipTextField.setForeground(Color.black);
            IpSettingFrame.maskTextField.setForeground(Color.black);
            IpSettingFrame.gatewayTextField.setForeground(Color.black);
            IpSettingFrame.dnsTextField.setForeground(Color.black);

            IpSettingFrame.nameRequiredLabel.setVisible(false);
            IpSettingFrame.adapterRequiredLabel.setVisible(false);
            IpSettingFrame.ipRequiredLabel.setVisible(false);
            IpSettingFrame.maskRequiredLabel.setVisible(false);
            IpSettingFrame.gatewayRequiredLabel.setVisible(false);
            IpSettingFrame.dnsRequiredLabel.setVisible(false);
        }else if(e.getActionCommand().equals("SAVE")){
            TableDefaultRenderer nameRenderer = ((TableDefaultRenderer)IpSettingFrame.historyTable.getCellRenderer(selectedRow,1));
            TableDefaultRenderer adapterRenderer = ((TableDefaultRenderer)IpSettingFrame.historyTable.getCellRenderer(selectedRow,2));
            TableDefaultRenderer ipRenderer = ((TableDefaultRenderer)IpSettingFrame.historyTable.getCellRenderer(selectedRow,3));
            TableDefaultRenderer maskRenderer = ((TableDefaultRenderer)IpSettingFrame.historyTable.getCellRenderer(selectedRow,4));
            TableDefaultRenderer gatewayRenderer = ((TableDefaultRenderer)IpSettingFrame.historyTable.getCellRenderer(selectedRow,5));
            TableDefaultRenderer dnsRenderer = ((TableDefaultRenderer)IpSettingFrame.historyTable.getCellRenderer(selectedRow,6));
            String name = nameRenderer.getText();
            String adapter = adapterRenderer.getText();
            String ip = ipRenderer.getText();
            String mask = maskRenderer.getText();
            String gateway = gatewayRenderer.getText();
            String dns = dnsRenderer.getText();

            if("名称不能为空".equals(name) || "适配器不能为空".equals(adapter) || "ip地址不能为空".equals(ip) || "子网掩码不能为空".equals(mask) || "网关不能为空".equals(gateway) || "DNS不能为空".equals(dns)){
                return;
            }

            TableDefaultEditor nameEditor = ((TableDefaultEditor)IpSettingFrame.historyTable.getCellEditor(selectedRow,1));
            TableDefaultEditor adapterEditor = ((TableDefaultEditor)IpSettingFrame.historyTable.getCellEditor(selectedRow,2));
            TableDefaultEditor ipEditor = ((TableDefaultEditor)IpSettingFrame.historyTable.getCellEditor(selectedRow,3));
            TableDefaultEditor maskEditor = ((TableDefaultEditor)IpSettingFrame.historyTable.getCellEditor(selectedRow,4));
            TableDefaultEditor gatewayEditor = ((TableDefaultEditor)IpSettingFrame.historyTable.getCellEditor(selectedRow,5));
            TableDefaultEditor dnsEditor = ((TableDefaultEditor)IpSettingFrame.historyTable.getCellEditor(selectedRow,6));
            nameEditor.resetUserInputValue();
            adapterEditor.resetUserInputValue();
            ipEditor.resetUserInputValue();
            maskEditor.resetUserInputValue();
            gatewayEditor.resetUserInputValue();
            dnsEditor.resetUserInputValue();

            IpSettingFrame.historyTable.setValueAt(false,selectedRow,IpSettingFrame.operationColumn);
            OperationEditor operationEditor = (OperationEditor) IpSettingFrame.historyTable.getCellEditor(selectedRow,IpSettingFrame.operationColumn);
            operationEditor.setEditButtonEnabled(true);
            operationEditor.setdeleteButtonVisible(true);
            operationEditor.setSaveButtonVisible(false);

            SettingHistory settingHistory = new SettingHistory();
            settingHistory.setAdapter(adapter);
            settingHistory.setName(name);
            settingHistory.setIp(ip);
            settingHistory.setMask(mask);
            settingHistory.setDns(dns);
            settingHistory.setGateway(gateway);
            boolean isExists = historyModel.selectIsExists(settingHistory);
            if(!isExists){
                IpSettingFrame.historyTable.setValueAt(name,selectedRow,1);
                IpSettingFrame.historyTable.setValueAt(adapter,selectedRow,2);
                IpSettingFrame.historyTable.setValueAt(ip,selectedRow,3);
                IpSettingFrame.historyTable.setValueAt(mask,selectedRow,4);
                IpSettingFrame.historyTable.setValueAt(gateway,selectedRow,5);
                IpSettingFrame.historyTable.setValueAt(dns,selectedRow,6);

                historyModel.fireTableRowsUpdated(selectedRow,selectedRow);

                //编辑历史记录
                settingHistory.setId(historyModel.getSelectedRowId(selectedRow));
                DataSourceHelper.edit(settingHistory);
            }
        } else if(e.getActionCommand().equals("DELETE")){
            String id = historyModel.getSelectedRowId(selectedRow);

            IpSettingFrame.historyTable.getCellEditor(selectedRow,IpSettingFrame.operationColumn).cancelCellEditing();
            historyModel.deleteRow(selectedRow);
            historyModel.fireTableRowsDeleted(selectedRow,selectedRow);
            if(historyModel.getRowCount() > 0){
                IpSettingFrame.historyTable.setValueAt(true,0,0);
                String name = (String) IpSettingFrame.historyTable.getValueAt(0,1);
                IpSettingFrame.nameTextField.setText(name);
                IpSettingFrame.nameTextField.setForeground(Color.black);
                String adapter = (String) IpSettingFrame.historyTable.getValueAt(0,2);
                IpSettingFrame.adapterTextField.setText(adapter);
                IpSettingFrame.adapterTextField.setForeground(Color.black);
                String ip = (String) IpSettingFrame.historyTable.getValueAt(0,3);
                IpSettingFrame.ipTextField.setText(ip);
                IpSettingFrame.ipTextField.setForeground(Color.black);
                String mask = (String) IpSettingFrame.historyTable.getValueAt(0,4);
                IpSettingFrame.maskTextField.setText(mask);
                IpSettingFrame.maskRequiredLabel.setForeground(Color.black);
                String gateway = (String) IpSettingFrame.historyTable.getValueAt(0,5);
                IpSettingFrame.gatewayTextField.setText(gateway);
                IpSettingFrame.gatewayTextField.setForeground(Color.black);
                String dns = (String) IpSettingFrame.historyTable.getValueAt(0,6);
                IpSettingFrame.dnsTextField.setText(dns);
                IpSettingFrame.dnsTextField.setForeground(Color.black);

                IpSettingFrame.nameRequiredLabel.setVisible(false);
                IpSettingFrame.adapterRequiredLabel.setVisible(false);
                IpSettingFrame.ipRequiredLabel.setVisible(false);
                IpSettingFrame.maskRequiredLabel.setVisible(false);
                IpSettingFrame.gatewayRequiredLabel.setVisible(false);
                IpSettingFrame.dnsRequiredLabel.setVisible(false);
            }else{
                IpSettingFrame.nameTextField.setText("给这个ip地址取一个方便记忆的名称");
                IpSettingFrame.nameTextField.setForeground(new Color(0xD7D7D7));
                String adapter = null;
                try {
                    adapter = BatUtil.getCurrentAdapter();
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                IpSettingFrame.adapterTextField.setText(adapter);
                IpSettingFrame.adapterTextField.setForeground(Color.black);
                IpSettingFrame.ipTextField.setText("例如：192.168.110.243");
                IpSettingFrame.ipTextField.setForeground(new Color(0xD7D7D7));
                String mask = IpUtil.getNetMask();
                IpSettingFrame.maskTextField.setText(mask);
                IpSettingFrame.maskTextField.setForeground(Color.black);
                IpSettingFrame.gatewayTextField.setText("例如：192.168.110.1");
                IpSettingFrame.gatewayTextField.setForeground(new Color(0xD7D7D7));
                IpSettingFrame.dnsTextField.setText("例如：192.168.110.1");
                IpSettingFrame.dnsTextField.setForeground(new Color(0xD7D7D7));
            }

            //删除历史记录
            DataSourceHelper.delete(id);
        }
    }
}
