var qmm012;
(function (qmm012) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.isEnable = ko.observable(true);
                    this.isEditable = ko.observable(true);
                    this.CurrentItemPeriod = ko.observable(null);
                    this.CurrentCycleAtr = ko.observable();
                    var self = this;
                    self.ComboBoxItemList_C_001 = ko.observableArray([
                        new ComboboxItemModel('1', '髫ｱ�ｽｲ驕橸ｿｽ'),
                        new ComboboxItemModel('2', '鬮ｱ讚�ｽｪ�ｽｲ驕橸ｿｽ(鬮ｯ莉呻ｽｺ�ｽｦ邵ｺ繧��ｽ奇ｿｽ�ｽｼ�ｿｽ'),
                        new ComboboxItemModel('3', '鬮ｱ讚�ｽｪ�ｽｲ驕橸ｿｽ(鬮ｯ莉呻ｽｺ�ｽｦ邵ｺ�ｽｪ邵ｺ證ｦ�ｽｼ�ｿｽ'),
                        new ComboboxItemModel('4', '鬨ｾ螢ｼ蜈ｱ髮具ｽｻ(隰�蜿･�ｿｽ�ｽ･陷会ｿｽ)'),
                        new ComboboxItemModel('5', '鬨ｾ螢ｼ蜈ｱ髮具ｽｻ(陞ｳ螢ｽ謔�陋ｻ�ｽｸ陋ｻ�ｽｩ騾包ｽｨ)')
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
                        { code: '1', name: '陝�ｽｾ髮趣ｽ｡' },
                        { code: '2', name: '陝�ｽｾ髮趣ｽ｡陞滂ｿｽ' }
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
                        { code: '1', name: '郢ｧ�ｽｼ郢晢ｽｭ郢ｧ螳夲ｽ｡�ｽｨ驕会ｽｺ邵ｺ蜷ｶ�ｽ�' },
                        { code: '2', name: '郢ｧ�ｽｼ郢晢ｽｭ郢ｧ螳夲ｽ｡�ｽｨ驕会ｽｺ邵ｺ蜉ｱ竊醍ｸｺ�ｿｽ' }
                    ]);
                    self.selectedRuleCode_C_011 = ko.observable(1);
                    //017
                    self.roundingRules_C_017 = ko.observableArray([
                        { code: '1', name: '驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｼ驛｢譎｢�ｽｽ�ｽｭ驛｢�ｽｧ陞ｳ螟ｲ�ｽｽ�ｽ｡�ｿｽ�ｽｽ�ｽｨ' },
                        { code: '2', name: '驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｼ驛｢譎｢�ｽｽ�ｽｭ驛｢�ｽｧ陞ｳ螟ｲ�ｽｽ�ｽ｡�ｿｽ�ｽｽ�ｽｨ' },
                        { code: '3', name: '驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｼ驛｢譎｢�ｽｽ�ｽｭ驛｢�ｽｧ陞ｳ螟ｲ�ｽｽ�ｽ｡�ｿｽ�ｽｽ�ｽｨ' },
                        { code: '4', name: '驛｢�ｽｧ�ｿｽ�ｽｽ�ｽｼ驛｢譎｢�ｽｽ�ｽｭ驛｢�ｽｧ陞ｳ螟ｲ�ｽｽ�ｽ｡�ｿｽ�ｽｽ�ｽｨ' },
                    ]);
                    self.selectedRuleCode_C_017 = ko.observable(1);
                    //endSwitch Data
                    //start radiogroup data
                    //004
                    self.RadioItemList_C_004 = ko.observableArray([
                        new BoxModel(1, '陷茨ｽｨ陷ｩ�ｽ｡闕ｳ�ｿｽ陟穂ｹ昴�'),
                        new BoxModel(2, '驍ｨ�ｽｦ闕ｳ荳ｻ�ｽ･驢搾ｽｴ�ｿｽ陟')
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
                    self.CurrentItemPeriod.subscribe(function (ItemPeriod) {
                        c.service.findItemSalary(ItemPeriod.itemCode).done(function (ItemSalary) {
                            debugger;
                            // self.screenModel.screenModelC.CurrentItemPeriod(PeriodItem);
                        }).fail(function (res) {
                            // Alert message
                            alert(res);
                        });
                        // self.CurrentCycleAtr(ItemPeriod.cycleAtr);
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
