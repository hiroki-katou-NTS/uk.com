var kdl024;
(function (kdl024) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
                insertExternalBudget: "at/schedule/budget/external/insertexternalbudget",
                updateExternalBudget: "at/schedule/budget/external/updateexternalbudget",
                deleteExternalBudget: "at/schedule/budget/external/deleteexternalbudget"
            };
            /**
             * get list External Budget
             */
            function getListExternalBudget() {
                return nts.uk.request.ajax("at", paths.getExternalBudgetList);
            }
            service.getListExternalBudget = getListExternalBudget;
            /**
             * Update Budget
             *
             */
            function updateExternalBudget(updateBudgetCmd) {
                var command = {};
                command.externalBudgetCode = updateBudgetCmd.externalBudgetCode();
                command.externalBudgetName = updateBudgetCmd.externalBudgetName();
                command.budgetAtr = Number(updateBudgetCmd.budgetAtr());
                command.unitAtr = updateBudgetCmd.unitAtr();
                return nts.uk.request.ajax(paths.updateExternalBudget, command);
            }
            service.updateExternalBudget = updateExternalBudget;
            //Insert 
            function insertExternalBudget(insertBudgetCmd) {
                var command = {};
                command.externalBudgetCode = insertBudgetCmd.externalBudgetCode();
                command.externalBudgetName = insertBudgetCmd.externalBudgetName();
                command.budgetAtr = Number(insertBudgetCmd.budgetAtr());
                command.unitAtr = insertBudgetCmd.unitAtr();
                return nts.uk.request.ajax(paths.insertExternalBudget, command);
            }
            service.insertExternalBudget = insertExternalBudget;
            //Delete
            function deleteExternalBudget(deleteBudgetCmd) {
                var command = {};
                command.externalBudgetCode = deleteBudgetCmd.externalBudgetCode();
                command.externalBudgetName = deleteBudgetCmd.externalBudgetName();
                command.budgetAtr = Number(deleteBudgetCmd.budgetAtr());
                command.unitAtr = deleteBudgetCmd.unitAtr();
                return nts.uk.request.ajax(paths.deleteExternalBudget, command);
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
        })(service = a.service || (a.service = {}));
    })(a = kdl024.a || (kdl024.a = {}));
})(kdl024 || (kdl024 = {}));
//# sourceMappingURL=kdl024.a.service.js.map