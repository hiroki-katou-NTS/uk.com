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
                                function ScreenModel(dataOfSelectedOffice, healthModel) {
                                    var self = this;
                                    self.healthInsuranceRateModel = new HealthInsuranceRateModel();
                                    self.healthInsuranceRateModel.officeCode = dataOfSelectedOffice.code;
                                    self.healthInsuranceRateModel.officeName = dataOfSelectedOffice.name;
                                    self.listAvgEarnLevelMasterSetting = [];
                                    self.listHealthInsuranceAvgearn = ko.observableArray([]);
                                    self.rateItems = healthModel.rateItems();
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
                                    h.service.findHealthInsuranceAvgEarn('id').done(function (res) {
                                        res.forEach(function (item) {
                                            self.listHealthInsuranceAvgearn.push(new HealthInsuranceAvgEarnModel(item.historyId, item.levelCode, new HealthInsuranceAvgEarnValueModel(item.personalAvg.healthGeneralMny, item.personalAvg.healthNursingMny, item.personalAvg.healthBasicMny, item.personalAvg.healthSpecificMny), new HealthInsuranceAvgEarnValueModel(item.companyAvg.healthGeneralMny, item.companyAvg.healthNursingMny, item.companyAvg.healthBasicMny, item.companyAvg.healthSpecificMny)));
                                        });
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
                                    h.service.updateHealthInsuranceAvgearn(this.collectData());
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var HealthInsuranceRateModel = (function () {
                                function HealthInsuranceRateModel() {
                                }
                                return HealthInsuranceRateModel;
                            }());
                            viewmodel.HealthInsuranceRateModel = HealthInsuranceRateModel;
                            var HealthInsuranceAvgEarnModel = (function () {
                                function HealthInsuranceAvgEarnModel(historyId, levelCode, companyAvg, personalAvg) {
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
                                    this.general = ko.observable(general);
                                    this.nursing = ko.observable(nursing);
                                    this.basic = ko.observable(basic);
                                    this.specific = ko.observable(specific);
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
