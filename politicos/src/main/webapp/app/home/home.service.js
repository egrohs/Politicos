(function() {
    'use strict';
    angular
        .module('politicosApp')
        .factory('Politic', Politic);

    Politic.$inject = ['$resource', 'DateUtils'];

    function Politic ($resource, DateUtils) {
        var resourceUrl =  'api/politics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.born = DateUtils.convertLocalDateFromServer(data.born);
                        data.created = DateUtils.convertDateTimeFromServer(data.created);
                        data.updated = DateUtils.convertDateTimeFromServer(data.updated);
                    }
                    return data;
                }
            }
        });
    }
})();
