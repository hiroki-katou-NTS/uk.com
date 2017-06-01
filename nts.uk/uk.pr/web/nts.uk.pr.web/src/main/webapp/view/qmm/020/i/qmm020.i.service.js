var qmm020;
(function (qmm020) {
    var i;
    (function (i) {
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
        })(service = i.service || (i.service = {}));
    })(i = qmm020.i || (qmm020.i = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.i.service.js.map