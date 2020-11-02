streusaApp.controller("booksController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from booksController");

    var host = mainController.getHost();

    $scope.newBookView = false;
    $scope.allBooksView = true;
    $scope.editBookView = false;

    $scope.setNewBookView = function(){
        $scope.newBookView = true;
        $scope.allBooksView = false;
        $scope.editBookView = false;
    }

    $scope.setAllBooksView = function(){
        $scope.newBookView = false;
        $scope.allBooksView = true;
        $scope.editBookView = false;
    }

    $scope.setEditBookView = function(book){
        $scope.newBookView = false;
        $scope.allBooksView = true;
        $scope.editBookView = true;

        $scope.editBook = book;
    }

    $scope.checkGiacenza = function(book){
        if(book.quantity < 1){
            return ['no-giacency'];
        }
    }

    $scope.retrieveAllBooks = function(){
        mainController.startProgressIndicator('#loading');
        $http.get(host+"/booksController/api/retrieveAllBooks/booksAdmin").then(function(response){
            $scope.books = response.data;
            mainController.stopProgressIndicator('#loading');
        })
    }

    $scope.retrieveAllEditors = function(){
        $http.get(host+"/editorsController/api/retrieveAllEditors").then(function(response){
            $scope.editors = response.data;
        })
    }

    $scope.retrieveAllEditors();

    $scope.retrieveAllSuppliers = function(){
        $http.get(host+"/suppliersController/api/retrieveAllSuppliers").then(function(response){
            $scope.suppliers = response.data;
        })
    }

    $scope.retrieveAllSuppliers();

    $scope.retrieveAllGenres = function(){
        $http.get(host+"/genresController/api/retrieveAllGenres").then(function(response){
            $scope.genres = response.data;
        })
    }

    $scope.retrieveAllGenres();

    $scope.insertNewBook = function(newBook){
        $http.post(host+"/booksController/api/insertBook", newBook).then(function(response){
            $scope.retrieveAllBooks();
            alert(response.data.message);
            $scope.setAllBooksView();
        })
    }

    $scope.modifyBook = function(editBook){
        $http.post(host+"/booksController/api/modifyBook", editBook).then(function(response){
            $scope.retrieveAllBooks();
            alert(response.data.message);
            $scope.setAllBooksView();
        })
    }

    $scope.deleteBook = function(deleteBook){
        $http.post(host+"/booksController/api/deleteBook", deleteBook).then(function(response){
            $scope.retrieveAllBooks();
            alert(response.data.message);
            $scope.setAllBooksView();
        })
    }



}]);