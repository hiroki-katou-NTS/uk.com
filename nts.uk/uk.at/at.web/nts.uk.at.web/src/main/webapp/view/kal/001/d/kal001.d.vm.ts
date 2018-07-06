module nts.uk.com.view.kal001.d.viewmodel {

    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import service = nts.uk.at.view.kal001.d.service;
    import block = nts.uk.ui.block;
    
    export class ScreenModel {
        // dialog mode
        dialogMode: KnockoutObservable<string>;
        isInterrupt: KnockoutObservable<boolean>;


        // time when start process
        timeStart: any;
        timeEnd: any;
        timeNow: any;
        timeProcess: KnockoutObservable<string>;
        // D1_12
        timeStartStr : KnockoutObservable<string>;
        
        empProcessCount: number;
        empProcessStr: KnockoutObservable<string>;
        empProcessTotalCount: KnockoutObservable<number>;
        employeeErrors: KnockoutObservableArray<String>;
        strPersion: KnockoutObservable<String> = ko.observable("人");
        strSlash: KnockoutObservable<String> = ko.observable("/");
        strFinish: KnockoutObservable<String> = ko.observable("完了");
        displayStrFinish: KnockoutObservable<boolean> = ko.observable(false);
        
        currentAlarmCode : KnockoutObservable<string>;
        listSelectedEmpployee : Array<UnitModel>;
        listPeriodByCategory : KnockoutObservableArray<PeriodByCategory>;
        selectedEmpployee : Array<UnitModel> ;
        listAlarmExtraValueWkReDto : KnockoutObservableArray<AlarmExtraValueWkReDto> ;
       
        constructor() {
            var self = this;
            var params = nts.uk.ui.windows.getShared("KAL001_A_PARAMS");          
            self.timeStart = new Date();
            self.timeEnd = ko.observable("");
            self.timeProcess = ko.observable("");
            self.timeStartStr = ko.observable("");
            self.empProcessStr = ko.observable("");
            self.empProcessTotalCount = params.totalEmpProcess;
            self.empProcessCount = 0;
            self.dialogMode = ko.observable("processing");
            self.timeStartStr = moment.utc().format('YYYY/MM/DD H:mm');
            
            self.currentAlarmCode = params.currentAlarmCode;
            self.listSelectedEmpployee = params.listSelectedEmpployee;
            self.listPeriodByCategory = params.listPeriodByCategory;
            self.listAlarmExtraValueWkReDto = ko.observableArray([]);
            self.employeeErrors = ko.observableArray([]);
            self.isInterrupt = ko.observable(false);
            
            //process alam list 
          
            //code process screen A
            block.invisible();
            service.isExtracting().done((isExtracting: boolean) => {
                if (isExtracting) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_993" });
                    block.clear();
                    return;
                }
                service.extractStarting().done((statusId: string) => {
                   let selectedEmpployees = self.listSelectedEmpployee; 
                   let unitModelLast = selectedEmpployees[selectedEmpployees.length-1];
                    _.forEach(selectedEmpployees, function(item: UnitModel) {
                        self.selectedEmpployee = ko.observableArray<UnitModel>([]);
                        self.selectedEmpployee.push(item);
                        if(!self.isInterrupt()){
                            self.getInformationExtracyAlarm(self.selectedEmpployee(),statusId,unitModelLast);
                        }
                    });

                }).fail((errorExtractAlarm) => {
                    block.clear();
                    alertError(errorExtractAlarm);
                })
            }).fail((errorExtractAlarm) => {
                block.clear();
                alertError(errorExtractAlarm);
            });
        }
        public getInformationExtracyAlarm(selectedEmpployeeList : Array<UnitModel>,statusId : string,unitModelLast : UnitModel ): void {
            let self = this;
            service.extractAlarm(selectedEmpployeeList, self.currentAlarmCode, self.listPeriodByCategory).done((dataExtractAlarm: service.ExtractedAlarmDto) => {
                service.extractFinished(statusId);
                if (dataExtractAlarm.extracting) {
                    //                                nts.uk.ui.dialog.info({ messageId: "Msg_993" });
                    //                                return;
                    self.employeeErrors.push(dataExtractAlarm.extracting);
                }
                if (dataExtractAlarm.nullData) {
                    self.employeeErrors.push(dataExtractAlarm.nullData);
                }
                let employeeId = "";
                _.forEach(dataExtractAlarm.extractedAlarmData, function(item: AlarmExtraValueWkReDto) {
                    self.listAlarmExtraValueWkReDto.push(item);
                    employeeId = item.employeeID;
                });
                self.empProcessCount = self.empProcessCount + 1;
                self.empProcessStr(self.empProcessCount);
                if (unitModelLast.id == employeeId) {
                   self.setFinished();
                }
            }).fail((errorExtractAlarm) => {
                self.employeeErrors.push(errorExtractAlarm);
            }).always(() => {

            });
        }
          // process when click button interrupt
          public interrupt(): void {
              let self = this;
              nts.uk.ui.dialog.confirm({ messageId: "Msg_835" })
                  .ifYes(() => {
                      self.isInterrupt(true);
                      self.setFinished();
                      nts.uk.ui.windows.setShared("extractedAlarmData", self.listAlarmExtraValueWkReDto());
                  })
                  .ifNo(() => {
                      return;
                  });
          }
        
        // set time now
         public setFinished(): void {
             let self = this;
             self.displayStrFinish(true);
             // count time
             self.timeNow = new Date();
             let over = (self.timeNow.getSeconds() + self.timeNow.getMinutes() * 60 + self.timeNow.getHours() * 60) - (self.timeStart.getSeconds() + self.timeStart.getMinutes() * 60 + self.timeStart.getHours() * 60);
             let time = new Date(null);
             time.setSeconds(over);
             let result = time.toISOString().substr(11, 8);
             self.timeProcess(result);
             self.timeEnd(moment.utc().format('YYYY/MM/DD H:mm'));
             self.dialogMode("done");
         }

          //  process when click button closePopup 
          closePopup() {
              close();
          }
    }
 
    export class AlarmExtraValueWkReDto {
        guid: string;
        workplaceID: string;
        hierarchyCd: string;
        workplaceName: string;
        employeeID: string;
        employeeCode: string;
        employeeName: string;
        alarmValueDate: string;
        category: number;
        categoryName: string;
        alarmItem: string;
        alarmValueMessage: string;
        comment: string;
    }

 export class DateValue{
        startDate : string;
        endDate: string;
        constructor(startDate: string, endDate: string){
            this.startDate = (startDate);
            this.endDate = (endDate);
        }
    }
    
    export class PeriodByCategory{
        ategoryName: string;
        startDate : string;
        endDate : string;
        checkBox: boolean;
        typeInput :  string;
        required: boolean;
        year:  number;
        visible: boolean;
        id : number;
    }
    
    
    
    // employee list component
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            id: string;
            code: string;
            name?: string;
            workplaceCode?: string;
            workplaceId?: string;
            workplaceName?: string;            
            isAlreadySetting?: boolean;
        }
        export class UnitModelDto implements UnitModel{
            id: string;
            code: string;
            name: string;
            workplaceCode: string;
            workplaceId: string;
            workplaceName: string;
            isAlreadySetting: boolean;
            
            constructor(employee : EmployeeSearchDto){
                this.id = employee.employeeId;
                this.code = employee.employeeCode;
                this.name = employee.employeeName;
                this.workplaceId = employee.workplaceId;
                this.workplaceCode = employee.workplaceCode;
                this.workplaceName = employee.workplaceName;    
            }            
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
    
    
    // search component
    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceCode: string;
        workplaceId: string;
        workplaceName: string;
    }
    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab: boolean; // クイック検索
        showAdvancedSearchTab: boolean; // 詳細検索
        showBaseDate: boolean; // 基準日利用
        showClosure: boolean; // 就業締め日利用
        showAllClosure: boolean; // 全締め表示
        showPeriod: boolean; // 対象期間利用
        periodFormatYM: boolean; // 対象期間精度
    
        /** Required parameter */
        baseDate?: string; // 基準日
        periodStartDate?: string; // 対象期間開始日
        periodEndDate?: string; // 対象期間終了日
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分
    
        /** Quick search tab options */
        showAllReferableEmployee: boolean; // 参照可能な社員すべて
        showOnlyMe: boolean; // 自分だけ
        showSameWorkplace: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild: boolean; // 同じ職場とその配下の社員
    
        /** Advanced search properties */
        showEmployment: boolean; // 雇用条件
        showWorkplace: boolean; // 職場条件
        showClassification: boolean; // 分類条件
        showJobTitle: boolean; // 職位条件
        showWorktype: boolean; // 勤種条件
        isMutipleCheck: boolean; // 選択モード
        // showDepartment: boolean; // 部門条件 not covered
        // showDelivery: boolean; not covered
    
        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }
    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    } 

}






