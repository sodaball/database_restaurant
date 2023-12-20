package view;

import cn.hutool.db.Entity;
import service.GoodsService;
import service.OrderService;
import service.RestService;
import service.StuService;

import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import view.AddGoodsFrame;
import view.UpdateGoodsFrame;
import view.OrderFrame;

public class MainFrame extends JFrame {

    private String userType;

    private String username;

    private MainFrame mainFrame;
    private int restaurantId;
   
    private StuService stuService = new StuService();
    private RestService restService = new RestService();

    private GoodsService goodsService = new GoodsService(restService);

    private OrderService orderService = new OrderService(goodsService);

    // 声明表格和表格模型
    private JTable table;
    private DefaultTableModel tModel;

    public MainFrame() {
        //初始化
        setVisible(false);
        setResizable(false);
        setLayout(null);
        setBounds(300, 300, 600, 500);

        //初始化登录页
        new LoginFrame(this).setVisible(true);
        this.userType = "学生";


    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        // mainFrame.showMain("lisi", "餐厅");
    }

    public StuService getStuService() {
        return stuService;
    }

    public RestService getRestService() {
        return restService;
    }

    public void showMain(String username, String userType) {
        this.username = username;
        this.userType = userType;
        init();
        setVisible(true);
    }

    public void refreshGoodsList() {
        // 获取当前用户的餐厅 ID
        int restaurantId = restService.getId();
    
        // 显示刷新中的提示框
        // JOptionPane progressDialog = new JOptionPane("正在刷新商品列表，请稍候...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        JOptionPane.showMessageDialog(this, "正在刷新商品列表，请稍候...", "刷新中", JOptionPane.INFORMATION_MESSAGE);
    
        // 从服务中获取最新的商品数据
        String[] columnNames = {"商品名", "商品号", "价格", "库存"};
        String[][] newData = goodsService.getGoodsList(restaurantId);
    
        // 获取表格模型并更新其数据
        tModel = (DefaultTableModel) table.getModel();
        tModel.setDataVector(newData, columnNames);
    
        // 通知表格模型数据已更改
        tModel.fireTableDataChanged();
    
        // 关闭刷新中的提示框
        JOptionPane.getRootFrame().dispose();
    }

    public void refreshOrdersList(int stuId, JTable sourceTable) {
        // 获取当前用户的餐厅 ID
        int restaurantId = restService.getId();
    
        // 显示刷新中的提示框
        JOptionPane.showMessageDialog(this, "正在刷新订单列表，请稍候...", "刷新中", JOptionPane.INFORMATION_MESSAGE);
    
        // 从服务中获取最新的商品数据
        String[] columnNames = {"订单名", "餐厅号", "金额", "日期", "状态"};
        String[][] newData = orderService.getOrderByStuId(stuId);
    
        // 直接设置新的数据模型到表格
        sourceTable.setModel(new javax.swing.table.DefaultTableModel(newData, columnNames));
    
        // 关闭刷新中的提示框
        JOptionPane.getRootFrame().dispose();
    }
    
    

    public void init() {
        //初始化面板

        if (userType.equals("学生")) {
            Entity entity = stuService.getEntity(username);
            setTitle("外卖管理系统 姓名：" + entity.getStr("name") + " 余额：" + entity.getDouble("point"));
            JLabel cj = new JLabel("充值金额：");
            cj.setBounds(0, 0, 70, 20);
            JTextField cj_tf = new JTextField();
            cj_tf.setBounds(cj.getWidth(), 0, 100, 20);
            JButton cj_btu = new JButton("充值");
            cj_btu.setBounds(cj.getWidth() + cj_tf.getWidth(), 0, 60, 20);
            cj_btu.addActionListener(e -> {
                //点击事件 更新余额
                stuService.recharge(username, Double.valueOf(cj_tf.getText()));
                Entity tmp = stuService.getEntity(username);
                setTitle("外卖管理系统 姓名：" + tmp.getStr("name") + " 余额：" + tmp.getDouble("point"));
            });
            add(cj);
            add(cj_tf);
            add(cj_btu);

            // 学生id
            int stuId = entity.getInt("id");

            JLabel ol = new JLabel("你的订单：");
            ol.setBounds(0, 20, 2000, 20);
            String[] columnNames = {"订单号", "餐厅名", "金额", "日期", "状态"};
            String[][] rowData = orderService.getOrderByStuId(entity.getInt("id"));
            JTable table = new JTable(rowData, columnNames);
            // 设置选择模式为单选
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane jsp = new JScrollPane(table);
            jsp.setBounds(0, 40, this.getWidth(), this.getHeight() - 40);
            this.add(jsp);
            this.add(ol);

            // 点餐功能
            JButton order_btu = new JButton("点餐");
            order_btu.setBounds(cj.getWidth() + cj_tf.getWidth() + cj_btu.getWidth(), 0, 60, 20);
            add(order_btu);
            order_btu.addActionListener(e -> {
                new OrderFrame(mainFrame, 1, goodsService, orderService);
            });
            this.add(order_btu);

            // // 添加返回按钮
            // JButton back_btu1 = new JButton("返回");
            // back_btu1.setBounds(cj.getWidth() + cj_tf.getWidth() + cj_btu.getWidth() + order_btu.getWidth(), 0, 60, 20);
            // add(back_btu1);

            // 刷新订单按钮
            JButton ref_order_btu = new JButton("刷新");
            ref_order_btu.setBounds(cj.getWidth() + cj_tf.getWidth() + cj_btu.getWidth() + order_btu.getWidth(), 0, 60, 20);
            add(ref_order_btu);

            // // 实现back_btu1的点击事件
            // back_btu1.addActionListener(e -> {
            //     // 关闭当前窗口
            //     dispose();
            //     // 显示登录窗口
            //     new LoginFrame(this).setVisible(true);
            // });

            // 实现刷新订单ref_order_btu的点击事件
            ref_order_btu.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    refreshOrdersList(stuId, table);
                });
            });
        
        } else {
            Entity entity = restService.getEntity(username);
            setTitle("外卖管理系统 餐厅名：" + entity.getStr("name") + " 餐厅号：" + entity.getInt("id") +
                    " 营业额：" + orderService.getOrderCountByRestId(entity.getInt("id")));

            JButton add_btu = new JButton("添加商品");
            add_btu.setBounds(0, 0, 100, 20);
            JButton del_btu = new JButton("删除商品");
            del_btu.setBounds(add_btu.getWidth(), 0, 100, 20);
            JButton upd_btu = new JButton("修改商品");
            upd_btu.setBounds(add_btu.getWidth() + del_btu.getWidth(), 0, 100, 20);

            JButton ref_good_btu = new JButton("刷新");
            ref_good_btu.setBounds(add_btu.getWidth() + del_btu.getWidth() + upd_btu.getWidth(), 0, 100, 20);

            // // 返回功能
            // JButton back_btu2 = new JButton("返回");
            // back_btu2.setBounds(add_btu.getWidth() + del_btu.getWidth() + upd_btu.getWidth() + ref_good_btu.getWidth(), 0, 60, 20);


            add(add_btu);
            add(del_btu);
            add(upd_btu);
            add(ref_good_btu);
            // add(back_btu2);

            JLabel ol = new JLabel("你的商品：");
            ol.setBounds(0, 20, 2000, 20);
            String[] columnNames = {"商品名", "商品号", "价格", "库存 "};
            String[][] rowData = goodsService.getGoodsList(entity.getInt("id"));
            tModel = new DefaultTableModel(rowData, columnNames);
            table = new JTable(tModel);
            // 设置选择模式为单选
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane jsp = new JScrollPane(table);
            jsp.setBounds(0, 40, this.getWidth(), this.getHeight() - 40);
            this.add(jsp);
            this.add(ol);

            // 实现add_btu的点击事件
            add_btu.addActionListener(e -> {
                new AddGoodsFrame(this, entity.getInt("id")).setVisible(true);
            });

            // 实现del_btu的点击事件
            del_btu.addActionListener(e -> {
                // 获取选中的行
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "请先选择一行", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 获取选中行的商品号
                int goodsId = Integer.parseInt((String) table.getValueAt(selectedRow, 1));
                // 删除商品
                goodsService.deleteById(goodsId);
            });
            
            // 实现upd_btu的点击事件
            upd_btu.addActionListener(e -> {
                // 获取选中的行
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "请先选择一行", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // 获取选中行的商品号
                int goodsId = Integer.parseInt((String) table.getValueAt(selectedRow, 1));
                // 获取选中行的商品名
                String goodsName = (String) table.getValueAt(selectedRow, 0);
                // 获取选中行的价格
                double goodsPrice = Double.parseDouble((String) table.getValueAt(selectedRow, 2));
                // 获取选中行的库存
                int goodsStock = Integer.parseInt((String) table.getValueAt(selectedRow, 3));
                // 显示修改商品窗口
                new UpdateGoodsFrame(this, goodsId, goodsName, goodsPrice, goodsStock).setVisible(true);
            });

            // 实现ref_good_btu的点击事件
            ref_good_btu.addActionListener(e -> refreshGoodsList());

            // // 实现back_btu2的点击事件
            // back_btu2.addActionListener(e -> {
            //     // 关闭当前窗口
            //     dispose();
            //     // 显示登录窗口
            //     new LoginFrame(this).setVisible(true);
            // });
            
        }
    }
}

