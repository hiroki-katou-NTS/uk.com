__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.items = ko.observableArray([]);
            for (var i = 1; i < 100; i++) {
                this.items.push(new ItemModel('00' + i, '基本給', "description " + i, "other" + i));
            }
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100, hidden: true },
                { headerText: '名称', prop: 'name', width: 150, hidden: true },
                { headerText: '説明', prop: 'description', width: 150 },
                { headerText: '説明1', prop: 'other1', width: 150 },
                { headerText: '説明2', prop: 'other2', width: 150 }
            ]);
            this.columns2 = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 },
                { headerText: '説明', prop: 'description', width: 150 },
                { headerText: '説明1', prop: 'other1', width: 150 },
                { headerText: '説明2', prop: 'other2', width: 150 }
            ]);
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
        }
        ScreenModel.prototype.selectSomeItems = function () {
            this.currentCode('150');
            this.currentCodeList.removeAll();
            this.currentCodeList.push('001');
            this.currentCodeList.push('002');
        };
        ScreenModel.prototype.deselectAll = function () {
            this.currentCode(null);
            this.currentCodeList.removeAll();
        };
        ScreenModel.prototype.addItem = function () {
            this.items.push(new ItemModel('999', '基本給', "description 1", "other1"));
        };
        ScreenModel.prototype.removeItem = function () {
            this.items.shift();
        };
        return ScreenModel;
    }());
    var ItemModel = (function () {
        function ItemModel(code, name, description, other1, other2) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
        }
        return ItemModel;
    }());
    this.bind(new ScreenModel());
});
