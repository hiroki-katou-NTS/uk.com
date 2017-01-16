__viewContext.ready(function() {

    class ScreenModel {
        itemsSwap: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;

        constructor() {

            this.itemsSwap = ko.observableArray([]);
            
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel("test" + i, '基本給', "description"));
            }
            this.itemsSwap(array);

            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 80 },
                { headerText: '名称', prop: 'name', width: 120 }
            ]);

            this.currentCodeListSwap = ko.observableArray([]);
        }
        
    }
    
    class ItemModel {
        code: string;
        name: string;
        description: string;
        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }


    this.bind(new ScreenModel());

});
