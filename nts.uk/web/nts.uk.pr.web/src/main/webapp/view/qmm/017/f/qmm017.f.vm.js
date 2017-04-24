var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        class FScreen {
            constructor() {
                var self = this;
                var hList001 = [
                    { code: '1', name: '全て' },
                ];
                self.listBoxItemType = ko.observable(new qmm017.ListBox(hList001));
                self.listBoxItems = ko.observable(new qmm017.ListBox([]));
                self.listBoxItemType().selectedCode.subscribe(function (codeChange) {
                    if (codeChange === '1') {
                        qmm017.service.getListSystemVariable().done(function (lstItem) {
                            self.listBoxItems().itemList([]);
                            _.forEach(lstItem, function (item) {
                                self.listBoxItems().itemList.push({ code: item.systemVariableCode, name: item.systemVariableName });
                            });
                        });
                    }
                });
            }
        }
        qmm017.FScreen = FScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
