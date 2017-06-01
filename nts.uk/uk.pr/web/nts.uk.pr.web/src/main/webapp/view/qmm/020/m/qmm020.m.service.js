var qmm020;
(function (qmm020) {
    var m;
    (function (m) {
        var service;
        (function (service) {
            var paths = {
                getData: 'pr/core/allotlayouthist/getdata'
            };
            function getData(baseYm) {
                // set default Yearmonth
                baseYm = baseYm || 197001;
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getData + "/" + baseYm, { baseYm: baseYm })
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getData = getData;
        })(service = m.service || (m.service = {}));
    })(m = qmm020.m || (qmm020.m = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.m.service.js.map