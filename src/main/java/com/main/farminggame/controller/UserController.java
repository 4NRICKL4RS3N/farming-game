package com.main.farminggame.controller;

import com.main.farminggame.connection.Connectiondb;
import com.main.farminggame.culture.Terrain;
import com.main.farminggame.dimension.Dimension;
import com.main.farminggame.user.User;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

class BuyTerrainInput {
    public String date;
    public int ax;
    public int ay;
    public int bx;
    public int by;
}

class StatutMessage {
    public String statut;
    public String message;
    public StatutMessage() {
    }
    public StatutMessage(String statut, String message) {
        this.statut = statut;
        this.message = message;
    }
}

class LoginInput {
    public String email;
    public String mdp;
}
@RestController
public class UserController {
    @PostMapping("/admin-login")
    @ResponseBody
    public StatutMessage login(@RequestBody LoginInput input) throws SQLException {
        Connection con = Connectiondb.connect();
        int result = User.connectAdmin(con, input.email, input.mdp);
        System.out.println("-------------"+input.email);
        StatutMessage response = new StatutMessage();
        if (result > 0){
            response.statut = "succes";
            response.message = "connexion réussie";
        } else {
            if (result == -2) {
                response.statut = "failed";
                response.message = "Email non exitant";
            }
            if (result == -1) {
                response.statut = "failed";
                response.message = "Vous n'êtes pas un administrateur";
            }
            if (result == 0) {
                response.statut = "failed";
                response.message = "Mot de passe incorect";
            }
        }
        con.close();
        return response;
    }

    @GetMapping("/user")
    @ResponseBody
    public User[] getAllUser() throws SQLException {
        Connection con = Connectiondb.connect();
        User[] result = User.getAllUsers(con);
        con.close();
        return result;
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public User getUserById(@PathVariable("id") String id) throws SQLException {
        Connection con = Connectiondb.connect();
        User result = User.get_user_by_id(con, id);
        con.close();
        return result;
    }

    @GetMapping("/user/{id}/terrain")
    @ResponseBody
    public Terrain[] getUserTerrain(@PathVariable("id") String id) throws SQLException {
        Connection con = Connectiondb.connect();
        Terrain[] result = Terrain.getTerrainByUser(con, id);
        con.close();
        return result;
    }

    @PostMapping("/user/{id}/terrain")
    @ResponseBody
    public StatutMessage buyTerrain(@RequestBody BuyTerrainInput input, @PathVariable String id) throws SQLException {
        Connection con = Connectiondb.connect();
        Timestamp date = Timestamp.valueOf(input.date);
        Dimension dim = new Dimension(input.ax, input.ay, input.bx, input.by);
        User user = User.get_user_by_id(con, id);
        StatutMessage message = new StatutMessage("succes", "terrain créé");
        if (!user.buyTerrain(con, dim, date)) {
            message.statut = "failed";
            message.message = "terrain non créé";
        }
        con.close();
        return message;
    }

}
