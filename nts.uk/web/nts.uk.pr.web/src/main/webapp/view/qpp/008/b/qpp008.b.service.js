var qpp008;
(function (qpp008) {
    var b;
    (function (b) {
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
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getPaymentDateProcessingList = getPaymentDateProcessingList;
        })(service = b.service || (b.service = {}));
    })(b = qpp008.b || (qpp008.b = {}));
})(qpp008 || (qpp008 = {}));
//# sourceMappingURL=qpp008.b.service.js.map