var qpp004;
(function (qpp004) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAllCompanys: "ctx/proto/company/ctx/proto/company",
                getCompanyDetail: "ctx/proto/company/"
            };
            /**
             * get list company
             */
            function getAllCompanys() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAllCompanys)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllCompanys = getAllCompanys;
        })(service = a.service || (a.service = {}));
    })(a = qpp004.a || (qpp004.a = {}));
})(qpp004 || (qpp004 = {}));
