var qet001;
(function (qet001) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var OuputSettingsService = qet001.a.service;
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.outputSettings = ko.observable(new OutputSettings());
                    this.outputSettingDetail = ko.observable(new OutputSettingDetail([], []));
                    this.reportItems = ko.observableArray([]);
                    this.isLoading = ko.observable(true);
                    this.outputItemColumns = ko.observableArray([
                        { headerText: '集約', prop: 'isAggregateItem', width: 40,
                            formatter: function (data) {
                                if (data == 'true') {
                                    return '<div class="center"><i class="icon icon-dot"></i></div>';
                                }
                                return '';
                            }
                        },
                        { headerText: 'コード', prop: 'code', width: 50 },
                        { headerText: '名称', prop: 'name', width: 100 },
                        { headerText: '削除', prop: 'code', width: 50,
                            formatter: function (data) {
                                return '<button class="delete-button icon icon-close" id="' + data + '" >'
                                    + '</button>';
                            }
                        },
                    ]);
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
                        { headerText: 'コード', prop: 'itemCode', width: 50 },
                        { headerText: '名称', prop: 'itemName', width: 100 },
                    ]);
                    this.reportItemSelected = ko.observable(null);
                    this.aggregateItemsList = [];
                    this.masterItemList = [];
                    this.hasUpdate = ko.observable(false);
                    var self = this;
                    self.codeDirtyChecker = new nts.uk.ui.DirtyChecker(self.outputSettingDetail().settingCode);
                    self.nameDirtyChecker = new nts.uk.ui.DirtyChecker(self.outputSettingDetail().settingName);
                    self.reportItemsDirtyChecker = new nts.uk.ui.DirtyChecker(self.reportItems);
                    self.outputSettings().outputSettingSelectedCode.subscribe(function (code) {
                        self.isLoading(true);
                        if (self.outputSettings().temporarySelectedCode() == code) {
                            return;
                        }
                        else if (code) {
                            if (self.isDirty()) {
                                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                                    self.outputSettings().temporarySelectedCode(code);
                                    self.loadOutputSettingDetail(code);
                                }).ifNo(function () {
                                    self.outputSettings().outputSettingSelectedCode(self.outputSettings().temporarySelectedCode());
                                });
                            }
                            else {
                                self.outputSettings().temporarySelectedCode(code);
                                self.loadOutputSettingDetail(code);
                            }
                            self.isLoading(false);
                        }
                        else {
                            if (self.isDirty()) {
                                nts.uk.ui.dialog.confirm('変更された内容が登録されていません。\r\nよろしいですか。').ifYes(function () {
                                    self.clearError();
                                    self.outputSettings().temporarySelectedCode('');
                                    self.outputSettingDetail(new OutputSettingDetail(self.aggregateItemsList, self.masterItemList));
                                    self.isLoading(false);
                                    self.resetDirty();
                                    return;
                                }).ifNo(function () {
                                    self.outputSettings().outputSettingSelectedCode(self.outputSettings().temporarySelectedCode());
                                });
                            }
                            else {
                                self.clearError();
                                self.outputSettings().temporarySelectedCode('');
                                self.outputSettingDetail(new OutputSettingDetail(self.aggregateItemsList, self.masterItemList));
                                self.isLoading(false);
                                self.resetDirty();
                                return;
                            }
                        }
                    });
                    self.outputSettingDetail.subscribe(function (data) {
                        self.reloadReportItem();
                        data.reloadReportItems = self.reloadReportItem.bind(self);
                    });
                }
                ScreenModel.prototype.resetDirty = function () {
                    var self = this;
                    self.codeDirtyChecker = new nts.uk.ui.DirtyChecker(self.outputSettingDetail().settingCode);
                    self.nameDirtyChecker = new nts.uk.ui.DirtyChecker(self.outputSettingDetail().settingName);
                    self.reportItemsDirtyChecker.reset();
                };
                ScreenModel.prototype.isDirty = function () {
                    var self = this;
                    if (self.codeDirtyChecker.isDirty()
                        || self.nameDirtyChecker.isDirty()
                        || self.reportItemsDirtyChecker.isDirty()) {
                        return true;
                    }
                    return false;
                };
                ScreenModel.prototype.clearError = function () {
                    if (nts.uk.ui._viewModel) {
                        $('#code-input').ntsError('clear');
                        $('#name-input').ntsError('clear');
                    }
                };
                ScreenModel.prototype.reloadReportItem = function () {
                    var self = this;
                    var data = self.outputSettingDetail();
                    if (!data || data.categorySettings().length == 0) {
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
                        self.outputSettingDetail(new OutputSettingDetail(self.aggregateItemsList, self.masterItemList));
                        var isHasData = outputSettings && outputSettings.length > 0;
                        if (!isHasData) {
                            self.outputSettings().outputSettingSelectedCode('');
                            dfd.resolve();
                            return;
                        }
                        self.outputSettings().outputSettingList(outputSettings);
                        self.outputSettings().outputSettingSelectedCode(selectedSettingCode);
                        self.outputSettingDetail().isCreateMode(!isHasData);
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadAllOutputSetting = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    OuputSettingsService.findOutputSettings().done(function (data) {
                        self.outputSettings().outputSettingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.close = function () {
                    var self = this;
                    if (self.isDirty()) {
                        nts.uk.ui.dialog.confirm('変更された内容が登録されていません。\r\nよろしいですか。').ifYes(function () {
                            nts.uk.ui.windows.close();
                        });
                    }
                    else {
                        nts.uk.ui.windows.close();
                    }
                };
                ScreenModel.prototype.save = function () {
                    var self = this;
                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                        return;
                    }
                    b.service.saveOutputSetting(self.outputSettingDetail()).done(function () {
                        nts.uk.ui.windows.setShared('isHasUpdate', true, false);
                        self.loadAllOutputSetting().done(function () {
                            self.resetDirty();
                            self.outputSettings().outputSettingSelectedCode(self.outputSettingDetail().settingCode());
                        });
                    }).fail(function (res) {
                        self.clearError();
                        $('#code-input').ntsError('set', res.message);
                    });
                };
                ScreenModel.prototype.remove = function () {
                    var self = this;
                    var selectedCode = self.outputSettings().temporarySelectedCode();
                    if (!selectedCode || selectedCode == '') {
                        return;
                    }
                    nts.uk.ui.dialog.confirm('出力項目設定からもデータを削除します。\r\nよろしいですか？').ifYes(function () {
                        b.service.removeOutputSetting(selectedCode).done(function () {
                            nts.uk.ui.windows.setShared('isHasUpdate', true, false);
                            var itemSelected = self.outputSettings().outputSettingList().filter(function (item) { return item.code == selectedCode; })[0];
                            var indexSelected = self.outputSettings().outputSettingList().indexOf(itemSelected);
                            self.outputSettings().outputSettingList.remove(itemSelected);
                            if (self.outputSettings().outputSettingList().length == 0) {
                                self.outputSettings().outputSettingSelectedCode(null);
                                return;
                            }
                            if (self.outputSettings().outputSettingList()[indexSelected]) {
                                self.outputSettings().outputSettingSelectedCode(self.outputSettings().outputSettingList()[indexSelected].code);
                                return;
                            }
                            self.outputSettings().outputSettingSelectedCode(self.outputSettings().outputSettingList()[indexSelected - 1].code);
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                        });
                    });
                };
                ScreenModel.prototype.loadOutputSettingDetail = function (selectedCode) {
                    var dfd = $.Deferred();
                    var self = this;
                    b.service.findOutputSettingDetail(selectedCode).done(function (data) {
                        self.clearError();
                        self.outputSettingDetail(new OutputSettingDetail(self.aggregateItemsList, self.masterItemList, data));
                        self.resetDirty();
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
                    var self = this;
                    self.outputSettings().outputSettingSelectedCode('');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var OutputSettings = (function () {
                function OutputSettings() {
                    this.searchText = ko.observable(null);
                    this.outputSettingList = ko.observableArray([]);
                    this.temporarySelectedCode = ko.observable(null);
                    this.outputSettingSelectedCode = ko.observable(null);
                    this.outputSettingColumns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 50 },
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
                        { id: 'tab-salary-payment', title: '給与支給', content: '#salary-payment',
                            enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-salary-deduction', title: '給与控除', content: '#salary-deduction',
                            enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-salary-attendance', title: '給与勤怠', content: '#salary-attendance',
                            enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-payment', title: '賞与支給', content: '#bonus-payment',
                            enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-deduction', title: '賞与控除', content: '#bonus-deduction',
                            enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-bonus-attendance', title: '賞与勤怠', content: '#bonus-attendance',
                            enable: ko.observable(true), visible: ko.observable(true) },
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
                    var aggregateItemsInCategory = aggregateItems.filter(function (item) { return item.category == category && item.paymentType == paymentType; });
                    var masterItemsInCategory = masterItem.filter(function (item) { return item.category == category; });
                    var cateTempSetting = { category: category, paymentType: paymentType, outputItems: [] };
                    if (!categorySettings) {
                        return new CategorySetting(aggregateItemsInCategory, masterItemsInCategory, cateTempSetting);
                    }
                    var categorySetting = categorySettings.filter(function (item) { return item.category == category
                        && item.paymentType == paymentType; })[0];
                    if (!categorySetting) {
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
                    this.fullCategoryName = this.getFullCategoryName(this.category, this.paymentType);
                    var masterSettingItemCode = [];
                    var aggregateSettingItemCode = [];
                    if (categorySetting != undefined) {
                        masterSettingItemCode = categorySetting.outputItems
                            .filter(function (item) { return !item.isAggregateItem; })
                            .map(function (item) {
                            return item.code;
                        });
                        aggregateSettingItemCode = categorySetting.outputItems
                            .filter(function (item) { return item.isAggregateItem; })
                            .map(function (item) {
                            return item.code;
                        });
                    }
                    this.outputItems = ko.observableArray(categorySetting != undefined ? categorySetting.outputItems : []);
                    var aggregateItemsExcluded = aggregateItems.filter(function (item) { return aggregateSettingItemCode.indexOf(item.code) == -1; });
                    var masterItemsExcluded = masterItems.filter(function (item) { return masterSettingItemCode.indexOf(item.code) == -1; });
                    this.aggregateItemsList = ko.observableArray(aggregateItemsExcluded);
                    this.masterItemList = ko.observableArray(masterItemsExcluded);
                    this.outputItemsSelected = ko.observable(null);
                    this.aggregateItemSelected = ko.observable(null);
                    this.masterItemSelected = ko.observable(null);
                    var self = this;
                    self.outputItemCache = categorySetting != undefined ? categorySetting.outputItems : [];
                    self.outputItems.subscribe(function (items) {
                        self.outputItemCache = items;
                    });
                    ko.bindingHandlers.rended = {
                        update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
                            var code = valueAccessor();
                            viewModel.outputItems().forEach(function (item) {
                                $('#' + item.code).on('click', function () {
                                    code(item.code);
                                    viewModel.remove();
                                    code(null);
                                });
                            });
                        }
                    };
                }
                CategorySetting.prototype.remove = function () {
                    var self = this;
                    var selectedItem = self.outputItems().filter(function (item) {
                        return item.code == self.outputItemsSelected();
                    })[0];
                    self.outputItems.remove(selectedItem);
                    if (selectedItem.isAggregateItem) {
                        self.aggregateItemsList.push({
                            code: selectedItem.code,
                            name: selectedItem.name,
                            paymentType: self.paymentType,
                            category: self.category,
                        });
                        return;
                    }
                    self.masterItemList.push({
                        code: selectedItem.code,
                        name: selectedItem.name,
                        paymentType: self.paymentType,
                        category: self.category,
                    });
                };
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
                        isAggregateItem: false,
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
                        isAggregateItem: true
                    });
                    self.aggregateItemSelected(null);
                };
                CategorySetting.prototype.getFullCategoryName = function (category, paymentType) {
                    var categoryName = '';
                    switch (category) {
                        case Category.PAYMENT:
                            categoryName = '支給';
                            break;
                        case Category.DEDUCTION:
                            categoryName = '控除';
                            break;
                        case Category.ATTENDANCE:
                            categoryName = '勤怠';
                            break;
                        default:
                            categoryName = '';
                    }
                    var paymentTypeName = paymentType == PaymentType.SALARY ? '給与' : '賞与';
                    return paymentTypeName + categoryName;
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
//# sourceMappingURL=qet001.b.vm.js.map