package com.main.farminggame.controller;

import com.main.farminggame.connection.Connectiondb;
import com.main.farminggame.culture.CategorieCulture;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

@RestController
public class CategorieCultureController {

    @GetMapping("/categorie-culture")
    @ResponseBody
    public CategorieCulture[] getAll() throws SQLException {
        Connection con = Connectiondb.connect();
        CategorieCulture[] result = CategorieCulture.get_all_categorie_culture(con);
        con.close();
        return result;
    }

    @GetMapping("/categorie-culture/{id}")
    @ResponseBody
    public CategorieCulture getById(@PathVariable("id") String id) throws SQLException {
        Connection con = Connectiondb.connect();
        CategorieCulture result = CategorieCulture.get_categorie_by_id(con, id);
        con.close();
        return result;
    }

    @PostMapping("/categorie-culture")
    @ResponseBody
    public StatutMessage insert(@RequestBody CategorieCulture categorieCulture) throws SQLException {
        Connection con = Connectiondb.connect();
        StatutMessage message = new StatutMessage();
        try {
            if (categorieCulture.insert(con)) {
                message.statut = "succes";
                message.message = "categorie culture inséré";
            } else {
                message.statut = "failed";
                message.message = "categorie culture non inséré";
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        con.close();
        return message;
    }

    @DeleteMapping("/categorie-culture/{id}")
    @ResponseBody
    public StatutMessage insert(@PathVariable("id") String id) throws SQLException {
        Connection con = Connectiondb.connect();
        StatutMessage message = new StatutMessage();
        if (CategorieCulture.delete(con, id)) {
            message.statut = "succes";
            message.message = "categorie culture supprimé";
        } else {
            message.statut = "failed";
            message.message = "categorie culture non supprimé";
        }
        con.close();
        return message;
    }

    @PutMapping("/categorie-culture")
    @ResponseBody
    public StatutMessage update(@RequestBody CategorieCulture categorieCulture) throws SQLException {
        Connection con = Connectiondb.connect();
        StatutMessage message = new StatutMessage();
        if (categorieCulture.update(con)) {
            message.statut = "succes";
            message.message = "categorie culture modifié";
        } else {
            message.statut = "failed";
            message.message = "categorie culture non modifié";
        }
        con.close();
        return message;
    }

}
