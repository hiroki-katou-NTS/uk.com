__viewContext.ready(function () {
    class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        isMulti: KnockoutObservable<boolean>;
        isMulti2: KnockoutObservable<boolean>;
        isValidate: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            var temp = [];
            for(var i = 0; i < 300; i++){
                temp.push(new ItemModel('基本給' + (i + 1), '基本給', "description " + (i + 1)));
            }
            self.itemList = ko.observableArray(temp);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.isEnable = ko.observable(true);
            self.isMulti = ko.observable(true);
            self.isMulti2 = ko.observable(true);
            self.isValidate = ko.observable(true);
            
            $('#list-box').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
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
            self.itemList.push(new ItemModel(itemCode, self.itemName(), ""));
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