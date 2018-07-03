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
                
                if(self.kdl005Data.employeeBasicInfo.length > 1) {
                    self.selectedCode.subscribe(function(value) {
                        let itemData = _.find(self.kdl005Data.employeeBasicInfo, ['employeeCode', value]);
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", [value, itemData.businessName]));
                        
                        service.getDetailsConfirm(itemData.employeeId, self.kdl005Data.baseDate).done(function(data) {
                            self.bindTimeData(data);
                            self.bindSummaryData(data);
                        }).fail(function(res) {
                              
                        });
                    });  
                        
                    self.baseDate = ko.observable(new Date());
                    self.selectedCode(self.kdl005Data.employeeBasicInfo[0].employeeCode);
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
                    this.employeeList = ko.observableArray<UnitModel>(_.map(self.kdl005Data.employeeBasicInfo,x=>{return {code:x.employeeCode ,name:x.businessName};}));
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
                        isShowSelectAllButton: self.isShowSelectAllButton()
                    };
                    
                    $('#component-items-list').ntsListComponent(self.listComponentOption);
                    
                    $("#date-fixed-table").ntsFixedTable({ height: 320 });
                } else if(self.kdl005Data.employeeBasicInfo.length == 1) {
                    self.employeeInfo(nts.uk.resource.getText("KDL009_25", [self.kdl005Data.employeeBasicInfo[0].employeeCode, self.kdl005Data.employeeBasicInfo[0].businessName]));
                    
                    service.getDetailsConfirm(self.kdl005Data.employeeBasicInfo[0].employeeId, self.kdl005Data.baseDate).done(function(data) {
                        self.bindTimeData(data);
                        self.bindSummaryData(data);
                    }).fail(function(res) {
                          
                    });
                    
                    $("#date-fixed-table").ntsFixedTable({ height: 320 });
                } else {
                    self.employeeInfo(nts.uk.resource.getText("KDL009_25", ["", ""]));
                    
                    $("#date-fixed-table").ntsFixedTable({ height: 320 });
                }
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                dfd.resolve();
    
                return dfd.promise();
            }
            
            bindTimeData(data: any) {
                var self = this;
                var leaveDate = "";
                var dayOffDate = "";
                var duedateHoliday = "";
                var occurrenceDays1 = "";
                var occurrenceDays2 = "";
                var isHalfDay = false;
                self.dataItems.removeAll();
                
                if(data.lstHistory != null && data.lstHistory.length >= 1) {
                    _.each(data.lstHistory, function (item) {
                        if(item.breakHis != null) {
                            if(!item.breakHis.chkDisappeared) {
                                if(item.breakHis.mngAtr == 0 || item.breakHis.mngAtr == 4) {
                                    leaveDate = nts.uk.resource.getText("KDL005_19", [nts.uk.time.applyFormat("Short_YMDW", [item.breakHis.breakDate.dayoffDate])]);
                                } else {
                                    leaveDate = nts.uk.time.applyFormat("Short_YMDW", [item.breakHis.breakDate.dayoffDate]);
                                }
                            } else {
                                leaveDate = "";
                            }
                            
                            if(Number(item.breakHis.occurrenceDays) == 0 || Number(item.breakHis.occurrenceDays) == 1) {
                                occurrenceDays1 = "";
                            } else {
                                isHalfDay = true;
                                occurrenceDays1 = item.breakHis.occurrenceDays + nts.uk.resource.getText("KDL005_27");
                            }
                            
                            if(item.breakHis.expirationDate != null && item.isManaged == 1) {
                                duedateHoliday = nts.uk.resource.getText("KDL005_19", [nts.uk.time.applyFormat("Short_YMDW", [item.breakHis.expirationDate])]);
                            } else {
                                duedateHoliday = "";
                            }
                        }
                        
                        if(item.dayOffHis != null) {
                            if(item.dayOffHis.createAtr == 0 || item.dayOffHis.createAtr == 4) {
                                if(item.dayOffHis.dayOffDate.dayoffDate != null) {
                                    dayOffDate = nts.uk.resource.getText("KDL005_19", [item.dayOffHis.dayOffDate.dayoffDate]);
                                } else {
                                    dayOffDate = "";
                                }
                            } else {
                                if(item.dayOffHis.dayOffDate.dayoffDate != null) {
                                    dayOffDate = nts.uk.time.applyFormat("Short_YMDW", [item.dayOffHis.dayOffDate.dayoffDate]);
                                } else {
                                    dayOffDate = "";
                                }
                            }
                            
                            if(Number(item.dayOffHis.requeiredDays) == 0 || Number(item.dayOffHis.requeiredDays) == 1) {
                                occurrenceDays2 = "";
                            } else {
                                isHalfDay = true;
                                occurrenceDays2 = item.dayOffHis.requeiredDays + nts.uk.resource.getText("KDL005_27");
                            }
                        }
                        
                        var temp = new DataItems(leaveDate, dayOffDate, duedateHoliday, occurrenceDays1, occurrenceDays2, isHalfDay);
                            
                        self.dataItems.push(temp);
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
            dayOffDate: string;
            duedateHoliday: string;
            occurrenceDays1: string;
            occurrenceDays2: string;
            isHalfDay: boolean;
    
            constructor(leaveDate: string, dayOffDate: string, duedateHoliday: string, occurrenceDays1: string, occurrenceDays2: string, isHalfDay: boolean) {
                this.leaveDate = leaveDate;
                this.dayOffDate = dayOffDate;
                this.duedateHoliday = duedateHoliday;
                this.occurrenceDays1 = occurrenceDays1;
                this.occurrenceDays2 = occurrenceDays2;
                this.isHalfDay = isHalfDay;
            }
        }
    }
}