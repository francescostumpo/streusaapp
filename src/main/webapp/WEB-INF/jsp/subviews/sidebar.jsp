<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="streusaApp"%>

<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/myShop">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-book-open"></i>
        </div>
        <div class="sidebar-brand-text mx-3">STREUSA <sup>2.0</sup></div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Nav Item - Dashboard -->
    <!--<li class="nav-item active">
        <a class="nav-link" href="/">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span></a>
    </li>


    <hr class="sidebar-divider my-0">-->

    <li class="nav-item">
        <a class="nav-link collapsed" href="/myShop">
            <i class="fas fa-store-alt"></i>
            <span>MyShop</span></a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <li class="nav-item">
        <a class="nav-link" href="/deadliners">
            <i class="far fa-calendar-alt"></i>
            <span>Scadenziario</span></a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Nav Item - Utilities Collapse Menu -->
    <streusaApp:if test="${role == 'SuperUser' }">
        <li class="nav-item">
            <a class="nav-link collapsed" href="/" data-toggle="collapse" data-target="#collapseUtilities" aria-expanded="true" aria-controls="collapseUtilities">
                <i class="fas fa-chart-line"></i>
                <span>Reportistica</span>
            </a>
            <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="/reportingActive">Attiva</a>
                    <a class="collapse-item" href="" style="color: grey">Passiva (disabled)</a>
                </div>
            </div>
        </li>
    <!-- Divider -->
    <hr class="sidebar-divider my-0">
    </streusaApp:if>

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
            <i class="fas fa-cogs"></i>
            <span>Amministrazione</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <a class="collapse-item" href="/users">Utenti</a>
                <a class="collapse-item" href="/editors">Editori</a>
                <a class="collapse-item" href="/suppliers">Fornitori</a>
                <!--<a class="collapse-item" href="cards.html">Ordini</a>-->
                <a class="collapse-item" href="/genres">Generi</a>
                <a class="collapse-item" href="/books">Libri</a>
                <a class="collapse-item" href="/merchandise">Merchandise</a>
            </div>
        </div>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider d-none d-md-block">

    <!-- Sidebar Toggler (Sidebar) -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>

</ul>
<!-- End of Sidebar -->