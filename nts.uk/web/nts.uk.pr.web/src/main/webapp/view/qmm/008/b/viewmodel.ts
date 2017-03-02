module nts.uk.pr.view.qmm008.b {
    export module viewmodel {
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;
        import aservice = nts.uk.pr.view.qmm008.a.service;
        import HealthInsuranceRateDto = nts.uk.pr.view.qmm008.a.service.model.finder.HealthInsuranceRateDto;
        import PensionRateDto = nts.uk.pr.view.qmm008.a.service.model.finder.PensionRateDto;
        export class ScreenModel {
            getInsuranceOfficeItemDto: KnockoutObservable<InsuranceOfficeItemDto>;
            getInsuranceRateDto: KnockoutObservable<any>;
            getPreviousInsuranceRateDto: KnockoutObservable<any>;
            returnInsuranceOfficeItemDto: KnockoutObservable<InsuranceOfficeItemDto>;
            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<any>;
            selectedValue: KnockoutObservable<any>;
            //for input 
            officeCodeName: KnockoutObservable<string>;
            selectedDate: KnockoutObservable<string>;
            isHealth: KnockoutObservable<boolean>;
            constructor(receiveOfficeItem: InsuranceOfficeItemDto, data: any, isHealth: boolean) {
                var self = this;
                self.getInsuranceOfficeItemDto = ko.observable(receiveOfficeItem);
                self.getInsuranceRateDto = ko.observable(data);
                self.getPreviousInsuranceRateDto = ko.observable(data);
                self.returnInsuranceOfficeItemDto = ko.observable(null);

                //select options 
                self.listOptions = ko.observableArray([new optionsModel(1, "最新の履歴(2016/04)から引き継ぐ"), new optionsModel(2, "初めから作成する")]);

                self.selectedValue = ko.observable(new optionsModel(1, ""));

                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                // Reset child value
                //            nts.uk.ui.windows.setShared("childValue", null);
                self.officeCodeName = ko.observable(receiveOfficeItem.codeName);
                //TODO get current date time of system
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

            public getLastHistory(OfficeItem: InsuranceOfficeItemDto) {
                if (OfficeItem.childs.length > 0) {
                    var index = OfficeItem.childs[0].codeName.indexOf("~");
                    var lastHistory = OfficeItem.childs[0].codeName.substring(0, index);
                    return lastHistory;
                }
                else
                    return "";
            }
            public minusOneMonth(stringDate: string) {
                var index = stringDate.indexOf("/");
                var year = stringDate.substr(0, index);
                var month = (Number(stringDate.substring(index + 1, stringDate.length)) - 1).toString();
                if (month == "0") {
                    year = (Number(year) - 1).toString();
                }
                return month.length == 1 ? year + "/0" + month : year + "/" + month;
            }
            public clickSettingButton() {
                var self = this;
                var updateFlag = false;
                //TODO save history service
                //add history to Health data
                //check if health
                if (self.isHealth()) {
                    //create new history
                    if (!self.compareStringDate(self.getLastHistory(self.getInsuranceOfficeItemDto()), self.selectedDate())) {
                        alert("ER011");
                    }
                    else {
                        var healthData: HealthInsuranceRateDto = self.getInsuranceRateDto();
                        var backupHistoryId = healthData.historyId;
                        var backupStartMonth = healthData.startMonth;
                        var backupEndMonth = healthData.endMonth;

                        healthData.historyId = "";
                        healthData.startMonth = self.selectedDate();
                        healthData.endMonth = "9999/12";
                        aservice.registerHealthRate(healthData).done(function() {
                            //update previous
                            if (self.getInsuranceOfficeItemDto().childs.length > 0) {
                                var previousHealthData: HealthInsuranceRateDto = self.getPreviousInsuranceRateDto();
                                previousHealthData.historyId = backupHistoryId;
                                previousHealthData.startMonth = backupStartMonth;
                                previousHealthData.endMonth = self.minusOneMonth(self.selectedDate());
                                aservice.updateHealthRate(previousHealthData).done(function() {
                                });
                            }
                        });
                    }
                }
                //is pension 
                else {
                    //create new history
                    if (!self.compareStringDate(self.getLastHistory(self.getInsuranceOfficeItemDto()), self.selectedDate())) {
                        alert("ER011");
                    }
                    else {
                        var pensionData: PensionRateDto = self.getInsuranceRateDto();
                        var backupHistoryId = pensionData.historyId;
                        var backupStartMonth = pensionData.startMonth;
                        var backupEndMonth = pensionData.endMonth;

                        pensionData.historyId = "";
                        pensionData.startMonth = self.selectedDate();
                        pensionData.endMonth = "9999/12";
                        aservice.registerPensionRate(pensionData).done(function() {
                            //update previous
                            if (self.getInsuranceOfficeItemDto().childs.length > 0) {
                                var previousPensionData: PensionRateDto = self.getPreviousInsuranceRateDto();
                                previousPensionData.historyId = backupHistoryId;
                                previousPensionData.startMonth = backupStartMonth;
                                previousPensionData.endMonth = self.minusOneMonth(self.selectedDate());
                                aservice.updatePensionRate(previousPensionData).done(function() {
                                });
                            }
                        });
                    }
                }
                //TODO recheck check if selected time invalid
                if (!self.compareStringDate(self.getLastHistory(self.getInsuranceOfficeItemDto()), self.selectedDate())) {
                    alert("ER011");
                }
                else {
                    nts.uk.ui.windows.setShared("addHistoryChildValue", self.getInsuranceOfficeItemDto(), true);
                    nts.uk.ui.windows.close();
                }
            }

            //compare 2 string date time
            public compareStringDate(date1: string, date2: string) {
                if (date1 != "") {
                    var index1 = date1.indexOf("/");
                    var year1 = Number(date1.substring(0, index1));
                    var month1 = Number(date1.substring(index1 + 1, date1.length));

                    var index2 = date2.indexOf("/");
                    var year2 = Number(date2.substring(0, index2));
                    var month2 = Number(date2.substring(index2 + 1, date2.length));
                    //compare year
                    if (year1 < year2) {
                        return true;
                    }
                    else {
                        //compare month
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
            }

            public closeDialog() {
                nts.uk.ui.windows.setShared("addHistoryChildValue", null, true);
                nts.uk.ui.windows.close();
            }
        }
        class optionsModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
    }
}