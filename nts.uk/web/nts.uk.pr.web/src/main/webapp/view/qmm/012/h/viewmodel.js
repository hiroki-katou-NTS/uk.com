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
                    this.CurrentItemSalaryPeriod = ko.observable(null);
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
                    //textediter
                    //001
                    self.texteditor_INP_001 = {
                        value: self.CurrentStrY,
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "60px",
                            textalign: "left"
                        }))
                    };
                    //002
                    self.texteditor_INP_002 = {
                        value: self.CurrentEndY,
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "60px",
                            textalign: "left"
                        }))
                    };
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
                    self.CurrentItemMaster(nts.uk.ui.windows.getShared('itemMaster'));
                    if (self.CurrentItemMaster()) {
                        h.service.findItemSalaryPeriod(self.CurrentItemMaster().itemCode).done(function (ItemSalary) {
                            self.CurrentItemSalaryPeriod(ItemSalary);
                        }).fail(function (res) {
                            // Alert message
                            alert(res);
                        });
                        self.CurrentCategoryAtrName(self.CurrentItemMaster().categoryAtrName);
                    }
                    self.CurrentItemSalaryPeriod.subscribe(function (ItemSalaryPeriod) {
                        self.CurrentItemCode(ItemSalaryPeriod ? ItemSalaryPeriod.itemCode : '');
                        self.CurrentPeriodAtr(ItemSalaryPeriod ? ItemSalaryPeriod.periodAtr : 0);
                        self.CurrentStrY(ItemSalaryPeriod ? ItemSalaryPeriod.strY : 0);
                        self.CurrentEndY(ItemSalaryPeriod ? ItemSalaryPeriod.endY : 0);
                        self.CurrentCycleAtr(ItemSalaryPeriod ? ItemSalaryPeriod.cycleAtr : 0);
                        self.CurrentCycle01Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle01Atr : 0);
                        self.CurrentCycle02Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle02Atr : 0);
                        self.CurrentCycle03Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle03Atr : 0);
                        self.CurrentCycle04Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle04Atr : 0);
                        self.CurrentCycle05Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle05Atr : 0);
                        self.CurrentCycle06Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle06Atr : 0);
                        self.CurrentCycle07Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle07Atr : 0);
                        self.CurrentCycle08Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle08Atr : 0);
                        self.CurrentCycle09Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle09Atr : 0);
                        self.CurrentCycle10Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle10Atr : 0);
                        self.CurrentCycle11Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle11Atr : 0);
                        self.CurrentCycle12Atr(ItemSalaryPeriod ? ItemSalaryPeriod.cycle12Atr : 0);
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
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qmm012.h || (qmm012.h = {}));
})(qmm012 || (qmm012 = {}));
