module qmm019.e.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        selectLayout: KnockoutObservable<service.model.LayoutMasterDto>;
        selectedLayoutAtr: KnockoutObservable<number>;
        selectLayoutCode: KnockoutObservable<string>;
        selectLayoutName: KnockoutObservable<string>;
        selectLayoutStartYm: KnockoutObservable<string>;
        selectLayoutEndYm: KnockoutObservable<string>;
        layoutStartYm: KnockoutObservable<string>;
        layoutYm: KnockoutObservable<LayoutYearMonth>;
        timeEditorOption: KnockoutObservable<any>;
        historyId: KnockoutObservable<string>;
        enableYm: KnockoutObservable<boolean>;
        //---radio        
        isRadioCheck: KnockoutObservable<number>;
        itemsRadio: KnockoutObservableArray<any>;
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.selectedLayoutAtr = ko.observable(null);
            self.selectLayoutCode = ko.observable(null);
            self.selectLayoutName = ko.observable(null);
            self.selectLayoutStartYm = ko.observable(null);
            self.selectLayoutEndYm = ko.observable(null);
            self.selectLayout = ko.observable(null);
            self.layoutYm = ko.observable(null);
            self.layoutStartYm = ko.observable(null);
            self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
            self.historyId = ko.observable(null);
            self.enableYm = ko.observable(true);
            //---radio
            self.itemsRadio = ko.observableArray([
                { value: 1, text: '履歴を削除する' },
                { value: 2, text: '履歴を修正する' }
            ]);
            self.isRadioCheck = ko.observable(2);

        }

        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            var layoutCode = nts.uk.ui.windows.getShared('stmtCode');
            var layoutYm = new LayoutYearMonth(nts.uk.ui.windows.getShared('startYm'), nts.uk.ui.windows.getShared('endYm'));
            self.historyId(nts.uk.ui.windows.getShared('historyId'));
            self.layoutYm(layoutYm);
            self.layoutStartYm(self.layoutYm().startYm);
            service.getLayout(layoutCode, self.historyId()).done(function(layoutReturn: service.model.LayoutMasterDto) {
                let layout  = new service.model.LayoutMasterDto(layoutReturn.companyCode, layoutReturn.stmtCode, layoutReturn.stmtName, parseInt(self.layoutYm().startYm.replace('/', '')), parseInt(self.layoutYm().endYM.replace('/', '')), self.historyId());
                self.selectLayout(layout);
                self.startDiaglog();
                dfd.resolve();
            }).fail(function(res) {
                alert(res);
            })
            //checkbox change
            self.isRadioCheck.subscribe(function(newValue) {
                if (newValue === 2) {
                    self.enableYm(true);
                } else {
                    self.enableYm(false);
                }
            })
            // Return.
            return dfd.promise();
        }

        startDiaglog(): any {
            var self = this;
            var layout = self.selectLayout();
            var code = layout.stmtCode;
            self.selectLayoutCode(code);
            self.selectLayoutName(layout.stmtName);
            self.selectLayoutStartYm(self.layoutYm().startYm);
            self.selectLayoutEndYm(self.layoutYm().endYM);
        }

        layoutProcess(): any {
            var self = this;
            //履歴の編集-削除処理
            if (self.isRadioCheck() === 1) {
                self.dataDelete();
            } else {
                self.dataUpdate();
            }
        }

        dataDelete(): any {
            var self = this;
            service.deleteLayout(self.selectLayout()).done(function() {
                //alert("履歴を削除しました。");
                nts.uk.ui.windows.close();
            }).fail(function(res) {
                alert(res);
            })
        }

        dataUpdate(): any {
            var self = this;
            var layoutInfor = self.selectLayout();
            //check YM
            var inputYm = $("#INP_001").val();
            if (!nts.uk.time.parseYearMonth(inputYm).success) {
                alert(nts.uk.time.parseYearMonth(inputYm).msg);
                return false;
            }
            layoutInfor.startYmOriginal = +self.layoutYm().startYm.replace('/', '');
            layoutInfor.startYm =  +self.selectLayoutStartYm().replace('/', '');
            //直前の[明細書マスタ]の開始年月　>　入力した開始年月　>=　終了年月　の場合
            if (layoutInfor.startYmOriginal > layoutInfor.startYm
                || layoutInfor.startYm > +self.selectLayoutEndYm().replace('/', '')) {
                alert("履歴の期間が重複しています。");
                return false;
            }
            else if (layoutInfor.startYmOriginal == layoutInfor.startYm) {
                //alert("履歴を修正しました。");
                nts.uk.ui.windows.close();
                return false;
            } else {
                service.updateLayout(layoutInfor).done(function() {
                    //alert("履歴を修正しました。");
                    nts.uk.ui.windows.close();
                }).fail(function(res) {
                    alert(res.message);
                })
            }
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
    class LayoutYearMonth {
        startYm: string;
        endYM: string;
        constructor(
            startYm: string,
            endYM: string
        ) {
            this.startYm = startYm;
            this.endYM = endYM;
        }
    }
}