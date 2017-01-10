module qet001.a.viewmodel {
    export class ScreenModel {
        targetYear: KnockoutObservable<number>;
        isAggreatePreliminaryMonth: KnockoutObservable<boolean>;
        layoutSelected: KnockoutObservable<LayoutOutput>;
        isPageBreakIndicator: KnockoutObservable<boolean>;
        outputTypeSelected : KnockoutObservable<OutputType>;
        outputSettings: KnockoutObservableArray<OutputSetting>;
        outputSettingSelectedCode: KnockoutObservable<string>;
        
        constructor() {
            this.targetYear = ko.observable(2016);
            this.isAggreatePreliminaryMonth = ko.observable(true);
            this.layoutSelected = ko.observable(LayoutOutput.WAGE_LEDGER);
            this.isPageBreakIndicator = ko.observable(false);
            this.outputTypeSelected = ko.observable(OutputType.DETAIL_ITEM);
            this.outputSettings = ko.observableArray([new OutputSetting('SETTING1', 'setting 1'),
                new OutputSetting('SETTING2', 'setting 2')]);
            this.outputSettingSelectedCode = ko.observable('SETTING2');
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
        
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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