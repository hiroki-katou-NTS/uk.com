var qmm005;
(function (qmm005) {
    var a;
    (function (a) {
        // api define in qmm005.ts
        var webapi = qmm005.common.webapi();
        var services;
        (function (services) {
            function getData() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005a.getdata)
                    .done(function (resp) {
                    dfd.resolve(resp);
                })
                    .fail(function (mess) {
                    dfd.reject(mess);
                });
                return dfd.promise();
            }
            services.getData = getData;
        })(services = a.services || (a.services = {}));
    })(a = qmm005.a || (qmm005.a = {}));
})(qmm005 || (qmm005 = {}));
