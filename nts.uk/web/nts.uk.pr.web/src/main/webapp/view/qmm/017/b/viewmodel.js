var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var BScreen = (function () {
            function BScreen() {
                var self = this;
                self.roundingRules = ko.observableArray([
                    { code: '0', name: 'かんたん設定' },
                    { code: '1', name: '詳細設定' }
                ]);
                self.selectedRuleCode = ko.observable(0);
                self.roundingRules2 = ko.observableArray([
                    { code: '0', name: '利用しない' },
                    { code: '1', name: '利用する' }
                ]);
                self.selectedRuleCode2 = ko.observable(0);
                self.yearMonth = ko.observable('2016/12');
            }
            return BScreen;
        }());
        qmm017.BScreen = BScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
