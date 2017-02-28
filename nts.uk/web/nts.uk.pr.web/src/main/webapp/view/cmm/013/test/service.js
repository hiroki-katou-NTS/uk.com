var cmm013;
(function (cmm013) {
    var test;
    (function (test) {
        var viewmodel;
        (function (viewmodel) {
            var service;
            (function (service) {
                var paths = {
                    getBankList: "basic/system/bank/find/all",
                    addBranchList: "basic/system/bank/branch/add",
                    updateBranchList: "basic/system/bank/branch/update"
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
            })(service = viewmodel.service || (viewmodel.service = {}));
        })(viewmodel = test.viewmodel || (test.viewmodel = {}));
    })(test = cmm013.test || (cmm013.test = {}));
})(cmm013 || (cmm013 = {}));
