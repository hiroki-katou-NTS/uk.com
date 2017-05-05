var kdl024;
(function (kdl024) {
    var service;
    (function (service) {
        var paths = {
            getExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
            insertExternalBudget: "at/schedule/budget/external/insertexternalbudget",
            updateExternalBudget: "at/schedule/budget/external/updateexternalbudget",
            deleteExternalBudget: "at/schedule/budget/external/deleteexternalbudget"
        };
        function getListExternalBudget() {
            var dfd = $.Deferred();
            nts.uk.request.ajax("at", paths.getExternalBudgetList)
                .done(function (res) {
                dfd.resolve(res);
            })
                .fail(function (res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }
        service.getListExternalBudget = getListExternalBudget;
        function updateExternalBudget(updateBudgetCmd) {
            var dfd = $.Deferred();
            var command = {};
            command.externalBudgetCode = updateBudgetCmd.externalBudgetCode();
            command.externalBudgetName = updateBudgetCmd.externalBudgetName();
            command.budgetAtr = Number(updateBudgetCmd.budgetAtr());
            command.unitAtr = updateBudgetCmd.unitAtr();
            nts.uk.request.ajax(paths.updateExternalBudget, command)
                .done(function (res) {
                dfd.resolve(res);
            })
                .fail(function (res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }
        service.updateExternalBudget = updateExternalBudget;
        function insertExternalBudget(insertBudgetCmd) {
            var dfd = $.Deferred();
            var command = {};
            command.externalBudgetCode = insertBudgetCmd.externalBudgetCode();
            command.externalBudgetName = insertBudgetCmd.externalBudgetName();
            command.budgetAtr = Number(insertBudgetCmd.budgetAtr());
            command.unitAtr = insertBudgetCmd.unitAtr();
            nts.uk.request.ajax(paths.insertExternalBudget, command)
                .done(function (res) {
                dfd.resolve(res);
            })
                .fail(function (res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }
        service.insertExternalBudget = insertExternalBudget;
        function deleteExternalBudget(deleteBudgetCmd) {
            var dfd = $.Deferred();
            var command = {};
            command.externalBudgetCode = deleteBudgetCmd.externalBudgetCode();
            command.externalBudgetName = deleteBudgetCmd.externalBudgetName();
            command.budgetAtr = Number(deleteBudgetCmd.budgetAtr());
            command.unitAtr = deleteBudgetCmd.unitAtr();
            nts.uk.request.ajax(paths.deleteExternalBudget, command)
                .done(function (res) {
                dfd.resolve(res);
            })
                .fail(function (res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }
        service.deleteExternalBudget = deleteExternalBudget;
        var model;
        (function (model) {
            var ExternalBudgetDto = (function () {
                function ExternalBudgetDto() {
                }
                return ExternalBudgetDto;
            }());
            model.ExternalBudgetDto = ExternalBudgetDto;
        })(model = service.model || (service.model = {}));
    })(service = kdl024.service || (kdl024.service = {}));
})(kdl024 || (kdl024 = {}));
//# sourceMappingURL=kdl024.service.js.map