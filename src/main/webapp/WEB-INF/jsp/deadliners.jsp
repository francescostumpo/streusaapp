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
    <jsp:include page="subviews/calendarSheets.jsp"></jsp:include>

</head>

<body id="page-top" ng-app="streusaApp" ng-controller="deadlinersController">

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
            <div class="container-fluid" >

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">Scadenziario</h1>
                <p class="lead">Da questo pannello, gestisci le scadenze di STREUSA 2.0</p>

                <div id="calendar"></div>
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


<!-- Calendar Modal - New Event-->
<div class="modal fade" id="calendarModal" tabindex="-1" role="dialog" aria-labelledby="calendarModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="calendarModalLabel">Crea Evento</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="row">
                        <div class="col-md-12 px-md-1">
                            <div class="form-group">
                                <label>Titolo Evento</label>
                                <input type="text" class="form-control" ng-model="event.title">
                            </div>
                        </div>
                    </div>
                    <!--<div class="row">
                        <div class="col-md-12 px-md-1">
                            <div class="form-group">
                                <label>Inizio Evento</label>
                                <input type="text" ng-value="event.start" class="form-control" ng-model="event.start">{{event.start}}
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 px-md-1">
                            <div class="form-group">
                                <label>Fine Evento</label>
                                <input type="text" ng-value="event.end" class="form-control" ng-model="event.end">{{event.end}}
                            </div>
                        </div>
                    </div>-->
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal" ng-click="deleteEventFromCache()">Indietro</button>
                <a class="btn btn-primary" ng-click="insertNewEvent(event)" style="color: white">Crea Evento</a>
            </div>
        </div>
    </div>
</div>

<!-- Calendar Modal - Modify Event-->
<div class="modal fade" id="calendarEditModal" tabindex="-1" role="dialog" aria-labelledby="calendarEditModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="calendarEditModalLabel">Modifica Evento</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="row">
                        <div class="col-md-12 px-md-1">
                            <div class="form-group">
                                <label>Titolo Evento</label>
                                <input type="text"  ng-value="{{editEvent.title}}" class="form-control" ng-model="editEvent.title">
                            </div>
                        </div>
                    </div>
                    <!--<div class="row">
                        <div class="col-md-12 px-md-1">
                            <div class="form-group">
                                <label>Inizio Evento</label>
                                <input type="text" ng-value="{{editEvent.start}}"class="form-control" ng-model="editEvent.start">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 px-md-1">
                            <div class="form-group">
                                <label>Fine Evento</label>
                                <input type="text" ng-value="{{editEvent.end}}" class="form-control" ng-model="editEvent.end">
                            </div>
                        </div>
                    </div>-->
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal" ng-click="deleteEvent(editEvent)">Elimina Evento</button>
                <a class="btn btn-primary" ng-click="modifyEvent(editEvent)" style="color: white">Modifica Evento</a>
            </div>
        </div>
    </div>
</div>
<!-- Logout Modal-->
<jsp:include page="subviews/logoutModal.jsp"></jsp:include>

<jsp:include page="subviews/scripts.jsp"></jsp:include>
<jsp:include page="subviews/calendarScripts.jsp"></jsp:include>
<script type="text/javascript" src="./webapp/AngularJS/controllers/deadlinersController.js"></script>


</body>

</html>
