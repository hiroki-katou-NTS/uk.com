var qmm012;
(function (qmm012) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.CurrentItemMaster = ko.observable(null);
                    var self = this;
                    self.enable = ko.observable(true);
                    //F_005
                    self.checked_F_002 = ko.observable(true);
                    //F_001
                    self.roundingRules_F_001 = ko.observableArray([
                        { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺吶ｋ' },
                        { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺励↑縺�' }
                    ]);
                    self.selectedRuleCode_F_001 = ko.observable(1);
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qmm012.f || (qmm012.f = {}));
})(qmm012 || (qmm012 = {}));
