var qmm005;
(function (qmm005) {
    var d;
    (function (d) {
        var webapi = qmm005.common.webapi();
        var services;
        (function (services) {
            function getData(processingNo) {
                var def = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005d.getdata, { processingNo: processingNo }).done(function (resp) {
                    def.resolve(resp);
                });
                return def.promise();
            }
            services.getData = getData;
            function updateData(model) {
                var def = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005d.update, model).done(function (resp) {
                    def.resolve(resp);
                });
                return def.promise();
            }
            services.updateData = updateData;
        })(services = d.services || (d.services = {}));
    })(d = qmm005.d || (qmm005.d = {}));
})(qmm005 || (qmm005 = {}));
