package com.imohuing.listener;

import com.imohuing.editor.TableDefaultEditor;
import com.imohuing.frame.IpSettingFrame;
import com.imohuing.editor.OperationEditor;
import com.imohuing.util.BatUtil;
import com.imohuing.util.IpUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;

/**
 * @Author Yangws
 * @Date 2022-11-10 22:20
 * @Description
 * @Version 1.0.0
 **/
public class TableMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {

        for(int i=1;i<IpSettingFrame.historyTable.getColumnCount()-1;i++){
            TableDefaultEditor tableDefaultEditor = (TableDefaultEditor) IpSettingFrame.historyTable.getColumnModel().getColumn(i).getCellEditor();
            tableDefaultEditor.resetUserInputValue();
        }

        JTable table = (JTable) e.getSource();
        int selectedRow = table.getSelectedRow();
        if(selectedRow >= 0){
            boolean isFull = (boolean)table.getValueAt(selectedRow,IpSettingFrame.checkBoxColumn);
            int rowCount = table.getRowCount();
            if(!isFull){
                for(int i=0;i<rowCount;i++){
                    if(i == selectedRow){
                        table.setValueAt(true,i,IpSettingFrame.checkBoxColumn);
                        IpSettingFrame.nameTextField.setText((String)table.getValueAt(i,1));
                        IpSettingFrame.nameTextField.setForeground(Color.black);
                        IpSettingFrame.adapterTextField.setText((String)table.getValueAt(i,2));
                        IpSettingFrame.adapterTextField.setForeground(Color.black);
                        IpSettingFrame.ipTextField.setText((String)table.getValueAt(i,3));
                        IpSettingFrame.ipTextField.setForeground(Color.black);
                        IpSettingFrame.maskTextField.setText((String)table.getValueAt(i,4));
                        IpSettingFrame.maskTextField.setForeground(Color.black);
                        IpSettingFrame.gatewayTextField.setText((String)table.getValueAt(i,5));
                        IpSettingFrame.gatewayTextField.setForeground(Color.black);
                        IpSettingFrame.dnsTextField.setText((String)table.getValueAt(i,6));
                        IpSettingFrame.dnsTextField.setForeground(Color.black);

                        IpSettingFrame.nameRequiredLabel.setVisible(false);
                        IpSettingFrame.adapterRequiredLabel.setVisible(false);
                        IpSettingFrame.ipRequiredLabel.setVisible(false);
                        IpSettingFrame.maskRequiredLabel.setVisible(false);
                        IpSettingFrame.gatewayRequiredLabel.setVisible(false);
                        IpSettingFrame.dnsRequiredLabel.setVisible(false);

                        OperationEditor operationEditor = (OperationEditor) IpSettingFrame.historyTable.getCellEditor(selectedRow,IpSettingFrame.operationColumn);
                        operationEditor.setOperationPanelBackground(new Color(0x81D3F8));
                    }else{
                        table.setValueAt(false,i,IpSettingFrame.checkBoxColumn);
                        table.setValueAt(false,i,IpSettingFrame.operationColumn);
                    }
                }
            }else{
                table.setValueAt(false,selectedRow,IpSettingFrame.checkBoxColumn);
                table.setValueAt(false,selectedRow,IpSettingFrame.operationColumn);
                IpSettingFrame.nameTextField.setText("给这个ip地址取一个方便记忆的名称");
                IpSettingFrame.nameTextField.setForeground(new Color(0xD7D7D7));
                String currentAdapter = null;
                try {
                    currentAdapter = BatUtil.getCurrentAdapter();
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
                IpSettingFrame.adapterTextField.setText(currentAdapter);
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
        }
    }
}
