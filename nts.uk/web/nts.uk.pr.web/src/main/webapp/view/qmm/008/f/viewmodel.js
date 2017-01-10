var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var f;
                    (function (f) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.listOptions = ko.observableArray([new optionsModel(1, "履歴を削除する"), new optionsModel(2, "履歴を修正する")]);
                                    self.selectedValue = ko.observable(new optionsModel(1, ""));
                                    self.modalValue = ko.observable("Goodbye world!");
                                    self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                                    self.inp_001 = {
                                        value: ko.observable('2016/04'),
                                        constraint: 'ResidenceCode',
                                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                            textmode: "text",
                                            width: "100",
                                            textalign: "center"
                                        })),
                                        required: ko.observable(false),
                                    };
                                }
                                ScreenModel.prototype.CloseModalSubWindow = function () {
                                    nts.uk.ui.windows.setShared("addHistoryChildValue", this.modalValue(), this.isTransistReturnData());
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var optionsModel = (function () {
                                function optionsModel(id, name) {
                                    var self = this;
                                    self.id = id;
                                    self.name = name;
                                }
                                return optionsModel;
                            }());
                        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
                    })(f = qmm008.f || (qmm008.f = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
