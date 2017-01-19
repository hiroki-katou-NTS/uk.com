module qet001.b.viewmodel {
    import NtsGridColumn = nts.uk.ui.NtsGridListColumn;
    import NtsTabModel = nts.uk.ui.NtsTabPanelModel;
    
    export class ScreenModel {
        outputSettings: KnockoutObservable<OutputSettings>;
        outputSettingDetail: KnockoutObservable<OutputSettingDetail>;
        reportItems: KnockoutObservableArray<ReportItem>;
        reportItemColumns: KnockoutObservableArray<NtsGridColumn>;
        reportItemSelected: KnockoutObservable<string>;
        
        constructor() {
            this.outputSettings = ko.observable(new OutputSettings());
            this.outputSettingDetail= ko.observable(new OutputSettingDetail());
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
            
            var self = this;
//            self.outputSettingDetail.subscribe((newVal: OutputSettingDetail) => {
//                //newVal.
//            });
            self.outputSettings().outputSettingSelectedCode.subscribe((newVal: string) => {
                if (newVal== undefined || newVal == null) {
                    return;
                }
                // load detail output setting.
                self.loadOutputSettingDetail(newVal);
            })
        }
        
        public start(): JQueryPromise<any>{
            var dfd = $.Deferred<any>();
            var self = this;
            // Load all output setting.
            this.loadAllOutputSetting().done(() => {
                // If not found output setting.
                if (self.outputSettings().outputSettingList().length == 0) {
                    dfd.resolve();
                    return;
                }
                // Load output setting detail.
                self.loadOutputSettingDetail(self.outputSettings().outputSettingList()[0].code).done(() => {
                    dfd.resolve();
                });
            })
            return dfd.promise();
        }
        
        /**
         * Load all output setting.
         */
        public loadAllOutputSetting(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var self = this;
            service.findOutputSettings().done(function(data: service.model.WageLedgerOutputSetting[]) {
                self.outputSettings().outputSettingList(data);
                dfd.resolve();
            }).fail(function(res) {
                //TODO: wait to show error message.
                dfd.reject();
            })
            return dfd.promise();
        }
        
        /**
         * Load detail output setting.
         */
        public loadOutputSettingDetail(selectedCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var self = this;
            service.findOutputSettingDetail(selectedCode).done(function(data: service.model.WageLedgerOutputSetting){
                self.outputSettingDetail(new OutputSettingDetail(data));
                dfd.resolve();
            }).fail(function(res) {
                //TODO: wait to show error message.
                dfd.reject();
            })
            return dfd.promise();
        }
    }
    
    /**
     * 登録済みの出力項目設定
     */
    export class OutputSettings {
        searchText: KnockoutObservable<string>;
        outputSettingList: KnockoutObservableArray<service.model.WageLedgerOutputSetting>;
        outputSettingSelectedCode: KnockoutObservable<string>;
        outputSettingColumns: KnockoutObservableArray<NtsGridColumn>;
        
        constructor() {
            this.searchText = ko.observable('');
            this.outputSettingList = ko.observableArray([]);
            this.outputSettingSelectedCode = ko.observable('');
            this.outputSettingColumns = ko.observableArray([
                {headerText: 'コード', prop: 'code', width: 90}, 
                {headerText: '名称', prop: 'name',  width: 100}]);
        }
    }
    
    export class OutputSettingDetail{
        settingCode: KnockoutObservable<string>;
        settingName: KnockoutObservable<string>;
        isPrintOnePageEachPer: KnockoutObservable<boolean>;
        categorySettingTabs: KnockoutObservableArray<NtsTabModel>;
        selectedCategory: KnockoutObservable<string>;
        
        constructor(outputSetting?: service.model.WageLedgerOutputSetting) {
            this.settingCode = ko.observable(outputSetting != undefined ? outputSetting.code : '');
            this.settingName = ko.observable(outputSetting != undefined ? outputSetting.name : '');
            this.isPrintOnePageEachPer = ko.observable(outputSetting != undefined ? outputSetting.onceSheetPerPerson : true);
            this.categorySettingTabs = ko.observableArray([
                { id: 'tab-salary-payment', title: '給与支給', content: '#salary-payment', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-salary-deduction', title: '給与控除', content: '#salary-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-salary-attendance', title: '給与勤怠', content: '#salary-attendance', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-bonus-payment', title: '賞与支給', content: '#bonus-payment', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-bonus-deduction', title: '賞与控除', content: '#bonus-deduction', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-bonus-attendance', title: '賞与勤怠', content: '#bonus-attendance', enable: ko.observable(true), visible: ko.observable(true) },
            ]);
            this.selectedCategory = ko.observable('tab-salary-payment');
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