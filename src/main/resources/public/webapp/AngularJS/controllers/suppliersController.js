streusaApp.controller("suppliersController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from suppliersController");

    var host = mainController.getHost();

    $scope.newSupplierView = false;
    $scope.allSuppliersView = true;
    $scope.editSupplierView = false;

    $scope.setNewSupplierView = function(){
        $scope.newSupplierView = true;
        $scope.allSuppliersView = false;
        $scope.editSupplierView = false;
    }

    $scope.setAllSuppliersView = function(){
        $scope.newSupplierView = false;
        $scope.allSuppliersView = true;
        $scope.editSupplierView = false;
    }

    $scope.setEditSupplierView = function(supplier){
        $scope.newSupplierView = false;
        $scope.allSuppliersView = true;
        $scope.editSupplierView = true;

        $scope.editSupplier = supplier;
    }

    $scope.retrieveAllSuppliers = function(){
        mainController.startProgressIndicator('#loading');
        $http.get(host+"/suppliersController/api/retrieveAllSuppliers").then(function(response){
            $scope.suppliers = response.data;
            mainController.stopProgressIndicator('#loading');
        })
    }

    $scope.insertNewSupplier = function(newSupplier){
        $http.post(host+"/suppliersController/api/insertSupplier", newSupplier).then(function(response){
            $scope.retrieveAllSuppliers();
            alert(response.data.message);
            $scope.setAllSuppliersView();
        })
    }

    $scope.modifySupplier = function(editSupplier){
        $http.post(host+"/suppliersController/api/modifySupplier", editSupplier).then(function(response){
            $scope.retrieveAllSuppliers();
            alert(response.data.message);
            $scope.setAllSuppliersView();
        })
    }
}]);