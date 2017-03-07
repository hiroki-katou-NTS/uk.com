var qmm012;
(function (qmm012) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    //start combobox data
                    self.isEnable = ko.observable(true);
                    self.isEditable = ko.observable(true);
                    //combobox data
                    self.ComboBoxItemList_C_001 = ko.observableArray([
                        new ComboboxItemModel('1', '課税'),
                        new ComboboxItemModel('2', '非課税(限度あり）'),
                        new ComboboxItemModel('3', '非課税(限度なし）'),
                        new ComboboxItemModel('4', '通勤費(手入力)'),
                        new ComboboxItemModel('5', '通勤費(定期券利用)')
                    ]);
                    self.selectedCode_C_001 = ko.observable('1');
                    //end combobox data
                    //start checkbox Data
                    self.checked_C_012 = ko.observable(true);
                    self.checked_C_013 = ko.observable(true);
                    self.checked_C_014 = ko.observable(true);
                    self.checked_C_015 = ko.observable(true);
                    self.checked_C_016 = ko.observable(true);
                    //end checkbox data
                    //start Switch Data
                    self.enable = ko.observable(true);
                    //005 006 007 008 009 010
                    self.roundingRules_C_002_003_005To010 = ko.observableArray([
                        { code: '1', name: '対象' },
                        { code: '2', name: '対象外' }
                    ]);
                    self.selectedRuleCode_C_002 = ko.observable(1);
                    self.selectedRuleCode_C_003 = ko.observable(1);
                    self.selectedRuleCode_C_005 = ko.observable(1);
                    self.selectedRuleCode_C_006 = ko.observable(1);
                    self.selectedRuleCode_C_007 = ko.observable(1);
                    self.selectedRuleCode_C_008 = ko.observable(1);
                    self.selectedRuleCode_C_009 = ko.observable(1);
                    self.selectedRuleCode_C_010 = ko.observable(1);
                    //011
                    self.roundingRules_C_011 = ko.observableArray([
                        { code: '1', name: 'ゼロを表示する' },
                        { code: '2', name: 'ゼロを表示しない' }
                    ]);
                    self.selectedRuleCode_C_011 = ko.observable(1);
                    //017
                    self.roundingRules_C_017 = ko.observableArray([
                        { code: '1', name: '繧ｼ繝ｭ繧定｡ｨ' },
                        { code: '2', name: '繧ｼ繝ｭ繧定｡ｨ' },
                        { code: '3', name: '繧ｼ繝ｭ繧定｡ｨ' },
                        { code: '4', name: '繧ｼ繝ｭ繧定｡ｨ' },
                    ]);
                    self.selectedRuleCode_C_017 = ko.observable(1);
                    //endSwitch Data
                    //start radiogroup data
                    //004
                    self.RadioItemList_C_004 = ko.observableArray([
                        new BoxModel(1, '全員一律で指定する'),
                        new BoxModel(2, '給与契約形態ごとに指定する')
                    ]);
                    self.selectedId_C_004 = ko.observable(1);
                    //end radiogroup data
                    //currencyeditor_C_001
                    self.currencyeditor_C_001 = {
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
                    //end currencyeditor
                    //start textarea
                    self.textArea = ko.observable("");
                    //end textarea
                    self.selectedCode_C_001.subscribe(function (newValue) {
                        $('#C_LBL_002').show();
                        $('#C_Div_002').show();
                        $('#C_BTN_003').show();
                        $('#C_Div_004').show();
                        switch (newValue) {
                            case '1':
                                $('#C_Div_001').hide();
                                break;
                            case '2':
                            case '3':
                            case '4':
                                $('#C_Div_001').show();
                                self.selectedRuleCode_C_017('1');
                                break;
                            case '5':
                                $('#C_Div_001').show();
                                $('#C_LBL_002').hide();
                                $('#C_Div_002').hide();
                                $('#C_Div_003').show();
                                $('#C_BTN_003').hide();
                                $('#C_Div_004').hide();
                                break;
                        }
                    });
                    self.selectedRuleCode_C_017.subscribe(function (newValue) {
                        $('#C_Div_002').hide();
                        $('#C_Div_003').hide();
                        switch (newValue) {
                            case '1':
                            case '3':
                                $('#C_Div_002').show();
                                break;
                            case '2':
                            case '4':
                                $('#C_Div_003').show();
                                break;
                        }
                    });
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    //dropdownlist event
                    //end switch event
                };
                ScreenModel.prototype.openIDialog = function () {
                    nts.uk.ui.windows.sub.modal('../i/index.xhtml', { height: 600, width: 1015, dialogClass: "no-close" }).onClosed(function () {
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    this.id = id;
                    this.name = name;
                }
                return BoxModel;
            }());
            var GridItemModel = (function () {
                function GridItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return GridItemModel;
            }());
            var ComboboxItemModel = (function () {
                function ComboboxItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ComboboxItemModel;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm012.c || (qmm012.c = {}));
})(qmm012 || (qmm012 = {}));
