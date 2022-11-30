package com.imohuing.editor;

import com.imohuing.frame.IpSettingFrame;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

/**
 * @Author Yangws
 * @Date 2022-11-13 21:01
 * @Description
 * @Version 1.0.0
 **/
public class TableDefaultEditor extends DefaultCellEditor {

    private JTextField textField;
    /**
     * 记录用户在输入框中输入的值
     */
    private String userInputValue;

    public TableDefaultEditor() {
        super(new JTextField());
        this.clickCountToStart = 1;
        this.textField = new JTextField();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.textField.setText((String)value);

        /**
         * 如果当前行是处于编辑状态那么当前行的边框颜色置成红色
         */
        boolean isEditing = (boolean)table.getValueAt(row,IpSettingFrame.operationColumn);
        if(isEditing){
            this.textField.setBackground(Color.WHITE);
            this.textField.setBorder(new MatteBorder(1,1,1,1,Color.red));
        }

        /**
         * 如果用户把输入框中的内容改变了
         */
        TableDefaultEditor tableDefaultEditor = (TableDefaultEditor) table.getCellEditor(row,column);
        String userInputValue = tableDefaultEditor.getUserInputValue();
        if(null != userInputValue && !userInputValue.equals(value)){
            this.textField.setText(userInputValue);
        }

        return this.textField;
    }

    @Override
    public Object getCellEditorValue() {
        int editingColumn = IpSettingFrame.historyTable.getEditingColumn();
        int editingRow = IpSettingFrame.historyTable.getEditingRow();

        return IpSettingFrame.historyTable.getValueAt(editingRow,editingColumn);
    }

    @Override
    public boolean stopCellEditing() {
        this.userInputValue = this.textField.getText();
        return super.stopCellEditing();
    }

    /**
     * 拿到用户在输入框中输入的值
     * @return
     */
    public String getUserInputValue(){
        return this.userInputValue;
    }

    /**
     * 将用户输入的信息重置成初始状态
     * @return
     */
    public String resetUserInputValue(){
        this.userInputValue = null;
        return this.userInputValue;
    }

}
