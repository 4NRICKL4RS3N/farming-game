package com.main.farminggame.controller;

import com.main.farminggame.connection.Connectiondb;
import com.main.farminggame.culture.CategorieCulture;
import com.main.farminggame.culture.Culture;
import com.main.farminggame.culture.Parcelle;
import com.main.farminggame.culture.Terrain;
import com.main.farminggame.dimension.Dimension;
import com.main.farminggame.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

class UserData {
    String id;
}

class ParcelleData{
    String idTerrain;
    String idCatCult;
    String idCulture;
    String ax;
    String ay;
    String bx;
    String by;
    String idCult;
}

@RestController
public class ParcelleController {
    @PostMapping("/insert-parcelle")
    @ResponseBody
    public StatutMessage insertParcelle (@RequestBody ParcelleData parcelle, UserData ud) throws SQLException {
        Connection con = Connectiondb.connect();
        Terrain t = new Terrain();
            t.setIdTerrain(parcelle.idTerrain);
        CategorieCulture catCult = new CategorieCulture();
            catCult.setIdCategorieCulture(parcelle.idCatCult);
        Dimension d = new Dimension();
            d.setaX(Integer.parseInt(parcelle.ax));
            d.setaY(Integer.parseInt(parcelle.ay));
            d.setbX(Integer.parseInt(parcelle.bx));
            d.setbY(Integer.parseInt(parcelle.bx));
        Culture c = new Culture();
        Culture cult = new Culture();
            cult.setIdCulture(parcelle.idCulture);
            cult.setCategorie(catCult);
        Culture cul = c.getById(con, cult.getIdCulture());

        User u = new User();
        User user = u.get_user_by_id(con, ud.id);
        double montantUser = user.getArgent();
        double prixPlanter = cul.getPrix_planter();
        double diff = montantUser - prixPlanter ;
        StatutMessage message = new StatutMessage();
        try {
            if (diff >= 0) {
                if (u.createParcelle(con, t, catCult, d)) {
                    message.statut = "Succes";
                    message.message = "Parcelle allouée";
                    if (u.updateArgent(con, diff)) {
                        message.message = "Argent mis à jour";
                    }
                } else {
                    message.statut = "Failed";
                    message.message = "Parcelle non inséré";
                }   
            } else {
                message.message = "Argent insuffisant";
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        con.close();
        return message;
    }
}
