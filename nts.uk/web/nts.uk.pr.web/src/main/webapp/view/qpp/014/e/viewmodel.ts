// TreeGrid Node
module qpp014.e {
    export class ScreenModel {
        //gridview
        //E_LST_001
        items_E_LST_001: KnockoutObservableArray<ItemModel_E_LST_001>;
        columns_E_LST_001: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_E_LST_001: KnockoutObservable<any>;
        //E_LST_002
        items_E_LST_002: KnockoutObservableArray<ItemModel_E_LST_002>;
        columns_E_LST_002: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_E_LST_002: KnockoutObservable<any>;
        //E_LST_003
        items_E_LST_003: KnockoutObservableArray<ItemModel_E_LST_003>;
        columns_E_LST_003: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_E_LST_003: KnockoutObservable<any>;
        constructor() {
            var self = this;
            //gridview
            //E_LST_001
            self.items_E_LST_001 = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.items_E_LST_001.push(new ItemModel_E_LST_001('00' + i, '蝓ｺ譛ｬ邨ｦ', "description " + i, "other" + i));
            }
            self.columns_E_LST_001 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 },
                { headerText: '説明', prop: 'description', width: 200 }
            ]);
            self.currentCode_E_LST_001 = ko.observable();
            //E_LST_002
            self.items_E_LST_002 = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.items_E_LST_002.push(new ItemModel_E_LST_002('00' + i, '蝓ｺ譛ｬ邨ｦ', "description " + i, "other" + i));
            }
            self.columns_E_LST_002 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 },
                { headerText: '説明', prop: 'description', width: 200 }
            ]);
            self.currentCode_E_LST_002 = ko.observable();
            //E_LST_003
            self.items_E_LST_003 = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.items_E_LST_003.push(new ItemModel_E_LST_003('00' + i, '蝓ｺ譛ｬ邨ｦ', "description " + i, "other" + i));
            }
            self.columns_E_LST_003 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 },
                { headerText: '説明', prop: 'description', width: 200 }
            ]);
            self.currentCode_E_LST_003 = ko.observable();
        }
    }
    export class ItemModel_E_LST_001 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
    export class ItemModel_E_LST_002 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }
    export class ItemModel_E_LST_003 {
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
