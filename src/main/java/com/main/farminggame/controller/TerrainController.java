package com.main.farminggame.controller;

import com.main.farminggame.connection.Connectiondb;
import com.main.farminggame.culture.Parcelle;
import com.main.farminggame.culture.Terrain;
import com.main.farminggame.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class TerrainController {
    @GetMapping("/terrain")
    @ResponseBody
    public Terrain[] getAllTerrain() throws SQLException {
        Connection con = Connectiondb.connect();
        Terrain[] result = Terrain.getAllTerrain(con);
        con.close();
        return result;
    }

    @GetMapping("/terrain/{id}/parcelle")
    @ResponseBody
    public Parcelle[] getParcelleTerrain(@PathVariable("id") String id) throws SQLException {
        Connection con = Connectiondb.connect();
        Parcelle[] result = Parcelle.get_parcelle_by_terrain(con, id);
        con.close();
        return result;
    }
}
