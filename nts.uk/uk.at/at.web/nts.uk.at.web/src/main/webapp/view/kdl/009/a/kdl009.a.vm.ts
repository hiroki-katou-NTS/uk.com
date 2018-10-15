module nts.uk.at.view.kdl009.a {
    export module viewmodel {
        export class ScreenModel {
            //Grid data
            listComponentOption: any;
            selectedCode: KnockoutObservable<string> = ko.observable('');
            multiSelectedCode: KnockoutObservableArray<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            isShowSelectAllButton: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;
            
            legendOptions: any;
            kdl009Data: KnockoutObservable<any>;
            employeeInfo: KnockoutObservable<string>;
            
            dataItems: KnockoutObservableArray<any>;
            
            value01: KnockoutObservable<string> = ko.observable("");
            value02: KnockoutObservable<string> = ko.observable("");
            hint02: KnockoutObservable<string> = ko.observable("");
            value03: KnockoutObservable<string> = ko.observable("");
            hint03: KnockoutObservable<string> = ko.observable("");
            value04: KnockoutObservable<string> = ko.observable("");
            hint04: KnockoutObservable<string> = ko.observable("");
            expirationDateText: KnockoutObservable<string> = ko.observable("");
            
            constructor() {
                var self = this;
                
                self.kdl009Data = nts.uk.ui.windows.getShared("KDL009_DATA");

                self.employeeInfo = ko.observable("");
                
                self.dataItems = ko.observableArray([]);
                
                this.legendOptions = {
                    items: [
                        { labelText: nts.uk.resource.getText("KDL009_18") + " : " + nts.uk.resource.getText("KDL009_19") }
                    ]
                };
                
                service.getEmployee(self.kdl009Data).done(function(data: any) {
                    if(data.employeeBasicInfo.length > 1) {
                        self.selectedCode.subscribe(function(value) {
                            let itemSelected = _.find(data.employeeBasicInfo, ['employeeCode', value]);
                            self.employeeInfo(nts.uk.resource.getText("KDL009_25", [value, itemSelected.businessName]));
                            
                            service.getAcquisitionNumberRestDays(itemSelected.employeeId, data.baseDate).done(function(data) {
                                self.expirationDateText(ExpirationDate[data.setting.expirationDate]);
                                self.bindTimeData(data);
                                self.bindSummaryData(data);
                            }).fail(function(res) {
                                nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                            });
                        });  
                            
                        self.bindEmpList(data.employeeBasicInfo);
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 341 });
                    } else if(data.employeeBasicInfo.length == 1) {
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", [data.employeeBasicInfo[0].employeeCode, data.employeeBasicInfo[0].businessName]));
                        
                        service.getAcquisitionNumberRestDays(data.employeeBasicInfo[0].employeeId, data.baseDate).done(function(data) {
                            self.expirationDateText(ExpirationDate[data.setting.expirationDate]);
                            self.bindTimeData(data);
                            self.bindSummaryData(data);
                        }).fail(function(res) {
                            nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                        });
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 341 });
                    } else {
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", ["", ""]));
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 341 });
                        
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_918" });
                    }
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                });

            }
           
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                dfd.resolve();
    
                return dfd.promise();
            }
            
            bindEmpList(data: any) {
                var self = this;
                
                self.baseDate = ko.observable(new Date());
                self.selectedCode(data[0].employeeCode);
                self.multiSelectedCode = ko.observableArray([]);
                self.isShowAlreadySet = ko.observable(false);
                self.alreadySettingList = ko.observableArray([
                    {code: '1', isAlreadySetting: true},
                    {code: '2', isAlreadySetting: true}
                ]);
                self.isDialog = ko.observable(false);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(false);
                self.isShowWorkPlaceName = ko.observable(false);
                self.isShowSelectAllButton = ko.observable(false);
                this.employeeList = ko.observableArray<UnitModel>(_.map(data,x=>{return {code:x.employeeCode ,name:x.businessName};}));
                self.listComponentOption = {
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: self.isMultiSelect(),
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList,
                    isShowWorkPlaceName: self.isShowWorkPlaceName(),
                    isShowSelectAllButton: self.isShowSelectAllButton(),
                    maxRows: 12
                };
                
                $('#component-items-list').ntsListComponent(self.listComponentOption);
            }
            
            bindTimeData(data: any) {
                let self = this;
                
                self.dataItems.removeAll();
                                
                if(data.recAbsHistoryOutput.length > 0) {
                    _.each(data.recAbsHistoryOutput, function (item) {
                        let isHalfDay = false;
                        let issueDate = "";
                        let holidayDateTop = "";
                        let holidayDateBot = "";
                        let expirationDate = "";
                        let occurrenceDays1 = "";
                        let occurrenceDays2Top = "";
                        let occurrenceDays2Bot = "";
                        if(item.recHisData != null) {
                            if(!item.recHisData.recDate.unknownDate) {
                                if(item.recHisData.dataAtr == 3) {
                                    issueDate = nts.uk.resource.getText("KDL009_13", [nts.uk.time.applyFormat("Short_YMDW", [item.recHisData.recDate.dayoffDate])]);
                                } else {
                                    issueDate = nts.uk.time.applyFormat("Short_YMDW", [item.recHisData.recDate.dayoffDate]);
                                }
                            } else {
                                issueDate = nts.uk.resource.getText("KDL009_11");
                            }
                            
                            if(item.recHisData.occurrenceDays == 0.5) {
                                occurrenceDays1 = nts.uk.resource.getText("KDL009_14", [item.recHisData.occurrenceDays]);
                            }
                            
                            if(item.recHisData.expirationDate != null) {
                                expirationDate = nts.uk.time.applyFormat("Short_YMDW", [item.recHisData.expirationDate]);
                            } else {
                                expirationDate = "";
                            }
                        }
                        
                        if(item.absHisData != null) {
                            if(item.absHisData.absDate.unknownDate) {
                                holidayDateTop = nts.uk.resource.getText("KDL009_11");
                            } else {
                                if(item.absHisData.createAtr == 3) {
                                    holidayDateTop = nts.uk.resource.getText("KDL009_13", [nts.uk.time.applyFormat("Short_YMDW", [item.absHisData.absDate.dayoffDate])]);
                                } else {
                                    holidayDateTop = nts.uk.time.applyFormat("Short_YMDW", [item.absHisData.absDate.dayoffDate]);
                                }
                            }
                            
                            if(item.absHisData.requeiredDays == 0.5) {
                                occurrenceDays2Top = nts.uk.resource.getText("KDL009_14", [item.absHisData.requeiredDays]);
                            }
                            
                            if(holidayDateTop === nts.uk.resource.getText("KDL009_11")) {
                                holidayDateTop = "";
                                occurrenceDays2Top = "";
                            }
                        }
                        
                        if(item.recHisData.dataAtr == 1) {
                            if(holidayDateTop === "" && occurrenceDays2Top === "") {
                                isHalfDay = false;
                            } else {
                                isHalfDay = true;
                            }                            
                        }
                        
                        let temp = new DataItems(issueDate, holidayDateTop, holidayDateBot, expirationDate, occurrenceDays1, occurrenceDays2Top, occurrenceDays2Bot, isHalfDay);
                            
                        self.dataItems.push(temp);
                    });                    
                }
            }
            
            bindSummaryData(data: any) {
                var self = this;
                
                if (data.absRecMng != null) {
                    self.value01(nts.uk.resource.getText("KDL009_14", [data.absRecMng.carryForwardDays]));
                    self.value02(nts.uk.resource.getText("KDL009_14", [data.absRecMng.occurrenceDays]));
                    self.value03(nts.uk.resource.getText("KDL009_14", [data.absRecMng.useDays]));
                    self.value04(nts.uk.resource.getText("KDL009_14", [data.absRecMng.remainDays]));
                } else {
                    self.value01(nts.uk.resource.getText("KDL009_14", ["0"]));
                    self.value02(nts.uk.resource.getText("KDL009_14", ["0"]));
                    self.value03(nts.uk.resource.getText("KDL009_14", ["0"]));
                    self.value04(nts.uk.resource.getText("KDL009_14", ["0"]));
                }
            }
            
            cancel() {
                nts.uk.ui.windows.close();
            }
        }
        
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }
        
        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
        
        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }
        
        class DataItems {
            issueDate: string;
            holidayDateTop: string;
            holidayDateBot: string;
            expirationDate: string;
            occurrenceDays1: string;
            occurrenceDays2Top: string;
            occurrenceDays2Bot: string;
            isHalfDay: boolean;
    
            constructor(issueDate: string, holidayDateTop: string, holidayDateBot: string, expirationDate: string, occurrenceDays1: string, 
                    occurrenceDays2Top: string, occurrenceDays2Bot: string, isHalfDay: boolean) {
                this.issueDate = issueDate;
                this.holidayDateTop = holidayDateTop;
                this.holidayDateBot = holidayDateBot;
                this.expirationDate = expirationDate;
                this.occurrenceDays1 = occurrenceDays1;
                this.occurrenceDays2Top = occurrenceDays2Top;
                this.occurrenceDays2Bot = occurrenceDays2Bot;
                this.isHalfDay = isHalfDay;
            }
        }
        export enum ExpirationDate {
            "当月",//0
            "常に繰越",//1
            "年度末クリア",//2
            "1ヶ月",//3
            "2ヶ月",//4
            "3ヶ月",//5
            "4ヶ月",//6
            "5ヶ月",//7
            "6ヶ月",//8
            "7ヶ月",//9
            "8ヶ月",//10
            "9ヶ月",//11
            "10ヶ月",//12
            "11ヶ月",//13
            "1年",//14
        }
    }
}