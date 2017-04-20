var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var k;
                    (function (k) {
                        var viewmodel;
                        (function (viewmodel) {
                            class ScreenModelKScreen {
                                constructor(param) {
                                    var self = this;
                                    self.data = param;
                                    self.startYm = ko.observable(param.startYm);
                                    self.endYm = ko.observable(param.endYm);
                                    self.selectedMode = ko.observable(1);
                                }
                                closeDialog() {
                                    nts.uk.ui.windows.close();
                                }
                                actionHandler() {
                                    var self = this;
                                    if (self.selectedMode() == 0) {
                                        let command = {
                                            formulaCode: self.data.formulaCode,
                                            historyId: self.data.historyId,
                                            startDate: self.startYm(),
                                            difficultyAtr: self.data.difficultyAtr
                                        };
                                        k.service.removeFormulaHistory(command)
                                            .done(function () {
                                            nts.uk.ui.windows.close();
                                        })
                                            .fail(function (res) {
                                            alert(res);
                                        });
                                    }
                                    else {
                                        let command = {
                                            formulaCode: self.data.formulaCode,
                                            historyId: self.data.historyId,
                                            startDate: self.startYm(),
                                        };
                                        k.service.updateFormulaHistory(command)
                                            .done(function () {
                                            nts.uk.ui.windows.close();
                                        })
                                            .fail(function (res) {
                                            alert(res);
                                        });
                                    }
                                }
                            }
                            viewmodel.ScreenModelKScreen = ScreenModelKScreen;
                        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
                    })(k = qmm017.k || (qmm017.k = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
