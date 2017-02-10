// TreeGrid Node
module qpp014.j {
    export class ScreenModel {
        //radiogroup
        itemList_J_SEL_001: KnockoutObservableArray<any>;
        selectedId_J_SEL_001: KnockoutObservable<number>;
        //combobox
        //J_SEL_002
        itemList_J_SEL_002: KnockoutObservableArray<ItemModel_J_SEL_002>;
        selectedCode_J_SEL_002: KnockoutObservable<string>;
        //J_SEL_003
        itemList_J_SEL_003: KnockoutObservableArray<ItemModel_J_SEL_003>;
        selectedCode_J_SEL_003: KnockoutObservable<string>;
        //gridview
        items_J_LST_001: KnockoutObservableArray<ItemModel_J_LST_001>;
        columns_J_LST_001: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_J_LST_001: KnockoutObservable<any>;
        constructor() {
            let self = this;
            //radiogroup
            self.itemList_J_SEL_001 = ko.observableArray([
                new BoxModel_J_SEL_001(1, 'box 1'),
                new BoxModel_J_SEL_001(2, 'box 2')
            ]);
            self.selectedId_J_SEL_001 = ko.observable(1);
            //combobox
            //J_SEL_002
            self.itemList_J_SEL_002 = ko.observableArray([
                new ItemModel_J_SEL_002('基本給1', '基本給'),
                new ItemModel_J_SEL_002('基本給2', '役職手当'),
                new ItemModel_J_SEL_002('0003', '基本給')
            ]);
            self.selectedCode_J_SEL_002 = ko.observable('0002')
            //J_SEL_003
            self.itemList_J_SEL_003 = ko.observableArray([
                new ItemModel_J_SEL_003('基本給1', '基本給'),
                new ItemModel_J_SEL_003('基本給2', '役職手当'),
                new ItemModel_J_SEL_003('0003', '基本給')
            ]);
            self.selectedCode_J_SEL_003 = ko.observable('0002');
            //gridview
            //LST_001
            self.items_J_LST_001 = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.items_J_LST_001.push(new ItemModel_J_LST_001('00' + i, '蝓ｺ譛ｬ邨ｦ', "description " + i, "other" + i));
            }
            self.columns_J_LST_001 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 },
                { headerText: '説明', prop: 'description', width: 200 }
            ]);
            self.currentCode_J_LST_001 = ko.observable();
        }
    }
    export class BoxModel_J_SEL_001 {
        id: number;
        name: string;
        constructor(id, name) {
            let self = this;
            self.id = id;
            self.name = name;
        }
    }
    export class ItemModel_J_SEL_002 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class ItemModel_J_SEL_003 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class ItemModel_J_LST_001 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
};
