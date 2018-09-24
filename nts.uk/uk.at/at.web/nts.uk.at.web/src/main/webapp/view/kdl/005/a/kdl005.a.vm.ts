module nts.uk.at.view.kdl005.a {
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
            kdl005Data: KnockoutObservable<any>;
            employeeInfo: KnockoutObservable<string>;
            
            dataItems: KnockoutObservableArray<any>;
            
            value01: KnockoutObservable<string> = ko.observable("");
            value02: KnockoutObservable<string> = ko.observable("");
            hint02: KnockoutObservable<string> = ko.observable("");
            value03: KnockoutObservable<string> = ko.observable("");
            hint03: KnockoutObservable<string> = ko.observable("");
            value04: KnockoutObservable<string> = ko.observable("");
            hint04: KnockoutObservable<string> = ko.observable("");
            
            constructor() {
                var self = this;
                
                self.kdl005Data = nts.uk.ui.windows.getShared("KDL005_DATA");

                self.employeeInfo = ko.observable("");
                
                self.dataItems = ko.observableArray([]);
                
                this.legendOptions = {
                    items: [
                        { labelText: nts.uk.resource.getText("KDL005_20") }
                    ]
                };
                
                service.getEmployeeList(self.kdl005Data).done(function(data: any) {
                    if(data.employeeBasicInfo.length > 1) {
                        self.selectedCode.subscribe(function(value) {
                            let itemData = _.find(data.employeeBasicInfo, ['employeeCode', value]);
                            self.employeeInfo(nts.uk.resource.getText("KDL009_25", [value, itemData.businessName]));
                            
                            service.getDetailsConfirm(itemData.employeeId, self.kdl005Data.baseDate).done(function(data) {
                                self.bindTimeData(data);
                                self.bindSummaryData(data);
                            }).fail(function(res) {
                                  
                            });
                        });  
                            
                        self.baseDate = ko.observable(new Date());
                        self.selectedCode(data.employeeBasicInfo[0].employeeCode);
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
                        self.employeeList = ko.observableArray<UnitModel>(_.map(data.employeeBasicInfo,x=>{return {code:x.employeeCode ,name:x.businessName};}));
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
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 340 });
                    } else if(data.employeeBasicInfo.length == 1) {
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", [data.employeeBasicInfo[0].employeeCode, data.employeeBasicInfo[0].businessName]));
                        
                        service.getDetailsConfirm(data.employeeBasicInfo[0].employeeId, self.kdl005Data.baseDate).done(function(data) {
                            self.bindTimeData(data);
                            self.bindSummaryData(data);
                        }).fail(function(res) {
                              
                        });
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 340 });
                    } else {
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", ["", ""]));
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 340 });
                    }
                });
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                dfd.resolve();
    
                return dfd.promise();
            }
            
            bindTimeData(data: any) {
                let self = this;
               
                
                self.dataItems.removeAll();
                
                if(data.lstHistory != null && data.lstHistory.length >= 1) {
                    _.each(data.lstHistory, function (item) {
                        
                        let leaveDate = "";
                        let dayOffDateTop = "";
                        let dayOffDateBot = "";
                        let duedateHoliday = "";
                        let occurrenceDays1 = "";
                        let occurrenceDays2Top = "";
                        let occurrenceDays2Bot = "";
                        let isHalfDay = false;
                        
                        if(item.breakHis != null) {
                            if(!item.breakHis.chkDisappeared) {
                                let dayoffDateStr = item.breakHis.breakDate.dayoffDate != null ? item.breakHis.breakDate.dayoffDate : "";
                                
                                if(dayoffDateStr !== "") {
                                    if(item.breakHis.mngAtr == 2 || item.breakHis.mngAtr == 3) {
                                        leaveDate = nts.uk.resource.getText("KDL005_19", [nts.uk.time.applyFormat("Short_YMDW", [dayoffDateStr])]);
                                    } else {
                                        leaveDate = nts.uk.time.applyFormat("Short_YMDW", [dayoffDateStr]);
                                    }
                                } else {
                                    leaveDate = "";
                                }                                
                            } else {
                                leaveDate = "";
                            }
                            
                            if(Number(item.breakHis.occurrenceDays) == 0 || Number(item.breakHis.occurrenceDays) == 1) {
                                occurrenceDays1 = "";
                            } else {
                                occurrenceDays1 = item.breakHis.occurrenceDays + nts.uk.resource.getText("KDL005_27");
                            }
                            
                            if(item.breakHis.expirationDate != null && item.isManaged == 1) {
                                duedateHoliday = nts.uk.time.applyFormat("Short_YMDW", [item.breakHis.expirationDate]);
                            } else {
                                duedateHoliday = "";
                            }
                        }
                        
                        if(item.dayOffHis != null) {
                            if(item.dayOffHis.createAtr == 2 || item.dayOffHis.createAtr == 3) {
                                if(item.dayOffHis.dayOffDate.dayoffDate != null) {
                                    dayOffDateTop = nts.uk.resource.getText("KDL005_19", [item.dayOffHis.dayOffDate.dayoffDate]);
                                } else {
                                    dayOffDateTop = "";
                                }
                            } else {
                                if(item.dayOffHis.dayOffDate.dayoffDate != null) {
                                    dayOffDateTop = nts.uk.time.applyFormat("Short_YMDW", [item.dayOffHis.dayOffDate.dayoffDate]);
                                } else {
                                    dayOffDateTop = "";
                                }
                            }
                            
                            if(Number(item.dayOffHis.requeiredDays) == 0 || Number(item.dayOffHis.requeiredDays) == 1) {
                                occurrenceDays2Top = "";
                            } else {
                                occurrenceDays2Top = item.dayOffHis.requeiredDays + nts.uk.resource.getText("KDL005_27");
                            }
                        }
                        
                        if(item.breakHis.mngAtr == 0 && Number(item.dayOffHis.requeiredDays) % 1 !== 0) {
                            isHalfDay = true;
                        }
                        
                        let temp = new DataItems(leaveDate, dayOffDateTop, dayOffDateBot, duedateHoliday, occurrenceDays1, occurrenceDays2Top, occurrenceDays2Bot, isHalfDay);
                            
                        if(temp.leaveDate !== "" || temp.dayOffDateTop !== "" || temp.dayOffDateBot !== "" || temp.duedateHoliday !== "" 
                                || temp.occurrenceDays1 !== "" || temp.occurrenceDays2Top !== "" || temp.occurrenceDays2Bot !== "") {
                            self.dataItems.push(temp);
                        }
                    });                    
                }
            }
            
            bindSummaryData(data: any) {
                var self = this;
                
                if(data.totalInfor != null) {
                    self.value01(data.totalInfor.carryForwardDays + nts.uk.resource.getText("KDL005_27"));
                    self.value02(data.totalInfor.recordOccurrenceDays + nts.uk.resource.getText("KDL005_27"));
                    self.hint02(data.totalInfor.scheOccurrenceDays + nts.uk.resource.getText("KDL005_27"));
                    self.value03(data.totalInfor.recordUseDays + nts.uk.resource.getText("KDL005_27"));
                    self.hint03(data.totalInfor.scheUseDays + nts.uk.resource.getText("KDL005_27"));
                    var ramaining01 = data.totalInfor.recordOccurrenceDays - data.totalInfor.recordUseDays;
                    var ramaining02 = data.totalInfor.scheOccurrenceDays - data.totalInfor.scheUseDays;
                    self.value04(ramaining01 + nts.uk.resource.getText("KDL005_27"));
                    self.hint04(ramaining02 + nts.uk.resource.getText("KDL005_27"));
                } else {
                    self.value01(nts.uk.resource.getText("KDL005_27", ["0"]));
                    self.value02(nts.uk.resource.getText("KDL005_27", ["0"]));
                    self.hint02(nts.uk.resource.getText("KDL005_27", ["0"]));
                    self.value03(nts.uk.resource.getText("KDL005_27", ["0"]));
                    self.hint03(nts.uk.resource.getText("KDL005_27", ["0"]));
                    self.value04(nts.uk.resource.getText("KDL005_27", ["0"]));
                    self.hint04(nts.uk.resource.getText("KDL005_27", ["0"]));
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
            leaveDate: string;
            dayOffDateTop: string;
            dayOffDateBot: string;
            duedateHoliday: string;
            occurrenceDays1: string;
            occurrenceDays2Top: string;
            occurrenceDays2Bot: string;
            isHalfDay: boolean;
    
            constructor(leaveDate: string, dayOffDateTop: string, dayOffDateBot: string, duedateHoliday: string, occurrenceDays1: string, 
                    occurrenceDays2Top: string, occurrenceDays2Bot: string, isHalfDay: boolean) {
                this.leaveDate = leaveDate;
                this.dayOffDateTop = dayOffDateTop;
                this.dayOffDateBot = dayOffDateBot;
                this.duedateHoliday = duedateHoliday;
                this.occurrenceDays1 = occurrenceDays1;
                this.occurrenceDays2Top = occurrenceDays2Top;
                this.occurrenceDays2Bot = occurrenceDays2Bot;
                this.isHalfDay = isHalfDay;
            }
        }
    }
}