var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm002;
                (function (qmm002) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                getBankList: "basic/system/bank/find/all",
                                addBranchList: "basic/system/bank/branch/add",
                                updateBranchList: "basic/system/bank/branch/update",
                                removeBranch: "basic/system/bank/branch/remove",
                                removeBank: "basic/system/bank/remove",
                                check: "basic/system/bank/find/check"
                            };
                            function getBankList() {
                                return nts.uk.request.ajax("com", paths.getBankList);
                            }
                            service.getBankList = getBankList;
                            function checkBankList() {
                                return nts.uk.request.ajax("com", paths.check);
                            }
                            service.checkBankList = checkBankList;
                            function addBank(isCreated, bankInfo) {
                                var path = isCreated ? paths.addBranchList : paths.updateBranchList;
                                return nts.uk.request.ajax("com", path, bankInfo);
                            }
                            service.addBank = addBank;
                            function removeBank(isParentNode, bankCode, branchId) {
                                var path = isParentNode ? paths.removeBank : paths.removeBranch;
                                var obj = {
                                    bankCode: bankCode,
                                    branchId: branchId
                                };
                                return nts.uk.request.ajax("com", path, obj);
                            }
                            service.removeBank = removeBank;
                        })(service = a.service || (a.service = {}));
                    })(a = qmm002.a || (qmm002.a = {}));
                })(qmm002 = view.qmm002 || (view.qmm002 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map