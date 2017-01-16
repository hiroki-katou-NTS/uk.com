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
                    var f;
                    (function (f) {
                        var option = nts.uk.ui.option;
                        var TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
                        var HistoryUnemployeeInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.HistoryUnemployeeInsuranceRateModel;
                        var HistoryAccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.HistoryAccidentInsuranceRateModel;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.fsel001 = ko.observableArray([
                                        new BoxModel(1, '最新の履歴（N）から引き継ぐ'),
                                        new BoxModel(2, '初めから作成する')
                                    ]);
                                    self.selectedId = ko.observable(1);
                                    self.enable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    var historyId = nts.uk.ui.windows.getShared("historyId");
                                    var lsthistoryValue = nts.uk.ui.windows.getShared("lsthistoryValue");
                                    self.typeHistory = nts.uk.ui.windows.getShared("type");
                                    if (self.typeHistory == TypeHistory.HistoryUnemployee) {
                                        for (var index = 0; index < lsthistoryValue.length; index++) {
                                            if (lsthistoryValue[index].historyId === historyId) {
                                                self.historyStart = ko.observable(new HistoryUnemployeeInsuranceRateModel(lsthistoryValue[index]).getViewStartMonth(lsthistoryValue[index]));
                                                self.historyEnd = ko.observable(new HistoryUnemployeeInsuranceRateModel(lsthistoryValue[index]).getViewEndMonth(lsthistoryValue[index]));
                                            }
                                        }
                                    }
                                    else {
                                        for (var index = 0; index < lsthistoryValue.length; index++) {
                                            if (lsthistoryValue[index].historyId === historyId) {
                                                self.historyStart = ko.observable(new HistoryAccidentInsuranceRateModel(lsthistoryValue[index]).getViewStartMonth(lsthistoryValue[index]));
                                                self.historyEnd = ko.observable(new HistoryAccidentInsuranceRateModel(lsthistoryValue[index]).getViewEndMonth(lsthistoryValue[index]));
                                            }
                                        }
                                    }
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
                        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
                    })(f = qmm011.f || (qmm011.f = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
