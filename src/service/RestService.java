package service;


import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import java.sql.SQLException;

public class RestService {

    private Db db = Db.use();

    public String getPassword(String username) {
        try {
            return db.get(Entity.create("restaurant").set("username", username)).getStr("password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // getId
    public int getId() {
        return 1;
    }

    public String getName(String username) {
        try {
            return db.get(Entity.create("restaurant").set("username", username)).getStr("name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getName(int id) {
        try {
            return db.get(Entity.create("restaurant").set("id", id)).getStr("name");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Entity getEntity(String username) {
        try {
            return db.get(Entity.create("restaurant").set("username", username));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
