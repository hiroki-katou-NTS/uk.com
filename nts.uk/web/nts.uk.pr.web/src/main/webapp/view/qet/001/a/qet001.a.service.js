var qet001;
(function (qet001) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            // Service paths.
            var servicePath = {
                findOutputSettings: 'ctx/pr/report/wageledger/outputsetting/findAll',
                printReport: 'screen/pr/qet001/print'
            };
            /**
             * Find all output setting services.
             */
            function findOutputSettings() {
                return nts.uk.request.ajax(servicePath.findOutputSettings);
            }
            service.findOutputSettings = findOutputSettings;
            /**
             * Print report service.
             */
            function printReport(data) {
                var dfd = $.Deferred();
                var dataJson = {
                    targetYear: data.targetYear(),
                    isAggreatePreliminaryMonth: data.isAggreatePreliminaryMonth(),
                    layoutType: data.layoutSelected(),
                    isPageBreakIndicator: data.isPageBreakIndicator(),
                    outputType: data.outputTypeSelected(),
                    outputSettingCode: data.outputSettingSelectedCode()
                };
                nts.uk.request.exportFile(servicePath.printReport, dataJson).done(function () {
                    dfd.resolve();
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.printReport = printReport;
            /**
            * Model namespace.
            */
            var model;
            (function (model) {
                var WageLedgerOutputSetting = (function () {
                    function WageLedgerOutputSetting() {
                    }
                    return WageLedgerOutputSetting;
                }());
                model.WageLedgerOutputSetting = WageLedgerOutputSetting;
                var WageledgerCategorySetting = (function () {
                    function WageledgerCategorySetting() {
                    }
                    return WageledgerCategorySetting;
                }());
                model.WageledgerCategorySetting = WageledgerCategorySetting;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qet001.a || (qet001.a = {}));
})(qet001 || (qet001 = {}));
//# sourceMappingURL=qet001.a.service.js.map