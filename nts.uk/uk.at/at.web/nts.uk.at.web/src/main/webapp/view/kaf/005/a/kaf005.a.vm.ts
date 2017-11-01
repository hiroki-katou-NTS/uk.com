module nts.uk.at.view.kaf005.a.viewmodel {
    import common = nts.uk.at.view.kaf005.share.common;
    export class ScreenModel {
        //kaf000
        kaf000_a: kaf000.a.viewmodel.ScreenModel;
        //current Data
//        curentGoBackDirect: KnockoutObservable<common.GoBackDirectData>;
        //申請者
        employeeName: KnockoutObservable<string> = ko.observable("Vu Thang Loi");
        //Pre-POST
        prePostSelected: KnockoutObservable<number> = ko.observable(0);
        workState : KnockoutObservable<boolean> = ko.observable(true);;
        typeSiftVisible : KnockoutObservable<boolean> = ko.observable(true);
        // 申請日付
        appDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));;
        //TIME LINE 1
        timeStart1: KnockoutObservable<number> = ko.observable(0);
        timeEnd1: KnockoutObservable<number> = ko.observable(0);   
        //TIME LINE 2
        timeStart2: KnockoutObservable<number> = ko.observable(0);
        timeEnd2: KnockoutObservable<number> = ko.observable(0);
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
        //Approval 
        approvalSource: Array<common.AppApprovalPhase> = [];
        employeeID : string ="000426a2-181b-4c7f-abc8-6fff9f4f983a";
        //休憩時間
        restTime: KnockoutObservableArray<common.RestTime> = ko.observableArray([]);
        //残業時間
        overtimeHours: KnockoutObservableArray<common.OvertimeHour> = ko.observableArray([]);
        //休出時間
        breakTimes: KnockoutObservableArray<common.BreakTime> = ko.observableArray([]);
        //加給時間
        bonusTimes: KnockoutObservableArray<common.BonusTime> = ko.observableArray([]);
        //menu-bar 
        enableSendMail :KnockoutObservable<boolean> = ko.observable(true); 
        prePostDisp: KnockoutObservable<boolean> = ko.observable(true);
        prePostEnable: KnockoutObservable<boolean> = ko.observable(true);
        useMulti : KnockoutObservable<boolean> = ko.observable(true);
        
        constructor() {
            
            let self = this;
          
            self.restTime.push( new common.RestTime("",null,null));
            self.restTime.push( new common.RestTime("2",-1050,1256));
            self.restTime.push( new common.RestTime("3",-1060,1256));
            
            self.overtimeHours.push(new common.OvertimeHour("1","12:00","11:00","100","120","100"));
            self.overtimeHours.push(new common.OvertimeHour("1","12:00","11:00","100","120","100"));
            self.overtimeHours.push(new common.OvertimeHour("1","12:00","11:00","100","120","100"));
            self.overtimeHours.push(new common.OvertimeHour("1","12:00","11:00","100","120","100"));
            self.overtimeHours.push(new common.OvertimeHour("1","12:00","11:00","100","120","100"));
            self.overtimeHours.push(new common.OvertimeHour("1","12:00","11:00","100","120","100"));
            
            self.breakTimes.push(new common.BreakTime("1","12:00","11:00","100","120","100"));
            self.breakTimes.push(new common.BreakTime("1","12:00","11:00","100","120","100"));
            self.breakTimes.push(new common.BreakTime("1","12:00","11:00","100","120","100"));
            
            self.bonusTimes.push(new common.BonusTime("1","加給時間1","11:00","100","120"));
            self.bonusTimes.push(new common.BonusTime("1","加給時間2","11:00","100","120"));
            self.bonusTimes.push(new common.BonusTime("1","加給時間3","11:00","100","120"));
            
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
            
            
            dfd.resolve();
            return dfd.promise();
            
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

