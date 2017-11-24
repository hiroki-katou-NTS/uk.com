module nts.uk.at.view.kaf005.a.viewmodel {
    import common = nts.uk.at.view.kaf005.share.common;
    import service = nts.uk.at.view.kaf005.shr.service;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        
        static DATEFORMART: string = "YYYY/MM/DD";
        //kaf000
        kaf000_a: kaf000.a.viewmodel.ScreenModel;
        //current Data
        //        curentGoBackDirect: KnockoutObservable<common.GoBackDirectData>;
        //manualSendMailAtr
        manualSendMailAtr: KnockoutObservable<boolean> = ko.observable(false);
        displayBreakTimeFlg: KnockoutObservable<boolean> = ko.observable(false);
        //申請者
        employeeName: KnockoutObservable<string> = ko.observable("");
        //Pre-POST
        prePostSelected: KnockoutObservable<number> = ko.observable(0);
        workState: KnockoutObservable<boolean> = ko.observable(true);;
        typeSiftVisible: KnockoutObservable<boolean> = ko.observable(true);
        // 申請日付
        appDate: KnockoutObservable<string> = ko.observable(moment().format("YYYY/MM/DD"));
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
        workTypecodes: KnockoutObservableArray<string> = ko.observableArray([]);
        workTimecodes: KnockoutObservableArray<string> = ko.observableArray([]);
        //comboBox 定型理由
        reasonCombo: KnockoutObservableArray<common.ComboReason> = ko.observableArray([]);
        selectedReason: KnockoutObservable<string> = ko.observable('');
        //MultilineEditor
        requiredReason: KnockoutObservable<boolean> = ko.observable(false);
        multilContent: KnockoutObservable<string> = ko.observable('');
        //comboBox 定型理由
        reasonCombo2: KnockoutObservableArray<common.ComboReason> = ko.observableArray([]);
        selectedReason2: KnockoutObservable<string> = ko.observable('');
        //MultilineEditor
        requiredReason2: KnockoutObservable<boolean> = ko.observable(false);
        multilContent2: KnockoutObservable<string> = ko.observable('');
        //Approval 
        approvalSource: Array<common.AppApprovalPhase> = [];
        employeeID: KnockoutObservable<string> = ko.observable('');
        heightOvertimeHours: KnockoutObservable<number> = ko.observable(null);
        //休憩時間
        restTime: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
        //残業時間
        overtimeHours: KnockoutObservableArray<common.OvertimeCaculation> = ko.observableArray([]);
        //休出時間
        breakTimes: KnockoutObservableArray<common.OvertimeCaculation> = ko.observableArray([]);
        //加給時間
        bonusTimes: KnockoutObservableArray<common.OvertimeCaculation> = ko.observableArray([]);
        //menu-bar 
        enableSendMail: KnockoutObservable<boolean> = ko.observable(true);
        prePostDisp: KnockoutObservable<boolean> = ko.observable(true);
        prePostEnable: KnockoutObservable<boolean> = ko.observable(true);
        useMulti: KnockoutObservable<boolean> = ko.observable(true);

        displayBonusTime: KnockoutObservable<boolean> = ko.observable(false);
        displayCaculationTime: KnockoutObservable<boolean> = ko.observable(false);
        displayPrePostFlg: KnockoutObservable<boolean> = ko.observable(false);
        displayRestTime: KnockoutObservable<boolean> = ko.observable(false);
        restTimeDisFlg: KnockoutObservable<boolean> = ko.observable(false); // RequestAppDetailSetting 
        typicalReasonDisplayFlg: KnockoutObservable<boolean> = ko.observable(false);
        displayAppReasonContentFlg: KnockoutObservable<boolean> = ko.observable(false);
        displayDivergenceReasonForm: KnockoutObservable<boolean> = ko.observable(false);
        displayDivergenceReasonInput: KnockoutObservable<boolean> = ko.observable(false);

        // 参照
        referencePanelFlg: KnockoutObservable<boolean> = ko.observable(false);
        preAppPanelFlg: KnockoutObservable<boolean> = ko.observable(false);
        
        instructInforFlag: KnockoutObservable <boolean> = ko.observable(true);
        instructInfor : KnockoutObservable <string> = ko.observable('');

        overtimeWork: KnockoutObservableArray<common.overtimeWork> = ko.observableArray([]);
        indicationOvertimeFlg: KnockoutObservable<boolean> = ko.observable(true);
        

        // preAppOvertime
        appDatePre: KnockoutObservable<string> = ko.observable(moment().format("YYYY/MM/DD"));
        workTypeCodePre:  KnockoutObservable<string> = ko.observable("");
        workTypeNamePre:  KnockoutObservable<string> = ko.observable("");
        siftCodePre:  KnockoutObservable<string> = ko.observable("");
        siftNamePre:  KnockoutObservable<string> = ko.observable("");
        //TIME LINE 1
        workClockFrom1Pre: KnockoutObservable<string> = ko.observable(null);
        workClockTo1Pre: KnockoutObservable<string> = ko.observable(null);
        //TIME LINE 2
        workClockFrom2Pre: KnockoutObservable<string> = ko.observable(null);
        workClockTo2Pre: KnockoutObservable<string> = ko.observable(null);
        overtimeHoursPre: KnockoutObservableArray<common.AppOvertimePre> = ko.observableArray([]);
        overTimeShiftNightPre: KnockoutObservable<string> = ko.observable(null);
        flexExessTimePre: KnockoutObservable<string> = ko.observable(null);
        //　初期起動時、計算フラグ=1とする。
        calculateFlag: KnockoutObservable<number> = ko.observable(1);
        constructor() {

            let self = this;
            
            $("#fixed-break_time-table").ntsFixedTable({ height: 120 });
            $("#fixed-bonus_time-table").ntsFixedTable({ height: 120 });
            $("#fixed-table-indicate").ntsFixedTable({ height: 120 });
            
           
            //KAF000_A
            self.kaf000_a = new kaf000.a.viewmodel.ScreenModel();
            //startPage 005a AFTER start 000_A
            self.startPage().done(function() {
                self.kaf000_a.start(self.employeeID, 1, 0, moment(new Date()).format("YYYY/MM/DD")).done(function() {
                    self.approvalSource = self.kaf000_a.approvalList;
                    $("#fixed-table").ntsFixedTable({ height: 120 });
                    $("#fixed-overtime-hour-table").ntsFixedTable({ height: self.heightOvertimeHours() });
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
                url: "REGULAROVERTIME",
                appDate: moment(new Date()).format("YYYY/MM/DD"),
                uiType: 0
            }).done((data) => {
                self.initData(data);
                $("#inputdate").focus();
                 // findByChangeAppDate
                self.appDate.subscribe(function(value){
                var dfd = $.Deferred();
                service.findByChangeAppDate({
                    appDate: moment(value).format("YYYY/MM/DD"),
                    prePostAtr: self.prePostSelected    
                }).done((data) =>{
                    self.findBychangeAppDateData(data);
                    dfd.resolve(data);
                }).fail((res) =>{
                        dfd.reject(res);
                    });
                    return dfd.promise();
                });
                self.prePostSelected.subscribe(function(value){
                    let dfd =$.Deferred();
                    service.checkConvertPrePost({
                    prePostAtr: value,
                    appDate: moment(self.appDate()).format("YYYY/MM/DD")
                    }).done((data) =>{
                        self.convertpreAppOvertimeDto(data);
                        self.referencePanelFlg(data.referencePanelFlg);
                        self.preAppPanelFlg(data.preAppPanelFlg);
                    }).fail((res) =>{
                        dfd.reject(res);    
                    });
                     return dfd.promise();
                });
                                
                dfd.resolve(data);
            }).fail((res) => {
                nts.uk.ui.dialog.alertError(res.messageId);
                dfd.reject(res);
                
            });
            return dfd.promise();

        }

        initData(data: any) {
            var self = this;
            self.manualSendMailAtr(data.manualSendMailAtr);
            self.displayPrePostFlg(data.displayPrePostFlg ? true : false);
            self.prePostSelected(data.application.prePostAtr);
            self.displayCaculationTime(data.displayCaculationTime);
            self.typicalReasonDisplayFlg(data.typicalReasonDisplayFlg);
            self.displayAppReasonContentFlg(data.displayAppReasonContentFlg);
            self.displayDivergenceReasonForm(data.displayDivergenceReasonForm);
            self.displayDivergenceReasonInput(data.displayDivergenceReasonInput);
            self.displayBonusTime(data.displayBonusTime);
            self.restTimeDisFlg(data.displayRestTime);
//            self.displayBreakTimeFlg();
            self.employeeName(data.employeeName);
            self.employeeID(data.employeeID);
            if (data.siftType != null) {
                self.siftCD(data.siftType.siftCode);
                self.siftName(data.siftType.siftName);
            }
            if (data.workType != null) {
                self.workTypeCd(data.workType.workTypeCode);
                self.workTypeName(data.workType.workTypeName);
            }
            self.workTypecodes(data.workTypes);
            self.workTimecodes(data.siftTypes);
            self.timeStart1(data.workClockFrom1);
            self.timeEnd1(data.workClockFrom2);
            self.timeStart2(data.workClockTo1);
            self.timeEnd2(data.workClockTo2);
            if(data.applicationReasonDtos != null){
                let lstReasonCombo = _.map(data.applicationReasonDtos, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); });
                self.reasonCombo(lstReasonCombo);
                let reasonID = _.find(data.applicationReasonDtos, o => { return o.defaultFlg == 1 }).reasonID;
                self.selectedReason(reasonID);
                
                self.multilContent(data.application.applicationReason);
            }
            
            if(data.divergenceReasonDtos != null){
                self.reasonCombo2(_.map(data.divergenceReasonDtos, o => { return new common.ComboReason(o.divergenceReasonID, o.reasonTemp); }));
                self.selectedReason2(data.divergenceReasonDtos.divergenceReasonIdDefault);
                self.multilContent2(data.divergenceReasonContent);
            }
            
            self.instructInforFlag(data.displayOvertimeInstructInforFlg);
            self.instructInfor(data.overtimeInstructInformation);
            self.referencePanelFlg(data.referencePanelFlg);
            self.preAppPanelFlg(data.preAppPanelFlg);
            // preAppOvertime
            self.convertpreAppOvertimeDto(data);
            // 休憩時間
            for (let i = 1; i < 11; i++) {
                self.restTime.push(new common.OverTimeInput("", "", 0, "", i,0, i, null, null, null,""));
            }
            // 残業時間
            if (data.overTimeInputs != null) {
                for (let i = 0; i < data.overTimeInputs.length; i++) {
                    if (data.overTimeInputs[i].attendanceID == 1) {
                        self.overtimeHours.push(new common.OvertimeCaculation("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,0, data.overTimeInputs[i].frameName, null, null, null,"#[KAF005_55]"));
                    }
                    if (data.overTimeInputs[i].attendanceID == 2) {
                        self.breakTimes.push(new common.OvertimeCaculation("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,0,data.overTimeInputs[i].frameName,null, null, null,""));
                    }
                    if (data.overTimeInputs[i].attendanceID == 3) {
                        self.bonusTimes.push(new common.OvertimeCaculation("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,data.overTimeInputs[i].timeItemTypeAtr ,data.overTimeInputs[i].frameName, null, null, null,""));
                    }
                }
            }
            //
            if (data.appOvertimeNightFlg == 1) {
                //self.overtimeHours.push(new common.OvertimeHour("overTimeShiftNight",nts.uk.resource.getText("KAF005_64"),"0",null,null));
                self.overtimeHours.push(new common.OvertimeCaculation("", "", 1, "", 11,0, nts.uk.resource.getText("KAF005_63"), null, null, null,"KAF005_64"));
            }
             self.overtimeHours.push(new common.OvertimeCaculation("", "", 1, "", 12,0, nts.uk.resource.getText("KAF005_65"), null, null, null,"KAF005_66"));
            if(data.overtimeAtr == 0){
                self.heightOvertimeHours(58);   
            }else if(data.overtimeAtr == 1){
                self.heightOvertimeHours(180);
            }else{
                self.heightOvertimeHours(216);
            }
            
        }
        //登録処理
        registerClick() {
            $("#inpStartTime1").trigger("validate");
            $("#inpEndTime1").trigger("validate");
            
            if (nts.uk.ui.errors.hasError()){return;}    
            
            nts.uk.ui.block.invisible();
            let self = this,
                appReason: string,
                divergenceReason: string;
            appReason = self.getReasonName(self.reasonCombo(), self.selectedReason());
            divergenceReason = self.getReasonName(self.reasonCombo2(), self.selectedReason2());       
            if (!nts.uk.util.isNullOrUndefined(self.multilContent())) {
                appReason = appReason + ":" + self.multilContent();
            }
            if (!nts.uk.util.isNullOrUndefined(self.multilContent2())) {
                divergenceReason = divergenceReason + ":" + self.multilContent2();
            }
            let overtime: common.AppOverTime = {
                applicationDate: self.appDate(),
                prePostAtr: self.prePostSelected(),
                applicantSID: self.employeeID,
                applicationReason: appReason,
                appApprovalPhaseCmds: self.kaf000_a.approvalList,
                workTypeCode: self.workTypeCd(),
                siftTypeCode: self.siftCD(),
                workClockFrom1: self.timeStart1(),
                workClockTo1: self.timeEnd1(),
                workClockFrom2: self.timeStart2(),
                workClockTo2: self.timeEnd1(),
                bonusTimes: ko.toJS(self.bonusTimes()),
                overtimeHours: ko.toJS(self.overtimeHours()),
                breakTimes: ko.toJS(self.breakTimes()),
                restTime: ko.toJS(self.restTime()),
                overTimeShiftNight: 100,
                flexExessTime: 100,
                divergenceReasonContent: divergenceReason,
                sendMail: self.manualSendMailAtr(),
                calculateFlag: self.calculateFlag()
            };
            //登録前エラーチェック
            service.checkBeforeRegister(overtime).done((data) => {                
                if (data.errorCode == 0) {
                    if (data.confirm) {
                        //メッセージNO：829
                        dialog.confirm({ messageId: "Msg_829" }).ifYes(() => {
                            //登録処理を実行
                            self.registerData(overtime);
                        }).ifNo(() => {
                            //終了状態：処理をキャンセル
                            return;
                        });
                    } else {
                        //登録処理を実行
                        self.registerData(overtime);
                    }
                } else if (data.errorCode == 1){
                    if(data.frameNo == -1){
                        //Setting color for item error
                        for (let i = 0; i < self.overtimeHours().length; i++) {
                            self.changeColor( self.overtimeHours()[i].attendanceID(), self.overtimeHours()[i].frameNo());
                        }
                    }else{
                      //Change background color
                        self.changeColor( data.attendanceId, data.frameNo);
                    }
                    
                }
            }).fail((res) => {
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                .then(function() { nts.uk.ui.block.clear(); });
            }).always(function(){
                nts.uk.ui.block.clear();
            });
        }
        //登録処理を実行
        registerData(overtime) {
            service.createOvertime(overtime).done(() => {
                //2-3.新規画面登録後の処理を実行
                //TODO:
                //メッセージを表示（Msg_15）
                //TODO:
                //  - 送信先リストに項目がいる 
                //      情報メッセージに（Msg_392）を表示する Display (Msg_392) in information message
                //  - 送信先リストに項目がない (There are no items in the destination list)
                //      - 情報メッセージを閉じる Close information message
                //      - メールを送信する(新規) Sending mail (new) (Đã có common xử lý)      
                //      - 画面をクリアする(起動時と同じ画面) Clear the screen (same screen as at startup)
                dialog.info({ messageId: "Msg_15" }).then(function() {
                    location.reload();
                });
            }).fail((res) => {
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                .then(function() { nts.uk.ui.block.clear(); });
            });
        }
        
        changeColor(attendanceId, frameNo){
            /*//休憩時間
            if(attendanceId == 0){
                $('td#restTime_'+attendanceId+'_'+frameNo).css('background', 'pink')
            }*/
            // 残業時間
            if(attendanceId == 1){
                $('td#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#FD4D4D')
                $('input#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#FD4D4D')
            }
            /*// 休出時間
            if(attendanceId == 2){
                $('td#breakTimesCheck_'+attendanceId+'_'+frameNo).css('background', 'pink')
            }
            //加給時間
            if(attendanceId == 3){
                $('td#breakTimesCheck_'+attendanceId+'_'+frameNo).css('background', 'pink')
            }*/
        }
        CaculationTime(){
            let self = this;
            let dfd = $.Deferred();
            //TODO: for test
            self.calculateFlag(0);
            let param : any ={
                overtimeHours: ko.toJS(self.overtimeHours()),
                bonusTimes: ko.toJS(self.bonusTimes()),
                prePostAtr : self.prePostSelected(),
                appDate : moment(self.appDate()).format("YYYY/MM/DD")
            }
            
            service.getCaculationResult(param).done(function(data){
               self.overtimeHours.removeAll();
               self.bonusTimes.removeAll();
                if(data != null){
                 for(let i =0; i < data.length; i++){
                   if(data[i].attendanceID == 1){
                        
                       if(data[i].frameNo != 11 && data[i].frameNo != 12){
                           self.overtimeHours.push(new common.OvertimeCaculation("", "",
                            data[i].attendanceID,
                             "", 
                             data[i].frameNo,
                             0, 
                             data[i].frameName,
                              data[i].applicationTime,
                              self.convertIntToTime(data[i].preAppTime),
                              self.convertIntToTime(data[i].caculationTime),"#[KAF005_55]"));
                       }else if(data[i].frameNo == 11){
                            self.overtimeHours.push(new common.OvertimeCaculation("", "",
                            data[i].attendanceID,
                             "", 
                             data[i].frameNo,
                             0, 
                             nts.uk.resource.getText("KAF005_63"),
                             data[i].applicationTime,
                             self.convertIntToTime(data[i].preAppTime),
                             self.convertIntToTime(data[i].caculationTime),"#[KAF005_64]"));
                       }else if(data[i].frameNo == 12){
                            self.overtimeHours.push(new common.OvertimeCaculation("", "",
                            data[i].attendanceID,
                             "", 
                             data[i].frameNo,
                             0, 
                             nts.uk.resource.getText("KAF005_65"),
                             data[i].applicationTime,
                             self.convertIntToTime(data[i].preAppTime),
                             self.convertIntToTime(data[i].caculationTime),"#[KAF005_66]"));
                       }
                       
                   }else if(data[i].attendanceID == 3){
                       self.bonusTimes.push(new common.OvertimeCaculation("", "", data[i].attendanceID,
                        "", data[i].frameNo,
                        data[i].timeItemTypeAtr ,
                        data[i].frameName, data[i].applicationTime,
                       self.convertIntToTime(data[i].preAppTime), null,""));
                   }   
                 }   
                }
                 dfd.resolve(data);
            }).fail(function(res){
                dfd.reject(res);
            });
            return dfd.promise();
        }
        
        getReasonName(reasonCombo: common.ComboReason, reasonId: string): string{  
            let self = this;
           let selectedReason = _.find(reasonCombo, item => {return item.reasonId == reasonId} );
           if(!nts.uk.util.isNullOrUndefined(selectedReason)){
              return selectedReason.reasonName; 
           }
           return "";
        }
        /**
         * KDL003
         */
        openDialogKdl003() {
            let self = this;
            
            nts.uk.ui.windows.setShared('parentCodes', {
                workTypeCodes: self.workTypecodes(),
                selectedWorkTypeCode: self.workTypeCd(),
                workTimeCodes: self.workTimecodes(),
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
        openCMM018() {
            let self = this;
            nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", { screen: 'Application', employeeId: self.employeeID });
        }
        
        findBychangeAppDateData(data: any) {
            var self = this;
            self.manualSendMailAtr(data.manualSendMailAtr);
            self.prePostSelected(data.application.prePostAtr);
            self.displayCaculationTime(data.displayCaculationTime);
            self.restTimeDisFlg(data.displayRestTime);
            self.employeeName(data.employeeName);
            if (data.siftType != null) {
                self.siftCD(data.siftType.siftCode);
                self.siftName(data.siftType.siftName);
            }
            if (data.workType != null) {
                self.workTypeCd(data.workType.workTypeCode);
                self.workTypeName(data.workType.workTypeName);
            }
            self.timeStart1(data.workClockFrom1);
            self.timeEnd1(data.workClockFrom2);
            self.timeStart2(data.workClockTo1);
            self.timeEnd2(data.workClockTo2);
            if(data.applicationReasonDtos != null){
                self.reasonCombo(_.map(data.applicationReasonDtos, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); }));
                self.selectedReason(_.find(data.applicationReasonDtos, o => { return o.defaultFlg == 1 }).reasonID);
                self.multilContent(data.application.applicationReason);
            }
            
            if(data.divergenceReasonDtos != null){
                self.reasonCombo2(_.map(data.divergenceReasonDtos, o => { return new common.ComboReason(o.divergenceReasonID, o.reasonTemp); }));
                self.selectedReason2(data.divergenceReasonDtos.divergenceReasonIdDefault);
                self.multilContent2(data.divergenceReasonContent);
            }
            
            self.instructInforFlag(data.displayOvertimeInstructInforFlg);
            self.instructInfor(data.overtimeInstructInformation);
            // preAppOvertime
            self.convertpreAppOvertimeDto(data);
           
             // 残業時間
            if (data.overTimeInputs != null) {
                for (let i = 0; i < data.overTimeInputs.length; i++) {
                    if (data.overTimeInputs[i].attendanceID == 1) {
                        self.overtimeHours.push(new common.OvertimeCaculation("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,0, data.overTimeInputs[i].frameName, null, null, null,"#[KAF005_55]"));
                    }
                    if (data.overTimeInputs[i].attendanceID == 2) {
                        self.breakTimes.push(new common.OvertimeCaculation("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,0,data.overTimeInputs[i].frameName, null, null, null,""));
                    }
                    if (data.overTimeInputs[i].attendanceID == 3) {
                        self.bonusTimes.push(new common.OvertimeCaculation("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,data.overTimeInputs[i].timeItemTypeAtr ,data.overTimeInputs[i].frameName, null, null, null,""));
                    }
                }
            }
            //
//            if (data.appOvertimeNightFlg == 1) {
//                //self.overtimeHours.push(new common.OvertimeHour("overTimeShiftNight",nts.uk.resource.getText("KAF005_64"),"0",null,null));
//                self.overtimeHours.push(new common.OverTimeInput("", "", 1, "", 11,0, nts.uk.resource.getText("KAF005_63"), 0, null, null,"KAF005_64"));
//            }
//             self.overtimeHours.push(new common.OverTimeInput("", "", 1, "", 12,0, nts.uk.resource.getText("KAF005_65"), 0, null, null,"KAF005_66"));
            if(data.overtimeAtr == 0){
                self.heightOvertimeHours(58);   
            }else if(data.overtimeAtr == 1){
                self.heightOvertimeHours(180);
            }else{
                self.heightOvertimeHours(216);
            }

        }
         convertpreAppOvertimeDto(data :any){
             let self = this;
             if(data.preAppOvertimeDto != null){
                self.appDatePre(data.preAppOvertimeDto.appDatePre);
                if(data.preAppOvertimeDto.workTypePre != null){
                    self.workTypeCodePre(data.preAppOvertimeDto.workTypePre.workTypeCode);
                    self.workTypeNamePre(data.preAppOvertimeDto.workTypePre.workTypeName);
                }
                if(data.preAppOvertimeDto.siftTypePre != null){
                    self.siftCodePre(data.preAppOvertimeDto.siftTypePre.siftCode);
                    self.siftNamePre(data.preAppOvertimeDto.siftTypePre.siftName);
                }
                self.workClockFrom1Pre(self.convertIntToTime(data.preAppOvertimeDto.workClockFrom1Pre));
                self.workClockTo1Pre(self.convertIntToTime(data.preAppOvertimeDto.workClockTo1Pre));
                self.workClockFrom2Pre(self.convertIntToTime(data.preAppOvertimeDto.workClockFrom2Pre));
                self.workClockTo2Pre(self.convertIntToTime(data.preAppOvertimeDto.workClockTo2Pre));
                self.overtimeHoursPre.removeAll();
                if(data.preAppOvertimeDto.overTimeInputsPre != null){
                    for (let i = 0; i < data.preAppOvertimeDto.overTimeInputsPre.length; i++) {
                        self.overtimeHoursPre.push(new common.AppOvertimePre("", "", 
                        data.preAppOvertimeDto.overTimeInputsPre[i].attendanceID,
                         "", data.preAppOvertimeDto.overTimeInputsPre[i].frameNo,
                         0, data.preAppOvertimeDto.overTimeInputsPre[i].frameName +" : ",
                         data.preAppOvertimeDto.overTimeInputsPre[i].startTime,
                         data.preAppOvertimeDto.overTimeInputsPre[i].endTime,
                         self.convertIntToTime(data.preAppOvertimeDto.overTimeInputsPre[i].applicationTime) ,null));
                    }
                }
                 self.overTimeShiftNightPre(self.convertIntToTime(data.preAppOvertimeDto.overTimeShiftNightPre));
                 self.flexExessTimePre(self.convertIntToTime(data.preAppOvertimeDto.flexExessTimePre));
            }
        }
       convertIntToTime(data : number) : string{
           let hourMinute : string = "";
        if(data == -1){
            return null;
        }else if (data == 0) {
            hourMinute = "00:00";
        }else if(data != null){
            let hour = Math.floor(data/60);
            let minutes = Math.floor(data%60);
            hourMinute = (hour < 10 ? ("0" + hour) : hour ) + ":"+ (minutes < 10 ? ("0" + minutes) : minutes);
        }
           return hourMinute;
       } 
        
    }

}

