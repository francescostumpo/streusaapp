package org.streusa.jobs;

import com.cloudant.client.api.Database;
import org.springframework.stereotype.Component;
import org.streusa.entities.Book;
import org.streusa.services.DatabaseService;
import org.streusa.services.MailService;

import java.util.ArrayList;
import java.util.List;

@Component
public class VerifyGiacenciesJob {

    public void startJob() {

        Database booksDB = DatabaseService.getDB("Cloudant-streusa20", "books");
        List<Book> booksList = null;
        booksList = booksDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Book.class).getDocs();

        List<Book> notAvailableBooks = new ArrayList<>();
        for(Book singleBook: booksList){
            if(singleBook.getQuantity() < 1){
                notAvailableBooks.add(singleBook);
            }
        }

        String subject = "[STREUSA] - LIBRI NON IN GIACENZA";
        String body = null;
        if(notAvailableBooks.isEmpty()){
            body = "Buongiorno,\r\nAl momento tutti i libri inventariati hanno giacenza maggiore di 0.\r\n" +" \r\n";
        }else{
            String booksNotInGiacency = "";
            for(Book book: notAvailableBooks){
                booksNotInGiacency = booksNotInGiacency + "- Titolo: "+book.getTitle() + ", Editore: "+book.getEditor() + ", Fornitore: " + book.getSupplier() + ";\r\n";
            }
            body = "Buongiorno,\r\n"+" \r\n"+"Al momento i libri non a giacenza sono i seguenti:\r\n"+" \r\n"+booksNotInGiacency+"\r\n";
        }
        body = body + "Questo è un messaggio automatico. Si prega di non rispondere.";

        MailService mailService = new MailService();
        mailService.sendEmail("SuperUser", subject, body);
    }
}
