module qet001.a.viewmodel {
    export class ScreenModel {
        targetYear: KnockoutObservable<number>;
        isAggreatePreliminaryMonth: KnockoutObservable<boolean>;
        layoutSelected: KnockoutObservable<LayoutOutput>;
        isPageBreakIndicator: KnockoutObservable<boolean>;
        outputTypeSelected : KnockoutObservable<OutputType>;
        outputSettings: KnockoutObservableArray<service.model.WageLedgerOutputSetting>;
        outputSettingSelectedCode: KnockoutObservable<string>;
        
        constructor() {
            this.targetYear = ko.observable(2016);
            this.isAggreatePreliminaryMonth = ko.observable(true);
            this.layoutSelected = ko.observable(LayoutOutput.WAGE_LEDGER);
            this.isPageBreakIndicator = ko.observable(false);
            this.outputTypeSelected = ko.observable(OutputType.DETAIL_ITEM);
            this.outputSettings = ko.observableArray([]);
            this.outputSettingSelectedCode = ko.observable('');
        }
        
        public start(): JQueryPromise<any>{
            var dfd = $.Deferred<any>();
            var self = this;
            $.when(self.loadAllOutputSetting()).done(function() {
                dfd.resolve();
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
                self.outputSettings(data);
                dfd.resolve();
            }).fail(function(res) {
                nts.uk.ui.dialog.alert(res.message);
                dfd.reject();
            })
            return dfd.promise();
        }
        
        /**
         * Go To Output setting dialog.
         */
        public goToOutputSetting() {
            nts.uk.ui.windows.setShared('selectedCode', this.outputSettingSelectedCode(), true);
            nts.uk.ui.windows.setShared('outputSettings', this.outputSettings(), true);
            nts.uk.ui.windows.sub.modal("/view/qet/001/b/index.xhtml", { title: "出カ項目の設定" });
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