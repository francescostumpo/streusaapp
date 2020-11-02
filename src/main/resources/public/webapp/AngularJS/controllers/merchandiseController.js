streusaApp.controller("merchandisesController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from merchandisesController");

    var host = mainController.getHost();

    $scope.newMerchandiseView = false;
    $scope.allMerchandisesView = true;
    $scope.editMerchandiseView = false;

    $scope.setNewMerchandiseView = function(){
        $scope.newMerchandiseView = true;
        $scope.allMerchandisesView = false;
        $scope.editMerchandiseView = false;
    }

    $scope.setAllMerchandisesView = function(){
        $scope.newMerchandiseView = false;
        $scope.allMerchandisesView = true;
        $scope.editMerchandiseView = false;
    }

    $scope.setEditMerchandiseView = function(merchandise){
        $scope.newMerchandiseView = false;
        $scope.allMerchandisesView = true;
        $scope.editMerchandiseView = true;

        $scope.editMerchandise = merchandise;
    }

    $scope.checkGiacenza = function(merchandise){
        if(merchandise.quantity < 1){
            return ['no-giacency'];
        }
    }

    $scope.retrieveAllMerchandises = function(){
        mainController.startProgressIndicator('#loading');
        $http.get(host+ "/merchandisesController/api/retrieveAllMerchandises/merchandiseAdmin").then(function(response){
            $scope.merchandises = response.data;
            mainController.stopProgressIndicator('#loading');
        })
    }


    $scope.insertNewMerchandise = function(newMerchandise){
        $http.post(host+"/merchandisesController/api/insertMerchandise", newMerchandise).then(function(response){
            $scope.retrieveAllMerchandises();
            alert(response.data.message);
            $scope.setAllMerchandisesView();
        })
    }

    $scope.modifyMerchandise = function(editMerchandise){
        $http.post(host+"/merchandisesController/api/modifyMerchandise", editMerchandise).then(function(response){
            $scope.retrieveAllMerchandises();
            alert(response.data.message);
            $scope.setAllMerchandisesView();
        })
    }

    $scope.deleteMerchandise = function(deleteMerchandise){
        $http.post(host+"/merchandisesController/api/deleteMerchandise", deleteMerchandise).then(function(response){
            $scope.retrieveAllMerchandises();
            alert(response.data.message);
            $scope.setAllMerchandisesView();
        })
    }
}]);