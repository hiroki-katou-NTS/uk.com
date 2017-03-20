module nts.uk.pr.view.qpp007.c {
    export module viewmodel {
        import aggregateService = nts.uk.pr.view.qpp007.j.service;

        export class ScreenModel {
            outputSettings: KnockoutObservableArray<OutputSettingHeader>;
            outputSettingSelectedCode: KnockoutObservable<string>;
            outputSettingColumns: KnockoutObservableArray<any>;
            outputSettingDetailModel: KnockoutObservable<OutputSettingDetailModel>;
            reportItems: KnockoutObservableArray<ReportItem>;
            reportItemSelected: KnockoutObservable<string>;
            reportItemColumns: KnockoutObservableArray<any>;
            isLoading: KnockoutObservable<boolean>;
            isNewMode: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.isLoading = ko.observable(true);
                self.isNewMode = ko.observable(false);
                self.outputSettings = ko.observableArray<OutputSettingHeader>([]);
                self.outputSettingSelectedCode = ko.observable('');
                self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel());
                self.reportItems = ko.observableArray<ReportItem>([]);
                self.reportItemSelected = ko.observable('');

                for (let i = 1; i < 30; i++) {
                    this.outputSettings.push(new OutputSettingHeader('00' + i, '基本給' + i));
                }

                this.reportItemColumns = ko.observableArray<any>([
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

                self.outputSettingSelectedCode.subscribe((id) => {
                    self.onSelectOutputSetting(id);
                })
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.loadAllOutputSetting().done(() => {
                    self.isLoading(false);
                    self.loadAggregateItems();
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
            * Reload report items.
            */
            public reloadReportItems(): void {
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
            * Collect Data
            */
            public collectData(): OutputSettingDto {
                var self = this;
                var model = self.outputSettingDetailModel();
                var data = new OutputSettingDto();
                data.code = model.settingCode();
                data.name = model.settingName();
                var settings = new Array<CategorySettingDto>();
                model.categorySettings().forEach(item => {
                    settings.push(new CategorySettingDto(SalaryCategory.PAYMENT, item.outputItems()));
                });
                data.categorySettings = settings;
                return data;
            }

            /**
            * Save outputSetting.
            */
            public save(): void {
                var self = this;
                // clear error.
                $('#inpCode').ntsError('clear');
                $('#inpName').ntsError('clear');
                // Validate.
                var hasError = false;
                if (self.outputSettingDetailModel().settingCode() == '') {
                    $('#inpCode').ntsError('set', '未入力エラー');
                    hasError = true;
                }
                if (self.outputSettingDetailModel().settingName() == '') {
                    $('#inpName').ntsError('set', '未入力エラー');
                    hasError = true;
                }
                if (hasError) {
                    return;
                }
                var data = self.collectData();
                if (self.isNewMode()) {
                    data.createMode = true;
                } else {
                    data.createMode = false;
                }
                service.save(data).done(() => {
                    self.isNewMode(false);
                    self.loadAllOutputSetting()
                });
            }

            /**
            * Delete outputSetting.
            */
            public remove(): void {
                if (this.outputSettingSelectedCode) {
                    service.remove(this.outputSettingSelectedCode()).done(() => this.loadAllOutputSetting());
                }
            }

            /**
            * Open common setting dialog.
            */
            public commonSettingBtnClick(): void {
                nts.uk.ui.windows.sub.modal('/view/qpp/007/j/index.xhtml', { title: '集計項目の設定', dialogClass: 'no-close' });
            }

            /**
             * Enter new mode.
             */
            public newModeBtnClick(): void {
                var self = this;
                // Clear outputSetting SelectedCode
                self.outputSettingDetailModel(new OutputSettingDetailModel());
                self.outputSettingSelectedCode('');
                self.isNewMode(true);
            }

            /**
           * On select outputSetting
           */
            private onSelectOutputSetting(id: string): void {
                var self = this;
                $('.save-error').ntsError('clear');
                // self.isLoading(true);
                self.isNewMode(false)
                self.loadOutputSettingDetail(id).done(() => {
                    // self.isLoading(false);
                });
            }

            /**
             * Load all output setting.
             */
            public loadAllOutputSetting(): JQueryPromise<void> {
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
            public loadOutputSettingDetail(code: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                service.findOutputSettingDetail(code).done(function(data: OutputSettingDto) {
                    self.outputSettingDetailModel(new OutputSettingDetailModel(data));
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
            public loadAggregateItems(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                $.when(aggregateService.findSalaryAggregateItem({ taxDivision: 0, aggregateItemCode: '001' }),
                    aggregateService.findSalaryAggregateItem({ taxDivision: 1, aggregateItemCode: '001' })).done((res1, res2) => {
                        // TODO ...
                    });
                return dfd.promise();
            }

            /**
            * Load master items
            */
            public loadMasterItems(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                return dfd.promise();
            }

            /**
            * Close dialog.
            */
            public close(): void {
                nts.uk.ui.windows.close();
            }

        }
        export class OutputSettingHeader {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class OutputSettingDto {
            code: string;
            name: string;
            categorySettings: CategorySettingDto[];
            createMode: boolean;
        }

        export class OutputSettingDetailModel {
            settingCode: KnockoutObservable<string>;
            settingName: KnockoutObservable<string>;
            categorySettingTabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedCategory: KnockoutObservable<string>;
            categorySettings: KnockoutObservableArray<CategorySettingModel>;
            reloadReportItems: () => void;
            constructor(outputSetting?: OutputSettingDto) {
                this.settingCode = ko.observable(outputSetting != undefined ? outputSetting.code : '');
                this.settingName = ko.observable(outputSetting != undefined ? outputSetting.name : '');
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
                    return new CategorySettingModel(category, cateTempSetting);
                }

                var categorySetting = categorySettings.filter(item => item.category == category)[0];
                if (categorySetting == undefined) {
                    categorySetting = cateTempSetting;
                }
                return new CategorySettingModel(category, categorySetting);
            }

        }

        export class CategorySettingDto {
            category: SalaryCategory;
            outputItems: OutputItem[];
            constructor(category: SalaryCategory,
                outputItems: OutputItem[]) {
                this.category = category;
                this.outputItems = outputItems;
            }
        }

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
            constructor(categoryName: SalaryCategory, categorySetting?: CategorySettingDto) {
                var self = this;
                self.categoryName = categoryName;
                self.aggregateItems = ko.observableArray<AggregateItem>([]);
                self.aggregateItemsSelected = ko.observableArray<string>([]);
                self.masterItems = ko.observableArray<MasterItem>([]);
                self.masterItemsSelected = ko.observableArray<string>([]);
                self.outputItems = ko.observableArray<OutputItem>(categorySetting != undefined ? categorySetting.outputItems : []);
                self.outputItemSelected = ko.observable(null);
                self.outputItemsSelected = ko.observableArray<string>([]);

                // mock data
                for (let i = 1; i < 15; i++) {
                    this.aggregateItems.push({ code: '00' + i, name: '基本給' + i, subItems: [], taxDivision: 'Payment', value: i });
                }
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
                        subItems: [],
                        taxDivision: TaxDivision.PAYMENT,
                        value: 5
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
            subItems: string[];
            taxDivision: TaxDivision;
            value: number;
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
        export class ReportItem {
            code: string;
            name: string;
            categoryName: SalaryCategory;
            isAggregate: boolean;
            constructor(code: string, name: string, categoryName: SalaryCategory, isAggregate: boolean) {
                this.code = code;
                this.name = name;
                this.categoryName = categoryName;
                this.isAggregate = isAggregate;
            }
        }
    }
}