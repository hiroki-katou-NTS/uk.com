var qpp008;
(function (qpp008) {
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
    })(g = qpp008.g || (qpp008.g = {}));
})(qpp008 || (qpp008 = {}));
//# sourceMappingURL=service.js.map