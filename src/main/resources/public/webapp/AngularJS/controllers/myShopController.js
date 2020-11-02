streusaApp.controller("myShopController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from myShopController");

    var host = mainController.getHost();
    $scope.shoppingCartCount = mainController.checkShoppingCart();


    $scope.booksView = true;
    $scope.merchandiseView = false;

    $scope.anteprimaView = false;

    $scope.setBooksView = function(){
        $scope.booksView = true;
        $scope.merchandiseView = false;
    }

    $scope.setMerchandiseView = function(){
        $scope.booksView = false;
        $scope.merchandiseView = true;
    }



    $scope.retrieveAllBooks = function(){
        mainController.startProgressIndicator('#loading');
        $http.get(host+"/booksController/api/retrieveAllBooks/myShop").then(function(response){
            $scope.books = response.data;
            mainController.stopProgressIndicator('#loading');
        })
    }

    $scope.retrieveAllMerchandises = function(){
        $http.get(host+ "/merchandisesController/api/retrieveAllMerchandises/myShop").then(function(response){
            $scope.merchandises = response.data;
        })
    }

    $scope.shoppingCartArray = mainController.getShoppingCart();
    $scope.count = 0;

    $scope.addToShoppingCart = function(purchase){
        $scope.shoppingCartArray.push(purchase);
        sessionStorage.setItem("shoppingCart", JSON.stringify($scope.shoppingCartArray));
        $scope.shoppingCartCount ++;
        //$scope.anteprimaView = false;
        $('#articleModal').modal('hide');
    }

    $scope.deletePurchase = function(purchase){
        console.log($scope.shoppingCartArray)
        for(i=0; i< $scope.shoppingCartArray.length; i++){
            if($scope.shoppingCartArray[i]._id === purchase._id){
                $scope.shoppingCartArray.splice(i, 1);
            }
        }
        $scope.shoppingCartCount --;
    }

    $scope.proceedToCheckout = function(){
        sessionStorage.setItem("shoppingCart", JSON.stringify($scope.shoppingCartArray));
        location.href = '/shoppingCart';
    }

    $scope.addToAnteprimaView = function(itemType, item){
        console.log(itemType);
        console.log(item);

        $scope.purchase = {};
        $scope.availableQuantities = [];

        $scope.purchase.price = item.price;
        $scope.purchase._id = item._id;
        $scope.purchase._rev = item._rev;

        if(itemType === 'book'){
            $scope.purchase.nome = item.title;
            $scope.purchase.itemType = itemType;
            $scope.purchase.author = item.author;
            $scope.purchase.editor = item.editor;
            if(item.supplier !== null && item.supplier !== undefined){
                $scope.purchase.supplier = item.supplier;
            }else{
                $scope.purchase.supplier = "";
            }
            if(item.genre !== null && item.genre !== undefined){
                $scope.purchase.genre = item.genre;
            }else{
                $scope.purchase.genre = "";
            }
        }else{
            $scope.purchase.nome = item.nome;
            $scope.purchase.itemType = itemType;
            $scope.purchase.provider = item.provider;
            $scope.purchase.gp = item.gp;
        }
        for(var i=0; i< item.quantity; i++){
            $scope.availableQuantities.push(i+1);
        }

        //$scope.anteprimaView = false;
        $('#articleModal').modal('show');
    }

    $scope.deleteShoppingCart = function(){
        mainController.deleteShoppingCart();
        $scope.shoppingCartArray = [];
        $scope.shoppingCartCount = 0;
    }
}]);