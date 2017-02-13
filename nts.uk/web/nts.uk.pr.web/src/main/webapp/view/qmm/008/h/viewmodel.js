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
                                function ScreenModel() {
                                    var self = this;
                                    self.healthInsuranceRateModel = ko.observable(new HealthInsuranceRateModel());
                                    self.listAvgEarnLevelMasterSetting = ko.observableArray([]);
                                    self.listHealthInsuranceAvgearn = ko.observableArray([]);
                                    self.rateItems = ko.observableArray([]);
                                    self.roundingMethods = ko.observableArray([]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    commonService.getAvgEarnLevelMasterSettingList().done(function (data) {
                                        self.listAvgEarnLevelMasterSetting(data);
                                        h.service.findHealthInsuranceRate('a').done(function (xx) {
                                            self.healthInsuranceRateModel().officeCode(xx.officeCode);
                                            self.healthInsuranceRateModel().officeName(xx.officeName);
                                            self.rateItems(xx.ratesItem);
                                        });
                                        h.service.findHealthInsuranceAvgEarn('a').done(function (zz) {
                                            self.listHealthInsuranceAvgearn(zz);
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
                                ScreenModel.prototype.loadHealthInsuranceAvgEarn = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    h.service.findHealthInsuranceAvgEarn('a').done(function (data) {
                                        data.forEach(function (item) {
                                        });
                                        self.listHealthInsuranceAvgearn(data);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var HealthInsuranceRateModel = (function () {
                                function HealthInsuranceRateModel() {
                                    this.companyCode = ko.observable('');
                                    this.officeCode = ko.observable('');
                                    this.officeName = ko.observable('');
                                    this.startMonth = ko.observable('');
                                    this.endMonth = ko.observable('');
                                    this.autoCalculate = ko.observable(false);
                                    this.maxAmount = ko.observable(0);
                                }
                                return HealthInsuranceRateModel;
                            }());
                            viewmodel.HealthInsuranceRateModel = HealthInsuranceRateModel;
                            var HealthInsuranceAvgEarnModel = (function () {
                                function HealthInsuranceAvgEarnModel() {
                                }
                                return HealthInsuranceAvgEarnModel;
                            }());
                            viewmodel.HealthInsuranceAvgEarnModel = HealthInsuranceAvgEarnModel;
                            var HealthInsuranceAvgEarnValueModel = (function () {
                                function HealthInsuranceAvgEarnValueModel() {
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
