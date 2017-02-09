module qet001.b.viewmodel {
    import NtsGridColumn = nts.uk.ui.NtsGridListColumn;
    import NtsTabModel = nts.uk.ui.NtsTabPanelModel;
    import WageLedgerOutputSetting = qet001.a.service.model.WageLedgerOutputSetting;
    import WageledgerCategorySetting = qet001.a.service.model.WageledgerCategorySetting;
    import WageLedgerSettingItem = qet001.a.service.model.WageLedgerSettingItem;
    
    export class ScreenModel {
        outputSettings: KnockoutObservable<OutputSettings>;
        outputSettingDetail: KnockoutObservable<OutputSettingDetail>;
        reportItems: KnockoutObservableArray<ReportItem>;
        reportItemColumns: KnockoutObservableArray<any>;
        reportItemSelected: KnockoutObservable<string>;
        aggregateItemsList: service.Item[];
        masterItemList: service.Item[];
        isLoading: KnockoutObservable<boolean>;
        
        constructor() {
            this.outputSettings = ko.observable(new OutputSettings());
            this.outputSettingDetail= ko.observable(new OutputSettingDetail([], []));
            this.reportItems = ko.observableArray([]);
            this.isLoading = ko.observable(true);
            this.reportItemColumns = ko.observableArray([
                    {headerText: '区分', prop: 'categoryNameJa', width: 50},
                    {headerText: '集約', prop: 'isAggregate', width: 40,
                        formatter: function(data: string) {
                            if (data == 'true') {
                                return '<div class="center"><i class="icon icon-dot"></i></div>';
                            }
                            return '';
                        }
                    },
                    {headerText: 'コード', prop: 'itemCode', width: 100},
                    {headerText: '名称', prop: 'itemName', width: 100},
                ]);
            this.reportItemSelected = ko.observable(null);
            this.aggregateItemsList = [];
            this.masterItemList = [];
            
            var self = this;
            self.outputSettings().outputSettingSelectedCode.subscribe((newVal: string) => {
                self.isLoading(true);
                if (newVal== undefined || newVal == null || newVal == '') {
                    self.outputSettingDetail(new OutputSettingDetail(self.aggregateItemsList, self.masterItemList));
                    self.isLoading(false);
                    return;
                }
                // load detail output setting.
                self.loadOutputSettingDetail(newVal);
                self.isLoading(false);
            })
            
            self.outputSettingDetail.subscribe((data: OutputSettingDetail) => {
                self.reloadReportItem();
                data.reloadReportItems = self.reloadReportItem.bind(self);
            });
        }
        
        /**
         * Reload report items.
         */
        public reloadReportItem() {
            var self = this;
            var data = self.outputSettingDetail();
            if (data == undefined || data == null || data.categorySettings().length == 0) {
                self.reportItems([]);
                return;
            }
            // Set data to report item list.
            var reportItemList: ReportItem[] = [];
            data.categorySettings().forEach((setting) => {
                var categoryName: string = setting.category;
                setting.outputItems().forEach((item) => {
                    reportItemList.push(new ReportItem(categoryName, item.isAggregateItem, item.code, item.name));
                });
            });
            self.reportItems(reportItemList);
        }
        
        public start(): JQueryPromise<void>{
            var dfd = $.Deferred<void>();
            var self = this;
            var outputSettings: WageLedgerOutputSetting[] = nts.uk.ui.windows.getShared('outputSettings');
            var selectedSettingCode: string = nts.uk.ui.windows.getShared('selectedCode');
            
            // Load master items and aggregate items.
            $.when(self.loadAggregateItems(), self.loadMasterItems()).done(() => {
                // Check output setting is empty.
                if (outputSettings != undefined) {
                    self.outputSettings().outputSettingList(outputSettings);
                }
                self.outputSettingDetail().isCreateMode(outputSettings == undefined || outputSettings.length == 0);
                self.outputSettings().outputSettingSelectedCode(selectedSettingCode);
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /**
         * Close dialog.
         */
        public close() {
            nts.uk.ui.windows.close();
        }
        
        /**
         * Save output setting.
         */
        public save() {
            var self = this;
            // Validate.
            if (self.outputSettingDetail().settingCode() == '') {
                $('#code-input').ntsError('set', '未入力エラー');
            }
            if(self.outputSettingDetail().settingName() == '') {
                $('#name-input').ntsError('set', '未入力エラー');
            }
            if(!nts.uk.ui._viewModel.errors.isEmpty()) {
                return;
            }
            service.saveOutputSetting(self.outputSettingDetail()).done(function() {
                
            }).fail(function(res) {
                // TODO: Show message duplicate code.
                nts.uk.ui.dialog.alert(res.message);
            })
        }
        
        /**
         * Remove Output setting.
         */
        public remove() {
            var self = this;
            // Check selected output setting.
            var selectedCode = self.outputSettings().outputSettingSelectedCode();
            if (selectedCode == '') {
                // TODO: Add error message '未選択エラー'.
                nts.uk.ui.dialog.alert('未選択エラー');
                return;
            }
            service.removeOutputSetting(selectedCode).done(function() {
                var selectedSetting = self.outputSettings().outputSettingList().filter(setting => setting.code == selectedCode)[0];
                self.outputSettings().outputSettingList.remove(selectedSetting);
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
            });
        }
        
        /**
         * Load detail output setting.
         */
        public loadOutputSettingDetail(selectedCode: string): JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            var self = this;
            
            service.findOutputSettingDetail(selectedCode).done(function(data: WageLedgerOutputSetting){
                self.outputSettingDetail(new OutputSettingDetail(self.aggregateItemsList, self.masterItemList, data));
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
                dfd.reject();
            })
            return dfd.promise();
        }
        
        /**
         * Load Aggregate items.
         */
        public loadAggregateItems(): JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            var self = this;
            service.findAggregateItems().done((item: service.Item[]) => {
                self.aggregateItemsList = item;
                dfd.resolve();
            }).fail((res) => {
                nts.uk.ui.dialog.alert(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        /**
         * Load master item.
         */
        public loadMasterItems(): JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            var self = this;
            service.findMasterItems().done((item: service.Item[]) => {
                self.masterItemList = item;
                dfd.resolve();
            }).fail((res) => {
                nts.uk.ui.dialog.alert(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        /**
         * Switch to create mode.
         */
        public switchToCreateMode() {
            this.outputSettingDetail(new OutputSettingDetail(this.aggregateItemsList, this.masterItemList));
            this.outputSettings().outputSettingSelectedCode('');
        }
    }
    
    /**
     * 登録済みの出力項目設定
     */
    export class OutputSettings {
        searchText: KnockoutObservable<string>;
        outputSettingList: KnockoutObservableArray<WageLedgerOutputSetting>;
        outputSettingSelectedCode: KnockoutObservable<string>;
        outputSettingColumns: KnockoutObservableArray<any>;
        
        constructor() {
            this.searchText = ko.observable('');
            this.outputSettingList = ko.observableArray([]);
            this.outputSettingSelectedCode = ko.observable('');
            this.outputSettingColumns = ko.observableArray([
                {headerText: 'コード', prop: 'code', width: 90}, 
                {headerText: '名称', prop: 'name',  width: 100}]);
        }
    }
    
    /**
     * Output setting detail.
     */
    export class OutputSettingDetail {
        settingCode: KnockoutObservable<string>;
        settingName: KnockoutObservable<string>;
        isPrintOnePageEachPer: KnockoutObservable<boolean>;
        categorySettingTabs: KnockoutObservableArray<NtsTabModel>;
        selectedCategory: KnockoutObservable<string>;
        isCreateMode: KnockoutObservable<boolean>;
        categorySettings: KnockoutObservableArray<CategorySetting>;
        reloadReportItems: () => void;
        
        constructor (aggregateItems: service.Item[], masterItem: service.Item[], outputSetting?: WageLedgerOutputSetting) {
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
            var categorySetting : CategorySetting[] = [];
            if (outputSetting == undefined) {
                categorySetting = this.convertCategorySettings(aggregateItems, masterItem);
            } else {
                categorySetting = this.convertCategorySettings(aggregateItems, masterItem, outputSetting.categorySettings);
            }
            this.categorySettings = ko.observableArray(categorySetting);
            var self = this;
            self.categorySettings().forEach((setting) => {
                setting.outputItems.subscribe((newValue) => {
                    self.reloadReportItems();
                });
            });
        }
        
        /**
         * Convert category setting data to screen model.
         */
        private convertCategorySettings(aggregateItems: service.Item[], masterItem: service.Item[],
                categorySettings?: WageledgerCategorySetting[]) : CategorySetting[] {
            var settings: CategorySetting[] = [];
            
            settings[0] = this.createCategorySetting(Category.PAYMENT, PaymentType.SALARY, aggregateItems, masterItem, categorySettings);
            settings[1] = this.createCategorySetting(Category.DEDUCTION, PaymentType.SALARY, aggregateItems, masterItem, categorySettings);
            settings[2] = this.createCategorySetting(Category.ATTENDANCE, PaymentType.SALARY, aggregateItems, masterItem, categorySettings);
            settings[3] = this.createCategorySetting(Category.PAYMENT, PaymentType.BONUS, aggregateItems, masterItem, categorySettings);
            settings[4] = this.createCategorySetting(Category.DEDUCTION, PaymentType.BONUS, aggregateItems, masterItem, categorySettings);
            settings[5] = this.createCategorySetting(Category.ATTENDANCE, PaymentType.BONUS, aggregateItems, masterItem, categorySettings);
            
            return settings;
        }
        
        private createCategorySetting(category: string, paymentType: string,
                aggregateItems: service.Item[], masterItem: service.Item[], categorySettings?: WageledgerCategorySetting[]): CategorySetting {
            //var categorySetting: CategorySetting;
            var aggregateItemsInCategory = aggregateItems.filter((item) => item.category == category 
                && item.paymentType == paymentType);
            var masterItemsInCategory = masterItem.filter((item) => item.category == category 
                && item.paymentType == paymentType);
            var cateTempSetting: WageledgerCategorySetting = {category: category, paymentType: paymentType, outputItems: []};
            if (categorySettings == undefined) {
                return new CategorySetting(aggregateItemsInCategory, masterItemsInCategory, cateTempSetting);
            }
            
            var categorySetting = categorySettings.filter((item) => item.category == category 
                && item.paymentType == paymentType)[0];
            if (categorySetting == undefined) {
                categorySetting = cateTempSetting;
            }
            return new CategorySetting(aggregateItemsInCategory, masterItemsInCategory, categorySetting);
        }
    }
    
    /**
     * Category setting class.
     */
    export class CategorySetting {
        category: string;
        paymentType: string;
        outputItems: KnockoutObservableArray<WageLedgerSettingItem>;
        aggregateItemsList: KnockoutObservableArray<service.Item>;
        masterItemList: KnockoutObservableArray<service.Item>;
        aggregateItemSelected: KnockoutObservable<string>;
        masterItemSelected: KnockoutObservable<string>;
        outputItemsSelected: KnockoutObservable<string>;
        outputItemColumns: KnockoutObservableArray<any>;

        constructor(aggregateItems: service.Item[], masterItems: service.Item[],
                categorySetting?: WageledgerCategorySetting) {
            this.category = categorySetting.category;
            this.paymentType = categorySetting.paymentType;
            this.outputItems = ko.observableArray(categorySetting != undefined ? categorySetting.outputItems : []);

            // exclude item contain in setting.
            var settingItemCode: string[] = [];
            if (categorySetting != undefined) {
                settingItemCode = categorySetting.outputItems.map((item) => {
                    return item.code;
                });
            }
            var aggregateItemsExcluded = aggregateItems.filter((item) => settingItemCode.indexOf(item.code) == -1);
            var masterItemsExcluded = masterItems.filter((item) => settingItemCode.indexOf(item.code) == -1);
            this.aggregateItemsList = ko.observableArray(aggregateItemsExcluded);
            this.masterItemList = ko.observableArray(masterItemsExcluded);
            this.outputItemsSelected = ko.observable(null);
            this.aggregateItemSelected = ko.observable(null);
            this.masterItemSelected = ko.observable(null);
            this.outputItemColumns = ko.observableArray([
                    {headerText: '集約', prop: 'isAggregateItem', width: 40,
                        formatter: function(data: string) {
                            if (data == 'true') {
                                return '<div class="center"><i class="icon icon-dot"></i></div>';
                            }
                            return '';
                        }
                    },
                    {headerText: 'コード', prop: 'code', width: 60},
                    {headerText: '名称', prop: 'name', width: 100},
                ]);
        }
        
        /**
         * Move master item to outputItems.
         */
        public masterItemToDisplay() {
            // If master item is unselected => return.
            if (this.masterItemSelected() == undefined || this.masterItemSelected() == null) {
                return;
            }
            var self = this;
            var selectedItem: service.Item = self.masterItemList().filter((item: service.Item) => {
                return item.code == self.masterItemSelected();
            })[0];
            
            // Remove form master list.
            self.masterItemList.remove(selectedItem);
            
            // Add to outputItems.
            self.outputItems.push({
                code: selectedItem.code,
                name: selectedItem.name,
                isAggregateItem: true,
            });
            self.masterItemSelected(null);
        }
        
        public aggregateItemToDisplay() {
            // If master item is unselected => return.
            if (this.aggregateItemSelected() == undefined || this.aggregateItemSelected() == null) {
                return;
            }
            var self = this;
            var selectedItem: service.Item = self.aggregateItemsList().filter((item: service.Item) => {
                return item.code == self.aggregateItemSelected();
            })[0];
            
            // Remove form master list.
            self.aggregateItemsList.remove(selectedItem);
            
            // Add to outputItems.
            self.outputItems.push({
                code: selectedItem.code,
                name: selectedItem.name,
                isAggregateItem: false,
            });
            self.aggregateItemSelected(null);
        }
    }
    
    /**
     * Report Item class.
     */
    export class ReportItem {
        categoryName: string;
        isAggregate: boolean;
        itemCode: string;
        itemName: string;
        categoryNameJa: string;
        
        constructor(categoryName: string, isAggregate: boolean, itemCode: string, itemName: string) {
            this.categoryName = categoryName;
            this.isAggregate = isAggregate;
            this.itemCode = itemCode;
            this.itemName = itemName;
            var self = this;
            // Convert category name to japanese.
            switch(categoryName) {
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
    }
    
    /**
     * 出力するレイアウト.
     */
    export class LayoutOutput {
        /**
         * 賃金台帳（A4横1ページ）を出力する
         */
        static WAGE_LEDGER = 0;
        
        /**
         * 賃金一覧表を出力する
         */
        static WAGE_LIST = 1;
    }
    
    /**
     * 出力する項目の選択
     */
    export class OutputType {
        /**
         * 明細書項目を出力する
         */
        static DETAIL_ITEM = 0;
        /**
         * 明細書の集約項目を出力する
         */
        static SUMMARY_DETAIL_ITEMS = 1;
    }
    
    /**
     * Wage ledger category.
     */
    export class Category {
        /**
         * 支給
         */
        static PAYMENT = 'Payment';
        
        /**
         * 控除
         */
        static DEDUCTION = 'Deduction';
        
        /**
         * 勤怠
         */
        static ATTENDANCE = 'Attendance';
    }
    
    /**
     * Wage ledger payment type.
     */
    export class PaymentType {
        /**
         * Salary.
         */
        static SALARY = 'Salary';
        
        /**
         * Bonus.
         */
        static BONUS = 'Bonus'
    }
}