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
                        var HistoryInfoDto = d.service.model.HistoryInfoDto;
                        var TypeHistory = nts.uk.pr.view.qmm011.a.service.model.TypeHistory;
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
                                }
                                ScreenModel.prototype.addHistoryInfo = function () {
                                    var self = this;
                                    nts.uk.ui.windows.close();
                                    if (self.typeHistory() == TypeHistory.HistoryUnemployee) {
                                        self.addHistoryInfoUnemployeeInsurance();
                                    }
                                    else {
                                        self.addHistoryInfoAccidentInsurance();
                                    }
                                };
                                ScreenModel.prototype.addHistoryInfoUnemployeeInsurance = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var historyInfo;
                                    historyInfo = new HistoryInfoDto("historyId001", "companyCode001", null, self.historyStart(), "9999/12", true);
                                    d.service.addHistoryInfoUnemployeeInsurance(historyInfo);
                                };
                                ScreenModel.prototype.addHistoryInfoAccidentInsurance = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var historyInfo;
                                    historyInfo = new HistoryInfoDto("historyId001", "companyCode001", null, self.historyStart(), "9999/12", true);
                                    d.service.addHistoryInfoAccidentInsurance(historyInfo);
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
