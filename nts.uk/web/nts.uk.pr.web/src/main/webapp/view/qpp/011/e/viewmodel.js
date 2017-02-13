// TreeGrid Node
var qpp011;
(function (qpp011) {
    var e;
    (function (e) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.enable = ko.observable(true);
                self.roundingRules = ko.observableArray([
                    { code: '1', name: 'Item1' },
                    { code: '2', name: 'Item2' }
                ]);
                self.selectedRuleCode = ko.observable(1);
            }
            ScreenModel.prototype.submitDialog = function () {
                nts.uk.ui.windows.close();
            };
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            return ScreenModel;
        }());
        e.ScreenModel = ScreenModel;
    })(e = qpp011.e || (qpp011.e = {}));
})(qpp011 || (qpp011 = {}));
;
