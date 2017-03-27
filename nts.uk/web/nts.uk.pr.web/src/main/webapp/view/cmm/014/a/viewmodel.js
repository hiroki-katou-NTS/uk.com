var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.classificationCode = ko.observable("");
                    self.classificationName = ko.observable("");
                    self.classificationMemo = ko.observable("");
                    self.classificationList = ko.observableArray([]);
                    self.selectedClassificationCode = ko.observable(null);
                    self.texteditorcode = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "100px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true)
                    };
                    self.texteditorname = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "100px",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true)
                    };
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
                    var self = this;
                    var dfd = $.Deferred();
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Classification = (function () {
                function Classification(classificationCode, classificationName) {
                    this.classificationCode = classificationCode;
                    this.classificationName = classificationName;
                }
                return Classification;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm014.a || (cmm014.a = {}));
})(cmm014 || (cmm014 = {}));
//# sourceMappingURL=viewmodel.js.map