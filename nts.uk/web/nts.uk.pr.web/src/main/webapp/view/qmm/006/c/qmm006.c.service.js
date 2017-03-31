var qmm006;
(function (qmm006) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                findAll: "basic/system/bank/linebank/findAll",
                transfer: "basic/system/bank/linebank/transfer"
            };
            function findAll() {
                return nts.uk.request.ajax("com", paths.findAll);
            }
            service.findAll = findAll;
            function transfer(data) {
                return nts.uk.request.ajax('com', paths.transfer, data);
            }
            service.transfer = transfer;
        })(service = c.service || (c.service = {}));
    })(c = qmm006.c || (qmm006.c = {}));
})(qmm006 || (qmm006 = {}));
//# sourceMappingURL=qmm006.c.service.js.map