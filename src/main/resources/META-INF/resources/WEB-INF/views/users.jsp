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
            <div class="container-fluid" ng-controller="usersController">

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">Amministrazione - Utenti</h1>
                <p class="lead">Da questo pannello, gestisci gli utenti che possono operare in STREUSA 2.0</p>
                <div class="row col-md-12 col-lg-12">
                    <button class="btn btn-outline-primary mr-3" ng-click="setAllUtentiView()">Tutti</button>
                    <button class="btn btn-outline-primary ml-3" ng-click="setNewUtenteView()">Nuovo</button>
                </div>
                <hr>

                <div class="row" ng-show="newUtenteView">
                    <div class="card col-md-8 col-lg-8">
                        <div class="card-header">
                            Nuovo Utente
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-md-6 pr-md-1">
                                        <div class="form-group">
                                            <label>Email</label>
                                            <input type="email" class="form-control" placeholder="mario.rossi@email.com" ng-model="newUser.email">
                                        </div>
                                    </div>
                                    <div class="col-md-6 pl-md-1">
                                        <div class="form-group">
                                            <label>Password</label>
                                            <input type="password" class="form-control" ng-model="newUser.password">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 pr-md-1">
                                        <div class="form-group">
                                            <label>Nome</label>
                                            <input type="text" class="form-control" ng-model="newUser.nome">
                                        </div>
                                    </div>
                                    <div class="col-md-4 px-md-1">
                                        <div class="form-group">
                                            <label>Cognome</label>
                                            <input type="text" class="form-control" ng-model="newUser.cognome">
                                        </div>
                                    </div>
                                    <div class="col-md-4 pl-md-1">
                                        <div class="form-group">
                                            <label>Ruolo</label>
                                            <select type="text" class="form-control" ng-model="newUser.ruolo">
                                                <option value="User">User</option>
                                                <option value="SuperUser">SuperUser</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary" ng-click="insertUser(newUser)">Inserisci</button>
                        </div>
                    </div>
                </div>

                <div class="row" ng-show="allUtentiView">
                    <div class="card col-md-8 col-lg-8" ng-init="retrieveAllUsers()">
                        <div class="card-header">
                            Tutti gli Utenti
                        </div>
                        <table datatable="ng" class="row-border hover" >
                            <thead>
                            <tr>
                                <th width="45%">Email</th>
                                <th width="25%">Nome</th>
                                <th width="25%">Cognome</th>
                                <th width="5%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="user in users">
                                <td>{{user.email}}</td>
                                <td>{{user.nome}}</td>
                                <td>{{user.cognome}}</td>
                                <td><i class="far fa-edit" style="cursor: pointer;" ng-click="setModificaUtenteView(user)"></i></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="card col-md-4 col-lg-4" ng-show="modificaUtenteView">
                        <div class="card-header">
                            Modifica Utente
                        </div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Email</label>
                                            <input type="text" class="form-control" ng-model="editUser.email">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Password</label>
                                            <input type="text" class="form-control" ng-model="editUser.password">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Nome</label>
                                            <input type="text" class="form-control" ng-model="editUser.nome">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Cognome</label>
                                            <input type="text" class="form-control" ng-model="editUser.cognome">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 px-md-1">
                                        <div class="form-group">
                                            <label>Ruolo</label>
                                            <select type="text" class="form-control" ng-model="editUser.ruolo">
                                                <option value="User">User</option>
                                                <option value="SuperUser">SuperUser</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-primary" ng-click="modifyUser(editUser)">Salva</button>
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
<script type="text/javascript" src="./webapp/AngularJS/controllers/usersControllers.js"></script>

</body>

</html>
