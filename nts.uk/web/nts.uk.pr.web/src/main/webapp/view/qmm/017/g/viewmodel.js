var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var GScreen = (function () {
            function GScreen() {
                var self = this;
                self.listBoxItemType = ko.observable(new qmm017.ListBox([]));
                self.listBoxItems = ko.observable(new qmm017.ListBox([]));
                self.listBoxItemType().selectedCode.subscribe(function (codeChange) {
                });
            }
            return GScreen;
        }());
        qmm017.GScreen = GScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
