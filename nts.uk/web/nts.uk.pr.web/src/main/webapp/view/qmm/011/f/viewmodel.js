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
                        var HistoryUnemployeeInsuranceDto = nts.uk.pr.view.qmm011.a.service.model.HistoryUnemployeeInsuranceDto;
                        var HistoryAccidentInsuranceDto = nts.uk.pr.view.qmm011.a.service.model.HistoryAccidentInsuranceDto;
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
                                    self.typeHistory = ko.observable(nts.uk.ui.windows.getShared("type"));
                                    self.historyStart = ko.observable(nts.uk.ui.windows.getShared("historyStart"));
                                    self.historyId = ko.observable(nts.uk.ui.windows.getShared("historyId"));
                                    self.historyEnd = ko.observable(nts.uk.ui.windows.getShared("historyEnd"));
                                }
                                ScreenModel.prototype.fwupdateHistoryInfoUnemployeeInsurance = function () {
                                    var self = this;
                                    var historyInfo;
                                    historyInfo = new HistoryUnemployeeInsuranceDto(self.historyId(), self.historyStart(), self.historyEnd());
                                    nts.uk.ui.windows.setShared("updateHistoryUnemployeeInsuranceDto", historyInfo);
                                };
                                ScreenModel.prototype.fwupdateHistoryInfoAccidentInsurance = function () {
                                    var self = this;
                                    var historyInfo;
                                    historyInfo = new HistoryAccidentInsuranceDto(self.historyId(), self.historyStart(), self.historyEnd());
                                    nts.uk.ui.windows.setShared("updateHistoryAccidentInsuranceDto", historyInfo);
                                };
                                ScreenModel.prototype.fwupdateHistoryInfo = function () {
                                    var self = this;
                                    if (self.typeHistory() == TypeHistory.HistoryUnemployee) {
                                        self.fwupdateHistoryInfoUnemployeeInsurance();
                                    }
                                    else {
                                        self.fwupdateHistoryInfoAccidentInsurance();
                                    }
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
                        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
                    })(f = qmm011.f || (qmm011.f = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
