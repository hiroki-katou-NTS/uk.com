// TreeGrid Node
module qpp014.e {
    export class ScreenModel {
        //E_LST_003
        items_E_LST_003: KnockoutObservableArray<ItemModel_E_LST_003>;
        columns_E_LST_003: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_E_LST_003: KnockoutObservable<any>;
        constructor() {
            var self = this;
            self.items_E_LST_003 = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                self.items_E_LST_003.push(new ItemModel_E_LST_003('00' + i, '基本給', "description " + i));
            }
            self.currentCode_E_LST_003 = ko.observable();
            //$("#list3-div").css("display","none");
            $("#E_BTN_004").css("display","none");
            //$("#E_BTN_005").css("display","none");$("E_BTN_002")
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
