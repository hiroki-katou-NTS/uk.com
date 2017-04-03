var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var RScreen = (function () {
            function RScreen() {
                var self = this;
                var rList001 = [
                    { code: '1', name: '会社単価項目（会社単価＠）' },
                    { code: '2', name: '個人単価項目（個人単価＠）' }
                ];
                self.listBoxItemType = ko.observable(new qmm017.ListBox(rList001));
                self.listBoxItems = ko.observable(new qmm017.ListBox([]));
                self.listBoxItemType().selectedCode.subscribe(function (codeChange) {
                });
            }
            return RScreen;
        }());
        qmm017.RScreen = RScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
