var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
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
                                    self.enable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.historyStart = ko.observable('');
                                    self.historyEnd = ko.observable('9999/12');
                                    self.selectedId = ko.observable(1);
                                    var isEmpty = nts.uk.ui.windows.getShared("isEmpty");
                                    if (!isEmpty) {
                                        var historyStart = nts.uk.ui.windows.getShared("historyStart");
                                        self.selectModel = ko.observableArray([
                                            new BoxModel(1, '最新の履歴 (' + historyStart + ') から引き継ぐ'),
                                            new BoxModel(2, '初めから作成する')
                                        ]);
                                    }
                                    else {
                                        self.selectModel = ko.observableArray([
                                            new BoxModel(1, '最新の履歴（N）から引き継ぐ'),
                                            new BoxModel(2, '初めから作成する')
                                        ]);
                                    }
                                }
                                ScreenModel.prototype.fwaddHistoryInfo = function () {
                                    var self = this;
                                    var addHistoryInfoModel;
                                    addHistoryInfoModel = new AddHistoryInfoModel();
                                    addHistoryInfoModel.typeModel = self.selectedId();
                                    addHistoryInfoModel.historyStart = self.historyStart();
                                    nts.uk.ui.windows.setShared("addHistoryInfoModel", addHistoryInfoModel);
                                    nts.uk.ui.windows.close();
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
                            viewmodel.BoxModel = BoxModel;
                            var AddHistoryInfoModel = (function () {
                                function AddHistoryInfoModel() {
                                }
                                return AddHistoryInfoModel;
                            }());
                            viewmodel.AddHistoryInfoModel = AddHistoryInfoModel;
                        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
                    })(d = qmm011.d || (qmm011.d = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
