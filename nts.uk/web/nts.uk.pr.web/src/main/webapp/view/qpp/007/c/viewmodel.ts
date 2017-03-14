module nts.uk.pr.view.qpp007.c {
    export module viewmodel {

        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            outputSettingDetailModel: KnockoutObservable<OutputSettingDetailModel>;
            reportItems: KnockoutObservableArray<ReportItem>;
            reportItemSelected: KnockoutObservable<string>;
            isLoading: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.isLoading = ko.observable(true);
                self.items = ko.observableArray<ItemModel>([]);
                self.currentCode = ko.observable();
                self.outputSettingDetailModel = ko.observable(new OutputSettingDetailModel());
                self.reportItems = ko.observableArray<ReportItem>([]);
                self.reportItemSelected = ko.observable('');

                for (let i = 1; i < 30; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給' + i, "name " + i, i % 3 === 0));
                }
                this.columns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', key: 'code', width: 50 },
                    { headerText: '名称', key: 'name', width: 50 }
                ]);
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
                this.categorySettingTabs = ko.observableArray<nts.uk.ui.NtsTabPanelModel>([
                    { id: SalaryCategory.SUPPLY, title: '支給', content: '#supply', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: SalaryCategory.DEDUCTION, title: '控除', content: '#deduction', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: SalaryCategory.ATTENDANCE, title: '勤怠', content: '#attendance', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: SalaryCategory.ARTICLE_OTHERS, title: '記事・その他', content: '#article-others', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                this.selectedCategory = ko.observable(SalaryCategory.SUPPLY);
            }
        }

        export class CategorySetting {
            outputItems: KnockoutObservableArray<OutputItem>;
            aggregateItems: KnockoutObservableArray<AggregateItem>;
            masterItems: KnockoutObservableArray<MasterItem>;
            aggregateItemsSelected: KnockoutObservableArray<string>;
            masterItemsSelected: KnockoutObservableArray<string>;
            outputItemSelected: KnockoutObservable<string>;
            outputItemColumns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            constructor() {
                var self = this;
                self.outputItems = ko.observableArray<OutputItem>([]);
                self.aggregateItems = ko.observableArray<AggregateItem>([]);
                self.masterItems = ko.observableArray<MasterItem>([]);
                self.outputItemSelected = ko.observable(null);
                self.aggregateItemsSelected = ko.observableArray<string>([]);
                self.masterItemsSelected = ko.observableArray<string>([]);

                for (let i = 1; i < 15; i++) {
                    this.aggregateItems.push({ code: '00' + i, name: '基本給' + i, subsItem: [], taxDivision: 'Payment', value: i });
                }
                for (let i = 1; i < 15; i++) {
                    this.masterItems.push({ code: '00' + i, name: '基本給' + i, paymentType: 'Salary', taxDivision: 'Deduction' });
                }
                this.outputItemColumns = ko.observableArray<nts.uk.ui.NtsGridListColumn>([
                    { headerText: 'コード', key: 'code', width: 50 },
                    { headerText: '名称', key: 'name', width: 50 }
                ]);
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
                    self.masterItemsSelected([]);
                }
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
            static SUPPLY = 'Supply';
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
        }
    }
}