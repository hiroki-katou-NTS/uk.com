module nts.uk.pr.view.qmm008.b {
    export module viewmodel {
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;
        import aservice = nts.uk.pr.view.qmm008.a.service;
        import HealthInsuranceRateDto = nts.uk.pr.view.qmm008.a.service.model.finder.HealthInsuranceRateDto;
        export class ScreenModel {
            getInsuranceOfficeItemDto: KnockoutObservable<InsuranceOfficeItemDto>;
            returnInsuranceOfficeItemDto: KnockoutObservable<InsuranceOfficeItemDto>;
            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<any>;
            selectedValue: KnockoutObservable<any>;
            //for input 
            officeCodeName: KnockoutObservable<string>;
            selectedDate: KnockoutObservable<string>;
            constructor(receiveOfficeItem: InsuranceOfficeItemDto) {
                var self = this;
                self.getInsuranceOfficeItemDto = ko.observable(receiveOfficeItem);
                self.returnInsuranceOfficeItemDto = ko.observable(null);

                //select options 
                self.listOptions = ko.observableArray([new optionsModel(1, "最新の履歴(2016/04)から引き継ぐ"), new optionsModel(2, "初めから作成する")]);

                self.selectedValue = ko.observable(new optionsModel(1, ""));

                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                // Reset child value
                //            nts.uk.ui.windows.setShared("childValue", null);
                self.officeCodeName = ko.observable(receiveOfficeItem.codeName);
                //TODO get current date time of system
                if(receiveOfficeItem.childs.length>0)
                self.selectedDate = ko.observable(self.getLastHistory(receiveOfficeItem));
                else {
                    self.selectedDate = ko.observable(new Date().getFullYear() + "/" + new Date().getMonth());
                }
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
                //TODO recheck check if selected time invalid
                if (!self.compareStringDate(self.getLastHistory(self.getInsuranceOfficeItemDto()), self.selectedDate())) {
                    alert("ER011");
                }
                else {
                    if (self.getInsuranceOfficeItemDto().childs.length > 0) {//update previous history
                        self.getInsuranceOfficeItemDto().childs[0].codeName = self.getLastHistory(self.getInsuranceOfficeItemDto()) + "~" + self.minusOneMonth(self.selectedDate());
                    }
                    //push new history 
                    self.getInsuranceOfficeItemDto().childs.unshift(
                        new InsuranceOfficeItemDto("", "code", (self.getInsuranceOfficeItemDto().childs.length + 1).toString(), [], self.selectedDate() + "~ 9999/12"));
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