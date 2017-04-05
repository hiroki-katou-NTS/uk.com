var qpp014;
(function (qpp014) {
    var g;
    (function (g) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.g_INP_001 = ko.observable(new Date('2016/12/01'));
                    self.g_SEL_001_items = ko.observableArray([
                        new ItemModel_G_SEL_001('0001', '登録済みの振込元銀行の名称'),
                        new ItemModel_G_SEL_001('0002', '銀行コード'),
                        new ItemModel_G_SEL_001('0003', '支店コード')
                    ]);
                    self.g_SEL_001_itemSelected = ko.observable(self.g_SEL_001_items()[0]);
                    self.g_SEL_002_items = ko.observableArray([
                        new ItemModel_G_SEL_002('00000001'),
                        new ItemModel_G_SEL_002('00000002'),
                        new ItemModel_G_SEL_002('00000003')
                    ]);
                    self.g_SEL_002_itemSelected = ko.observable(self.g_SEL_002_items()[0]);
                    self.g_INP_002 = {
                        value: ko.observable(12)
                    };
                    self.g_SEL_003_itemSelected = ko.observable(1);
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel_G_SEL_001 = (function () {
                function ItemModel_G_SEL_001(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel_G_SEL_001;
            }());
            viewmodel.ItemModel_G_SEL_001 = ItemModel_G_SEL_001;
            var ItemModel_G_SEL_002 = (function () {
                function ItemModel_G_SEL_002(code) {
                    this.code = code;
                }
                return ItemModel_G_SEL_002;
            }());
            viewmodel.ItemModel_G_SEL_002 = ItemModel_G_SEL_002;
        })(viewmodel = g.viewmodel || (g.viewmodel = {}));
    })(g = qpp014.g || (qpp014.g = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.g.viewmodel.js.map