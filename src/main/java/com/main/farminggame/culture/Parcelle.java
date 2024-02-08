package com.main.farminggame.culture;

import com.main.farminggame.dimension.Dimension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Parcelle {
    String idParcelle;
    Terrain terrain;
    CategorieCulture categorie;
    Dimension dimension;
    Culture culture;
    int niveau_culture;

    public Parcelle(){}

    public Dimension getDimension() {
        return dimension;
    }
    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
    public String getIdParcelle() {
        return idParcelle;
    }
    public void setIdParcelle(String idParcelle) {
        this.idParcelle = idParcelle;
    }
    public Terrain getTerrain() {
        return terrain;
    }
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
    public CategorieCulture getCategorie() {
        return categorie;
    }
    public void setCategorie(CategorieCulture categorie) {
        this.categorie = categorie;
    }
    public Culture getCulture() {
        return culture;
    }
    public void setCulture(Culture culture) {
        this.culture = culture;
    }
    public int getNiveau_culture() {
        return niveau_culture;
    }
    public void setNiveau_culture(int niveau_culture) {
        this.niveau_culture = niveau_culture;
    }

    public boolean insert(Connection con) {
        String sql = "insert into parcelle values (default, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, this.getTerrain().getIdTerrain());
            st.setString(2, this.getCategorie().getIdCategorieCulture());
            st.setInt(3, this.getDimension().getaX());
            st.setInt(4, this.getDimension().getaY());
            st.setInt(5, this.getDimension().getbX());
            st.setInt(6, this.getDimension().getbY());

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

    public static Parcelle[] get_parcelle_by_terrain(Connection con, String idTerrain) {
        Parcelle[] list = null;
        try {
            String req = "select * from v_parc_cat where id_terrain = ?";
            PreparedStatement s = con.prepareStatement(req);
            s.setString(1, idTerrain);
            ResultSet rs = s.executeQuery(req);
            Vector v = new Vector<>();
            while (rs.next()) {
                Parcelle newParcelle = new Parcelle();
                newParcelle.setIdParcelle(rs.getString("id"));
                newParcelle.setCategorie(new CategorieCulture(rs.getString("id_categorie"), rs.getString("categorie_designation"), rs.getDouble("categorie_prix")));
                newParcelle.setCulture(new Culture(rs.getString("id_culture"), null, rs.getString("culture_designation"), rs.getDouble("prix_planter"), rs.getDouble("prix_recolter")));
                newParcelle.setDimension(new Dimension(rs.getInt("ax"), rs.getInt("ay"), rs.getInt("bx"), rs.getInt("by")));
                v.add(newParcelle);
            }
            list = new Parcelle[v.size()];
            v.copyInto(list);
            s.close();
            con.close();
            return list;
        } catch (Exception e) {
        }
        return list;
    }
}
