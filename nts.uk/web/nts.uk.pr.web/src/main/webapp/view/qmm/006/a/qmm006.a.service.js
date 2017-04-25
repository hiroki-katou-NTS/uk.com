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
            /**
             * remove data (lineBank) in database
             */
            function remove(command) {
                return nts.uk.request.ajax("com", paths.remove, command);
            }
            service.remove = remove;
            /**
             * update or insert data (lineBank) in database
             * define update mode or insert mode base-on isEnable property
             */
            function saveData(isEnable, command) {
                var path = isEnable ? paths.saveData : paths.update;
                return nts.uk.request.ajax("com", path, command);
            }
            service.saveData = saveData;
            /**
             * get data from database to screen
             */
            function findAll() {
                return nts.uk.request.ajax("com", paths.findAll);
            }
            service.findAll = findAll;
            /**
             * get data of Bank
             */
            function findBankAll() {
                return nts.uk.request.ajax("com", paths.findBankAll);
            }
            service.findBankAll = findBankAll;
            /**
             * check exist data of Bank
             */
            function checkExistBankAndBranch() {
                return nts.uk.request.ajax("com", paths.checkExistBankAndBranch);
            }
            service.checkExistBankAndBranch = checkExistBankAndBranch;
        })(service = a.service || (a.service = {}));
    })(a = qmm006.a || (qmm006.a = {}));
})(qmm006 || (qmm006 = {}));
