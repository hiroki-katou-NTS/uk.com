module nts.uk.at.view.kaf005.b {
    import common = nts.uk.at.view.kaf005.share.common;
    import model = nts.uk.at.view.kaf000.b.viewmodel.model;
    import service = nts.uk.at.view.kaf005.shr.service;
    export module viewmodel {
        export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {
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
            appDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
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
            employeeID: KnockoutObservable<string> = ko.observable('');
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
            displayCaculationTime: KnockoutObservable<boolean> = ko.observable(true);
            displayPrePostFlg: KnockoutObservable<boolean> = ko.observable(true);
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
            appDatePre: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
            workTypeCodePre:  KnockoutObservable<string> = ko.observable("");
            workTypeNamePre:  KnockoutObservable<string> = ko.observable("");
            siftCodePre:  KnockoutObservable<string> = ko.observable("");
            siftNamePre:  KnockoutObservable<string> = ko.observable("");
            //TIME LINE 1
            workClockFrom1Pre: KnockoutObservable<number> = ko.observable(null);
            workClockTo1Pre: KnockoutObservable<number> = ko.observable(null);
            //TIME LINE 2
            workClockFrom2Pre: KnockoutObservable<number> = ko.observable(null);
            workClockTo2Pre: KnockoutObservable<number> = ko.observable(null);
            overtimeHoursPre: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
            overTimeShiftNightPre: KnockoutObservable<number> = ko.observable(null);
            flexExessTimePre: KnockoutObservable<number> = ko.observable(null);
            //　初期起動時、計算フラグ=1とする。
            //calculateFlag: KnockoutObservable<number> = ko.observable(1);
            //TODO: test-setting calculateFlag = 0
            calculateFlag: KnockoutObservable<number> = ko.observable(0);
            version: number = 0;
            constructor(listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata) {
                super(listAppMetadata, currentApp);
                var self = this;
                $("#fixed-overtime-hour-table").ntsFixedTable({ height: 216 });
                $("#fixed-break_time-table").ntsFixedTable({ height: 120 });
                $("#fixed-bonus_time-table").ntsFixedTable({ height: 120 });
                $("#fixed-table-indicate").ntsFixedTable({ height: 120 });
                self.startPage(self.appID());
            }
            
            startPage(appID: string): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                service.findByAppID(appID).done((data) => { 
                    self.initData(data);
                    dfd.resolve(); 
                })
                .fail(function(res) { 
                    nts.uk.ui.dialog.alertError(res.message).then(function(){
                        nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml"); T
                        nts.uk.ui.block.clear();
                    });
                    dfd.reject(res);  
                });
                return dfd.promise();
            }
            
            initData(data: any) {
                var self = this;
                self.version = data.version;
                self.manualSendMailAtr(data.manualSendMailAtr);
                self.prePostSelected(data.application.prePostAtr);
                self.typicalReasonDisplayFlg(data.typicalReasonDisplayFlg);
                self.displayAppReasonContentFlg(data.displayAppReasonContentFlg);
                self.displayDivergenceReasonForm(data.displayDivergenceReasonForm);
                self.displayDivergenceReasonInput(data.displayDivergenceReasonInput);
                self.displayBonusTime(data.displayBonusTime);
                self.restTimeDisFlg(data.displayRestTime);
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
                self.timeStart1(data.workClockFrom1);
                self.timeEnd1(data.workClockFrom2);
                self.timeStart2(data.workClockTo1);
                self.timeEnd2(data.workClockTo2);
                if(data.applicationReasonDtos != null){
                    let reasonID = _.find(data.applicationReasonDtos, o => { return o.defaultFlg == 1 }).reasonID;
                    self.selectedReason(reasonID);
                    
                    let lstReasonCombo = _.map(data.applicationReasonDtos, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); });
                    self.reasonCombo(lstReasonCombo);
                    
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
                if(data.preAppOvertimeDto != null){
                    self.appDatePre(data.preAppOvertimeDto.appDatePre);
                    if(data.preAppOvertimeDto.workTypePre != null){
                        self.workTypeCodePre(data.preAppOvertimeDto.workTypePre.workTypeCode);
                        self.workTypeNamePre(data.preAppOvertimeDto.workTypePre.workTypeName);
                    }
                    if(data.siftTypePre != null){
                        self.siftCodePre(data.preAppOvertimeDto.siftTypePre.siftCode);
                        self.siftNamePre(data.preAppOvertimeDto.siftTypePre.siftName);
                    }
                    self.workClockFrom1Pre(data.preAppOvertimeDto.workClockFrom1Pre);
                    self.workClockTo1Pre(data.preAppOvertimeDto.workClockTo1Pre);
                    self.workClockFrom2Pre(data.preAppOvertimeDto.workClockFrom2Pre);
                    self.workClockTo2Pre(data.preAppOvertimeDto.workClockTo2Pre);
                    if(data.preAppOvertimeDto.overTimeInputsPre != null){
                        for (let i = 0; i < data.preAppOvertimeDto.overTimeInputsPre.length; i++) {
                            self.overtimeHoursPre.push(new common.OverTimeInput("", "", data.preAppOvertimeDto.overTimeInputsPre[i].attendanceID, "", data.preAppOvertimeDto.overTimeInputsPre[i].frameNo,0, data.preAppOvertimeDto.overTimeInputsPre[i].frameName, data.preAppOvertimeDto.overTimeInputsPre[i].startTime, data.preAppOvertimeDto.overTimeInputsPre[i].endTime,data.preAppOvertimeDto.overTimeInputsPre[i].applicationTime,null));
                        }
                    }
                }
                
                let dataRestTime = _.filter(data.overTimeInputs, {'attendanceID': 0});
                let dataOverTime = _.filter(data.overTimeInputs, {'attendanceID': 1});
                let dataBreakTime = _.filter(data.overTimeInputs, {'attendanceID': 2});
                let dataBonusTime = _.filter(data.overTimeInputs, {'attendanceID': 3});
                if(nts.uk.util.isNullOrEmpty(dataRestTime)){
                    for (let i = 0; i < 11; i++) {
                        self.restTime.push(new common.OverTimeInput("", "", 0, "", i,0, i, 0, 0, null));
                    }    
                } else {
                    _.forEach(dataRestTime, (item) => { 
                        self.restTime.push(new common.OverTimeInput(
                            item.companyID, 
                            item.appID, 
                            item.attendanceID, 
                            "", 
                            item.frameNo, 
                            item.timeItemTypeAtr, 
                            item.frameName, 
                            item.startTime, 
                            item.endTime, 
                            item.applicationTime, ""));
                    });    
                }
                _.forEach(dataOverTime, (item) => { 
                    self.overtimeHours.push(new common.OverTimeInput(
                        item.companyID, 
                        item.appID, 
                        item.attendanceID, 
                        "", 
                        item.frameNo, 
                        item.timeItemTypeAtr, 
                        item.frameName, 
                        item.startTime, 
                        item.endTime, 
                        item.applicationTime, ""));
                }); 
                _.forEach(dataBreakTime, (item) => { 
                    self.breakTimes.push(new common.OverTimeInput(
                        item.companyID, 
                        item.appID, 
                        item.attendanceID, 
                        "", 
                        item.frameNo, 
                        item.timeItemTypeAtr, 
                        item.frameName, 
                        item.startTime, 
                        item.endTime, 
                        item.applicationTime, ""));
                }); 
                _.forEach(dataBonusTime, (item) => { 
                    self.bonusTimes.push(new common.OverTimeInput(
                        item.companyID, 
                        item.appID, 
                        item.attendanceID, 
                        "", 
                        item.frameNo, 
                        item.timeItemTypeAtr, 
                        item.frameName, 
                        item.startTime, 
                        item.endTime, 
                        item.applicationTime, ""));
                }); 
                
                /*
                // 休憩時間
                
                // 残業時間
                if (data.overTimeInputs != null) {
                    for (let i = 0; i < data.overTimeInputs.length; i++) {
                        if (data.overTimeInputs[i].attendanceID == 1) {
                            self.overtimeHours.push(new common.OverTimeInput("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,0, data.overTimeInputs[i].frameName, "0", data.overTimeInputs[i].endTime, null,"#[KAF005_55]"));
                            //self.overtimeHours.push(new common.OvertimeHour(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime,null));
                            
                        }
                        if (data.overTimeInputs[i].attendanceID == 2) {
                            self.breakTimes.push(new common.OverTimeInput("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,0,data.overTimeInputs[i].frameName, "0", data.overTimeInputs[i].endTime, null,));
                            //self.breakTimes.push(new common.BreakTime(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime));
                        }
                        if (data.overTimeInputs[i].attendanceID == 3) {
                            //self.bonusTimes.push(new common.BonusTime(data.overTimeInputs[i].frameNo,data.overTimeInputs[i].frameName,"0",data.overTimeInputs[i].endTime));
                            self.bonusTimes.push(new common.OverTimeInput("", "", data.overTimeInputs[i].attendanceID, "", data.overTimeInputs[i].frameNo,data.overTimeInputs[i].timeItemTypeAtr ,data.overTimeInputs[i].frameName, "0", data.overTimeInputs[i].endTime, null));
                        }
                    }
                }
                */
                //
                if (data.appOvertimeNightFlg == 1) {
                    //self.overtimeHours.push(new common.OvertimeHour("overTimeShiftNight",nts.uk.resource.getText("KAF005_64"),"0",null,null));
                    self.overtimeHours.push(new common.OverTimeInput("", "", 1, "", 11,0, nts.uk.resource.getText("KAF005_63"), "0", null, null,"KAF005_64"));
                }
                 self.overtimeHours.push(new common.OverTimeInput("", "", 1, "", 12,0, nts.uk.resource.getText("KAF005_65"), "0", null, null,"KAF005_66"));
            }
            
            update(): JQueryPromise<any> {
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
                let command = {
                    version: self.version,
                    appID: self.appID(),
                    applicationReason: appReason,
                    workTypeCode: self.workTypeCd(),
                    siftTypeCode: self.siftCD(),
                    workClockFrom1: self.timeStart1(),
                    workClockTo1: self.timeEnd1(),
                    workClockFrom2: self.timeStart2(),
                    workClockTo2: self.timeEnd2(),
                    breakTimes: ko.mapping.toJS(self.breakTimes()),
                    overtimeHours: ko.mapping.toJS(self.overtimeHours()),
                    restTime: ko.mapping.toJS(self.restTime()),
                    bonusTimes: ko.mapping.toJS(self.bonusTimes()),
                    overtimeAtr: 0,
                    overTimeShiftNight: self.overTimeShiftNightPre(),
                    flexExessTime: self.flexExessTimePre(),
                    divergenceReasonContent: divergenceReason
                }
                service.updateOvertime(command);
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
}