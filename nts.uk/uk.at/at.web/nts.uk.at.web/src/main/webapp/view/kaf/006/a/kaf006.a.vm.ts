module nts.uk.at.view.kaf006.a.viewmodel {
    import common = nts.uk.at.view.kaf006.share.common;
    import service = nts.uk.at.view.kaf006.shr.service;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        DATE_FORMAT: string = "YYYY/MM/DD";
        //kaf000
        kaf000_a: kaf000.a.viewmodel.ScreenModel;
        manualSendMailAtr: KnockoutObservable<boolean> = ko.observable(true);
        screenModeNew: KnockoutObservable<boolean> = ko.observable(true);
        valueChecked : KnockoutObservable<boolean> = ko.observable(false);
        //current Data
//        curentGoBackDirect: KnockoutObservable<common.GoBackDirectData>;
        //申請者
        employeeName: KnockoutObservable<string> = ko.observable("");
        //Pre-POST
        prePostSelected: KnockoutObservable<number> = ko.observable(3);
        workState: KnockoutObservable<boolean> = ko.observable(true);
        typeSiftVisible: KnockoutObservable<boolean> = ko.observable(true);
        // 申請日付
        startAppDate: KnockoutObservable<string> = ko.observable('');
         // 申請日付
        endAppDate: KnockoutObservable<string> = ko.observable('');
        selectedValue: KnockoutObservable<number> = ko.observable(0);
        holidayTypes: KnockoutObservableArray<common.HolidayType> = ko.observableArray([]);
        holidayTypeCode: KnockoutObservable<number> = ko.observable(0);
        typeOfDutys: KnockoutObservableArray<common.TypeOfDuty> = ko.observableArray([]);
        selectedTypeOfDuty:  KnockoutObservable<number> = ko.observable(null);
        displayHalfDayValue: KnockoutObservable<boolean> = ko.observable(false);
        changeWorkHourValue: KnockoutObservable<boolean> = ko.observable(false);
        displayChangeWorkHour:  KnockoutObservable<boolean> = ko.observable(true);
        contentFlg: KnockoutObservable<boolean> = ko.observable(true);
        eblTimeStart1:  KnockoutObservable<boolean> = ko.observable(false);
        eblTimeEnd1:  KnockoutObservable<boolean> = ko.observable(false);
        //TIME LINE 1
        timeStart1: KnockoutObservable<number> = ko.observable(null);
        timeEnd1: KnockoutObservable<number> = ko.observable(null);   
        //TIME LINE 2
        timeStart2: KnockoutObservable<number> = ko.observable(null);
        timeEnd2: KnockoutObservable<number> = ko.observable(null);
        //勤務種類
        workTypeCd: KnockoutObservable<string> = ko.observable('');
        workTypeName: KnockoutObservable<string> = ko.observable('');
        //勤務種類
        siftCD: KnockoutObservable<string> = ko.observable('');
        siftName: KnockoutObservable<string> = ko.observable('');
        //comboBox 定型理由
        reasonCombo: KnockoutObservableArray<common.ComboReason> = ko.observableArray([]);
        selectedReason: KnockoutObservable<string> = ko.observable('');
        //MultilineEditor
        requiredReason : KnockoutObservable<boolean> = ko.observable(false);
        multilContent: KnockoutObservable<string> = ko.observable('');
        //comboBox 定型理由
        reasonCombo2: KnockoutObservableArray<common.ComboReason> = ko.observableArray([]);
        selectedReason2: KnockoutObservable<string> = ko.observable('');
        //MultilineEditor
        requiredReason2 : KnockoutObservable<boolean> = ko.observable(false);
        multilContent2: KnockoutObservable<string> = ko.observable('');
        //Approval 
        approvalSource: Array<common.AppApprovalPhase> = [];
        employeeID : string ="000426a2-181b-4c7f-abc8-6fff9f4f983a";
        //休憩時間
        restTime: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
        //残業時間
        overtimeHours: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
        //休出時間
        breakTimes: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
        //加給時間OverTimeInput
        bonusTimes: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
        //menu-bar 
        enableSendMail :KnockoutObservable<boolean> = ko.observable(true); 
        prePostDisp: KnockoutObservable<boolean> = ko.observable(true);
        prePostEnable: KnockoutObservable<boolean> = ko.observable(true);
        useMulti: KnockoutObservable<boolean> = ko.observable(true);
        
        displayBonusTime: KnockoutObservable<boolean> = ko.observable(false);
        displayCaculationTime: KnockoutObservable<boolean> = ko.observable(false);
        displayPrePostFlg: KnockoutObservable<boolean> = ko.observable(true); 
        displayRestTime: KnockoutObservable<boolean> = ko.observable(false);
        
        typicalReasonDisplayFlg: KnockoutObservable<boolean> = ko.observable(true);
        displayAppReasonContentFlg: KnockoutObservable<boolean> = ko.observable(true);
        displayDivergenceReasonForm: KnockoutObservable<boolean> = ko.observable(false);
        displayDivergenceReasonInput: KnockoutObservable<boolean> = ko.observable(false);
        
        constructor() {
            
            let self = this;
          
            
            $("#fixed-overtime-hour-table").ntsFixedTable({ height: 216 });
            $("#fixed-break_time-table").ntsFixedTable({ height: 120 });
            $("#fixed-bonus_time-table").ntsFixedTable({ height: 120 });
            //KAF000_A
            self.kaf000_a = new kaf000.a.viewmodel.ScreenModel();
            //startPage 005a AFTER start 000_A
            self.startPage().done(function(){
                self.kaf000_a.start(self.employeeID,1,0,moment(new Date()).format("YYYY/MM/DD")).done(function(){
                    self.approvalSource = self.kaf000_a.approvalList;
                    $("#fixed-table").ntsFixedTable({ height: 120 });
                })    
            })
            
        }
        /**
         * 
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            service.getAppForLeaveStart({
                appDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : moment(self.startAppDate()).format(self.DATE_FORMAT),
                employeeID: null        
            }).done((data) => {
                self.initData(data);
                nts.uk.ui.block.clear();
                dfd.resolve(data);    
            }).fail((res) => {
                if(res.messageId == 'Msg_426'){
                    dialog.alertError({messageId : res.messageId}).then(function(){
                        nts.uk.ui.block.clear();
                    });
                }else if(res.messageId == 'Msg_473'){
                   dialog.alertError({messageId : res.messageId}).then(function(){
                        nts.uk.ui.block.clear();
                    });
                }else{
                     nts.uk.ui.dialog.alertError({messageId : res.messageId}).then(function(){
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml"); 
                            nts.uk.ui.block.clear();
                        });
                }
                dfd.reject(res);     
            });
            return dfd.promise();
            
        }
        
        initData(data: any){
            let self = this;
            self.manualSendMailAtr(data.manualSendMailFlg);
            self.employeeName(data.employeeName);
            self.prePostSelected(data.application.prePostAtr);
            self.convertListHolidayType(data.holidayAppTypes);
            self.holidayTypeCode(10);
            self.displayPrePostFlg(data.prePostFlg);
        }
         registerClick(){
             
         }
        btnSelectWorkTimeZone(){
            let self = this;
            self.eblTimeStart1(self.changeWorkHourValue() && !nts.uk.util.isNullOrEmpty(self.timeStart1()));
            self.eblTimeEnd1(self.changeWorkHourValue() && !nts.uk.util.isNullOrEmpty(self.timeEnd1()));
        }
        convertListHolidayType(data: any){
            let self =  this;
            let nameHolidayType :[] = { 0: "年次有休",1: "代休",2: "振休",3: "欠勤",4: "特別休暇",5: "積立年休",6: "休日",7: "時間消化"};
            for(let i = 0; i < data.length ; i++){
                self.holidayTypes.push(new common.HolidayType(i,nameHolidayType[i]));
            }
        }
        
        /**
         * KDL003
         */
        openDialogKdl003() {
            let self = this;
            let workTypeCodes = [];
            let workTimeCodes = [];
            nts.uk.ui.windows.setShared('parentCodes', {
                workTypeCodes: workTypeCodes,
                selectedWorkTypeCode: self.workTypeCd(),
                workTimeCodes: workTimeCodes,
                selectedWorkTimeCode: self.siftCD()
            }, true);

            nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                //view all code of selected item 
                var childData = nts.uk.ui.windows.getShared('childData');
                if (childData) {
                    self.workTypeCd(childData.selectedWorkTypeCode);
                    self.workTypeName(childData.selectedWorkTypeName);
                    self.siftCD(childData.selectedWorkTimeCode);
                    self.siftName(childData.selectedWorkTimeName);
                }
            })
        }
        /**
         * Jump to CMM018 Screen
         */
        openCMM018(){
            let self = this;
            nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", {screen: 'Application', employeeId: self.employeeID});  
        }
    }
    
}

