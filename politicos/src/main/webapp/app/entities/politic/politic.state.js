(function() {
    'use strict';

    angular
        .module('politicosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('politic', {
            parent: 'entity',
            url: '/politic',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Politics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/politic/politics.html',
                    controller: 'PoliticController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('politic-detail', {
            parent: 'entity',
            url: '/politic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Politic'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/politic/politic-detail.html',
                    controller: 'PoliticDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Politic', function($stateParams, Politic) {
                    return Politic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'politic',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('politic-detail.edit', {
            parent: 'politic-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/politic/politic-dialog.html',
                    controller: 'PoliticDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Politic', function(Politic) {
                            return Politic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('politic.new', {
            parent: 'politic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/politic/politic-dialog.html',
                    controller: 'PoliticDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                camarapk: null,
                                nome: null,
                                codinomes: null,
                                foto: null,
                                urls: null,
                                created: null,
                                updated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('politic', null, { reload: 'politic' });
                }, function() {
                    $state.go('politic');
                });
            }]
        })
        .state('politic.edit', {
            parent: 'politic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/politic/politic-dialog.html',
                    controller: 'PoliticDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Politic', function(Politic) {
                            return Politic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('politic', null, { reload: 'politic' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('politic.delete', {
            parent: 'politic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/politic/politic-delete-dialog.html',
                    controller: 'PoliticDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Politic', function(Politic) {
                            return Politic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('politic', null, { reload: 'politic' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
