var qmm005;
(function (qmm005) {
    var e;
    (function (e) {
        var ViewModel = (function () {
            function ViewModel() {
                this.start();
            }
            ViewModel.prototype.start = function () {
                var self = this;
                self.sel001 = ko.observable(1);
                self.sel001Data = ko.observableArray([
                    new qmm005.common.SelectItem({ index: 1, label: '1' }),
                    new qmm005.common.SelectItem({ index: 2, label: '2' }),
                    new qmm005.common.SelectItem({ index: 3, label: '3' }),
                    new qmm005.common.SelectItem({ index: 4, label: '4' }),
                    new qmm005.common.SelectItem({ index: 5, label: '5' }),
                    new qmm005.common.SelectItem({ index: 6, label: '6' }),
                    new qmm005.common.SelectItem({ index: 7, label: '7' }),
                    new qmm005.common.SelectItem({ index: 8, label: '8' }),
                    new qmm005.common.SelectItem({ index: 9, label: '9' }),
                    new qmm005.common.SelectItem({ index: 10, label: '10' }),
                    new qmm005.common.SelectItem({ index: 11, label: '11' }),
                    new qmm005.common.SelectItem({ index: 12, label: '12' })
                ]);
                self.sel002 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '支払日', helper: '処理月の末日' });
                self.sel003 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '対象者抽出基準日', helper: '処理月の当月15日' });
                self.sel004 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '社会保険徴収月', helper: '処理月当月' });
                self.sel005 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '明細書印字年月', helper: '常に活性' });
                self.sel006 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '社会保険基準日', helper: '処理月の前月末日' });
                self.sel007 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '雇用保険基準日', helper: '処理月の直前の４月１日' });
                self.sel008 = new qmm005.common.CheckBoxItem({ enable: false, checked: false, text: '所得税基準日', helper: '処理月の習前の１月１日' });
                self.sel009 = new qmm005.common.CheckBoxItem({ enable: true, checked: false, text: '経理締め日', helper: '処理月の前月' });
            };
            ViewModel.prototype.saveData = function () {
                var self = this;
                self.sel008.enable(true);
            };
            ViewModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            return ViewModel;
        }());
        e.ViewModel = ViewModel;
    })(e = qmm005.e || (qmm005.e = {}));
})(qmm005 || (qmm005 = {}));
