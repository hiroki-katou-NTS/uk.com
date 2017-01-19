var qet001;
(function (qet001) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var servicePath = {
                findOutputSettings: 'ctx/pr/report/wageledger/outputsetting/findAll'
            };
            function findOutputSettings() {
                return nts.uk.request.ajax(servicePath.findOutputSettings);
            }
            service.findOutputSettings = findOutputSettings;
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
                var WageLedgerSettingItem = (function () {
                    function WageLedgerSettingItem() {
                    }
                    return WageLedgerSettingItem;
                }());
                model.WageLedgerSettingItem = WageLedgerSettingItem;
                var Enum = (function () {
                    function Enum() {
                    }
                    return Enum;
                }());
                model.Enum = Enum;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qet001.a || (qet001.a = {}));
})(qet001 || (qet001 = {}));
