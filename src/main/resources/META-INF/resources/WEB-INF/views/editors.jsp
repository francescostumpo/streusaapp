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
        <div id="content">

            <jsp:include page="subviews/topbar.jsp"></jsp:include>

            <!-- Begin Page Content -->
            <div class="container-fluid" ng-controller="editorsController">

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">Amministrazione - Editori</h1>
                <p class="lead">Da questo pannello, gestisci gli Editori di STREUSA 2.0</p>

                <div class="row col-md-12 col-lg-12">
                    <button class="btn btn-outline-primary mr-3" ng-click="setAllEditorsView()">Tutti</button>
                    <button class="btn btn-outline-primary ml-3" ng-click="setNewEditorView()">Nuovo</button>
                </div>
                <hr>
                <div class="row" ng-show="newEditorView">
                    <div class="card col-md-8 col-lg-8">
                        <div class="card-header">
                            Nuovo Editore
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-md-8 pr-md-1">
                                        <div class="form-group">
                                            <label>Nome</label>
                                            <input type="text" class="form-control" ng-model="editor.nome">
                                        </div>
                                    </div>
                                    <div class="col-md-4 pl-md-1">
                                        <div class="form-group">
                                            <label>Gross Profit (%)</label>
                                            <input type="number" class="form-control" ng-model="editor.gp">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary" ng-click="insertNewEditor(editor)">Inserisci</button>
                        </div>
                    </div>
                </div>

                <div class="row" ng-show="allEditorsView">
                    <div class="card col-md-8 col-lg-8" ng-init="retrieveAllEditors()">
                        <div class="card-header">
                            Tutti gli Editori
                        </div>
                        <table datatable="ng" class="row-border hover">
                            <thead>
                            <tr>
                                <th width="70%">Editore</th>
                                <th width="25%">GP (%)</th>
                                <th width="5%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="editor in editors">
                                <td>{{editor.nome}}</td>
                                <td>{{editor.gp}}</td>
                                <td><i class="far fa-edit" style="cursor: pointer;" ng-click="setEditEditorView(editor)"></i></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="card col-md-4 col-lg-4" ng-show="editEditorView">
                        <div class="card-header">
                            Modifica Editore
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Nome</label>
                                            <input type="text" class="form-control" ng-model="editEditor.nome">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Gross Profit (%)</label>
                                            <input type="number" class="form-control" ng-model="editEditor.gp">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary" ng-click="modifyEditor(editEditor)">Salva</button>
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
<script type="text/javascript" src="./webapp/AngularJS/controllers/editorsController.js"></script>
</body>

</html>
