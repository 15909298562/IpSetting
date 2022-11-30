package com.imohuing.renderer;

import com.imohuing.frame.IpSettingFrame;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @Author Yangws
 * @Date 2022-11-09 13:03
 * @Description
 * @Version 1.0.0
 **/
public class OperationRenderer implements TableCellRenderer {

    private JPanel operationPanel;
    private JButton editButton;
    private JButton deleteButton;
    private JButton saveButton;

    public OperationRenderer(){
        this.operationPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        this.operationPanel.setLayout(flowLayout);

        this.editButton = new JButton("编辑");
        this.deleteButton = new JButton("删除");
        this.saveButton = new JButton("保存");

        this.operationPanel.add(this.editButton);
        this.operationPanel.add(this.deleteButton);
        this.operationPanel.add(this.saveButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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

        boolean isEditing = (boolean)table.getValueAt(row, IpSettingFrame.operationColumn);
        if(isEditing){
            this.editButton.setEnabled(false);
            this.deleteButton.setVisible(false);
            this.saveButton.setVisible(true);
        }

        return this.operationPanel;
    }
}
