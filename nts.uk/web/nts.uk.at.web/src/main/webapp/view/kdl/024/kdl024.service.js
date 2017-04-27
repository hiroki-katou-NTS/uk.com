var kdl024;
(function (kdl024) {
    var service;
    (function (service) {
        var path = {
            getExternalBudgetList: "at/findallexternalbudget",
            insertExternalBudget: "at/insertexternalbudget",
            updateExternalBudget: "at/updateexternalbudget",
            deleteExternalBudget: "at/deleteexternalbudget"
        };
        /**
         * get list External Budget
         */
        function getListExternalBudget() {
            var dfd = $.Deferred();
            //TODO-- service Get List
            return dfd.promise();
        }
        service.getListExternalBudget = getListExternalBudget;
        /**
         * Update Budget
         *
         */
        function updateExternalBudget(updateBudgetCmd) {
            var dfd = $.Deferred();
            //TODO --> update List
            return dfd.promise();
        }
        service.updateExternalBudget = updateExternalBudget;
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