var qmm020;
(function (qmm020) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                getEmployAllotSettingHeaderList: "pr/core/allot/findallemployeeallotheader",
                getEmployAllotSettingDetailList: "pr/core/allot/findallemployeeallotdetail",
            };
            function getEmployeeAllotHeaderList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getEmployAllotSettingHeaderList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getEmployeeAllotHeaderList = getEmployeeAllotHeaderList;
            function getEmployeeAllotDetailList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getEmployAllotSettingDetailList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getEmployeeAllotDetailList = getEmployeeAllotDetailList;
            var model;
            (function (model) {
                var EmployeeAllotSettingHeaderDto = (function () {
                    function EmployeeAllotSettingHeaderDto() {
                    }
                    return EmployeeAllotSettingHeaderDto;
                }());
                model.EmployeeAllotSettingHeaderDto = EmployeeAllotSettingHeaderDto;
                var EmployeeAllotSettingDetailDto = (function () {
                    function EmployeeAllotSettingDetailDto() {
                    }
                    return EmployeeAllotSettingDetailDto;
                }());
                model.EmployeeAllotSettingDetailDto = EmployeeAllotSettingDetailDto;
            })(model = service.model || (service.model = {}));
        })(service = c.service || (c.service = {}));
    })(c = qmm020.c || (qmm020.c = {}));
})(qmm020 || (qmm020 = {}));
//# sourceMappingURL=qmm020.c.service.js.map