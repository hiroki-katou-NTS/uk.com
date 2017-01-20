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
                    this.reportItems = ko.observableArray([]);
                    this.reportItemColumns = ko.observableArray([
                        { headerText: '区分', prop: 'categoryNameJa', width: 50 },
                        { headerText: '集約', prop: 'isAggregate', width: 40,
                            formatter: function (data) {
                                if (data == 'true') {
                                    return '<div class="center"><i class="icon icon-dot"></i></div>';
                                }
                                return '';
                            }
                        },
                        { headerText: 'コード', prop: 'itemCode', width: 100 },
                        { headerText: '名称', prop: 'itemName', width: 100 },
                    ]);
                    this.reportItemSelected = ko.observable(null);
                    var self = this;
                    self.outputSettings().outputSettingSelectedCode.subscribe(function (newVal) {
                        if (newVal == undefined || newVal == null) {
                            self.outputSettingDetail(new OutputSettingDetail());
                            return;
                        }
                        self.loadOutputSettingDetail(newVal);
                    });
                    self.outputSettingDetail.subscribe(function (data) {
                        if (data == undefined || data == null || data.categorySettings().length == 0) {
                            self.reportItems([]);
                            return;
                        }
                        var reportItemList = [];
                        data.categorySettings().forEach(function (setting) {
                            var categoryName = setting.category;
                            setting.outputItems.forEach(function (item) {
                                reportItemList.push(new ReportItem(categoryName, item.isAggregateItem, item.code, item.name));
                            });
                        });
                        self.reportItems(reportItemList);
                    });
                }
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    var outputSettings = nts.uk.ui.windows.getShared('outputSettings');
                    var selectedSettingCode = nts.uk.ui.windows.getShared('selectedCode');
                    if (outputSettings != undefined) {
                        self.outputSettings().outputSettingList(outputSettings);
                    }
                    self.outputSettingDetail().isCreateMode(outputSettings == undefined || outputSettings.length == 0);
                    self.outputSettings().outputSettingSelectedCode(selectedSettingCode);
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.close = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.save = function () {
                    var self = this;
                    if (self.outputSettingDetail().settingCode() == '' || self.outputSettingDetail().settingName() == '') {
                        nts.uk.ui.dialog.alert('未入力エラー');
                        return;
                    }
                };
                ScreenModel.prototype.remove = function () {
                    var self = this;
                    if (self.outputSettings().outputSettingSelectedCode() == '') {
                        nts.uk.ui.dialog.alert('未選択エラー');
                        return;
                    }
                };
                ScreenModel.prototype.loadOutputSettingDetail = function (selectedCode) {
                    var dfd = $.Deferred();
                    var self = this;
                    b.service.findOutputSettingDetail(selectedCode).done(function (data) {
                        self.outputSettingDetail(new OutputSettingDetail(data));
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.switchToCreateMode = function () {
                    this.outputSettingDetail(new OutputSettingDetail());
                    this.outputSettings().outputSettingSelectedCode('');
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
                    this.isPrintOnePageEachPer = ko.observable(outputSetting != undefined ? outputSetting.onceSheetPerPerson : false);
                    this.categorySettingTabs = ko.observableArray([
                        { id: 'tab-salary-payment', title: '給与支給', content: '#salary-payment', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-salary-deduction', title: '給与控除', content: '#salary-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-salary-attendance', title: '給与勤怠', content: '#salary-attendance', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-payment', title: '賞与支給', content: '#bonus-payment', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-deduction', title: '賞与控除', content: '#bonus-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-attendance', title: '賞与勤怠', content: '#bonus-attendance', enable: ko.observable(true), visible: ko.observable(true) },
                    ]);
                    this.selectedCategory = ko.observable('tab-salary-payment');
                    this.isCreateMode = ko.observable(outputSetting == undefined);
                    this.categorySettings = ko.observableArray(outputSetting != undefined ? outputSetting.categorySettings : []);
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
                    var self = this;
                    switch (categoryName) {
                        case Category.PAYMENT:
                            self.categoryNameJa = '支給';
                            break;
                        case Category.DEDUCTION:
                            self.categoryNameJa = '控除';
                            break;
                        case Category.ATTENDANCE:
                            self.categoryNameJa = '勤怠';
                            break;
                        default:
                            self.categoryNameJa = '';
                    }
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
            var Category = (function () {
                function Category() {
                }
                Category.PAYMENT = 'Payment';
                Category.DEDUCTION = 'Deduction';
                Category.ATTENDANCE = 'Attendance';
                return Category;
            }());
            viewmodel.Category = Category;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qet001.b || (qet001.b = {}));
})(qet001 || (qet001 = {}));
