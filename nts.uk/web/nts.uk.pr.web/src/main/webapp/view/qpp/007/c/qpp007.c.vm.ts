module nts.uk.pr.view.qpp007.c {
    export module viewmodel {
        export class ScreenModel {
            outputSettings: KnockoutObservableArray<OutputSettingHeader>;
            outputSettingSelectedCode: KnockoutObservable<string>;
            temporarySelectedCode: KnockoutObservable<string>;
            outputSettingColumns: KnockoutObservableArray<any>;
            outputSettingDetailModel: KnockoutObservable<OutputSettingDetailModel>;
            reportItems: KnockoutObservableArray<ReportItem>;
            reportItemSelected: KnockoutObservable<string>;
            reportItemColumns: KnockoutObservableArray<any>;
            allAggregateItems: KnockoutObservableArray<AggregateItem>;
            isLoading: KnockoutObservable<boolean>;
            isNewMode: KnockoutObservable<boolean>;
            dirtyChecker: nts.uk.ui.DirtyChecker;

            constructor() {
                var self = this;
                self.isLoading = ko.observable(true);
                self.isNewMode = ko.observable(true);
                self.outputSettings = ko.observableArray<OutputSettingHeader>([]);
                self.outputSettingSelectedCode = ko.observable('');
                self.temporarySelectedCode = ko.observable('');
                self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel(ko.observableArray<AggregateItem>([])));
                self.reportItems = ko.observableArray<ReportItem>([]);
                self.reportItemSelected = ko.observable('');
                self.allAggregateItems = ko.observableArray<AggregateItem>([]);
                self.dirtyChecker = new nts.uk.ui.DirtyChecker(self.outputSettingDetailModel);

                for (let i = 1; i < 30; i++) {
                    this.outputSettings.push(new OutputSettingHeader('00' + i, '基本給' + i));
                }

                this.reportItemColumns = ko.observableArray<any>([
                    { headerText: '区分', prop: 'categoryNameJPN', width: 50 },
                    {
                        headerText: '集約', prop: 'isAggregate', width: 40,
                        formatter: function(isAggregate: string) {
                            if (isAggregate == 'true') {
                                return '<div class="halign-center"><i class="icon icon-dot"></i></div>';
                            }
                            return '';
                        }
                    },
                    { headerText: 'コード', prop: 'code', width: 100 },
                    { headerText: '名称', prop: 'name', width: 100 },
                ]);

                self.outputSettingDetailModel.subscribe((data: OutputSettingDetailModel) => {
                    self.reloadReportItems();
                    data.reloadReportItems = self.reloadReportItems.bind(self);
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
                    // Check dirty then execute appropriate function.
                    else {
                        self.confirmDirtyAndExecute(executeIfConfirmed, executeIfCanceled);
                    }
                })
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                $.when(self.loadAllOutputSetting(), self.loadMasterItems(), self.loadAggregateItems()).done(() => {
                    self.isLoading(false);
                    // New mode if there is 0 outputSettings. 
                    if (!self.outputSettings || self.outputSettings().length == 0) {
                        self.enableNewMode();
                    }
                    // else select first outputSetting.
                    else {
                        self.temporarySelectedCode(self.outputSettings()[0].code);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
            * Open common setting dialog.
            */
            public onCommonSettingBtnClicked(): void {
                var self = this;
                nts.uk.ui.windows.sub.modal('/view/qpp/007/j/index.xhtml', { title: '集計項目の設定', dialogClass: 'no-close' })
                    .onClosed(function() {
                        self.loadAggregateItems().done(() => {
                            self.loadOutputSettingDetail(self.outputSettingDetailModel().settingCode());
                        });
                    });
            }

            /**
             * Clear errors and enable new mode.
             */
            public onNewModeBtnClicked(): void {
                var self = this;
                self.confirmDirtyAndExecute(function() {
                    self.clearError();
                    self.enableNewMode();
                });
            }

            /**
             * Save outputSetting.
             */
            public onSaveBtnClicked(): void {
                var self = this;
                // Validate.
                self.validate();
                if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                    return;
                }
                var data = self.collectData();
                // Set isCreateMode.
                if (self.isNewMode()) {
                    data.createMode = true;
                } else {
                    data.createMode = false;
                }
                // Save.
                service.save(data).done(() => {
                    self.isNewMode(false);
                    self.dirtyChecker.reset();
                    self.loadAllOutputSetting();
                }).fail(res => {
                    // TODO: ...
                    console.log(res);
                });
            }

            /**
             * Delete selected outputSetting.
             */
            public onRemoveBtnClicked(): void {
                var self = this;
                if (self.outputSettingSelectedCode) {
                    nts.uk.ui.dialog.confirm("データを削除します。\r\n よろしいですか？").ifYes(function() {
                        service.remove(self.outputSettingSelectedCode()).done(() => {
                            self.loadAllOutputSetting();
                            // Set new mode if outputSettings has 0 element.
                            if (!self.outputSettings || self.outputSettings.length == 0) {
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
            public onCloseBtnClicked(): void {
                var self = this;
                self.confirmDirtyAndExecute(function() {
                    nts.uk.ui.windows.close();
                });
            }

            /**
              * On select outputSetting
              */
            private onSelectOutputSetting(code: string): void {
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
            private collectData(): OutputSettingDto {
                var self = this;
                var model = self.outputSettingDetailModel();
                var data = new OutputSettingDto();
                data.code = model.settingCode();
                data.name = model.settingName();
                var settings = new Array<CategorySettingDto>();
                model.categorySettings().forEach(setting => {
                    settings.push(new CategorySettingDto(setting.categoryName, setting.outputItems().map(item => {
                        var mappedItem = item;
                        if (!item.isAggregateItem) {
                            mappedItem = new OutputItem();
                            mappedItem.code = item.code;
                            mappedItem.name = item.name;
                            mappedItem.isAggregateItem = false;
                        }
                        return mappedItem;
                    })));
                });
                data.categorySettings = settings;
                return data;
            }

            /**
            * Clear all input errors.
            */
            private clearError(): void {
                if (nts.uk.ui._viewModel) {
                    $('#inpCode').ntsError('clear');
                    $('#inpName').ntsError('clear');
                }
            }

            /**
            * Validate all inputs.
            */
            private validate(): void {
                $('#inpCode').ntsEditor('validate');
                $('#inpName').ntsEditor('validate');
            }

            /**
             * Confirm dirty state and execute function.
             */
            private confirmDirtyAndExecute(functionToExecute: () => void, functionToExecuteIfNo?: () => void) {
                var self = this;
                if (self.dirtyChecker.isDirty()) {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function() {
                        functionToExecute();
                    }).ifNo(function() {
                        if (functionToExecuteIfNo) {
                            functionToExecuteIfNo();
                        }
                    });
                } else {
                    functionToExecute();
                }
            }

            /**
            * Reset selected code & all inputs.
            */
            private enableNewMode(): void {
                var self = this;
                self.outputSettingDetailModel(new OutputSettingDetailModel(ko.observableArray<AggregateItem>([])));
                self.outputSettingSelectedCode(null);
                self.dirtyChecker.reset();
                self.isNewMode(true);
            }

            /**
            * Reload report items.
            */
            private reloadReportItems(): void {
                var self = this;
                var data = self.outputSettingDetailModel();
                if (!data || data.categorySettings().length == 0) {
                    self.reportItems([]);
                    return;
                }
                // Set data to report item list.
                var reportItemList: ReportItem[] = [];
                data.categorySettings().forEach((setting) => {
                    var categoryName: SalaryCategory = setting.categoryName;
                    setting.outputItems().forEach((item) => {
                        reportItemList.push(new ReportItem(item.code, item.name, categoryName, item.isAggregateItem));
                    });
                });
                self.reportItems(reportItemList);
            }

            /**
             * Load all output setting.
             */
            private loadAllOutputSetting(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.findAllOutputSettings().done(data => {
                    self.outputSettings(data);
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res);
                    dfd.reject();
                })
                return dfd.promise();
            }

            /**
             * Load detail output setting.
             */
            private loadOutputSettingDetail(code: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.findOutputSettingDetail(code).done(function(data: OutputSettingDto) {
                    self.outputSettingDetailModel(new OutputSettingDetailModel(self.allAggregateItems, data));
                    self.dirtyChecker.reset();
                    dfd.resolve();
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res);
                    dfd.reject();
                })
                return dfd.promise();
            }

            /**
            * Load aggregate items
            */
            private loadAggregateItems(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.findAllAggregateItems().done(res => {
                    self.allAggregateItems.removeAll();
                    res.forEach(function(item: any) {
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
            private loadMasterItems(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }

        }

        /**
         * OutputSettingHeader model.
         */
        export class OutputSettingHeader {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        /**
         * OutputSettingDto model.
         */
        export class OutputSettingDto {
            code: string;
            name: string;
            categorySettings: CategorySettingDto[];
            createMode: boolean;
        }

        /**
         * OutputSettingDetailModel model.
         */
        export class OutputSettingDetailModel {
            settingCode: KnockoutObservable<string>;
            settingName: KnockoutObservable<string>;
            categorySettingTabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedCategory: KnockoutObservable<string>;
            categorySettings: KnockoutObservableArray<CategorySettingModel>;
            aggregateItems: KnockoutObservableArray<AggregateItem>;
            reloadReportItems: () => void;
            constructor(aggregateItems: KnockoutObservableArray<AggregateItem>, outputSetting?: OutputSettingDto) {
                this.settingCode = ko.observable(outputSetting != undefined ? outputSetting.code : '');
                this.settingName = ko.observable(outputSetting != undefined ? outputSetting.name : '');
                this.aggregateItems = aggregateItems;
                var settings: CategorySettingModel[] = [];
                if (outputSetting == undefined) {
                    settings = this.toModel();
                } else {
                    settings = this.toModel(outputSetting.categorySettings);
                }
                this.categorySettings = ko.observableArray<CategorySettingModel>(settings);
                this.categorySettingTabs = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: SalaryCategory.PAYMENT, title: '支給', content: '#payment', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: SalaryCategory.DEDUCTION, title: '控除', content: '#deduction', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: SalaryCategory.ATTENDANCE, title: '勤怠', content: '#attendance', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: SalaryCategory.ARTICLE_OTHERS, title: '記事・その他', content: '#article-others', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                this.selectedCategory = ko.observable(SalaryCategory.PAYMENT);
                var self = this;
                self.categorySettings().forEach((setting) => {
                    setting.outputItems.subscribe((newValue) => {
                        self.reloadReportItems();
                    });
                });
            }

            /**
            * Convert category setting data from dto to screen model.
            */
            private toModel(categorySettings?: CategorySettingDto[]): CategorySettingModel[] {
                var settings: CategorySettingModel[] = [];
                var test: CategorySettingDto[];
                if (categorySettings != undefined && categorySettings.length > 0) {
                    test = categorySettings;
                }

                // TODO... change later.
                settings[0] = this.filterSettingByCategory(SalaryCategory.PAYMENT, test);
                settings[1] = this.filterSettingByCategory(SalaryCategory.DEDUCTION, test);
                settings[2] = this.filterSettingByCategory(SalaryCategory.ATTENDANCE, test);
                settings[3] = this.filterSettingByCategory(SalaryCategory.ARTICLE_OTHERS, test);
                return settings;
            }

            private filterSettingByCategory(category: SalaryCategory, categorySettings?: CategorySettingDto[]): CategorySettingModel {
                var cateTempSetting: CategorySettingDto = { category: category, outputItems: [] };
                if (categorySettings == undefined) {
                    return new CategorySettingModel(category, this.aggregateItems, cateTempSetting);
                }

                var categorySetting = categorySettings.filter(item => item.category == category)[0];
                if (categorySetting == undefined) {
                    categorySetting = cateTempSetting;
                }
                return new CategorySettingModel(category, this.aggregateItems, categorySetting);
            }

        }

        /**
         * CategorySettingDto model.
         */
        export class CategorySettingDto {
            category: SalaryCategory;
            outputItems: OutputItem[];
            constructor(category: SalaryCategory,
                outputItems: OutputItem[]) {
                this.category = category;
                this.outputItems = outputItems;
            }
        }

        /**
         * ReportItem model.
         */
        export class ReportItem {
            code: string;
            name: string;
            categoryNameJPN: string;
            isAggregate: boolean;
            constructor(code: string, name: string, categoryName: SalaryCategory, isAggregate: boolean) {
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

        /**
         * CategorySettingModel model.
         */
        export class CategorySettingModel {
            categoryName: SalaryCategory;
            aggregateItems: KnockoutObservableArray<AggregateItem>;
            aggregateItemsSelected: KnockoutObservableArray<string>;
            masterItems: KnockoutObservableArray<MasterItem>;
            masterItemsSelected: KnockoutObservableArray<string>;
            outputItems: KnockoutObservableArray<OutputItem>;
            outputItemSelected: KnockoutObservable<string>;
            outputItemsSelected: KnockoutObservableArray<string>;
            outputItemColumns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            constructor(categoryName: SalaryCategory, aggregateItems: KnockoutObservableArray<AggregateItem>, categorySetting?: CategorySettingDto) {
                var self = this;
                self.categoryName = categoryName;
                self.aggregateItems = ko.observableArray<AggregateItem>([]);
                self.aggregateItemsSelected = ko.observableArray<string>([]);
                self.masterItems = ko.observableArray<MasterItem>([]);
                self.masterItemsSelected = ko.observableArray<string>([]);
                self.outputItems = ko.observableArray<OutputItem>(categorySetting != undefined ? categorySetting.outputItems : []);
                self.outputItemSelected = ko.observable(null);
                self.outputItemsSelected = ko.observableArray<string>([]);

                if (categoryName == SalaryCategory.PAYMENT) {
                    aggregateItems().forEach(item => {
                        if (item.taxDivision == TaxDivision.PAYMENT) {
                            self.aggregateItems.push(item);
                        }
                    })
                }

                if (categoryName == SalaryCategory.DEDUCTION) {
                    aggregateItems().forEach(item => {
                        if (item.taxDivision == TaxDivision.DEDUCTION) {
                            self.aggregateItems.push(item);
                        }
                    })
                }

                // mock data
                for (let i = 1; i < 15; i++) {
                    this.masterItems.push({ code: '00' + i, name: '基本給' + i, paymentType: 'Salary', taxDivision: 'Deduction' });
                }

                // Define outputItemColumns.
                this.outputItemColumns = ko.observableArray<any>([
                    {
                        headerText: '集約', prop: 'isAggregateItem', width: 40,
                        formatter: function(data: string) {
                            if (data == 'true') {
                                return '<div class="halign-center"><i class="icon icon-dot"></i></div>';
                            }
                            return '';
                        }
                    },
                    { headerText: 'コード', prop: 'code', width: 50 },
                    { headerText: '名称', prop: 'name', width: 60 },
                    {
                        headerText: '削除', prop: 'code', width: 60,
                        formatter: function(code: string) {
                            return '<div class="halign-center"><button class="icon icon-close" id="' + code + '" >'
                                + '</button></div>';
                        }
                    }
                ]);

                // Customs handle.
                (<any>ko.bindingHandlers).rended = {
                    init: function(element: any, valueAccessor: any, allBindings: any, viewModel: any, bindingContext: any) { },
                    update: function(element: any, valueAccessor: any, allBindings: any, viewModel: CategorySettingModel, bindingContext: any) {
                        var code = valueAccessor();
                        viewModel.outputItems().forEach(item => {
                            $('#' + item.code).on('click', function() {
                                code(item.code);
                                viewModel.remove();
                                code(null);
                            })
                        });
                    }
                };
            }

            /**
            * Move master item to outputItems.
            */
            public moveMasterItem() {
                // Check if has selected
                if (this.masterItemsSelected()[0]) {
                    var self = this;
                    // Get selected items from selected code list.
                    var selectedItems: MasterItem[] = [];
                    self.masterItemsSelected().forEach(selectedCode => {
                        selectedItems.push(self.masterItems().filter((item: MasterItem) => {
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
                        });
                    });
                    self.masterItemsSelected([]);
                }
            }

            /**
            * Move aggregate item to outputItems.
            */
            public moveAggregateItem() {
                // Check if has selected
                if (this.aggregateItemsSelected()[0]) {
                    var self = this;
                    // Get selected items from selected code list.
                    var selectedItems: AggregateItem[] = [];
                    self.aggregateItemsSelected().forEach(selectedCode => {
                        selectedItems.push(self.aggregateItems().filter((item: AggregateItem) => {
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
                        });
                    });
                    self.aggregateItemsSelected([]);
                }
            }

            /**
            * Remove item from outputItems.
            */
            public remove(): void {
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
                        taxDivision: TaxDivision.PAYMENT,
                    });
                    return;
                }
                // Return to master items table.
                self.masterItems.push({
                    code: selectedItem.code,
                    name: selectedItem.name,
                    paymentType: PaymentType.SALARY,
                    taxDivision: TaxDivision.DEDUCTION
                });
            }

        }
        export class AggregateItem {
            code: string;
            name: string;
            taxDivision: TaxDivision;
        }
        export class MasterItem {
            code: string;
            name: string;
            paymentType: PaymentType;
            taxDivision: TaxDivision;
        }
        export class OutputItem {
            code: string;
            name: string;
            isAggregateItem: boolean;
            //orderNumber: number; no use?
        }
        export class SalaryCategory {
            static PAYMENT = 'Payment';
            static DEDUCTION = 'Deduction';
            static ATTENDANCE = 'Attendance';
            static ARTICLE_OTHERS = 'ArticleOthers';
        }
        export class TaxDivision {
            static PAYMENT = 'Payment';
            static DEDUCTION = 'Deduction';
        }
        export class SalaryOutputDistinction {
            static HOURLY = 'Hourly';
            static MINUTLY = 'Minutely';
        }
        export class PaymentType {
            static SALARY = 'Salary';
            static BONUS = 'Bonus';
        }
    }
}