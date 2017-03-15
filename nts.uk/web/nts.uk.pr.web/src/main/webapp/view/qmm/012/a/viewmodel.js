var qmm012;
(function (qmm012) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.enable = ko.observable(true);
                    var self = this;
                    //start Switch Data
                    self.enable;
                    self.roundingRules = ko.observableArray([
                        { code: 0, name: '�x������' },
                        { code: 1, name: '�T������' },
                        { code: 2, name: '�ΑӍ���' }
                    ]);
                    self.selectedRuleCode = ko.observable(0);
                    //endSwitch Data
                }
                ScreenModel.prototype.submitInfo = function () {
                    var self = this;
                    var groupCode = self.selectedRuleCode();
                    nts.uk.ui.windows.setShared('groupCode', groupCode);
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
