(function() {
    'use strict';
    angular
        .module('siteApp')
        .factory('Politico', Politico);

    Politico.$inject = ['$resource'];

    function Politico ($resource) {
        var resourceUrl =  'api/politicos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
