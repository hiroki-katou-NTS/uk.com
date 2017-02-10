var qet001;
(function (qet001) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.outputSettings = ko.observable(new OutputSettings());
                    this.outputSettingDetail = ko.observable(new OutputSettingDetail([], []));
                    this.reportItems = ko.observableArray([]);
                    this.isLoading = ko.observable(true);
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
                    this.aggregateItemsList = [];
                    this.masterItemList = [];
                    this.hasUpdate = ko.observable(false);
                    var self = this;
                    self.outputSettings().outputSettingSelectedCode.subscribe(function (newVal) {
                        self.isLoading(true);
                        if (newVal == undefined || newVal == null || newVal == '') {
                            self.outputSettingDetail(new OutputSettingDetail(self.aggregateItemsList, self.masterItemList));
                            self.isLoading(false);
                            return;
                        }
                        self.loadOutputSettingDetail(newVal);
                        self.isLoading(false);
                    });
                    self.outputSettingDetail.subscribe(function (data) {
                        self.reloadReportItem();
                        data.reloadReportItems = self.reloadReportItem.bind(self);
                    });
                }
                ScreenModel.prototype.reloadReportItem = function () {
                    var self = this;
                    var data = self.outputSettingDetail();
                    if (data == undefined || data == null || data.categorySettings().length == 0) {
                        self.reportItems([]);
                        return;
                    }
                    var reportItemList = [];
                    data.categorySettings().forEach(function (setting) {
                        var categoryName = setting.category;
                        setting.outputItems().forEach(function (item) {
                            reportItemList.push(new ReportItem(categoryName, item.isAggregateItem, item.code, item.name));
                        });
                    });
                    self.reportItems(reportItemList);
                };
                ScreenModel.prototype.start = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    var outputSettings = nts.uk.ui.windows.getShared('outputSettings');
                    var selectedSettingCode = nts.uk.ui.windows.getShared('selectedCode');
                    $.when(self.loadAggregateItems(), self.loadMasterItems()).done(function () {
                        if (outputSettings != undefined) {
                            self.outputSettings().outputSettingList(outputSettings);
                        }
                        self.outputSettingDetail().isCreateMode(outputSettings == undefined || outputSettings.length == 0);
                        self.outputSettings().outputSettingSelectedCode(selectedSettingCode);
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.close = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.save = function () {
                    var self = this;
                    if (self.outputSettingDetail().settingCode() == '') {
                        $('#code-input').ntsError('set', '未入力エラー');
                    }
                    if (self.outputSettingDetail().settingName() == '') {
                        $('#name-input').ntsError('set', '未入力エラー');
                    }
                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                        return;
                    }
                    b.service.saveOutputSetting(self.outputSettingDetail()).done(function () {
                        nts.uk.ui.windows.setShared('isHasUpdate', true, false);
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                ScreenModel.prototype.remove = function () {
                    var self = this;
                    var selectedCode = self.outputSettings().outputSettingSelectedCode();
                    if (selectedCode == '') {
                        nts.uk.ui.dialog.alert('未選択エラー');
                        return;
                    }
                    b.service.removeOutputSetting(selectedCode).done(function () {
                        nts.uk.ui.windows.setShared('isHasUpdate', true, false);
                        var selectedSetting = self.outputSettings().outputSettingList().filter(function (setting) { return setting.code == selectedCode; })[0];
                        self.outputSettings().outputSettingList.remove(selectedSetting);
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                    });
                };
                ScreenModel.prototype.loadOutputSettingDetail = function (selectedCode) {
                    var dfd = $.Deferred();
                    var self = this;
                    b.service.findOutputSettingDetail(selectedCode).done(function (data) {
                        self.outputSettingDetail(new OutputSettingDetail(self.aggregateItemsList, self.masterItemList, data));
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadAggregateItems = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    b.service.findAggregateItems().done(function (item) {
                        self.aggregateItemsList = item;
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadMasterItems = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    b.service.findMasterItems().done(function (item) {
                        self.masterItemList = item;
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.switchToCreateMode = function () {
                    this.outputSettingDetail(new OutputSettingDetail(this.aggregateItemsList, this.masterItemList));
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
                function OutputSettingDetail(aggregateItems, masterItem, outputSetting) {
                    this.settingCode = ko.observable(outputSetting != undefined ? outputSetting.code : '');
                    this.settingName = ko.observable(outputSetting != undefined ? outputSetting.name : '');
                    this.isPrintOnePageEachPer = ko.observable(outputSetting != undefined ? outputSetting.isOnceSheetPerPerson : false);
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
                    var categorySetting = [];
                    if (outputSetting == undefined) {
                        categorySetting = this.convertCategorySettings(aggregateItems, masterItem);
                    }
                    else {
                        categorySetting = this.convertCategorySettings(aggregateItems, masterItem, outputSetting.categorySettings);
                    }
                    this.categorySettings = ko.observableArray(categorySetting);
                    var self = this;
                    self.categorySettings().forEach(function (setting) {
                        setting.outputItems.subscribe(function (newValue) {
                            self.reloadReportItems();
                        });
                    });
                }
                OutputSettingDetail.prototype.convertCategorySettings = function (aggregateItems, masterItem, categorySettings) {
                    var settings = [];
                    settings[0] = this.createCategorySetting(Category.PAYMENT, PaymentType.SALARY, aggregateItems, masterItem, categorySettings);
                    settings[1] = this.createCategorySetting(Category.DEDUCTION, PaymentType.SALARY, aggregateItems, masterItem, categorySettings);
                    settings[2] = this.createCategorySetting(Category.ATTENDANCE, PaymentType.SALARY, aggregateItems, masterItem, categorySettings);
                    settings[3] = this.createCategorySetting(Category.PAYMENT, PaymentType.BONUS, aggregateItems, masterItem, categorySettings);
                    settings[4] = this.createCategorySetting(Category.DEDUCTION, PaymentType.BONUS, aggregateItems, masterItem, categorySettings);
                    settings[5] = this.createCategorySetting(Category.ATTENDANCE, PaymentType.BONUS, aggregateItems, masterItem, categorySettings);
                    return settings;
                };
                OutputSettingDetail.prototype.createCategorySetting = function (category, paymentType, aggregateItems, masterItem, categorySettings) {
                    var aggregateItemsInCategory = aggregateItems.filter(function (item) { return item.category == category
                        && item.paymentType == paymentType; });
                    var masterItemsInCategory = masterItem.filter(function (item) { return item.category == category
                        && item.paymentType == paymentType; });
                    var cateTempSetting = { category: category, paymentType: paymentType, outputItems: [] };
                    if (categorySettings == undefined) {
                        return new CategorySetting(aggregateItemsInCategory, masterItemsInCategory, cateTempSetting);
                    }
                    var categorySetting = categorySettings.filter(function (item) { return item.category == category
                        && item.paymentType == paymentType; })[0];
                    if (categorySetting == undefined) {
                        categorySetting = cateTempSetting;
                    }
                    return new CategorySetting(aggregateItemsInCategory, masterItemsInCategory, categorySetting);
                };
                return OutputSettingDetail;
            }());
            viewmodel.OutputSettingDetail = OutputSettingDetail;
            var CategorySetting = (function () {
                function CategorySetting(aggregateItems, masterItems, categorySetting) {
                    this.category = categorySetting.category;
                    this.paymentType = categorySetting.paymentType;
                    this.outputItems = ko.observableArray(categorySetting != undefined ? categorySetting.outputItems : []);
                    var settingItemCode = [];
                    if (categorySetting != undefined) {
                        settingItemCode = categorySetting.outputItems.map(function (item) {
                            return item.code;
                        });
                    }
                    var aggregateItemsExcluded = aggregateItems.filter(function (item) { return settingItemCode.indexOf(item.code) == -1; });
                    var masterItemsExcluded = masterItems.filter(function (item) { return settingItemCode.indexOf(item.code) == -1; });
                    this.aggregateItemsList = ko.observableArray(aggregateItemsExcluded);
                    this.masterItemList = ko.observableArray(masterItemsExcluded);
                    this.outputItemsSelected = ko.observable(null);
                    this.aggregateItemSelected = ko.observable(null);
                    this.masterItemSelected = ko.observable(null);
                    this.outputItemColumns = ko.observableArray([
                        { headerText: '集約', prop: 'isAggregateItem', width: 40,
                            formatter: function (data) {
                                if (data == 'true') {
                                    return '<div class="center"><i class="icon icon-dot"></i></div>';
                                }
                                return '';
                            }
                        },
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 100 },
                    ]);
                }
                CategorySetting.prototype.masterItemToDisplay = function () {
                    if (this.masterItemSelected() == undefined || this.masterItemSelected() == null) {
                        return;
                    }
                    var self = this;
                    var selectedItem = self.masterItemList().filter(function (item) {
                        return item.code == self.masterItemSelected();
                    })[0];
                    self.masterItemList.remove(selectedItem);
                    self.outputItems.push({
                        code: selectedItem.code,
                        name: selectedItem.name,
                        isAggregateItem: true,
                    });
                    self.masterItemSelected(null);
                };
                CategorySetting.prototype.aggregateItemToDisplay = function () {
                    if (this.aggregateItemSelected() == undefined || this.aggregateItemSelected() == null) {
                        return;
                    }
                    var self = this;
                    var selectedItem = self.aggregateItemsList().filter(function (item) {
                        return item.code == self.aggregateItemSelected();
                    })[0];
                    self.aggregateItemsList.remove(selectedItem);
                    self.outputItems.push({
                        code: selectedItem.code,
                        name: selectedItem.name,
                        isAggregateItem: false,
                    });
                    self.aggregateItemSelected(null);
                };
                return CategorySetting;
            }());
            viewmodel.CategorySetting = CategorySetting;
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
            var PaymentType = (function () {
                function PaymentType() {
                }
                PaymentType.SALARY = 'Salary';
                PaymentType.BONUS = 'Bonus';
                return PaymentType;
            }());
            viewmodel.PaymentType = PaymentType;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qet001.b || (qet001.b = {}));
})(qet001 || (qet001 = {}));
