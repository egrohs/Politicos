(function() {
    'use strict';

    angular
        .module('politicosApp')
        .controller('PoliticDetailController', PoliticDetailController);

    PoliticDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Politic'];

    function PoliticDetailController($scope, $rootScope, $stateParams, previousState, entity, Politic) {
        var vm = this;

        vm.politic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('politicosApp:politicUpdate', function(event, result) {
            vm.politic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
