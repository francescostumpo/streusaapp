streusaApp.controller("genresController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from genresController");

    var host = mainController.getHost();

    $scope.newGenreView = false;
    $scope.allGenresView = true;
    $scope.editGenreView = false;

    $scope.setNewGenreView = function(){
        $scope.newGenreView = true;
        $scope.allGenresView = false;
        $scope.editGenreView = false;
    }

    $scope.setAllGenresView = function(){
        $scope.newGenreView = false;
        $scope.allGenresView = true;
        $scope.editGenreView = false;
    }

    $scope.setEditGenreView = function(genre){
        $scope.newGenreView = false;
        $scope.allGenresView = true;
        $scope.editGenreView = true;

        $scope.editGenre = genre;
    }

    $scope.retrieveAllGenres = function(){
        mainController.startProgressIndicator('#loading');
        $http.get(host+"/genresController/api/retrieveAllGenres").then(function(response){
            $scope.genres = response.data;
            mainController.stopProgressIndicator('#loading');
        })
    }

    $scope.insertNewGenre = function(newGenre){
        $http.post(host+"/genresController/api/insertGenre", newGenre).then(function(response){
            $scope.retrieveAllGenres();
            alert(response.data.message);
            $scope.setAllGenresView();
        })
    }

    $scope.modifyGenre = function(editGenre){
        $http.post(host+"/genresController/api/modifyGenre", editGenre).then(function(response){
            $scope.retrieveAllGenres();
            alert(response.data.message);
            $scope.setAllGenresView();
        })
    }
}]);
