var qpp004;
(function (qpp004) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
            };
            /**
             * Get list payment date processing.
             */
            function getPaymentDateProcessingMasterList() {
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
            service.getPaymentDateProcessingMasterList = getPaymentDateProcessingMasterList;
        })(service = a.service || (a.service = {}));
    })(a = qpp004.a || (qpp004.a = {}));
})(qpp004 || (qpp004 = {}));
//# sourceMappingURL=service.js.map