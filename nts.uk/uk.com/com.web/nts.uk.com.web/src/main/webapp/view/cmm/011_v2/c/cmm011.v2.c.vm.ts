module nts.uk.com.view.cmm011.v2.c.viewmodel {
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;

        inputList: KnockoutObservableArray<inputItems>;
        listColums: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, getText('CMM011-1_8')),
                new BoxModel(2, getText('CMM011-1_9'))
            ]);
            self.selectedId = ko.observable(1);

            this.inputList = ko.observableArray([]);
            this.listColums = ko.observableArray([
                { headerText: getText('CMM011-0_30'), key: 'code', width: 100},
                { headerText: getText('CMM011-0_31'), key: 'name', width: 100},
                { headerText: getText('CMM011-0_32'), key: 'date', width: 150 }
            ]);
            this.currentCode = ko.observable();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            dfd.resolve();
            return dfd.promise();
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    class inputItems {
        code: string;
        name: string;
        date: string;
        constructor(code: string, name: string, date: string) {
            this.code = code;
            this.name = name;
            this.date = date;
        }
    }

}