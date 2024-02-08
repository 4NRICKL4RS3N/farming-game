package com.main.farminggame.culture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Culture {
    String idCulture;
    CategorieCulture categorie;
    String designation;
    double prix_planter;
    double prix_recolter;

    public Culture(){}

    public Culture(String idCulture, CategorieCulture categorie, String designation, double prix_planter, double prix_recolter) {
        this.idCulture = idCulture;
        this.categorie = categorie;
        this.designation = designation;
        this.prix_planter = prix_planter;
        this.prix_recolter = prix_recolter;
    }

    public String getIdCulture() {
        return idCulture;
    }
    public void setIdCulture(String idCulture) {
        this.idCulture = idCulture;
    }
    public CategorieCulture getCategorie() {
        return categorie;
    }
    public void setCategorie(CategorieCulture categorie) {
        this.categorie = categorie;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public double getPrix_planter() {
        return prix_planter;
    }
    public void setPrix_planter(double prix_planter) {
        this.prix_planter = prix_planter;
    }
    public double getPrix_recolter() {
        return prix_recolter;
    }
    public void setPrix_recolter(double prix_recolter) {
        this.prix_recolter = prix_recolter;
    }

    public static Culture getById(Connection con, String idCulture) {
        Culture cult = null;
        try {
            String sql = "select * from v_cult_cat where id = ?";
            PreparedStatement s = con.prepareStatement(sql);
            s.setString(1, idCulture);
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                cult = new Culture();
                cult.setIdCulture(rs.getString(1));
                cult.setCategorie(new CategorieCulture(rs.getString(2), rs.getString(6),rs.getDouble(7)));
                cult.setDesignation(rs.getString(3));
                cult.setPrix_planter(rs.getDouble(4));
                cult.setPrix_recolter(rs.getDouble(5));
            }
            s.close();
            con.close();
            return cult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cult;
    }

    public static Culture[] getAll(Connection con) {
        Culture[] cults = null;
        try {
            String sql = "select * from v_cult_cat";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);
            Vector v = new Vector<>();
            while (rs.next()) {
                Culture cult = new Culture();
                cult.setIdCulture(rs.getString(1));
                cult.setCategorie(new CategorieCulture(rs.getString(2), rs.getString(6),rs.getDouble(7)));
                cult.setDesignation(rs.getString(3));
                cult.setPrix_planter(rs.getDouble(4));
                cult.setPrix_recolter(rs.getDouble(5));
                v.add(cult);
            }
            cults = new Culture[v.size()];
            v.copyInto(cults);
            s.close();
            con.close();
            return cults;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cults;
    }
}
