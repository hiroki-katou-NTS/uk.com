var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var l;
                    (function (l) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.easyFormulaName = ko.observable('');
                                    self.comboBoxFormulaType = ko.observable(new ComboBox([
                                        new ItemModel(0, '計算式１'),
                                        new ItemModel(1, '計算式２'),
                                        new ItemModel(2, '計算式3')
                                    ]));
                                    self.comboBoxBaseAmount = ko.observable(new ComboBox([
                                        new ItemModel(0, '固定値'),
                                        new ItemModel(2, '個人単価'),
                                        new ItemModel(3, '支給項目'),
                                        new ItemModel(4, '控除項目')
                                    ]));
                                    self.comboBoxFormulaType().selectedCode.subscribe(function (codeChange) {
                                        if (codeChange === 2) {
                                            self.comboBoxBaseAmount().itemList([
                                                new ItemModel(0, '固定値'),
                                                new ItemModel(1, '会社単価'),
                                                new ItemModel(3, '支給項目'),
                                                new ItemModel(4, '控除項目')
                                            ]);
                                        }
                                        else {
                                            self.comboBoxBaseAmount().itemList([
                                                new ItemModel(0, '固定値'),
                                                new ItemModel(2, '個人単価'),
                                                new ItemModel(3, '支給項目'),
                                                new ItemModel(4, '控除項目')
                                            ]);
                                        }
                                    });
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ComboBox = (function () {
                                function ComboBox(itemList) {
                                    var self = this;
                                    self.itemList = ko.observableArray(itemList);
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable(0);
                                    self.selectedCode = ko.observable(null);
                                }
                                return ComboBox;
                            }());
                            viewmodel.ComboBox = ComboBox;
                            var ItemModel = (function () {
                                function ItemModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return ItemModel;
                            }());
                        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
                    })(l = qmm017.l || (qmm017.l = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
