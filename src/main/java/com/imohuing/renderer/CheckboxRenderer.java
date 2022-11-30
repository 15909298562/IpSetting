package com.imohuing.renderer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @Author Yangws
 * @Date 2022-11-09 13:03
 * @Description
 * @Version 1.0.0
 **/
public class CheckboxRenderer implements TableCellRenderer {

    private JPanel checkboxPanel;
    private JCheckBox checkBox;

    public CheckboxRenderer(){
        this.checkboxPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        this.checkboxPanel.setLayout(flowLayout);

        this.checkBox = new JCheckBox();
        this.checkboxPanel.add(checkBox);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.checkBox.setSelected(false);
        this.checkboxPanel.setBackground(new Color(0xF2F2F2));
        this.checkBox.setBackground(new Color(0xF2F2F2));

        if(row%2 == 0){
            this.checkboxPanel.setBackground(new Color(0xAAAAAA));
            this.checkBox.setBackground(new Color(0xAAAAAA));
        }

        if((boolean)value){
            this.checkboxPanel.setBackground(new Color(0x81D3F8));
            this.checkBox.setBackground(new Color(0x81D3F8));
            this.checkBox.setSelected(true);
        }

        return this.checkboxPanel;
    }
}
