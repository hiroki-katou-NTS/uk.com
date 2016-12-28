__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.itemsSwap = ko.observableArray([
                new ItemModel('001', '基本給', "description 1"),
                new ItemModel('150', '役職手当', "description 2"),
                new ItemModel('ABC', '基12本ghj給', "description 3")
            ]);
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 200 },
                { headerText: '説明', prop: 'description', width: 200 }
            ]);
            this.textArea = ko.observable("");
            this.divValue = ko.observable("");
            $("#input-text").keypress(function (event) {
                alert(event.key);
            });
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
