var qpp008;
(function (qpp008) {
    var c;
    (function (c) {
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
        })(service = c.service || (c.service = {}));
    })(c = qpp008.c || (qpp008.c = {}));
})(qpp008 || (qpp008 = {}));
