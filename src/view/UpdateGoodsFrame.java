package view;

import service.GoodsService;
import service.OrderService;
import service.RestService;
import service.StuService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateGoodsFrame extends JFrame {
    private MainFrame mainFrame;
    private int goodsId;
    private String goodsName;
    private double goodsPrice;
    private int goodsStock;

    private RestService restService = new RestService();

    private GoodsService goodsService = new GoodsService(restService);

    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;

    public UpdateGoodsFrame(MainFrame mainFrame, int goodsId, String goodsName, double goodsPrice, int goodsStock) {
        this.mainFrame = mainFrame;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsStock = goodsStock;

        setTitle("修改商品");
        setResizable(false);
        setLayout(null);
        setBounds(400, 400, 300, 200);

        JLabel nameLabel = new JLabel("商品名：");
        nameLabel.setBounds(20, 20, 80, 20);
        nameTextField = new JTextField(goodsName);
        nameTextField.setBounds(100, 20, 150, 20);

        JLabel priceLabel = new JLabel("价格：");
        priceLabel.setBounds(20, 50, 80, 20);
        priceTextField = new JTextField(String.valueOf(goodsPrice));
        priceTextField.setBounds(100, 50, 150, 20);

        JLabel stockLabel = new JLabel("库存：");
        stockLabel.setBounds(20, 80, 80, 20);
        stockTextField = new JTextField(String.valueOf(goodsStock));
        stockTextField.setBounds(100, 80, 150, 20);

        JButton updateButton = new JButton("更新");
        updateButton.setBounds(100, 120, 80, 30);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取更新后的值
                String updatedName = nameTextField.getText();
                double updatedPrice = Double.parseDouble(priceTextField.getText());
                int updatedStock = Integer.parseInt(stockTextField.getText());

                // 使用你的服务类更新商品信息
                goodsService.updateGoods(goodsId, updatedName, updatedPrice, updatedStock);

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
        add(updateButton);

        setVisible(true);
    }
}
