streusaApp.controller("editorsController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from editorsController");

    var host = mainController.getHost();

    $scope.newEditorView = false;
    $scope.allEditorsView = true;
    $scope.editEditorView = false;

    $scope.setNewEditorView = function(){
        $scope.newEditorView = true;
        $scope.allEditorsView = false;
        $scope.editEditorView = false;
    }

    $scope.setAllEditorsView = function(){
        $scope.newEditorView = false;
        $scope.allEditorsView = true;
        $scope.editEditorView = false;
    }

    $scope.setEditEditorView = function(editor){
        $scope.newEditorView = false;
        $scope.allEditorsView = true;
        $scope.editEditorView = true;

        $scope.editEditor = editor;
    }

    $scope.retrieveAllEditors = function(){
        mainController.startProgressIndicator('#loading');
        $http.get(host+"/editorsController/api/retrieveAllEditors").then(function(response){
            $scope.editors = response.data;
            mainController.stopProgressIndicator('#loading');
        })
    }

    $scope.insertNewEditor = function(newEditor){
        $http.post(host+"/editorsController/api/insertEditor", newEditor).then(function(response){
            $scope.retrieveAllEditors();
            alert(response.data.message);
            $scope.setAllEditorsView();
        })
    }

    $scope.modifyEditor = function(editEditor){
        $http.post(host+"/editorsController/api/modifyEditor", editEditor).then(function(response){
            $scope.retrieveAllEditors();
            alert(response.data.message);
            $scope.setAllEditorsView();
        })
    }
}]);