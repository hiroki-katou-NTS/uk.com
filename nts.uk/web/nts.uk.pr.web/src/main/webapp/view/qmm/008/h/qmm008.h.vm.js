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
                            var commonService = nts.uk.pr.view.qmm008._0.common.service;
                            var ScreenModel = (function () {
                                function ScreenModel(officeName, healthModel) {
                                    var self = this;
                                    self.healthInsuranceRateModel = new HealthInsuranceRateModel(healthModel.officeCode(), officeName, healthModel.historyId, healthModel.startMonth(), healthModel.endMonth(), healthModel.autoCalculate(), healthModel.rateItems(), healthModel.roundingMethods());
                                    self.listAvgEarnLevelMasterSetting = [];
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
                                    self.loadAvgEarnLevelMasterSetting().done(function () {
                                        return self.loadHealthInsuranceAvgearn().done(function () {
                                            return dfd.resolve();
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadAvgEarnLevelMasterSetting = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    commonService.getAvgEarnLevelMasterSettingList().done(function (res) {
                                        self.listAvgEarnLevelMasterSetting = res;
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadHealthInsuranceAvgearn = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    h.service.findHealthInsuranceAvgEarn(self.healthInsuranceRateModel.historyId).done(function (res) {
                                        res.forEach(function (item) {
                                            self.listHealthInsuranceAvgearn.push(new HealthInsuranceAvgEarnModel(item.historyId, item.levelCode, new HealthInsuranceAvgEarnValueModel(item.personalAvg.healthGeneralMny, item.personalAvg.healthNursingMny, item.personalAvg.healthBasicMny, item.personalAvg.healthSpecificMny), new HealthInsuranceAvgEarnValueModel(item.companyAvg.healthGeneralMny, item.companyAvg.healthNursingMny, item.companyAvg.healthBasicMny, item.companyAvg.healthSpecificMny)));
                                        });
                                        self.dirty = new nts.uk.ui.DirtyChecker(self.listHealthInsuranceAvgearn);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var data = [];
                                    self.listHealthInsuranceAvgearn().forEach(function (item) {
                                        data.push(ko.toJS(item));
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
                                    self.listAvgEarnLevelMasterSetting.forEach(function (item) {
                                        self.listHealthInsuranceAvgearn.push(self.calculateHealthInsuranceAvgEarnModel(item));
                                    });
                                };
                                ScreenModel.prototype.calculateHealthInsuranceAvgEarnModel = function (levelMasterSetting) {
                                    var self = this;
                                    var historyId = self.healthInsuranceRateModel.historyId;
                                    var rateItems = self.healthInsuranceRateModel.rateItems;
                                    var roundingMethods = self.healthInsuranceRateModel.roundingMethods;
                                    var personalRounding = self.convertToRounding(roundingMethods.healthSalaryPersonalComboBoxSelectedCode());
                                    var companyRounding = self.convertToRounding(roundingMethods.healthSalaryCompanyComboBoxSelectedCode());
                                    var rate = levelMasterSetting.avgEarn / 1000;
                                    var autoCalculate = self.healthInsuranceRateModel.autoCalculate;
                                    if (autoCalculate == AutoCalculate.Auto) {
                                        return new HealthInsuranceAvgEarnModel(historyId, levelMasterSetting.code, new HealthInsuranceAvgEarnValueModel(self.rounding(personalRounding, rateItems.healthSalaryPersonalGeneral() * rate, Number.One), self.rounding(personalRounding, rateItems.healthSalaryPersonalNursing() * rate, Number.One), self.rounding(personalRounding, rateItems.healthSalaryPersonalBasic() * rate, Number.Three), self.rounding(personalRounding, rateItems.healthSalaryPersonalSpecific() * rate, Number.Three)), new HealthInsuranceAvgEarnValueModel(self.rounding(companyRounding, rateItems.healthSalaryCompanyGeneral() * rate, Number.One), self.rounding(companyRounding, rateItems.healthSalaryCompanyNursing() * rate, Number.One), self.rounding(companyRounding, rateItems.healthSalaryCompanyBasic() * rate, Number.Three), self.rounding(companyRounding, rateItems.healthSalaryCompanySpecific() * rate, Number.Three)));
                                    }
                                    else {
                                        return new HealthInsuranceAvgEarnModel(historyId, levelMasterSetting.code, new HealthInsuranceAvgEarnValueModel(Number.Zero, Number.Zero, Number.Zero, Number.Zero), new HealthInsuranceAvgEarnValueModel(Number.Zero, Number.Zero, Number.Zero, Number.Zero));
                                    }
                                };
                                ScreenModel.prototype.rounding = function (roudingMethod, roundValue, roundType) {
                                    var self = this;
                                    var getLevel = Math.pow(10, roundType);
                                    var backupValue = roundValue * (getLevel / 10);
                                    switch (roudingMethod) {
                                        case Rounding.ROUNDUP: return Math.ceil(backupValue) / (getLevel / 10);
                                        case Rounding.TRUNCATION: return Math.floor(backupValue) / (getLevel / 10);
                                        case Rounding.ROUNDDOWN:
                                            if ((backupValue * getLevel) % 10 > 5)
                                                return (Math.ceil(backupValue)) / (getLevel / 10);
                                            else
                                                return Math.floor(backupValue) / (getLevel / 10);
                                        case Rounding.DOWN4_UP5: return self.roudingDownUp(backupValue, 4) / (getLevel / 10);
                                        case Rounding.DOWN5_UP6: return self.roudingDownUp(backupValue, 5) / (getLevel / 10);
                                    }
                                };
                                ScreenModel.prototype.roudingDownUp = function (value, down) {
                                    var newVal = Math.round(value * 10) / 10;
                                    if ((newVal * 10) % 10 > down)
                                        return Math.ceil(value);
                                    else
                                        return Math.floor(value);
                                };
                                ScreenModel.prototype.convertToRounding = function (stringValue) {
                                    switch (stringValue) {
                                        case "0": return Rounding.ROUNDUP;
                                        case "1": return Rounding.TRUNCATION;
                                        case "2": return Rounding.DOWN4_UP5;
                                        case "3": return Rounding.ROUNDDOWN;
                                        case "4": return Rounding.DOWN5_UP6;
                                        default: return Rounding.ROUNDUP;
                                    }
                                };
                                ScreenModel.prototype.closeDialogWithDirtyCheck = function () {
                                    var self = this;
                                    if (self.dirty.isDirty()) {
                                        nts.uk.ui.dialog.confirm(self.errorList()[0].message).ifYes(function () {
                                            self.closeDialog();
                                            self.dirty.reset();
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
                                function HealthInsuranceAvgEarnModel(historyId, levelCode, personalAvg, companyAvg) {
                                    this.historyId = historyId;
                                    this.levelCode = levelCode;
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
                            var Rounding = (function () {
                                function Rounding() {
                                }
                                Rounding.ROUNDUP = 'RoundUp';
                                Rounding.TRUNCATION = 'Truncation';
                                Rounding.ROUNDDOWN = 'RoundDown';
                                Rounding.DOWN5_UP6 = 'Down5_Up6';
                                Rounding.DOWN4_UP5 = 'Down4_Up5';
                                return Rounding;
                            }());
                            viewmodel.Rounding = Rounding;
                            (function (Number) {
                                Number[Number["Zero"] = 0] = "Zero";
                                Number[Number["One"] = 1] = "One";
                                Number[Number["Three"] = 3] = "Three";
                            })(viewmodel.Number || (viewmodel.Number = {}));
                            var Number = viewmodel.Number;
                            (function (AutoCalculate) {
                                AutoCalculate[AutoCalculate["Auto"] = 0] = "Auto";
                                AutoCalculate[AutoCalculate["Manual"] = 1] = "Manual";
                            })(viewmodel.AutoCalculate || (viewmodel.AutoCalculate = {}));
                            var AutoCalculate = viewmodel.AutoCalculate;
                        })(viewmodel = h.viewmodel || (h.viewmodel = {}));
                    })(h = qmm008.h || (qmm008.h = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.h.vm.js.map