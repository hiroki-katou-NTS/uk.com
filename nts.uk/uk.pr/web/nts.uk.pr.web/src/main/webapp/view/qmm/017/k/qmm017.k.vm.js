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
                            var ScreenModelKScreen = (function () {
                                function ScreenModelKScreen(param) {
                                    var self = this;
                                    self.data = param;
                                    self.startYm = ko.observable(param.startYm);
                                    self.endYm = ko.observable(param.endYm);
                                    self.selectedMode = ko.observable(1);
                                }
                                ScreenModelKScreen.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModelKScreen.prototype.actionHandler = function () {
                                    var self = this;
                                    if (self.selectedMode() == 0) {
                                        var command_1 = {
                                            formulaCode: self.data.formulaCode,
                                            historyId: self.data.historyId,
                                            startDate: self.startYm(),
                                            difficultyAtr: self.data.difficultyAtr
                                        };
                                        nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？")
                                            .ifYes(function () {
                                            k.service.removeFormulaHistory(command_1)
                                                .done(function () {
                                                nts.uk.ui.windows.close();
                                            })
                                                .fail(function (res) {
                                                nts.uk.ui.dialog.alert(res);
                                            });
                                        });
                                    }
                                    else {
                                        var command = {
                                            formulaCode: self.data.formulaCode,
                                            historyId: self.data.historyId,
                                            startDate: self.startYm(),
                                        };
                                        k.service.updateFormulaHistory(command)
                                            .done(function () {
                                            nts.uk.ui.windows.close();
                                        })
                                            .fail(function (res) {
                                            nts.uk.ui.dialog.alert(res);
                                        });
                                    }
                                };
                                return ScreenModelKScreen;
                            }());
                            viewmodel.ScreenModelKScreen = ScreenModelKScreen;
                        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
                    })(k = qmm017.k || (qmm017.k = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm017.k.vm.js.map