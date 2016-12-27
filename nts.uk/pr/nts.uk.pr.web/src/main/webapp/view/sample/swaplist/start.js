__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
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
            this.currentCodeListSwap = ko.observableArray([]);
        }
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
