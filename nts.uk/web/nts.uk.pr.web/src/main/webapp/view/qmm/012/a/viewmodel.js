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
                    this.lBL_004_Text = ko.observable('社員に対して支払う金額の種類を登録します。」と表記');
                    var self = this;
                    //start Switch Data
                    self.enable;
                    self.roundingRules = ko.observableArray([
                        { code: 0, name: '支給項目' },
                        { code: 1, name: '控除項目' },
                        { code: 2, name: '勤怠項目' }
                    ]);
                    //endSwitch Data
                    self.selectedRuleCode.subscribe(function (NewValue) {
                        self.lBL_004_Text(Gen_LBL_004_Text(NewValue));
                    });
                    function Gen_LBL_004_Text(NewValue) {
                        var text;
                        switch (NewValue) {
                            case 0:
                                text = "社員に対して支払う金額の種類を登録します。」と表記";
                                break;
                            case 1:
                                text = "社員から徴収する金額の種類を登録します。";
                                break;
                            case 2:
                                text = "社員の勤怠実績（日数・回数,時間）の種類を登録します";
                                break;
                        }
                        return text;
                    }
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
