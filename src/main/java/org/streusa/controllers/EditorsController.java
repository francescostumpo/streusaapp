package org.streusa.controllers;

import com.cloudant.client.api.Database;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.streusa.entities.Book;
import org.streusa.entities.Editor;
import org.streusa.services.DatabaseService;

import java.util.List;

@RestController
@RequestMapping("/editorsController/api")
public class EditorsController {

    //Database editorsDB = DatabaseService.getDB("Cloudant-streusa20", "editors");

    @GetMapping("/retrieveAllEditors")
    public ResponseEntity<List<Editor>> retrieveAllEditors(){

        List<Editor> editorsList = null;
        editorsList = DatabaseService.editorsDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Editor.class).getDocs();

        return ResponseEntity.status(HttpStatus.OK).body(editorsList);
    }

    @PostMapping("/insertEditor")
    public ResponseEntity<JSONObject> insertEditor(@RequestBody Editor newEditor){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.editorsDB.post(newEditor);
            jo.put("message", "Nuovo editore inserito correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/modifyEditor")
    public ResponseEntity<JSONObject> modifyEditor(@RequestBody Editor editEditor){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.editorsDB.update(editEditor);
            jo.put("message", "Editore modificato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }
}
