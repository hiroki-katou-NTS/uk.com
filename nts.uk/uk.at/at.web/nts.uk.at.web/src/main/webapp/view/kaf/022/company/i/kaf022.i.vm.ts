module nts.uk.at.view.kaf022.i.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelI {
        lateEarlyClearAlarmAtr: KnockoutObservable<number>;
        lateEarlyCancelAtr: KnockoutObservable<number>;
        itemListI1_2: KnockoutObservableArray<ItemModel>;
        itemListI1_3: KnockoutObservableArray<ItemModel> = ko.observableArray([
            new ItemModel(0, text("KAF022_671")),
            new ItemModel(1, text("KAF022_672")),
            new ItemModel(2, text("KAF022_673"))
        ]);

        constructor() {
            const self = this;
            self.lateEarlyClearAlarmAtr = ko.observable(0);
            self.lateEarlyCancelAtr = ko.observable(0);
            self.itemListI1_2 = ko.observableArray([
                new ItemModel(1, text("KAF022_75")),
                new ItemModel(0, text("KAF022_82"))
            ]);


            $("#fixed-table-i").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            self.lateEarlyCancelAtr(allData.lateEarlyCancelAtr || 0);
            self.lateEarlyClearAlarmAtr(allData.lateEarlyClearAlarmAtr || 0);
        }

    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}