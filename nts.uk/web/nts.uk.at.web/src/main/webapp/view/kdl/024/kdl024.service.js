var kdl024;
(function (kdl024) {
    var service;
    (function (service) {
        var paths = {
            getExternalBudgetList: "at/schedule/budget/findallexternalbudget",
            insertExternalBudget: "at/schedule/budget/insertexternalbudget",
            updateExternalBudget: "at/schedule/budget/updateexternalbudget",
            deleteExternalBudget: "at/schedule/budget/deleteexternalbudget"
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