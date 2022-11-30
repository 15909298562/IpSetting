package com.imohuing.frame;

import com.imohuing.datasource.DataSourceHelper;
import com.imohuing.datasource.module.SettingHistory;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Yangws
 * @Date 2022-11-09 00:06
 * @Description
 * @Version 1.0.0
 **/
public class HistoryModel extends AbstractTableModel {

    private String[] columnNames = {"填充","名称","网络适配器","IP地址","子网掩码","网关地址","DNS服务器","操作"};

    private List<Object[]> data = new ArrayList<>();

    @Override
    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data.get(rowIndex)[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == getColumnCount()-1 || (boolean) IpSettingFrame.historyTable.getValueAt(rowIndex,7) == true ? true : false;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        this.data.get(rowIndex)[columnIndex] = aValue;
        super.fireTableRowsUpdated(rowIndex,rowIndex);
    }

    /**
     * 拿到选中行的主键
     * @param selectedRow
     * @return
     */
    public String getSelectedRowId(int selectedRow){
        for(int i=0;i<getRowCount();i++){
            if(i == selectedRow){
                return (String)this.data.get(i)[8];
            }
        }
        return "";
    }

    /**
     * 重新加载数据
     */
    public void reloadData(){
        this.data.removeAll(this.data);
        List<SettingHistory> settingHistoryList = DataSourceHelper.select();
        settingHistoryList.forEach(t->{
            this.data.add(new Object[]{false,t.getName(),t.getAdapter(),t.getIp(),t.getMask(),t.getGateway(),t.getDns(),false,t.getId()});
        });
    }

    /**
     * 删除行
     * @param row
     */
    public void deleteRow(int row){
        this.data.remove(row);
    }

    /**
     * 查询该历史记录是否已经存在
     * @param history
     * @return
     */
    public boolean selectIsExists(SettingHistory history){
        for(int i=0;i<getRowCount();i++){
            String name = (String)this.data.get(i)[1];
            String adapter = (String)this.data.get(i)[2];
            String ip = (String)this.data.get(i)[3];
            String mask = (String)this.data.get(i)[4];
            String gateway = (String)this.data.get(i)[5];
            String dns = (String)this.data.get(i)[6];

            String hName = history.getName();
            String hAdapter = history.getAdapter();
            String hIp = history.getIp();
            String hMask = history.getMask();
            String hGateway = history.getGateway();
            String hDns = history.getDns();
            if(name.equals(hName) && adapter.equals(hAdapter) && ip.equals(hIp) && mask.equals(hMask) && gateway.equals(hGateway) && dns.equals(hDns)){
                return true;
            }
        }
        return false;
    }

}
