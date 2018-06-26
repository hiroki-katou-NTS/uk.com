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
                            let itemName = _.find(data.employeeBasicInfo, ['employeeCode', value]);
                            self.employeeInfo(nts.uk.resource.getText("KDL009_25", [value, itemName.businessName]));
                            
                            service.getAcquisitionNumberRestDays(value, data.baseDate).done(function(data) {
                                self.bindTimeData(data);
                                self.bindSummaryData(data);
                            }).fail(function(res) {
                                nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                            });
                        });  
                            
                        self.bindEmpList(data.employeeBasicInfo);
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 320, width: 650 });
                    } else if(data.employeeBasicInfo.length == 1) {
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", [data.employeeBasicInfo[0].employeeCode, data.employeeBasicInfo[0].businessName]));
                        
                        service.getAcquisitionNumberRestDays(data.employeeBasicInfo[0].employeeId, data.baseDate).done(function(data) {
                            self.bindTimeData(data);
                            self.bindSummaryData(data);
                        }).fail(function(res) {
                            nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                        });
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 320, width: 700 });
                    } else {
                        self.employeeInfo(nts.uk.resource.getText("KDL009_25", ["", ""]));
                        
                        $("#date-fixed-table").ntsFixedTable({ height: 320, width: 700 });
                        
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
                    isShowSelectAllButton: self.isShowSelectAllButton()
                };
                
                $('#component-items-list').ntsListComponent(self.listComponentOption);
            }
            
            bindTimeData(data: any) {
                var self = this;
                var greneraGigesHisData = data.absRecGenerationDigestionHis.greneraGigesHis;
                var issueDate = "";
                var holidayDate = "";
                var expirationDate = "";
                var occurrenceDays = "";
                var isHalfDay = false;
                
                if(greneraGigesHisData != null && greneraGigesHisData.length >= 1) {
                    _.each(greneraGigesHisData, function (item) {
                        if(greneraGigesHisData.recHisData != null) {
                            if(greneraGigesHisData.recHisData.recDate.unknownDate) {
                                issueDate = nts.uk.resource.getText("KDL009_11");
                            } else if(greneraGigesHisData.recHisData.dataAtr == 3) {
                                var time = nts.uk.resource.getText("KDL009_13", [greneraGigesHisData.ymdData.dayoffDate]);
                                issueDate = nts.uk.time.applyFormat("Short_YMDW", time);
                            } else {
                                issueDate = "";
                            }
                            
                            if(Number(greneraGigesHisData.recHisData.occurrenceDays) == 0.5) {
                                occurrenceDays = nts.uk.resource.getText("KDL009_14", [greneraGigesHisData.recHisData.occurrenceDays]);
                                isHalfDay = true;
                            } else {
                                occurrenceDays = "";
                            }
                            
                            expirationDate = nts.uk.time.applyFormat("Short_YMDW", [greneraGigesHisData.recHisData.expirationDate]);
                            
                            var temp = new DataItems(issueDate, issueDate, expirationDate, occurrenceDays, isHalfDay);
                            
                            self.dataItems.push(temp);
                        }
                    });                    
                }
            }
            
            bindSummaryData(data: any) {
                var self = this;
                
                if(data.absRecGenerationDigestionHis != null) {
                    self.value01(nts.uk.resource.getText("KDL009_14", [data.absRecGenerationDigestionHis.absRemainInfor.carryForwardDays]));
                    self.value02(nts.uk.resource.getText("KDL009_14", [data.absRecGenerationDigestionHis.absRemainInfor.recordOccurrenceDays]));
                    self.hint02(nts.uk.resource.getText("KDL009_15", [data.absRecGenerationDigestionHis.absRemainInfor.scheOccurrenceDays]));
                    self.value03(nts.uk.resource.getText("KDL009_14", [data.absRecGenerationDigestionHis.absRemainInfor.recordUseDays]));
                    self.hint03(nts.uk.resource.getText("KDL009_15", [data.absRecGenerationDigestionHis.absRemainInfor.scheUseDays]));
                    self.value04(nts.uk.resource.getText("KDL009_14", [data.absRecGenerationDigestionHis.absRemainInfor.recordOccurrenceDays - data.absRecGenerationDigestionHis.absRemainInfor.recordUseDays]));
                    self.hint04(nts.uk.resource.getText("KDL009_15", [data.absRecGenerationDigestionHis.absRemainInfor.scheOccurrenceDays - data.absRecGenerationDigestionHis.absRemainInfor.scheUseDays]));
                } else {
                    self.value01(nts.uk.resource.getText("KDL009_14", ["0"]));
                    self.value02(nts.uk.resource.getText("KDL009_14", ["0"]));
                    self.hint02(nts.uk.resource.getText("KDL009_15", ["0"]));
                    self.value03(nts.uk.resource.getText("KDL009_14", ["0"]));
                    self.hint03(nts.uk.resource.getText("KDL009_15", ["0"]));
                    self.value04(nts.uk.resource.getText("KDL009_14", ["0"]));
                    self.hint04(nts.uk.resource.getText("KDL009_15", ["0"]));
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
            holidayDate: string;
            expirationDate: string;
            occurrenceDays: string;
            isHalfDay: boolean;
    
            constructor(issueDate: string, holidayDate: string, expirationDate: string, occurrenceDays: string, isHalfDay: boolean) {
                this.issueDate = issueDate;
                this.holidayDate = holidayDate;
                this.expirationDate = expirationDate;
                this.occurrenceDays = occurrenceDays;
                this.isHalfDay = isHalfDay;
            }
        }
    }
}