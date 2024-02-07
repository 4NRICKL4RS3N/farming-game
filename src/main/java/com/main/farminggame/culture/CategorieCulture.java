package com.main.farminggame.culture;

import java.sql.*;
import java.util.Vector;

public class CategorieCulture 
{
    String idCategorieCulture;
    String designation;
    Double prix;
//    Culture[] culture_possible;

    public CategorieCulture (){}

    public String  getIdCategorieCulture() {
        return idCategorieCulture;
    }
    public void setIdCategorieCulture(String idCategorieCulture) {
        this.idCategorieCulture = idCategorieCulture;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public Double getPrix() {
        return prix;
    }
    public void setPrix(Double prix) {
        this.prix = prix;
    }
//    public Culture[] getCulture_possible() {
//        return culture_possible;
//    }
//    public void setCulture_possible(Culture[] culture_possible) {
//        this.culture_possible = culture_possible;
//    }

    public CategorieCulture (String id, String designation,double prix) {
        this.setIdCategorieCulture(id);
        this.setDesignation(designation);
        this.setPrix(prix);
    }

    public static CategorieCulture get_categorie_by_id(Connection con,String id) {
        CategorieCulture cat = null;
        try {
            Statement s = con.createStatement();
            String sql = "select * from categorie_culture where id = '" + id + "'";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                cat = new CategorieCulture(rs.getString(1), rs.getString(2), rs.getDouble(3));                
            }
            s.close();
            return cat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cat;
    }

    public static CategorieCulture[] get_all_categorie_culture(Connection con) {
        CategorieCulture[] listcat = null;
        try {
            Statement s = con.createStatement();
            String sql = "select * from categorie_culture ";
            ResultSet rs = s.executeQuery(sql);
            Vector v = new Vector<>();
            while (rs.next()) {
                v.add(new CategorieCulture(rs.getString(1), rs.getString(2), rs.getDouble(3)));                
            }
            listcat = new CategorieCulture[v.size()];
            v.copyInto(listcat);
            s.close();
            return listcat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listcat;
    }

    public boolean insert(Connection con) {
        String sql = "insert into categorie_culture values (default, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, this.getDesignation());
            st.setDouble(2, this.getPrix());

            int line = st.executeUpdate();
            st.close();
            if (line > 0) {
                return true;
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public boolean update(Connection con) {
        String sql = "update categorie_culture set designation = ?, prix = ? where id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, this.getDesignation());
            st.setDouble(2, this.getPrix());
            st.setString(3, this.getIdCategorieCulture());

            int line = st.executeUpdate();
            st.close();
            if (line > 0) {
                return true;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public static boolean delete(Connection con, String id) {
        String sql = "delete from categorie_culture where id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id);

            int line = st.executeUpdate();
            st.close();
            if (line > 0) {
                return true;
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

}
