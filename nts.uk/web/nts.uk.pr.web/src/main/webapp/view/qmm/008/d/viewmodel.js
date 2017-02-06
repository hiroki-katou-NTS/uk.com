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
                    var d;
                    (function (d) {
                        var viewmodel;
                        (function (viewmodel) {
                            var commonService = nts.uk.pr.view.qmm008._0.common.service;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.listAvgEarnLevelMasterSetting = ko.observableArray();
                                    self.listHealthInsuranceAvgearn = ko.observableArray([
                                        { historyId: 1, levelCode: 1, companyAvg: { general: 123, nursing: 345, basic: 567, specific: 678 }, personalAvg: { general: 123, nursing: 345, basic: 567, specific: 678 } },
                                        { historyId: 2, levelCode: 2, companyAvg: { general: 444, nursing: 222, basic: 111, specific: 333 }, personalAvg: { general: 222, nursing: 444, basic: 555, specific: 666 } }
                                    ]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    commonService.getAvgEarnLevelMasterSettingList().done(function (data) {
                                        self.listAvgEarnLevelMasterSetting(data);
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
                                    console.log(data);
                                    return data;
                                };
                                ScreenModel.prototype.save = function () {
                                    var self = this;
                                };
                                ScreenModel.prototype.loadListHealthInsuranceAvgEarn = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    d.service.getListHealthInsuranceAvgEarn().done(function (data) {
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
                        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
                    })(d = qmm008.d || (qmm008.d = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
