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
                            function checkBankList() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax("com", paths.check)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.checkBankList = checkBankList;
                            function addBank(isCreated, bankInfo) {
                                var dfd = $.Deferred();
                                var path = isCreated ? paths.addBranchList : paths.updateBranchList;
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
                            function removeBank(isParentNode, bankCode, branchId) {
                                var dfd = $.Deferred();
                                var path = isParentNode ? paths.removeBank : paths.removeBranch;
                                var obj = {
                                    bankCode: bankCode,
                                    branchId: branchId
                                };
                                nts.uk.request.ajax("com", path, obj)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.removeBank = removeBank;
                        })(service = a.service || (a.service = {}));
                    })(a = qmm002.a || (qmm002.a = {}));
                })(qmm002 = view.qmm002 || (view.qmm002 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm002.a.service.js.map