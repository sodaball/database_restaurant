package service;


import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import java.sql.SQLException;

public class StuService {

    private Db db = Db.use();

    public String getPassword(String username) {
        try {
            return db.get(Entity.create("student").set("username", username)).getStr("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName(String username) {
        try {
            return db.get(Entity.create("student").set("username", username)).getStr("name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Entity getEntity(String username) {
        try {
            return db.get(Entity.create("student").set("username", username));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void recharge(String username, Double rechargePoint) {
        try {
            db.tx(db -> {
                double point = getEntity(username).getDouble("point");
                db.update(Entity.create().set("point", point + rechargePoint),
                        Entity.create("student").set("username", username)
                );
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
