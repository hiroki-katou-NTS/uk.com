var qpp014;
(function (qpp014) {
    var h;
    (function (h) {
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
        })(service = h.service || (h.service = {}));
    })(h = qpp014.h || (qpp014.h = {}));
})(qpp014 || (qpp014 = {}));
//# sourceMappingURL=qpp014.h.service.js.map