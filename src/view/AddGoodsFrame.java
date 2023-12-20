package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.SQLException;

import cn.hutool.db.Db;

import service.GoodsService;
import service.OrderService;
import service.RestService;
import service.StuService;

public class AddGoodsFrame extends JFrame {
    private MainFrame mainFrame;
    private int restaurantId;

    private RestService restService = new RestService();

    private GoodsService goodsService = new GoodsService(restService);

    public AddGoodsFrame(MainFrame mainFrame, int restaurantId) {
        this.mainFrame = mainFrame;
        this.restaurantId = restaurantId;

        setTitle("添加商品");
        setResizable(false);
        setLayout(null);
        setBounds(400, 400, 300, 200);

        JLabel nameLabel = new JLabel("商品名：");
        nameLabel.setBounds(20, 20, 80, 20);
        JTextField nameTextField = new JTextField();
        nameTextField.setBounds(100, 20, 150, 20);

        JLabel priceLabel = new JLabel("价格：");
        priceLabel.setBounds(20, 50, 80, 20);
        JTextField priceTextField = new JTextField();
        priceTextField.setBounds(100, 50, 150, 20);

        JLabel stockLabel = new JLabel("库存：");
        stockLabel.setBounds(20, 80, 80, 20);
        JTextField stockTextField = new JTextField();
        stockTextField.setBounds(100, 80, 150, 20);

        JButton addButton = new JButton("添加");
        addButton.setBounds(100, 120, 80, 30);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的商品信息
                String name = nameTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int stock = Integer.parseInt(stockTextField.getText());

                // 增加商品
                goodsService.addGoods(name, price, stock, restaurantId);

                // 关闭当前窗口
                dispose();

                // 刷新商品列表
                mainFrame.refreshGoodsList();
            }
        });

        add(nameLabel);
        add(nameTextField);
        add(priceLabel);
        add(priceTextField);
        add(stockLabel);
        add(stockTextField);
        add(addButton);

        setVisible(true);
    }

    private void refreshGoodsList() {
        mainFrame.refreshGoodsList();
    }
    
}
