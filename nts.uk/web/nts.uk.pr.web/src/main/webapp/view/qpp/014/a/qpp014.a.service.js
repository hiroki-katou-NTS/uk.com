var qpp014;
(function (qpp014) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var path = {
                findPayDayProcessing: "pr/proto/paymentdata/banktransfer/findPayDayProcessing/{companyCode}/{pay_bonus_atr}",
            };
            function findAll() {
                return nts.uk.request.ajax(path.findPayDayProcessing);
            }
            service.findAll = findAll;
        })(service = a.service || (a.service = {}));
    })(a = qpp014.a || (qpp014.a = {}));
})(qpp014 || (qpp014 = {}));
//# sourceMappingURL=qpp014.a.service.js.map