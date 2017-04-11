var qpp014;
(function (qpp014) {
    var j;
    (function (j) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.itemList_J_SEL_001 = ko.observableArray([
                    new BoxModel_J_SEL_001(0, '銀行集計'),
                    new BoxModel_J_SEL_001(1, '明細出力')
                ]);
                self.selectedId_J_SEL_001 = ko.observable(0);
                self.itemList_J_SEL_002 = ko.observableArray([
                    new ItemModel_J_SEL_002('0001', 'aaaaaaa'),
                    new ItemModel_J_SEL_002('0002', 'bbbbbbb'),
                    new ItemModel_J_SEL_002('0003', 'ccccccc')
                ]);
                self.selectedCode_J_SEL_002 = ko.observable('0002');
                self.itemList_J_SEL_003 = ko.observableArray([
                    new ItemModel_J_SEL_003('0001', 'mmmmmm'),
                    new ItemModel_J_SEL_003('0002', 'qqqqqq'),
                    new ItemModel_J_SEL_003('0003', 'oooooo')
                ]);
                self.selectedCode_J_SEL_003 = ko.observable('0001');
                self.items_J_LST_001 = ko.observableArray([]);
                for (var i_1 = 1; i_1 < 100; i_1++) {
                    self.items_J_LST_001.push(new ItemModel_J_LST_001('00' + i_1, '基本給 ' + i_1, 'des ' + i_1));
                }
                self.currentCode_J_LST_001 = ko.observable();
                self.currentCode_J_SEL_004 = ko.observable(1);
            }
            ScreenModel.prototype.closeDialog = function () {
                nts.uk.ui.windows.close();
            };
            return ScreenModel;
        }());
        j.ScreenModel = ScreenModel;
        var BoxModel_J_SEL_001 = (function () {
            function BoxModel_J_SEL_001(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
            return BoxModel_J_SEL_001;
        }());
        j.BoxModel_J_SEL_001 = BoxModel_J_SEL_001;
        var ItemModel_J_SEL_002 = (function () {
            function ItemModel_J_SEL_002(code, name) {
                this.code = code;
                this.name = name;
            }
            return ItemModel_J_SEL_002;
        }());
        j.ItemModel_J_SEL_002 = ItemModel_J_SEL_002;
        var ItemModel_J_SEL_003 = (function () {
            function ItemModel_J_SEL_003(code, name) {
                this.code = code;
                this.name = name;
            }
            return ItemModel_J_SEL_003;
        }());
        j.ItemModel_J_SEL_003 = ItemModel_J_SEL_003;
        var ItemModel_J_LST_001 = (function () {
            function ItemModel_J_LST_001(code, name, description) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
            return ItemModel_J_LST_001;
        }());
        j.ItemModel_J_LST_001 = ItemModel_J_LST_001;
    })(j = qpp014.j || (qpp014.j = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.j.viewmodel.js.map