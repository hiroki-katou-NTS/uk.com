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
                    var h;
                    (function (h) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel(officeName, healthModel) {
                                    var self = this;
                                    self.healthInsuranceRateModel = new HealthInsuranceRateModel(healthModel.officeCode(), officeName, healthModel.historyId, healthModel.startMonth(), healthModel.endMonth(), healthModel.autoCalculate(), healthModel.rateItems(), healthModel.roundingMethods());
                                    self.listHealthInsuranceAvgearn = ko.observableArray([]);
                                    self.numberEditorCommonOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3
                                    }));
                                    self.dirty = new nts.uk.ui.DirtyChecker(ko.observable(''));
                                    self.errorList = ko.observableArray([
                                        { messageId: "AL001", message: "変更された内容が登録されていません。\r\n よろしいですか。" },
                                        { messageId: "AL002", message: "データを削除します。\r\nよろしいですか？" },
                                    ]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadHealthInsuranceAvgearn().done(function () {
                                        return dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadHealthInsuranceAvgearn = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    h.service.findHealthInsuranceAvgEarn(self.healthInsuranceRateModel.historyId).done(function (res) {
                                        var salMin = 0;
                                        res.listHealthInsuranceAvgearnDto.forEach(function (item) {
                                            self.listHealthInsuranceAvgearn.push(new HealthInsuranceAvgEarnModel(item.grade, item.avgEarn, salMin, item.upperLimit, new HealthInsuranceAvgEarnValueModel(item.personalAvg.healthGeneralMny, item.personalAvg.healthNursingMny, item.personalAvg.healthBasicMny, item.personalAvg.healthSpecificMny), new HealthInsuranceAvgEarnValueModel(item.companyAvg.healthGeneralMny, item.companyAvg.healthNursingMny, item.companyAvg.healthBasicMny, item.companyAvg.healthSpecificMny)));
                                            salMin = item.upperLimit;
                                        });
                                        self.dirty = new nts.uk.ui.DirtyChecker(self.listHealthInsuranceAvgearn);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var data = { historyId: self.healthInsuranceRateModel.historyId, listHealthInsuranceAvgearnDto: [] };
                                    self.listHealthInsuranceAvgearn().forEach(function (item) {
                                        data.listHealthInsuranceAvgearnDto.push(ko.toJS(item));
                                    });
                                    return data;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    if (!nts.uk.ui._viewModel.errors.isEmpty()) {
                                        return;
                                    }
                                    h.service.updateHealthInsuranceAvgearn(self.collectData(), self.healthInsuranceRateModel.officeCode).done(function () {
                                        return self.closeDialog();
                                    });
                                };
                                ScreenModel.prototype.clearError = function () {
                                    $('.has-error').ntsError('clear');
                                };
                                ScreenModel.prototype.reCalculate = function () {
                                    var self = this;
                                    self.clearError();
                                    self.listHealthInsuranceAvgearn.removeAll();
                                    h.service.recalculateHealthInsuranceAvgearn(self.healthInsuranceRateModel.historyId).done(function (res) {
                                        var salMin = 0;
                                        res.listHealthInsuranceAvgearnDto.forEach(function (item) {
                                            self.listHealthInsuranceAvgearn.push(new HealthInsuranceAvgEarnModel(item.grade, item.avgEarn, salMin, item.upperLimit, new HealthInsuranceAvgEarnValueModel(item.personalAvg.healthGeneralMny, item.personalAvg.healthNursingMny, item.personalAvg.healthBasicMny, item.personalAvg.healthSpecificMny), new HealthInsuranceAvgEarnValueModel(item.companyAvg.healthGeneralMny, item.companyAvg.healthNursingMny, item.companyAvg.healthBasicMny, item.companyAvg.healthSpecificMny)));
                                            salMin = item.upperLimit;
                                        });
                                    });
                                };
                                ScreenModel.prototype.closeDialogWithDirtyCheck = function () {
                                    var self = this;
                                    if (self.dirty.isDirty()) {
                                        nts.uk.ui.dialog.confirm(self.errorList()[0].message).ifYes(function () {
                                            self.dirty.reset();
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
                            var HealthInsuranceRateModel = (function () {
                                function HealthInsuranceRateModel(officeCode, officeName, historyId, startMonth, endMonth, autoCalculate, rateItems, roundingMethods) {
                                    this.officeCode = officeCode;
                                    this.officeName = officeName;
                                    this.historyId = historyId;
                                    this.startMonth = startMonth;
                                    this.endMonth = endMonth;
                                    this.autoCalculate = autoCalculate;
                                    this.rateItems = rateItems;
                                    this.roundingMethods = roundingMethods;
                                }
                                return HealthInsuranceRateModel;
                            }());
                            viewmodel.HealthInsuranceRateModel = HealthInsuranceRateModel;
                            var HealthInsuranceAvgEarnModel = (function () {
                                function HealthInsuranceAvgEarnModel(grade, avgEarn, lowerLimit, upperLimit, personalAvg, companyAvg) {
                                    this.grade = grade;
                                    this.avgEarn = avgEarn;
                                    this.lowerLimit = lowerLimit;
                                    this.upperLimit = upperLimit;
                                    this.companyAvg = companyAvg;
                                    this.personalAvg = personalAvg;
                                }
                                return HealthInsuranceAvgEarnModel;
                            }());
                            viewmodel.HealthInsuranceAvgEarnModel = HealthInsuranceAvgEarnModel;
                            var HealthInsuranceAvgEarnValueModel = (function () {
                                function HealthInsuranceAvgEarnValueModel(general, nursing, basic, specific) {
                                    this.healthGeneralMny = ko.observable(general);
                                    this.healthNursingMny = ko.observable(nursing);
                                    this.healthBasicMny = ko.observable(basic);
                                    this.healthSpecificMny = ko.observable(specific);
                                }
                                return HealthInsuranceAvgEarnValueModel;
                            }());
                            viewmodel.HealthInsuranceAvgEarnValueModel = HealthInsuranceAvgEarnValueModel;
                        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
                    })(h = qmm008.h || (qmm008.h = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.h.vm.js.map