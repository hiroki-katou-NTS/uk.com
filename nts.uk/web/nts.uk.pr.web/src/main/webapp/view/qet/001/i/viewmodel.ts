module qet001.i.viewmodel {
    import NtsGridColumn = nts.uk.ui.NtsGridListColumn;
    import WageLedgerOutputSetting = qet001.a.service.model.WageLedgerOutputSetting;
    import WageledgerCategorySetting = qet001.a.service.model.WageledgerCategorySetting;
    import WageLedgerSettingItem = qet001.a.service.model.WageLedgerSettingItem;
    
    export class ScreenModel {
        
        
        constructor() {
        }
        
        start(): JQueryPromise<void>{
            var dfd = $.Deferred<void>();
            dfd.resolve();
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
            var masterItemInCate = masterItems.filter(item => item.paymentType == paymentType 
                && item.category == categoryName);
            this.aggregateItemDetail = ko.observable(new AggregateItemDetail(paymentType, categoryName, masterItemInCate));
        }
        
        loadAggregateItemByCategory() : JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            // Fake data.
            var self = this;
//            var 
            for (var i = 0; i < 10; i++) {
                
            }
            return dfd.promise();
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
        
        constructor(paymentType: string, category: string, 
                masterItems: service.Item[], item?: service.Item) {
            this.code = item == undefined ? ko.observable(null) : ko.observable(item.code);
            this.name = item == undefined ? ko.observable(null) : ko.observable(item.name);
            this.paymentType = paymentType;
            this.category = category;
            this.showNameZeroValue = item == undefined ? ko.observable(false) 
                : ko.observable(item.showNameZeroValue);
            this.showValueZeroValue = item == undefined ? ko.observable(false)
                : ko.observable(item.showValueZeroValue);
            this.masterItems = ko.observableArray(masterItems);
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