module nts.uk.pr.view.qpp007.c {
    export module viewmodel {

        export class ScreenModel {
            outputSettings: KnockoutObservableArray<ItemModel>;
            outputSettingSelectedCode: KnockoutObservable<string>;
            outputSettingColumns: KnockoutObservableArray<any>;
            outputSettingDetailModel: KnockoutObservable<OutputSettingDetailModel>;
            reportItems: KnockoutObservableArray<ReportItem>;
            reportItemSelected: KnockoutObservable<string>;
            reportItemColumns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            isLoading: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.isLoading = ko.observable(true);
                self.outputSettings = ko.observableArray<ItemModel>([]);
                self.outputSettingSelectedCode = ko.observable('');
                self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel());
                self.reportItems = ko.observableArray<ReportItem>([]);
                self.reportItemSelected = ko.observable('');
                self.outputSettingSelectedCode

                for (let i = 1; i < 30; i++) {
                    this.outputSettings.push(new ItemModel('00' + i, '基本給' + i, "name " + i, i % 3 === 0));
                }

                this.reportItemColumns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    //{headerText: '区分', prop: '', width: 50},
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

                //                self.outputSettingDetailModel.subscribe((data: OutputSettingDetailModel) => {
                //                    self.reloadReportItem();
                //                    data.reloadReportItems = self.reloadReportItem.bind(self);
                //                });
                self.outputSettingDetailModel().reloadReportItems = self.reloadReportItems.bind(self);
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
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
                    var categoryName: string = setting.category;
                    setting.outputItems().forEach((item) => {
                        reportItemList.push(new ReportItem(item.code, item.name, categoryName, item.isAggregateItem));
                    });
                });
                self.reportItems(reportItemList);
            }

            public commonSettingBtnClick() {
                nts.uk.ui.windows.sub.modal('/view/qpp/007/j/index.xhtml', { title: '集計項目の設定', dialogClass: 'no-close' });
            }

        }
        export class ItemModel {
            code: string;
            name: string;
            description: string;
            deletable: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.deletable = deletable;
            }
        }

        export class OutputSettingDetailModel {
            settingCode: KnockoutObservable<string>;
            settingName: KnockoutObservable<string>;
            categorySettingTabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedCategory: KnockoutObservable<string>;
            categorySettings: KnockoutObservableArray<CategorySetting>;
            reloadReportItems: () => void;
            constructor() {
                this.settingCode = ko.observable('code');
                this.settingName = ko.observable('name 123');
                this.categorySettings = ko.observableArray<CategorySetting>([]);
                this.categorySettings.push(new CategorySetting()); this.categorySettings.push(new CategorySetting());
                this.categorySettings.push(new CategorySetting()); this.categorySettings.push(new CategorySetting());
                for (let i = 1; i < 15; i++) {
                    this.categorySettings()[2].outputItems.push({
                        code: i,
                        name: 'name' + i,
                        isAggregateItem: false,
                        removable: true
                    });
                    this.categorySettings()[3].outputItems.push({
                        code: i+1,
                        name: 'name' + i+1,
                        isAggregateItem: false,
                        removable: true
                    });
                };
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
             * Change swaplist's width after render
             */
            public afterRender() {
                $('.master-table-label').width($('#swap-list-gridArea1').width());
                $('.sub-table-label').width($('#swap-list-gridArea2').width());
            }

        }

        export class CategorySetting {
            aggregateItems: KnockoutObservableArray<AggregateItem>;
            aggregateItemsSelected: KnockoutObservableArray<string>;
            masterItems: KnockoutObservableArray<MasterItem>;
            masterItemsSelected: KnockoutObservableArray<string>;
            outputItems: KnockoutObservableArray<OutputItem>;
            outputItemSelected: KnockoutObservable<string>;
            outputItemsSelected: KnockoutObservableArray<string>;
            outputItemColumns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            constructor() {
                var self = this;
                self.aggregateItems = ko.observableArray<AggregateItem>([]);
                self.aggregateItemsSelected = ko.observableArray<string>([]);
                self.masterItems = ko.observableArray<MasterItem>([]);
                self.masterItemsSelected = ko.observableArray<string>([]);
                self.outputItems = ko.observableArray<OutputItem>([]);
                self.outputItemSelected = ko.observable(null);
                self.outputItemsSelected = ko.observableArray<string>([]);

                // mock data
                for (let i = 1; i < 15; i++) {
                    this.aggregateItems.push({ code: '00' + i, name: '基本給' + i, subsItem: [], taxDivision: 'Payment', value: i });
                }
                for (let i = 1; i < 15; i++) {
                    this.masterItems.push({ code: '00' + i, name: '基本給' + i, paymentType: 'Salary', taxDivision: 'Deduction' });
                }

                // Define outputItemColumns.
                this.outputItemColumns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
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
                    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) { },
                    update: function(element, valueAccessor, allBindings, viewModel: CategorySetting, bindingContext) {
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
                            removable: true
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
                            removable: true
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
                        subsItem: [],
                        taxDivision: 'Payment',
                        value: 5
                    });
                    return;
                }
                // Return to master items table.
                self.masterItems.push({
                    code: selectedItem.code,
                    name: selectedItem.name,
                    paymentType: 'Salary',
                    taxDivision: 'Deduction'
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
            removable: boolean;
        }
        export class SalaryCategory {
            static PAYMENT = 'Payment';
            static PAYMENT_TOTAL = 'PaymentTotal';
            static DEDUCTION = 'Deduction';
            static DEDUCTION_TABULATION = 'DeductionTabulation';
            static ATTENDANCE = 'Attendance';
            static ARTICLE_OTHERS = 'ArticleOthers';
        }
        export class TaxDivision {
            static PAYMENT = 'Payment';
            static DEDUCTION = 'Deduction';
        }
        export class SalaryOutputDistinction {
            HOURLY = 'Hourly';
            MINUTLY = 'Minutely';
        }
        export class PaymentType {
            SALARY = 'Salary';
            BONUS = 'Bonus';
        }
        export class ReportItem {
            code: string;
            name: string;
            categoryName: string;
            isAggregate: boolean;
            constructor(code: string, name: string, categoryName: string, isAggregate: boolean) {
                this.code = code;
                this.name = name;
                this.categoryName = categoryName;
                this.isAggregate = isAggregate;
            }
        }
    }
}