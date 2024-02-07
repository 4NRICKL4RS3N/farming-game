package com.main.farminggame.user;
import java.sql.*;
import java.util.Vector;
import com.main.farminggame.connection.Connectiondb;
//import culture.CategorieCulture;
//import culture.Parcelle;
//import culture.Terrain;
//import dimension.Dimension;

public class User 
{
    String idUser;
    String pseudo;
    String password;
    String email;
    double argent;
    boolean isAdmin;
//    Terrain[] terrain;
    
    public User(){}
    public String getIdUser() {
        return idUser;
    }
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
    
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public double getArgent() {
        return argent;
    }
    public void setArgent(double argent) {
        this.argent = argent;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

//    public Terrain[] getTerrain() {
//        return terrain;
//    }
//    public void setTerrain(Terrain[] terrain) {
//        this.terrain = terrain;
//    }

    public User (String id,String pseudo,String password,String mail, double argent, int isAdmin) {
        this.setIdUser(id);
        this.setPseudo(pseudo);
        this.setPassword(password);
        this.setEmail(mail);
        this.setArgent(argent);
        if (isAdmin == 0) {
            this.setIsAdmin(false);
        } else {
            this.setIsAdmin(true);
        }
    }

    public static User[] getAllUsers(Connection con) {
        User[] list = null;
        try {
            Statement s = con.createStatement();
            String req = "select id,pseudo,password,email,argent,role from \"user\"";
            ResultSet rs = s.executeQuery(req);
            Vector v = new Vector<>();
            while (rs.next()) {
                v.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6)));
            }
            list = new User[v.size()];
            v.copyInto(list);
            s.close();
            con.close();
            return list;
        } catch (Exception e) {
        }
        return list;
    }

    public static User get_user_by_id(Connection con, String id) {
        User user = null;
        String sql = "select * from \"user\" where id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getInt(6));
        } catch (Exception e) {
        }
        return user;
    }

    public boolean updateArgent(Connection con, double newArgent) {
        String sql = "update \"user\" set argent = ? where id = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setDouble(1, newArgent);
            st.setString(2, this.getIdUser());

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

    public static int connectAdmin(Connection con, String email, String mpd) {
        String sqlCheckUser = "select * from \"user\" where email = ?";
        try {
            PreparedStatement st = con.prepareStatement(sqlCheckUser);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                // tsy existant le user
                return -2;
            }
            int role = rs.getInt("role");
            if (role == 0) {
                // tsy admin
                return -1;
            }
            String trueMdp = rs.getString("password");
            if (trueMdp.compareTo(mpd) != 0) {
                //diso mdp
                return 0;
            }
        } catch (Exception e) {
        }
        return 1;
    }

//    public boolean createParcelle(Connection con, Terrain terrain, CategorieCulture categorieCulture, Dimension dimension) {
//        Parcelle newParcelle = new Parcelle();
//        newParcelle.setTerrain(terrain);
//        newParcelle.setCategorie(categorieCulture);
//        newParcelle.setDimension(dimension);
//        if (newParcelle.insert(con)) {
//            return true;
//        }
//        return false;
//    }
//
//    boolean checkByTerrain(Dimension dimension) {
//        double prixTotal = dimension.getArea() * Terrain.prix;
//        if (prixTotal > this.getArgent()) {
//            return false;
//        }
//        return true;
//    }
//
//    public boolean buyTerrain(Connection con, Dimension dimension, Timestamp dateCreation) {
//        if (!checkByTerrain(dimension)) {
//            return false;
//        }
//        Terrain newTerrain = new Terrain();
//        newTerrain.setDate_creation(dateCreation);
//        newTerrain.setUser(this);
//        newTerrain.setDimension(dimension);
//        newTerrain.insert(con);
//
//        double newArgent = this.getArgent() - dimension.getArea()*Terrain.prix;
//        this.setArgent(newArgent);
//        this.updateArgent(con, newArgent);
//
//        return true;
//    }
 
}
