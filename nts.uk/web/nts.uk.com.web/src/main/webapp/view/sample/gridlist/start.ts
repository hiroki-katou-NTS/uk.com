module nts.uk.ui.gridlist {
    __viewContext.ready(function () {
    
        class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            
            constructor() {
                
                this.items = ko.observableArray([]);
                
                for(let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i%3 === 0, "other" + i));
                }
                
                this.columns = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true },
                    { headerText: '名称', key: 'name', width: 150, hidden: true },
                    { headerText: '説明', key: 'description', width: 150 },
                    { headerText: '説明1', key: 'other1', width: 150 },
                    { headerText: '説明2', key: 'other2', width: 150 }
                ]);
                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100 },
                    { headerText: '名称', key: 'name', width: 150 },
                    { headerText: '説明', key: 'description', width: 150 },
                    { headerText: '説明1', key: 'other1', width: 150 },
                    { headerText: '説明2', key: 'other2', width: 150 }
                ]);
                
                this.currentCode = ko.observable();
                this.currentCodeList = ko.observableArray([]);
                // Fire event.
                $("#multi-list").on('itemDeleted', (function(e: Event) {
                    alert("Item is deleted in multi grid is " + e["detail"]["target"]);
                }));
                
                $("#single-list").on('itemDeleted', (function(e: Event) {
                    alert("Item is deleted in single grid is " + e["detail"]["target"]);
                }));   
            }
            
            selectSomeItems() {
                this.currentCode('0010');
                this.currentCodeList.removeAll();
                this.currentCodeList.push('001');
                this.currentCodeList.push('002');
            }
            
            deselectAll() {
                this.currentCode(null);
                this.currentCodeList.removeAll();
            }
            
            addItem() {
                this.items.push(new ItemModel(this.count.toString(), '基本給', "description " + this.count, true, "other " + this.count));
                this.count++;
            }
            
            removeItem() {
                this.items.shift();
            }
            
            addDeleteButton() {
                $("#multi-list").ntsGridList("setupDeleteButton", {deleteField: "deletable", sourceTarget: this.items});
            }
    
        }
        
        class ItemModel {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            deletable: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;    
                this.deletable = deletable;     
            }
        }
        
        this.bind(new ScreenModel());
    });
}