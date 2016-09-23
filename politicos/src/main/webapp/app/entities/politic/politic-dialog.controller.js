(function() {
    'use strict';

    angular
        .module('politicosApp')
        .controller('PoliticDialogController', PoliticDialogController);

    PoliticDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Politic'];

    function PoliticDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Politic) {
        var vm = this;

        vm.politic = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.politic.id !== null) {
                Politic.update(vm.politic, onSaveSuccess, onSaveError);
            } else {
                Politic.save(vm.politic, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('politicosApp:politicUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.born = false;
        vm.datePickerOpenStatus.created = false;
        vm.datePickerOpenStatus.updated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
