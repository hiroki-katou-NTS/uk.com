var qmm012;
(function (qmm012) {
    var e;
    (function (e) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.CurrentItemMaster = ko.observable(null);
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
                        { code: '1', name: '譎る俣' },
                        { code: '2', name: '蝗樊焚' }
                    ]);
                    self.roundingRules_E_002 = ko.observableArray([
                        { code: '1', name: '蟇ｾ雎｡' },
                        { code: '2', name: '髱槫ｯｾ遘ｰ' }
                    ]);
                    self.roundingRules_E_003 = ko.observableArray([
                        { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺吶ｋ' },
                        { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ遉ｺ縺励↑縺�' }
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
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qmm012.e || (qmm012.e = {}));
})(qmm012 || (qmm012 = {}));
