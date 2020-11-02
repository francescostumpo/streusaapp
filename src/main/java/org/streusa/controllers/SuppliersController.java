package org.streusa.controllers;

import com.cloudant.client.api.Database;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.streusa.entities.Editor;
import org.streusa.entities.Supplier;
import org.streusa.services.DatabaseService;

import java.util.List;
@RestController
@RequestMapping("/suppliersController/api")
public class SuppliersController {

    //Database suppliersDB = DatabaseService.getDB("Cloudant-streusa20", "suppliers");

    @GetMapping("/retrieveAllSuppliers")
    public ResponseEntity<List<Supplier>> retrieveAllEditors(){

        List<Supplier> suppliersList = null;
        suppliersList = DatabaseService.suppliersDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Supplier.class).getDocs();

        return ResponseEntity.status(HttpStatus.OK).body(suppliersList);
    }

    @PostMapping("/insertSupplier")
    public ResponseEntity<JSONObject> insertSupplier(@RequestBody Supplier newSupplier){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.suppliersDB.post(newSupplier);
            jo.put("message", "Nuovo fornitore inserito correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/modifySupplier")
    public ResponseEntity<JSONObject> modifySupplier(@RequestBody Supplier editSupplier){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.suppliersDB.update(editSupplier);
            jo.put("message", "Fornitore modificato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }
}
