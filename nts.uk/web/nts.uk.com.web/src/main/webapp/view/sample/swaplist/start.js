__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.itemsSwap = ko.observableArray([]);
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel("test" + i, '基本給', "description"));
            }
            this.itemsSwap(array);
            this.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100 },
                { headerText: '名称', key: 'name', width: 150 }
            ]);
            this.currentCodeListSwap = ko.observableArray([]);
            this.test = ko.observableArray([]);
        }
        ScreenModel.prototype.remove = function () {
            this.itemsSwap.shift();
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
