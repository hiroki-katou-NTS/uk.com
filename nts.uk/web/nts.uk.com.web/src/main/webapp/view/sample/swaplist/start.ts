__viewContext.ready(function() {

    class ScreenModel {
        itemsSwap: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
        test: KnockoutObservableArray<any>;

        constructor() {

            this.itemsSwap = ko.observableArray([]);
            
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel(i, '基本給', "description"));
            }
            this.itemsSwap(array);

            this.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 150 }
            ]);

            this.currentCodeListSwap = ko.observableArray([]);
            this.test = ko.observableArray([]);
        }
        
        remove(){
            this.itemsSwap.shift();            
        }
        
    }
    
    class ItemModel {
        code: number;
        name: string;
        description: string;
        deletable: boolean;
        constructor(code: number, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.deletable = code % 3 === 0;
        }
    }


    this.bind(new ScreenModel());

});
