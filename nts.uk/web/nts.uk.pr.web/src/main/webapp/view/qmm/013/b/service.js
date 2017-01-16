var qmm013;
(function (qmm013) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
            };
            function getPaymentDateProcessingList() {
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            service.getPaymentDateProcessingList = getPaymentDateProcessingList;
        })(service = b.service || (b.service = {}));
    })(b = qmm013.b || (qmm013.b = {}));
})(qmm013 || (qmm013 = {}));
