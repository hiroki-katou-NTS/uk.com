module qet001.b.viewmodel {
    import NtsGridColumn = nts.uk.ui.NtsGridListColumn;
    import NtsTabModel = nts.uk.ui.NtsTabPanelModel;
    
    export class ScreenModel {
        searchText: KnockoutObservable<string>;
        outputSettingList: KnockoutObservable<OutputSetting>;
        outputSettingSelectedCode: KnockoutObservable<string>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        isPrintOnePageEachPer: KnockoutObservable<boolean>;
        aggregateCategories: KnockoutObservableArray<NtsTabModel>;
        selectedCategory: KnockoutObservable<string>;
        outputSettingColumns: KnockoutObservableArray<NtsGridColumn>;
        reportItems: KnockoutObservableArray<ReportItem>;
        reportItemColumns: KnockoutObservableArray<NtsGridColumn>;
        reportItemSelected: KnockoutObservable<string>;
        
        constructor() {
            this.searchText = ko.observable('');
            this.outputSettingList = ko.observableArray([]);
            this.outputSettingList.push(new OutputSetting('SET1', 'setting 1'));
            this.outputSettingList.push(new OutputSetting('SET2', 'setting 2'));
            this.outputSettingSelectedCode = ko.observable('');
            this.outputSettingColumns = ko.observableArray([
                {headerText: 'コード', prop: 'code', width: 90}, 
                {headerText: '名称', prop: 'name',  width: 100}]);
            this.code = ko.observable('0001');
            this.name = ko.observable('Name 1');
            this.isPrintOnePageEachPer = ko.observable(true);
            this.aggregateCategories = ko.observableArray([
                { id: 'tab-salary-payment', title: '給与支給', content: '#salary-payment', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-salary-deduction', title: '給与控除', content: '#salary-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-salary-attendance', title: '給与勤怠', content: '#salary-attendance', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-bonus-payment', title: '賞与支給', content: '#bonus-payment', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-bonus-deduction', title: '賞与控除', content: '#bonus-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-bonus-attendance', title: '賞与勤怠', content: '#bonus-attendance', enable: ko.observable(true), visible: ko.observable(true) },
            ]);
            this.selectedCategory = ko.observable('tab-salary-payment');
            this.reportItems = ko.observableArray([
                    new ReportItem('CAT1', true, 'CODE1', 'Name 1'),
                    new ReportItem('CAT2', false, 'CODE2', 'Name 2'),
                    new ReportItem('CAT4', true, 'CODE5', 'Name 5'),
                ]);
            this.reportItemColumns = ko.observableArray([
                    {headerText: '区分', prop: 'categoryName', width: 50},
                    {headerText: '集約', prop: 'isAggregate', width: 30},
                    {headerText: 'コード', prop: 'itemCode', width: 100},
                    {headerText: '名称', prop: 'itemName', width: 100},
                ]);
            this.reportItemSelected = ko.observable(null);
        }
        
        public start(): JQueryPromise<any>{
            var dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
        
    }
    
    /**
     * 登録済みの出力項目設定
     */
    export class OutputSetting {
        code: string;
        name: string;
        test: string
        
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    
    export class ReportItem {
        categoryName: string;
        isAggregate: boolean;
        itemCode: string;
        itemName: string;
        
        constructor(categoryName: string, isAggregate: boolean, itemCode: string, itemName: string) {
            this.categoryName = categoryName;
            this.isAggregate = isAggregate;
            this.itemCode = itemCode;
            this.itemName = itemName;
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
}