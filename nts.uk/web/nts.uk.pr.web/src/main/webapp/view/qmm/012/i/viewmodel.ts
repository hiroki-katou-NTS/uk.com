module qmm012.i.viewmodel {
    export class ScreenModel {

        //textediter

        //Checkbox
        checked: KnockoutObservable<boolean>;
        //002
        checked_002: KnockoutObservable<boolean> = ko.observable(false);
        checked_003: KnockoutObservable<boolean> = ko.observable(false);
        checked_004: KnockoutObservable<boolean> = ko.observable(false);
        checked_005: KnockoutObservable<boolean> = ko.observable(false);
        checked_006: KnockoutObservable<boolean> = ko.observable(false);

        columns: KnockoutObservableArray<any>;
        gridListCurrentCode: KnockoutObservable<string> = ko.observable('');
        //Switch
        roundingRules_001: KnockoutObservableArray<any>;
        selectedRuleCode_001: KnockoutObservable<number> = ko.observable(1);

        enable: KnockoutObservable<boolean>;
        //currencyeditor
        currencyeditor_I_INP_005: any;
        currencyeditor_I_INP_006: any;
        currencyeditor_I_INP_007: any;
        currencyeditor_I_INP_008: any;
        //search box 
        filteredData: any;
        CurrentItemMaster: KnockoutObservable<qmm012.b.service.model.ItemMaster> = ko.observable(null);
        ItemBDList: KnockoutObservableArray<service.model.ItemBD> = ko.observableArray([]);
        CurrentCategoryAtrName: KnockoutObservable<string> = ko.observable('');
        CurrentItemBD: KnockoutObservable<service.model.ItemBD> = ko.observable(null);
        CurrentItemBreakdownCd: KnockoutObservable<string> = ko.observable('');
        CurrentItemBreakdownName: KnockoutObservable<string> = ko.observable('');
        CurrentItemBreakdownAbName: KnockoutObservable<string> = ko.observable('');
        CurrentUniteCd: KnockoutObservable<string> = ko.observable('');
        CurrentZeroDispSet: KnockoutObservable<number> = ko.observable(1);
        CurrentItemDispAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeHigh: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeHigh: KnockoutObservable<number> = ko.observable(0);

        enable_I_INP_002: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            var self = this;
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
                constraint: 'ErrRangeHigh',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                }))
            };
            //006
            self.currencyeditor_I_INP_006 = {
                value: self.CurrentAlRangeHigh,
                constraint: 'AlRangeHigh',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                }))
            };
            //007
            self.currencyeditor_I_INP_007 = {
                value: self.CurrentErrRangeLow,
                constraint: 'ErrRangeLow',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                }))
            };
            //008
            self.currencyeditor_I_INP_008 = {
                value: self.CurrentAlRangeLow,
                constraint: 'AlRangeLow',
                option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    currencyformat: "JPY",
                    currencyposition: 'right'
                }))
            };
            //end currencyeditor
            // start search box 
            self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.ItemBDList(), "childs"));
            // end search box 


            self.columns = ko.observableArray([
                { headerText: 'ード', prop: 'itemBreakdownCd', width: 100 },
                { headerText: '名', prop: 'itemBreakdownName', width: 150 }
            ]);
            self.CurrentItemMaster(nts.uk.ui.windows.getShared('itemMaster'));
            if (self.CurrentItemMaster()) {
                self.LoadItem();
                self.CurrentCategoryAtrName(self.CurrentItemMaster().categoryAtrName);
            }

            self.gridListCurrentCode.subscribe(function(newValue) {
                var item = _.find(self.ItemBDList(), function(ItemBD: service.model.ItemBD) {
                    return ItemBD.itemBreakdownCd == newValue;
                });
                self.CurrentItemBD(item);
            });
            self.CurrentItemBD.subscribe(function(ItemBD: service.model.ItemBD) {
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
            self.checked_002.subscribe(function(newValue) {
                self.CurrentItemDispAtr(newValue == false ? 1 : 0);
            });
        }

        LoadItem(itemCode?) {
            let self = this;
            service.findAllItemBD(self.CurrentItemMaster()).done(function(ItemBDs: Array<service.model.ItemBD>) {
                self.ItemBDList(ItemBDs);
                if (self.ItemBDList().length)
                    if (!itemCode)
                        self.gridListCurrentCode(self.ItemBDList()[0].itemBreakdownCd);
                    else
                        self.gridListCurrentCode(itemCode);
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
        }
        GetCurrentItemBD() {
            let self = this;
            return new service.model.ItemBD(
                self.CurrentItemMaster().itemCode,
                self.CurrentItemBreakdownCd(),
                self.CurrentItemBreakdownName(),
                self.CurrentItemBreakdownAbName(),
                self.CurrentUniteCd(),
                self.CurrentZeroDispSet(),
                self.checked_002() == true ? 0 : 1,
                self.checked_005() == true ? 1 : 0,
                self.CurrentErrRangeLow(),
                self.checked_003() == true ? 1 : 0,
                self.CurrentErrRangeHigh(),
                self.checked_006() == true ? 1 : 0,
                self.CurrentAlRangeLow(),
                self.checked_004() == true ? 1 : 0,
                self.CurrentAlRangeHigh()
            );
        }

        SaveItem() {
            let self = this;
            if (self.enable_I_INP_002())
                self.addItemBD();
            else
                self.updateItemBD();

        }
        deleteItem() {
            let self = this;
            let itemBD = self.GetCurrentItemBD();
            let itemCode;
            let index = self.ItemBDList.indexOf(self.CurrentItemBD());
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
                } else
                    itemCode = '';
            }
            service.deleteItemBD(self.CurrentItemMaster(), itemBD).done(function(any) {
                self.LoadItem(itemCode);
            }).fail(function(res) {
                alert(res);
            });

        }
        addItemBD() {
            let self = this;
            let itemBD = self.GetCurrentItemBD();
            let itemCode = itemBD.itemBreakdownCd;
            service.addItemBD(self.CurrentItemMaster(), itemBD).done(function(any) {
                self.LoadItem(itemCode);
            }).fail(function(res) {
                alert(res);
            });
        }

        updateItemBD() {
            let self = this;
            let itemBD = self.GetCurrentItemBD();
            let itemCode = itemBD.itemBreakdownCd;
            service.updateItemBD(self.CurrentItemMaster(), itemBD).done(function(any) {
                self.LoadItem(itemCode);
            }).fail(function(res) {
                alert(res);
            });
        }
        CloseDialog() {
            nts.uk.ui.windows.close();
        }
        AddNewItem() {
            let self = this;
            self.gridListCurrentCode('');
            self.enable_I_INP_002(true);
        }
    }
    class GridItemModel {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }

    }
    class ComboboxItemModel {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

}