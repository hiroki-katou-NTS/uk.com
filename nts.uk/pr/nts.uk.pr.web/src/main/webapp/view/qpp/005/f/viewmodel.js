var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp005;
                (function (qpp005) {
                    var f;
                    (function (f) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.totalCheck = new CheckBox();
                                    self.oneMonthCheck = new CheckBox();
                                    self.oneMonthCheck.isChecked = ko.observable(false);
                                    self.taxCommuteCheck = new CheckBox();
                                    self.taxCommuteCheck.isChecked = ko.observable(false);
                                    self.numberEditor = new NumberEditor();
                                    self.numberEditor.isEnable = self.totalCheck.isChecked;
                                    self.numberEditor.value = ko.observable("10000");
                                }
                                ScreenModel.prototype.IsEnableText = function () {
                                    var self = this;
                                    if (self.totalCheck.isChecked() && self.taxCommuteCheck.isChecked())
                                        return ko.observable(true);
                                    return ko.observable(false);
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var CheckBox = (function () {
                                function CheckBox() {
                                    var self = this;
                                    self.isChecked = ko.observable(true);
                                }
                                return CheckBox;
                            }());
                            viewmodel.CheckBox = CheckBox;
                            var NumberEditor = (function () {
                                function NumberEditor() {
                                    var self = this;
                                    self.option = ko.mapping.fromJS(new uk.ui.option.CurrencyEditorOption({ grouplength: 3, currencyformat: 'JPY', width: '80' }));
                                }
                                return NumberEditor;
                            }());
                            viewmodel.NumberEditor = NumberEditor;
                        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
                    })(f = qpp005.f || (qpp005.f = {}));
                })(qpp005 = view.qpp005 || (view.qpp005 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
