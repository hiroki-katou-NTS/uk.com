__viewContext.ready(function () {
    class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        selectedValues: KnockoutObservableArray<any>;
        count: any = 4;
        
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, 'box 1'),
                new BoxModel(2, 'box 2'),
                new BoxModel(3, 'box 3')
            ]);
            self.selectedValues = ko.observableArray([
                {id: 1, name: 'box 1'},
                new BoxModel(3, 'box 3')
            ]);
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