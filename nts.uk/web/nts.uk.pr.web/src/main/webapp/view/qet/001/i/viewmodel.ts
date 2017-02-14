module qet001.i.viewmodel {
    import NtsGridColumn = nts.uk.ui.NtsGridListColumn;
    import WageLedgerOutputSetting = qet001.a.service.model.WageLedgerOutputSetting;
    import WageledgerCategorySetting = qet001.a.service.model.WageledgerCategorySetting;
    import WageLedgerSettingItem = qet001.a.service.model.WageLedgerSettingItem;
    
    export class ScreenModel {
        aggregateItemCategories: KnockoutObservableArray<AggregateCategory>;
        masterItems: KnockoutObservableArray<service.Item>;
        selectedTab: KnockoutObservable<number>;
        
        constructor() {
            this.aggregateItemCategories = ko.observableArray([]);
            this.masterItems = ko.observableArray([]);
            this.selectedTab = ko.observable(0);
            var self = this;
            $("#sidebar-area > ul > li").on('click', function() {
                self.selectedTab($("#sidebar-area > ul > li").index(this));
            });
            self.selectedTab.subscribe(val => {
                if (val == undefined || val == null) {
                    return;
                }
                // reload tab.
                self.aggregateItemCategories()[val].loadAggregateItemByCategory();
            })
        }
        
        start(): JQueryPromise<void>{
            var dfd = $.Deferred<void>();
            var self = this;
            service.findMasterItems().done(function(res) {
                self.masterItems(res);
                
                // init aggregate categories.
                self.aggregateItemCategories.push(new AggregateCategory(PaymentType.SALARY, Category.PAYMENT, res));
                self.aggregateItemCategories.push(new AggregateCategory(PaymentType.SALARY, Category.DEDUCTION, res));
                self.aggregateItemCategories.push(new AggregateCategory(PaymentType.SALARY, Category.ATTENDANCE, res));
                self.aggregateItemCategories.push(new AggregateCategory(PaymentType.BONUS, Category.PAYMENT, res));
                self.aggregateItemCategories.push(new AggregateCategory(PaymentType.BONUS, Category.DEDUCTION, res));
                self.aggregateItemCategories.push(new AggregateCategory(PaymentType.BONUS, Category.ATTENDANCE, res));
                
                self.aggregateItemCategories()[0].loadAggregateItemByCategory();
            })
            dfd.resolve();
            return dfd.promise();
        }
    }
    
    export class AggregateCategory {
        itemList: KnockoutObservableArray<service.Item>;
        category: string;
        paymentType: string;
        itemListColumns: KnockoutObservableArray<any>;
        aggregateItemSelectedCode: KnockoutObservable<string>;
        aggregateItemDetail: KnockoutObservable<AggregateItemDetail>;
        
        constructor(paymentType: string, categoryName: string, masterItems: service.Item[]) {
            this.itemList = ko.observableArray([]);
            this.category = categoryName;
            this.paymentType = paymentType;
            this.aggregateItemSelectedCode = ko.observable(null);
            this.itemListColumns = ko.observableArray([
                {headerText: 'コード', prop: 'code', width: 90}, 
                {headerText: '名称', prop: 'name',  width: 100}]);
            
            // Filter master item by category and payment type.
            var masterItemInCate = masterItems.filter(item => item.category == categoryName);
            this.aggregateItemDetail = ko.observable(new AggregateItemDetail(paymentType, 
                categoryName, masterItemInCate));
            var self = this;
            self.aggregateItemSelectedCode.subscribe((code) => {
                if (code == undefined || code == null || code == '') {
                    return;
                }
                self.loadDetailAggregateItem(code).done(function(res: service.Item) {
                    self.aggregateItemDetail(new AggregateItemDetail(paymentType,
                        categoryName, masterItemInCate, res));
                });
            })
        }
        
        loadAggregateItemByCategory() : JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            // Fake data.
            var self = this;
            var items: Array<service.Item> = [];
            for (var i = 0; i < 10; i++) {
                items.push({code: 'AGG' + i, name: 'Aggregate item ' + i, category: self.category, 
                    paymentType: self.paymentType, showNameZeroValue: true, showValueZeroValue: true});
            }
            self.itemList(items);
            dfd.resolve();
            return dfd.promise();
        }
        
        
        loadDetailAggregateItem(code: string): JQueryPromise<service.Item> {
            var dfd = $.Deferred<service.Item>();
            var self = this;
            var selectedCode = code;
            // Fake data
            var selectedNumber = parseInt(selectedCode.substring(selectedCode.length - 1, selectedCode.length));
            var item: service.Item = {code: selectedCode, name: 'Aggregate item ' + selectedNumber, 
                category: self.category, paymentType: self.paymentType, showNameZeroValue: true, 
                showValueZeroValue: true, subItems: [
                            {code: 'MI' + selectedNumber, name: 'sub item ' + selectedNumber},
                            {code: 'MI' + selectedNumber + 2, name: 'sub item ' + selectedNumber + 2},
                        ]};
            dfd.resolve(item);
            return dfd.promise();
        }
        
        
        switchToCreateMode() {
            
        }
        
        save() {
            
        }
        
        remove() {
            
        }
        
        close() {
            
        }
    }
    
    /**
     * Aggregate detail model.
     */
    export class AggregateItemDetail {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        paymentType: string;
        category: string;
        showNameZeroValue: KnockoutObservable<boolean>;
        showValueZeroValue: KnockoutObservable<boolean>;
        masterItems: KnockoutObservableArray<service.Item>;
        masterItemsSelected: KnockoutObservableArray<string>;
        subItems: KnockoutObservableArray<service.SubItem>;
        subItemsSelected: KnockoutObservableArray<string>;
        switchs: KnockoutObservableArray<any>;
        showNameZeroCode: KnockoutObservable<string>;
        showValueZeroCode: KnockoutObservable<string>;
        swapListColumns: KnockoutObservableArray<any>;
        
        constructor(paymentType: string, category: string, 
                masterItems: service.Item[], item?: service.Item) {
            this.code = item == undefined ? ko.observable(null) : ko.observable(item.code);
            this.name = item == undefined ? ko.observable(null) : ko.observable(item.name);
            this.paymentType = paymentType;
            this.category = category;
            this.showNameZeroValue = item == undefined ? ko.observable(true) 
                : ko.observable(item.showNameZeroValue);
            this.showValueZeroValue = item == undefined ? ko.observable(true)
                : ko.observable(item.showValueZeroValue);
            this.subItems = item == undefined ? ko.observableArray([]) : ko.observableArray(item.subItems)
            this.switchs = ko.observableArray([
                    { code: '0', name: '表示する' },
                    { code: '1', name: '表示しない' }
                ]);
            this.showNameZeroCode = ko.observable(this.showNameZeroValue() ? '0' : '1');
            this.showValueZeroCode = ko.observable(this.showValueZeroValue() ? '0' : '1');
            this.swapListColumns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 160 }
            ]);
            var self = this;
            
            // Computed show values variable.
            self.showNameZeroValue = ko.computed(function() {
                return self.showNameZeroCode() == '0';
            });
            self.showValueZeroValue = ko.computed(function() {
                return self.showValueZeroCode() == '0';
            })
            
            // exclude items contain in sub items.
            var subItemCodes = self.subItems().map(item => item.code);
            var masterItemsExcluded = masterItems.filter((item) => subItemCodes.indexOf(item.code) == -1);
            self.masterItems = ko.observableArray(masterItemsExcluded);
        }
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