package org.streusa.controllers;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.streusa.entities.Book;
import org.streusa.entities.Genre;
import org.streusa.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/inventoryController/api")
public class InventoryController {

    @GetMapping("/resetBooksQuantities")
    public ResponseEntity<Object> resetBookQuantities() throws InterruptedException {

        List<Book> booksList = null;
        booksList = DatabaseService.booksDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Book.class).getDocs();

        log.info(booksList.size() + "");
        List<JSONObject> booksReset = new ArrayList<>();
        for(Book book: booksList){
            log.info("Title {} - Quantity {}", book.getTitle(), book.getQuantity());
            book.setQuantity(0);
            DatabaseService.booksDB.update(book);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", book.getTitle());
            jsonObject.put("quantity", book.getQuantity());
            booksReset.add(jsonObject);
            Thread.sleep(210);
        }

        return ResponseEntity.status(HttpStatus.OK).body(booksReset);
    }

}
