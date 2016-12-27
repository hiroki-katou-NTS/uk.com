__viewContext.ready(function () {
    
    class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        columns2: KnockoutObservableArray<any>;
//        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
//        selectedCode: KnockoutObservable<string>;
//        selectedCodes: KnockoutObservableArray<string>;
//        isEnable: KnockoutObservable<boolean>;
//        isRequired: KnockoutObservable<boolean>;
        
        constructor() {
            
            this.items = ko.observableArray([
                new ItemModel('001', '基本給', "description 1", "other1"),
                new ItemModel('150', '役職手当', "description 2", "other2"),
                new ItemModel('ABC', '基12本ghj給', "description 3", "other3")
            ]);
            
            this.columns = ko.observableArray([
                {headerText: 'group1', key: 'group1', group: [
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 150 }]},
                { headerText: '説明', key: 'description', width: 150 },
                
                {headerText: 'group2', group:[{ headerText: '説明1', key: 'other1', width: 150 },
                { headerText: '説明2', key: 'other2', width: 150 }]}
            ]);
            this.columns2 = ko.observableArray([
                {headerText: 'group1', key: 'group1', group: [
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 150 }]},
                { headerText: '説明', key: 'description', width: 150 },
                {headerText: '<button onclick="toggleColumns()">-</button>', group:[{ headerText: '説明1', key: 'other1', width: 150 },
                { headerText: '説明2', key: 'other2', width: 150 }]}
            ]);
            
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            
            
//            self.itemName = ko.observable('');
//            self.currentCode = ko.observable(3);
//            self.selectedCode = ko.observable(null)
//            self.isEnable = ko.observable(true);
//            self.isRequired = ko.observable(true);
//            self.selectedCodes = ko.observableArray([]);
            
//            $('#list-box').on('selectionChanging', function(event) {
//                console.log('Selecting value:' + (<any>event.originalEvent).detail);
//                //Cancel event.
//                //event.preventDefault();
//                //return false;
//            })
//            $('#list-box').on('selectionChanged', function(event: any) {
//                console.log('Selected value:' + (<any>event.originalEvent).detail)
//            })
        }
        
        selectSomeItems() {
            this.currentCode('150');
            this.currentCodeList.removeAll();
            this.currentCodeList.push('001');
            this.currentCodeList.push('ABC');
        }
        
        deselectAll() {
            this.currentCode(null);
            this.currentCodeList.removeAll();
        }

//        addOptions() {
//            var self = this;
//            var newCode = self.currentCode() + 1;
//            var itemCode = newCode.toString();
//            var codeLength = itemCode.length;
//            while (codeLength < 4) {
//                itemCode = '0' + itemCode;
//                codeLength++;
//            }
//            self.itemList.push(new ItemModel(itemCode, self.itemName(), "New Item"));
//            self.currentCode(newCode);
//        }
        
//        deselectAll() {
//            $('#list-box').ntsListBox('deselectAll');
//        }
//        
//        selectAll() {
//            $('#list-box').ntsListBox('selectAll');
//        }
        
        /**
         * Clear options.
         */
//        clearOptions() {
//            this.itemList([]);
//        }
        
        /**
         * Remove item by code;
         */
//        remove() {
//            var self = this;
//            
//            // Remove by code.
//            var selected: ItemModel = self.itemList().filter(item => item.code == self.selectedCode())[0];
//            self.itemList.remove(selected);
//            
//            // Remove by codes
//            var selecteds: ItemModel[] = self.itemList().filter(item => self.selectedCodes().indexOf(item.code) != -1);
//            self.itemList.removeAll(selecteds);
//        }
    }
    
    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;         
        }
    }

    
    this.bind(new ScreenModel());
    
});
var toggle = true;
function toggleColumns() {
    if(toggle) {
        $('#single-list').igGridHiding("hideMultiColumns", ["other1","other2"]);
    } else {
        $('#single-list').igGridHiding("showMultiColumns", ["other1","other2"]);
    }
    toggle = !toggle;
}
