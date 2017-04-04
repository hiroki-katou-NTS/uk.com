var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var RScreen = (function () {
            function RScreen(root) {
                var self = this;
                self.startYm = ko.observable(root.viewModel017b().startYearMonth());
                root.viewModel017b().startYearMonth.subscribe(function (yMChange) {
                    if (yMChange.indexOf('/') !== -1) {
                        self.startYm(yMChange.replace('/', ''));
                    }
                    else {
                        self.startYm(yMChange);
                    }
                });
                var rList001 = [
                    { code: '1', name: '会社単価項目（会社単価＠）' },
                    { code: '2', name: '個人単価項目（個人単価＠）' }
                ];
                self.listBoxItemType = ko.observable(new qmm017.ListBox(rList001));
                self.listBoxItems = ko.observable(new qmm017.ListBox([]));
                self.listBoxItemType().selectedCode.subscribe(function (codeChange) {
                    if (codeChange === '1') {
                        self.listBoxItems().itemList([]);
                        qmm017.service.getListCompanyUnitPrice(self.startYm()).done(function (lstCompanyUnitPrice) {
                            _.forEach(lstCompanyUnitPrice, function (companyUnitPrice) {
                                self.listBoxItems().itemList.push({ code: companyUnitPrice.unitPriceCode, name: companyUnitPrice.unitPriceName });
                            });
                        });
                    }
                    else if (codeChange === '2') {
                        self.listBoxItems().itemList([]);
                        qmm017.service.getListPersonalUnitPrice().done(function (lstPersonalUnitPrice) {
                            _.forEach(lstPersonalUnitPrice, function (personalUnitPrice) {
                                self.listBoxItems().itemList.push({ code: personalUnitPrice.personalUnitPriceCode, name: personalUnitPrice.personalUnitPriceName });
                            });
                        });
                    }
                });
            }
            return RScreen;
        }());
        qmm017.RScreen = RScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
