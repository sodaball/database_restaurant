package view;

import service.GoodsService;
import service.RestService;
import service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderFrame extends JFrame {

    private JFrame parentFrame;
    private int stuId;
    private GoodsService goodsService;
    private RestService restService;
    private OrderService orderService;
    private DefaultTableModel tableModel;

    private MainFrame mainFrame;

    // 声明表格和表格模型
    private JTable table;
    private DefaultTableModel tModel;

    private int restaurantId = 1;

    public OrderFrame(JFrame parentFrame, int stuId, GoodsService goodsService, OrderService orderService) {
        this.parentFrame = parentFrame;
        this.stuId = stuId;
        this.goodsService = goodsService;
        this.orderService = orderService;

        initUI();
    }

    private void initUI() {
        // 初始化点餐界面UI组件
        tableModel = new DefaultTableModel(new String[]{"商品", "价格", "库存"}, 0);
        JTable goodsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(goodsTable);
        JButton confirmOrderButton = new JButton("确认点餐");

        // 获取商品数据并更新表格
        updateGoodsTable();

        // 为确认点餐按钮添加事件监听器
        confirmOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = goodsTable.getSelectedRow();
                if (selectedRow != -1) {
                    addOrderToDatabase(selectedRow);
                    // 关闭当前窗口
                    dispose();
                    // 弹出提示框
                    JOptionPane.showMessageDialog(OrderFrame.this, "点餐成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    // 刷新订单列表
                    // mainFrame.refreshOrdersList(stuId);
                    SwingUtilities.invokeLater(() -> {
                        SwingUtilities.invokeLater(() -> {
                            mainFrame.refreshOrdersList(stuId, table);
                        });
                    });
                } else {
                    JOptionPane.showMessageDialog(OrderFrame.this, "请选择一项商品", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 设置布局、添加组件，配置窗口属性
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(scrollPane);
        add(confirmOrderButton);

        setTitle("点餐");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentFrame);
        setVisible(true);
        
    }

    private void addOrderToDatabase(int selectedRow) {
        // 获取选中行的商品信息
        String goodsName = (String) tableModel.getValueAt(selectedRow, 0);
        double price = Double.parseDouble((String) tableModel.getValueAt(selectedRow, 1));

        // 获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());

        // 添加订单到数据库
        orderService.addOrder(goodsService.getGoodsIdByName(goodsName), stuId, "配送中", price, date);
    }

    private void updateGoodsTable() {
        
        // 获取商品数据并更新表格
        String[][] goodsData = goodsService.getGoodsList(restaurantId);
        for (String[] row : goodsData) {
            tableModel.addRow(row);
        }
    }
}
