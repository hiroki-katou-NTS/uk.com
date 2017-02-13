var qet001;
(function (qet001) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var servicePath = {
                findOutputSettingDetail: 'ctx/pr/report/wageledger/outputsetting/find',
                findAggregateItems: 'ctx/pr/report/wageledger/aggregateitem/findAll',
                findMasterItems: '???',
                saveOutputSetting: 'ctx/pr/report/wageledger/outputsetting/save',
                removeOutputSetting: 'ctx/pr/report/wageledger/outputsetting/remove',
            };
            function findOutputSettingDetail(settingCode) {
                return nts.uk.request.ajax(servicePath.findOutputSettingDetail + '/' + settingCode);
            }
            service.findOutputSettingDetail = findOutputSettingDetail;
            function findAggregateItems() {
                return nts.uk.request.ajax(servicePath.findAggregateItems);
            }
            service.findAggregateItems = findAggregateItems;
            function saveOutputSetting(settingDetail) {
                var dfd = $.Deferred();
                var categorySettingData = [];
                settingDetail.categorySettings().forEach(function (setting) {
                    for (var i = 0; i < setting.outputItems().length; i++) {
                        setting.outputItems()[i].orderNumber = i;
                    }
                });
                var data = {
                    code: settingDetail.settingCode(),
                    name: settingDetail.settingName(),
                    onceSheetPerPerson: settingDetail.isPrintOnePageEachPer(),
                    categorySettings: ko.toJS(settingDetail.categorySettings()),
                    createMode: settingDetail.isCreateMode()
                };
                nts.uk.request.ajax(servicePath.saveOutputSetting, data).done(function () {
                    dfd.resolve();
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.saveOutputSetting = saveOutputSetting;
            function removeOutputSetting(code) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(servicePath.removeOutputSetting, { code: code }).done(function () {
                    dfd.resolve();
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.removeOutputSetting = removeOutputSetting;
            function findMasterItems() {
                var dfd = $.Deferred();
                var data = [];
                for (var i = 0; i < 10; i++) {
                    data.push({ code: 'MI' + i, name: 'Master item' + i, paymentType: 'Salary', category: 'Payment' });
                }
                for (var i = 0; i < 10; i++) {
                    data.push({ code: 'MI' + i, name: 'Master item' + i, paymentType: 'Salary', category: 'Deduction' });
                }
                dfd.resolve(data);
                return dfd.promise();
            }
            service.findMasterItems = findMasterItems;
        })(service = b.service || (b.service = {}));
    })(b = qet001.b || (qet001.b = {}));
})(qet001 || (qet001 = {}));
