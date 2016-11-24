var qpp004;
(function (qpp004) {
    var l;
    (function (l) {
        var service;
        (function (service) {
            var paths = {
                createpaymentdata: "pr/proto/paymentdata/add"
            };
            /**
             * Get list payment date processing.
             */
            function createPaymentData(param) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.createpaymentdata, param)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.createPaymentData = createPaymentData;
        })(service = l.service || (l.service = {}));
    })(l = qpp004.l || (qpp004.l = {}));
})(qpp004 || (qpp004 = {}));
