package org.streusa.controllers;

import com.cloudant.client.api.Database;
import net.minidev.json.JSONObject;
import net.sf.json.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.streusa.entities.User;
import org.streusa.services.DatabaseService;

import java.util.List;

@RestController
@RequestMapping("/usersController/api")
public class UsersController {


    @GetMapping("/retrieveAllUsers")
    public ResponseEntity<List<User>> retrieveAllUsers(){

        List<User> usersList = null;
        usersList = DatabaseService.usersDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", User.class).getDocs();

        return ResponseEntity.status(HttpStatus.OK).body(usersList);
    }

    @PostMapping("/insertUser")
    public ResponseEntity<JSONObject> insertUser(@RequestBody User newUser){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.usersDB.post(newUser);
            jo.put("message", "Nuovo utente inserito correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/modifyUser")
    public ResponseEntity<JSONObject> modifyUser(@RequestBody User editUser){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.usersDB.update(editUser);
            jo.put("message", "Utente modificato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }
}
