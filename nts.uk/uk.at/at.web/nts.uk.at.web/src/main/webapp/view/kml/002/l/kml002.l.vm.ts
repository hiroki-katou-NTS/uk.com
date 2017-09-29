module kml002.l.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG013_76"), prop: 'webMenuCode', key: 'webMenuCode', width: 55 },
                { headerText: nts.uk.resource.getText("CCG013_77"), prop: 'webMenuName', key: 'webMenuName', width: 167 },
                { headerText: 'pk', prop: 'primaryKey', key: 'primaryKey', width: 1, hidden: true }
            ]);
            self.currentCodeList = ko.observableArray([]);
        }
        start() {
            var self = this,
                dfd = $.Deferred();
            dfd.resolve();

            return dfd.promise();
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }

    export module model {
    }
    interface IPersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate?: number;
    }

    class PersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.personId = param.personId;
            this.code = param.code;
            this.name = param.name;
            this.baseDate = param.baseDate || 20170104;
        }
    }
    
     export class ItemModel {
        primaryKey: string;
        webMenuCode: string;
        webMenuName: string;
        order: number;


        constructor(id: string, webMenuCode: string, webMenuName: string, order: number) {
            this.primaryKey = webMenuCode + order;
            this.webMenuCode = webMenuCode;
            this.webMenuName = webMenuName;
            this.order = order;

        }
    }

}