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
                new ItemModel('ABC', '基12本ghj給', "description 3"),
                new ItemModel('002', '基本給', "description 4"),
                new ItemModel('153', '役職手当', "description 5"),
                new ItemModel('AB4', '基12本ghj給', "description 6"),
                new ItemModel('003', '基本給', "description 7"),
                new ItemModel('155', '役職手当', "description 8"),
                new ItemModel('AB5', '基12本ghj給', "description 9")
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
