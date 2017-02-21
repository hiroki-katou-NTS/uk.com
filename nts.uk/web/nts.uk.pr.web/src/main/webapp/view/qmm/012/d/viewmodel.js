var qmm012;
(function (qmm012) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.isEditable = ko.observable(true);
                    self.isEnable = ko.observable(true);
                    self.enable = ko.observable(true);
                    self.selectedCode_D_001 = ko.observable('1');
                    self.ComboBoxItemList_D_001 = ko.observableArray([
                        new ComboboxItemModel('1', '任意控除項目'),
                        new ComboboxItemModel('2', '社会保険項目'),
                        new ComboboxItemModel('3', '所得税項目'),
                        new ComboboxItemModel('4', '住民税項目')
                    ]);
                    self.selectedCode_D_001 = ko.observable('1');
                    //end combobox data
                    //D_003
                    self.checked_D_003 = ko.observable(true);
                    //D_004
                    self.checked_D_004 = ko.observable(true);
                    //D_005
                    self.checked_D_005 = ko.observable(true);
                    //D_006
                    self.checked_D_006 = ko.observable(true);
                    //D_006
                    self.checked_D_007 = ko.observable(true);
                    //D_002
                    self.roundingRules_D_002 = ko.observableArray([
                        { code: '1', name: 'ゼロを表示する' },
                        { code: '2', name: 'ゼロを表示しない' }
                    ]);
                    self.selectedRuleCode_D_002 = ko.observable(1);
                    self.currencyeditor_D_001 = {
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
                    self.textArea_D_005 = ko.observable("");
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
            var ComboboxItemModel = (function () {
                function ComboboxItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ComboboxItemModel;
            }());
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm012.d || (qmm012.d = {}));
})(qmm012 || (qmm012 = {}));
