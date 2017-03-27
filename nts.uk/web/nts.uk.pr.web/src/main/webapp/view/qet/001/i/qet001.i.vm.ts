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
            $("#sidebar-area > div > ul > li").on('click', function() {
                var index = $("#sidebar-area > div > ul > li").index(this);
                $("#sidebar").ntsSideBar("active", index);
                self.selectedTab(index);
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
        
        
        /**
         * After rended template.
         */
        public afterRender() {
            // set width when swap list is rended.
            $('.master-table-label').width($('#swap-list-gridArea1').width());
            $('.sub-table-label').width($('#swap-list-gridArea2').width())
        }
    }
    
    export class AggregateCategory {
        itemList: KnockoutObservableArray<service.Item>;
        category: string;
        paymentType: string;
        itemListColumns: KnockoutObservableArray<any>;
        aggregateItemSelectedCode: KnockoutObservable<string>;
        aggregateItemDetail: KnockoutObservable<AggregateItemDetail>;
        dirty: nts.uk.ui.DirtyChecker;
        
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
            self.dirty = new nts.uk.ui.DirtyChecker(self.aggregateItemDetail);
            
            // When selected aggregate item => load detail.
            self.aggregateItemSelectedCode.subscribe((code) => {
                if (code == undefined || code == null || code == '') {
                    self.aggregateItemDetail(new AggregateItemDetail(paymentType,
                        categoryName, masterItemInCate));
                    self.setStyle();
                    self.dirty.reset()
                    return;
                }
                self.loadDetailAggregateItem(self.category, self.paymentType, code).done(function(res: service.Item) {
                    self.aggregateItemDetail(new AggregateItemDetail(paymentType,
                        categoryName, masterItemInCate, res));
                    self.dirty.reset();
                    self.setStyle();
                });
            });
            
        }
        
        /**
         * Load Aggregate items by category.
         */
        public loadAggregateItemByCategory() : JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            // Fake data.
            var self = this;
            service.findAggregateItemsByCategory(self.category, self.paymentType).done(function(res: service.Item[]) {
                self.itemList(res);
                self.aggregateItemSelectedCode(res.length > 0 ? res[0].code : null);
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }
        
        /**
         * Load detail aggregate item.
         */
        public loadDetailAggregateItem(category: string, paymentType: string, code: string): JQueryPromise<service.Item> {
            var dfd = $.Deferred<service.Item>();
            service.findAggregateItemDetail(category, paymentType, code).done((data: service.Item) => {
                dfd.resolve(data);
            }).fail((res) => {
                nts.uk.ui.dialog.alert(res.message);
                dfd.reject();
            })
            return dfd.promise();
        }
        
        /**
         * Switch to create mode.
         */
        public switchToCreateMode() {
            // Dirty check.
            var self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm('変更された内容が登録されていません。\r\nよろしいですか。').ifYes(function() {
                    self.aggregateItemSelectedCode(null);
                });
            }
        }
        
        public save() {
            var self = this;
            // clear error.
            $('#code-input').ntsError('clear');
            $('#name-input').ntsError('clear');
            // Validate.
            var hasError = false;
            if (self.aggregateItemDetail().code() == '') {
                $('#code-input').ntsError('set', 'コードが入力されていません。');
                hasError = true;
            }
            if (self.aggregateItemDetail().name() == '') {
                $('#name-input').ntsError('set', '名称が入力されていません。');
                hasError = true;
            }
            if(hasError) {
                return;
            }
            
            // save.
            service.save(self.aggregateItemDetail()).done(function() {
                // TODO: Show message save success.
                nts.uk.ui.dialog.alert('Save success!');
                self.loadAggregateItemByCategory();
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
            });
        }
        
        public remove() {
            var self = this;
            if (self.aggregateItemSelectedCode() == null) {
                return;
            }
            // save.
            service.remove(self.category, self.paymentType, self.aggregateItemSelectedCode()).done(function() {
                // Find item selected.
                var itemSelected = self.itemList().filter(item => item.code == self.aggregateItemSelectedCode())[0];
                var indexSelected = self.itemList().indexOf(itemSelected);
                // Remove item selected in list.
                self.itemList.remove(itemSelected);
                
                // If list is empty -> new mode.
                if (self.itemList.length == 0) {
                    self.aggregateItemSelectedCode(null);
                    return;
                }
                
                // Select same row with item selected.
                if (self.itemList()[indexSelected]) {
                    self.aggregateItemSelectedCode(self.itemList()[indexSelected].code);
                    return;
                }
                
                // Select next higher row.
                self.aggregateItemSelectedCode(self.itemList()[indexSelected - 1].code)
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
            });
        }
        
        /**
         * Close dialog.
         */
        public close() {
            // Dirty check.
            var self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm('変更された内容が登録されていません。\r\nよろしいですか。').ifYes(function() {
                    nts.uk.ui.windows.close();
                });
            }
        }
        
        /**
         * Set style when re-rending list.
         */
        public setStyle() {
            // set width when swap list is rended.
            $('.master-table-label').attr('style','width: ' + $('#swap-list-gridArea1').width() + 'px');
            $('.sub-table-label').attr('style','width: ' + $('#swap-list-gridArea2').width() + 'px');
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
        createMode: KnockoutObservable<boolean>;
        
        constructor(paymentType: string, category: string, 
                masterItems: service.Item[], item?: service.Item) {
            this.code = item == undefined ? ko.observable('') : ko.observable(item.code);
            this.name = item == undefined ? ko.observable('') : ko.observable(item.name);
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
            this.createMode = ko.observable(item == undefined);
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