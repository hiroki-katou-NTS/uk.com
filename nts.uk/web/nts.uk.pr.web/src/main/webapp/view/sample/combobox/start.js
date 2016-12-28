__viewContext.ready(function () {
    var ScreenModel = (function () {
        /**
         * Constructor.
         */
        function ScreenModel() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('基本給1', '基本給'),
                new ItemModel('基本給2', '役職手当'),
                new ItemModel('0003', '基本給')
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable('0003');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
        }
        /**
         * Add options.
         */
        ScreenModel.prototype.addOptions = function () {
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
        };
        /**
         * Clear options.
         */
        ScreenModel.prototype.clearOptions = function () {
            this.itemList([]);
        };
        /**
         * Remove item by code;
         */
        ScreenModel.prototype.removeByCode = function () {
            var self = this;
            var selected = self.itemList().filter(function (item) { return item.code == self.selectedCode(); })[0];
            self.itemList.remove(selected);
        };
        return ScreenModel;
    }());
    ;
    var ItemModel = (function () {
        function ItemModel(code, name) {
            this.code = code;
            this.name = name;
        }
        return ItemModel;
    }());
    this.bind(new ScreenModel());
});
