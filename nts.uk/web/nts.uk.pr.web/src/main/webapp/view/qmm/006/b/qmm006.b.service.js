var qmm006;
(function (qmm006) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                findBankAll: "basic/system/bank/find/all"
            };
            function findBankAll() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.findBankAll)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findBankAll = findBankAll;
        })(service = b.service || (b.service = {}));
    })(b = qmm006.b || (qmm006.b = {}));
})(qmm006 || (qmm006 = {}));
//# sourceMappingURL=qmm006.b.service.js.map