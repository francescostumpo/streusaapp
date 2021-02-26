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

    $scope.findBook = function (isbn){
        mainController.startProgressIndicator('#loading');
        $http.get('https://www.googleapis.com/books/v1/volumes?q=isbn:'+isbn).then(function(response){
            try{
                const data = response.data;
                if(data.totalItems > 0){
                    const bookFound = data.items[0];
                    const bookLink = bookFound.selfLink;

                    $http.get(bookLink).then(function(response){
                        console.log(response);
                        const book = response.data;
                        $scope.newBook.title = book.volumeInfo.title;
                        let author = '';
                        for(let i = 0; i < book.volumeInfo.authors.length; i ++){
                            if(i === 0){
                                author = book.volumeInfo.authors[i];
                            }else{
                                author = author + ', ' +book.volumeInfo.authors[i];
                            }
                        }
                        $scope.newBook.author = author;
                        $scope.newBook.editor = book.volumeInfo.publisher;
                        $scope.newBook.quantity = 1;
                        if(book.saleInfo.saleability === 'FOR_SALE'){
                            $scope.newBook.price = book.volumeInfo.saleInfo.retailPrice.amount;
                        }
                        console.log($scope.newBook);
                        mainController.stopProgressIndicator('#loading');
                    }).catch(() => {
                        mainController.stopProgressIndicator('#loading');
                        alert('Errore generico');
                    })
                }
                else{
                    alert('Nessun libro trovato con il seguente ISBN: ' + isbn)
                }
                $scope.newBook.barCode = isbn;

            }catch (e) {
                alert('Errore generico');
            }
            mainController.stopProgressIndicator('#loading');
        }).catch(() =>{
            mainController.stopProgressIndicator('#loading');
            alert('Errore generico');
        })

    }



}]);
