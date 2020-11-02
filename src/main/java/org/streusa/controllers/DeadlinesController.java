package org.streusa.controllers;

import com.cloudant.client.api.Database;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.streusa.entities.Deadline;
import org.streusa.services.DatabaseService;

import java.util.List;

@RestController
@RequestMapping("/deadlinesController/api")
public class DeadlinesController {

    //Database deadlinesDB = DatabaseService.getDB("Cloudant-streusa20", "deadlines");

    @GetMapping("/retrieveAllDeadlines")
    public ResponseEntity<List<Deadline>> retrieveAllDeadlines(){

        List<Deadline> deadlineList = null;
        deadlineList = DatabaseService.deadlinesDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Deadline.class).getDocs();

        return ResponseEntity.status(HttpStatus.OK).body(deadlineList);
    }

    @PostMapping("/insertDeadline")
    public ResponseEntity<JSONObject> insertDeadline(@RequestBody Deadline newDeadline){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.deadlinesDB.post(newDeadline);
            jo.put("message", "Nuovo evento inserito correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/modifyDeadline")
    public ResponseEntity<JSONObject> modifyDeadline(@RequestBody Deadline editDeadline){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.deadlinesDB.update(editDeadline);
            jo.put("message", "Evento modificato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/deleteDeadline")
    public ResponseEntity<JSONObject> deleteDeadline(@RequestBody Deadline deleteDeadline){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.deadlinesDB.remove(deleteDeadline.get_id(), deleteDeadline.get_rev());
            jo.put("message", "Evento eliminato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }
}
