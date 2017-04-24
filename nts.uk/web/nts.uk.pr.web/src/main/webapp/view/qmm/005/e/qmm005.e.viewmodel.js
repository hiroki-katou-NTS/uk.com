/// <reference path="../qmm005.ts"/>
var qmm005;
(function (qmm005) {
    var e;
    (function (e) {
        class ViewModel {
            constructor() {
                this.index = ko.observable(0);
                this.lbl003 = ko.observable('');
                this.lbl005 = ko.observable(0);
                this.lbl006 = ko.observable('');
                this.sel001 = ko.observable(0);
                this.sel001Data = ko.observableArray([]);
                this.sel002 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '支払日', helper: '処理月の末日' });
                this.sel003 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '対象者抽出基準日', helper: '処理月の当月15日' });
                this.sel004 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '社会保険徴収月', helper: '処理月当月' });
                this.sel005 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '明細書印字年月', helper: '常に活性' });
                this.sel006 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '社会保険基準日', helper: '処理月の前月末日' });
                this.sel007 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '雇用保険基準日', helper: '処理月の直前の４月１日' });
                this.sel008 = new qmm005.common.CheckBoxItem({ enable: false, checked: false, text: '所得税基準日', helper: '処理月の習前の１月１日' });
                this.sel009 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '経理締め日', helper: '処理月の前月' });
                let self = this;
                self.lbl005.subscribe(function (v) {
                    if (v) {
                        self.lbl006("(" + v["yearInJapanEmpire"]() + ")");
                    }
                    else {
                        self.lbl006("");
                    }
                });
                self.start();
            }
            start() {
                let self = this;
                let dataRow = nts.uk.ui.windows.getShared('dataRow');
                let viewModelB = nts.uk.ui.windows.getShared('viewModelB');
                // Month           
                self.sel001(dataRow.sel002());
                let sel001Data = [];
                for (let i = 1; i <= 12; i++) {
                    sel001Data.push(new qmm005.common.SelectItem({ index: i, label: i.toString() }));
                }
                self.sel001Data(sel001Data);
                self.lbl003(dataRow.index() + "." + dataRow.label());
                // Year
                self.lbl005(viewModelB.inp001());
                e.services.getData(dataRow.index()).done(function (resp) {
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
        e.ViewModel = ViewModel;
    })(e = qmm005.e || (qmm005.e = {}));
})(qmm005 || (qmm005 = {}));
