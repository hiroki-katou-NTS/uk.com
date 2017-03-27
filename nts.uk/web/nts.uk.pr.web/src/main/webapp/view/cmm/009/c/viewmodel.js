var cmm009;
(function (cmm009) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.startDate = ko.observable(null);
                    self.endDate = ko.observable(null);
                    self.itemList = ko.observableArray([
                        new BoxModel(1, 'box 1'),
                        new BoxModel(2, 'box 2')
                    ]);
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                    self.multilineeditor = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                            placeholder: "",
                            width: "",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                }
                ScreenModel.prototype.start = function () {
                };
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
    })(c = cmm009.c || (cmm009.c = {}));
})(cmm009 || (cmm009 = {}));
//# sourceMappingURL=viewmodel.js.map