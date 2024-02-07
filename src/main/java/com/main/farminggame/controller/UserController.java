package com.main.farminggame.controller;

import com.main.farminggame.connection.Connectiondb;
import com.main.farminggame.user.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

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
    @PostMapping("/adminLogin")
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

}
