module nts.uk.at.view.kaf010.cm.viewmodel {
    import common = nts.uk.at.view.kaf010.share.common;
    import service = nts.uk.at.view.kaf010.shr.service;
    export class ScreenModel {
        //kaf000
        kaf000_a: kaf000.a.viewmodel.ScreenModel;
        //current Data
//        curentGoBackDirect: KnockoutObservable<common.GoBackDirectData>;
        //申請者
        employeeName: KnockoutObservable<string> = ko.observable("");
        //Pre-POST
        prePostSelected: KnockoutObservable<number> = ko.observable(0);
        workState : KnockoutObservable<boolean> = ko.observable(true);;
        typeSiftVisible : KnockoutObservable<boolean> = ko.observable(true);
        // 申請日付
        appDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));;
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
        displayPrePostFlg: KnockoutObservable<boolean> = ko.observable(false); 
        displayRestTime: KnockoutObservable<boolean> = ko.observable(false);
        
        typicalReasonDisplayFlg: KnockoutObservable<boolean> = ko.observable(false);
        displayAppReasonContentFlg: KnockoutObservable<boolean> = ko.observable(false);
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
            service.getOvertimeByUI({
                url: "",
                appDate: "2017/11/03",
                uiType: 0        
            }).done((data) => {
                self.initData(data);
                dfd.resolve(data);    
            }).fail((res) => {
                dfd.reject(res);     
            });
            return dfd.promise();
            
        }
        
        initData(data: any){
            var self = this;
            self.displayCaculationTime(data.calculationOverTime ? true : false);
            self.typicalReasonDisplayFlg(data.typicalReasonDisplayFlg);
            self.displayAppReasonContentFlg(data.displayAppReasonContentFlg);
            self.displayDivergenceReasonForm(data.displayDivergenceReasonForm);
            self.displayDivergenceReasonInput(data.displayDivergenceReasonInput);
            self.employeeName(data.employeeName);
            self.siftCD(data.siftCode);
            self.siftName(data.siftName);
            self.workTypeCd(data.workTypeCd);
            self.workTypeName(data.workTypeName);
            self.timeStart1(data.workClockFrom1);
            self.timeEnd1(data.workClockFrom2); 
            self.timeStart2(data.workClockTo1);
            self.timeEnd2(data.workClockTo2); 
            
            self.reasonCombo(_.map(data.applicationReasonDtos, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); } ));
            self.selectedReason(data.application.appReasonID);
            self.multilContent(data.application.applicationReason);
            self.reasonCombo2(_.map(data.divergenceReasonDtos, o => { return new common.ComboReason(o.divergenceReasonID, o.reasonTemp); } ));
            self.selectedReason2(data.divergenceReasonID);
            self.multilContent2(data.divergenceReasonContent);
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

