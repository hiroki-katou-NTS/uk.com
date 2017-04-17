/// <reference path="../qmm005.ts"/>
module qmm005.e {
    export class ViewModel {
        index: KnockoutObservable<number> = ko.observable(0);
        lbl003: KnockoutObservable<string> = ko.observable('');
        lbl005: KnockoutObservable<number> = ko.observable(0);
        lbl006: KnockoutObservable<string> = ko.observable('');
        
        sel001: KnockoutObservable<number> = ko.observable(0);
        sel001Data: KnockoutObservableArray<common.SelectItem> = ko.observableArray([]);

        sel002: common.CheckBoxItem = new common.CheckBoxItem({ enable: true, checked: false, text: '支払日', helper: '処理月の末日' });
        sel003: common.CheckBoxItem = new common.CheckBoxItem({ enable: true, checked: false, text: '対象者抽出基準日', helper: '処理月の当月15日' });
        sel004: common.CheckBoxItem = new common.CheckBoxItem({ enable: true, checked: false, text: '社会保険徴収月', helper: '処理月当月' });
        sel005: common.CheckBoxItem = new common.CheckBoxItem({ enable: true, checked: false, text: '明細書印字年月', helper: '常に活性' });
        sel006: common.CheckBoxItem = new common.CheckBoxItem({ enable: true, checked: false, text: '社会保険基準日', helper: '処理月の前月末日' });
        sel007: common.CheckBoxItem = new common.CheckBoxItem({ enable: true, checked: false, text: '雇用保険基準日', helper: '処理月の直前の４月１日' });
        sel008: common.CheckBoxItem = new common.CheckBoxItem({ enable: false, checked: false, text: '所得税基準日', helper: '処理月の習前の１月１日' });
        sel009: common.CheckBoxItem = new common.CheckBoxItem({ enable: true, checked: false, text: '経理締め日', helper: '処理月の前月' });

        constructor() {
            let self = this;
            
            self.lbl005.subscribe(function(v) {
                if (v) {
                    self.lbl006("(" + v["yearInJapanEmpire"]() + ")");
                } else {
                    self.lbl006("");
                }
            });
            self.start();
        }

        start() {
            let self = this;
            
            let sel001Data: Array<common.SelectItem> = [];
            for (let i = 1; i <= 12; i++) {
                sel001Data.push(new common.SelectItem({ index: i, label: i.toString() }));
            }
            self.sel001Data(sel001Data);
            
            let dataRow = nts.uk.ui.windows.getShared('dataRow');
            self.lbl003(dataRow.index() + "." + dataRow.label());
            
            self.lbl005(2017);
            
            services.getData(dataRow.index()).done(function(resp) {
               debugger; 
            });
        }

        saveData() {
            let self = this;
            debugger;
            self.sel008.enable(false);
        }

        closeDialog() { nts.uk.ui.windows.close(); }
    }
}