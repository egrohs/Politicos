(function() {
    'use strict';

   angular.module('politicosApp')
    .service('imageService',['$q','$http',function($q,$http){
        this.loadImages = function(){
            return $http.jsonp("https://api.flickr.com/services/feeds/photos_public.gne?format=json&jsoncallback=JSON_CALLBACK");
        };
    }]).controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Politic', 'ParseLinks', 'imageService', 'angularGridInstance'];

    function HomeController ($scope, Principal, LoginService, $state, Politic, ParseLinks, imageService, angularGridInstance) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }


        // load politics collection
        vm.politics = [];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        vm.images = [];

        loadAll();

        function loadAll () {
            Politic.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.politics.push(data[i]);
                    data[i].actualWidth = 100;
                    data[i].actualHeight = 100;
                    if(data[i].photo == "")
			data[i].photo = "http://sensoincomum.org/wp-content/uploads/2016/03/marketing-politico.jpg";
		    vm.images.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.politics = [];
            vm.images = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

	imageService.loadImages().then(function(data){
            data.data.items.forEach(function(obj){
                var desc = obj.description,
                    width = desc.match(/width="(.*?)"/)[1],
                    height = desc.match(/height="(.*?)"/)[1];
                
                obj.actualHeight = height;
                obj.actualWidth = width;
            });
           $scope.images = vm.images;// data.data.items;
        });

//intialized imageList
$scope.page = 1;
//method to load next data
$scope.loadMore = function(){
    loadPage(page).then(function(nextPageImages){
        $scope.images = $scope.images.concat(nextPageImages);
        $scope.page++;
    });
};

    }
})();
