var qmm012;
(function (qmm012) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.enable = ko.observable(true);
                    //F_005
                    self.checked_F_002 = ko.observable(true);
                    //F_001
                    self.roundingRules_F_001 = ko.observableArray([
                        { code: '1', name: 'ゼロを表示する' },
                        { code: '2', name: 'ゼロを表示しない' }
                    ]);
                    self.selectedRuleCode_F_001 = ko.observable(1);
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    //dropdownlist event
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qmm012.f || (qmm012.f = {}));
})(qmm012 || (qmm012 = {}));
