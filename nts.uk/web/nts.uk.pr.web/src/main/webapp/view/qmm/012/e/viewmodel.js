var qmm012;
(function (qmm012) {
    var e;
    (function (e) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //E_004
                    self.checked_E_004 = ko.observable(true);
                    //E_005
                    self.checked_E_005 = ko.observable(true);
                    //E_006
                    self.checked_E_006 = ko.observable(true);
                    //E_007
                    self.checked_E_007 = ko.observable(true);
                    //E_008
                    self.checked_E_008 = ko.observable(true);
                    //E_001 To 003
                    //E_001To003
                    self.roundingRules_E_001 = ko.observableArray([
                        { code: '1', name: '時間' },
                        { code: '2', name: '回数' }
                    ]);
                    self.roundingRules_E_002 = ko.observableArray([
                        { code: '1', name: '対象' },
                        { code: '2', name: '非対称' }
                    ]);
                    self.roundingRules_E_003 = ko.observableArray([
                        { code: '1', name: 'ゼロを表示する' },
                        { code: '2', name: 'ゼロを表示しない' }
                    ]);
                    self.selectedRuleCode_E_001 = ko.observable(1);
                    self.selectedRuleCode_E_002 = ko.observable(1);
                    self.selectedRuleCode_E_003 = ko.observable(1);
                    self.enable = ko.observable(true);
                    self.currencyeditor_E_001 = {
                        value: ko.observable(),
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            decimallength: 2,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        required: ko.observable(false),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    //start textarea
                    self.textArea_E_005 = ko.observable("");
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
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qmm012.e || (qmm012.e = {}));
})(qmm012 || (qmm012 = {}));
