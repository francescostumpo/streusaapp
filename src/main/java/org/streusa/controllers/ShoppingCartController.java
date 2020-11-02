package org.streusa.controllers;

import com.cloudant.client.api.Database;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.streusa.entities.Book;
import org.streusa.entities.Merchandise;
import org.streusa.entities.Purchase;
import org.streusa.services.DatabaseService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/shoppingCartController/api")
public class ShoppingCartController {

    //Database merchandisesDB = DatabaseService.getDB("Cloudant-streusa20", "merchandises");
    //Database booksDB = DatabaseService.getDB("Cloudant-streusa20", "books");
    //Database purchasesDB = DatabaseService.getDB("Cloudant-streusa20", "purchases");
    Gson gson = new Gson();

    @PostMapping("/pay")
    public ResponseEntity<JSONObject> pay(@RequestBody String shoppingCart){
        JSONObject response = new JSONObject();
        Purchase purchase = new Purchase();

        JsonObject jo = gson.fromJson(shoppingCart, JsonObject.class);
        JsonArray ja = jo.get("cart").getAsJsonArray();
        float finalTotal = jo.get("finalTotal").getAsFloat();

        purchase.setFinalTotal(finalTotal);
        purchase.setPurchaseDate(new Date());

        JsonArray books = new JsonArray();
        JsonArray merchandises = new JsonArray();

        for(int i=0; i< ja.size(); i++){

            JsonObject item = ja.get(i).getAsJsonObject();
            if (item.get("itemType").getAsString().equals("book")) {
                books.add(item);
                manageBookPurchase(item);
            }else{
                merchandises.add(item);
                manageMerchandisePurchase(item);
            }
        }
        purchase.setBooks(books);
        purchase.setMerchandises(merchandises);

        DatabaseService.purchasesDB.post(purchase);
        response.put("message", "Carrello processato correttamente");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private void manageBookPurchase(JsonObject item) {
        Book book = DatabaseService.booksDB.find(Book.class, item.get("_id").getAsString());
        int oldQuantity = book.getQuantity();
        book.setQuantity(oldQuantity - item.get("quantity").getAsInt());
        DatabaseService.booksDB.update(book);
    }

    private void manageMerchandisePurchase(JsonObject item) {
        Merchandise merchandise = DatabaseService.merchandisesDB.find(Merchandise.class, item.get("_id").getAsString());
        int oldQuantity = merchandise.getQuantity();
        merchandise.setQuantity(oldQuantity - item.get("quantity").getAsInt());
        DatabaseService.merchandisesDB.update(merchandise);
    }

}
