module qmm012.h.viewmodel {
    export class ScreenModel {
        //textediter

        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //gridlist
        gridListItems: KnockoutObservableArray<GridItemModel>;
        columns: KnockoutObservableArray<any>;
        gridListCurrentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        //Switch
        roundingRules_H_SEL_001: KnockoutObservableArray<any>;
        roundingRules_H_SEL_002: KnockoutObservableArray<any>;

        enable: KnockoutObservable<boolean> = ko.observable(true);

        CurrentItemMaster: KnockoutObservable<qmm012.b.service.model.ItemMaster> = ko.observable(null);
        CurrentCategoryAtrName: KnockoutObservable<string> = ko.observable('');
        CurrentItemPeriod: KnockoutObservable<service.model.ItemPeriod> = ko.observable(null);
        CurrentItemCode: KnockoutObservable<string> = ko.observable('');
        CurrentPeriodAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentStrY: KnockoutObservable<number> = ko.observable(1900);
        CurrentEndY: KnockoutObservable<number> = ko.observable(1900);
        CurrentCycleAtr: KnockoutObservable<number> = ko.observable(0);

        H_SEL_003_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_004_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_005_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_006_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_007_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_008_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_009_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_010_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_011_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_012_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_013_checked: KnockoutObservable<boolean> = ko.observable(false);
        H_SEL_014_checked: KnockoutObservable<boolean> = ko.observable(false);

        CurrentCycle01Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle02Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle03Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle04Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle05Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle06Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle07Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle08Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle09Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle10Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle11Atr: KnockoutObservable<number> = ko.observable(0);
        CurrentCycle12Atr: KnockoutObservable<number> = ko.observable(0);

        constructor() {
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

            self.CurrentItemPeriod.subscribe(function(ItemPeriod: service.model.ItemPeriod) {
                self.CurrentPeriodAtr(ItemPeriod ? ItemPeriod.periodAtr : 0);
                self.CurrentStrY(ItemPeriod ? ItemPeriod.strY : 1900);
                self.CurrentEndY(ItemPeriod ? ItemPeriod.endY : 1900);
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
            self.LoadItemPeriod();
        }
        LoadItemPeriod() {
            let self = this;

            let itemMaster = nts.uk.ui.windows.getShared('itemMaster');
            if (itemMaster != undefined) {
                self.CurrentItemMaster(itemMaster);
                self.CurrentCategoryAtrName(itemMaster.categoryAtrName);
                self.CurrentItemCode(itemMaster.itemCode);

            self.CurrentItemMaster(nts.uk.ui.windows.getShared('itemMaster'));
            if (self.CurrentItemMaster()) {
                self.CurrentCategoryAtrName(self.CurrentItemMaster().categoryAtrName);
                service.findItemPeriod(self.CurrentItemMaster()).done(function(ItemPeriod: service.model.ItemPeriod) {
                  self.CurrentItem(ItemPeriod);
                });

            }
            if (nts.uk.ui.windows.getShared('itemPeriod'))
                self.CurrentItemPeriod(nts.uk.ui.windows.getShared('itemPeriod'));
        }

        getCurrentItemPeriod() {
            let self = this;
            return new service.model.ItemPeriod(
                self.CurrentPeriodAtr(),
                self.CurrentStrY(),
                self.CurrentEndY(),
                self.CurrentCycleAtr(),
                self.H_SEL_003_checked() == true ? 1 : 0,
                self.H_SEL_004_checked() == true ? 1 : 0,
                self.H_SEL_005_checked() == true ? 1 : 0,
                self.H_SEL_006_checked() == true ? 1 : 0,
                self.H_SEL_007_checked() == true ? 1 : 0,
                self.H_SEL_008_checked() == true ? 1 : 0,
                self.H_SEL_009_checked() == true ? 1 : 0,
                self.H_SEL_010_checked() == true ? 1 : 0,
                self.H_SEL_011_checked() == true ? 1 : 0,
                self.H_SEL_012_checked() == true ? 1 : 0,
                self.H_SEL_013_checked() == true ? 1 : 0,
                self.H_SEL_014_checked() == true ? 1 : 0
            );
        }
        SubmitDialog() {
            let self = this;
            let itemPeriod = self.getCurrentItemPeriod();
            nts.uk.ui.windows.setShared('itemPeriod', itemPeriod);
        }
        CloseDialog() {
            nts.uk.ui.windows.close();
        }
    }
    
    class GridItemModel {
        code: string;
        name: string;

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