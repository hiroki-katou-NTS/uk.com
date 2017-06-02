var qpp014;
(function (qpp014) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var path = {
                findPayDayProcessing: "pr/proto/payment/banktransfer/findPayDayProcessing",
            };
            function findAll() {
                var _path = nts.uk.text.format(path.findPayDayProcessing);
                return nts.uk.request.ajax(_path);
            }
            service.findAll = findAll;
        })(service = a.service || (a.service = {}));
    })(a = qpp014.a || (qpp014.a = {}));
})(qpp014 || (qpp014 = {}));
//# sourceMappingURL=qpp014.a.service.js.map