var qmm012;
(function (qmm012) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.enable = ko.observable(true);
                    this.CurrentItemMaster = ko.observable(null);
                    this.CurrentCategoryAtrName = ko.observable('');
                    this.CurrentItemPeriod = ko.observable(null);
                    this.CurrentCodeAndNameText = ko.observable('');
                    this.CurrentPeriodAtr = ko.observable(0);
                    this.CurrentStrY = ko.observable(1900);
                    this.CurrentEndY = ko.observable(1900);
                    this.CurrentCycleAtr = ko.observable(0);
                    this.H_Sel_January = ko.observable(false);
                    this.H_Sel_February = ko.observable(false);
                    this.H_Sel_InMarch = ko.observable(false);
                    this.H_Sel_April = ko.observable(false);
                    this.H_Sel_May = ko.observable(false);
                    this.H_Sel_June = ko.observable(false);
                    this.H_Sel_July = ko.observable(false);
                    this.H_Sel_August = ko.observable(false);
                    this.H_Sel_September = ko.observable(false);
                    this.H_Sel_October = ko.observable(false);
                    this.H_Sel_November = ko.observable(false);
                    this.H_Sel_December = ko.observable(false);
                    this.CycleSetting = ko.observable(false);
                    var self = this;
                    //set Switch Data
                    self.Roundingrules_ValidityPeriod = ko.observableArray([
                        { code: 1, name: '設定する' },
                        { code: 0, name: '設定しない' }
                    ]);
                    self.Roundingrules_CycleSetting = ko.observableArray([
                        { code: 1, name: 'する' },
                        { code: 0, name: 'しない' }
                    ]);
                    self.CurrentItemPeriod.subscribe(function (ItemPeriod) {
                        self.CurrentPeriodAtr(ItemPeriod ? ItemPeriod.periodAtr : 0);
                        self.CurrentStrY(ItemPeriod ? ItemPeriod.strY : 1900);
                        self.CurrentEndY(ItemPeriod ? ItemPeriod.endY : 1900);
                        self.CurrentCycleAtr(ItemPeriod ? ItemPeriod.cycleAtr : 0);
                        self.H_Sel_January(ItemPeriod ? ItemPeriod.cycle01Atr == 1 ? true : false : false);
                        self.H_Sel_February(ItemPeriod ? ItemPeriod.cycle02Atr == 1 ? true : false : false);
                        self.H_Sel_InMarch(ItemPeriod ? ItemPeriod.cycle03Atr == 1 ? true : false : false);
                        self.H_Sel_April(ItemPeriod ? ItemPeriod.cycle04Atr == 1 ? true : false : false);
                        self.H_Sel_May(ItemPeriod ? ItemPeriod.cycle05Atr == 1 ? true : false : false);
                        self.H_Sel_June(ItemPeriod ? ItemPeriod.cycle06Atr == 1 ? true : false : false);
                        self.H_Sel_July(ItemPeriod ? ItemPeriod.cycle07Atr == 1 ? true : false : false);
                        self.H_Sel_August(ItemPeriod ? ItemPeriod.cycle08Atr == 1 ? true : false : false);
                        self.H_Sel_September(ItemPeriod ? ItemPeriod.cycle09Atr == 1 ? true : false : false);
                        self.H_Sel_October(ItemPeriod ? ItemPeriod.cycle10Atr == 1 ? true : false : false);
                        self.H_Sel_November(ItemPeriod ? ItemPeriod.cycle11Atr == 1 ? true : false : false);
                        self.H_Sel_December(ItemPeriod ? ItemPeriod.cycle12Atr == 1 ? true : false : false);
                    });
                    self.CurrentCycleAtr.subscribe(function (newValue) {
                        if (newValue == 1)
                            self.CycleSetting(true);
                        else
                            self.CycleSetting(false);
                    });
                    self.LoadItemPeriod();
                }
                ScreenModel.prototype.LoadItemPeriod = function () {
                    //this dialog only load data in session from parrent call it
                    var self = this;
                    var itemMaster = nts.uk.ui.windows.getShared('itemMaster');
                    if (itemMaster != undefined) {
                        self.CurrentItemMaster(itemMaster);
                        self.CurrentCategoryAtrName(itemMaster.categoryAtrName);
                        self.CurrentCodeAndNameText(itemMaster.itemCode + "  " + itemMaster.itemName);
                    }
                    if (nts.uk.ui.windows.getShared('itemPeriod'))
                        self.CurrentItemPeriod(nts.uk.ui.windows.getShared('itemPeriod'));
                };
                ScreenModel.prototype.getCurrentItemPeriod = function () {
                    //return  ItemPeriod customer has input to form
                    var self = this;
                    return new h.service.model.ItemPeriod(self.CurrentItemMaster().itemCode, self.CurrentPeriodAtr(), self.CurrentStrY(), self.CurrentEndY(), self.CurrentCycleAtr(), self.H_Sel_January() == true ? 1 : 0, self.H_Sel_February() == true ? 1 : 0, self.H_Sel_InMarch() == true ? 1 : 0, self.H_Sel_April() == true ? 1 : 0, self.H_Sel_May() == true ? 1 : 0, self.H_Sel_June() == true ? 1 : 0, self.H_Sel_July() == true ? 1 : 0, self.H_Sel_August() == true ? 1 : 0, self.H_Sel_September() == true ? 1 : 0, self.H_Sel_October() == true ? 1 : 0, self.H_Sel_November() == true ? 1 : 0, self.H_Sel_December() == true ? 1 : 0);
                };
                ScreenModel.prototype.validateForm = function () {
                    var self = this;
                    var returnResult = true;
                    if (self.CurrentStrY() > self.CurrentEndY()) {
                        nts.uk.ui.dialog.alert("範囲の指定が正しくありません。");
                        return false;
                    }
                    if (self.CurrentCycleAtr() == 1 && !self.H_Sel_January() && !self.H_Sel_December()) {
                        nts.uk.ui.dialog.alert("1月か12月が選択されていません。");
                        return false;
                    }
                    return returnResult;
                };
                ScreenModel.prototype.clearError = function () {
                    $('#H_Inp_StartYear').ntsError('clear');
                    $('#H_Inp_EndYear').ntsError('clear');
                };
                ScreenModel.prototype.SubmitDialog = function () {
                    var self = this;
                    var itemPeriodOld = self.CurrentItemPeriod();
                    var itemPeriod = self.getCurrentItemPeriod();
                    if (self.validateForm()) {
                        if (itemPeriodOld) {
                            //it mean this item has been created before
                            h.service.updateItemPeriod(itemPeriod, self.CurrentItemMaster()).done(function (res) {
                                nts.uk.ui.windows.setShared('itemPeriod', itemPeriod);
                                nts.uk.ui.windows.close();
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert(res.value);
                            });
                        }
                        else {
                            h.service.addItemPeriod(itemPeriod, self.CurrentItemMaster()).done(function (res) {
                                nts.uk.ui.windows.setShared('itemPeriod', itemPeriod);
                                nts.uk.ui.windows.close();
                            }).fail(function (res) {
                                nts.uk.ui.dialog.alert(res.value);
                            });
                        }
                    }
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
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qmm012.h || (qmm012.h = {}));
})(qmm012 || (qmm012 = {}));
