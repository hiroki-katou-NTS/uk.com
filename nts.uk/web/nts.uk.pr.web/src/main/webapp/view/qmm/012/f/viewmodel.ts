module qmm012.f.viewmodel {
    export class ScreenModel {
        //F 001
        roundingRules_F_001: KnockoutObservableArray<any>;
        CurrentItemMaster: KnockoutObservable<qmm012.b.service.model.ItemMaster> = ko.observable(null);
        CurrentZeroDisplaySet: KnockoutObservable<number> = ko.observable(1);
        checked_F_002: KnockoutObservable<boolean> = ko.observable(false);
        CurrentItemDisplayAtr: KnockoutObservable<number> = ko.observable(1);
        constructor() {
            let self = this;
            //F_001
            self.roundingRules_F_001 = ko.observableArray([
                { code: 1, name: 'ゼロを表示する' },
                { code: 0, name: 'ゼロを表示しない' }
            ]);
            self.CurrentItemMaster.subscribe(function(ItemMaster: qmm012.b.service.model.ItemMaster) {
                self.CurrentZeroDisplaySet(ItemMaster ? ItemMaster.zeroDisplaySet : 1);
                self.checked_F_002(ItemMaster ? ItemMaster.itemDisplayAtr == 0 ? true : false : false);
            });

            self.checked_F_002.subscribe(function(NewValue: boolean) {
                self.CurrentItemDisplayAtr(NewValue ? 0 : 1);
            });
        }

    }
}