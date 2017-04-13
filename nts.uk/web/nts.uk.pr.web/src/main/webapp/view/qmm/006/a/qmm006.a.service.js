var qmm006;
(function (qmm006) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                saveData: "basic/system/bank/linebank/add",
                findAll: "basic/system/bank/linebank/findAll",
                update: "basic/system/bank/linebank/update",
                remove: "basic/system/bank/linebank/remove",
                findBankAll: "basic/system/bank/find/all",
                checkExistBankAndBranch: "basic/system/bank/find/check"
            };
            function remove(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.remove, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.remove = remove;
            function saveData(isEnable, command) {
                var dfd = $.Deferred();
                var path = isEnable ? paths.saveData : paths.update;
                nts.uk.request.ajax("com", path, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.saveData = saveData;
            function findAll() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.findAll)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAll = findAll;
            function findBankAll() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.findBankAll)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findBankAll = findBankAll;
            function checkExistBankAndBranch() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.checkExistBankAndBranch)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.checkExistBankAndBranch = checkExistBankAndBranch;
        })(service = a.service || (a.service = {}));
    })(a = qmm006.a || (qmm006.a = {}));
})(qmm006 || (qmm006 = {}));
