streusaApp.controller("loginController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from loginController");

    var host = mainController.getHost();

    $scope.login = function(user){
        $http.post(host+"/userLogin", user).then(function (response) {
            console.log(response.data);
            if(response.data.message === "OK"){
                sessionStorage.setItem('profileName', response.data.profileName)
                location.href = "/myShop"
            }else{
                alert(response.data.message);
            }
        })
    }
}]);