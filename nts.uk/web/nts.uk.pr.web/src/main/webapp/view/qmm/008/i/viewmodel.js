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
                            var commonService = nts.uk.pr.view.qmm008._0.common.service;
                            var ScreenModel = (function () {
                                function ScreenModel(dataOfSelectedOffice, pensionModel) {
                                    var self = this;
                                    self.listAvgEarnLevelMasterSetting = [];
                                    self.listPensionAvgearnModel = ko.observableArray([]);
                                    self.listPensionRateItemModel = pensionModel.rateItems;
                                    self.leftShow = ko.observable(true);
                                    self.rightShow = ko.observable(true);
                                    self.leftBtnText = ko.computed(function () { if (self.leftShow())
                                        return "—"; return "+"; });
                                    self.rightBtnText = ko.computed(function () { if (self.rightShow())
                                        return "—"; return "+"; });
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadAvgEarnLevelMasterSetting().done(function () {
                                        return self.loadPensionAvgearn().done(function () {
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
                                ScreenModel.prototype.loadPensionAvgearn = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    i.service.findPensionAvgearn('id').done(function (res) {
                                        res.forEach(function (item) {
                                            self.listPensionAvgearnModel.push(new PensionAvgearnModel(item.historyId, item.levelCode, new PensionAvgearnValueModel(item.companyFund.maleAmount, item.companyFund.femaleAmount, item.companyFund.unknownAmount), new PensionAvgearnValueModel(item.companyFundExemption.maleAmount, item.companyFundExemption.femaleAmount, item.companyFundExemption.unknownAmount), new PensionAvgearnValueModel(item.companyPension.maleAmount, item.companyPension.femaleAmount, item.companyPension.unknownAmount), new PensionAvgearnValueModel(item.personalFund.maleAmount, item.personalFund.femaleAmount, item.personalFund.unknownAmount), new PensionAvgearnValueModel(item.personalFundExemption.maleAmount, item.personalFundExemption.femaleAmount, item.personalFundExemption.unknownAmount), new PensionAvgearnValueModel(item.personalPension.maleAmount, item.personalPension.femaleAmount, item.personalPension.unknownAmount), item.childContributionAmount));
                                        });
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var data = [];
                                    self.listPensionAvgearnModel().forEach(function (item) {
                                        data.push(ko.toJS(item));
                                    });
                                    return data;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                    i.service.updatePensionAvgearn(this.collectData());
                                };
                                ScreenModel.prototype.leftToggle = function () {
                                    this.leftShow(!this.leftShow());
                                };
                                ScreenModel.prototype.rightToggle = function () {
                                    this.rightShow(!this.rightShow());
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var PensionAvgearnModel = (function () {
                                function PensionAvgearnModel(historyId, levelCode, companyFund, companyFundExemption, companyPension, personalFund, personalFundExemption, personalPension, childContributionAmount) {
                                    this.historyId = historyId;
                                    this.levelCode = levelCode;
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
