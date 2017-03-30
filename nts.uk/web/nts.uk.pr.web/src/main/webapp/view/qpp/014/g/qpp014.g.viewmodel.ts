// TreeGrid Node
module qpp014.g.viewmodel {
    export class ScreenModel {

        //viewmodel G
        g_INP_001: KnockoutObservable<Date>;
        g_SEL_001_items: KnockoutObservableArray<ItemModel_G_SEL_001>;
        g_SEL_001_itemSelected: KnockoutObservable<any>;
        g_SEL_002_items: KnockoutObservableArray<ItemModel_G_SEL_002>;
        g_SEL_002_itemSelected: KnockoutObservable<any>;
        g_INP_002: any;
        g_SEL_003_itemSelected: KnockoutObservable<any>;

        constructor() {
            let self = this;
            self.g_INP_001 = ko.observable(new Date('2016/12/01'));
            self.g_SEL_001_items = ko.observableArray([
                new ItemModel_G_SEL_001('��{��1', '��{��'),
                new ItemModel_G_SEL_001('��{��2', '��E�蓖'),
                new ItemModel_G_SEL_001('0003', '��{��')
            ]);
            self.g_SEL_001_itemSelected = ko.observable('0002');
            self.g_SEL_002_items = ko.observableArray([
                new ItemModel_G_SEL_002('��{��1', '��{��'),
                new ItemModel_G_SEL_002('��{��2', '��E�蓖'),
                new ItemModel_G_SEL_002('0003', '��{��')
            ]);
            self.g_SEL_002_itemSelected = ko.observable('0002');
            self.g_INP_002 = {
                value: ko.observable(12)
            };
            self.g_SEL_003_itemSelected = ko.observable('0002');
        
        }
      
    }

   
    
    export class ItemModel_G_SEL_001 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class ItemModel_G_SEL_002 {
        code: string;
        name: string;
        label: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    

};
