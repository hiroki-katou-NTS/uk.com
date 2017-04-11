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
                return nts.uk.request.ajax("com", paths.getBankList);
            }
            service.getBankList = getBankList;
            function addBank(isCreated, bankInfo) {
                var path = isCreated ? paths.addBank : paths.updateBank;
                return nts.uk.request.ajax("com", path, bankInfo);
            }
            service.addBank = addBank;
            function removeBank(bankInfo) {
                return nts.uk.request.ajax("com", paths.removeBank, bankInfo);
            }
            service.removeBank = removeBank;
        })(service = d.service || (d.service = {}));
    })(d = qmm002.d || (qmm002.d = {}));
})(qmm002 || (qmm002 = {}));
