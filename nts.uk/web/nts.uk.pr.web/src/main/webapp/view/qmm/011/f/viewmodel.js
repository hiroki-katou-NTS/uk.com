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
                        var HistoryInfoDto = nts.uk.pr.view.qmm011.d.service.model.HistoryInfoDto;
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
                                    var historyStart = nts.uk.ui.windows.getShared("historyStart");
                                    var historyEnd = nts.uk.ui.windows.getShared("historyEnd");
                                    self.typeHistory = nts.uk.ui.windows.getShared("type");
                                    self.historyStart = ko.observable(historyStart);
                                    self.historyEnd = ko.observable(historyEnd);
                                }
                                ScreenModel.prototype.updateHistoryInfoUnemployeeInsurance = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    var historyInfo;
                                    historyInfo = new HistoryInfoDto("historyId001", "companyCode001", null, self.historyStart(), "9999/12", true);
                                    f.service.updateHistoryInfoUnemployeeInsurance(historyInfo).done(function (data) {
                                    });
                                    return dfd.promise();
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
