(function() {
    'use strict';

    angular
        .module('siteApp')
        .factory('PoliticoSearch', PoliticoSearch);

    PoliticoSearch.$inject = ['$resource'];

    function PoliticoSearch($resource) {
        var resourceUrl =  'api/_search/politicos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
