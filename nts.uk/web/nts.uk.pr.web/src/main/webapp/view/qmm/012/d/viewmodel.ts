module qmm012.d.viewmodel {
    export class ScreenModel {
        isEditable: KnockoutObservable<boolean>;
        isEnable: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        //001
        ComboBoxItemList_D_001: KnockoutObservableArray<ComboboxItemModel>;
        //Checkbox
        //D_003
        checked_D_003: KnockoutObservable<boolean> = ko.observable(true);
        //D_003
        checked_D_004: KnockoutObservable<boolean> = ko.observable(false);
        //D_003
        checked_D_005: KnockoutObservable<boolean> = ko.observable(false);
        //D_003
        checked_D_006: KnockoutObservable<boolean> = ko.observable(false);
        //D_003
        checked_D_007: KnockoutObservable<boolean> = ko.observable(false);
        //Switch
        currencyeditor_D_001: any;
        currencyeditor_D_002: any;
        currencyeditor_D_003: any;
        currencyeditor_D_004: any;
        //D_002
        roundingRules_D_002: KnockoutObservableArray<any>;

        CurrentItemMaster: KnockoutObservable<qmm012.b.service.model.ItemMaster> = ko.observable(null);
        CurrentItemDeduct: KnockoutObservable<service.model.ItemDeduct> = ko.observable(null);
        CurrentAlRangeHigh: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentDeductAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeHigh: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentMemo: KnockoutObservable<String> = ko.observable("");
        CurrentItemDisplayAtr: KnockoutObservable<number> = ko.observable(1);
        CurrentZeroDisplaySet: KnockoutObservable<number> = ko.observable(1);
        currentItemPeriod: KnockoutObservable<qmm012.h.service.model.ItemPeriod> = ko.observable(null);
        D_LBL_011_Text: KnockoutObservable<string> = ko.observable('設定なし');
        currentItemBDs: KnockoutObservableArray<qmm012.i.service.model.ItemBD> = ko.observableArray([]);
        D_LBL_012_Text: KnockoutObservable<string> = ko.observable("設定なし");
        constructor() {
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
                constraint: 'ErrRangeHigh',
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
                constraint: 'AlRangeHigh',
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
                constraint: 'ErrRangeLow',
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
                constraint: 'AlRangeLow',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                })),
                required: ko.observable(false),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            self.CurrentItemMaster.subscribe(function(ItemMaster: qmm012.b.service.model.ItemMaster) {
                if (ItemMaster) {
                    service.findItemDeduct(ItemMaster.itemCode).done(function(ItemDeduct: service.model.ItemDeduct) {
                        self.CurrentItemDeduct(ItemDeduct);
                    }).fail(function(res) {
                        // Alert message
                        alert(res);
                    });
                } else {
                    self.CurrentItemDeduct(null);
                }
                self.loadItemPeriod();
                self.loadItemBDs();
                self.checked_D_003(ItemMaster ? ItemMaster.itemDisplayAtr == 0 ? true : false : false);
                self.CurrentZeroDisplaySet(ItemMaster ? ItemMaster.zeroDisplaySet : 1);
            });
            self.CurrentItemDeduct.subscribe(function(ItemDeduct: service.model.ItemDeduct) {
                self.CurrentAlRangeHigh(ItemDeduct ? ItemDeduct.alRangeHigh : 0);
                self.CurrentAlRangeLow(ItemDeduct ? ItemDeduct.alRangeLow : 0);
                self.CurrentDeductAtr(ItemDeduct ? ItemDeduct.deductAtr : 0);
                self.CurrentErrRangeHigh(ItemDeduct ? ItemDeduct.errRangeHigh : 0);
                self.CurrentErrRangeLow(ItemDeduct ? ItemDeduct.errRangeLow : 0);
                self.CurrentMemo(ItemDeduct ? ItemDeduct.memo : "");
                self.checked_D_004(ItemDeduct ? ItemDeduct.errRangeHighAtr == 0 ? false : true : false);
                self.checked_D_005(ItemDeduct ? ItemDeduct.alRangeHighAtr == 0 ? false : true : false);
                self.checked_D_006(ItemDeduct ? ItemDeduct.errRangeLowAtr == 0 ? false : true : false);
                self.checked_D_007(ItemDeduct ? ItemDeduct.alRangeLowAtr == 0 ? false : true : false);
            });

            self.checked_D_003.subscribe(function(NewValue) {
                self.CurrentItemDisplayAtr(NewValue ? 0 : 1);
            });
            self.currentItemPeriod.subscribe(function(newValue) {
                self.D_LBL_011_Text(newValue ? newValue.periodAtr == 1 ? '設定あり' : '設定なし' : '設定なし');
            });
            self.currentItemBDs.subscribe(function(newValue) {
                self.D_LBL_012_Text(newValue.length ? '設定あり' : '設定なし');
            });

        }
        loadItemPeriod() {
            let self = this;
            //Load Screen H  Data
            if (self.CurrentItemMaster()) {
                qmm012.h.service.findItemPeriod(self.CurrentItemMaster()).done(function(ItemPeriod: qmm012.h.service.model.ItemPeriod) {
                    self.currentItemPeriod(ItemPeriod);
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                });
            } else
                self.currentItemPeriod(undefined);
        }
        loadItemBDs() {
            let self = this;
            if (self.CurrentItemMaster()) {
                qmm012.i.service.findAllItemBD(self.CurrentItemMaster()).done(function(ItemBDs: Array<qmm012.i.service.model.ItemBD>) {
                    self.currentItemBDs(ItemBDs);
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                });
            } else
                self.currentItemBDs([]);
        }

        openHDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('itemMaster', self.CurrentItemMaster());
            nts.uk.ui.windows.setShared('itemPeriod', self.currentItemPeriod());
            nts.uk.ui.windows.sub.modal('../h/index.xhtml', { height: 570, width: 735, dialogClass: "no-close" }).onClosed(function(): any {
                self.currentItemPeriod(nts.uk.ui.windows.getShared('itemPeriod'));
            });
        }

        openIDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('itemMaster', self.CurrentItemMaster());
            nts.uk.ui.windows.setShared('itemBDs', self.currentItemBDs());
            nts.uk.ui.windows.sub.modal('../i/index.xhtml', { height: 620, width: 1060, dialogClass: "no-close" }).onClosed(function(): any {
                self.currentItemBDs(nts.uk.ui.windows.getShared('itemBDs'));
            });
        }
        GetCurrentItemDeduct() {
            let self = this;
            let ItemDeduct = new service.model.ItemDeduct(
                self.CurrentDeductAtr(),
                self.checked_D_006() ? 1 : 0,
                self.CurrentErrRangeLow(),
                self.checked_D_004() ? 1 : 0,
                self.CurrentErrRangeHigh(),
                self.checked_D_007() ? 1 : 0,
                self.CurrentAlRangeLow(),
                self.checked_D_005() ? 1 : 0,
                self.CurrentAlRangeHigh(),
                self.CurrentMemo());
            return ItemDeduct;
        }

    }

    class ComboboxItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}