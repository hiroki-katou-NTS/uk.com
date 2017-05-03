module kml001.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns2: KnockoutObservableArray<NtsGridListColumn>;
        constructor() {
            this.items = ko.observableArray([]);
            
            for(let i = 1; i < 100; i++) {
                this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i%3 === 0, "2010/1/1"));
            }
            
            this.columns2 = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100, hidden: true },
                { headerText: '名称', key: 'name', width: 150 },
                { headerText: '説明', key: 'description', width: 150 },
                { headerText: '説明1', key: 'other1', width: 150 }, 
                { headerText: '説明2', key: 'other2', width: 150 },
                { headerText: 'Switch', key: 'switchValue', width: 300, controlType: 'switch' }
            ]);
            this.switchOptions = ko.observableArray([
                { code: "1", name: '四捨五入' },
                { code: "2", name: '切り上げ' },
                { code: "3", name: '切り捨て' }
            ]);
            this.currentCode = ko.observable();
        }
        
    }
    class ItemModel {
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