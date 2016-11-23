var qpp004;
(function (qpp004) {
    var service;
    (function (service) {
        var paths = {
            getPaymentDateProcessing: "pr/proto/paymentdatemaster/processing/find"
        };
        /**
         * Get list payment date processing.
         */
        function getPaymentDateProcessingMaster() {
            var dfd = $.Deferred();
            nts.uk.request.ajax(paths.getPaymentDateProcessing)
                .done(function (res) {
                dfd.resolve(res);
            })
                .fail(function (res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }
        service.getPaymentDateProcessingMaster = getPaymentDateProcessingMaster;
    })(service = qpp004.service || (qpp004.service = {}));
})(qpp004 || (qpp004 = {}));
