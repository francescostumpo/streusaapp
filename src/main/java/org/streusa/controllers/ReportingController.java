package org.streusa.controllers;

import com.google.gson.JsonObject;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.streusa.entities.Book;
import org.streusa.entities.Purchase;
import org.streusa.entities.User;
import org.streusa.services.DatabaseService;
import org.streusa.services.ExcelWriterService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reportingController/api")
public class ReportingController {

    @GetMapping("/retrieveAllDBData")
    public ResponseEntity<List<JSONObject>> retrieveAllDBData(){

        List<JSONObject> purchasesList = null;

        purchasesList = DatabaseService.purchasesDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", JSONObject.class).getDocs();

        return ResponseEntity.status(HttpStatus.OK).body(purchasesList);
    }

    @GetMapping("/retrievePrice")
    public ResponseEntity<String> retrievePrice(){

        List<Purchase> purchasesList = null;

        purchasesList = DatabaseService.purchasesDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Purchase.class).getDocs();
        String price = null;
        for(Purchase purchase: purchasesList){
            price = String.valueOf(purchase.getFinalTotal());
        }

        return ResponseEntity.status(HttpStatus.OK).body(price);
    }

    @GetMapping("/extractTodayData")
    public ResponseEntity<InputStreamResource> extractTodayData(){

        List<Purchase> purchasesList = null;

        purchasesList = DatabaseService.purchasesDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Purchase.class).getDocs();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExcelWriterService excelWS = new ExcelWriterService();
        JsonObject jo = new JsonObject();
        jo.addProperty("startDate", "today");
        XSSFWorkbook workbook = excelWS.writeReport(purchasesList, jo);

        try {
            workbook.write(baos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        InputStreamResource re = new InputStreamResource(is);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Estrazione.xlsx\"")
                .body(re);
    }

    @PostMapping("/extractTimeFrame")
    public ResponseEntity<InputStreamResource> extractTimeFrame(@RequestBody net.minidev.json.JSONObject jsonObject){

        System.out.println("JSON received: "+ jsonObject.toString());
        List<Purchase> purchasesList = null;

        purchasesList = DatabaseService.purchasesDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Purchase.class).getDocs();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExcelWriterService excelWS = new ExcelWriterService();

        JsonObject jo = new JsonObject();
        String startDate = jsonObject.getAsString("startDate").split("T")[0];
        String[] startDateArray = startDate.split("-");
        int startDay = Integer.valueOf(startDateArray[2]) +1;
        String finalStartDate = startDateArray[0] + "-" + startDateArray[1] + "-" + startDay;

        String endDate = jsonObject.getAsString("endDate").split("T")[0];
        String[] endDateArray = endDate.split("-");
        int endDay = Integer.valueOf(endDateArray[2]) +1;
        String finalEndDate = endDateArray[0] + "-" + endDateArray[1] + "-" + endDay;

        jo.addProperty("startDate", finalStartDate);
        jo.addProperty("endDate", finalEndDate);

        System.out.println("StartDateRefactored: " + finalStartDate);
        System.out.println("EndDateRefactored: " + finalEndDate);

        XSSFWorkbook workbook = excelWS.writeReport(purchasesList, jo);

        try {
            workbook.write(baos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        InputStreamResource re = new InputStreamResource(is);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Estrazione.xlsx\"")
                .body(re);
    }

    @PostMapping("/extractBooksLinkedToSupplier")
    public ResponseEntity<InputStreamResource> extractBooksLinkedToSupplier(@RequestBody net.minidev.json.JSONObject jsonObject){

        String supplier = jsonObject.getAsString("supplier");
        List<Book> booksList = null;
        booksList = DatabaseService.booksDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"supplier\": \""+supplier+"\"\n" +
                "   }\n" +
                "}", Book.class).getDocs();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExcelWriterService excelWS = new ExcelWriterService();
        XSSFWorkbook workbook = excelWS.writeSheet(supplier, booksList);

        try {
            workbook.write(baos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        InputStreamResource re = new InputStreamResource(is);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+supplier+".xlsx\"")
                .body(re);
    }

    @PostMapping("/extractBooksLinkedToEditor")
    public ResponseEntity<InputStreamResource> extractBooksLinkedToEditor(@RequestBody net.minidev.json.JSONObject jsonObject){

        String editor = jsonObject.getAsString("editor");
        List<Book> booksList = null;
        booksList = DatabaseService.booksDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"editor\": \""+editor+"\"\n" +
                "   }\n" +
                "}", Book.class).getDocs();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExcelWriterService excelWS = new ExcelWriterService();
        XSSFWorkbook workbook = excelWS.writeSheet(editor, booksList);

        try {
            workbook.write(baos);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        InputStreamResource re = new InputStreamResource(is);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+editor+".xlsx\"")
                .body(re);
    }

}
