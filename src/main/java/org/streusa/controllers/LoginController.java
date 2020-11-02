package org.streusa.controllers;

import com.cloudant.client.api.Database;
import net.minidev.json.JSONObject;
import net.sf.json.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.streusa.entities.Book;
import org.streusa.entities.User;
import org.streusa.services.DatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class LoginController {

    //Database usersDB = DatabaseService.getDB("Cloudant-streusa20", "users");

    @PostMapping("/userLogin")
    public ResponseEntity<JSONObject> login(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();
        HttpSession session = request.getSession();
        try{
            List<User> usersList = null;
            usersList = DatabaseService.usersDB.query("{\n" +
                    "   \"selector\": {\n" +
                    "      \"email\": \""+user.getEmail()+"\",\n" +
                    "      \"password\": \""+user.getPassword()+"\"\n" +
                    "   }\n" +
                    "}", User.class).getDocs();
            if(usersList.size() > 0){
                session.setAttribute("authenticated", true);
                jo.put("message", "OK");
                for(User userDb: usersList){
                    jo.put("profileName", userDb.getNome() + " " + userDb.getCognome());
                    session.setAttribute("role", userDb.getRuolo());
                }
            }else{
                jo.put("message", "Username o Password errate.");
            }
        }catch (Exception e){
            e.printStackTrace();
            jo.put("message", "Errore: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }
}
