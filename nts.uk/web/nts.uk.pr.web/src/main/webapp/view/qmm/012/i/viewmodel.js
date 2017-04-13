var qmm012;
(function (qmm012) {
    var i;
    (function (i) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.checked_002 = ko.observable(false);
                    this.checked_003 = ko.observable(false);
                    this.checked_004 = ko.observable(false);
                    this.checked_005 = ko.observable(false);
                    this.checked_006 = ko.observable(false);
                    this.gridListCurrentCode = ko.observable('');
                    this.selectedRuleCode_001 = ko.observable(1);
                    this.CurrentItemMaster = ko.observable(null);
                    this.ItemBDList = ko.observableArray([]);
                    this.CurrentCategoryAtrName = ko.observable('');
                    this.CurrentItemBD = ko.observable(null);
                    this.CurrentItemBreakdownCd = ko.observable('');
                    this.CurrentItemBreakdownName = ko.observable('');
                    this.CurrentItemBreakdownAbName = ko.observable('');
                    this.CurrentUniteCd = ko.observable('');
                    this.CurrentZeroDispSet = ko.observable(1);
                    this.CurrentItemDispAtr = ko.observable(0);
                    this.CurrentErrRangeLow = ko.observable(0);
                    this.CurrentErrRangeHigh = ko.observable(0);
                    this.CurrentAlRangeLow = ko.observable(0);
                    this.CurrentAlRangeHigh = ko.observable(0);
                    this.enable_I_INP_002 = ko.observable(false);
                    var self = this;
                    self.enable = ko.observable(true);
                    self.roundingRules_001 = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    self.currencyeditor_I_INP_005 = {
                        value: self.CurrentErrRangeHigh,
                        constraint: 'ErrRangeHigh',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    self.currencyeditor_I_INP_006 = {
                        value: self.CurrentAlRangeHigh,
                        constraint: 'AlRangeHigh',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    self.currencyeditor_I_INP_007 = {
                        value: self.CurrentErrRangeLow,
                        constraint: 'ErrRangeLow',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    self.currencyeditor_I_INP_008 = {
                        value: self.CurrentAlRangeLow,
                        constraint: 'AlRangeLow',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.ItemBDList(), "childs"));
                    self.columns = ko.observableArray([
                        { headerText: 'ード', prop: 'itemBreakdownCd', width: 100 },
                        { headerText: '名', prop: 'itemBreakdownName', width: 150 }
                    ]);
                    self.CurrentItemMaster(nts.uk.ui.windows.getShared('itemMaster'));
                    if (self.CurrentItemMaster()) {
                        self.LoadItem();
                        self.CurrentCategoryAtrName(self.CurrentItemMaster().categoryAtrName);
                    }
                    self.gridListCurrentCode.subscribe(function (newValue) {
                        var item = _.find(self.ItemBDList(), function (ItemBD) {
                            return ItemBD.itemBreakdownCd == newValue;
                        });
                        self.CurrentItemBD(item);
                    });
                    self.CurrentItemBD.subscribe(function (ItemBD) {
                        self.CurrentItemBreakdownCd(ItemBD ? ItemBD.itemBreakdownCd : '');
                        self.CurrentItemBreakdownName(ItemBD ? ItemBD.itemBreakdownName : '');
                        self.CurrentItemBreakdownAbName(ItemBD ? ItemBD.itemBreakdownAbName : '');
                        self.CurrentUniteCd(ItemBD ? ItemBD.uniteCd : '');
                        self.CurrentZeroDispSet(ItemBD ? ItemBD.zeroDispSet : 1);
                        self.checked_002(ItemBD ? ItemBD.itemDispAtr == 1 ? false : true : false);
                        self.CurrentItemDispAtr(ItemBD ? ItemBD.itemDispAtr : 0);
                        self.checked_005(ItemBD ? ItemBD.errRangeLowAtr == 1 ? true : false : false);
                        self.CurrentErrRangeLow(ItemBD ? ItemBD.errRangeLow : 0);
                        self.checked_003(ItemBD ? ItemBD.errRangeHighAtr == 1 ? true : false : false);
                        self.CurrentErrRangeHigh(ItemBD ? ItemBD.errRangeHigh : 0);
                        self.checked_006(ItemBD ? ItemBD.alRangeLowAtr == 1 ? true : false : false);
                        self.CurrentAlRangeLow(ItemBD ? ItemBD.alRangeLow : 0);
                        self.checked_004(ItemBD ? ItemBD.alRangeHighAtr == 1 ? true : false : false);
                        self.CurrentAlRangeHigh(ItemBD ? ItemBD.alRangeHigh : 0);
                        if (self.CurrentItemBreakdownCd() != undefined) {
                            self.enable_I_INP_002(false);
                        }
                    });
                    self.checked_002.subscribe(function (newValue) {
                        self.CurrentItemDispAtr(newValue == false ? 1 : 0);
                    });
                }
                ScreenModel.prototype.LoadItem = function (itemCode) {
                    var self = this;
                    i.service.findAllItemBD(self.CurrentItemMaster()).done(function (ItemBDs) {
                        self.ItemBDList(ItemBDs);
                        if (self.ItemBDList().length)
                            if (!itemCode)
                                self.gridListCurrentCode(self.ItemBDList()[0].itemBreakdownCd);
                            else
                                self.gridListCurrentCode(itemCode);
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.GetCurrentItemBD = function () {
                    var self = this;
                    return new i.service.model.ItemBD(self.CurrentItemMaster().itemCode, self.CurrentItemBreakdownCd(), self.CurrentItemBreakdownName(), self.CurrentItemBreakdownAbName(), self.CurrentUniteCd(), self.CurrentZeroDispSet(), self.checked_002() == true ? 0 : 1, self.checked_005() == true ? 1 : 0, self.CurrentErrRangeLow(), self.checked_003() == true ? 1 : 0, self.CurrentErrRangeHigh(), self.checked_006() == true ? 1 : 0, self.CurrentAlRangeLow(), self.checked_004() == true ? 1 : 0, self.CurrentAlRangeHigh());
                };
                ScreenModel.prototype.SaveItem = function () {
                    var self = this;
                    if (self.enable_I_INP_002())
                        self.addItemBD();
                    else
                        self.updateItemBD();
                };
                ScreenModel.prototype.deleteItem = function () {
                    var self = this;
                    var itemBD = self.GetCurrentItemBD();
                    var itemCode;
                    var index = self.ItemBDList.indexOf(self.CurrentItemBD());
                    if (index != undefined) {
                        if (self.ItemBDList().length > 1) {
                            if (index == 0)
                                itemCode = self.ItemBDList()[index + 1].itemBreakdownCd;
                            else {
                                if (index < self.ItemBDList().length - 1)
                                    itemCode = self.ItemBDList()[index + 1].itemBreakdownCd;
                                else
                                    itemCode = self.ItemBDList()[index - 1].itemBreakdownCd;
                            }
                        }
                        else
                            itemCode = '';
                    }
                    i.service.deleteItemBD(self.CurrentItemMaster(), itemBD).done(function (any) {
                        self.LoadItem(itemCode);
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.addItemBD = function () {
                    var self = this;
                    var itemBD = self.GetCurrentItemBD();
                    var itemCode = itemBD.itemBreakdownCd;
                    i.service.addItemBD(self.CurrentItemMaster(), itemBD).done(function (any) {
                        self.LoadItem(itemCode);
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.updateItemBD = function () {
                    var self = this;
                    var itemBD = self.GetCurrentItemBD();
                    var itemCode = itemBD.itemBreakdownCd;
                    i.service.updateItemBD(self.CurrentItemMaster(), itemBD).done(function (any) {
                        self.LoadItem(itemCode);
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.CloseDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.AddNewItem = function () {
                    var self = this;
                    self.gridListCurrentCode('');
                    self.enable_I_INP_002(true);
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
