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
                    this.CurrentItem = ko.observable(null);
                    this.CurrentItemCode = ko.observable('');
                    this.CurrentPeriodAtr = ko.observable(0);
                    this.CurrentStrY = ko.observable(0);
                    this.CurrentEndY = ko.observable(0);
                    this.CurrentCycleAtr = ko.observable(0);
                    this.CurrentCycle01Atr = ko.observable(0);
                    this.CurrentCycle02Atr = ko.observable(0);
                    this.CurrentCycle03Atr = ko.observable(0);
                    this.CurrentCycle04Atr = ko.observable(0);
                    this.CurrentCycle05Atr = ko.observable(0);
                    this.CurrentCycle06Atr = ko.observable(0);
                    this.CurrentCycle07Atr = ko.observable(0);
                    this.CurrentCycle08Atr = ko.observable(0);
                    this.CurrentCycle09Atr = ko.observable(0);
                    this.CurrentCycle10Atr = ko.observable(0);
                    this.CurrentCycle11Atr = ko.observable(0);
                    this.CurrentCycle12Atr = ko.observable(0);
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
                    self.LoadItemPeriod();
                    self.CurrentItem.subscribe(function (ItemPeriod) {
                        self.CurrentItemCode(ItemPeriod ? ItemPeriod.itemCd : '');
                        self.CurrentPeriodAtr(ItemPeriod ? ItemPeriod.periodAtr : 0);
                        self.CurrentStrY(ItemPeriod ? ItemPeriod.strY : 0);
                        self.CurrentEndY(ItemPeriod ? ItemPeriod.endY : 0);
                        self.CurrentCycleAtr(ItemPeriod ? ItemPeriod.cycleAtr : 0);
                        self.CurrentCycle01Atr(ItemPeriod ? ItemPeriod.cycle01Atr : 0);
                        self.CurrentCycle02Atr(ItemPeriod ? ItemPeriod.cycle02Atr : 0);
                        self.CurrentCycle03Atr(ItemPeriod ? ItemPeriod.cycle03Atr : 0);
                        self.CurrentCycle04Atr(ItemPeriod ? ItemPeriod.cycle04Atr : 0);
                        self.CurrentCycle05Atr(ItemPeriod ? ItemPeriod.cycle05Atr : 0);
                        self.CurrentCycle06Atr(ItemPeriod ? ItemPeriod.cycle06Atr : 0);
                        self.CurrentCycle07Atr(ItemPeriod ? ItemPeriod.cycle07Atr : 0);
                        self.CurrentCycle08Atr(ItemPeriod ? ItemPeriod.cycle08Atr : 0);
                        self.CurrentCycle09Atr(ItemPeriod ? ItemPeriod.cycle09Atr : 0);
                        self.CurrentCycle10Atr(ItemPeriod ? ItemPeriod.cycle10Atr : 0);
                        self.CurrentCycle11Atr(ItemPeriod ? ItemPeriod.cycle11Atr : 0);
                        self.CurrentCycle12Atr(ItemPeriod ? ItemPeriod.cycle12Atr : 0);
                    });
                }
                ScreenModel.prototype.LoadItemPeriod = function () {
                    var self = this;
                    self.CurrentItemMaster(nts.uk.ui.windows.getShared('itemMaster'));
                    if (self.CurrentItemMaster()) {
                        self.CurrentCategoryAtrName(self.CurrentItemMaster().categoryAtrName);
                        h.service.findItemPeriod(self.CurrentItemMaster()).done(function (ItemPeriod) {
                            self.CurrentItem(ItemPeriod);
                        });
                    }
                };
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
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qmm012.h || (qmm012.h = {}));
})(qmm012 || (qmm012 = {}));
