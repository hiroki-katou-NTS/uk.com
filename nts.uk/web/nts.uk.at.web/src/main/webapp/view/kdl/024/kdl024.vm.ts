module kdl024.a.viewmodel {
    export class ScreenModel {
        contraint: Array<string>;
        items: KnockoutObservableArray<Item>;
        constructor() {
            var self = this;
            self.constraint = ['ResidenceCode', 'PersonId'];
            //            for (let i = 1; i < 100; i++) {
            //                this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0, "other" + i));
            //            }
            self.items = ko.observableArray([]);
            self.start();
        }
        //start
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();


            return dfd.promise();
        }
    }

    //item 
    class Item {
        code: string;
        name: string;
        attribute: number;
        unit: number;
        constructor(code: string, name: string, attribute: number, unit: number) {
            this.code = code;
            this.name = name;
            this.attribute = attribute;
            this.unit = unit;
        }
    }

}