var qmm012;
(function (qmm012) {
    var i;
    (function (i) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    //002
                    this.checked_002 = ko.observable(false);
                    this.gridListCurrentCode = ko.observable();
                    this.selectedRuleCode_001 = ko.observable(1);
                    this.CurrentItemMaster = ko.observable(null);
                    this.ItemSalaryBDList = ko.observableArray([]);
                    this.CurrentCategoryAtrName = ko.observable('');
                    this.CurrentItemSalaryBD = ko.observable(null);
                    this.CurrentItemBreakdownCd = ko.observable('');
                    this.CurrentItemBreakdownName = ko.observable('');
                    this.CurrentItemBreakdownAbName = ko.observable('');
                    this.CurrentUniteCd = ko.observable('');
                    this.CurrentZeroDispSet = ko.observable(1);
                    this.CurrentItemDispAtr = ko.observable(0);
                    this.CurrentErrRangeLowAtr = ko.observable(0);
                    this.CurrentErrRangeLow = ko.observable(0);
                    this.CurrentErrRangeHighAtr = ko.observable(0);
                    this.CurrentErrRangeHigh = ko.observable(0);
                    this.CurrentAlRangeLowAtr = ko.observable(0);
                    this.CurrentAlRangeLow = ko.observable(0);
                    this.CurrentAlRangeHighAtr = ko.observable(0);
                    this.CurrentAlRangeHigh = ko.observable(0);
                    this.enable_I_INP_002 = ko.observable(false);
                    var self = this;
                    //textediter
                    self.texteditor = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "60px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    //start Switch Data
                    self.enable = ko.observable(true);
                    self.roundingRules_001 = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    //endSwitch Data
                    //currencyeditor
                    //005
                    self.currencyeditor_I_INP_005 = {
                        value: self.CurrentErrRangeHigh,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    //006
                    self.currencyeditor_I_INP_006 = {
                        value: self.CurrentAlRangeHigh,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    //007
                    self.currencyeditor_I_INP_007 = {
                        value: self.CurrentErrRangeLow,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    //008
                    self.currencyeditor_I_INP_008 = {
                        value: self.CurrentAlRangeLow,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    //end currencyeditor
                    // start search box 
                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.ItemSalaryBDList(), "childs"));
                    // end search box 
                    self.columns = ko.observableArray([
                        { headerText: 'ード', prop: 'itemBreakdownCd', width: 100 },
                        { headerText: '名', prop: 'itemBreakdownName', width: 150 }
                    ]);
                    self.CurrentItemMaster(nts.uk.ui.windows.getShared('itemMaster'));
                    if (self.CurrentItemMaster()) {
                        i.service.findItemSalaryBD(self.CurrentItemMaster().itemCode).done(function (ItemSalaryBDs) {
                            for (var _i = 0, ItemSalaryBDs_1 = ItemSalaryBDs; _i < ItemSalaryBDs_1.length; _i++) {
                                var ItemSalaryBD = ItemSalaryBDs_1[_i];
                                self.ItemSalaryBDList.push(ItemSalaryBD);
                            }
                            if (self.ItemSalaryBDList().length)
                                self.gridListCurrentCode(self.ItemSalaryBDList()[0].itemBreakdownCd);
                        }).fail(function (res) {
                            // Alert message
                            alert(res);
                        });
                        self.CurrentCategoryAtrName(self.CurrentItemMaster().categoryAtrName);
                    }
                    self.gridListCurrentCode.subscribe(function (newValue) {
                        var item = _.find(self.ItemSalaryBDList(), function (ItemSalaryBD) {
                            return ItemSalaryBD.itemBreakdownCd == newValue;
                        });
                        self.CurrentItemSalaryBD(item);
                    });
                    self.CurrentItemSalaryBD.subscribe(function (ItemSalaryBD) {
                        self.CurrentItemBreakdownCd(ItemSalaryBD ? ItemSalaryBD.itemBreakdownCd : '');
                        self.CurrentItemBreakdownName(ItemSalaryBD ? ItemSalaryBD.itemBreakdownName : '');
                        self.CurrentItemBreakdownAbName(ItemSalaryBD ? ItemSalaryBD.itemBreakdownAbName : '');
                        self.CurrentUniteCd(ItemSalaryBD ? ItemSalaryBD.uniteCd : '');
                        self.CurrentZeroDispSet(ItemSalaryBD ? ItemSalaryBD.zeroDispSet : 1);
                        self.checked_002(ItemSalaryBD ? ItemSalaryBD.itemDispAtr == 1 ? false : true : false);
                        self.CurrentItemDispAtr(ItemSalaryBD ? ItemSalaryBD.itemDispAtr : 0);
                        self.CurrentErrRangeLowAtr(ItemSalaryBD ? ItemSalaryBD.errRangeLowAtr : 0);
                        self.CurrentErrRangeLow(ItemSalaryBD ? ItemSalaryBD.errRangeLow : 0);
                        self.CurrentErrRangeHighAtr(ItemSalaryBD ? ItemSalaryBD.errRangeHighAtr : 0);
                        self.CurrentErrRangeHigh(ItemSalaryBD ? ItemSalaryBD.errRangeHigh : 0);
                        self.CurrentAlRangeLowAtr(ItemSalaryBD ? ItemSalaryBD.alRangeLowAtr : 0);
                        self.CurrentAlRangeLow(ItemSalaryBD ? ItemSalaryBD.alRangeLow : 0);
                        self.CurrentAlRangeHighAtr(ItemSalaryBD ? ItemSalaryBD.alRangeHighAtr : 0);
                        self.CurrentAlRangeHigh(ItemSalaryBD ? ItemSalaryBD.alRangeHigh : 0);
                    });
                    self.checked_002.subscribe(function (NewValue) {
                        self.CurrentItemDispAtr(NewValue == false ? 1 : 0);
                    });
                }
                ScreenModel.prototype.SubmitDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.CloseDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
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
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
        })(viewmodel = i.viewmodel || (i.viewmodel = {}));
    })(i = qmm012.i || (qmm012.i = {}));
})(qmm012 || (qmm012 = {}));
