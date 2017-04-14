var qmm002;
(function (qmm002) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                getBankList: "basic/system/bank/find/all",
                addBank: "basic/system/bank/add",
                updateBank: "basic/system/bank/update",
                removeBank: "basic/system/bank/remove"
            };
            function getBankList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getBankList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getBankList = getBankList;
            function addBank(isCreated, bankInfo) {
                var dfd = $.Deferred();
                var path = isCreated ? paths.addBank : paths.updateBank;
                nts.uk.request.ajax("com", path, bankInfo)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addBank = addBank;
            function removeBank(bankInfo) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.removeBank, bankInfo)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.removeBank = removeBank;
        })(service = d.service || (d.service = {}));
    })(d = qmm002.d || (qmm002.d = {}));
})(qmm002 || (qmm002 = {}));
//# sourceMappingURL=service.js.map