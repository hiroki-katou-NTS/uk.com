// TreeGrid Node
module qpp014.a {

    export class ScreenModel {
        //combobox
        //A_SEL_001
        itemList_A_SEL_001: KnockoutObservableArray<ItemModel_A_SEL_001>;
        selectedCode_A_SEL_001: KnockoutObservable<string>;
        constructor() {
            let self = this;

            //combobox
            //G_SEL_001
            self.itemList_A_SEL_001 = ko.observableArray([
                new ItemModel_A_SEL_001('基本給1', '基本給'),
                new ItemModel_A_SEL_001('基本給2', '役職手当'),
                new ItemModel_A_SEL_001('0003', '基本給')
            ]);
            self.selectedCode_A_SEL_001 = ko.observable('0003');

        }
    }
    export class ItemModel_A_SEL_001 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

};
