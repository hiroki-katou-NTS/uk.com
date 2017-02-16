var cmm015;
(function (cmm015) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.payClassificationCode = ko.observable("");
                    self.payClassificationName = ko.observable("");
                    self.payClassificationMemo = ko.observable("");
                    self.payClassificationList = ko.observableArray([]);
                    self.selectedPayClassificationCode = ko.observable(null);
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
            var PayClassification = (function () {
                function PayClassification(payClassificationCode, payClassificationName) {
                    this.payClassificationCode = payClassificationCode;
                    this.payClassificationName = payClassificationName;
                }
                return PayClassification;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm015.a || (cmm015.a = {}));
})(cmm015 || (cmm015 = {}));
