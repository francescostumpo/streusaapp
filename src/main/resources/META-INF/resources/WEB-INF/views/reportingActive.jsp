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

            <jsp:include page="subviews/topbar.jsp"></jsp:include>

            <!-- Begin Page Content -->
            <div class="container-fluid" ng-controller="reportingActiveController">

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">Reportistica - Attiva</h1>
                <p class="lead">Da questo pannello, gestisci le entrate di STREUSA 2.0</p>
                <hr>
                <div class="row">
                    <div class="card col-md-12 col-lg-12">

                        <div class="card-body">
                            <div class="form-group row">
                                <label class="col-md-10 pr-md-1">Estrai i dati di fatturazione della giornata odierna</label>
                                <button type="submit" class="btn btn-primary col-md-2 pr-md-1" style="cursor:pointer;" ng-click="extractToday()">Procedi all'estrazione</button>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-4 pr-md-1">Estrai i dati di fatturazione dell'intervallo selezionato</label>
                                <input type="date" class="form-control col-md-3 pr-md-1"ng-model="query.startDate">
                                <input type="date" class="form-control col-md-3 pr-md-1"ng-model="query.endDate">
                                <button type="submit" class="btn btn-primary col-md-2 pr-md-1" style="cursor:pointer;" ng-click="extractTimeFrame(query)">Procedi all'estrazione</button>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-7 pr-md-1">Estrai i libri di uno specifico fornitore</label>
                                <select type="text" ng-init="querySupplier.supplier" class="form-control col-md-3 pr-md-1" ng-model="querySupplier.supplier">
                                    <option ng-repeat="supplier in suppliers | orderBy:'supplier.nome'" value="{{supplier.nome}}"> {{ supplier.nome }} </option>
                                </select>
                                <button type="submit" class="btn btn-primary col-md-2 pr-md-1" style="cursor:pointer;" ng-click="extractBooksLinkedToSupplier(querySupplier)">Procedi all'estrazione</button>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-7 pr-md-1">Estrai i libri di uno specifico editore</label>
                                <select type="text" ng-init="queryEditor.editor = suppliers[0].nome" class="form-control col-md-3 pr-md-1" ng-model="queryEditor.editor">
                                    <option ng-repeat="editor in editors | orderBy:'editor.nome'" value="{{editor.nome}}"> {{ editor.nome }} </option>
                                </select>
                                <button type="submit" class="btn btn-primary col-md-2 pr-md-1" style="cursor:pointer;" ng-click="extractBooksLinkedToEditor(queryEditor)">Procedi all'estrazione</button>
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
<script type="text/javascript" src="./webapp/AngularJS/controllers/reportingActiveController.js"></script>

</body>

</html>
