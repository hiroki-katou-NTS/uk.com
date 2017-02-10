// TreeGrid Node
var qpp014;
(function (qpp014) {
    var e;
    (function (e) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //gridview
                //E_LST_001
                self.items_E_LST_001 = ko.observableArray([]);
                for (var i = 1; i < 100; i++) {
                    self.items_E_LST_001.push(new ItemModel_E_LST_001('00' + i, '基本給', "description " + i, "other" + i));
                }
                self.columns_E_LST_001 = ko.observableArray([
                    { headerText: '�R�[�h', prop: 'code', width: 100 },
                    { headerText: '����', prop: 'name', width: 150 },
                    { headerText: '����', prop: 'description', width: 200 }
                ]);
                self.currentCode_E_LST_001 = ko.observable();
                //E_LST_002
                self.items_E_LST_002 = ko.observableArray([]);
                for (var i = 1; i < 100; i++) {
                    self.items_E_LST_002.push(new ItemModel_E_LST_002('00' + i, '基本給', "description " + i, "other" + i));
                }
                self.columns_E_LST_002 = ko.observableArray([
                    { headerText: '�R�[�h', prop: 'code', width: 100 },
                    { headerText: '����', prop: 'name', width: 150 },
                    { headerText: '����', prop: 'description', width: 200 }
                ]);
                self.currentCode_E_LST_002 = ko.observable();
                //E_LST_003
                self.items_E_LST_003 = ko.observableArray([]);
                for (var i = 1; i < 100; i++) {
                    self.items_E_LST_003.push(new ItemModel_E_LST_003('00' + i, '基本給', "description " + i, "other" + i));
                }
                self.columns_E_LST_003 = ko.observableArray([
                    { headerText: '�R�[�h', prop: 'code', width: 100 },
                    { headerText: '����', prop: 'name', width: 150 },
                    { headerText: '����', prop: 'description', width: 200 }
                ]);
                self.currentCode_E_LST_003 = ko.observable();
            }
            return ScreenModel;
        }());
        e.ScreenModel = ScreenModel;
        var ItemModel_E_LST_001 = (function () {
            function ItemModel_E_LST_001(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_E_LST_001;
        }());
        e.ItemModel_E_LST_001 = ItemModel_E_LST_001;
        var ItemModel_E_LST_002 = (function () {
            function ItemModel_E_LST_002(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_E_LST_002;
        }());
        e.ItemModel_E_LST_002 = ItemModel_E_LST_002;
        var ItemModel_E_LST_003 = (function () {
            function ItemModel_E_LST_003(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_E_LST_003;
        }());
        e.ItemModel_E_LST_003 = ItemModel_E_LST_003;
    })(e = qpp014.e || (qpp014.e = {}));
})(qpp014 || (qpp014 = {}));
;
