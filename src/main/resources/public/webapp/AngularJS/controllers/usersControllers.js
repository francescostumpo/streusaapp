streusaApp.controller("usersController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from usersController");

    var host = mainController.getHost();

    $scope.newUtenteView = false;
    $scope.allUtentiView = true;
    $scope.modificaUtenteView = false;

    $scope.setAllUtentiView = function(){
        $scope.newUtenteView = false;
        $scope.allUtentiView = true;
        $scope.modificaUtenteView = false;
    }

    $scope.setNewUtenteView = function(){
        $scope.newUtenteView = true;
        $scope.allUtentiView = false;
        $scope.modificaUtenteView = false;
    }

    $scope.setModificaUtenteView = function(user){
        $scope.newUtenteView = false;
        $scope.allUtentiView = true;
        $scope.modificaUtenteView = true;

        $scope.editUser = user;
    }

    $scope.retrieveAllUsers = function(){
        mainController.startProgressIndicator('#loading');
        $http.get(host+"/usersController/api/retrieveAllUsers").then(function(response){
            $scope.users = response.data;
            mainController.stopProgressIndicator('#loading');
        })
    }

    $scope.modifyUser = function(editUser){
        $http.post(host+"/usersController/api/modifyUser", editUser).then(function(response){
            $scope.retrieveAllUsers();
            alert(response.data.message);
            $scope.setAllUtentiView();
        })


    }

    $scope.insertUser = function(newUser){
        $http.post(host+"/usersController/api/insertUser", newUser).then(function(response){
            $scope.retrieveAllUsers();
            alert(response.data.message);
            $scope.setAllUtentiView();
        })

    }



}]);