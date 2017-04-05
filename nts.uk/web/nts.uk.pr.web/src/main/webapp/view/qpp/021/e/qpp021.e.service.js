var qpp021;
(function (qpp021) {
    var e;
    (function (e) {
        var service;
        (function (service) {
            var paths = {
                printService: "/file/paymentdata/print",
            };
            function printE(query) {
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            service.printE = printE;
        })(service = e.service || (e.service = {}));
    })(e = qpp021.e || (qpp021.e = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.e.service.js.map