package com.main.farminggame.controller;

import com.main.farminggame.connection.Connectiondb;
import com.main.farminggame.culture.CategorieCulture;
import com.main.farminggame.culture.Culture;
import com.main.farminggame.culture.Terrain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
public class CultureController {
    @GetMapping("/culture")
    @ResponseBody
    public Culture[] getAllCulture() throws SQLException {
        Connection con = Connectiondb.connect();
        Culture[] result = Culture.getAll(con);
        con.close();
        return result;
    }

    @GetMapping("/culture/{id}")
    @ResponseBody
    public Culture getCultureById(@PathVariable("id") String id) throws SQLException {
        Connection con = Connectiondb.connect();
        Culture result = Culture.getById(con, id);
        con.close();
        return result;
    }
}
