var qmm007;
(function (qmm007) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.lbl_003 = ko.observable('001');
                    self.lbl_004 = ko.observable('ガソリン単価');
                    self.lbl_008 = ko.observable('~ 9999/12');
                    self.sel_001_radio = ko.observable('2');
                    self.inp_001 = {
                        value: ko.observable('2016/04'),
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "",
                            textalign: "left"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(true),
                        readonly: ko.observable(false)
                    };
                }
                ScreenModel.prototype.btn_001_clicked = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.btn_002_clicked = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm007.c || (qmm007.c = {}));
})(qmm007 || (qmm007 = {}));
