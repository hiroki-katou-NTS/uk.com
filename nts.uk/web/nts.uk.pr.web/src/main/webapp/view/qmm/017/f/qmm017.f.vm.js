var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var FScreen = (function () {
            function FScreen() {
                var self = this;
                var hList001 = [
                    { code: '1', name: '全て' },
                ];
                self.listBoxItemType = ko.observable(new qmm017.ListBox(hList001));
                self.listBoxItems = ko.observable(new qmm017.ListBox([]));
                self.listBoxItemType().selectedCode.subscribe(function (codeChange) {
                });
            }
            return FScreen;
        }());
        qmm017.FScreen = FScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
