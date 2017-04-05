var qpp021;
(function (qpp021) {
    var f;
    (function (f) {
        var service;
        (function (service) {
            var paths = {
                printService: "/file/paymentdata/print",
            };
            function printF(query) {
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            service.printF = printF;
        })(service = f.service || (f.service = {}));
    })(f = qpp021.f || (qpp021.f = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.f.service.js.map