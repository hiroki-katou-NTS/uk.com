var qmm005;
(function (qmm005) {
    var b;
    (function (b) {
        // api define in qmm005.ts
        var webapi = qmm005.common.webapi();
        var services;
        (function (services) {
            function getData(processingNo) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005b.getdata, { processingNo: processingNo })
                    .done(function (resp) {
                    dfd.resolve(resp);
                })
                    .fail(function (mess) {
                    dfd.reject(mess);
                });
                return dfd.promise();
            }
            services.getData = getData;
            function updatData(model) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005b.update, model)
                    .done(function (resp) {
                    dfd.resolve(resp);
                })
                    .fail(function (mess) {
                    dfd.reject(mess);
                });
                return dfd.promise();
            }
            services.updatData = updatData;
            function deleteData(model) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005b.delete, model)
                    .done(function (resp) {
                    dfd.resolve(resp);
                })
                    .fail(function (mess) {
                    dfd.reject(mess);
                });
                return dfd.promise();
            }
            services.deleteData = deleteData;
        })(services = b.services || (b.services = {}));
    })(b = qmm005.b || (qmm005.b = {}));
})(qmm005 || (qmm005 = {}));
