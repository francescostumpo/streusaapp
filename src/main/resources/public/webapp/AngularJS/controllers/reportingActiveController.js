streusaApp.controller("reportingActiveController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from reportingActiveController");

    var host = mainController.getHost();

    $scope.retrieveAllEditors = function(){
        mainController.startProgressIndicator("#loading");
        $http.get(host+"/editorsController/api/retrieveAllEditors").then(function(response){
            $scope.editors = response.data;
            mainController.stopProgressIndicator("#loading");
        })
    }

    $scope.retrieveAllEditors();

    $scope.retrieveAllSuppliers = function(){
        $http.get(host+"/suppliersController/api/retrieveAllSuppliers").then(function(response){
            $scope.suppliers = response.data;
        })
    }

    $scope.retrieveAllSuppliers();

    $scope.extractToday = function(){
        mainController.startProgressIndicator('#loading');
        var config = {
            responseType: 'arraybuffer'
        };

        $http.get(host+"/reportingController/api/extractTodayData", config).then(function(response){
            var file = new Blob([response.data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
            mainController.stopProgressIndicator('#loading');
            mainController.downloadFile(file);

        })
    }

    $scope.extractTimeFrame = function(query){
        console.log(query);
        mainController.startProgressIndicator('#loading');
        var config = {
            responseType: 'arraybuffer'
        };

        $http.post(host+"/reportingController/api/extractTimeFrame", query, config).then(function(response){
            var file = new Blob([response.data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
            mainController.stopProgressIndicator('#loading');
            mainController.downloadFile(file);

        })
    }

    $scope.extractBooksLinkedToSupplier = function(query){
        console.log(query);
        mainController.startProgressIndicator('#loading');
        var config = {
            responseType: 'arraybuffer'
        };

        $http.post(host+"/reportingController/api/extractBooksLinkedToSupplier", query, config).then(function(response){
            var file = new Blob([response.data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
            mainController.stopProgressIndicator('#loading');
            mainController.downloadFile(file);

        })
    }

    $scope.extractBooksLinkedToEditor = function(query){
        console.log(query);
        mainController.startProgressIndicator('#loading');
        var config = {
            responseType: 'arraybuffer'
        };

        $http.post(host+"/reportingController/api/extractBooksLinkedToEditor", query, config).then(function(response){
            var file = new Blob([response.data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
            mainController.stopProgressIndicator('#loading');
            mainController.downloadFile(file);

        })
    }


}]);