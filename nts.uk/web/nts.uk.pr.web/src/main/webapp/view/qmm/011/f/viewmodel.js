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
                        var AccidentInsuranceRateDeleteDto = f.service.model.AccidentInsuranceRateDeleteDto;
                        var UnemployeeInsuranceDeleteDto = f.service.model.UnemployeeInsuranceDeleteDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.selectModel = ko.observableArray([
                                        new BoxModel(1, '履歴を削除する'),
                                        new BoxModel(2, '履歴を修正する')
                                    ]);
                                    self.selectedId = ko.observable(2);
                                    self.enable = ko.observable(true);
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.typeHistory = ko.observable(nts.uk.ui.windows.getShared("type"));
                                    self.historyStart = ko.observable(nts.uk.ui.windows.getShared("historyStart"));
                                    self.historyId = ko.observable(nts.uk.ui.windows.getShared("historyId"));
                                    self.historyEnd = ko.observable(nts.uk.ui.windows.getShared("historyEnd"));
                                    self.endMonthRage = ko.observable(nts.uk.time.formatYearMonth(self.historyEnd()));
                                }
                                ScreenModel.prototype.fwupdateHistoryInfoUnemployeeInsurance = function () {
                                    var self = this;
                                    var unemployeeInsuranceDeleteDto;
                                    unemployeeInsuranceDeleteDto = new UnemployeeInsuranceDeleteDto();
                                    unemployeeInsuranceDeleteDto.code = self.historyId();
                                    unemployeeInsuranceDeleteDto.version = 11;
                                    var updateHistoryInfoModel;
                                    updateHistoryInfoModel = new UpdateHistoryInfoModel();
                                    updateHistoryInfoModel.typeUpdate = self.selectedId();
                                    updateHistoryInfoModel.historyId = self.historyId();
                                    updateHistoryInfoModel.historyStart = self.historyStart();
                                    updateHistoryInfoModel.historyEnd = self.historyEnd();
                                    if (self.selectedId() == 1) {
                                        f.service.deleteUnemployeeInsurance(unemployeeInsuranceDeleteDto).done(function (data) {
                                            nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                                            nts.uk.ui.windows.close();
                                        }).fail(function (error) {
                                            nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                                            nts.uk.ui.windows.close();
                                        });
                                    }
                                    else {
                                        nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                                        nts.uk.ui.windows.close();
                                    }
                                };
                                ScreenModel.prototype.fwupdateHistoryInfoAccidentInsurance = function () {
                                    var self = this;
                                    var accidentInsuranceRateDeleteDto;
                                    accidentInsuranceRateDeleteDto = new AccidentInsuranceRateDeleteDto();
                                    accidentInsuranceRateDeleteDto.code = self.historyId();
                                    accidentInsuranceRateDeleteDto.version = 11;
                                    var updateHistoryInfoModel;
                                    updateHistoryInfoModel = new UpdateHistoryInfoModel();
                                    updateHistoryInfoModel.typeUpdate = self.selectedId();
                                    updateHistoryInfoModel.historyId = self.historyId();
                                    updateHistoryInfoModel.historyStart = self.historyStart();
                                    updateHistoryInfoModel.historyEnd = self.historyEnd();
                                    if (self.selectedId() == 1) {
                                        f.service.deleteAccidentInsuranceRate(accidentInsuranceRateDeleteDto).done(function (data) {
                                            nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                                            nts.uk.ui.windows.close();
                                        }).fail(function (error) {
                                            nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                                            nts.uk.ui.windows.close();
                                        });
                                    }
                                    else {
                                        nts.uk.ui.windows.setShared("updateHistoryInfoModel", updateHistoryInfoModel);
                                        nts.uk.ui.windows.close();
                                    }
                                };
                                ScreenModel.prototype.fwupdateHistoryInfo = function () {
                                    var self = this;
                                    if (self.typeHistory() == TypeHistory.HistoryUnemployee) {
                                        self.fwupdateHistoryInfoUnemployeeInsurance();
                                    }
                                    else {
                                        self.fwupdateHistoryInfoAccidentInsurance();
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
                            var UpdateHistoryInfoModel = (function () {
                                function UpdateHistoryInfoModel() {
                                }
                                return UpdateHistoryInfoModel;
                            }());
                            viewmodel.UpdateHistoryInfoModel = UpdateHistoryInfoModel;
                        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
                    })(f = qmm011.f || (qmm011.f = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
