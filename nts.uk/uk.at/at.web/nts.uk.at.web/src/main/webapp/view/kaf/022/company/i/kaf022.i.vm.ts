module nts.uk.at.view.kmf022.i.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelI {
        selectedI12: KnockoutObservable<number>;
        selectedI13: KnockoutObservable<number>;
        itemListI1_2: KnockoutObservableArray<ItemModel>;

        constructor() {
            const self = this;
            self.selectedI12 = ko.observable(0);
            self.selectedI13 = ko.observable(0);
            self.itemListI1_2 = ko.observableArray([
                new ItemModel(0, text("KAF022_75")),
                new ItemModel(0, text("KAF022_82"))
            ]);


            $("#fixed-table-i").ntsFixedTable({});
        }

        initData(allData: any): void {
            // const self = this;
            // let data = allData.goBack;
            // if (data) {
            //     self.selectedValueF13(data.workChangeFlg);
            // }
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