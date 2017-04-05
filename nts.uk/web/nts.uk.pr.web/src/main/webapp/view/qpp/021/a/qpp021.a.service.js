var qpp021;
(function (qpp021) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                printService: "/file/paymentdata/print",
            };
            function print(query) {
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            service.print = print;
        })(service = a.service || (a.service = {}));
    })(a = qpp021.a || (qpp021.a = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.a.service.js.map