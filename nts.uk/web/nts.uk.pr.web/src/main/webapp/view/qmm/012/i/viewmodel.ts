module qmm012.i.viewmodel {
    export class ScreenModel {

        //textediter

        //Checkbox
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
        CurrentItemBreakdownCode: KnockoutObservable<string> = ko.observable('');
        CurrentItemBreakdownName: KnockoutObservable<string> = ko.observable('');
        CurrentItemBreakdownAbName: KnockoutObservable<string> = ko.observable('');
        CurrentUniteCode: KnockoutObservable<string> = ko.observable('');
        CurrentZeroDispSet: KnockoutObservable<number> = ko.observable(1);
        CurrentItemDispAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeHigh: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeHigh: KnockoutObservable<number> = ko.observable(0);

        enable_I_INP_002: KnockoutObservable<boolean> = ko.observable(false);
        I_BTN_003_enable: KnockoutObservable<boolean> = ko.observable(true);
        currentItemCode: KnockoutObservable<string> = ko.observable('');
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
                { headerText: 'ード', prop: 'itemBreakdownCode', width: 100 },
                { headerText: '名', prop: 'itemBreakdownName', width: 150 }
            ]);

            self.gridListCurrentCode.subscribe(function(newValue) {
                var item = _.find(self.ItemBDList(), function(ItemBD: service.model.ItemBD) {
                    return ItemBD.itemBreakdownCode == newValue;
                });
                self.CurrentItemBD(item);
            });
            self.CurrentItemBD.subscribe(function(ItemBD: service.model.ItemBD) {
                self.CurrentItemBreakdownCode(ItemBD ? ItemBD.itemBreakdownCode : '');
                self.CurrentItemBreakdownName(ItemBD ? ItemBD.itemBreakdownName : '');
                self.CurrentItemBreakdownAbName(ItemBD ? ItemBD.itemBreakdownAbName : '');
                self.CurrentUniteCode(ItemBD ? ItemBD.uniteCode : '');
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
                if (ItemBD != undefined) {
                    self.enable_I_INP_002(false);
                }
            });
            self.enable_I_INP_002.subscribe(function(newValue) {
                if (newValue) {
                    self.I_BTN_003_enable(false);
                    self.gridListCurrentCode('');
                }
                else {
                    self.I_BTN_003_enable(true);
                    $('#I_INP_002').ntsError('clear');
                }

            })
            self.checked_002.subscribe(function(newValue) {
                self.CurrentItemDispAtr(newValue == false ? 1 : 0);
            });
            self.CurrentItemBreakdownCode.subscribe(function(newValue) {
                if (self.enable_I_INP_002()) {
                    var item = _.find(self.ItemBDList(), function(ItemBD: service.model.ItemBD) {
                        return ItemBD.itemBreakdownCode == newValue;
                    });
                    if (item)
                        $('#I_INP_002').ntsError('set', 'えらーです');
                    else
                        $('#I_INP_002').ntsError('clear');
                }
            })
            self.loadItemBDs();
        }

        loadItemBDs() {
            let self = this;
            self.CurrentItemMaster(nts.uk.ui.windows.getShared('itemMaster'));
            let itemMaster = self.CurrentItemMaster();
            if (itemMaster != undefined) {
                self.loadItem();
                self.CurrentCategoryAtrName(self.CurrentItemMaster().categoryAtrName);
                self.currentItemCode(itemMaster.itemCode);
            }
        }
        refreshAndSetSelectedCode(itemCode) {
            let self = this;
            //refresh list
            self.ItemBDList(self.ItemBDList());
            //set selected 
            if (self.ItemBDList().length)
                if (!itemCode)
                    self.gridListCurrentCode(self.ItemBDList()[0].itemBreakdownCode);
                else
                    self.gridListCurrentCode(itemCode);
        }
        loadItem(itemCode?) {
            //load itemBDList
            let self = this;
            let itemBDs = nts.uk.ui.windows.getShared('itemBDs');
            self.ItemBDList(itemBDs);
            self.refreshAndSetSelectedCode(itemCode);
        }
        getCurrentItemBD() {
            //get item customer has input on form 
            let self = this;
            return new service.model.ItemBD(
                self.CurrentItemBreakdownCode(),
                self.CurrentItemBreakdownName(),
                self.CurrentItemBreakdownAbName(),
                self.CurrentUniteCode(),
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

        saveItem() {
            let self = this;
            //if I_INP_002 is enable is mean add new mode
            if (self.enable_I_INP_002())
                self.addItemBD();
            else
                self.updateItemBD();

        }
        deleteItem() {
            let self = this;
            let itemBD = self.CurrentItemBD();
            if (itemBD) {
                nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                    let itemCode;
                    let index = self.ItemBDList().indexOf(self.CurrentItemBD());
                    //set selected code after remove item
                    if (self.ItemBDList().length > 1) {
                        if (index < self.ItemBDList().length - 1)
                            itemCode = self.ItemBDList()[index + 1].itemBreakdownCode;
                        else
                            itemCode = self.ItemBDList()[index - 1].itemBreakdownCode;
                    } else
                        itemCode = '';
                    //remove item and set selected code
                    self.ItemBDList().splice(index, 1);
                    self.refreshAndSetSelectedCode(itemCode);
                })
            }
        }
        addItemBD() {
            let self = this;
            //get itemBD on form
            let itemBD = self.getCurrentItemBD();
            //add item to array and set selected code
            self.ItemBDList().push(itemBD);
            self.refreshAndSetSelectedCode(itemBD.itemBreakdownCode);

        }

        updateItemBD() {
            let self = this;
            let itemBD = self.getCurrentItemBD();
            let index = self.ItemBDList().indexOf(self.CurrentItemBD());
            let itemCode = itemBD.itemBreakdownCode;
            //update item in array and set selected code
            self.ItemBDList()[index] = itemBD;
            self.refreshAndSetSelectedCode(itemCode);
        }
        closeDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('itemBDs', self.ItemBDList())
            nts.uk.ui.windows.close();
        }
        addNewItem() {
            let self = this;
            self.enable_I_INP_002(true);
        }
    }

}