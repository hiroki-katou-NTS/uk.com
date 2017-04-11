__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.itemsSwap = ko.observableArray([]);
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel(i, '蝓ｺ譛ｬ邨ｦ', "description"));
            }
            this.itemsSwap(array);
            this.columns = ko.observableArray([
                { headerText: '繧ｳ繝ｼ繝・', key: 'code', width: 100 },
                { headerText: '蜷咲ｧｰ', key: 'name', width: 150 }
            ]);
            var x = [];
            x.push(_.cloneDeep(array[0]));
            x.push(_.cloneDeep(array[1]));
            x.push(_.cloneDeep(array[2]));
            this.currentCodeListSwap = ko.observableArray(x);
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
            this.deletable = code % 3 === 0;
        }
        return ItemModel;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map