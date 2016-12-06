__viewContext.ready(function () {
    class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        /**
         * Constructor.
         */
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('基本給1', '基本給'),
                new ItemModel('基本給2', '役職手当'),
                new ItemModel('0003', '基本給')
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable('0002')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
        }

        /**
         * Add options.
         */
        addOptions() {
            var self = this;
            var newCode = self.currentCode() + 1;
            var itemCode = newCode.toString();
            var codeLength = itemCode.length;
            while (codeLength < 4) {
                itemCode = '0' + itemCode;
                codeLength++;
            }
            self.itemList.push(new ItemModel(itemCode, self.itemName()));
            self.currentCode(newCode);
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
        removeByCode() {
            var self = this;
            var selected: ItemModel = self.itemList().filter(item => item.code == self.selectedCode())[0];
            self.itemList.remove(selected);
        }
    };
    
    class ItemModel {
        code: string;
        name: string;
        label: string;
        
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }


    this.bind(new ScreenModel());
    
});