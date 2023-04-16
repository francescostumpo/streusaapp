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
    <jsp:include page="subviews/loader.jsp"></jsp:include>
    <jsp:include page="subviews/sidebar.jsp"></jsp:include>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content" ng-controller="myShopController">


            <jsp:include page="subviews/topbar.jsp"></jsp:include>

            <!-- Begin Page Content -->
            <div class="container-fluid" >

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">MyShop</h1>
                <p class="lead">Da questo pannello, crea la lista della spesa</p>

                <div class="row col-md-12 col-lg-12">
                    <button class="btn btn-outline-primary mr-3" ng-click="setBooksView()">Libri</button>
                    <button class="btn btn-outline-primary ml-3" ng-click="setMerchandiseView()">Merchandise</button>
                </div>
                <hr>
                <div class="row">
                    <div class="card col-md-12 col-lg-12" ng-show="booksView" ng-init="retrieveAllBooks()">
                        <div class="card-header">
                            Tutti i Libri
                        </div>
                        <table datatable="ng" class="row-border hover">
                            <thead>
                            <tr>
                                <th width="60%">Titolo</th>
                                <th width="20%">Autore</th>
                                <th width="10%">Barcode</th>
                                <th width="5%">Prezzo</th>
                                <th width="5%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="book in books">
                                <td>{{book.title}}</td>
                                <td>{{book.author}}</td>
                                <td>{{book.barCode}}</td>
                                <td>{{book.price}}</td>
                                <td><i class="fas fa-plus-circle" style="cursor: pointer;" ng-click="addToAnteprimaView('book', book)"></i></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="card col-md-12 col-lg-12" ng-show="merchandiseView" ng-init="retrieveAllMerchandises()">
                        <div class="card-header">
                            Tutti il Merchandise
                        </div>
                        <table datatable="ng" class="row-border hover">
                            <thead>
                            <tr>
                                <th width="60%">Nome</th>
                                <th width="25%">Fornitore</th>
                                <th width="10%">Prezzo</th>
                                <th width="5%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="merchandise in merchandises">
                                <td>{{merchandise.nome}}</td>
                                <td>{{merchandise.provider}}</td>
                                <td>{{merchandise.price}}</td>
                                <td><i class="fas fa-plus-circle" style="cursor: pointer;" ng-click="addToAnteprimaView('merchandise', merchandise)"></i></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>


                    <div class="card col-md-4 col-lg-4" ng-show="false">
                        <div class="card-header">
                            Anteprima
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Articolo</label>
                                            <p>{{purchase.nome}}</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Prezzo</label>
                                            <p>{{purchase.price}} &euro;</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Quantita</label>
                                            <select type="number" ng-init="1" class="form-control" ng-model="purchase.quantity">
                                                <option ng-repeat="quantity in availableQuantities" value="{{quantity}}"> {{ quantity }} </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary" ng-click="addToShoppingCart(purchase)">Aggiungi al Carrello</button>
                        </div>
                    </div>
                </div>



                <!-- Article Modal-->
                <div class="modal fade" id="articleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Anteprima Articolo</h5>
                                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="container-fluid">
                                    <form>
                                        <div class="row">
                                            <div class="col-md-12 px-md-1">
                                                <div class="form-group">
                                                    <label>Articolo</label>
                                                    <p>{{purchase.nome}}</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 px-md-1">
                                                <div class="form-group">
                                                    <label>Prezzo</label>
                                                    <p>{{purchase.price}} &euro;</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 px-md-1">
                                                <div class="form-group">
                                                    <label>Quantita</label>
                                                    <select type="number" ng-init="1" class="form-control" ng-model="purchase.quantity">
                                                        <option ng-repeat="quantity in availableQuantities" value="{{quantity}}"> {{ quantity }} </option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-secondary" type="button" data-dismiss="modal">Indietro</button>
                                <button type="submit" class="btn btn-primary" ng-click="addToShoppingCart(purchase)">Aggiungi al Carrello</button>
                            </div>
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

<jsp:include page="subviews/logoutModal.jsp"></jsp:include>

<jsp:include page="subviews/scripts.jsp"></jsp:include>
<script type="text/javascript" src="./webapp/AngularJS/controllers/myShopController.js"></script>


</body>

</html>
