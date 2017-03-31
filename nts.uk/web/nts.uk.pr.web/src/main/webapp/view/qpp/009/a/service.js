var qpp009;
(function (qpp009) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var servicePath = {
                printService: "/screen/pr/qpp009/generate",
            };
            function printService(data) {
                var dfd = $.Deferred();
                var reportQuery = {
                    targetYear: data.targetYear(),
                    selectedDivision: data.selectedDivision(),
                    isPrintDetailItem: data.detailItemsSetting().isPrintDetailItem(),
                    isPrintTotalOfDepartment: data.detailItemsSetting().isPrintTotalOfDepartment(),
                    isPrintDepHierarchy: data.detailItemsSetting().isPrintDepHierarchy(),
                    selectedLevels: data.detailItemsSetting().selectedLevels(),
                    isCalculateTotal: data.detailItemsSetting().isCalculateTotal(),
                    selectedBreakPageCode: data.printSetting().selectedBreakPageCode(),
                    selectedUse2000yen: data.printSetting().selectedUse2000yen(),
                    selectedBreakPageHierarchyCode: data.printSetting().selectedBreakPageHierarchyCode(),
                    isBreakPageByAccumulated: data.printSetting().isBreakPageByAccumulated()
                };
                nts.uk.request.exportFile(servicePath.printService, reportQuery)
                    .done(function () {
                    dfd.resolve();
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.printService = printService;
        })(service = a.service || (a.service = {}));
    })(a = qpp009.a || (qpp009.a = {}));
})(qpp009 || (qpp009 = {}));
//# sourceMappingURL=service.js.map