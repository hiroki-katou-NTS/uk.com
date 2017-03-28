var qmm012;
(function (qmm012) {
    var k;
    (function (k) {
        var service;
        (function (service) {
            var paths = {
                getListByCompanyCode: "core/commutelimit/find/bycompanycode"
            };
            function getCommutelimitsByCompanyCode() {
                var dfd = $.Deferred();
                var _path = paths.getListByCompanyCode;
                nts.uk.request.ajax(_path)
                    .done(function (res) {
                    dfd.resolve(res);
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getCommutelimitsByCompanyCode = getCommutelimitsByCompanyCode;
        })(service = k.service || (k.service = {}));
    })(k = qmm012.k || (qmm012.k = {}));
})(qmm012 || (qmm012 = {}));
