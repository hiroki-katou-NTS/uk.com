var qmm011;
(function (qmm011) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.fsel001 = ko.observableArray([
                        new BoxModel(1, '履歴を削除する'),
                        new BoxModel(2, '履歴を修正する')
                    ]);
                    self.selectedId = ko.observable(1);
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
            viewmodel.BoxModel = BoxModel;
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = qmm011.f || (qmm011.f = {}));
})(qmm011 || (qmm011 = {}));
