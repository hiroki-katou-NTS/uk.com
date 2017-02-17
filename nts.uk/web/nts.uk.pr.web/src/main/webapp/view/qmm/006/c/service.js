var qmm006;
(function (qmm006) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                findAll: "basic/system/bank/linebank/findAll"
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
        })(service = c.service || (c.service = {}));
    })(c = qmm006.c || (qmm006.c = {}));
})(qmm006 || (qmm006 = {}));
