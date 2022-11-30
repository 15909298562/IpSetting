package com.imohuing.renderer;

import com.imohuing.editor.TableDefaultEditor;
import com.imohuing.frame.IpSettingFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @Author Yangws
 * @Date 2022-11-09 13:03
 * @Description
 * @Version 1.0.0
 **/
public class TableDefaultRenderer extends DefaultTableCellRenderer {

    private JTextField textField;

    public TableDefaultRenderer(){
        this.textField = new JTextField();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.textField.setText((String) value);
        this.textField.setForeground(Color.black);
        this.textField.setBorder(new EmptyBorder(1,1,1,1));
        this.textField.setBackground(new Color(0xF2F2F2));

        TableDefaultEditor tableDefaultEditor = (TableDefaultEditor) table.getCellEditor(row,column);

        if(row%2 == 0){
            this.textField.setBackground(new Color(0xAAAAAA));
        }

        /**
         * 当当前行处于选中状态的时候设备复选框的背景颜色
         */
        boolean isFull = (boolean)table.getValueAt(row,IpSettingFrame.checkBoxColumn);
        if(isFull){
            this.textField.setBackground(new Color(0x81D3F8));

            String userInputValue = tableDefaultEditor.getUserInputValue();
            if(null != userInputValue && !userInputValue.equals(value)){
                if(column == 1 && "".equals(userInputValue)){
                    this.textField.setText("名称不能为空");
                    this.textField.setForeground(Color.red);
                }else if(column == 2 && "".equals(userInputValue)){
                    this.textField.setText("适配器不能为空");
                    this.textField.setForeground(Color.red);
                }else if(column == 3 && "".equals(userInputValue)){
                    this.textField.setText("ip地址不能为空");
                    this.textField.setForeground(Color.red);
                }else if(column == 4 && "".equals(userInputValue)){
                    this.textField.setText("子网掩码不能为空");
                    this.textField.setForeground(Color.red);
                }else if(column == 5 && "".equals(userInputValue)){
                    this.textField.setText("网关不能为空");
                    this.textField.setForeground(Color.red);
                }else if(column == 6 && "".equals(userInputValue)){
                    this.textField.setText("DNS不能为空");
                    this.textField.setForeground(Color.red);
                }else{
                    this.textField.setText(userInputValue);
                }
            }
        }

        /**
         * 如果当前单元行处于编辑状态那么把当前行的边框颜色置成红色
         */
        boolean isEditing = (boolean)table.getValueAt(row, IpSettingFrame.operationColumn);
        if(isEditing){
            this.textField.setBackground(Color.WHITE);
            this.textField.setBorder(new MatteBorder(1,1,1,1,Color.red));
        }

        return textField;
    }

    @Override
    public String getText() {
        if(null != this.textField){
            return this.textField.getText();
        }
        return super.getText();
    }

    @Override
    public void setText(String text) {
        if(null != this.textField){
            this.textField.setText(text);
        }else{
            super.setText(text);
        }
    }

    @Override
    public void setForeground(Color c) {
        if(null != this.textField){
            this.textField.setForeground(c);
        }else{
            super.setForeground(c);
        }
    }
}
