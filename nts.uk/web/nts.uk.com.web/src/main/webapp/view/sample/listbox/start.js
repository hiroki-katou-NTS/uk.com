__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            var temp = [];
            for (var i = 0; i < 100; i++) {
                temp.push(new ItemModel('基本給' + (i + 1), '基本給', "description " + (i + 1)));
            }
            self.itemList = ko.observableArray(temp);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.isEnable = ko.observable(true);
            $('#list-box').on('selectionChanging', function (event) {
                console.log('Selecting value:' + event.originalEvent.detail);
            });
            $('#list-box').on('selectionChanged', function (event) {
                console.log('Selected value:' + event.originalEvent.detail);
            });
        }
        ScreenModel.prototype.addOptions = function () {
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
        };
        ScreenModel.prototype.deselectAll = function () {
            $('#list-box').ntsListBox('deselectAll');
        };
        ScreenModel.prototype.selectAll = function () {
            $('#list-box').ntsListBox('selectAll');
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
        ScreenModel.prototype.remove = function () {
            var self = this;
            // Remove by code.
            var selected = self.itemList().filter(function (item) { return item.code == self.selectedCode(); })[0];
            self.itemList.remove(selected);
            // Remove by codes
            var selecteds = self.itemList().filter(function (item) { return self.selectedCodes().indexOf(item.code) != -1; });
            self.itemList.removeAll(selecteds);
        };
        return ScreenModel;
    }());
    var ItemModel = (function () {
        function ItemModel(code, name, description) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
        return ItemModel;
    }());
    this.bind(new ScreenModel());
});
