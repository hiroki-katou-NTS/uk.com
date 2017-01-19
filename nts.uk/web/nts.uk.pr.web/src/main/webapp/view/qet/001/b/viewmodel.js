var qet001;
(function (qet001) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.outputSettings = ko.observable(new OutputSettings());
                    this.outputSettingDetail = ko.observable(new OutputSettingDetail());
                    this.reportItems = ko.observableArray([
                        new ReportItem('CAT1', true, 'CODE1', 'Name 1'),
                        new ReportItem('CAT2', false, 'CODE2', 'Name 2'),
                        new ReportItem('CAT4', true, 'CODE5', 'Name 5'),
                    ]);
                    this.reportItemColumns = ko.observableArray([
                        { headerText: '区分', prop: 'categoryName', width: 50 },
                        { headerText: '集約', prop: 'isAggregate', width: 30 },
                        { headerText: 'コード', prop: 'itemCode', width: 100 },
                        { headerText: '名称', prop: 'itemName', width: 100 },
                    ]);
                    this.reportItemSelected = ko.observable(null);
                    var self = this;
                    self.outputSettings().outputSettingSelectedCode.subscribe(function (newVal) {
                        if (newVal == undefined || newVal == null) {
                            return;
                        }
                        self.loadOutputSettingDetail(newVal);
                    });
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    this.loadAllOutputSetting().done(function () {
                        if (self.outputSettings().outputSettingList().length == 0) {
                            dfd.resolve();
                            return;
                        }
                        self.loadOutputSettingDetail(self.outputSettings().outputSettingList()[0].code).done(function () {
                            dfd.resolve();
                        });
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadAllOutputSetting = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    b.service.findOutputSettings().done(function (data) {
                        self.outputSettings().outputSettingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadOutputSettingDetail = function (selectedCode) {
                    var dfd = $.Deferred();
                    var self = this;
                    b.service.findOutputSettingDetail(selectedCode).done(function (data) {
                        self.outputSettingDetail(new OutputSettingDetail(data));
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var OutputSettings = (function () {
                function OutputSettings() {
                    this.searchText = ko.observable('');
                    this.outputSettingList = ko.observableArray([]);
                    this.outputSettingSelectedCode = ko.observable('');
                    this.outputSettingColumns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 90 },
                        { headerText: '名称', prop: 'name', width: 100 }]);
                }
                return OutputSettings;
            }());
            viewmodel.OutputSettings = OutputSettings;
            var OutputSettingDetail = (function () {
                function OutputSettingDetail(outputSetting) {
                    this.settingCode = ko.observable(outputSetting != undefined ? outputSetting.code : '');
                    this.settingName = ko.observable(outputSetting != undefined ? outputSetting.name : '');
                    this.isPrintOnePageEachPer = ko.observable(outputSetting != undefined ? outputSetting.onceSheetPerPerson : true);
                    this.categorySettingTabs = ko.observableArray([
                        { id: 'tab-salary-payment', title: '給与支給', content: '#salary-payment', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-salary-deduction', title: '給与控除', content: '#salary-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-salary-attendance', title: '給与勤怠', content: '#salary-attendance', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-payment', title: '賞与支給', content: '#bonus-payment', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-deduction', title: '賞与控除', content: '#bonus-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-attendance', title: '賞与勤怠', content: '#bonus-attendance', enable: ko.observable(true), visible: ko.observable(true) },
                    ]);
                    this.selectedCategory = ko.observable('tab-salary-payment');
                }
                return OutputSettingDetail;
            }());
            viewmodel.OutputSettingDetail = OutputSettingDetail;
            var ReportItem = (function () {
                function ReportItem(categoryName, isAggregate, itemCode, itemName) {
                    this.categoryName = categoryName;
                    this.isAggregate = isAggregate;
                    this.itemCode = itemCode;
                    this.itemName = itemName;
                }
                return ReportItem;
            }());
            viewmodel.ReportItem = ReportItem;
            var LayoutOutput = (function () {
                function LayoutOutput() {
                }
                LayoutOutput.WAGE_LEDGER = 0;
                LayoutOutput.WAGE_LIST = 1;
                return LayoutOutput;
            }());
            viewmodel.LayoutOutput = LayoutOutput;
            var OutputType = (function () {
                function OutputType() {
                }
                OutputType.DETAIL_ITEM = 0;
                OutputType.SUMMARY_DETAIL_ITEMS = 1;
                return OutputType;
            }());
            viewmodel.OutputType = OutputType;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qet001.b || (qet001.b = {}));
})(qet001 || (qet001 = {}));
