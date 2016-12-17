__viewContext.ready(function () {
    class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedValue: KnockoutObservable<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        count: any = 4;
        
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([]);
            for (let i = 1; i < 10; i++) {
                self.itemList.push(new BoxModel(i, 'box ' + i));
            }
            self.selectedValue = ko.observable(new BoxModel(3, 'box 3'));
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
        }
        
        addBoxes() {
            var self = this;
            self.itemList.push(new BoxModel(self.count, 'box ' + self.count));
            self.count++;
        }
        
        removeBoxes() {
            var self = this;
            self.itemList.pop();
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
    
    this.bind(new ScreenModel());
    
});