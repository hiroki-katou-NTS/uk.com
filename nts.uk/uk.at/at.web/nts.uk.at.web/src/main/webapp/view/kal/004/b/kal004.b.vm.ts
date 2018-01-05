module nts.uk.com.view.kal004.b.viewmodel {


    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
        selectMonth: KnockoutObservable<boolean>;
        strSelected: KnockoutObservable<any>;
        endSelected: KnockoutObservable<any>;
        txtDay: KnockoutObservable<string>;
        txtMonth: KnockoutObservable<string>;
        
        
        //Combo Box
        listItems: KnockoutObservableArray<item>;
        selected: KnockoutObservable<string>;
        simpleValue: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            self.enable = ko.observable(true);
            self.selectMonth = ko.observable(true);
            self.strSelected = ko.observable(0);
            self.endSelected = ko.observable(0);
            self.txtDay = ko.observable(resource.getText('KAL004_32'));
            self.txtMonth = ko.observable(resource.getText('KAL004_32'));
            
            // combo box
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
        test(): void {
            console.log("success!");
            nts.uk.ui.windows.sub.modal("../004/b/index.xhtml").onClosed(() => {
                console.log("success!");
            });  
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
        var self = this;
        self.value = value;
        self.name = name;
        self.description = description;
    }
}

