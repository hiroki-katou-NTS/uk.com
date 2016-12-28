__viewContext.ready(function () {
    
    class ScreenModel {
        itemsSwap: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
        textArea: KnockoutObservable<any>;
        divValue: KnockoutObservable<any>;
        
        constructor() {
            
            this.itemsSwap = ko.observableArray([
                new ItemModel('001', '基本給', "description 1"),
                new ItemModel('150', '役職手当', "description 2"),
                new ItemModel('ABC', '基12本ghj給', "description 3")
            ]);
            
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 200 },
                { headerText: '説明', prop: 'description', width: 200 }
            ]);
            this.textArea = ko.observable("");
            this.divValue = ko.observable("");
            $("#input-text").keypress((event) => {
                    alert(event.key);
            });
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
