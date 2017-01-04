__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            //radiogroup data
            self.RadioItemList = ko.observableArray([
                new BoxModel(1, 'box 1'),
                new BoxModel(2, 'box 2')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            //combobox data
            self.ComboBoxItemList = ko.observableArray([
                new ComboboxItemModel('0001', 'Item1'),
                new ComboboxItemModel('0002', 'Item2'),
                new ComboboxItemModel('0003', 'Item3')
            ]);
            self.ComboBoxCurrentCode = ko.observable(1);
            self.selectedCode = ko.observable('0001');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //gridlist data
            this.items = ko.observableArray([
                new GridItemModel('001', '基本給'),
                new GridItemModel('150', '役職手当'),
                new GridItemModel('ABC', '基12本ghj給')
            ]);
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 150 }
            ]);
            this.gridListCurrentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
        }
        return ScreenModel;
    }());
    var BoxModel = (function () {
        function BoxModel(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
        return BoxModel;
    }());
    var ComboboxItemModel = (function () {
        function ComboboxItemModel(code, name) {
            this.code = code;
            this.name = name;
        }
        return ComboboxItemModel;
    }());
    var GridItemModel = (function () {
        function GridItemModel(code, name, description) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
        return GridItemModel;
    }());
    this.bind(new ScreenModel());
});
