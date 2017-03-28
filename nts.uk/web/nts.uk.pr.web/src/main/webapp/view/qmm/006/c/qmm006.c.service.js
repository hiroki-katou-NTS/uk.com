var qmm006;
(function (qmm006) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                findAll: "basic/system/bank/linebank/findAll",
                transfer: "basic/system/bank/linebank/transfer"
            };
            function findAll() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.findAll)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAll = findAll;
            function transfer(data) {
                var dfd = $.Deferred();
                nts.uk.request.ajax('com', paths.transfer, data)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.transfer = transfer;
        })(service = c.service || (c.service = {}));
    })(c = qmm006.c || (qmm006.c = {}));
})(qmm006 || (qmm006 = {}));
//# sourceMappingURL=qmm006.c.service.js.map