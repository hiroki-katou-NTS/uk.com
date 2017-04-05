var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var IScreen = (function () {
            function IScreen(root) {
                var self = this;
                self.baseYm = ko.observable(root.viewModel017b().startYearMonth());
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
                        qmm017.service.getListWageTable(baseYm).done(function (lstWageTbl) {
                            _.forEach(lstWageTbl, function (wageTbl) {
                                self.listBoxItems().itemList.push({ code: wageTbl.code, name: wageTbl.name });
                            });
                        });
                    }
                });
            }
            return IScreen;
        }());
        qmm017.IScreen = IScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
