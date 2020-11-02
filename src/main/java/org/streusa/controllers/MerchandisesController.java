package org.streusa.controllers;

import com.cloudant.client.api.Database;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.streusa.entities.Book;
import org.streusa.entities.Merchandise;
import org.streusa.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/merchandisesController/api")
public class MerchandisesController {

    //Database merchandisesDB = DatabaseService.getDB("Cloudant-streusa20", "merchandises");

    @GetMapping("/retrieveAllMerchandises/{comingFrom}")
    public ResponseEntity<List<Merchandise>> retrieveAllMerchandises(@PathVariable String comingFrom){

        List<Merchandise> merchandisesList = null;
        merchandisesList = DatabaseService.merchandisesDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Merchandise.class).getDocs();

        List<Merchandise> availableMerchandises = new ArrayList<>();
        for(Merchandise singleMerchandise: merchandisesList){
            if(singleMerchandise.getQuantity() > 0){
                availableMerchandises.add(singleMerchandise);
            }
        }
        if(comingFrom.equals("myShop")){
            return ResponseEntity.status(HttpStatus.OK).body(availableMerchandises);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(merchandisesList);
        }

    }

    @PostMapping("/insertMerchandise")
    public ResponseEntity<JSONObject> insertMerchandise(@RequestBody Merchandise newMerchandise){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.merchandisesDB.post(newMerchandise);
            jo.put("message", "Nuovo articolo inserito correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/modifyMerchandise")
    public ResponseEntity<JSONObject> modifyMerchandise(@RequestBody Merchandise editMerchandise){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.merchandisesDB.update(editMerchandise);
            jo.put("message", "Articolo modificato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/deleteMerchandise")
    public ResponseEntity<JSONObject> deleteMerchandise(@RequestBody Merchandise deleteMerchandise){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.merchandisesDB.remove(deleteMerchandise.get_id(), deleteMerchandise.get_rev());
            jo.put("message", "Articolo eliminato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }
}
