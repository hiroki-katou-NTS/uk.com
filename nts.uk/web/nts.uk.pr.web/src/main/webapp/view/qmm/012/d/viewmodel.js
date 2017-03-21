var qmm012;
(function (qmm012) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    //Checkbox
                    //D_003
                    this.checked_D_003 = ko.observable(false);
                    //D_003
                    this.checked_D_004 = ko.observable(false);
                    //D_003
                    this.checked_D_005 = ko.observable(false);
                    //D_003
                    this.checked_D_006 = ko.observable(false);
                    //D_003
                    this.checked_D_007 = ko.observable(false);
                    this.CurrentItemMaster = ko.observable(null);
                    this.CurrentItemDeduct = ko.observable(null);
                    this.CurrentAlRangeHigh = ko.observable(0);
                    this.CurrentAlRangeHighAtr = ko.observable(0);
                    this.CurrentAlRangeLow = ko.observable(0);
                    this.CurrentAlRangeLowAtr = ko.observable(0);
                    this.CurrentDeductAtr = ko.observable(0);
                    this.CurrentErrRangeHigh = ko.observable(0);
                    this.CurrentErrRangeHighAtr = ko.observable(0);
                    this.CurrentErrRangeLow = ko.observable(0);
                    this.CurrentErrRangeLowAtr = ko.observable(0);
                    this.CurrentMemo = ko.observable("");
                    this.CurrentItemDisplayAtr = ko.observable(1);
                    this.CurrentZeroDisplaySet = ko.observable(1);
                    var self = this;
                    self.isEditable = ko.observable(true);
                    self.isEnable = ko.observable(true);
                    self.enable = ko.observable(true);
                    self.ComboBoxItemList_D_001 = ko.observableArray([
                        new ComboboxItemModel(0, '任意控除項目'),
                        new ComboboxItemModel(1, '社会保険項目'),
                        new ComboboxItemModel(2, '所得税項目'),
                        new ComboboxItemModel(3, '住民税項目')
                    ]);
                    //end combobox data
                    //D_002
                    self.roundingRules_D_002 = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    //currencyeditor
                    //001
                    self.currencyeditor_D_001 = {
                        value: self.CurrentErrRangeHigh,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        required: ko.observable(false),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    //002
                    self.currencyeditor_D_002 = {
                        value: self.CurrentAlRangeHigh,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        required: ko.observable(false),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    //003
                    self.currencyeditor_D_003 = {
                        value: self.CurrentErrRangeLow,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        required: ko.observable(false),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    //004
                    self.currencyeditor_D_004 = {
                        value: self.CurrentAlRangeLow,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        required: ko.observable(false),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                    self.CurrentItemMaster.subscribe(function (ItemMaster) {
                        if (ItemMaster) {
                            d.service.findItemDeduct(ItemMaster.itemCode).done(function (ItemDeduct) {
                                self.CurrentItemDeduct(ItemDeduct);
                            }).fail(function (res) {
                                // Alert message
                                alert(res);
                            });
                        }
                        else {
                            self.CurrentItemDeduct(null);
                        }
                        self.checked_D_003(ItemMaster ? ItemMaster.itemDisplayAtr == 0 ? true : false : false);
                        self.CurrentZeroDisplaySet(ItemMaster ? ItemMaster.zeroDisplaySet : 1);
                    });
                    self.CurrentItemDeduct.subscribe(function (ItemDeduct) {
                        self.CurrentAlRangeHigh(ItemDeduct ? ItemDeduct.alRangeHigh : 0);
                        self.CurrentAlRangeLow(ItemDeduct ? ItemDeduct.alRangeLow : 0);
                        self.CurrentAlRangeLowAtr(ItemDeduct ? ItemDeduct.alRangeLowAtr : 0);
                        self.CurrentDeductAtr(ItemDeduct ? ItemDeduct.deductAtr : 0);
                        self.CurrentErrRangeHigh(ItemDeduct ? ItemDeduct.errRangeHigh : 0);
                        self.CurrentErrRangeLow(ItemDeduct ? ItemDeduct.errRangeLow : 0);
                        self.CurrentMemo(ItemDeduct ? ItemDeduct.memo : "");
                        self.checked_D_004(ItemDeduct ? ItemDeduct.errRangeHighAtr == 0 ? false : true : false);
                        self.checked_D_005(ItemDeduct ? ItemDeduct.alRangeHighAtr == 0 ? false : true : false);
                        self.checked_D_006(ItemDeduct ? ItemDeduct.errRangeLowAtr == 0 ? false : true : false);
                        self.checked_D_007(ItemDeduct ? ItemDeduct.alRangeLowAtr == 0 ? false : true : false);
                    });
                    self.checked_D_003.subscribe(function (NewValue) {
                        self.CurrentItemDisplayAtr(NewValue ? 0 : 1);
                    });
                    self.checked_D_004.subscribe(function (NewValue) {
                        self.CurrentErrRangeHighAtr(NewValue ? 1 : 0);
                    });
                    self.checked_D_005.subscribe(function (NewValue) {
                        self.CurrentAlRangeHighAtr(NewValue ? 1 : 0);
                    });
                    self.checked_D_006.subscribe(function (NewValue) {
                        self.CurrentErrRangeLowAtr(NewValue ? 1 : 0);
                    });
                    self.checked_D_007.subscribe(function (NewValue) {
                        self.CurrentAlRangeLowAtr(NewValue ? 1 : 0);
                    });
                }
                ScreenModel.prototype.openHDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('itemMaster', self.CurrentItemMaster());
                    nts.uk.ui.windows.sub.modal('../h/index.xhtml', { height: 570, width: 735, dialogClass: "no-close" }).onClosed(function () {
                    });
                };
                ScreenModel.prototype.openIDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('itemMaster', self.CurrentItemMaster());
                    nts.uk.ui.windows.sub.modal('../i/index.xhtml', { height: 620, width: 1060, dialogClass: "no-close" }).onClosed(function () {
                    });
                };
                ScreenModel.prototype.GetCurrentItemDeduct = function () {
                    var self = this;
                    var ItemDeduct = new d.service.model.ItemDeduct(self.CurrentDeductAtr(), self.CurrentErrRangeLowAtr(), self.CurrentErrRangeLow(), self.CurrentErrRangeHighAtr(), self.CurrentErrRangeHigh(), self.CurrentAlRangeLowAtr(), self.CurrentAlRangeLow(), self.CurrentAlRangeHighAtr(), self.CurrentAlRangeHigh(), self.CurrentMemo());
                    return ItemDeduct;
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
