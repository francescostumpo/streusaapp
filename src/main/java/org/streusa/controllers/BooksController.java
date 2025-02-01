package org.streusa.controllers;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.streusa.entities.Book;
import org.streusa.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/booksController/api")
public class BooksController {

    //Database booksDB = DatabaseService.getDB("Cloudant-streusa20", "books");

    @GetMapping("/retrieveAllBooks/{comingFrom}")
    public ResponseEntity<List<Book>> retrieveAllBooks(@PathVariable String comingFrom){

        List<Book> booksList = null;
        booksList = DatabaseService.booksDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Book.class).getDocs();

        List<Book> availableBooks = new ArrayList<>();
        for(Book singleBook: booksList){
            if(singleBook.getQuantity() > 0){
                availableBooks.add(singleBook);
            }
        }
        if(comingFrom.equals("myShop")){
            return ResponseEntity.status(HttpStatus.OK).body(availableBooks);
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(booksList);
        }

    }

    @PostMapping("/insertBook")
    public ResponseEntity<JSONObject> insertBook(@RequestBody Book newBook){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.booksDB.post(newBook);
            jo.put("message", "Nuovo libro inserito correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/modifyBook")
    public ResponseEntity<JSONObject> modifyBook(@RequestBody Book editBook){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.booksDB.update(editBook);
            jo.put("message", "Libro modificato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/deleteBook")
    public ResponseEntity<JSONObject> deleteBook(@RequestBody Book deleteBook){
        JSONObject jo = new JSONObject();
        try{
            DatabaseService.booksDB.remove(deleteBook.get_id(), deleteBook.get_rev());
            jo.put("message", "Libro eliminato correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }

    @PostMapping("/deleteSelectedBooks/booksAdmin")
    public ResponseEntity<JSONObject> deleteSelectedBooks(@RequestBody List<Book> booksToDelete){
        JSONObject jo = new JSONObject();
        try{
            if(booksToDelete.isEmpty()){
                ResponseEntity.status(HttpStatus.NO_CONTENT);
            }
            booksToDelete.forEach(book -> DatabaseService.booksDB.remove(book.get_id(), book.get_rev()));
            jo.put("message", "Libri eliminati correttamente");
        }catch (Exception e){
            jo.put("message", "Errore: " +e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jo);
    }


}
