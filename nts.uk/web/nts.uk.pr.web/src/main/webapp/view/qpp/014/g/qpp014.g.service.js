var qpp014;
(function (qpp014) {
    var g;
    (function (g) {
        var service;
        (function (service) {
            var paths = {
                getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
            };
            function getPaymentDateProcessingList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getPaymentDateProcessingList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getPaymentDateProcessingList = getPaymentDateProcessingList;
        })(service = g.service || (g.service = {}));
    })(g = qpp014.g || (qpp014.g = {}));
})(qpp014 || (qpp014 = {}));
//# sourceMappingURL=qpp014.g.service.js.map