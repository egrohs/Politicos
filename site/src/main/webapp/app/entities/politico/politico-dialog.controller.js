(function() {
    'use strict';

    angular
        .module('siteApp')
        .controller('PoliticoDialogController', PoliticoDialogController);

    PoliticoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Politico'];

    function PoliticoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Politico) {
        var vm = this;

        vm.politico = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.politico.id !== null) {
                Politico.update(vm.politico, onSaveSuccess, onSaveError);
            } else {
                Politico.save(vm.politico, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('siteApp:politicoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
