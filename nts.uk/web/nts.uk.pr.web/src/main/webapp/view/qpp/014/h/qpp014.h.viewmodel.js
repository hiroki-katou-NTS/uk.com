var qpp014;
(function (qpp014) {
    var h;
    (function (h) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.h_INP_001 = ko.observable(new Date('2016/12/01'));
                    self.h_LST_001_items = ko.observableArray([]);
                    for (var i_1 = 1; i_1 < 100; i_1++) {
                        self.h_LST_001_items.push(new ItemModel_H_LST_001('00' + i_1, '基本給', "description " + i_1));
                    }
                    self.h_LST_001_itemsSelected = ko.observable();
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel_H_LST_001 = (function () {
                function ItemModel_H_LST_001(code, name, description) {
                    this.code = code;
                    this.name = name;
                    this.description = description;
                }
                return ItemModel_H_LST_001;
            }());
            viewmodel.ItemModel_H_LST_001 = ItemModel_H_LST_001;
        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
    })(h = qpp014.h || (qpp014.h = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.h.viewmodel.js.map