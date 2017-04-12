var cmm013;
(function (cmm013) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new BoxModel("1", '明細書に印字する行'),
                        new BoxModel("2", '明細書に印字しない行（この行は印刷はされませんが、値の参照・修正が可能です）'),
                    ]);
                    self.selectedCode = ko.observable("1");
                    self.enable = ko.observable(true);
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = cmm013.c || (cmm013.c = {}));
})(cmm013 || (cmm013 = {}));
