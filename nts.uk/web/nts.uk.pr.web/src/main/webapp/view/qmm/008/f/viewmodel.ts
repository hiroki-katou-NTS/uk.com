module nts.uk.pr.view.qmm008.f {
    export module viewmodel {
        import InsuranceOfficeItemDto = nts.uk.pr.view.qmm008.a.service.model.finder.InsuranceOfficeItemDto;
        import aservice = nts.uk.pr.view.qmm008.a.service;
        import HealthInsuranceRateDto = nts.uk.pr.view.qmm008.a.service.model.finder.HealthInsuranceRateDto;
        import PensionRateDto = nts.uk.pr.view.qmm008.a.service.model.finder.PensionRateDto;
        export class ScreenModel {
            OfficeItemModel: KnockoutObservable<InsuranceOfficeItemDto>;
            selectedHistoryCode: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            previousStartMonth: KnockoutObservable<string>;

            modalValue: KnockoutObservable<string>;
            isTransistReturnData: KnockoutObservable<boolean>;

            // for group radio button
            listOptions: KnockoutObservableArray<any>;
            selectedValue: KnockoutObservable<any>;
            officeCodeName: KnockoutObservable<string>;
            isHealth: KnockoutObservable<boolean>;
            isRemove: KnockoutObservable<boolean>;
            constructor(receiveOfficeItem: InsuranceOfficeItemDto, selectedHistoryCode: string, isHealthParentValue: boolean) {
                var self = this;
                self.OfficeItemModel = ko.observable(receiveOfficeItem);
                self.selectedHistoryCode = ko.observable(selectedHistoryCode);

                self.listOptions = ko.observableArray([new optionsModel(1, "履歴を削除する"), new optionsModel(2, "履歴を修正する")]);
                self.selectedValue = ko.observable(self.listOptions()[1]);

                self.modalValue = ko.observable("Goodbye world!");
                self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
                // Reset child value
                if (receiveOfficeItem != null) {
                    this.officeCodeName = ko.observable(receiveOfficeItem.codeName);
                }
                self.startMonth = ko.observable('');
                self.endMonth = ko.observable('');
                self.previousStartMonth = ko.observable('');
                self.getDate();
                self.selectedValue.subscribe(function(selectedValue: optionsModel) {
                    if (selectedValue.id == self.listOptions()[0].id) {
                        //TODO disable date picker  
                        self.isRemove(true);
                    }
                    else {
                        //
                        self.isRemove(false);
                    }
                });
                self.isHealth = ko.observable(isHealthParentValue);
                self.isRemove = ko.observable(false);
            }

            //get start and end  date
            getDate() {
                var self = this;
                self.OfficeItemModel().childs.forEach(function(item, index) {
                    if (item.code == self.selectedHistoryCode()) {
                        //get previous startMonth
                        var previousViewRangeString = self.OfficeItemModel().childs[index + 1].codeName;
                        var previousRangeCharIndex = previousViewRangeString.indexOf("~");
                        self.previousStartMonth(previousViewRangeString.substr(0, previousRangeCharIndex));
                        //get start and end month
                        var viewRangeString = self.OfficeItemModel().childs[index].codeName;
                        var rangeCharIndex = viewRangeString.indexOf("~");
                        self.endMonth(viewRangeString.substr(rangeCharIndex + 1, viewRangeString.length));
                        self.startMonth(viewRangeString.substr(0, rangeCharIndex));
                    }
                });
            }

            //compare 2 string date time
            public compareStringDate(date1: string, date2: string) {
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
                    if (month1 < month2) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
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

            public convertMonth(stringDate: string) {
                var year = stringDate.substr(0, 4);
                var month = stringDate.substring(4, stringDate.length);
                return year + "/" + month;
            }

            // click button setting
            clickSettingButton() {
                var self = this;
                //TODO check health or pension
                if (self.isHealth()) {
                    if (self.isRemove()) {
                        //remove current history  
                        aservice.removeHealthRate(self.OfficeItemModel().childs[0].code);
                        //update previous history
                        aservice.getHealthInsuranceItemDetail(self.OfficeItemModel().childs[1].code).done(function(data: HealthInsuranceRateDto) {
                            //update previous end month to 9999/12
                            data.endMonth = "9999/12";
                            data.startMonth = self.convertMonth(data.startMonth);
                            aservice.updateHealthRate(data);
                        });
                    }
                    else {
                        //compare previous startMonth and startMonth -1
                        if (self.compareStringDate(self.previousStartMonth(), self.minusOneMonth(self.startMonth()))) {
                            //update current history
                            aservice.getHealthInsuranceItemDetail(self.OfficeItemModel().childs[0].code).done(function(data: HealthInsuranceRateDto) {
                                //
                                data.startMonth = self.startMonth();
                                data.endMonth = self.convertMonth(data.endMonth);
                                aservice.updateHealthRate(data);
                            });
                            //TODO update previous history
                            aservice.getHealthInsuranceItemDetail(self.OfficeItemModel().childs[1].code).done(function(data: HealthInsuranceRateDto) {
                                //update previous end month to 9999/12
                                data.startMonth = self.convertMonth(data.startMonth);
                                data.endMonth = self.minusOneMonth(self.startMonth());
                                aservice.updateHealthRate(data);
                            });
                        }
                        else {
                            //invalid date
                            alert("ER011");
                        }
                    }
                }
                //for pension
                else {

                    if (self.isRemove()) {
                        //remove current history  
                        aservice.removePensionRate(self.OfficeItemModel().childs[0].code);
                        //update previous history
                        aservice.getPensionItemDetail(self.OfficeItemModel().childs[1].code).done(function(data: PensionRateDto) {
                            //update previous end month to 9999/12
                            data.endMonth = "9999/12";
                            data.startMonth = self.convertMonth(data.startMonth);
                            aservice.updatePensionRate(data);
                        });
                    }
                    else {
                        //compare previous startMonth and startMonth -1
                        if (self.compareStringDate(self.previousStartMonth(), self.minusOneMonth(self.startMonth()))) {
                            //update current history
                            aservice.getPensionItemDetail(self.OfficeItemModel().childs[0].code).done(function(data: PensionRateDto) {
                                //
                                data.startMonth = self.startMonth();
                                data.endMonth = self.convertMonth(data.endMonth);
                                aservice.updatePensionRate(data);
                            });
                            //TODO update previous history
                            aservice.getPensionItemDetail(self.OfficeItemModel().childs[1].code).done(function(data: PensionRateDto) {
                                //update previous end month to 9999/12
                                data.startMonth = self.convertMonth(data.startMonth);
                                data.endMonth = self.minusOneMonth(self.startMonth());
                                aservice.updatePensionRate(data);
                            });
                        }
                        else {
                            //invalid date
                            alert("ER011");
                        }
                    }

                }
                //update
                self.OfficeItemModel().childs.forEach(function(item, index) {
                    //update history 
                    if (item.code == self.selectedHistoryCode()) {
                        //check start month < end month and previous startMonth < startMonth
                        if (self.compareStringDate(self.startMonth(), self.endMonth()) && self.compareStringDate(self.previousStartMonth(), self.minusOneMonth(self.startMonth()))) {
                            self.OfficeItemModel().childs[index].codeName = self.startMonth() + "~" + self.endMonth();
                            //update endMonth of previous history
                            self.OfficeItemModel().childs[index + 1].codeName = self.previousStartMonth() + "~" + self.minusOneMonth(self.startMonth());
                        }
                        else {
                            alert("ER011");
                        }
                    }
                });
                nts.uk.ui.windows.setShared("updateHistoryChildValue", self.OfficeItemModel(), true);
                nts.uk.ui.windows.close();
            }
            CloseModalSubWindow() {
                // Set child value
                nts.uk.ui.windows.setShared("updateHistoryChildValue", this.modalValue(), this.isTransistReturnData());
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