module nts.uk.at.view.kaf005.a.viewmodel {
    import common = nts.uk.at.view.kaf005.share.common;
    import service = nts.uk.at.view.kaf005.shr.service;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
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
        restTime: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
        //残業時間
        overtimeHours: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
        //休出時間
        breakTimes: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
        //加給時間
        bonusTimes: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
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

        constructor() {

            let self = this;
            $("#fixed-overtime-hour-table").ntsFixedTable({ height: 216 });
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
                uiType: 0
            }).done((data) => {
                self.initData(data);
                $("#inputdate").focus();
                dfd.resolve(data);
            }).fail((res) => {
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

            self.reasonCombo(_.map(data.applicationReasonDtos, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); }));
            self.selectedReason(_.find(data.applicationReasonDtos, o => { return o.defaultFlg == 1 }).reasonID);
            self.multilContent(data.application.applicationReason);
            self.reasonCombo2(_.map(data.divergenceReasonDtos, o => { return new common.ComboReason(o.divergenceReasonID, o.reasonTemp); }));
            self.selectedReason2(data.divergenceReasonDtos.divergenceReasonIdDefault);
            self.multilContent2(data.divergenceReasonContent);
            self.instructInforFlag(data.displayOvertimeInstructInforFlg);
            self.instructInfor(data.overtimeInstructInformation);
            self.referencePanelFlg(data.referencePanelFlg);
            self.preAppPanelFlg(data.preAppPanelFlg);
            // 休憩時間
            for (let i = 0; i < 11; i++) {
                self.restTime.push(new common.OverTimeInput("", "", 0, "", i, i, 0, 0, null));
            }
            // 残業時間
            if (data.overTimeInputs != null) {
                for (let i = 0; i < data.overTimeInputs.length; i++) {
                    if (data.overTimeInputs[i].attendanceID == 1) {
                        self.overtimeHours.push(new common.OverTimeInput("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo, data.overTimeInputs[i].frameName, "0", data.overTimeInputs[i].endTime, null));
                        //self.overtimeHours.push(new common.OvertimeHour(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime,null));
                    }
                    if (data.overTimeInputs[i].attendanceID == 2) {
                        self.breakTimes.push(new common.OverTimeInput("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo, data.overTimeInputs[i].frameName, "0", data.overTimeInputs[i].endTime, null));
                        //self.breakTimes.push(new common.BreakTime(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime));
                    }
                    if (data.overTimeInputs[i].attendanceID == 3) {
                        //self.bonusTimes.push(new common.BonusTime(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime));
                        self.bonusTimes.push(new common.OverTimeInput("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo, data.overTimeInputs[i].frameName, "0", data.overTimeInputs[i].endTime, null));
                    }
                }
            }
            //
            if (data.appOvertimeNightFlg == 1) {
                //self.overtimeHours.push(new common.OvertimeHour("overTimeShiftNight",nts.uk.resource.getText("KAF005_64"),"0",null,null));
                self.overtimeHours.push(new common.OverTimeInput("", "", 1, "", 11, nts.uk.resource.getText("KAF005_64"), "0", null, null));
            }

        }
        //登録処理
        registerClick() {
            let self = this,
                appReason: string,
                divergenceReason: string;
            appReason = self.selectedReason();
            divergenceReason = self.selectedReason2();
            if (!nts.uk.util.isNullOrUndefined(self.multilContent())) {
                appReason = appReason + ":" + self.multilContent();
            }
            if (!nts.uk.util.isNullOrUndefined(self.multilContent2())) {
                divergenceReason = divergenceReason + ":" + self.multilContent2();
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
                bonusTimes: ko.toJS(self.bonusTimes()),
                overtimeHours: ko.toJS(self.overtimeHours()),
                breakTimes: ko.toJS(self.breakTimes()),
                restTime: ko.toJS(self.restTime()),
                overTimeShiftNight: 100,
                flexExessTime: 100,
                divergenceReasonContent: divergenceReason
            };
            //登録前エラーチェック
            service.checkBeforeRegister(overtime).done((data) => {
                if (data.errorCode == 0) {
                    if (data.isConfirm) {
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
                } else {
                    //Change background color
                    dialog.alertError("Change backgroud:attendanceId=" + data.attendanceId + "frameNo:" + data.frameNo);
                }
            }).fail((res) => {
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() { nts.uk.ui.block.clear(); });
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
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() { nts.uk.ui.block.clear(); });
            });
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

