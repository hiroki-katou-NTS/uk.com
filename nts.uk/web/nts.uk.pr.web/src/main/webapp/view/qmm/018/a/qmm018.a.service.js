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
                averagePayItemUpdate: "pr/core/averagepay/update",
                itemSalaryUpdate: "",
                itemAttendUpdate: ""
            };
            function averagePayItemSelect() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.averagePayItemSelect)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.averagePayItemSelect = averagePayItemSelect;
            function averagePayItemSelectBySalary() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.averagePayItemSelectBySalary)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.averagePayItemSelectBySalary = averagePayItemSelectBySalary;
            function averagePayItemSelectByAttend() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.averagePayItemSelectByAttend)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.averagePayItemSelectByAttend = averagePayItemSelectByAttend;
            function averagePayItemInsert(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.averagePayItemInsert, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.averagePayItemInsert = averagePayItemInsert;
            function averagePayItemUpdate(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.averagePayItemUpdate, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.averagePayItemUpdate = averagePayItemUpdate;
            function itemSalaryUpdate() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.itemSalaryUpdate))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.itemSalaryUpdate = itemSalaryUpdate;
            function itemAttendUpdate() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.itemAttendUpdate))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.itemAttendUpdate = itemAttendUpdate;
        })(service = a.service || (a.service = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
