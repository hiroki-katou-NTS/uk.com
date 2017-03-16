var qmm012;
(function (qmm012) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.selectedRuleCode = ko.observable(0);
                    this.enable = ko.observable(true);
                    var self = this;
                    //start Switch Data
                    self.enable;
                    self.roundingRules = ko.observableArray([
                        { code: 0, name: '支給項目' },
                        { code: 1, name: '控除項目' },
                        { code: 2, name: '勤怠項目' }
                    ]);
                    self.selectedRuleCode = ko.observable(0);
                    //endSwitch Data
                }
                ScreenModel.prototype.submitInfo = function () {
                    var self = this;
                    var groupCode = self.selectedRuleCode();
                    nts.uk.sessionStorage.setItem('groupCode', groupCode);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm012.a || (qmm012.a = {}));
})(qmm012 || (qmm012 = {}));
