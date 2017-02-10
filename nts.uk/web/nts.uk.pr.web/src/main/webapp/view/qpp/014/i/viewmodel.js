// TreeGrid Node
var qpp014;
(function (qpp014) {
    var i;
    (function (i_1) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //gridview
                //I_LST_001
                self.items_I_LST_001 = ko.observableArray([]);
                for (var i_2 = 1; i_2 < 100; i_2++) {
                    self.items_I_LST_001.push(new ItemModel_I_LST_001('00' + i_2, '基本給', "description " + i_2, "other" + i_2));
                }
                self.columns_I_LST_001 = ko.observableArray([
                    { headerText: '�R�[�h', prop: 'code', width: 100 },
                    { headerText: '����', prop: 'name', width: 150 },
                    { headerText: '����', prop: 'description', width: 200 }
                ]);
                self.currentCodI_I_LST_001 = ko.observable();
                //I_LST_002
                self.items_I_LST_002 = ko.observableArray([]);
                for (var i_3 = 1; i_3 < 100; i_3++) {
                    self.items_I_LST_002.push(new ItemModel_I_LST_002('00' + i_3, '基本給', "description " + i_3, "other" + i_3));
                }
                self.columns_I_LST_002 = ko.observableArray([
                    { headerText: '�R�[�h', prop: 'code', width: 100 },
                    { headerText: '����', prop: 'name', width: 150 },
                    { headerText: '����', prop: 'description', width: 200 }
                ]);
                self.currentCodI_I_LST_002 = ko.observable();
                //I_LST_003
                self.items_I_LST_003 = ko.observableArray([]);
                for (var i_4 = 1; i_4 < 100; i_4++) {
                    self.items_I_LST_003.push(new ItemModel_I_LST_003('00' + i_4, '基本給', "description " + i_4, "other" + i_4));
                }
                self.columns_I_LST_003 = ko.observableArray([
                    { headerText: '�R�[�h', prop: 'code', width: 100 },
                    { headerText: '����', prop: 'name', width: 150 },
                    { headerText: '����', prop: 'description', width: 200 }
                ]);
                self.currentCodI_I_LST_003 = ko.observable();
            }
            return ScreenModel;
        }());
        i_1.ScreenModel = ScreenModel;
        var ItemModel_I_LST_001 = (function () {
            function ItemModel_I_LST_001(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_I_LST_001;
        }());
        i_1.ItemModel_I_LST_001 = ItemModel_I_LST_001;
        var ItemModel_I_LST_002 = (function () {
            function ItemModel_I_LST_002(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_I_LST_002;
        }());
        i_1.ItemModel_I_LST_002 = ItemModel_I_LST_002;
        var ItemModel_I_LST_003 = (function () {
            function ItemModel_I_LST_003(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_I_LST_003;
        }());
        i_1.ItemModel_I_LST_003 = ItemModel_I_LST_003;
    })(i = qpp014.i || (qpp014.i = {}));
})(qpp014 || (qpp014 = {}));
;
