module nts.uk.com.view.kal003.c.viewmodel {


    export class ScreenModel {
        listItems: KnockoutObservableArray<item>;
        selected: KnockoutObservable<string>;
        simpleValue: KnockoutObservable<string>;
        
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            
            self.itemList = ko.observableArray([
                new BoxModel(1, 'box 1'),
                new BoxModel(2, 'box 2'),
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            
            self.selected = ko.observable('1');
            self.simpleValue = ko.observable('test');
            self.listItems = ko.observableArray([
                new item('1', 'test', 'test'),
                new item('2', 'test2', 'test2')
            ]);
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

    }
}
class BoxModel {
    id: number;
    name: string;
    constructor(id, name){
        var self = this;
        self.id = id;
        self.name = name;
    }
}
class item {
    value: string;
    name: string;
    description: string;

    constructor(value: string, name: string, description: string) {
        this.value = value;
        this.name = name;
        this.description = description;
    }
}

