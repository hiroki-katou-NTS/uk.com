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
                    var j;
                    (function (j) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel(data) {
                                    var self = this;
                                    self.lastestHistory = data;
                                    self.formulaCode = ko.observable(data.formulaCode);
                                    self.formulaName = ko.observable(data.formulaName);
                                    self.startYm = ko.observable(data.startYm);
                                    self.switchButtonDifficultyAtr = ko.observable({
                                        items: ko.observableArray([
                                            { code: '0', name: 'かんたん設定' },
                                            { code: '1', name: '詳細設定' }
                                        ]),
                                        selectedCode: ko.observable(0)
                                    });
                                    self.switchButtonConditionAtr = ko.observable({
                                        items: ko.observableArray([
                                            { code: '0', name: '利用しない' },
                                            { code: '1', name: '利用する' }
                                        ]),
                                        selectedCode: ko.observable(0)
                                    });
                                    self.comboBoxReferenceMasterNo = ko.observable({
                                        items: ko.observableArray([
                                            { code: '1', name: '雇用マスタ' },
                                            { code: '2', name: '部門マスタ' },
                                            { code: '3', name: '分類マスタ' },
                                            { code: '4', name: '給与分類マスタ' },
                                            { code: '5', name: '職位マスタ' },
                                            { code: '6', name: '給与区分' },
                                        ]),
                                        selectedCode: ko.observable(1)
                                    });
                                    self.startYearMonthFormated = ko.observable('(' + nts.uk.time.yearmonthInJapanEmpire(self.startYm()).toString() + ') ~');
                                    self.startYm.subscribe(function (ymChange) {
                                        self.startYearMonthFormated('(' + nts.uk.time.yearmonthInJapanEmpire(ymChange).toString() + ') ~');
                                    });
                                    self.textUseLastest = "最新の履歴（" + data.startYm + "）から引き継ぐ";
                                    self.textCreateNew = "初めから作成する";
                                    self.selectedMode = ko.observable(0);
                                }
                                ScreenModel.prototype.registerFormulaHistory = function () {
                                    var self = this;
                                    var command = {};
                                    if (self.selectedMode() === 0) {
                                        command = {
                                            formulaCode: self.lastestHistory.formulaCode,
                                            startDate: self.startYm(),
                                            difficultyAtr: self.lastestHistory.difficultyAtr,
                                            conditionAtr: self.lastestHistory.conditionAtr,
                                            referenceMasterNo: self.lastestHistory.referenceMasterNo,
                                        };
                                    }
                                    else {
                                        command = {
                                            formulaCode: self.lastestHistory.formulaCode,
                                            startDate: self.startYm(),
                                            difficultyAtr: self.switchButtonDifficultyAtr().selectedCode(),
                                            conditionAtr: self.switchButtonConditionAtr().selectedCode(),
                                            referenceMasterNo: self.comboBoxReferenceMasterNo().selectedCode(),
                                        };
                                    }
                                    j.service.registerFormulaHistory(command)
                                        .done(function () {
                                        nts.uk.ui.windows.close();
                                    })
                                        .fail(function (res) {
                                        alert(res);
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
                        var BoxModel = (function () {
                            function BoxModel(id, name) {
                                var self = this;
                                self.id = id;
                                self.name = name;
                            }
                            return BoxModel;
                        }());
                    })(j = qmm017.j || (qmm017.j = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
