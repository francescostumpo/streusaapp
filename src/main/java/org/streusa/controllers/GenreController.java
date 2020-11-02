package org.streusa.controllers;

import com.cloudant.client.api.Database;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.streusa.entities.Genre;
import org.streusa.services.DatabaseService;

import java.util.List;

@RestController
@RequestMapping("/genresController/api")
public class GenreController {

    //Database genresDB = DatabaseService.getDB("Cloudant-streusa20", "genres");

    @GetMapping("/retrieveAllGenres")
    public ResponseEntity<List<Genre>> retrieveAllGenres(){

        List<Genre> genresList = null;
        genresList = DatabaseService.genresDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Genre.class).getDocs();

        return ResponseEntity.status(HttpStatus.OK).body(genresList);
    }

    @PostMapping("/insertGenre")
    public ResponseEntity<JSONObject> insertGenre(@RequestBody Genre newGenre){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.genresDB.post(newGenre);
            jo.put("message", "Nuovo genere inserito correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/modifyGenre")
    public ResponseEntity<JSONObject> modifyGenre(@RequestBody Genre editGenre){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.genresDB.update(editGenre);
            jo.put("message", "Genere modificato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }
}