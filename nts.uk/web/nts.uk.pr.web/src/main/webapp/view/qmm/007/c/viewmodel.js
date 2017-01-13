var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm007;
                (function (qmm007) {
                    var c;
                    (function (c) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.code = ko.observable('001');
                                    self.name = ko.observable('ガソリン単価');
                                    self.endDate = ko.observable('~ 9999/12');
                                    self.edittingMethod = ko.observable('2');
                                    self.startDate = {
                                        value: ko.observable('2016/04'),
                                        constraint: '',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            placeholder: "",
                                            width: "",
                                            textalign: "left"
                                        })),
                                        required: ko.observable(false),
                                        enable: ko.observable(true),
                                        readonly: ko.observable(false)
                                    };
                                }
                                ScreenModel.prototype.btnApplyClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.btnCancelClicked = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
                    })(c = qmm007.c || (qmm007.c = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
