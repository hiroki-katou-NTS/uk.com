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
                    this.CurrentItemCode = ko.observable('');
                    this.CurrentPeriodAtr = ko.observable(0);
                    this.CurrentStrY = ko.observable(1900);
                    this.CurrentEndY = ko.observable(1900);
                    this.CurrentCycleAtr = ko.observable(0);
                    this.H_SEL_003_checked = ko.observable(false);
                    this.H_SEL_004_checked = ko.observable(false);
                    this.H_SEL_005_checked = ko.observable(false);
                    this.H_SEL_006_checked = ko.observable(false);
                    this.H_SEL_007_checked = ko.observable(false);
                    this.H_SEL_008_checked = ko.observable(false);
                    this.H_SEL_009_checked = ko.observable(false);
                    this.H_SEL_010_checked = ko.observable(false);
                    this.H_SEL_011_checked = ko.observable(false);
                    this.H_SEL_012_checked = ko.observable(false);
                    this.H_SEL_013_checked = ko.observable(false);
                    this.H_SEL_014_checked = ko.observable(false);
                    var self = this;
                    //set Switch Data
                    self.roundingRules_H_SEL_001 = ko.observableArray([
                        { code: 1, name: '設定する' },
                        { code: 0, name: '設定しない' }
                    ]);
                    //005 006 007 008 009 010
                    self.roundingRules_H_SEL_002 = ko.observableArray([
                        { code: 1, name: 'する' },
                        { code: 0, name: 'しない' }
                    ]);
                    self.CurrentItemPeriod.subscribe(function (ItemPeriod) {
                        self.CurrentPeriodAtr(ItemPeriod ? ItemPeriod.periodAtr : 0);
                        self.CurrentStrY(ItemPeriod ? ItemPeriod.strY : 1900);
                        self.CurrentEndY(ItemPeriod ? ItemPeriod.endY : 1900);
                        self.CurrentCycleAtr(ItemPeriod ? ItemPeriod.cycleAtr : 0);
                        self.H_SEL_003_checked(ItemPeriod ? ItemPeriod.cycle01Atr == 1 ? true : false : false);
                        self.H_SEL_004_checked(ItemPeriod ? ItemPeriod.cycle02Atr == 1 ? true : false : false);
                        self.H_SEL_005_checked(ItemPeriod ? ItemPeriod.cycle03Atr == 1 ? true : false : false);
                        self.H_SEL_006_checked(ItemPeriod ? ItemPeriod.cycle04Atr == 1 ? true : false : false);
                        self.H_SEL_007_checked(ItemPeriod ? ItemPeriod.cycle05Atr == 1 ? true : false : false);
                        self.H_SEL_008_checked(ItemPeriod ? ItemPeriod.cycle06Atr == 1 ? true : false : false);
                        self.H_SEL_009_checked(ItemPeriod ? ItemPeriod.cycle07Atr == 1 ? true : false : false);
                        self.H_SEL_010_checked(ItemPeriod ? ItemPeriod.cycle08Atr == 1 ? true : false : false);
                        self.H_SEL_011_checked(ItemPeriod ? ItemPeriod.cycle09Atr == 1 ? true : false : false);
                        self.H_SEL_012_checked(ItemPeriod ? ItemPeriod.cycle10Atr == 1 ? true : false : false);
                        self.H_SEL_013_checked(ItemPeriod ? ItemPeriod.cycle11Atr == 1 ? true : false : false);
                        self.H_SEL_014_checked(ItemPeriod ? ItemPeriod.cycle12Atr == 1 ? true : false : false);
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
                        self.CurrentItemCode(itemMaster.itemCode);
                    }
                    if (nts.uk.ui.windows.getShared('itemPeriod'))
                        self.CurrentItemPeriod(nts.uk.ui.windows.getShared('itemPeriod'));
                };
                ScreenModel.prototype.getCurrentItemPeriod = function () {
                    //return  ItemPeriod customer has input to form
                    var self = this;
                    return new h.service.model.ItemPeriod(self.CurrentItemMaster().itemCode, self.CurrentPeriodAtr(), self.CurrentStrY(), self.CurrentEndY(), self.CurrentCycleAtr(), self.H_SEL_003_checked() == true ? 1 : 0, self.H_SEL_004_checked() == true ? 1 : 0, self.H_SEL_005_checked() == true ? 1 : 0, self.H_SEL_006_checked() == true ? 1 : 0, self.H_SEL_007_checked() == true ? 1 : 0, self.H_SEL_008_checked() == true ? 1 : 0, self.H_SEL_009_checked() == true ? 1 : 0, self.H_SEL_010_checked() == true ? 1 : 0, self.H_SEL_011_checked() == true ? 1 : 0, self.H_SEL_012_checked() == true ? 1 : 0, self.H_SEL_013_checked() == true ? 1 : 0, self.H_SEL_014_checked() == true ? 1 : 0);
                };
                ScreenModel.prototype.SubmitDialog = function () {
                    var self = this;
                    var itemPeriodOld = self.CurrentItemPeriod();
                    var itemPeriod = self.getCurrentItemPeriod();
                    if (itemPeriodOld) {
                        //it mean this item has been created before
                        h.service.updateItemPeriod(itemPeriod, self.CurrentItemMaster()).done(function (res) {
                            nts.uk.ui.windows.setShared('itemPeriod', itemPeriod);
                            nts.uk.ui.windows.close();
                        }).fail(function (res) {
                            alert(res.value);
                        });
                    }
                    else {
                        h.service.addItemPeriod(itemPeriod, self.CurrentItemMaster()).done(function (res) {
                            nts.uk.ui.windows.setShared('itemPeriod', itemPeriod);
                            nts.uk.ui.windows.close();
                        }).fail(function (res) {
                            alert(res.value);
                        });
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
//# sourceMappingURL=viewmodel.js.map