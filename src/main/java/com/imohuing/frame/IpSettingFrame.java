package com.imohuing.frame;

import com.imohuing.IpSetting;
import com.imohuing.editor.OperationEditor;
import com.imohuing.editor.TableDefaultEditor;
import com.imohuing.listener.IpInfoFocusListener;
import com.imohuing.listener.IpSettingFrameListener;
import com.imohuing.listener.SettingButtonActionListener;
import com.imohuing.listener.TableMouseListener;
import com.imohuing.renderer.CheckboxRenderer;
import com.imohuing.renderer.OperationRenderer;
import com.imohuing.renderer.TableDefaultRenderer;
import com.imohuing.util.BatUtil;
import com.imohuing.util.IpUtil;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

public class IpSettingFrame extends JFrame {

    private static IpSettingFrame frame;

    public static IpSettingFrame getInstance() throws MalformedURLException {
        if(frame == null){
            frame = new IpSettingFrame();
        }
        return frame;
    }

    /**
     * 窗口的大小，宽930高640
     */
    public static final int FRAME_WIDTH = 930;
    public static final int Frame_HEIGHT = 640;

    /**
     * 窗口北区的大小，宽930高60
     */
    public static final int NORTH_HEIGHT = 60;

    /**
     * 主要按钮的宽和高
     */
    public static final int PRIMARY_BUTTON_WIDTH = 116;
    public static final int PRIMARY_BUTTON_HEIGHT = 30;

    /**
     * 默认文本行高度为25
     */
    public static final int DEFAULT_TEXT_FIELD_HEIGHT = 25;

    /**
     * 默认的组件之间的间隔为10
     */
    public static final int DEFAULT_PANEL_INTERVAL = 10;

    /**
     * ip信息面板中的文本输入框的宽度为350
     */
    public static final int IP_INFO_TEXT_FIELD_WIDTH = 350;

    /**
     * ip信息面板中的文本必填提示的宽度为235
     */
    public static final int IP_REQUIRED_PANEL_WIDTH = 235;

    /**
     * 字段名称面板宽度为250
     */
    public static final int FIELD_PANEL_WIDTH = 250;

    /**
     * 字段名称对齐组件宽度为100
     */
    public static final int FIELD_ALIGN_PANEL_WIDTH = 100;

    /**
     * 历史记录表格宽度为895、高度为210
     */
    public static final int HISTORY_TABLE_WIDTH = 895;
    public static final int HISTORY_TABLE_HEIGHT = 210;

    /**
     * 默认的面板高度为30
     */
    public static final int DEFAULT_PANEL_HEIGHT = 30;

    /**
     * 默认主按钮的文字大小为16
     */
    public static final int DEFAULT_PRIMARY_BUTTON_FONT_SIZE = 16;

    /**
     * 名称输入框、必填提示
     */
    public static JTextField nameTextField = new JTextField();
    public static JLabel nameRequiredLabel = new JLabel("名称不能为空");

    /**
     * 网络适配器输入框、必填提示
     */
    public static JTextField adapterTextField = new JTextField();
    public static JLabel adapterRequiredLabel = new JLabel("网络适配器不能为空");

    /**
     * IP地址输入框、必填提示
     */
    public static JTextField ipTextField = new JTextField();
    public static JLabel ipRequiredLabel = new JLabel("IP地址不能为空");

    /**
     * 网关输入框、必填提示
     */
    public static JTextField gatewayTextField = new JTextField();
    public static JLabel gatewayRequiredLabel = new JLabel("网关地址不能为空");

    /**
     * DNS输入框、必填提示
     */
    public static JTextField dnsTextField = new JTextField();
    public static JLabel dnsRequiredLabel = new JLabel("DNS服务器不能为空");

    /**
     * 历史记录表格
     */
    public static JTable historyTable = new JTable();

    /**
     * 执行中加载动画
     */
    public static JLabel loadingLabel = new JLabel();

    /**
     * 执行成功、失败的消息提示
     */
    public static JLabel messageLabel = new JLabel();

    /**
     * 子网掩码输入框、必填提示
     */
    public static JTextField maskTextField = new JTextField();
    public static JLabel maskRequiredLabel = new JLabel("子网掩码不能为空");

    /**
     * 操作列所在的index
     */
    public static int operationColumn = 7;

    /**
     * 复选框列所在的index
     */
    public static int checkBoxColumn = 0;

    /**
     * 当前ip地址
     */
    public static JLabel currentIpLabel = new JLabel();

    /**
     * 动态设置ip、获取当前正在使用的网络适配器和设置静态IP地址的bat文件路径
     */
    public static String DynamicIpUrl = "/bat/DynamicIp.bat";
    public static String GetAdapterUrl = "/bat/GetAdapter.bat";
    public static String StaticIpUrl = "/bat/StaticIp.bat";

    /**
     * IpSetting图标和加载中动画文件
     */
    public static String IpSettingIconUrl = "/static/IpSettingIcon.png";
    public static String LoadingUrl = "/static/Loading.gif";

    public IpSettingFrame() throws MalformedURLException {
        /**
         * 设置标题
         */
        super("IpSetting");

        /**
         * 构建北区面板
         */
        buildNorthPanel();

        /**
         * 构建中间面板
         */
        buildCenterPanel();

        /**
         * 构建南区面板
         */
        buildSourcePanel();

        /**
         * 设置图标、位置、大小以及显示
         */
        this.setIconImage(new ImageIcon(IpSetting.class.getResource(IpSettingFrame.IpSettingIconUrl)).getImage());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.setLocation((int)(width-FRAME_WIDTH)/2, (int)(height-Frame_HEIGHT)/2);
        this.setSize(FRAME_WIDTH, Frame_HEIGHT);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addWindowListener(new IpSettingFrameListener());
    }

    /**
     * 构建IP信息组件面板
     * @param centerPanel 中间区域面板
     */
    private void buildIpInfoPanel(JPanel centerPanel){
        /**
         * IP信息组件面板
         */
        JPanel ipInfoPanel = new JPanel();
        ipInfoPanel.setLayout(new BoxLayout(ipInfoPanel,BoxLayout.Y_AXIS));
        centerPanel.add(ipInfoPanel);

        /**
         * 名称组件
         */
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BorderLayout());
        namePanel.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        ipInfoPanel.add(namePanel);

        /**
         * 名称字段组件
         */
        JPanel nameLabelPanel = new JPanel();
        FlowLayout nameLabelPanelLayout = new FlowLayout(FlowLayout.RIGHT);
        nameLabelPanel.setLayout(nameLabelPanelLayout);
        nameLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        namePanel.add(nameLabelPanel,BorderLayout.WEST);

        /**
         * 名称字段对齐组件
         */
        JPanel nameLabelAlignPanel = new JPanel();
        FlowLayout nameLabelAlignPanelLayout = new FlowLayout(FlowLayout.LEFT);
        nameLabelAlignPanel.setLayout(nameLabelAlignPanelLayout);
        nameLabelAlignPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_ALIGN_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        nameLabelPanel.add(nameLabelAlignPanel,BorderLayout.WEST);

        /**
         * 名称字段
         */
        JLabel nameLabel = new JLabel("名称：");
        nameLabelAlignPanel.add(nameLabel);

        /**
         * 名称输入框组件
         */
        JPanel nameTextFieldPanel = new JPanel();
        namePanel.add(nameTextFieldPanel,BorderLayout.CENTER);

        /**
         * 名称输入框
         */
        nameTextField.setPreferredSize(new Dimension(IpSettingFrame.IP_INFO_TEXT_FIELD_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        nameTextFieldPanel.add(nameTextField,BorderLayout.CENTER);
        nameTextField.addFocusListener(new IpInfoFocusListener());

        /**
         * 名称必填提示组件
         */
        JPanel nameRequiredLabelPanel = new JPanel();
        FlowLayout nameRequiredLabelPanelLayout = new FlowLayout(FlowLayout.LEFT);
        nameRequiredLabelPanelLayout.setVgap(7);
        nameRequiredLabelPanel.setLayout(nameRequiredLabelPanelLayout);
        nameRequiredLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.IP_REQUIRED_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        namePanel.add(nameRequiredLabelPanel,BorderLayout.EAST);

        /**
         * 名称必填提示
         */
        nameRequiredLabel.setForeground(Color.red);
        nameRequiredLabel.setVisible(false);
        nameRequiredLabelPanel.add(nameRequiredLabel);

        /**
         * 适配器组件
         */
        JPanel adapterPanel = new JPanel();
        adapterPanel.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        adapterPanel.setLayout(new BorderLayout());
        ipInfoPanel.add(adapterPanel);

        /**
         * 适配器名称组件
         */
        JPanel adapterLabelPanel = new JPanel();
        FlowLayout adapterLabelPanelLayout = new FlowLayout(FlowLayout.RIGHT);
        adapterLabelPanel.setLayout(adapterLabelPanelLayout);
        adapterLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        adapterPanel.add(adapterLabelPanel,BorderLayout.WEST);

        /**
         * 适配器对齐组件
         */
        JPanel adapterAlignPanel = new JPanel();
        FlowLayout adapterAlignPanelLayout = new FlowLayout(FlowLayout.LEFT);
        adapterAlignPanel.setLayout(adapterAlignPanelLayout);
        adapterAlignPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_ALIGN_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        adapterLabelPanel.add(adapterAlignPanel,BorderLayout.WEST);

        /**
         * 网络适配器字段
         */
        JLabel adapterLabel = new JLabel("网络适配器：");
        adapterAlignPanel.add(adapterLabel,BorderLayout.WEST);

        /**
         * 网络适配器输入框组件
         */
        JPanel adapterTextFieldPanel = new JPanel();
        adapterPanel.add(adapterTextFieldPanel,BorderLayout.CENTER);

        /**
         * 网络适配器输入框
         */
        adapterTextField.setPreferredSize(new Dimension(IpSettingFrame.IP_INFO_TEXT_FIELD_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        adapterTextFieldPanel.add(adapterTextField,BorderLayout.CENTER);
        adapterTextField.addFocusListener(new IpInfoFocusListener());

        /**
         * 网络适配器必填提示组件
         */
        JPanel adapterRequiredLabelPanel = new JPanel();
        FlowLayout adapterRequiredLabelPanelLayout = new FlowLayout(FlowLayout.LEFT);
        adapterRequiredLabelPanelLayout.setVgap(7);
        adapterRequiredLabelPanel.setLayout(adapterRequiredLabelPanelLayout);
        adapterRequiredLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.IP_REQUIRED_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        adapterPanel.add(adapterRequiredLabelPanel,BorderLayout.EAST);

        /**
         * 网络适配器必填提示
         */
        adapterRequiredLabel.setForeground(Color.red);
        adapterRequiredLabel.setVisible(false);
        adapterRequiredLabelPanel.add(adapterRequiredLabel,BorderLayout.EAST);

        /**
         * IP地址组件
         */
        JPanel ipPanel = new JPanel();
        ipPanel.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        ipPanel.setLayout(new BorderLayout());
        ipInfoPanel.add(ipPanel);

        /**
         * IP地址名称组件
         */
        JPanel ipLabelPanel = new JPanel();
        FlowLayout ipLabelPanelLayout = new FlowLayout(FlowLayout.RIGHT);
        ipLabelPanel.setLayout(ipLabelPanelLayout);
        ipLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        ipPanel.add(ipLabelPanel,BorderLayout.WEST);

        /**
         * IP地址对齐组件
         */
        JPanel ipAlignPanel = new JPanel();
        FlowLayout ipAlignPanelLayout = new FlowLayout(FlowLayout.LEFT);
        ipAlignPanel.setLayout(ipAlignPanelLayout);
        ipAlignPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_ALIGN_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        ipLabelPanel.add(ipAlignPanel,BorderLayout.WEST);

        /**
         * IP地址字段
         */
        JLabel ipLabel = new JLabel("IP地址：");
        ipAlignPanel.add(ipLabel,BorderLayout.WEST);

        /**
         * IP地址输入框组件
         */
        JPanel ipTextFieldPanel = new JPanel();
        ipPanel.add(ipTextFieldPanel,BorderLayout.CENTER);

        /**
         * IP地址输入框
         */
        ipTextField.setPreferredSize(new Dimension(IpSettingFrame.IP_INFO_TEXT_FIELD_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        ipTextFieldPanel.add(ipTextField,BorderLayout.CENTER);
        ipTextField.addFocusListener(new IpInfoFocusListener());

        /**
         * IP地址必填提示组件
         */
        JPanel ipRequiredLabelPanel = new JPanel();
        FlowLayout ipRequiredLabelPanelLayout = new FlowLayout(FlowLayout.LEFT);
        ipRequiredLabelPanelLayout.setVgap(7);
        ipRequiredLabelPanel.setLayout(ipRequiredLabelPanelLayout);
        ipRequiredLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.IP_REQUIRED_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        ipPanel.add(ipRequiredLabelPanel,BorderLayout.EAST);

        /**
         * IP地址必填提示
         */
        ipRequiredLabel.setForeground(Color.red);
        ipRequiredLabel.setVisible(false);
        ipRequiredLabelPanel.add(ipRequiredLabel,BorderLayout.EAST);

        /**
         * 子网掩码组件
         */
        JPanel maskPanel = new JPanel();
        maskPanel.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        maskPanel.setLayout(new BorderLayout());
        ipInfoPanel.add(maskPanel);

        /**
         * 子网掩码名称组件
         */
        JPanel maskLabelPanel = new JPanel();
        FlowLayout maskLabelPanelLayout = new FlowLayout(FlowLayout.RIGHT);
        maskLabelPanel.setLayout(maskLabelPanelLayout);
        maskLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        maskPanel.add(maskLabelPanel,BorderLayout.WEST);

        /**
         * 子网掩码对齐组件
         */
        JPanel maskAlignPanel = new JPanel();
        FlowLayout maskAlignPanelLayout = new FlowLayout(FlowLayout.LEFT);
        maskAlignPanel.setLayout(maskAlignPanelLayout);
        maskAlignPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_ALIGN_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        maskLabelPanel.add(maskAlignPanel,BorderLayout.WEST);

        /**
         * 子网掩码字段
         */
        JLabel maskLabel = new JLabel("子网掩码：");
        maskAlignPanel.add(maskLabel,BorderLayout.WEST);

        /**
         * 子网掩码输入框组件
         */
        JPanel maskTextFieldPanel = new JPanel();
        maskPanel.add(maskTextFieldPanel,BorderLayout.CENTER);

        /**
         * 子网掩码输入框
         */
        maskTextField.setPreferredSize(new Dimension(IpSettingFrame.IP_INFO_TEXT_FIELD_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        maskTextFieldPanel.add(maskTextField,BorderLayout.CENTER);
        maskTextField.addFocusListener(new IpInfoFocusListener());

        /**
         * 子网掩码必填提示组件
         */
        JPanel maskRequiredLabelPanel = new JPanel();
        FlowLayout maskRequiredLabelPanelLayout = new FlowLayout(FlowLayout.LEFT);
        ipRequiredLabelPanelLayout.setVgap(7);
        maskRequiredLabelPanel.setLayout(maskRequiredLabelPanelLayout);
        maskRequiredLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.IP_REQUIRED_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        maskPanel.add(maskRequiredLabelPanel,BorderLayout.EAST);

        /**
         * 子网掩码必填提示
         */
        maskRequiredLabel.setForeground(Color.red);
        maskRequiredLabel.setVisible(false);
        maskRequiredLabelPanel.add(maskRequiredLabel,BorderLayout.EAST);

        /**
         * 网关组件
         */
        JPanel gatewayPanel = new JPanel();
        gatewayPanel.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        gatewayPanel.setLayout(new BorderLayout());
        ipInfoPanel.add(gatewayPanel);

        /**
         * 网关名称组件
         */
        JPanel gatewayLabelPanel = new JPanel();
        FlowLayout gatewayLabelPanelLayout = new FlowLayout(FlowLayout.RIGHT);
        gatewayLabelPanel.setLayout(gatewayLabelPanelLayout);
        gatewayLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        gatewayPanel.add(gatewayLabelPanel,BorderLayout.WEST);

        /**
         * 网关对齐组件
         */
        JPanel gatewayAlignPanel = new JPanel();
        FlowLayout gatewayAlignPanelLayout = new FlowLayout(FlowLayout.LEFT);
        gatewayAlignPanel.setLayout(gatewayAlignPanelLayout);
        gatewayAlignPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_ALIGN_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        gatewayLabelPanel.add(gatewayAlignPanel,BorderLayout.WEST);

        /**
         * 网关地址字段
         */
        JLabel gatewayLabel = new JLabel("网关地址：");
        gatewayAlignPanel.add(gatewayLabel,BorderLayout.WEST);

        /**
         * 网关地址输入框组件
         */
        JPanel gatewayTextFieldPanel = new JPanel();
        gatewayPanel.add(gatewayTextFieldPanel,BorderLayout.CENTER);

        /**
         * 网关地址输入框
         */
        gatewayTextField.setPreferredSize(new Dimension(IpSettingFrame.IP_INFO_TEXT_FIELD_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        gatewayTextFieldPanel.add(gatewayTextField,BorderLayout.CENTER);
        gatewayTextField.addFocusListener(new IpInfoFocusListener());

        /**
         * 网关地址必填提示组件
         */
        JPanel gatewayRequiredLabelPanel = new JPanel();
        FlowLayout gatewayRequiredLabelPanelLayout = new FlowLayout(FlowLayout.LEFT);
        gatewayRequiredLabelPanelLayout.setVgap(7);
        gatewayRequiredLabelPanel.setLayout(gatewayRequiredLabelPanelLayout);
        gatewayRequiredLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.IP_REQUIRED_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        gatewayPanel.add(gatewayRequiredLabelPanel,BorderLayout.EAST);

        /**
         * 网关地址必填提示
         */
        gatewayRequiredLabel.setForeground(Color.red);
        gatewayRequiredLabel.setVisible(false);
        gatewayRequiredLabelPanel.add(gatewayRequiredLabel,BorderLayout.EAST);

        /**
         * DNS组件
         */
        JPanel dnsPanel = new JPanel();
        dnsPanel.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        dnsPanel.setLayout(new BorderLayout());
        ipInfoPanel.add(dnsPanel);

        /**
         * DNS名称组件
         */
        JPanel dnsLabelPanel = new JPanel();
        FlowLayout dnsLabelPanelLayout = new FlowLayout(FlowLayout.RIGHT);
        dnsLabelPanel.setLayout(dnsLabelPanelLayout);
        dnsLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        dnsPanel.add(dnsLabelPanel,BorderLayout.WEST);

        /**
         * DNS对齐组件
         */
        JPanel dnsAlignPanel = new JPanel();
        FlowLayout dnsAlignPanelLayout = new FlowLayout(FlowLayout.LEFT);
        dnsAlignPanel.setLayout(dnsAlignPanelLayout);
        dnsAlignPanel.setPreferredSize(new Dimension(IpSettingFrame.FIELD_ALIGN_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        dnsLabelPanel.add(dnsAlignPanel,BorderLayout.WEST);

        /**
         * DNS地址字段
         */
        JLabel dnsLabel = new JLabel("DNS服务器地址：");
        dnsAlignPanel.add(dnsLabel,BorderLayout.WEST);

        /**
         * DNS地址输入框组件
         */
        JPanel dnsTextFieldPanel = new JPanel();
        dnsPanel.add(dnsTextFieldPanel,BorderLayout.CENTER);

        /**
         * DNS地址输入框
         */
        dnsTextField.setPreferredSize(new Dimension(IpSettingFrame.IP_INFO_TEXT_FIELD_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        dnsTextFieldPanel.add(dnsTextField,BorderLayout.CENTER);
        dnsTextField.addFocusListener(new IpInfoFocusListener());

        /**
         * DNS地址必填提示组件
         */
        JPanel dnsRequiredLabelPanel = new JPanel();
        FlowLayout dnsRequiredLabelPanelLayout = new FlowLayout(FlowLayout.LEFT);
        dnsRequiredLabelPanelLayout.setVgap(7);
        dnsRequiredLabelPanel.setLayout(dnsRequiredLabelPanelLayout);
        dnsRequiredLabelPanel.setPreferredSize(new Dimension(IpSettingFrame.IP_REQUIRED_PANEL_WIDTH,IpSettingFrame.DEFAULT_TEXT_FIELD_HEIGHT));
        dnsPanel.add(dnsRequiredLabelPanel,BorderLayout.EAST);

        /**
         * DNS地址必填提示
         */
        dnsRequiredLabel.setForeground(Color.red);
        dnsRequiredLabel.setVisible(false);
        dnsRequiredLabelPanel.add(dnsRequiredLabel,BorderLayout.EAST);
    }

    /**
     * 构建手动设置按钮面板
     * @param centerPanel 中间区域面板
     */
    private void buildManualPanel(JPanel centerPanel){
        /**
         * 手动设置按钮面板
         */
        JPanel manualPanel = new JPanel();
        centerPanel.add(manualPanel);

        /**
         * 手动设置按钮
         */
        JButton manualButton = new JButton("手动设置");
        manualButton.setPreferredSize(new Dimension(PRIMARY_BUTTON_WIDTH,PRIMARY_BUTTON_HEIGHT));
        manualButton.setBackground(new Color(149, 242, 4));
        Font manualButtonFont = new Font("宋体",Font.BOLD,IpSettingFrame.DEFAULT_PRIMARY_BUTTON_FONT_SIZE);
        manualButton.setFont(manualButtonFont);
        manualButton.setForeground(Color.WHITE);
        manualButton.setActionCommand("MANUAL");
        manualButton.setFocusPainted(false);
        manualButton.addActionListener(new SettingButtonActionListener());
        manualPanel.add(manualButton);
    }

    /**
     * 构建历史记录标题栏面板
     * @param centerPanel 中间区域面板
     */
    private void buildHistoryLabelPanel(JPanel centerPanel){
        /**
         * 历史记录标题栏面板
         */
        JPanel historyLabelPanel = new JPanel();
        FlowLayout historyLabelPanelFlowLayout = new FlowLayout();
        historyLabelPanelFlowLayout.setVgap(0);
        historyLabelPanel.setLayout(historyLabelPanelFlowLayout);
        centerPanel.add(historyLabelPanel);

        /**
         * 历史记录布局面板
         */
        JPanel historyLabelLayoutPanel = new JPanel();
        historyLabelLayoutPanel.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.DEFAULT_PANEL_HEIGHT));
        historyLabelLayoutPanel.setBorder(new MatteBorder(1,1,1,1,Color.BLACK));
        historyLabelPanel.add(historyLabelLayoutPanel);

        /**
         * 历史记录标题栏
         */
        JLabel historyLabel = new JLabel("历史记录");
        historyLabel.setFont(new Font("宋体",Font.PLAIN,18));
        historyLabel.setOpaque(true);
        historyLabelLayoutPanel.add(historyLabel);
    }

    /**
     * 构建历史记录表格布局面板
     * @param centerPanel 中间区域面板
     */
    private void buildHistoryScrollLayoutPanel(JPanel centerPanel) throws MalformedURLException {
        /**
         * 历史记录表格布局面板
         */
        JPanel historyScrollLayoutPanel = new JPanel();
        FlowLayout historyScrollLayoutPanelFlowLayout = new FlowLayout();
        historyScrollLayoutPanelFlowLayout.setVgap(0);
        historyScrollLayoutPanel.setLayout(historyScrollLayoutPanelFlowLayout);
        centerPanel.add(historyScrollLayoutPanel);

        /**
         * 历史记录表格
         */
        HistoryModel historyModel = new HistoryModel();
        historyModel.reloadData();
        historyTable.setModel(historyModel);
        historyTable.setRowHeight(IpSettingFrame.DEFAULT_PANEL_HEIGHT);
        historyTable.setFillsViewportHeight(true);
        historyTable.setSelectionBackground(new Color(0x81D3F8));
        TableColumnModel tableColumnModel = historyTable.getColumnModel();
        historyTable.getTableHeader().setReorderingAllowed(false);
        TableColumn tableColumn0 = tableColumnModel.getColumn(0);
        tableColumn0.setPreferredWidth(55);
        tableColumn0.setCellRenderer(new CheckboxRenderer());
        TableColumn tableColumn1 = tableColumnModel.getColumn(1);
        tableColumn1.setPreferredWidth(110);
        tableColumn1.setCellRenderer(new TableDefaultRenderer());
        tableColumn1.setCellEditor(new TableDefaultEditor());
        TableColumn tableColumn2 = tableColumnModel.getColumn(2);
        tableColumn2.setPreferredWidth(110);
        tableColumn2.setCellRenderer(new TableDefaultRenderer());
        tableColumn2.setCellEditor(new TableDefaultEditor());
        TableColumn tableColumn3 = tableColumnModel.getColumn(3);
        tableColumn3.setPreferredWidth(130);
        tableColumn3.setCellRenderer(new TableDefaultRenderer());
        tableColumn3.setCellEditor(new TableDefaultEditor());
        TableColumn tableColumn4 = tableColumnModel.getColumn(4);
        tableColumn4.setPreferredWidth(130);
        tableColumn4.setCellRenderer(new TableDefaultRenderer());
        tableColumn4.setCellEditor(new TableDefaultEditor());
        TableColumn tableColumn5 = tableColumnModel.getColumn(5);
        tableColumn5.setPreferredWidth(130);
        tableColumn5.setCellRenderer(new TableDefaultRenderer());
        tableColumn5.setCellEditor(new TableDefaultEditor());
        TableColumn tableColumn6 = tableColumnModel.getColumn(6);
        tableColumn6.setPreferredWidth(130);
        tableColumn6.setCellRenderer(new TableDefaultRenderer());
        tableColumn6.setCellEditor(new TableDefaultEditor());
        TableColumn tableColumn7 = tableColumnModel.getColumn(7);
        tableColumn7.setPreferredWidth(86);
        tableColumn7.setCellRenderer(new OperationRenderer());
        tableColumn7.setCellEditor(new OperationEditor());

        JTableHeader tableHeader = historyTable.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.DEFAULT_PANEL_HEIGHT));
        tableHeader.setBackground(new Color(0xAAAAAA));
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyTable.addMouseListener(new TableMouseListener());
        int rowCount = historyTable.getRowCount();
        if(rowCount > 0){
            historyTable.setRowSelectionInterval(0,0);
            historyTable.setValueAt(true,0,0);

            nameTextField.setText((String)historyTable.getValueAt(0,1));
            adapterTextField.setText((String)historyTable.getValueAt(0,2));
            ipTextField.setText((String)historyTable.getValueAt(0,3));
            maskTextField.setText((String)historyTable.getValueAt(0,4));
            gatewayTextField.setText((String)historyTable.getValueAt(0,5));
            dnsTextField.setText((String)historyTable.getValueAt(0,6));
        }else{
            IpSettingFrame.nameTextField.setText("给这个ip地址取一个方便记忆的名称");
            IpSettingFrame.nameTextField.setForeground(new Color(0xD7D7D7));
            String currentAdapter = BatUtil.getCurrentAdapter();
            IpSettingFrame.adapterTextField.setText(currentAdapter);
            IpSettingFrame.adapterTextField.setForeground(Color.black);
            IpSettingFrame.ipTextField.setText("例如：192.168.110.243");
            IpSettingFrame.ipTextField.setForeground(new Color(0xD7D7D7));
            IpSettingFrame.gatewayTextField.setText("例如：192.168.110.1");
            IpSettingFrame.gatewayTextField.setForeground(new Color(0xD7D7D7));
            IpSettingFrame.dnsTextField.setText("例如：192.168.110.1");
            IpSettingFrame.dnsTextField.setForeground(new Color(0xD7D7D7));
            String mask = IpUtil.getNetMask();
            IpSettingFrame.maskTextField.setText(mask);
            IpSettingFrame.maskTextField.setForeground(Color.black);
        }

        /**
         * 历史记录表格面板
         */
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setPreferredSize(new Dimension(IpSettingFrame.HISTORY_TABLE_WIDTH,IpSettingFrame.HISTORY_TABLE_HEIGHT));
        historyScrollLayoutPanel.add(historyScrollPane);
    }

    /**
     * 构建消息面板
     * @param sourcePanel 南区面板
     */
    private void buildMessagePanel(JPanel sourcePanel){
        /**
         * 消息面板
         */
        JPanel messagePanel = new JPanel();
        messagePanel.setPreferredSize(new Dimension(400,IpSettingFrame.DEFAULT_PANEL_HEIGHT));
        messagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        sourcePanel.add(messagePanel,BorderLayout.WEST);

        /**
         * 加载中动画
         */
        loadingLabel.setVisible(false);
        loadingLabel.setIcon(new ImageIcon(this.getClass().getResource(IpSettingFrame.LoadingUrl)));
        messagePanel.add(loadingLabel);

        /**
         * 执行成功、执行失败消息
         */
        messageLabel.setVisible(false);
        messagePanel.add(messageLabel);
    }

    /**
     * 构建IP状态面板
     * @param sourcePanel 南区面板
     */
    private void buildIpStatusPanel(JPanel sourcePanel){
        /**
         * IP状态面板
         */
        JPanel ipStatusPanel = new JPanel();
        ipStatusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sourcePanel.add(ipStatusPanel,BorderLayout.CENTER);

        /**
         * 当前IP地址
         */
        currentIpLabel = new JLabel();
        String ipAddr = "127.0.0.1";
        try{
            ipAddr = Inet4Address.getLocalHost().getHostAddress();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        String statusText = "当前IP地址为：" + ipAddr;
        currentIpLabel.setText(statusText);
        currentIpLabel.setOpaque(true);
        ipStatusPanel.add(currentIpLabel);
    }

    /**
     * 构建自动分配面板
     * @param northPanel 北区面板
     */
    private void buildAutoPanel(JPanel northPanel){
        /**
         * 自动分配面板
         */
        JPanel autoPanel = new JPanel();
        autoPanel.setPreferredSize(new Dimension(IpSettingFrame.FRAME_WIDTH,NORTH_HEIGHT));
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setVgap(15);
        autoPanel.setLayout(flowLayout);
        northPanel.add(autoPanel);

        /**
         * 自动分配按钮
         */
        JButton autoButton = new JButton("自动分配");
        autoButton.setPreferredSize(new Dimension(PRIMARY_BUTTON_WIDTH,PRIMARY_BUTTON_HEIGHT));
        autoButton.setBackground(new Color(129,211,248));
        Font font = new Font("宋体",Font.BOLD,IpSettingFrame.DEFAULT_PRIMARY_BUTTON_FONT_SIZE);
        autoButton.setFont(font);
        autoButton.setForeground(Color.WHITE);
        autoButton.setActionCommand("AUTO");
        autoButton.addActionListener(new SettingButtonActionListener());
        autoButton.setFocusPainted(false);
        autoPanel.add(autoButton);
    }

    /**
     * 构建北区面板
     */
    private void buildNorthPanel(){
        /**
         * 窗口北区
         */
        JPanel northPanel = new JPanel();
        this.add(northPanel,BorderLayout.NORTH);

        /**
         * 自动分配面板
         */
        buildAutoPanel(northPanel);
    }

    /**
     * 构建中间面板
     */
    private void buildCenterPanel() throws MalformedURLException {
        /**
         * 窗口中间
         */
        JPanel centerPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(centerPanel,BoxLayout.Y_AXIS);
        centerPanel.setBorder(new MatteBorder(1,0,1,0,Color.BLACK));
        centerPanel.setLayout(boxLayout);
        this.add(centerPanel,BorderLayout.CENTER);

        /**
         * 间隙面板
         */
        JPanel clearancePanel = new JPanel();
        clearancePanel.setPreferredSize(new Dimension(IpSettingFrame.FRAME_WIDTH,IpSettingFrame.DEFAULT_PANEL_INTERVAL));
        centerPanel.add(clearancePanel);

        /**
         * 历史记录标题栏面板
         */
        buildHistoryLabelPanel(centerPanel);

        /**
         * 历史记录表格布局面板
         */
        buildHistoryScrollLayoutPanel(centerPanel);

        /**
         * IP信息组件面板
         */
        buildIpInfoPanel(centerPanel);

        /**
         * 手动设置按钮面板
         */
        buildManualPanel(centerPanel);
    }

    /**
     * 构建南区面板
     */
    private void buildSourcePanel(){
        /**
         * 窗口南区
         */
        JPanel sourcePanel = new JPanel();
        sourcePanel.setLayout(new BorderLayout());
        sourcePanel.setPreferredSize(new Dimension(IpSettingFrame.FRAME_WIDTH,IpSettingFrame.DEFAULT_PANEL_HEIGHT));
        this.add(sourcePanel,BorderLayout.SOUTH);

        /**
         * 消息面板
         */
        buildMessagePanel(sourcePanel);

        /**
         * IP状态面板
         */
        buildIpStatusPanel(sourcePanel);
    }

}