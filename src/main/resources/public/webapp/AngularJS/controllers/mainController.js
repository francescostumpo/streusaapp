var streusaApp = angular.module("streusaApp", ["datatables"]);

mainController = {

    getHost: function(){
        let host = window.location.origin;

        console.log('Origin: ' + host)
        return host;
    },

    getShoppingCart: function(){
        var shoppingCart = JSON.parse(sessionStorage.getItem("shoppingCart"));
        if(shoppingCart === undefined || shoppingCart === null){
            return [];
        }else{
            return shoppingCart;
        }
    },

    checkShoppingCart: function () {
        var shoppingCart = JSON.parse(sessionStorage.getItem("shoppingCart"));
        if(shoppingCart === undefined || shoppingCart === null){
            return 0;
        }else{
            return shoppingCart.length;
        }
    },

    deleteShoppingCart: function () {
        sessionStorage.setItem("shoppingCart", null);
    },

    startProgressIndicator: function (id){
        $(id).show();


    },

    stopProgressIndicator: function(id){
        setTimeout(function(){$(id).hide()}, 150);

    },

    setProfileName: function () {
        var profileName = sessionStorage.getItem("profileName");
        $('#profileName').text(profileName);
    },

    showShoppingCart: function () {
        var location = window.location.pathname;
        //if(location.includes('myShop') || location.includes('shoppingCart')){
        if(location.includes('myShop')){
            $('#shoppingCart').show();
        }else{
            $('#shoppingCart').hide();
        }
    },
    downloadFile: function (file) {
        var url = window.URL || window.webkitURL;
        var element = document.createElement('a');
        element.setAttribute('href', url.createObjectURL(file));
        element.setAttribute('download', "Estrazione.xlsx");

        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    }
}

mainController.setProfileName();
mainController.showShoppingCart();
