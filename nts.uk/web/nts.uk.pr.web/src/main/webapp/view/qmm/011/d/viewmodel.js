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
                        var TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
                        var HistoryUnemployeeInsuranceDto = nts.uk.pr.view.qmm011.a.service.model.HistoryUnemployeeInsuranceDto;
                        var HistoryAccidentInsuranceDto = nts.uk.pr.view.qmm011.a.service.model.HistoryAccidentInsuranceDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.dsel001 = ko.observableArray([
                                        new BoxModel(1, '最新の履歴（N）から引き継ぐ'),
                                        new BoxModel(2, '初めから作成する')
                                    ]);
                                    self.enable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.historyStart = ko.observable('');
                                    self.historyEnd = ko.observable('9999/12');
                                    self.selectedId = ko.observable(1);
                                    self.typeHistory = ko.observable(nts.uk.ui.windows.getShared("type"));
                                    self.selectedId.subscribe(function (typeSelect) {
                                        self.changeSelect(typeSelect);
                                    });
                                }
                                ScreenModel.prototype.fwaddHistoryInfo = function () {
                                    var self = this;
                                    if (self.typeHistory() == TypeHistory.HistoryUnemployee) {
                                        self.fwaddHistoryInfoUnemployeeInsurance();
                                    }
                                    else {
                                        self.fwaddHistoryInfoAccidentInsurance();
                                    }
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.fwaddHistoryInfoUnemployeeInsurance = function () {
                                    var self = this;
                                    var historyInfo;
                                    historyInfo = new HistoryUnemployeeInsuranceDto("historyId001", self.historyStart(), "9999/12");
                                    nts.uk.ui.windows.setShared("addHistoryUnemployeeInsuranceDto", historyInfo);
                                };
                                ScreenModel.prototype.fwaddHistoryInfoAccidentInsurance = function () {
                                    var self = this;
                                    var historyInfo;
                                    historyInfo = new HistoryAccidentInsuranceDto("", self.historyStart(), self.historyEnd());
                                    nts.uk.ui.windows.setShared("addHistoryAccidentInsuranceDto", historyInfo);
                                };
                                ScreenModel.prototype.changeSelect = function (typeSelect) {
                                    var self = this;
                                    if (typeSelect == 1) {
                                        self.historyEnd('9999/12');
                                    }
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
                        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
                    })(d = qmm011.d || (qmm011.d = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
