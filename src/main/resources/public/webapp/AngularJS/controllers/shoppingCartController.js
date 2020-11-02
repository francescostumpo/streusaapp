streusaApp.controller("shoppingCartController", ['$scope', '$http', '$location', '$rootScope', function($scope, $http, $location,$rootScope) {
    console.log("[INFO] Hello World from shoppingCartController");

    var host = mainController.getHost();
    $scope.purchases = mainController.getShoppingCart();

    console.log($scope.purchases);
    $scope.nominalTotal = 0;

    $scope.calculateTotalPrice = function(){
        for(i=0; i< $scope.purchases.length; i++){
            $scope.nominalTotal = $scope.nominalTotal + $scope.purchases[i].price * $scope.purchases[i].quantity;
            $scope.finalTotal = $scope.nominalTotal;
            $scope.discountedTotal = $scope.nominalTotal;
        }
    }

    $scope.calculateTotalPrice();


    $scope.applyDiscount = function(discount){
        $scope.discountedTotal = $scope.nominalTotal * (1 - (discount / 100));
        $scope.finalTotal = $scope.discountedTotal;
    }

    $scope.deletePurchase = function(purchase){
        console.log($scope.purchases)
        for(i=0; i< $scope.purchases.length; i++){
            if($scope.purchases[i]._id === purchase._id){
                $scope.purchases.splice(i, 1);
            }
        }
        sessionStorage.setItem("shoppingCart", JSON.stringify($scope.purchases));
        $scope.nominalTotal = 0;
        $scope.finalTotal = $scope.nominalTotal;
        $scope.discountedTotal = $scope.nominalTotal;
        $scope.calculateTotalPrice();
    }

    $scope.verifyShoppingCart = function(){
        if($scope.purchases.length>0){
            return false;
        }else{
            return true;
        }
    }

    $scope.proceedToPayment = function(){
        $scope.shoppingCart = { cart: $scope.purchases, finalTotal: $scope.finalTotal};
        $http.post(host + "/shoppingCartController/api/pay", $scope.shoppingCart).then(function (response) {
            mainController.deleteShoppingCart();
            alert(response.data.message);
            location.href = '/myShop';
        })

    }

}]);