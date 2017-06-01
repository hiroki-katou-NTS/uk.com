var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var BScreen = (function () {
            function BScreen(data) {
                var self = this;
                self.historyId = ko.observable('');
                self.roundingRules = ko.observableArray([
                    { code: '0', name: 'かんたん設定' },
                    { code: '1', name: '詳細設定' }
                ]);
                self.selectedDifficultyAtr = ko.observable(0);
                self.roundingRules2 = ko.observableArray([
                    { code: '0', name: '利用しない' },
                    { code: '1', name: '利用する' }
                ]);
                self.selectedConditionAtr = ko.observable(0);
                self.startYearMonth = ko.observable('');
                self.startYearMonth(data.startYearMonth());
                self.formulaCode = ko.observable('');
                self.formulaName = ko.observable('');
                self.startYearMonthFormated = ko.observable('');
                self.isNewMode = ko.observable(data.isNewMode());
                ;
                data.isNewMode.subscribe(function (val) {
                    self.isNewMode(val);
                });
                self.startYearMonth.subscribe(function (ymChange) {
                    if (!self.isNewMode()) {
                        self.startYearMonthFormated('(' + nts.uk.time.yearmonthInJapanEmpire(ymChange).toString() + ') ~');
                    }
                    else {
                        self.startYearMonthFormated('');
                    }
                });
                self.comboBoxUseMaster = ko.observable({
                    itemList: ko.observableArray([]),
                    selectedCode: ko.observable(1)
                });
                //auto fill input formula code to 3 char
                $("#inp-formulaCode").blur(function () {
                    self.autoFill();
                });
            }
            BScreen.prototype.autoFill = function () {
                if ($("#inp-formulaCode").val().length > 0 && $("#inp-formulaCode").val().length < 3) {
                    var countMissing = 3 - $("#inp-formulaCode").val().length;
                    var zeroToFill = '';
                    for (var i = 0; i < countMissing; i++) {
                        zeroToFill += '0';
                    }
                    var currentValue = $("#inp-formulaCode").val();
                    $("#inp-formulaCode").val(zeroToFill + currentValue);
                }
            };
            return BScreen;
        }());
        qmm017.BScreen = BScreen;
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm017.b.vm.js.map