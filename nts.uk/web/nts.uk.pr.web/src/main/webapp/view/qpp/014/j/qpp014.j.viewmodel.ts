// TreeGrid Node
module qpp014.j {
    export class ScreenModel {
        //radiogroup
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
        currentCode_J_LST_001: KnockoutObservable<any>;
        currentCode_J_SEL_004: KnockoutObservable<any>;
        
        constructor() {
            let self = this;
            self.selectedId_J_SEL_001 = ko.observable(1);
            self.itemList_J_SEL_002 = ko.observableArray([
                new ItemModel_J_SEL_002('��{��1', '��{��'),
                new ItemModel_J_SEL_002('��{��2', '��E�蓖'),
                new ItemModel_J_SEL_002('0003', '��{��')
            ]);
            self.selectedCode_J_SEL_002 = ko.observable('0002')
            self.itemList_J_SEL_003 = ko.observableArray([
                new ItemModel_J_SEL_003('��{��1', '��{��'),
                new ItemModel_J_SEL_003('��{��2', '��E�蓖'),
                new ItemModel_J_SEL_003('0003', '��{��')
            ]);
            self.selectedCode_J_SEL_003 = ko.observable('0002');
            //gridview
            //LST_001
            self.items_J_LST_001 = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.items_J_LST_001.push(new ItemModel_J_LST_001('00' + i, '基本給', "description " + i));
            }
            self.currentCode_J_LST_001 = ko.observable();
            self.currentCode_J_SEL_004 = ko.observable(1);
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
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
