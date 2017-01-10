var qmm011;
(function (qmm011) {
    var d;
    (function (d) {
        var option = nts.uk.ui.option;
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.dsel001 = ko.observableArray([
                        new BoxModel(1, '最新の履歴（N）から引き継ぐ'),
                        new BoxModel(2, '初めから作成する')
                    ]);
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                    self.dinp001 = ko.observable('2017/01');
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
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm011.d || (qmm011.d = {}));
})(qmm011 || (qmm011 = {}));
