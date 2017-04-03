var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var ListBoxD = (function () {
            function ListBoxD(data) {
                var self = this;
                self.itemList = ko.observableArray(data);
                self.itemName = ko.observable('');
                self.currentCode = ko.observable(3);
                self.selectedCode = ko.observable('');
                self.isEnable = ko.observable(true);
            }
            return ListBoxD;
        }());
        qmm017.ListBoxD = ListBoxD;
        var DScreen = (function () {
            function DScreen() {
                var self = this;
                var dList001 = [
                    { code: '1', name: '支給項目（支給＠）' },
                    { code: '2', name: '控除項目（控除＠）' },
                    { code: '3', name: '勤怠項目（勤怠＠）' }
                ];
                self.listBoxItemType = ko.observable(new ListBoxD(dList001));
                self.listBoxItems = ko.observable(new ListBoxD([]));
                self.listBoxItemType().selectedCode.subscribe(function (codeChange) {
                    if (codeChange === '1') {
                        qmm017.service.getListItemMaster(0).done(function (lstItem) {
                            self.listBoxItems().itemList([]);
                            _.forEach(lstItem, function (item) {
                                self.listBoxItems().itemList.push({ code: item.itemCode, name: item.itemName });
                            });
                        });
                    }
                    else if (codeChange === '2') {
                        qmm017.service.getListItemMaster(1).done(function (lstItem) {
                            self.listBoxItems().itemList([]);
                            _.forEach(lstItem, function (item) {
                                self.listBoxItems().itemList.push({ code: item.itemCode, name: item.itemName });
                            });
                        });
                    }
                    else if (codeChange === '3') {
                        qmm017.service.getListItemMaster(2).done(function (lstItem) {
                            self.listBoxItems().itemList([]);
                            _.forEach(lstItem, function (item) {
                                self.listBoxItems().itemList.push({ code: item.itemCode, name: item.itemName });
                            });
                        });
                    }
                });
            }
            return DScreen;
        }());
        qmm017.DScreen = DScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
