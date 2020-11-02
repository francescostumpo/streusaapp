package org.streusa.services;

import com.cloudant.client.api.Database;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFAnchor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.streusa.entities.Book;
import org.streusa.entities.Purchase;
import org.streusa.entities.Supplier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExcelWriterService {

    private int rowCountShoppingCart = 0;
    private int rowCountBooks = 0;
    private int rowCountMerchandise = 0;
    private int rowCountBooksReport = 0;
    List<Supplier> suppliersList = null;

    public XSSFWorkbook writeReport(List<Purchase> purchasesList, JsonObject jo) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        suppliersList = DatabaseService.suppliersDB.query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"0\"\n" +
                "      }\n" +
                "   }\n" +
                "}", Supplier.class).getDocs();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet shoppingCartSheet = wb.createSheet("Carrelli");
        XSSFSheet booksSheet = wb.createSheet("Libri");
        XSSFSheet merchandisesSheet = wb.createSheet("Merchandise");
        createShoppingCartHeaderRow(shoppingCartSheet);
        createBooksHeaderRow(booksSheet);
        createMerchandiseHeaderRow(merchandisesSheet);


        for(Purchase purchase: purchasesList){
            if(jo.get("startDate").getAsString().equals("today")){
                String dateToBenchMark = dateFormat.format(new Date());
                String purchaseDate = dateFormat.format(purchase.getPurchaseDate());
                if(dateToBenchMark.equals(purchaseDate)){

                    createShoppingCartRow(shoppingCartSheet, purchase);
                    createBooksRow(booksSheet, purchase);
                    createMerchandiseRow(merchandisesSheet, purchase);


                }
            }else{
                try {
                    Date startDate = dateFormat.parse(jo.get("startDate").getAsString());
                    Date endDate = dateFormat.parse(jo.get("endDate").getAsString());
                    long startDiffInMillis = purchase.getPurchaseDate().getTime() - startDate.getTime();
                    long endDiffInMillis =  endDate.getTime() - purchase.getPurchaseDate().getTime();

                    long  startDiff = TimeUnit.DAYS.convert(startDiffInMillis, TimeUnit.MILLISECONDS);
                    long endDiff = TimeUnit.DAYS.convert(endDiffInMillis, TimeUnit.MILLISECONDS);

                    if(startDiff >= 0 && endDiff >=0){

                        createShoppingCartRow(shoppingCartSheet, purchase);
                        createBooksRow(booksSheet, purchase);
                        createMerchandiseRow(merchandisesSheet, purchase);

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }
        return wb;
    }


    private void createShoppingCartHeaderRow(XSSFSheet shoppingCartSheet) {
        Row headerRow = shoppingCartSheet.createRow(rowCountShoppingCart);
        Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue("ID CARRELLO");
        Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
        cell1.setCellValue("DATA DI ACQUISTO");
        Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
        cell2.setCellValue("QT. LIBRI ACQUISTATA");
        Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
        cell3.setCellValue("QT. MERCHANDISE ACQUISTATA");
        Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
        cell4.setCellValue("PREZZO TOTALE LIBRI (€)");
        Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
        cell5.setCellValue("PREZZO TOTALE MERCHANDISE (€)");
        Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
        cell6.setCellValue("PREZZO TOTALE CARRELLO (€)");
        rowCountShoppingCart++;
    }

    private void createShoppingCartRow(XSSFSheet shoppingCartSheet, Purchase purchase) {
        DateFormat dateFormatExcel = new SimpleDateFormat("dd-MM-yyy");
        Row row = shoppingCartSheet.createRow(rowCountShoppingCart);
        Cell cell = row.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue(purchase.get_id());
        Cell cell1 = row.createCell(CellReference.convertColStringToIndex("B"));
        cell1.setCellValue(dateFormatExcel.format(purchase.getPurchaseDate()));

        JsonArray jab = purchase.getBooks();
        double priceBooks = 0;
        int quantityBooks = 0;
        for(JsonElement je: jab){
            JsonObject job = je.getAsJsonObject();
            quantityBooks = quantityBooks + job.get("quantity").getAsInt();
            priceBooks = priceBooks + job.get("price").getAsDouble();
        }

        JsonArray jam = purchase.getMerchandises();
        double priceMerchs = 0;
        int quantityMerchs = 0;
        for(JsonElement je: jam){
            JsonObject job = je.getAsJsonObject();
            quantityMerchs = quantityMerchs + job.get("quantity").getAsInt();
            priceMerchs = priceMerchs + job.get("price").getAsDouble();
        }

        Cell cell2 = row.createCell(CellReference.convertColStringToIndex("C"));
        cell2.setCellValue(quantityBooks);
        Cell cell3 = row.createCell(CellReference.convertColStringToIndex("D"));
        cell3.setCellValue(quantityMerchs);
        Cell cell4 = row.createCell(CellReference.convertColStringToIndex("E"));
        cell4.setCellValue(priceBooks);
        Cell cell5 = row.createCell(CellReference.convertColStringToIndex("F"));
        cell5.setCellValue(priceMerchs);
        Cell cell6 = row.createCell(CellReference.convertColStringToIndex("G"));
        cell6.setCellValue(purchase.getFinalTotal());
        rowCountShoppingCart++;
    }

    private void createBooksHeaderRow(XSSFSheet booksSheet) {
        Row headerRow = booksSheet.createRow(rowCountBooks);
        Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue("ID CARRELLO");
        Cell cell0 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
        cell0.setCellValue("DATA DI ACQUISTO");
        Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
        cell1.setCellValue("TITOLO");
        Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
        cell2.setCellValue("AUTORE");
        Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
        cell3.setCellValue("EDITORE");
        Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
        cell4.setCellValue("FORNITORE");
        Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
        cell5.setCellValue("PREZZO (€)");
        Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("H"));
        cell6.setCellValue("QUANTITA");
        Cell cell7 = headerRow.createCell(CellReference.convertColStringToIndex("I"));
        cell7.setCellValue("MARGINE (%)");
        Cell cell8 = headerRow.createCell(CellReference.convertColStringToIndex("J"));
        cell8.setCellValue("UTILE (€)");
        rowCountBooks++;
    }

    private void createBooksRow(XSSFSheet booksSheet, Purchase purchase) {
        DateFormat dateFormatExcel = new SimpleDateFormat("dd-MM-yyy");
        JsonArray ja = purchase.getBooks();

        if(ja.size()>0){
            for(JsonElement je : ja){
                JsonObject job = je.getAsJsonObject();
                try {
                    Row headerRow = booksSheet.createRow(rowCountBooks);
                    Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
                    cell.setCellValue(purchase.get_id());
                    Cell cell0 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
                    cell0.setCellValue(dateFormatExcel.format(purchase.getPurchaseDate()));
                    Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
                    cell1.setCellValue(job.get("nome").getAsString() != null ? job.get("nome").getAsString() : "");
                    Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
                    cell2.setCellValue(job.get("author").getAsString() != null ? job.get("author").getAsString() : "");
                    Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
                    cell3.setCellValue(job.get("editor").getAsString() != null ? job.get("editor").getAsString() : "");
                    Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
                    cell4.setCellValue(job.get("supplier").getAsString() != null ? job.get("supplier").getAsString() : "");
                    Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
                    cell5.setCellValue(job.get("price").getAsDouble());
                    Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("H"));
                    cell6.setCellValue(job.get("quantity").getAsInt());

                    double margin = 0;
                    for (Supplier supplier : suppliersList) {
                        if (supplier.getNome().equals(job.get("supplier").getAsString())) {
                            margin = Double.valueOf(supplier.getGp());
                        }
                    }
                    Cell cell7 = headerRow.createCell(CellReference.convertColStringToIndex("I"));
                    cell7.setCellValue(margin);

                    double util = 0;
                    if (margin != 0) {
                        util = margin * job.get("price").getAsDouble() * job.get("quantity").getAsInt();
                    }
                    Cell cell8 = headerRow.createCell(CellReference.convertColStringToIndex("J"));
                    cell8.setCellValue(util);
                    rowCountBooks++;
                }catch (Exception e){
                    //System.err.println("Exception: " + e.getMessage());
                    //e.printStackTrace();
                }
            }

        }
    }


    private void createMerchandiseHeaderRow(XSSFSheet merchandiseSheet) {
            Row headerRow = merchandiseSheet.createRow(rowCountMerchandise);
            Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
            cell.setCellValue("ID CARRELLO");
            Cell cell0 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
            cell0.setCellValue("DATA DI ACQUISTO");
            Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
            cell1.setCellValue("ARTICOLO");
            Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
            cell2.setCellValue("FORNITORE");
            Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
            cell3.setCellValue("PREZZO (€)");
            Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
            cell4.setCellValue("QUANTITA");
            Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
            cell5.setCellValue("IVA (%)");
            Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("H"));
            cell6.setCellValue("MARGINE (%)");
            Cell cell7 = headerRow.createCell(CellReference.convertColStringToIndex("I"));
            cell7.setCellValue("UTILE (€)");
            rowCountMerchandise++;

    }

    private void createMerchandiseRow(XSSFSheet merchandiseSheet, Purchase purchase) {
        DateFormat dateFormatExcel = new SimpleDateFormat("dd-MM-yyy");
        JsonArray ja = purchase.getMerchandises();

        if(ja.size()>0){
            for(JsonElement je : ja){
                JsonObject job = je.getAsJsonObject();
                try {
                    Row headerRow = merchandiseSheet.createRow(rowCountMerchandise);
                    Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
                    cell.setCellValue(purchase.get_id());
                    Cell cell0 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
                    cell0.setCellValue(dateFormatExcel.format(purchase.getPurchaseDate()));
                    Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
                    cell1.setCellValue(job.get("nome").getAsString());
                    Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
                    cell2.setCellValue(job.get("provider").getAsString());
                    Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
                    cell3.setCellValue(job.get("price").getAsDouble()); // Modificare
                    Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
                    cell4.setCellValue(job.get("quantity").getAsString());
                    Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
                    cell5.setCellValue(22);
                    Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("H"));
                    cell6.setCellValue(job.get("gp").getAsInt()); // Modificare

                    double margin = job.get("gp").getAsDouble();
                    double price = job.get("price").getAsDouble();
                    int iva = 22;
                    double util = 0;

                    if (margin != 0 && job.get("gp").getAsString() != null && job.get("price").getAsString() != null) {
                        double priceNoIVA = price / (1 + (iva / 100));
                        double priceNoCharge = priceNoIVA / (1 + (margin / 100));
                        util = (priceNoIVA - priceNoCharge) * job.get("quantity").getAsInt();
                    }

                    Cell cell8 = headerRow.createCell(CellReference.convertColStringToIndex("I"));
                    cell8.setCellValue(util);


                    rowCountMerchandise++;
                }catch (Exception e){
                    //System.err.println("Exception: " + e.getMessage());
                    //e.printStackTrace();
                }
            }

        }
    }

    public XSSFWorkbook writeSheet(String item, List<Book> booksList)  {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet booksReportSheet = wb.createSheet(item);
        createBooksReportHeaderRow(booksReportSheet);

        for(Book book: booksList){
            createBooksReportRow(booksReportSheet, book);
        }
        return wb;
    }

    private void createBooksReportHeaderRow(XSSFSheet booksReportSheet) {
        Row headerRow = booksReportSheet.createRow(rowCountBooksReport);
        Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue("TITOLO");
        Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
        cell1.setCellValue("AUTORE");
        Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
        cell2.setCellValue("EDITORE");
        Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
        cell3.setCellValue("FORNITORE");
        Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
        cell4.setCellValue("GENERE");
        Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
        cell5.setCellValue("PREZZO");
        Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
        cell6.setCellValue("QUANTITA");
        rowCountBooksReport++;
    }

    private void createBooksReportRow(XSSFSheet booksReportSheet, Book book) {
        Row headerRow = booksReportSheet.createRow(rowCountBooksReport);
        Cell cell = headerRow.createCell(CellReference.convertColStringToIndex("A"));
        cell.setCellValue(book.getTitle());
        Cell cell1 = headerRow.createCell(CellReference.convertColStringToIndex("B"));
        cell1.setCellValue(book.getAuthor());

        String editor = "";
        if(book.getEditor() != null){
            editor = book.getEditor();
        }
        Cell cell2 = headerRow.createCell(CellReference.convertColStringToIndex("C"));
        cell2.setCellValue(editor);

        String supplier = "";
        if(book.getSupplier() != null){
            supplier = book.getSupplier();
        }
        Cell cell3 = headerRow.createCell(CellReference.convertColStringToIndex("D"));
        cell3.setCellValue(supplier);

        String genre = "";
        if(book.getGenre() != null){
            genre = book.getGenre();
        }
        Cell cell4 = headerRow.createCell(CellReference.convertColStringToIndex("E"));
        cell4.setCellValue(genre);
        Cell cell5 = headerRow.createCell(CellReference.convertColStringToIndex("F"));
        cell5.setCellValue(book.getPrice());
        Cell cell6 = headerRow.createCell(CellReference.convertColStringToIndex("G"));
        cell6.setCellValue(book.getQuantity());
        rowCountBooksReport++;
    }

}
