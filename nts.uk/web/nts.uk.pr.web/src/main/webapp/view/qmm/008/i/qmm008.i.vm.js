var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var i;
                    (function (i) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel(officeName, pensionModel) {
                                    var self = this;
                                    self.listPensionAvgearnModel = ko.observableArray([]);
                                    self.pensionRateModel = new PensionRateModel(pensionModel.historyId, pensionModel.officeCode(), officeName, pensionModel.startMonth(), pensionModel.endMonth(), pensionModel.autoCalculate(), pensionModel.fundInputApply(), pensionModel.rateItems(), pensionModel.fundRateItems(), pensionModel.roundingMethods(), pensionModel.childContributionRate());
                                    self.leftShow = ko.observable(true);
                                    self.rightShow = ko.observable(true);
                                    self.leftBtnText = ko.computed(function () { if (self.leftShow())
                                        return "—"; return "+"; });
                                    self.rightBtnText = ko.computed(function () { if (self.rightShow())
                                        return "—"; return "+"; });
                                    self.numberEditorCommonOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3
                                    }));
                                    self.dirty = new nts.uk.ui.DirtyChecker(self.listPensionAvgearnModel);
                                    self.errorList = ko.observableArray([
                                        { messageId: "AL001", message: "変更された内容が登録されていません。\r\n よろしいですか。" },
                                        { messageId: "AL002", message: "データを削除します。\r\nよろしいですか？" },
                                    ]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    $.when(self.loadPensionAvgearn()).done(function () {
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadPensionAvgearn = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    i.service.findPensionAvgearn(self.pensionRateModel.historyId).done(function (res) {
                                        var salMin = 0;
                                        res.listPensionAvgearnDto.forEach(function (item) {
                                            self.listPensionAvgearnModel.push(new PensionAvgearnModel(item.grade, item.avgEarn, salMin, item.upperLimit, new PensionAvgearnValueModel(item.companyFund.maleAmount, item.companyFund.femaleAmount, item.companyFund.unknownAmount), new PensionAvgearnValueModel(item.companyFundExemption.maleAmount, item.companyFundExemption.femaleAmount, item.companyFundExemption.unknownAmount), new PensionAvgearnValueModel(item.companyPension.maleAmount, item.companyPension.femaleAmount, item.companyPension.unknownAmount), new PensionAvgearnValueModel(item.personalFund.maleAmount, item.personalFund.femaleAmount, item.personalFund.unknownAmount), new PensionAvgearnValueModel(item.personalFundExemption.maleAmount, item.personalFundExemption.femaleAmount, item.personalFundExemption.unknownAmount), new PensionAvgearnValueModel(item.personalPension.maleAmount, item.personalPension.femaleAmount, item.personalPension.unknownAmount), item.childContributionAmount));
                                            salMin = item.upperLimit;
                                        });
                                        self.dirty.reset();
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var data = { historyId: self.pensionRateModel.historyId, listPensionAvgearnDto: [] };
                                    self.listPensionAvgearnModel().forEach(function (item) {
                                        data.listPensionAvgearnDto.push(ko.toJS(item));
                                    });
                                    return data;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                                        return;
                                    }
                                    i.service.updatePensionAvgearn(self.collectData(), self.pensionRateModel.officeCode)
                                        .done(function () { return self.closeDialog(); });
                                };
                                ScreenModel.prototype.leftToggle = function () {
                                    this.leftShow(!this.leftShow());
                                };
                                ScreenModel.prototype.rightToggle = function () {
                                    this.rightShow(!this.rightShow());
                                };
                                ScreenModel.prototype.clearError = function () {
                                    $('.has-error').ntsError('clear');
                                };
                                ScreenModel.prototype.reCalculate = function () {
                                    var self = this;
                                    self.clearError();
                                    self.listPensionAvgearnModel.removeAll();
                                    i.service.recalculatePensionAvgearn(self.pensionRateModel.historyId)
                                        .done(function (res) {
                                        var salMin = 0;
                                        res.listPensionAvgearnDto.forEach(function (item) {
                                            self.listPensionAvgearnModel.push(new PensionAvgearnModel(item.grade, item.avgEarn, salMin, item.upperLimit, new PensionAvgearnValueModel(item.companyFund.maleAmount, item.companyFund.femaleAmount, item.companyFund.unknownAmount), new PensionAvgearnValueModel(item.companyFundExemption.maleAmount, item.companyFundExemption.femaleAmount, item.companyFundExemption.unknownAmount), new PensionAvgearnValueModel(item.companyPension.maleAmount, item.companyPension.femaleAmount, item.companyPension.unknownAmount), new PensionAvgearnValueModel(item.personalFund.maleAmount, item.personalFund.femaleAmount, item.personalFund.unknownAmount), new PensionAvgearnValueModel(item.personalFundExemption.maleAmount, item.personalFundExemption.femaleAmount, item.personalFundExemption.unknownAmount), new PensionAvgearnValueModel(item.personalPension.maleAmount, item.personalPension.femaleAmount, item.personalPension.unknownAmount), item.childContributionAmount));
                                            salMin = item.upperLimit;
                                        });
                                        self.dirty.reset();
                                    });
                                };
                                ScreenModel.prototype.closeDialogWithDirtyCheck = function () {
                                    var self = this;
                                    if (self.dirty.isDirty()) {
                                        nts.uk.ui.dialog.confirm(self.errorList()[0].message).ifYes(function () {
                                            self.closeDialog();
                                        }).ifCancel(function () {
                                        });
                                    }
                                    else {
                                        self.closeDialog();
                                    }
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var PensionRateModel = (function () {
                                function PensionRateModel(historyId, officeCode, officeName, startMonth, endMonth, autoCalculate, fundInputApply, rateItems, fundRateItems, roundingMethods, childContributionRate) {
                                    this.historyId = historyId;
                                    this.officeCode = officeCode;
                                    this.officeName = officeName;
                                    this.startMonth = startMonth;
                                    this.endMonth = endMonth;
                                    this.autoCalculate = autoCalculate;
                                    this.fundInputApply = fundInputApply;
                                    this.rateItems = rateItems;
                                    this.fundRateItems = fundRateItems;
                                    this.roundingMethods = roundingMethods;
                                    this.childContributionRate = ko.observable(childContributionRate);
                                }
                                return PensionRateModel;
                            }());
                            viewmodel.PensionRateModel = PensionRateModel;
                            var PensionAvgearnModel = (function () {
                                function PensionAvgearnModel(grade, avgEarn, lowerLimit, upperLimit, companyFund, companyFundExemption, companyPension, personalFund, personalFundExemption, personalPension, childContributionAmount) {
                                    this.grade = grade;
                                    this.avgEarn = avgEarn;
                                    this.lowerLimit = lowerLimit;
                                    this.upperLimit = upperLimit;
                                    this.companyFund = companyFund;
                                    this.companyFundExemption = companyFundExemption;
                                    this.companyPension = companyPension;
                                    this.personalFund = personalFund;
                                    this.personalFundExemption = personalFundExemption;
                                    this.personalPension = personalPension;
                                    this.childContributionAmount = ko.observable(childContributionAmount);
                                }
                                return PensionAvgearnModel;
                            }());
                            viewmodel.PensionAvgearnModel = PensionAvgearnModel;
                            var PensionAvgearnValueModel = (function () {
                                function PensionAvgearnValueModel(maleAmount, femaleAmount, unknownAmount) {
                                    this.maleAmount = ko.observable(maleAmount);
                                    this.femaleAmount = ko.observable(femaleAmount);
                                    this.unknownAmount = ko.observable(unknownAmount);
                                }
                                return PensionAvgearnValueModel;
                            }());
                            viewmodel.PensionAvgearnValueModel = PensionAvgearnValueModel;
                        })(viewmodel = i.viewmodel || (i.viewmodel = {}));
                    })(i = qmm008.i || (qmm008.i = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.i.vm.js.map