// TreeGrid Node
module qpp014.i {
    export class ScreenModel {
        //E_LST_003
        items_I_LST_003: KnockoutObservableArray<ItemModel_I_LST_003>;
        columns_I_LST_003: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_I_LST_003: KnockoutObservable<any>;
        constructor() {
            var self = this;
            $('#successful').css('display', 'none');
            $('#stop').css('display', 'none');
            //$('#error').css('display', 'none');
            nts.uk.ui.windows.getSelf().setHeight(570);
            self.items_I_LST_003 = ko.observableArray([]);
            for (let i = 1; i < 14; i++) {
                self.items_I_LST_003.push(new ItemModel_I_LST_003('00' + i, '基本給', "description " + i));
            }
            self.currentCode_I_LST_003 = ko.observable();
        }

        /**
         * close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
    export class ItemModel_I_LST_003 {
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
