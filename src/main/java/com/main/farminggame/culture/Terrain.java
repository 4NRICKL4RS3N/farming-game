package com.main.farminggame.culture;

import com.main.farminggame.dimension.Dimension;
import com.main.farminggame.user.User;
//import culture.Parcelle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

public class Terrain {

    String idTerrain;
    User user;
    Dimension dimension;
//    Parcelle[] parcelle;
    Timestamp date_creation;
    public static double prix = 50000;

    public Terrain(){}


    public String getIdTerrain() {
        return idTerrain;
    }
    public void setIdTerrain(String idTerrain) {
        this.idTerrain = idTerrain;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Dimension getDimension() {
        return dimension;
    }
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
//    public Parcelle[] getParcelle() {
//        return parcelle;
//    }
//    public void setParcelle(Parcelle[] parcelle) {
//        this.parcelle = parcelle;
//    }
    public Timestamp getDate_creation() {
        return date_creation;
    }
    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    public static Terrain[] getAllTerrain(Connection con) {
        Terrain[] list = null;
        try {
            Statement s = con.createStatement();
            String req = "select * from terrain";
            ResultSet rs = s.executeQuery(req);
            Vector v = new Vector<>();
            while (rs.next()) {
                Terrain newTerrain = new Terrain();
                newTerrain.setIdTerrain(rs.getString("id"));
                newTerrain.setUser(User.get_user_by_id(con, rs.getString("id_user")));
                newTerrain.setDimension(new Dimension(rs.getInt("ax"), rs.getInt("ay"), rs.getInt("bx"), rs.getInt("by")));
                newTerrain.setDate_creation(rs.getTimestamp("date_creation"));
                v.add(newTerrain);
            }
            list = new Terrain[v.size()];
            v.copyInto(list);
            s.close();
            con.close();
            return list;
        } catch (Exception e) {
        }
        return list;
    }

    public static Terrain[] getTerrainByUser(Connection con, String idUser) {
        Terrain[] list = null;
        try {
            String req = "select * from terrain where id_user = ?";
            PreparedStatement s = con.prepareStatement(req);
            s.setString(1, idUser);
            ResultSet rs = s.executeQuery(req);
            Vector v = new Vector<>();
            while (rs.next()) {
                Terrain newTerrain = new Terrain();
                newTerrain.setIdTerrain(rs.getString("id"));
                newTerrain.setUser(User.get_user_by_id(con, rs.getString("id_user")));
                newTerrain.setDimension(new Dimension(rs.getInt("ax"), rs.getInt("ay"), rs.getInt("bx"), rs.getInt("by")));
                newTerrain.setDate_creation(rs.getTimestamp("date_creation"));
                v.add(newTerrain);
            }
            list = new Terrain[v.size()];
            v.copyInto(list);
            s.close();
            con.close();
            return list;
        } catch (Exception e) {
        }
        return list;
    }

    public static Terrain[] getTerrainValide(Connection con) {
        Terrain[] list = null;
        try {
            Statement s = con.createStatement();
            String req = "select * from v_terrain_valide";
            ResultSet rs = s.executeQuery(req);
            Vector v = new Vector<>();
            while (rs.next()) {
                Terrain newTerrain = new Terrain();
                newTerrain.setIdTerrain(rs.getString("id"));
                newTerrain.setUser(User.get_user_by_id(con, rs.getString("id_user")));
                newTerrain.setDimension(new Dimension(rs.getInt("ax"), rs.getInt("ay"), rs.getInt("bx"), rs.getInt("by")));
                newTerrain.setDate_creation(rs.getTimestamp("date_creation"));
                v.add(newTerrain);
            }
            list = new Terrain[v.size()];
            v.copyInto(list);
            s.close();
            con.close();
            return list;
        } catch (Exception e) {
        }
        return list;
    }

    public static Terrain[] getTerrainNonValide(Connection con) {
        Terrain[] list = null;
        try {
            Statement s = con.createStatement();
            String req = "select * from v_terrain_non_valide";
            ResultSet rs = s.executeQuery(req);
            Vector v = new Vector<>();
            while (rs.next()) {
                Terrain newTerrain = new Terrain();
                newTerrain.setIdTerrain(rs.getString("id"));
                newTerrain.setUser(User.get_user_by_id(con, rs.getString("id_user")));
                newTerrain.setDimension(new Dimension(rs.getInt("ax"), rs.getInt("ay"), rs.getInt("bx"), rs.getInt("by")));
                newTerrain.setDate_creation(rs.getTimestamp("date_creation"));
                v.add(newTerrain);
            }
            list = new Terrain[v.size()];
            v.copyInto(list);
            s.close();
            con.close();
            return list;
        } catch (Exception e) {
        }
        return list;
    }

    public boolean insert(Connection con) {
        String sql = "insert into terrain values (default, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, this.getUser().getIdUser());
            st.setInt(2, this.getDimension().getaX());
            st.setInt(3, this.getDimension().getaY());
            st.setInt(4, this.getDimension().getbX());
            st.setInt(5, this.getDimension().getbY());
            st.setTimestamp(6, this.getDate_creation());

            int line = st.executeUpdate();
            st.close();
            if (line > 0) {
                return true;
            }

        } catch (Exception e) {
        }
        return false;
    }

    public static boolean valider(Connection con, String idTerrain, Timestamp date_validation) {
        String sql = "insert into terrain_validation values (default, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, idTerrain);
            st.setTimestamp(2, date_validation);

            int line = st.executeUpdate();
            st.close();
            if (line > 0) {
                return true;
            }

        } catch (Exception e) {
        }
        return false;
    }

}
