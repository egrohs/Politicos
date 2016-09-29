(function() {
    'use strict';

    angular
        .module('siteApp')
        .controller('PoliticoDetailController', PoliticoDetailController);

    PoliticoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Politico'];

    function PoliticoDetailController($scope, $rootScope, $stateParams, previousState, entity, Politico) {
        var vm = this;

        vm.politico = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('siteApp:politicoUpdate', function(event, result) {
            vm.politico = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
