var qmm006;
(function (qmm006) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                findBankAll: "basic/system/bank/find/all"
            };
            function findBankAll() {
                return nts.uk.request.ajax("com", paths.findBankAll);
            }
            service.findBankAll = findBankAll;
        })(service = b.service || (b.service = {}));
    })(b = qmm006.b || (qmm006.b = {}));
})(qmm006 || (qmm006 = {}));
//# sourceMappingURL=qmm006.b.service.js.map