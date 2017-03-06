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
                array.push(new ItemModel(i, '蝓ｺ譛ｬ邨ｦ', "description"));
            }
            this.itemsSwap(array);

            this.columns = ko.observableArray([
                { headerText: '繧ｳ繝ｼ繝・', key: 'code', width: 100 },
                { headerText: '蜷咲ｧｰ', key: 'name', width: 150 }
            ]);
            var x = [];
            x.push(_.cloneDeep(array[0]));
            x.push(_.cloneDeep(array[1]));
            x.push(_.cloneDeep(array[2]));
            this.currentCodeListSwap = ko.observableArray(x);
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
