<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="streusaApp"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>STREUSA 2.0 - MyLibraryApp</title>

    <jsp:include page="subviews/cssSheets.jsp"></jsp:include>

</head>

<body id="page-top" ng-app="streusaApp">

<!-- Page Wrapper -->
<div id="wrapper">

    <jsp:include page="subviews/sidebar.jsp"></jsp:include>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">
            <jsp:include page="subviews/loader.jsp"></jsp:include>
            <jsp:include page="subviews/topbar.jsp"></jsp:include>

            <!-- Begin Page Content -->
            <div class="container-fluid" ng-controller="booksController">

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">Amministrazione - Libri</h1>
                <p class="lead">Da questo pannello, gestisci i libri di STREUSA 2.0</p>

                <div class="row col-md-12 col-lg-12">
                    <button class="btn btn-outline-primary mr-3" ng-click="setAllBooksView()">Tutti</button>
                    <button class="btn btn-outline-primary ml-3" ng-click="setNewBookView()">Nuovo</button>
                </div>
                <hr>
                <div class="row" ng-show="newBookView">
                    <div class="card col-md-8 col-lg-8">
                        <div class="card-header">
                            Nuovo Libro
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-md-6 pr-md-1">
                                        <p>Carica informazioni da Google</p>
                                    </div>
                                    <div class="col-md-4 pl-md-1">
                                        <div class="form-group">
                                            <input type="text" class="form-control" ng-model="isbn" placeholder="ISBN">
                                        </div>
                                    </div>
                                    <div class="col-md-2 pl-md-1">
                                        <button type="submit" class="btn btn-primary" ng-click="findBook(isbn)">Cerca</button>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col-md-8 pr-md-1">
                                        <div class="form-group">
                                            <label>Titolo</label>
                                            <input type="text" class="form-control" ng-model="newBook.title">
                                        </div>
                                    </div>
                                    <div class="col-md-4 pl-md-1">
                                        <div class="form-group">
                                            <label>Autore</label>
                                            <input type="text" class="form-control" ng-model="newBook.author">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8 pr-md-1">
                                        <div class="form-group">
                                            <label>Editore</label>
                                            <select type="text" ng-init="newBook.editor = editors[0].nome" class="form-control" ng-model="newBook.editor">
                                                <option ng-repeat="editor in editors" value="{{editor.nome}}"> {{ editor.nome }} </option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2 px-md-1">
                                        <div class="form-group">
                                            <label>Prezzo (euro)</label>
                                            <input type="number" step="0.01" min="0" class="form-control" ng-model="newBook.price">
                                        </div>
                                    </div>
                                    <div class="col-md-2 pl-md-1">
                                        <div class="form-group">
                                            <label>Quantita</label>
                                            <input type="number" class="form-control" ng-model="newBook.quantity">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 pr-md-1">
                                        <div class="form-group">
                                            <label>Fornitore</label>
                                            <select type="text" ng-init="newBook.supplier = suppliers[0].nome" class="form-control" ng-model="newBook.supplier">
                                                <option ng-repeat="supplier in suppliers" value="{{supplier.nome}}"> {{ supplier.nome }} </option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-4 pr-md-1">
                                        <label>Genere</label>
                                        <select type="text" ng-init="newBook.genre = genres[0].nome" class="form-control" ng-model="newBook.genre">
                                            <option ng-repeat="genre in genres" value="{{genre.nome}}"> {{ genre.nome }} </option>
                                        </select>
                                    </div>
                                    <div class="col-md-4 pl-md-1">
                                        <div class="form-group">
                                            <label>Barcode</label>
                                            <input type="text" class="form-control" ng-model="newBook.barCode">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary" ng-click="insertNewBook(newBook)">Inserisci</button>
                        </div>
                    </div>
                </div>

                <div class="row" ng-show="allBooksView">
                    <div class="card col-md-8 col-lg-8" ng-init="retrieveAllBooks()">
                        <div class="card-header">
                            Tutti i Libri
                        </div>
                        <table datatable="ng" class="row-border hover">
                            <thead>
                            <tr>
                                <th width="40%">Titolo</th>
                                <th width="20%">Autore</th>
                                <th width="20%">Fornitore</th>
                                <th width="10%">Qt.</th>
                                <th width="5%"></th>
                                <th width="5%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="book in books" ng-class="checkGiacenza(book)">
                                <td>{{book.title}}</td>
                                <td>{{book.author}}</td>
                                <td>{{book.supplier}}</td>
                                <td>{{book.quantity}}</td>
                                <td><i class="far fa-edit" style="cursor: pointer;" ng-click="setEditBookView(book)"></i></td>
                                <td><i class="fa fa-trash" style="cursor: pointer;" ng-click="deleteBook(book)"></i></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="card col-md-4 col-lg-4" ng-show="editBookView">
                        <div class="card-header">
                            Modifica Libro
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Titolo</label>
                                            <input type="text" class="form-control" ng-model="editBook.title">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Autore</label>
                                            <input type="text" class="form-control" ng-model="editBook.author">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Editore</label>
                                            <select type="text" ng-init="editBook.editor" class="form-control" ng-model="editBook.editor">
                                                <option ng-repeat="editor in editors" value="{{editor.nome}}"> {{ editor.nome }} </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Prezzo (euro)</label>
                                            <input type="number" step="0.01" min="0" class="form-control" ng-model="editBook.price">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Quantita</label>
                                            <input type="number" class="form-control" ng-model="editBook.quantity">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Fornitore</label>
                                            <select type="text" ng-init="editBook.supplier" class="form-control" ng-model="editBook.supplier">
                                                <option ng-repeat="supplier in suppliers" value="{{supplier.nome}}"> {{ supplier.nome }} </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Genere</label>
                                            <select type="text" ng-init="editBook.genre" class="form-control" ng-model="editBook.genre">
                                                <option ng-repeat="genre in genres" value="{{genre.nome}}"> {{ genre.nome }} </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Barcode</label>
                                            <input type="text" class="form-control" ng-model="editBook.barCode">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary" ng-click="modifyBook(editBook)">Salva</button>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Your Website 2019</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<jsp:include page="subviews/logoutModal.jsp"></jsp:include>

<jsp:include page="subviews/scripts.jsp"></jsp:include>
<script type="text/javascript" src="./webapp/AngularJS/controllers/booksController.js"></script>

</body>

</html>
