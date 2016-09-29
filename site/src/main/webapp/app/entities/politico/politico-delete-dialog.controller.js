(function() {
    'use strict';

    angular
        .module('siteApp')
        .controller('PoliticoDeleteController',PoliticoDeleteController);

    PoliticoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Politico'];

    function PoliticoDeleteController($uibModalInstance, entity, Politico) {
        var vm = this;

        vm.politico = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Politico.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
