module nts.uk.at.view.kdw001.e.viewmodel {
    export class ScreenModel {
        //combo box 
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        //gridlist
        items: KnockoutObservableArray<Gridlist>;
        columns: KnockoutObservableArray<NtsGridListColumn>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('1'),
                new ItemModel('2'),
                new ItemModel('3')
            ]);

            self.selectedCode = ko.observable('1');
            self.items = ko.observableArray([]);

            for (let i = 1; i < 100; i++) {
                this.items.push(new Gridlist('00' + i, '基本給', "description " + i, i % 3 === 0, "2010/1/1"));
            }
            this.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 150 },
                { headerText: '説明', key: 'description', width: 150 },
                { headerText: '説明1', key: 'other1', width: 150 },
                { headerText: '説明2', key: 'other2', width: 150, isDateColumn: true, format: 'YYYY/MM/DD' }
            ]);
            this.currentCode = ko.observable();
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            service.getImplementationResult().done(function(data) {
                cosole.log(data);
                dfd.resolve(data);
            });

            return dfd.promise();
        }


    }
    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class Gridlist {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        deletable: boolean;
        switchValue: boolean;
        constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
            this.deletable = deletable;
            this.switchValue = ((code % 3) + 1).toString();

        }
    }
} 
