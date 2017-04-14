var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                averagePayItemSelect: "pr/core/averagepay/findByCompanyCode",
                averagePayItemInsert: "pr/core/averagepay/register",
                averagePayItemUpdate: "pr/core/averagepay/update"
            };
            function averagePayItemSelect() {
                return nts.uk.request.ajax(paths.averagePayItemSelect);
            }
            service.averagePayItemSelect = averagePayItemSelect;
            function averagePayItemInsert(command) {
                return nts.uk.request.ajax(paths.averagePayItemInsert, command);
            }
            service.averagePayItemInsert = averagePayItemInsert;
            function averagePayItemUpdate(command) {
                return nts.uk.request.ajax(paths.averagePayItemUpdate, command);
            }
            service.averagePayItemUpdate = averagePayItemUpdate;
        })(service = a.service || (a.service = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
//# sourceMappingURL=qmm018.a.service.js.map