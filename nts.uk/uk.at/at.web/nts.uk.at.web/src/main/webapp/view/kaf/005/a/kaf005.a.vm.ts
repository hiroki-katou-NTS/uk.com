module nts.uk.at.view.kaf005.a.viewmodel {
    import common = nts.uk.at.view.kaf005.share.common;
    import service = nts.uk.at.view.kaf005.shr.service;
    export class ScreenModel {
        //kaf000
        kaf000_a: kaf000.a.viewmodel.ScreenModel;
        //current Data
        //        curentGoBackDirect: KnockoutObservable<common.GoBackDirectData>;
        //申請者
        employeeName: KnockoutObservable<string> = ko.observable("");
        //Pre-POST
        prePostSelected: KnockoutObservable<number> = ko.observable(0);
        workState: KnockoutObservable<boolean> = ko.observable(true);;
        typeSiftVisible: KnockoutObservable<boolean> = ko.observable(true);
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
        employeeID: string = "000426a2-181b-4c7f-abc8-6fff9f4f983a";
        //休憩時間
        restTime: KnockoutObservableArray<common.RestTime> = ko.observableArray([]);
        //残業時間
        overtimeHours: KnockoutObservableArray<common.OvertimeHour> = ko.observableArray([]);
        //休出時間
        breakTimes: KnockoutObservableArray<common.BreakTime> = ko.observableArray([]);
        //加給時間
        bonusTimes: KnockoutObservableArray<common.BonusTime> = ko.observableArray([]);
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

        constructor() {

            let self = this;
            $("#fixed-overtime-hour-table").ntsFixedTable({ height: 216 });
            $("#fixed-break_time-table").ntsFixedTable({ height: 120 });
            $("#fixed-bonus_time-table").ntsFixedTable({ height: 120 });
            //KAF000_A
            self.kaf000_a = new kaf000.a.viewmodel.ScreenModel();
            //startPage 005a AFTER start 000_A
            self.startPage().done(function() {
                self.kaf000_a.start(self.employeeID, 1, 0, moment(new Date()).format("YYYY/MM/DD")).done(function() {
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
                appDate: moment(new Date()).format("YYYY/MM/DD"),
                uiType: 1
            }).done((data) => {
                self.initData(data);
                dfd.resolve(data);
            }).fail((res) => {
                dfd.reject(res);
            });
            return dfd.promise();

        }

        initData(data: any) {
            var self = this;
            self.displayPrePostFlg(data.displayPrePostFlg ? true : false);
            self.prePostSelected(data.application.prePostAtr);
            self.displayCaculationTime(data.displayCaculationTime);
            self.typicalReasonDisplayFlg(data.typicalReasonDisplayFlg);
            self.displayAppReasonContentFlg(data.displayAppReasonContentFlg);
            self.displayDivergenceReasonForm(data.displayDivergenceReasonForm);
            self.displayDivergenceReasonInput(data.displayDivergenceReasonInput);
            self.displayBonusTime(data.displayBonusTime);
            self.restTimeDisFlg(data.displayRestTime);
            self.employeeName(data.employeeName);
            self.siftCD(data.siftType.siftCode);
            self.siftName(data.siftType.siftName);
            self.workTypeCd(data.workType.workTypeCode);
            self.workTypeName(data.workType.workTypeName);
            self.timeStart1(data.workClockFrom1);
            self.timeEnd1(data.workClockFrom2);
            self.timeStart2(data.workClockTo1);
            self.timeEnd2(data.workClockTo2);

            self.reasonCombo(_.map(data.applicationReasonDtos, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); }));
            self.selectedReason(data.application.appReasonID);
            self.multilContent(data.application.applicationReason);
            self.reasonCombo2(_.map(data.divergenceReasonDtos, o => { return new common.ComboReason(o.divergenceReasonID, o.reasonTemp); }));
            self.selectedReason2(data.divergenceReasonID);
            self.multilContent2(data.divergenceReasonContent);

            // 休憩時間
            for(let i = 0;i < 11; i++){
                 self.restTime.push( new common.RestTime(i,null,null));

            }
            // 残業時間
            if(data.overTimeInputs != null){
                for(let i = 0;i < data.overTimeInputs.length;i++){
                    if(data.overTimeInputs[i].attendanceID == 1){
                        self.overtimeHours.push(new common.OverTimeInput("", "", 1, , "", data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime,null));
                        //self.overtimeHours.push(new common.OvertimeHour(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime,null));
                    }
                    if(data.overTimeInputs[i].attendanceID == 2){
                        self.breakTimes.push(new common.BreakTime(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime));
                    }
                    if(data.overTimeInputs[i].attendanceID == 3){
                        self.bonusTimes.push(new common.BonusTime(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime));
                    }
                }
            }
            //
            if(data.appOvertimeNightFlg == 1){
                 self.overtimeHours.push(new common.OvertimeHour("overTimeShiftNight",nts.uk.resource.getText("KAF005_64"),"0",null,null));
            }
            
        }
        //登録処理
        registerClick() {
            let self = this,
            appReason: string,
            divergenceReason: string;     
            appReason =self.selectedReason();     
            divergenceReason = self.selectedReason2(); 
            if(!nts.uk.util.isNullOrUndefined(self.multilContent()))
            {
                appReason = appReason + "CHAR(13)" + self.multilContent();
            }
            if(!nts.uk.util.isNullOrUndefined(self.multilContent2()))
            {
                divergenceReason = divergenceReason + "CHAR(13)" + self.multilContent2();
            }
            let overtime: AppOverTime = {
            applicationDate: self.appDate(),
            prePostAtr: self.prePostSelected(),
            applicantSID: self.employeeID,
            applicationReason: appReason,
            appApprovalPhaseCmds: self.kaf000_a.approvalList,
            workType: self.workTypeCd(),
            siftType: self.siftCD(),
            workClockFrom1: self.timeStart1(),
            workClockTo1: self.timeEnd1(),
            workClockFrom2: self.timeStart2(),
            workClockTo2: self.timeEnd1(),
            bonusTimes: self.bonusTimes(),
            overtimeHours: self.overtimeHours(),
            breakTimes: self.breakTimes(),
            restTime: self.restTime(),
            overTimeShiftNight: 100,
            flexExessTime: 100,
            divergenceReasonContent: divergenceReason
            };            
            //登録前エラーチェック
            //TODO:           
            //登録処理を実行
            service.createOvertime(overtime).done((data) => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    location.reload();
                });
            }).fail((res) => {
                if (res.optimisticLock == true) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_197" }).then(function() {
                        location.reload();
                    });
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() { nts.uk.ui.block.clear(); });
                }
            });
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
        openCMM018() {
            let self = this;
            nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", { screen: 'Application', employeeId: self.employeeID });
        }
    }

}

