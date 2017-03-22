var qmm005;
(function (qmm005) {
    var c;
    (function (c) {
        // api define in qmm005.ts
        var webapi = qmm005.common.webapi();
        var services;
        (function (services) {
            function getData() {
            }
            services.getData = getData;
            function insertData(model) {
                var def = $.Deferred();
                nts.uk.request.ajax(webapi.qmm005c.insert, model).done(function (resp) {
                    def.resolve(resp);
                });
                return def.promise();
            }
            services.insertData = insertData;
        })(services = c.services || (c.services = {}));
    })(c = qmm005.c || (qmm005.c = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.c.service.js.map