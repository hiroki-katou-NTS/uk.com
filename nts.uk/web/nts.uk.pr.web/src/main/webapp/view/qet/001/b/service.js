var qet001;
(function (qet001) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var servicePath = {
                findOutputSettings: 'ctx/pr/report/wageledger/outputsetting/findAll',
                findOutputSettingDetail: 'ctx/pr/report/wageledger/outputsetting/find'
            };
            function findOutputSettings() {
                return nts.uk.request.ajax(servicePath.findOutputSettings);
            }
            service.findOutputSettings = findOutputSettings;
            function findOutputSettingDetail(settingCode) {
                return nts.uk.request.ajax(servicePath.findOutputSettingDetail + '/' + settingCode);
            }
            service.findOutputSettingDetail = findOutputSettingDetail;
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
        })(service = b.service || (b.service = {}));
    })(b = qet001.b || (qet001.b = {}));
})(qet001 || (qet001 = {}));
