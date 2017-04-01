var qpp014;
(function (qpp014) {
    var e;
    (function (e) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.items_E_LST_003 = ko.observableArray([]);
                for (var i_1 = 1; i_1 < 100; i_1++) {
                    self.items_E_LST_003.push(new ItemModel_E_LST_003('00' + i_1, '基本給', "description " + i_1));
                }
                self.currentCode_E_LST_003 = ko.observable();
                $("#E_BTN_004").css("display", "none");
            }
            return ScreenModel;
        }());
        e.ScreenModel = ScreenModel;
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
//# sourceMappingURL=qpp014.e.viewmodel.js.map