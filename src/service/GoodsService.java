package service;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import view.MainFrame;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GoodsService {

    private Db db = Db.use();

    private RestService restService;
    private MainFrame mainFrame;

    // 声明表格和表格模型
    private JTable table;
    private DefaultTableModel tModel;

    public GoodsService(RestService restService) {
        this.restService = restService;
    }

    public String getRestName(int id) {
        try {
            return restService.getName(
                    Integer.parseInt(db.get(Entity.create("goods").set("id", id)).getStr("rest_id"))
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getGoodsIdByName(String name) {
        try {
            return db.get(Entity.create("goods").set("name", name)).getStr("id");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        try {
            return restService.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getGoodsList(int restId) {
        try {
            List<Entity> list = db.findAll(Entity.create("goods").set("rest_id", restId));
            String[][] res = new String[list.size()][4];
            int i = 0;
            for (Entity it : list) {
                res[i++] = new String[]{
                        it.getStr("name"),
                        it.getStr("id"),
                        it.getDouble("price").toString(),
                        it.getInt("stock").toString()
                };
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 增加商品
    public void addGoods(String name, double price, int stock, int restId) {
        try {
            db.tx(it -> {
                db.insert(Entity.create("goods")
                        .set("name", name)
                        .set("price", price)
                        .set("stock", stock)
                        .set("rest_id", restId)
                );
            });
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 更新商品
    public void updateGoods(int id, String name, double price, int stock) {
        try {
            db.tx(it -> db.update(
                    Entity.create().set("name", name).set("price", price).set("stock", stock),
                    Entity.create("goods").set("id", id)
            ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 刷新页面
    public void deleteById(int id) {
        try {
            db.tx(it -> db.del(Entity.create("goods").set("id", id)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
