__viewContext.ready(function () {
    class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('基本給2', '基本給', "description 1"),
                new ItemModel('基本給1', '役職手当', "description 2"),
                new ItemModel('基本給3', '基本給', "description 3")
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable(null)
            self.isEnable = ko.observable(true);
            self.selectedCodes = ko.observableArray([]);
            
            $('#list-box').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
                //Cancel event.
                //event.preventDefault();
                //return false;
            })
            $('#list-box').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
        }

        addOptions() {
            var self = this;
            var newCode = self.currentCode() + 1;
            var itemCode = newCode.toString();
            var codeLength = itemCode.length;
            while (codeLength < 4) {
                itemCode = '0' + itemCode;
                codeLength++;
            }
            self.itemList.push(new ItemModel(itemCode, self.itemName(), "New Item"));
            self.currentCode(newCode);
        }
        
        deselectAll() {
            $('#list-box').ntsListBox('deselectAll');
        }
        
        selectAll() {
            $('#list-box').ntsListBox('selectAll');
        }
        
        /**
         * Clear options.
         */
        clearOptions() {
            this.itemList([]);
        }
        
        /**
         * Remove item by code;
         */
        remove() {
            var self = this;
            
            // Remove by code.
            var selected: ItemModel = self.itemList().filter(item => item.code == self.selectedCode())[0];
            self.itemList.remove(selected);
            
            // Remove by codes
            var selecteds: ItemModel[] = self.itemList().filter(item => self.selectedCodes().indexOf(item.code) != -1);
            self.itemList.removeAll(selecteds);
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