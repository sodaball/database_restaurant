package service;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OrderService {

    // 声明表格和表格模型
    private JTable table;
    private DefaultTableModel tModel;

    private Db db = Db.use();

    private GoodsService goodsService;
    private RestService restService;

    public OrderService(GoodsService restService) {
        this.goodsService = restService;
    }

    public String[][] getOrderByStuId(int stuId) {
        try {
            List<Entity> list = db.findAll(Entity.create("order").set("stu_id", stuId));
            String[][] res = new String[list.size()][5];
            int i = 0;
            for (Entity it : list) {
                res[i++] = new String[]{
                        it.getStr("id"),
                        goodsService.getRestName(it.getInt("goods_id")),
                        it.getDouble("price").toString(),
                        it.getStr("date"),
                        it.getStr("status"),
                };
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getOrderCountByRestId(int id) {
        try {
            return db.get(Entity.create("rest_count").set("id", id)).getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOrder(String goodsId, int stuId, String status, double price, String date) {
        try {
            db.insert(Entity.create("order")
                    .set("stu_id", stuId)
                    .set("goods_id", goodsId)
                    .set("price", price)
                    .set("date", date)
                    .set("status", status)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // public void refreshOrdersList(int stuId) {
    //     // 获取当前用户的餐厅 ID
    //     int restaurantId = restService.getId();
    
    //     // 显示刷新中的提示框
    //     // JOptionPane.showMessageDialog(this, "正在刷新订单列表，请稍候...", "刷新中", JOptionPane.INFORMATION_MESSAGE);
    //     JOptionPane.showMessageDialog(null, "正在刷新订单列表，请稍候...", "刷新中", JOptionPane.INFORMATION_MESSAGE);
    
    //     // 从服务中获取最新的商品数据
    //     String[] columnNames = {"订单名", "餐厅号", "金额", "日期", "状态"};
    //     String[][] newData = getOrderByStuId(stuId);
    //     // String[][] newData = goodsService.getGoodsList(restaurantId);
    
    //     // 获取表格模型并更新其数据
    //     tModel = (DefaultTableModel) table.getModel();
    //     tModel.setDataVector(newData, columnNames);
    
    //     // 通知表格模型数据已更改
    //     tModel.fireTableDataChanged();
    
    //     // 关闭刷新中的提示框
    //     JOptionPane.getRootFrame().dispose();
    // }

}
