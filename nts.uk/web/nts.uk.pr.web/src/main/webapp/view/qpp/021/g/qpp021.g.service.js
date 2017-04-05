var qpp021;
(function (qpp021) {
    var g;
    (function (g) {
        var service;
        (function (service) {
            var paths = {
                printService: "/file/paymentdata/print",
            };
            function printG(query) {
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            service.printG = printG;
        })(service = g.service || (g.service = {}));
    })(g = qpp021.g || (qpp021.g = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.g.service.js.map