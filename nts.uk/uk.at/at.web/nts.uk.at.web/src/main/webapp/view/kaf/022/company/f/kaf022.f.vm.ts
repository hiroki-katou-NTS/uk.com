module nts.uk.at.view.kaf022.f.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModelF {
        // 勤務情報を反映する
        selectedValueF13: KnockoutObservable<number>;
        itemListF13: KnockoutObservableArray<ItemModel> = ko.observableArray([
            new ItemModel(3, text("KAF022_198")),
            new ItemModel(2, text("KAF022_199")),
            new ItemModel(1, text("KAF022_200")),
            new ItemModel(0, text("KAF022_201"))
        ]);

        constructor() {
            const self = this;
            self.selectedValueF13 = ko.observable(0);

            $("#fixed-table-f").ntsFixedTable({});
        }

        initData(allData: any): void {
            const self = this;
            let data = allData.goBackReflect;
            if (data) {
                self.selectedValueF13(data.reflectApplication);
            }
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