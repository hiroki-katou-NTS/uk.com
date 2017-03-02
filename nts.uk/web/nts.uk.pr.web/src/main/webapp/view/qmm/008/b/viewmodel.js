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
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var aservice = nts.uk.pr.view.qmm008.a.service;
                            var ScreenModel = (function () {
                                function ScreenModel(receiveOfficeItem, data, isHealth) {
                                    var self = this;
                                    self.getInsuranceOfficeItemDto = ko.observable(receiveOfficeItem);
                                    self.getInsuranceRateDto = ko.observable(data);
                                    self.getPreviousInsuranceRateDto = ko.observable(data);
                                    self.returnInsuranceOfficeItemDto = ko.observable(null);
                                    self.listOptions = ko.observableArray([new optionsModel(1, "最新の履歴(2016/04)から引き継ぐ"), new optionsModel(2, "初めから作成する")]);
                                    self.selectedValue = ko.observable(new optionsModel(1, ""));
                                    self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                                    self.officeCodeName = ko.observable(receiveOfficeItem.codeName);
                                    if (receiveOfficeItem.childs.length > 0)
                                        self.selectedDate = ko.observable(self.getLastHistory(receiveOfficeItem));
                                    else {
                                        if (new Date().getMonth().toString().length == 1) {
                                            self.selectedDate = ko.observable(new Date().getFullYear() + "/0" + new Date().getMonth());
                                        }
                                        else {
                                            self.selectedDate = ko.observable(new Date().getFullYear() + "/" + new Date().getMonth());
                                        }
                                    }
                                    self.isHealth = ko.observable(isHealth);
                                }
                                ScreenModel.prototype.getLastHistory = function (OfficeItem) {
                                    if (OfficeItem.childs.length > 0) {
                                        var index = OfficeItem.childs[0].codeName.indexOf("~");
                                        var lastHistory = OfficeItem.childs[0].codeName.substring(0, index);
                                        return lastHistory;
                                    }
                                    else
                                        return "";
                                };
                                ScreenModel.prototype.minusOneMonth = function (stringDate) {
                                    var index = stringDate.indexOf("/");
                                    var year = stringDate.substr(0, index);
                                    var month = (Number(stringDate.substring(index + 1, stringDate.length)) - 1).toString();
                                    if (month == "0") {
                                        year = (Number(year) - 1).toString();
                                    }
                                    return month.length == 1 ? year + "/0" + month : year + "/" + month;
                                };
                                ScreenModel.prototype.clickSettingButton = function () {
                                    var self = this;
                                    var updateFlag = false;
                                    if (self.isHealth()) {
                                        if (!self.compareStringDate(self.getLastHistory(self.getInsuranceOfficeItemDto()), self.selectedDate())) {
                                            alert("ER011");
                                        }
                                        else {
                                            var healthData = self.getInsuranceRateDto();
                                            var backupHistoryId = healthData.historyId;
                                            var backupStartMonth = healthData.startMonth;
                                            var backupEndMonth = healthData.endMonth;
                                            healthData.historyId = "";
                                            healthData.startMonth = self.selectedDate();
                                            healthData.endMonth = "9999/12";
                                            aservice.registerHealthRate(healthData).done(function () {
                                                if (self.getInsuranceOfficeItemDto().childs.length > 0) {
                                                    var previousHealthData = self.getPreviousInsuranceRateDto();
                                                    previousHealthData.historyId = backupHistoryId;
                                                    previousHealthData.startMonth = backupStartMonth;
                                                    previousHealthData.endMonth = self.minusOneMonth(self.selectedDate());
                                                    aservice.updateHealthRate(previousHealthData).done(function () {
                                                    });
                                                }
                                            });
                                        }
                                    }
                                    else {
                                        if (!self.compareStringDate(self.getLastHistory(self.getInsuranceOfficeItemDto()), self.selectedDate())) {
                                            alert("ER011");
                                        }
                                        else {
                                            var pensionData = self.getInsuranceRateDto();
                                            var backupHistoryId = pensionData.historyId;
                                            var backupStartMonth = pensionData.startMonth;
                                            var backupEndMonth = pensionData.endMonth;
                                            pensionData.historyId = "";
                                            pensionData.startMonth = self.selectedDate();
                                            pensionData.endMonth = "9999/12";
                                            aservice.registerPensionRate(pensionData).done(function () {
                                                if (self.getInsuranceOfficeItemDto().childs.length > 0) {
                                                    var previousPensionData = self.getPreviousInsuranceRateDto();
                                                    previousPensionData.historyId = backupHistoryId;
                                                    previousPensionData.startMonth = backupStartMonth;
                                                    previousPensionData.endMonth = self.minusOneMonth(self.selectedDate());
                                                    aservice.updatePensionRate(previousPensionData).done(function () {
                                                    });
                                                }
                                            });
                                        }
                                    }
                                    if (!self.compareStringDate(self.getLastHistory(self.getInsuranceOfficeItemDto()), self.selectedDate())) {
                                        alert("ER011");
                                    }
                                    else {
                                        nts.uk.ui.windows.setShared("addHistoryChildValue", self.getInsuranceOfficeItemDto(), true);
                                        nts.uk.ui.windows.close();
                                    }
                                };
                                ScreenModel.prototype.compareStringDate = function (date1, date2) {
                                    if (date1 != "") {
                                        var index1 = date1.indexOf("/");
                                        var year1 = Number(date1.substring(0, index1));
                                        var month1 = Number(date1.substring(index1 + 1, date1.length));
                                        var index2 = date2.indexOf("/");
                                        var year2 = Number(date2.substring(0, index2));
                                        var month2 = Number(date2.substring(index2 + 1, date2.length));
                                        if (year1 < year2) {
                                            return true;
                                        }
                                        else {
                                            if (month1 + 1 < month2) {
                                                return true;
                                            }
                                            else {
                                                return false;
                                            }
                                        }
                                    }
                                    else {
                                        return true;
                                    }
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.setShared("addHistoryChildValue", null, true);
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var optionsModel = (function () {
                                function optionsModel(id, name) {
                                    var self = this;
                                    self.id = id;
                                    self.name = name;
                                }
                                return optionsModel;
                            }());
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qmm008.b || (qmm008.b = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
