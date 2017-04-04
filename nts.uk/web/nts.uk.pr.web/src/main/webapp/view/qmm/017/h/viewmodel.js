var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var HScreen = (function () {
            function HScreen(root) {
                var self = this;
                self.formulaCode = ko.observable(root.viewModel017b().formulaCode());
                self.baseYm = ko.observable(root.viewModel017b().startYearMonth());
                root.viewModel017b().formulaCode.subscribe(function (codeChange) {
                    self.formulaCode(codeChange);
                });
                root.viewModel017b().startYearMonth.subscribe(function (yM) {
                    self.baseYm(yM);
                });
                var hList001 = [
                    { code: '1', name: '全て' },
                ];
                self.listBoxItemType = ko.observable(new qmm017.ListBox(hList001));
                self.listBoxItems = ko.observable(new qmm017.ListBox([]));
                self.listBoxItemType().selectedCode.subscribe(function (codeChange) {
                    if (codeChange === '1') {
                        self.listBoxItems().itemList([]);
                        var baseYm = 0;
                        if (self.baseYm().indexOf('/') !== -1) {
                            baseYm = self.baseYm().replace('/', '');
                        }
                        else {
                            baseYm = self.baseYm();
                        }
                        qmm017.service.findOtherFormulas(self.formulaCode(), baseYm).done(function (lstFormula) {
                            _.forEach(lstFormula, function (formula) {
                                self.listBoxItems().itemList.push({ code: formula.formulaCode, name: formula.formulaName });
                            });
                        });
                    }
                });
            }
            return HScreen;
        }());
        qmm017.HScreen = HScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
