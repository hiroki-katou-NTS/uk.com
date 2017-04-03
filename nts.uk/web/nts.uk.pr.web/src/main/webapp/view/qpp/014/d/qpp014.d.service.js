var qpp014;
(function (qpp014) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
            };
            function getPaymentDateProcessingList() {
                return nts.uk.request.ajax(paths.getPaymentDateProcessingList);
            }
            service.getPaymentDateProcessingList = getPaymentDateProcessingList;
        })(service = d.service || (d.service = {}));
    })(d = qpp014.d || (qpp014.d = {}));
})(qpp014 || (qpp014 = {}));
//# sourceMappingURL=qpp014.d.service.js.map