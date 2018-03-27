module nts.uk.at.view.kaf018.a.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModel {
        targets: Array<model.ItemModel>;
        selectTarget: number;
        items: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        selectedValue1: KnockoutObservable<boolean>;
        selectedValue2: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.targets = [
                new model.ItemModel(0, 'Anh'),
                new model.ItemModel(1, 'Duc'),
                new model.ItemModel(2, 'Tuan'),
                new model.ItemModel(3, 'Anh'),
                new model.ItemModel(4, 'Anh'),
                
            ];
            self.selectTarget = 1;
            self.items = ko.observableArray([
            new model.ItemModel(0, text('KAF018_12')),
            new model.ItemModel(1, text('KAF018_13'))
            ]);
            self.enable = ko.observable(true);
            self.selectedValue1 = ko.observable(true);
            self.selectedValue2 = ko.observable(false);
        }
    
        gotoH() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/kaf/018/h/index.xhtml");
        }
        
        gotoB() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/kaf/018/b/index.xhtml");
        }
    }
    
    export module model {
        export class ItemModel {
            code: number;
            name: string;
    
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }   
    }
}