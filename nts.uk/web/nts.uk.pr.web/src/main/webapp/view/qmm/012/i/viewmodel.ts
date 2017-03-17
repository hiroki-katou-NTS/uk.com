module qmm012.i.viewmodel {
    export class ScreenModel {

        //textediter
        texteditor: any;
        //Checkbox
        checked: KnockoutObservable<boolean>;
        //002
        checked_002: KnockoutObservable<boolean> = ko.observable(false);

        columns: KnockoutObservableArray<any>;
        gridListCurrentCode: KnockoutObservable<any> = ko.observable();
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
        CurrentItemMaster: KnockoutObservable<qmm012.b.service.model.ItemMasterModel> = ko.observable(null);
        ItemSalaryBDList: KnockoutObservableArray<service.model.ItemSalaryBD> = ko.observableArray([]);
        CurrentCategoryAtrName: KnockoutObservable<string> = ko.observable('');
        CurrentItemSalaryBD: KnockoutObservable<service.model.ItemSalaryBD> = ko.observable(null);
        CurrentItemBreakdownCd: KnockoutObservable<string> = ko.observable('');
        CurrentItemBreakdownName: KnockoutObservable<string> = ko.observable('');
        CurrentItemBreakdownAbName: KnockoutObservable<string> = ko.observable('');
        CurrentUniteCd: KnockoutObservable<string> = ko.observable('');
        CurrentZeroDispSet: KnockoutObservable<number> = ko.observable(1);
        CurrentItemDispAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeLowAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeHighAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeHigh: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeLowAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeHighAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeHigh: KnockoutObservable<number> = ko.observable(0);

        enable_I_INP_002: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
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
                service.findItemSalaryBD(self.CurrentItemMaster().itemCode).done(function(ItemSalaryBDs: Array<service.model.ItemSalaryBD>) {
                    for (let ItemSalaryBD of ItemSalaryBDs) {
                        self.ItemSalaryBDList.push(ItemSalaryBD);
                    }
                    if (self.ItemSalaryBDList().length)
                        self.gridListCurrentCode(self.ItemSalaryBDList()[0].itemBreakdownCd);
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                });
                self.CurrentCategoryAtrName(self.CurrentItemMaster().categoryAtrName);
            }
            self.gridListCurrentCode.subscribe(function(newValue) {
                var item = _.find(self.ItemSalaryBDList(), function(ItemSalaryBD: service.model.ItemSalaryBD) {
                    return ItemSalaryBD.itemBreakdownCd == newValue;
                });
                self.CurrentItemSalaryBD(item);
            });
            self.CurrentItemSalaryBD.subscribe(function(ItemSalaryBD: service.model.ItemSalaryBD) {
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
            self.checked_002.subscribe(function(NewValue) {
                self.CurrentItemDispAtr(NewValue == false ? 1 : 0);
            });
        }
        SubmitDialog() {
            nts.uk.ui.windows.close();
        }
        CloseDialog() {
            nts.uk.ui.windows.close();
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