package com.imohuing.editor;

import com.imohuing.frame.IpSettingFrame;
import com.imohuing.listener.OperationActionListener;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * @Author Yangws
 * @Date 2022-11-12 14:51
 * @Description
 * @Version 1.0.0
 **/
public class OperationEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel operationPanel;
    private JButton editButton;
    private JButton deleteButton;
    private JButton saveButton;

    public OperationEditor(){
        this.operationPanel = new JPanel();
        this.editButton = new JButton("编辑");
        this.editButton.addActionListener(new OperationActionListener());
        this.deleteButton = new JButton("删除");
        this.deleteButton.addActionListener(new OperationActionListener());
        this.saveButton = new JButton("保存");
        this.saveButton.addActionListener(new OperationActionListener());

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        this.operationPanel.setLayout(flowLayout);

        this.operationPanel.add(editButton);
        this.operationPanel.add(deleteButton);
        this.operationPanel.add(saveButton);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.editButton.setBackground(new Color(22,155,213));
        this.editButton.setForeground(Color.WHITE);
        this.editButton.setPreferredSize(new Dimension(30,20));
        this.editButton.setFont(new Font("宋体",Font.PLAIN,11));
        this.editButton.setMargin(new Insets(0,0,0,0));
        this.editButton.setActionCommand("EDIT");
        this.editButton.setEnabled(true);

        this.deleteButton.setBackground(new Color(221,0,27));
        this.deleteButton.setForeground(Color.WHITE);
        this.deleteButton.setPreferredSize(new Dimension(30,20));
        this.deleteButton.setFont(new Font("宋体",Font.PLAIN,11));
        this.deleteButton.setMargin(new Insets(0,0,0,0));
        this.deleteButton.setActionCommand("DELETE");
        this.deleteButton.setVisible(true);

        this.saveButton.setVisible(false);
        this.saveButton.setBackground(new Color(4,250,29));
        this.saveButton.setForeground(Color.WHITE);
        this.saveButton.setFont(new Font("宋体",Font.PLAIN,11));
        this.saveButton.setMargin(new Insets(0,0,0,0));
        this.saveButton.setActionCommand("SAVE");
        this.saveButton.setVisible(false);

        this.operationPanel.setBackground(new Color(0xF2F2F2));

        if(row%2 == 0){
            this.operationPanel.setBackground(new Color(0xAAAAAA));
        }

        boolean isFull = (boolean)table.getValueAt(row,IpSettingFrame.checkBoxColumn);
        if(isFull){
            this.operationPanel.setBackground(new Color(0x81D3F8));
        }

        boolean isEditing = (boolean)table.getValueAt(row,IpSettingFrame.operationColumn);
        if(isEditing){
            this.editButton.setEnabled(false);
            this.deleteButton.setVisible(false);
            this.saveButton.setVisible(true);
        }

        return this.operationPanel;
    }

    @Override
    public Object getCellEditorValue() {
        int editingColumn = IpSettingFrame.historyTable.getEditingColumn();
        int editingRow = IpSettingFrame.historyTable.getEditingRow();
        return IpSettingFrame.historyTable.getValueAt(editingRow,editingColumn);
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            return ((MouseEvent)e).getClickCount() >= 1;
        }
        return true;
    }

    /**
     * 设置操作面板背景颜色
     * @param color 背景颜色
     */
    public void setOperationPanelBackground(Color color){
        if(null == this.operationPanel){
            int selectedRow = IpSettingFrame.historyTable.getSelectedRow();
            this.operationPanel = (JPanel) getTableCellEditorComponent(IpSettingFrame.historyTable, IpSettingFrame.historyTable.getValueAt(selectedRow,IpSettingFrame.operationColumn),true,selectedRow,IpSettingFrame.operationColumn);
        }
        this.operationPanel.setBackground(color);
    }

    /**
     * 设置保存按钮显隐
     * @param visible
     */
    public void setSaveButtonVisible(boolean visible){
        saveButton.setVisible(visible);
    }

    /**
     * 设置删除按钮显隐
     * @param visible
     */
    public void setdeleteButtonVisible(boolean visible){
        deleteButton.setVisible(visible);
    }

    /**
     * 设置编辑按钮启用/禁用
     * @param enabled
     */
    public void setEditButtonEnabled(boolean enabled){
        editButton.setEnabled(enabled);
    }

}
