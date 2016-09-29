(function() {
    'use strict';

    angular
        .module('siteApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('politico', {
            parent: 'entity',
            url: '/politico',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Politicos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/politico/politicos.html',
                    controller: 'PoliticoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('politico-detail', {
            parent: 'entity',
            url: '/politico/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Politico'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/politico/politico-detail.html',
                    controller: 'PoliticoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Politico', function($stateParams, Politico) {
                    return Politico.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'politico',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('politico-detail.edit', {
            parent: 'politico-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/politico/politico-dialog.html',
                    controller: 'PoliticoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Politico', function(Politico) {
                            return Politico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('politico.new', {
            parent: 'politico',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/politico/politico-dialog.html',
                    controller: 'PoliticoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                camaraPk: null,
                                senadoId: null,
                                nome: null,
                                codinomes: null,
                                uf: null,
                                partidoAtual: null,
                                outrosPartidos: null,
                                profissoes: null,
                                cargo: null,
                                legislaturas: null,
                                foto: null,
                                urls: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('politico', null, { reload: 'politico' });
                }, function() {
                    $state.go('politico');
                });
            }]
        })
        .state('politico.edit', {
            parent: 'politico',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/politico/politico-dialog.html',
                    controller: 'PoliticoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Politico', function(Politico) {
                            return Politico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('politico', null, { reload: 'politico' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('politico.delete', {
            parent: 'politico',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/politico/politico-delete-dialog.html',
                    controller: 'PoliticoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Politico', function(Politico) {
                            return Politico.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('politico', null, { reload: 'politico' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
