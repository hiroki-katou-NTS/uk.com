/// <reference path="../qmm005.ts"/>
var qmm005;
(function (qmm005) {
    var e;
    (function (e) {
        // api define in qmm005.ts
        var webapi = qmm005.common.webapi();
        var services;
        (function (services) {
            function getData(processingNo) {
                let def = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005d.getdata, { processingNo: processingNo }).done(function (resp) {
                    def.resolve(resp);
                });
                return def.promise();
            }
            services.getData = getData;
            function updateData(model) {
                let def = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005d.update, model).done(function (resp) {
                    def.resolve(resp);
                });
                return def.promise();
            }
            services.updateData = updateData;
        })(services = e.services || (e.services = {}));
    })(e = qmm005.e || (qmm005.e = {}));
})(qmm005 || (qmm005 = {}));
