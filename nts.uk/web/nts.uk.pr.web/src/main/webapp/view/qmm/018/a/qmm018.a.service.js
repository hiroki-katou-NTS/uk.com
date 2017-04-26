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
            /**
             * select average pay item
             */
            function averagePayItemSelect() {
                return nts.uk.request.ajax(paths.averagePayItemSelect);
            }
            service.averagePayItemSelect = averagePayItemSelect;
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
