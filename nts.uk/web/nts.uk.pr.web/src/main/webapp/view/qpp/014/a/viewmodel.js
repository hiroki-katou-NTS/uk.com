// TreeGrid Node
var qpp014;
(function (qpp014) {
    var a;
    (function (a) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //combobox
                //G_SEL_001
                self.itemList_A_SEL_001 = ko.observableArray([
                    new ItemModel_A_SEL_001('基本給1', '基本給'),
                    new ItemModel_A_SEL_001('基本給2', '役職手当'),
                    new ItemModel_A_SEL_001('0003', '基本給')
                ]);
                self.selectedCode_A_SEL_001 = ko.observable('0003');
            }
            return ScreenModel;
        }());
        a.ScreenModel = ScreenModel;
        var ItemModel_A_SEL_001 = (function () {
            function ItemModel_A_SEL_001(code, name) {
                this.code = code;
                this.name = name;
            }
            return ItemModel_A_SEL_001;
        }());
        a.ItemModel_A_SEL_001 = ItemModel_A_SEL_001;
    })(a = qpp014.a || (qpp014.a = {}));
})(qpp014 || (qpp014 = {}));
;
