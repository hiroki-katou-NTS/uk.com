var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp007;
                (function (qpp007) {
                    var b;
                    (function (b) {
                        var service;
                        (function (service) {
                            var paths = {
                                saveSalaryPrintSetting: "ctx/pr/report/salarydetail/printsetting/save",
                                findSalaryPrintSetting: "ctx/pr/report/salarydetail/printsetting/find"
                            };
                            function saveSalaryPrintSetting(data) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.saveSalaryPrintSetting, data).done(function () {
                                    return dfd.resolve();
                                });
                                return dfd.promise();
                            }
                            service.saveSalaryPrintSetting = saveSalaryPrintSetting;
                            function findSalaryPrintSetting() {
                                var dfd = $.Deferred();
                                return nts.uk.request.ajax(paths.findSalaryPrintSetting);
                            }
                            service.findSalaryPrintSetting = findSalaryPrintSetting;
                        })(service = b.service || (b.service = {}));
                    })(b = qpp007.b || (qpp007.b = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp007.b.service.js.map