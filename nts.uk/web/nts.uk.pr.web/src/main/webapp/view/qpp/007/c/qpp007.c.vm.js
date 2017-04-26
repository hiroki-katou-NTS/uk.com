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
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            class ScreenModel {
                                constructor() {
                                    var self = this;
                                    self.isLoading = ko.observable(true);
                                    self.isNewMode = ko.observable(true);
                                    self.isSomethingChanged = ko.observable(false);
                                    self.outputSettings = ko.observableArray([]);
                                    self.outputSettingSelectedCode = ko.observable('');
                                    self.temporarySelectedCode = ko.observable('');
                                    self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel(ko.observableArray([]), ko.observableArray([])));
                                    self.aggregateOutputItems = ko.observableArray([]);
                                    self.aggregateOutputItemSelected = ko.observable('');
                                    self.allAggregateItems = ko.observableArray([]);
                                    self.allMasterItems = ko.observableArray([]);
                                    self.dirtyChecker = new nts.uk.ui.DirtyChecker(self.outputSettingDetailModel);
                                    // Define aggregateOutputItems column.
                                    self.aggregateOutputItemColumns = ko.observableArray([
                                        { headerText: '区分', prop: 'categoryNameJPN', width: 50 },
                                        {
                                            headerText: '集約', prop: 'isAggregate', width: 40,
                                            formatter: function (isAggregate) {
                                                if (isAggregate == 'true') {
                                                    return '<div class="halign-center"><i class="icon icon-dot"></i></div>';
                                                }
                                                return '';
                                            }
                                        },
                                        { headerText: 'コード', prop: 'code', width: 50 },
                                        { headerText: '名称', prop: 'name', width: 100 },
                                    ]);
                                    self.outputSettingDetailModel.subscribe((data) => {
                                        self.reloadAggregateOutputItems();
                                        data.reloadAggregateOutputItems = self.reloadAggregateOutputItems.bind(self);
                                    });
                                    self.temporarySelectedCode.subscribe(code => {
                                        // Do nothing if code == null or undefined.
                                        if (!code) {
                                            return;
                                        }
                                        // Function to executed on confirmDirty dialog.
                                        var executeIfConfirmed = () => {
                                            self.outputSettingSelectedCode(self.temporarySelectedCode());
                                            self.onSelectOutputSetting(code);
                                        };
                                        var executeIfCanceled = () => {
                                            self.temporarySelectedCode(self.outputSettingSelectedCode());
                                        };
                                        // Do nothing if selected same code.
                                        if (self.outputSettingSelectedCode() == code) {
                                            return;
                                        }
                                        else {
                                            self.confirmDirtyAndExecute(executeIfConfirmed, executeIfCanceled);
                                        }
                                    });
                                }
                                /**
                                 * Start page.
                                 */
                                startPage() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var outputSettings = nts.uk.ui.windows.getShared('outputSettings');
                                    var selectedCode = nts.uk.ui.windows.getShared('selectedCode');
                                    $.when(self.loadMasterItems(), self.loadAggregateItems()).done(() => {
                                        self.isLoading(false);
                                        // New mode if there is 0 outputSettings. 
                                        if (!outputSettings || outputSettings.length == 0) {
                                            self.enableNewMode();
                                        }
                                        else {
                                            self.outputSettings(outputSettings);
                                            self.temporarySelectedCode(selectedCode);
                                        }
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                }
                                /**
                                * Open common setting dialog.
                                */
                                onCommonSettingBtnClicked() {
                                    var self = this;
                                    nts.uk.ui.windows.sub.modal('/view/qpp/007/j/index.xhtml', { title: '集計項目の設定', dialogClass: 'no-close' })
                                        .onClosed(function () {
                                        self.loadAggregateItems().done(() => {
                                            self.loadOutputSettingDetail(self.outputSettingDetailModel().settingCode());
                                        });
                                    });
                                }
                                /**
                                 * Clear errors and enable new mode.
                                 */
                                onNewModeBtnClicked() {
                                    var self = this;
                                    self.confirmDirtyAndExecute(function () {
                                        self.clearError();
                                        self.enableNewMode();
                                    });
                                }
                                /**
                                 * Save outputSetting.
                                 */
                                onSaveBtnClicked() {
                                    var self = this;
                                    // Clear errors.
                                    self.clearError();
                                    // Validate.
                                    self.validate();
                                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                                        return;
                                    }
                                    var data = self.collectData();
                                    // Set isCreateMode.
                                    if (self.isNewMode()) {
                                        data.createMode = true;
                                    }
                                    else {
                                        data.createMode = false;
                                    }
                                    // Save.
                                    c.service.save(data).done(() => {
                                        self.isNewMode(false);
                                        self.isSomethingChanged(true);
                                        self.dirtyChecker.reset();
                                        self.loadAllOutputSetting();
                                    }).fail(res => {
                                        if (res.messageId == 'ER005') {
                                            $('#inpCode').ntsError('set', '入力した＊は既に存在しています。\r\n ＊を確認してください。');
                                        }
                                    });
                                }
                                /**
                                 * Delete selected outputSetting.
                                 */
                                onRemoveBtnClicked() {
                                    var self = this;
                                    var selectedCode = self.outputSettingSelectedCode();
                                    if (selectedCode) {
                                        nts.uk.ui.dialog.confirm("データを削除します。\r\n よろしいですか？").ifYes(function () {
                                            c.service.remove(selectedCode).done(() => {
                                                self.isSomethingChanged(true);
                                                // Find selected outputSetting.
                                                var selectedOutputSetting = self.outputSettings().filter(item => item.code == selectedCode)[0];
                                                var selectedIndex = self.outputSettings().indexOf(selectedOutputSetting);
                                                // Remove selected setting from list.
                                                self.outputSettings.remove(selectedOutputSetting);
                                                if (self.outputSettings() && self.outputSettings().length > 0) {
                                                    var currentSetting = self.outputSettings()[selectedIndex];
                                                    // Select setting with the same index.
                                                    if (currentSetting) {
                                                        self.temporarySelectedCode(currentSetting.code);
                                                    }
                                                    else {
                                                        self.temporarySelectedCode(self.outputSettings()[selectedIndex - 1].code);
                                                    }
                                                }
                                                else {
                                                    self.clearError();
                                                    self.enableNewMode();
                                                }
                                            });
                                        });
                                    }
                                }
                                /**
                                 * Close dialog.
                                 */
                                onCloseBtnClicked() {
                                    var self = this;
                                    self.confirmDirtyAndExecute(function () {
                                        nts.uk.ui.windows.setShared('isSomethingChanged', self.isSomethingChanged());
                                        nts.uk.ui.windows.close();
                                    });
                                }
                                /**
                                  * On select outputSetting
                                  */
                                onSelectOutputSetting(code) {
                                    var self = this;
                                    self.loadOutputSettingDetail(code).done(() => {
                                        self.isNewMode(false);
                                        self.isLoading(false);
                                        self.clearError();
                                    });
                                }
                                /**
                                * Collect Data
                                */
                                collectData() {
                                    var self = this;
                                    var model = self.outputSettingDetailModel();
                                    // Convert model to dto.
                                    var dto = new OutputSettingDto();
                                    dto.code = model.settingCode();
                                    dto.name = model.settingName();
                                    var categorySettingDto = new Array();
                                    model.categorySettings().forEach(setting => {
                                        // Set order number.
                                        for (var i = 0; i < setting.outputItems().length; i++) {
                                            setting.outputItems()[i].orderNumber = i;
                                        }
                                        categorySettingDto.push(new CategorySettingDto(setting.categoryName, setting.outputItems().map(item => {
                                            var mappedItem = item;
                                            // map Attendance && ArticleOthers item.
                                            if (!item.isAggregateItem) {
                                                mappedItem = new OutputItem();
                                                mappedItem.code = item.code;
                                                mappedItem.name = item.name;
                                                mappedItem.orderNumber = item.orderNumber;
                                                mappedItem.isAggregateItem = false;
                                            }
                                            return mappedItem;
                                        })));
                                    });
                                    // Set categorySettings.
                                    dto.categorySettings = categorySettingDto;
                                    // return dto.
                                    return dto;
                                }
                                /**
                                * Clear all input errors.
                                */
                                clearError() {
                                    if (nts.uk.ui._viewModel) {
                                        $('#inpCode').ntsError('clear');
                                        $('#inpName').ntsError('clear');
                                    }
                                }
                                /**
                                * Validate all inputs.
                                */
                                validate() {
                                    $('#inpCode').ntsEditor('validate');
                                    $('#inpName').ntsEditor('validate');
                                }
                                /**
                                 * Confirm dirty state and execute function.
                                 */
                                confirmDirtyAndExecute(functionToExecute, functionToExecuteIfNo) {
                                    var self = this;
                                    if (self.dirtyChecker.isDirty()) {
                                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                                            functionToExecute();
                                        }).ifNo(function () {
                                            if (functionToExecuteIfNo) {
                                                functionToExecuteIfNo();
                                            }
                                        });
                                    }
                                    else {
                                        functionToExecute();
                                    }
                                }
                                /**
                                * Reset selected code & all inputs.
                                */
                                enableNewMode() {
                                    var self = this;
                                    self.outputSettingDetailModel(new OutputSettingDetailModel(self.allMasterItems, self.allAggregateItems));
                                    self.outputSettingSelectedCode(null);
                                    self.dirtyChecker.reset();
                                    self.isNewMode(true);
                                }
                                /**
                                * Reload aggregateOutput items.
                                */
                                reloadAggregateOutputItems() {
                                    var self = this;
                                    var data = self.outputSettingDetailModel();
                                    if (!data || data.categorySettings().length == 0) {
                                        self.aggregateOutputItems([]);
                                        return;
                                    }
                                    // Set data to report item list.
                                    var reportItemList = [];
                                    data.categorySettings().forEach((setting) => {
                                        var categoryName = setting.categoryName;
                                        setting.outputItems().forEach((item) => {
                                            reportItemList.push(new AggregateOutputItem(item.code, item.name, categoryName, item.isAggregateItem));
                                        });
                                    });
                                    self.aggregateOutputItems(reportItemList);
                                }
                                /**
                                 * Load all output setting.
                                 */
                                loadAllOutputSetting() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    c.service.findAllOutputSettings().done(data => {
                                        self.outputSettings(data);
                                        dfd.resolve();
                                    }).fail(function (res) {
                                        dfd.reject();
                                    });
                                    return dfd.promise();
                                }
                                /**
                                 * Load detail output setting.
                                 */
                                loadOutputSettingDetail(code) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    if (code) {
                                        c.service.findOutputSettingDetail(code).done(function (data) {
                                            self.outputSettingDetailModel(new OutputSettingDetailModel(self.allMasterItems, self.allAggregateItems, data));
                                            self.dirtyChecker.reset();
                                            dfd.resolve();
                                        }).fail(function (res) {
                                            dfd.reject();
                                        });
                                    }
                                    else {
                                        self.outputSettingDetailModel(new OutputSettingDetailModel(self.allMasterItems, self.allAggregateItems));
                                    }
                                    return dfd.promise();
                                }
                                /**
                                * Load aggregate items
                                */
                                loadAggregateItems() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    c.service.findAllAggregateItems().done(res => {
                                        self.allAggregateItems.removeAll();
                                        res.forEach(function (item) {
                                            self.allAggregateItems.push({
                                                code: item.salaryAggregateItemCode,
                                                name: item.salaryAggregateItemName,
                                                taxDivision: item.taxDivision,
                                            });
                                        });
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                }
                                /**
                                * Load master items
                                */
                                loadMasterItems() {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    c.service.findAllMasterItems().done(res => {
                                        self.allMasterItems(res);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                            /**
                             * OutputSettingHeader model.
                             */
                            class OutputSettingHeader {
                                constructor(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                            }
                            viewmodel.OutputSettingHeader = OutputSettingHeader;
                            /**
                             * OutputSettingDto model.
                             */
                            class OutputSettingDto {
                            }
                            viewmodel.OutputSettingDto = OutputSettingDto;
                            /**
                             * OutputSettingDetailModel model.
                             */
                            class OutputSettingDetailModel {
                                constructor(masterItems, aggregateItems, outputSetting) {
                                    this.settingCode = ko.observable(outputSetting != undefined ? outputSetting.code : '');
                                    this.settingName = ko.observable(outputSetting != undefined ? outputSetting.name : '');
                                    this.aggregateItems = aggregateItems;
                                    this.masterItems = masterItems;
                                    // construct categorySettings.
                                    var settings = [];
                                    if (outputSetting == undefined) {
                                        settings = this.toModel();
                                    }
                                    else {
                                        settings = this.toModel(outputSetting.categorySettings);
                                    }
                                    this.categorySettings = ko.observableArray(settings);
                                    this.categorySettingTabs = ko.observableArray([
                                        { id: SalaryCategory.PAYMENT, title: '支給', content: '#payment', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: SalaryCategory.DEDUCTION, title: '控除', content: '#deduction', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: SalaryCategory.ATTENDANCE, title: '勤怠', content: '#attendance', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: SalaryCategory.ARTICLE_OTHERS, title: '記事・その他', content: '#article-others', enable: ko.observable(true), visible: ko.observable(true) }
                                    ]);
                                    this.selectedCategory = ko.observable(SalaryCategory.PAYMENT);
                                    var self = this;
                                    self.categorySettings().forEach((setting) => {
                                        setting.outputItems.subscribe((newValue) => {
                                            self.reloadAggregateOutputItems();
                                        });
                                    });
                                }
                                /**
                                * Convert category setting data from dto to screen model.
                                */
                                toModel(categorySettings) {
                                    var settings = [];
                                    var categorySettingDtos;
                                    if (categorySettings && categorySettings.length > 0) {
                                        categorySettingDtos = categorySettings;
                                    }
                                    settings[0] = this.filterSettingByCategory(SalaryCategory.PAYMENT, categorySettingDtos);
                                    settings[1] = this.filterSettingByCategory(SalaryCategory.DEDUCTION, categorySettingDtos);
                                    settings[2] = this.filterSettingByCategory(SalaryCategory.ATTENDANCE, categorySettingDtos);
                                    settings[3] = this.filterSettingByCategory(SalaryCategory.ARTICLE_OTHERS, categorySettingDtos);
                                    return settings;
                                }
                                filterSettingByCategory(category, categorySettings) {
                                    var cateTempSetting = { category: category, outputItems: [] };
                                    if (categorySettings == undefined) {
                                        return new CategorySettingModel(category, this.masterItems, this.aggregateItems, cateTempSetting);
                                    }
                                    var categorySetting = categorySettings.filter(item => item.category == category)[0];
                                    if (categorySetting == undefined) {
                                        categorySetting = cateTempSetting;
                                    }
                                    return new CategorySettingModel(category, this.masterItems, this.aggregateItems, categorySetting);
                                }
                            }
                            viewmodel.OutputSettingDetailModel = OutputSettingDetailModel;
                            /**
                             * CategorySettingDto model.
                             */
                            class CategorySettingDto {
                                constructor(category, outputItems) {
                                    this.category = category;
                                    this.outputItems = outputItems;
                                }
                            }
                            viewmodel.CategorySettingDto = CategorySettingDto;
                            /**
                             * ReportItem model.
                             */
                            class AggregateOutputItem {
                                constructor(code, name, categoryName, isAggregate) {
                                    this.code = code;
                                    this.name = name;
                                    this.isAggregate = isAggregate;
                                    var self = this;
                                    switch (categoryName) {
                                        case SalaryCategory.PAYMENT:
                                            self.categoryNameJPN = '支給';
                                            break;
                                        case SalaryCategory.DEDUCTION:
                                            self.categoryNameJPN = '控除';
                                            break;
                                        case SalaryCategory.ATTENDANCE:
                                            self.categoryNameJPN = '勤怠';
                                            break;
                                        case SalaryCategory.ARTICLE_OTHERS:
                                            self.categoryNameJPN = '記事・その他';
                                            break;
                                        default:
                                            self.categoryNameJPN = '';
                                    }
                                }
                            }
                            viewmodel.AggregateOutputItem = AggregateOutputItem;
                            /**
                             * CategorySettingModel model.
                             */
                            class CategorySettingModel {
                                constructor(categoryName, masterItems, aggregateItems, categorySetting) {
                                    var self = this;
                                    self.categoryName = categoryName;
                                    self.aggregateItems = ko.observableArray([]);
                                    self.aggregateItemsSelected = ko.observableArray([]);
                                    self.masterItems = ko.observableArray([]);
                                    self.masterItemsSelected = ko.observableArray([]);
                                    self.outputItems = ko.observableArray(categorySetting != undefined ? categorySetting.outputItems : []);
                                    self.outputItemSelected = ko.observable(null);
                                    self.outputItemsSelected = ko.observableArray([]);
                                    switch (categoryName) {
                                        case SalaryCategory.PAYMENT:
                                            aggregateItems().forEach(item => {
                                                if (item.taxDivision == TaxDivision.PAYMENT) {
                                                    self.aggregateItems.push(item);
                                                }
                                            });
                                            masterItems().forEach(item => {
                                                if (item.category == SalaryCategory.PAYMENT) {
                                                    self.masterItems.push(item);
                                                }
                                            });
                                            break;
                                        case SalaryCategory.DEDUCTION:
                                            aggregateItems().forEach(item => {
                                                if (item.taxDivision == TaxDivision.DEDUCTION) {
                                                    self.aggregateItems.push(item);
                                                }
                                            });
                                            masterItems().forEach(item => {
                                                if (item.category == SalaryCategory.DEDUCTION) {
                                                    self.masterItems.push(item);
                                                }
                                            });
                                            break;
                                        case SalaryCategory.ATTENDANCE:
                                            masterItems().forEach(item => {
                                                if (item.category == SalaryCategory.ATTENDANCE) {
                                                    self.masterItems.push(item);
                                                }
                                            });
                                            break;
                                        case SalaryCategory.ARTICLE_OTHERS:
                                            masterItems().forEach(item => {
                                                if (item.category == SalaryCategory.ARTICLE_OTHERS) {
                                                    self.masterItems.push(item);
                                                }
                                            });
                                            break;
                                        default: // Do nothing.
                                    }
                                    // Define outputItemColumns.
                                    this.outputItemColumns = ko.observableArray([
                                        {
                                            headerText: '集約', prop: 'isAggregateItem', width: 40,
                                            formatter: function (data) {
                                                if (data == 'true') {
                                                    return '<div class="halign-center"><i class="icon icon-dot"></i></div>';
                                                }
                                                return '';
                                            }
                                        },
                                        { headerText: 'コード', prop: 'code', width: 50 },
                                        { headerText: '名称', prop: 'name', width: 120 },
                                        {
                                            headerText: '削除', prop: 'code', width: 60,
                                            formatter: function (code) {
                                                return '<div class="halign-center"><button class="icon icon-close" id="' + code + '" >'
                                                    + '</button></div>';
                                            }
                                        }
                                    ]);
                                    // Customs handle.
                                    ko.bindingHandlers.rended = {
                                        init: function (element, valueAccessor, allBindings, viewModel, bindingContext) { },
                                        update: function (element, valueAccessor, allBindings, viewModel, bindingContext) {
                                            var code = valueAccessor();
                                            viewModel.outputItems().forEach(item => {
                                                $('#' + item.code).on('click', function () {
                                                    code(item.code);
                                                    viewModel.remove();
                                                    code(null);
                                                });
                                            });
                                        }
                                    };
                                }
                                /**
                                * Move master item to outputItems.
                                */
                                moveMasterItem() {
                                    // Check if has selected
                                    if (this.masterItemsSelected()[0]) {
                                        var self = this;
                                        // Get selected items from selected code list.
                                        var selectedItems = [];
                                        self.masterItemsSelected().forEach(selectedCode => {
                                            selectedItems.push(self.masterItems().filter((item) => {
                                                return selectedCode == item.code;
                                            })[0]);
                                        });
                                        // Remove from master list and add to output list
                                        selectedItems.forEach(item => {
                                            self.masterItems.remove(item);
                                            self.outputItems.push({
                                                code: item.code,
                                                name: item.name,
                                                isAggregateItem: false,
                                                orderNumber: 1
                                            });
                                        });
                                        self.masterItemsSelected([]);
                                    }
                                }
                                /**
                                * Move aggregate item to outputItems.
                                */
                                moveAggregateItem() {
                                    // Check if has selected
                                    if (this.aggregateItemsSelected()[0]) {
                                        var self = this;
                                        // Get selected items from selected code list.
                                        var selectedItems = [];
                                        self.aggregateItemsSelected().forEach(selectedCode => {
                                            selectedItems.push(self.aggregateItems().filter((item) => {
                                                return selectedCode == item.code;
                                            })[0]);
                                        });
                                        // Remove from aggregate list and add to output list
                                        selectedItems.forEach(item => {
                                            self.aggregateItems.remove(item);
                                            self.outputItems.push({
                                                code: item.code,
                                                name: item.name,
                                                isAggregateItem: true,
                                                orderNumber: 1
                                            });
                                        });
                                        self.aggregateItemsSelected([]);
                                    }
                                }
                                /**
                                * Remove item from outputItems.
                                */
                                remove() {
                                    var self = this;
                                    var selectedItem = self.outputItems().filter((item) => {
                                        return item.code == self.outputItemSelected();
                                    })[0];
                                    self.outputItems.remove(selectedItem);
                                    // Return item.
                                    if (selectedItem.isAggregateItem) {
                                        // Return to Aggregate items table.
                                        self.aggregateItems.push({
                                            code: selectedItem.code,
                                            name: selectedItem.name,
                                            taxDivision: TaxDivision.PAYMENT
                                        });
                                        return;
                                    }
                                    // Return to master items table.
                                    self.masterItems.push({
                                        code: selectedItem.code,
                                        name: selectedItem.name,
                                        category: SalaryCategory.PAYMENT
                                    });
                                }
                            }
                            viewmodel.CategorySettingModel = CategorySettingModel;
                            class AggregateItem {
                            }
                            viewmodel.AggregateItem = AggregateItem;
                            class MasterItem {
                            }
                            viewmodel.MasterItem = MasterItem;
                            class OutputItem {
                            }
                            viewmodel.OutputItem = OutputItem;
                            class SalaryCategory {
                            }
                            SalaryCategory.PAYMENT = 'Payment';
                            SalaryCategory.DEDUCTION = 'Deduction';
                            SalaryCategory.ATTENDANCE = 'Attendance';
                            SalaryCategory.ARTICLE_OTHERS = 'ArticleOthers';
                            viewmodel.SalaryCategory = SalaryCategory;
                            class TaxDivision {
                            }
                            TaxDivision.PAYMENT = 'Payment';
                            TaxDivision.DEDUCTION = 'Deduction';
                            viewmodel.TaxDivision = TaxDivision;
                            class SalaryOutputDistinction {
                            }
                            SalaryOutputDistinction.HOURLY = 'Hourly';
                            SalaryOutputDistinction.MINUTLY = 'Minutely';
                            viewmodel.SalaryOutputDistinction = SalaryOutputDistinction;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
