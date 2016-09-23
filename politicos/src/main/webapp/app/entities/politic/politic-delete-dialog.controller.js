(function() {
    'use strict';

    angular
        .module('politicosApp')
        .controller('PoliticDeleteController',PoliticDeleteController);

    PoliticDeleteController.$inject = ['$uibModalInstance', 'entity', 'Politic'];

    function PoliticDeleteController($uibModalInstance, entity, Politic) {
        var vm = this;

        vm.politic = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Politic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
