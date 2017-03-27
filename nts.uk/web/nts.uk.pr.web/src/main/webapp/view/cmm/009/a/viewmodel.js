var cmm009;
(function (cmm009) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.departmentCode = ko.observable("");
                    self.departmentName = ko.observable("");
                    self.shortName = ko.observable("");
                    self.genericName = ko.observable("");
                    self.fullGenericName = ko.observable("");
                    self.externalCode = ko.observable("");
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
                    self.texteditorshortname = {
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
                    self.texteditorgenericname = {
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
                    self.texteditorexternalcode = {
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm009.a || (cmm009.a = {}));
})(cmm009 || (cmm009 = {}));
//# sourceMappingURL=viewmodel.js.map