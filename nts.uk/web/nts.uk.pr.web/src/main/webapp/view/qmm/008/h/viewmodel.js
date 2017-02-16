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
                                    self.healthInsuranceRateModel = ko.observable(new HealthInsuranceRateModel());
                                    self.listAvgEarnLevelMasterSetting = ko.observableArray([]);
                                    self.listHealthInsuranceAvgearn = ko.observableArray([]);
                                    self.rateItems = healthModel.rateItems();
                                    self.roundingMethods = ko.observableArray([]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.loadAvgEarnLevelMasterSetting().done(function () {
                                        return self.loadHealthInsuranceRate().done(function () {
                                            return self.loadHealthInsuranceAvgearn().done(function () {
                                                return dfd.resolve();
                                            });
                                        });
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadAvgEarnLevelMasterSetting = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    commonService.getAvgEarnLevelMasterSettingList().done(function (res) {
                                        self.listAvgEarnLevelMasterSetting(res);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadHealthInsuranceRate = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    h.service.findHealthInsuranceRate('id').done(function (res) {
                                        self.healthInsuranceRateModel().officeCode = res.officeCode;
                                        self.healthInsuranceRateModel().officeName = res.officeName;
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.loadHealthInsuranceAvgearn = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    h.service.findHealthInsuranceAvgEarn('id').done(function (res) {
                                        self.listHealthInsuranceAvgearn(res);
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
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoidmlld21vZGVsLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsidmlld21vZGVsLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLElBQU8sR0FBRyxDQTZIVDtBQTdIRCxXQUFPLEdBQUc7SUFBQyxJQUFBLEVBQUUsQ0E2SFo7SUE3SFUsV0FBQSxFQUFFO1FBQUMsSUFBQSxFQUFFLENBNkhmO1FBN0hhLFdBQUEsRUFBRTtZQUFDLElBQUEsSUFBSSxDQTZIcEI7WUE3SGdCLFdBQUEsSUFBSTtnQkFBQyxJQUFBLE1BQU0sQ0E2SDNCO2dCQTdIcUIsV0FBQSxNQUFNO29CQUFDLElBQUEsQ0FBQyxDQTZIN0I7b0JBN0g0QixXQUFBLENBQUMsRUFBQyxDQUFDO3dCQUM1QixJQUFjLFNBQVMsQ0EySHRCO3dCQTNIRCxXQUFjLFNBQVMsRUFBQyxDQUFDOzRCQUNyQixJQUFPLGFBQWEsR0FBRyxHQUFHLENBQUMsRUFBRSxDQUFDLEVBQUUsQ0FBQyxJQUFJLENBQUMsTUFBTSxDQUFDLEVBQUUsQ0FBQyxNQUFNLENBQUMsT0FBTyxDQUFDOzRCQUsvRDtnQ0FPSSxxQkFBWSxvQkFBb0IsRUFBRSxXQUFXO29DQUN6QyxJQUFJLElBQUksR0FBRyxJQUFJLENBQUM7b0NBQ2hCLElBQUksQ0FBQyx3QkFBd0IsR0FBRyxFQUFFLENBQUMsVUFBVSxDQUFDLElBQUksd0JBQXdCLEVBQUUsQ0FBQyxDQUFDO29DQUM5RSxJQUFJLENBQUMsNkJBQTZCLEdBQUcsRUFBRSxDQUFDLGVBQWUsQ0FBQyxFQUFFLENBQUMsQ0FBQztvQ0FDNUQsSUFBSSxDQUFDLDBCQUEwQixHQUFHLEVBQUUsQ0FBQyxlQUFlLENBQUMsRUFBRSxDQUFDLENBQUM7b0NBQ3pELElBQUksQ0FBQyxTQUFTLEdBQUcsV0FBVyxDQUFDLFNBQVMsRUFBRSxDQUFDO29DQUN6QyxJQUFJLENBQUMsZUFBZSxHQUFHLEVBQUUsQ0FBQyxlQUFlLENBQUMsRUFBRSxDQUFDLENBQUM7Z0NBRWxELENBQUM7Z0NBS00s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QUFFLENBQUMsRUFBRSxDQUFDLE9BQU8sQ0FBQyxLQUFLLEVBQUUsQ0FBQztnQ0FDOUIsQ0FBQztnQ0FDTCxrQkFBQzs0QkFBRCxDQUFDLEFBaEdELElBZ0dDOzRCQWhHWSxxQkFBVyxjQWdHdkIsQ0FBQTs0QkFFRDtnQ0FBQTtnQ0FLQSxDQUFDO2dDQUFELCtCQUFDOzRCQUFELENBQUMsQUFMRCxJQUtDOzRCQUxZLGtDQUF3QiwyQkFLcEMsQ0FBQTs0QkFDRDtnQ0FBQTtnQ0FLQSxDQUFDO2dDQUFELGtDQUFDOzRCQUFELENBQUMsQUFMRCxJQUtDOzRCQUxZLHFDQUEyQiw4QkFLdkMsQ0FBQTs0QkFDRDtnQ0FBQTtnQ0FLQSxDQUFDO2dDQUFELHVDQUFDOzRCQUFELENBQUMsQUFMRCxJQUtDOzRCQUxZLDBDQUFnQyxtQ0FLNUMsQ0FBQTt3QkFFTCxDQUFDLEVBM0hhLFNBQVMsR0FBVCxXQUFTLEtBQVQsV0FBUyxRQTJIdEI7b0JBQ0wsQ0FBQyxFQTdINEIsQ0FBQyxHQUFELFFBQUMsS0FBRCxRQUFDLFFBNkg3QjtnQkFBRCxDQUFDLEVBN0hxQixNQUFNLEdBQU4sV0FBTSxLQUFOLFdBQU0sUUE2SDNCO1lBQUQsQ0FBQyxFQTdIZ0IsSUFBSSxHQUFKLE9BQUksS0FBSixPQUFJLFFBNkhwQjtRQUFELENBQUMsRUE3SGEsRUFBRSxHQUFGLEtBQUUsS0FBRixLQUFFLFFBNkhmO0lBQUQsQ0FBQyxFQTdIVSxFQUFFLEdBQUYsTUFBRSxLQUFGLE1BQUUsUUE2SFo7QUFBRCxDQUFDLEVBN0hNLEdBQUcsS0FBSCxHQUFHLFFBNkhUIiwic291cmNlc0NvbnRlbnQiOlsibW9kdWxlIG50cy51ay5wci52aWV3LnFtbTAwOC5oIHtcclxuICAgIGV4cG9ydCBtb2R1bGUgdmlld21vZGVsIHtcclxuICAgICAgICBpbXBvcnQgY29tbW9uU2VydmljZSA9IG50cy51ay5wci52aWV3LnFtbTAwOC5fMC5jb21tb24uc2VydmljZTtcclxuICAgICAgICBpbXBvcnQgQXZnRWFybkxldmVsTWFzdGVyU2V0dGluZ0R0byA9IG50cy51ay5wci52aWV3LnFtbTAwOC5fMC5jb21tb24uc2VydmljZS5tb2RlbC5BdmdFYXJuTGV2ZWxNYXN0ZXJTZXR0aW5nRHRvO1xyXG4gICAgICAgIGltcG9ydCBIZWFsdGhJbnN1cmFuY2VBdmdFYXJuRHRvID0gc2VydmljZS5tb2RlbC5IZWFsdGhJbnN1cmFuY2VBdmdFYXJuRHRvO1xyXG4gICAgICAgIGltcG9ydCBIZWFsdGhJbnN1cmFuY2VBdmdFYXJuVmFsdWUgPSBzZXJ2aWNlLm1vZGVsLkhlYWx0aEluc3VyYW5jZUF2Z0Vhcm5WYWx1ZTtcclxuXHJcbiAgICAgICAgZXhwb3J0IGNsYXNzIFNjcmVlbk1vZGVsIHtcclxuICAgICAgICAgICAgbGlzdEF2Z0Vhcm5MZXZlbE1hc3RlclNldHRpbmc6IEtub2Nrb3V0T2JzZXJ2YWJsZUFycmF5PGFueT47XHJcbiAgICAgICAgICAgIGxpc3RIZWFsdGhJbnN1cmFuY2VBdmdlYXJuOiBLbm9ja291dE9ic2VydmFibGVBcnJheTxhbnk+O1xyXG4gICAgICAgICAgICBoZWFsdGhJbnN1cmFuY2VSYXRlTW9kZWw6IEtub2Nrb3V0T2JzZXJ2YWJsZTxIZWFsdGhJbnN1cmFuY2VSYXRlTW9kZWw+O1xyXG4gICAgICAgICAgICByYXRlSXRlbXM6IGFueTtcclxuICAgICAgICAgICAgcm91bmRpbmdNZXRob2RzOiBLbm9ja291dE9ic2VydmFibGVBcnJheTxhbnk+O1xyXG5cclxuICAgICAgICAgICAgY29uc3RydWN0b3IoZGF0YU9mU2VsZWN0ZWRPZmZpY2UsIGhlYWx0aE1vZGVsKSB7XHJcbiAgICAgICAgICAgICAgICB2YXIgc2VsZiA9IHRoaXM7XHJcbiAgICAgICAgICAgICAgICBzZWxmLmhlYWx0aEluc3VyYW5jZVJhdGVNb2RlbCA9IGtvLm9ic2VydmFibGUobmV3IEhlYWx0aEluc3VyYW5jZVJhdGVNb2RlbCgpKTtcclxuICAgICAgICAgICAgICAgIHNlbGYubGlzdEF2Z0Vhcm5MZXZlbE1hc3RlclNldHRpbmcgPSBrby5vYnNlcnZhYmxlQXJyYXkoW10pO1xyXG4gICAgICAgICAgICAgICAgc2VsZi5saXN0SGVhbHRoSW5zdXJhbmNlQXZnZWFybiA9IGtvLm9ic2VydmFibGVBcnJheShbXSk7XHJcbiAgICAgICAgICAgICAgICBzZWxmLnJhdGVJdGVtcyA9IGhlYWx0aE1vZGVsLnJhdGVJdGVtcygpO1xyXG4gICAgICAgICAgICAgICAgc2VsZi5yb3VuZGluZ01ldGhvZHMgPSBrby5vYnNlcnZhYmxlQXJyYXkoW10pO1xyXG5cclxuICAgICAgICAgICAgfVxyXG5cclxuICAgICAgICAgICAgLyoqXHJcbiAgICAgICAgICAgICAqIFN0YXJ0IHBhZ2UuXHJcbiAgICAgICAgICAgICAqL1xyXG4gICAgICAgICAgICBwdWJsaWMgc3RhcnRQYWdlKCk6IEpRdWVyeVByb21pc2U8YW55PiB7XHJcbiAgICAgICAgICAgICAgICB2YXIgc2VsZiA9IHRoaXM7XHJcbiAgICAgICAgICAgICAgICB2YXIgZGZkID0gJC5EZWZlcnJlZCgpO1xyXG4gICAgICAgICAgICAgICAgc2VsZi5sb2FkQXZnRWFybkxldmVsTWFzdGVyU2V0dGluZygpLmRvbmUoKCkgPT5cclxuICAgICAgICAgICAgICAgICAgICBzZWxmLmxvYWRIZWFsdGhJbnN1cmFuY2VSYXRlKCkuZG9uZSgoKSA9PlxyXG4gICAgICAgICAgICAgICAgICAgICAgICBzZWxmLmxvYWRIZWFsdGhJbnN1cmFuY2VBdmdlYXJuKCkuZG9uZSgoKSA9PlxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgZGZkLnJlc29sdmUoKSkpKTtcclxuICAgICAgICAgICAgICAgIHJldHVybiBkZmQucHJvbWlzZSgpO1xyXG4gICAgICAgICAgICB9XHJcblxyXG4gICAgICAgICAgICAvKipcclxuICAgICAgICAgICAgICogTG9hZCBBdmdFYXJuTGV2ZWxNYXN0ZXJTZXR0aW5nIGxpc3QuXHJcbiAgICAgICAgICAgICAqL1xyXG4gICAgICAgICAgICBwcml2YXRlIGxvYWRBdmdFYXJuTGV2ZWxNYXN0ZXJTZXR0aW5nKCk6IEpRdWVyeVByb21pc2U8YW55PiB7XHJcbiAgICAgICAgICAgICAgICB2YXIgc2VsZiA9IHRoaXM7XHJcbiAgICAgICAgICAgICAgICB2YXIgZGZkID0gJC5EZWZlcnJlZDxhbnk+KCk7XHJcbiAgICAgICAgICAgICAgICBjb21tb25TZXJ2aWNlLmdldEF2Z0Vhcm5MZXZlbE1hc3RlclNldHRpbmdMaXN0KCkuZG9uZShyZXMgPT4ge1xyXG4gICAgICAgICAgICAgICAgICAgIHNlbGYubGlzdEF2Z0Vhcm5MZXZlbE1hc3RlclNldHRpbmcocmVzKTtcclxuICAgICAgICAgICAgICAgICAgICBkZmQucmVzb2x2ZSgpO1xyXG4gICAgICAgICAgICAgICAgfSk7XHJcbiAgICAgICAgICAgICAgICByZXR1cm4gZGZkLnByb21pc2UoKTtcclxuICAgICAgICAgICAgfVxyXG5cclxuICAgICAgICAgICAgLyoqXHJcbiAgICAgICAgICAgICAqIExvYWQgaGVhbHRoSW5zdXJhbmNlUmF0ZS5cclxuICAgICAgICAgICAgICovXHJcbiAgICAgICAgICAgIHByaXZhdGUgbG9hZEhlYWx0aEluc3VyYW5jZVJhdGUoKTogSlF1ZXJ5UHJvbWlzZTxhbnk+IHtcclxuICAgICAgICAgICAgICAgIHZhciBzZWxmID0gdGhpcztcclxuICAgICAgICAgICAgICAgIHZhciBkZmQgPSAkLkRlZmVycmVkPGFueT4oKTtcclxuICAgICAgICAgICAgICAgIHNlcnZpY2UuZmluZEhlYWx0aEluc3VyYW5jZVJhdGUoJ2lkJykuZG9uZShyZXMgPT4ge1xyXG4gICAgICAgICAgICAgICAgICAgIHNlbGYuaGVhbHRoSW5zdXJhbmNlUmF0ZU1vZGVsKCkub2ZmaWNlQ29kZSA9IHJlcy5vZmZpY2VDb2RlO1xyXG4gICAgICAgICAgICAgICAgICAgIHNlbGYuaGVhbHRoSW5zdXJhbmNlUmF0ZU1vZGVsKCkub2ZmaWNlTmFtZSA9IHJlcy5vZmZpY2VOYW1lO1xyXG4gICAgICAgICAgICAgICAgICAgIGRmZC5yZXNvbHZlKCk7XHJcbiAgICAgICAgICAgICAgICB9KTtcclxuICAgICAgICAgICAgICAgIHJldHVybiBkZmQucHJvbWlzZSgpO1xyXG4gICAgICAgICAgICB9XHJcblxyXG4gICAgICAgICAgICAvKipcclxuICAgICAgICAgICAgICogTG9hZCBIZWFsdGhJbnN1cmFuY2VBdmdFYXJuLlxyXG4gICAgICAgICAgICAgKi9cclxuICAgICAgICAgICAgcHJpdmF0ZSBsb2FkSGVhbHRoSW5zdXJhbmNlQXZnZWFybigpOiBKUXVlcnlQcm9taXNlPGFueT4ge1xyXG4gICAgICAgICAgICAgICAgdmFyIHNlbGYgPSB0aGlzO1xyXG4gICAgICAgICAgICAgICAgdmFyIGRmZCA9ICQuRGVmZXJyZWQ8YW55PigpO1xyXG4gICAgICAgICAgICAgICAgc2VydmljZS5maW5kSGVhbHRoSW5zdXJhbmNlQXZnRWFybignaWQnKS5kb25lKHJlcyA9PiB7XHJcbiAgICAgICAgICAgICAgICAgICAgc2VsZi5saXN0SGVhbHRoSW5zdXJhbmNlQXZnZWFybihyZXMpO1xyXG4gICAgICAgICAgICAgICAgICAgIGRmZC5yZXNvbHZlKCk7XHJcbiAgICAgICAgICAgICAgICB9KTtcclxuICAgICAgICAgICAgICAgIHJldHVybiBkZmQucHJvbWlzZSgpO1xyXG4gICAgICAgICAgICB9XHJcblxyXG4gICAgICAgICAgICAvKipcclxuICAgICAgICAgICAgICogQ29sbGVjdCBkYXRhIGZyb20gaW5wdXQuXHJcbiAgICAgICAgICAgICAqL1xyXG4gICAgICAgICAgICBwcml2YXRlIGNvbGxlY3REYXRhKCk6IEFycmF5PEhlYWx0aEluc3VyYW5jZUF2Z0Vhcm5EdG8+IHtcclxuICAgICAgICAgICAgICAgIHZhciBzZWxmID0gdGhpcztcclxuICAgICAgICAgICAgICAgIHZhciBkYXRhID0gW107XHJcbiAgICAgICAgICAgICAgICBzZWxmLmxpc3RIZWFsdGhJbnN1cmFuY2VBdmdlYXJuKCkuZm9yRWFjaChpdGVtID0+IHtcclxuICAgICAgICAgICAgICAgICAgICBkYXRhLnB1c2goa28udG9KUyhpdGVtKSk7XHJcbiAgICAgICAgICAgICAgICB9KTtcclxuICAgICAgICAgICAgICAgIHJldHVybiBkYXRhO1xyXG4gICAgICAgICAgICB9XHJcblxyXG4gICAgICAgICAgICAvKipcclxuICAgICAgICAgICAgICogQ2FsbCBzZXJ2aWNlIHRvIHNhdmUgaGVhbHRoSW5zdWFyYW5jZUF2Z2Vhcm4uXHJcbiAgICAgICAgICAgICAqL1xyXG4gICAgICAgICAgICBwcml2YXRlIHNhdmUoKSB7XHJcbiAgICAgICAgICAgICAgICB2YXIgc2VsZiA9IHRoaXM7XHJcbiAgICAgICAgICAgICAgICBzZXJ2aWNlLnVwZGF0ZUhlYWx0aEluc3VyYW5jZUF2Z2Vhcm4odGhpcy5jb2xsZWN0RGF0YSgpKTtcclxuICAgICAgICAgICAgfVxyXG5cclxuICAgICAgICAgICAgLyoqXHJcbiAgICAgICAgICAgICAqIENsb3NlIGRpYWxvZy5cclxuICAgICAgICAgICAgICovXHJcbiAgICAgICAgICAgIHByaXZhdGUgY2xvc2VEaWFsb2coKSB7XHJcbiAgICAgICAgICAgICAgICBudHMudWsudWkud2luZG93cy5jbG9zZSgpO1xyXG4gICAgICAgICAgICB9XHJcbiAgICAgICAgfVxyXG5cclxuICAgICAgICBleHBvcnQgY2xhc3MgSGVhbHRoSW5zdXJhbmNlUmF0ZU1vZGVsIHtcclxuICAgICAgICAgICAgb2ZmaWNlQ29kZTogc3RyaW5nO1xyXG4gICAgICAgICAgICBvZmZpY2VOYW1lOiBzdHJpbmc7XHJcbiAgICAgICAgICAgIHN0YXJ0TW9udGg6IHN0cmluZztcclxuICAgICAgICAgICAgZW5kTW9udGg6IHN0cmluZztcclxuICAgICAgICB9XHJcbiAgICAgICAgZXhwb3J0IGNsYXNzIEhlYWx0aEluc3VyYW5jZUF2Z0Vhcm5Nb2RlbCB7XHJcbiAgICAgICAgICAgIGhpc3RvcnlJZDogc3RyaW5nO1xyXG4gICAgICAgICAgICBsZXZlbENvZGU6IG51bWJlcjtcclxuICAgICAgICAgICAgY29tcGFueUF2ZzogSGVhbHRoSW5zdXJhbmNlQXZnRWFyblZhbHVlTW9kZWw7XHJcbiAgICAgICAgICAgIHBlcnNvbmFsQXZnOiBIZWFsdGhJbnN1cmFuY2VBdmdFYXJuVmFsdWVNb2RlbDtcclxuICAgICAgICB9XHJcbiAgICAgICAgZXhwb3J0IGNsYXNzIEhlYWx0aEluc3VyYW5jZUF2Z0Vhcm5WYWx1ZU1vZGVsIHtcclxuICAgICAgICAgICAgZ2VuZXJhbDogS25vY2tvdXRPYnNlcnZhYmxlPG51bWJlcj47XHJcbiAgICAgICAgICAgIG51cnNpbmc6IEtub2Nrb3V0T2JzZXJ2YWJsZTxudW1iZXI+O1xyXG4gICAgICAgICAgICBiYXNpYzogS25vY2tvdXRPYnNlcnZhYmxlPG51bWJlcj47XHJcbiAgICAgICAgICAgIHNwZWNpZmljOiBLbm9ja291dE9ic2VydmFibGU8bnVtYmVyPjtcclxuICAgICAgICB9XHJcblxyXG4gICAgfVxyXG59Il19