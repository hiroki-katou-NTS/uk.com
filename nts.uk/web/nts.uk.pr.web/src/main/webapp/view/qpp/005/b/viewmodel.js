var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp005;
                (function (qpp005) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var SwitchButton = (function () {
                                function SwitchButton() {
                                    var self = this;
                                    self.roundingRules = ko.observableArray([
                                        { code: '1', name: '縦方向' },
                                        { code: '2', name: '横方向' }
                                    ]);
                                    self.selectedRuleCode = ko.observable('2');
                                }
                                return SwitchButton;
                            }());
                            viewmodel.SwitchButton = SwitchButton;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.switchButton = new SwitchButton();
                                    self.visible = ko.observable(self.switchButton.selectedRuleCode() == '1');
                                    self.switchButton.selectedRuleCode.subscribe(function (newValue) {
                                        self.visible(newValue == '1');
                                    });
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qpp005.b || (qpp005.b = {}));
                })(qpp005 = view.qpp005 || (view.qpp005 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
