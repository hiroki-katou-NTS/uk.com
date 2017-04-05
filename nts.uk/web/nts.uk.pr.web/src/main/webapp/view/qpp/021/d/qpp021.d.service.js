var qpp021;
(function (qpp021) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                printService: "/file/paymentdata/print",
            };
            function printD(query) {
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            service.printD = printD;
        })(service = d.service || (d.service = {}));
    })(d = qpp021.d || (qpp021.d = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.d.service.js.map