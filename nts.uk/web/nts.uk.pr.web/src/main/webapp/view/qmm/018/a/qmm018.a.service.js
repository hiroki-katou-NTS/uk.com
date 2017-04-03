var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                averagePayItemSelect: "pr/core/averagepay/findByCompanyCode",
                averagePayItemSelectBySalary: "pr/core/averagepay/findByItemSalary",
                averagePayItemSelectByAttend: "pr/core/averagepay/findByItemAttend",
                averagePayItemInsert: "pr/core/averagepay/register",
                averagePayItemUpdate: "pr/core/averagepay/update"
            };
            /**
             * select average pay item
             */
            function averagePayItemSelect() {
                return nts.uk.request.ajax(paths.averagePayItemSelect);
            }
            service.averagePayItemSelect = averagePayItemSelect;
            /**
             * select items master by salary item code
             */
            function averagePayItemSelectBySalary() {
                return nts.uk.request.ajax(paths.averagePayItemSelectBySalary);
            }
            service.averagePayItemSelectBySalary = averagePayItemSelectBySalary;
            /**
             * select items master by attend item code
             */
            function averagePayItemSelectByAttend() {
                return nts.uk.request.ajax(paths.averagePayItemSelectByAttend);
            }
            service.averagePayItemSelectByAttend = averagePayItemSelectByAttend;
            /**
             * insert average pay item, salary items, attend items
             */
            function averagePayItemInsert(command) {
                return nts.uk.request.ajax(paths.averagePayItemInsert, command);
            }
            service.averagePayItemInsert = averagePayItemInsert;
            /**
             * update average pay item, salary items, attend items
             */
            function averagePayItemUpdate(command) {
                return nts.uk.request.ajax(paths.averagePayItemUpdate, command);
            }
            service.averagePayItemUpdate = averagePayItemUpdate;
        })(service = a.service || (a.service = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
//# sourceMappingURL=qmm018.a.service.js.map