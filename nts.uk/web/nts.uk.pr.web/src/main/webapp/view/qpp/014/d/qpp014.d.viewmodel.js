var qpp014;
(function (qpp014) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.d_SEL_001_selectedCode = ko.observable(1);
                    self.d_SEL_002_selectedCode = ko.observable(1);
                    self.d_INP_001 = {
                        value: ko.observable('')
                    };
                    self.d_LST_001_items = ko.observableArray([]);
                    for (var i_1 = 1; i_1 < 31; i_1++) {
                        self.d_LST_001_items.push(({ code: '00' + i_1, name: '基本給' + i_1, description: ('description' + i_1) }));
                    }
                    self.d_LST_001_itemSelected = ko.observable(0);
                }
                ScreenModel.prototype.openEDialog = function () {
                    nts.uk.ui.windows.sub.modal("/view/qpp/014/e/index.xhtml", { title: "振込データの作成結果一覧", dialogClass: "no-close" });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qpp014.d || (qpp014.d = {}));
})(qpp014 || (qpp014 = {}));
;
//# sourceMappingURL=qpp014.d.viewmodel.js.map