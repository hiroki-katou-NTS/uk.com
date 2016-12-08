__viewContext.ready(function () {
    class ScreenModel {
        boxes: KnockoutObservableArray<BoxModel>;
        selectedBoxes: KnockoutObservableArray<boolean>;
        
        constructor() {
            var self = this;
            self.boxes = ko.observableArray([
                new BoxModel(1, 'box 1'), new BoxModel(2, 'box 2'), new BoxModel(3, 'box 3')
            ]);
            self.selectedBoxes = ko.observableArray([]);
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