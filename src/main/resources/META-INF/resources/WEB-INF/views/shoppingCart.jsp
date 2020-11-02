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
        <div id="content" ng-controller="myShopController">

            <jsp:include page="subviews/topbar.jsp"></jsp:include>

            <!-- Begin Page Content -->
            <div class="container-fluid" ng-controller="shoppingCartController">

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">Shopping Cart</h1>
                <p class="lead">Da questo pannello, procedi alla chiusura della spesa</p>
                <hr>
                <div class="row">
                    <div class="card col-md-12 col-lg-12">

                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-5 pr-md-1">
                                    <div class="form-group">
                                        <label>Articolo</label>
                                    </div>
                                </div>
                                <div class="col-md-3 px-md-1">
                                    <div class="form-group">
                                        <label>Quantita</label>
                                    </div>
                                </div>
                                <div class="col-md-3 px-md-1">
                                    <div class="form-group">
                                        <label>Prezzo Complessivo</label>
                                    </div>
                                </div>
                                <div class="col-md-1 pl-md-1">
                                    <div class="form-group">
                                    </div>
                                </div>
                            </div>
                            <div class="row" ng-repeat="purchase in purchases">
                                <div class="col-md-5 pr-md-1">
                                    <div class="form-group">
                                        <p>{{purchase.nome}}</p>
                                    </div>
                                </div>
                                <div class="col-md-3 px-md-1">
                                    <div class="form-group">
                                        <p>{{purchase.quantity}} X {{purchase.price}} &euro;</p>
                                    </div>
                                </div>
                                <div class="col-md-3 px-md-1">
                                    <div class="form-group">
                                        <p>{{purchase.price * purchase.quantity}} &euro;</p>
                                    </div>
                                </div>
                                <div class="col-md-1 pl-md-1">
                                    <div class="form-group">
                                        <i class="fas fa-minus-circle align-middle" style="cursor: pointer;" ng-click="deletePurchase(purchase)"></i>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-md-8 pr-md-1">
                                    <div class="form-group">
                                        <p>TOTALE NOMINALE</p>
                                    </div>
                                </div>
                                <div class="col-md-3 px-md-1">
                                    <div class="form-group">
                                        <p>{{nominalTotal}} &euro;</p>
                                    </div>
                                </div>
                                <div class="col-md-1 pl-md-1">
                                    <div class="form-group">

                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row align-middle">
                                <div class="col-md-4 pr-md-1">
                                    <div class="form-group">
                                        <p>TOTALE SCONTATO</p>
                                    </div>
                                </div>
                                <div class="col-md-4 px-md-1">
                                    <div class="form-group">
                                        <input type="number" class="form-control" placeholder="Applica sconto" ng-model="discount" ng-change="applyDiscount(discount)">
                                    </div>
                                </div>
                                <div class="col-md-3 px-md-1">
                                    <div class="form-group">
                                        <p>{{discountedTotal}} &euro;</p>
                                    </div>
                                </div>
                                <div class="col-md-1 pl-md-1">
                                    <div class="form-group">

                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row align-middle">
                                <div class="col-md-4 pr-md-1">
                                    <div class="form-group">
                                        <p>TOTALE FINALE</p>
                                    </div>
                                </div>
                                <div class="col-md-4 px-md-1">
                                    <div class="form-group">
                                        <input type="number" step="0.01" min="0" class="form-control" placeholder="Override Totale Finale" ng-model="finalTotal">
                                    </div>
                                </div>
                                <div class="col-md-3 px-md-1">
                                    <div class="form-group">
                                        <p>{{finalTotal}} &euro;</p>
                                    </div>
                                </div>
                                <div class="col-md-1 pl-md-1">
                                    <div class="form-group">

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary" ng-disabled="verifyShoppingCart()" ng-click="proceedToPayment()">Procedi con il pagamento</button>
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
<script type="text/javascript" src="./webapp/AngularJS/controllers/shoppingCartController.js"></script>
<script type="text/javascript" src="./webapp/AngularJS/controllers/myShopController.js"></script>

</body>

</html>
