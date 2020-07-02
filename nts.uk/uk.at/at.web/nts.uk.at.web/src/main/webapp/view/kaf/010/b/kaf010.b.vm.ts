module nts.uk.at.view.kaf010.b {
    import common = nts.uk.at.view.kaf010.share.common;
    import model = nts.uk.at.view.kaf000.b.viewmodel.model;
    import service = nts.uk.at.view.kaf010.shr.service;
    import dialog = nts.uk.ui.dialog;
    import appcommon = nts.uk.at.view.kaf000.shr.model;
    import util = nts.uk.util;
    import text = nts.uk.resource.getText;

    export module viewmodel {
        export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {
            DATE_FORMAT: string = "YYYY/MM/DD";
            datatest : any;
            screenModeNew: KnockoutObservable<boolean> = ko.observable(false);
            //current Data
            //        curentGoBackDirect: KnockoutObservable<common.GoBackDirectData>;
            //manualSendMailAtr
            manualSendMailAtr: KnockoutObservable<boolean> = ko.observable(false);
            displayBreakTimeFlg: KnockoutObservable<boolean> = ko.observable(false);
            //申請者
            employeeName: KnockoutObservable<string> = ko.observable("");
            employeeList: KnockoutObservableArray<common.EmployeeOT> = ko.observableArray([]);
            selectedEmplCodes: KnockoutObservable<string> = ko.observable(null);
            employeeFlag: KnockoutObservable<boolean> = ko.observable(false);
            totalEmployee: KnockoutObservable<string> = ko.observable(null);
            //Pre-POST
            prePostSelected: KnockoutObservable<number> = ko.observable(0);
            backSelected1: KnockoutObservable<number> = ko.observable(0);
            goSelected1 : KnockoutObservable<number> = ko.observable(0);
            backSelected2 : KnockoutObservable<number> = ko.observable(0);
            goSelected2 : KnockoutObservable<number> = ko.observable(0);
            goSelected1Value: KnockoutObservable<string> = ko.observable("");
            backSelected1Value: KnockoutObservable<string> = ko.observable("");
            workState: KnockoutObservable<boolean> = ko.observable(true);;
            typeSiftVisible: KnockoutObservable<boolean> = ko.observable(true);
            // 申請日付
            appDate: KnockoutObservable<string> = ko.observable(moment().format(this.DATE_FORMAT));
            enbAppDate: KnockoutObservable<boolean> = ko.observable(true);
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
            //休出時間
            restTime: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
            //残業時間
            overtimeHours: KnockoutObservableArray<common.OvertimeCaculation> = ko.observableArray([]);
            //休憩時間
            breakTimes: KnockoutObservableArray<common.OvertimeCaculation> = ko.observableArray([]);
            
            breakTimesOld: Array<common.OvertimeCaculation> = [];
            //加給時間
            bonusTimes: KnockoutObservableArray<common.OvertimeCaculation> = ko.observableArray([]);
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
            isRightContent: KnockoutObservable<boolean> = ko.observable(false);
            
            instructInforFlag: KnockoutObservable <boolean> = ko.observable(true);
            instructInfor : KnockoutObservable <string> = ko.observable('');
    
            overtimeWork: KnockoutObservableArray<common.overtimeWork> = ko.observableArray([]);
            indicationOvertimeFlg: KnockoutObservable<boolean> = ko.observable(true);
            
            // preAppOvertime
            appDatePre: KnockoutObservable<string> = ko.observable(moment().format(this.DATE_FORMAT));
            workTypeCodePre:  KnockoutObservable<string> = ko.observable("");
            workTypeNamePre:  KnockoutObservable<string> = ko.observable("");
            siftCodePre:  KnockoutObservable<string> = ko.observable("");
            siftNamePre:  KnockoutObservable<string> = ko.observable("");
            //TIME LINE 1
            workClockFrom1To1Pre: KnockoutObservable<string> = ko.observable(null);
            //TIME LINE 2
            workClockFrom2To2Pre: KnockoutObservable<string> = ko.observable(null);
            displayWorkClockFrom2To2Pre: KnockoutObservable <boolean> = ko.observable(true);
            overtimeHoursPre: KnockoutObservableArray<common.OverTimeInput> = ko.observableArray([]);
            overTimeShiftNightPre: KnockoutObservable<number> = ko.observable(null);
            flexExessTimePre: KnockoutObservable<number> = ko.observable(null);
            
            // AppOvertimeReference
            appDateReference: KnockoutObservable<string> = ko.observable(moment().format(this.DATE_FORMAT));
            workTypeCodeReference:  KnockoutObservable<string> = ko.observable("");
            workTypeNameReference:  KnockoutObservable<string> = ko.observable("");
            siftCodeReference:  KnockoutObservable<string> = ko.observable("");
            siftNameReference:  KnockoutObservable<string> = ko.observable("");
            //TIME LINE 1
            workClockFrom1To1Reference: KnockoutObservable<string> = ko.observable(null);
            //TIME LINE 2
            workClockFrom2To2Reference: KnockoutObservable<string> = ko.observable(null);
            displayWorkClockFrom2To2Reference: KnockoutObservable <boolean> = ko.observable(true);
            overtimeHoursReference: KnockoutObservableArray<common.AppOvertimePre> = ko.observableArray([]);
            overTimeShiftNightRefer: KnockoutObservable<string> = ko.observable(null);
            flexExessTimeRefer: KnockoutObservable<string> = ko.observable(null);
            //　初期起動時、計算フラグ=1とする。
            calculateFlag: KnockoutObservable<number> = ko.observable(0);
            version: number = 0;
            preWorkContent: common.WorkContent;
            inputDate: KnockoutObservable<string> = ko.observable('');
            allPreAppPanelFlg: KnockoutObservable<boolean> = ko.observable(false);
            //画面モード(表示/編集)
            editable: KnockoutObservable<boolean> = ko.observable(true);
            enableOvertimeInput: KnockoutObservable<boolean> = ko.observable(false);
            appCur: any = null;
            performanceExcessAtr: KnockoutObservable<number> = ko.observable(0);
            preExcessDisplaySetting: KnockoutObservable<number> = ko.observable(0);
            appHdWorkDispInfoDto: any = null;
            constructor(listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata, rebind?: boolean) {
                super(listAppMetadata, currentApp);
                var self = this;
                self.appCur = currentApp;
                self.startPage(self.appID()).done(function(){
                    if(nts.uk.util.isNullOrUndefined(rebind)){
                        $("#fixed-table-holiday").ntsFixedTable({ height: 120 });
                        $("#fixed-break_time-table-holiday").ntsFixedTable({ height: 119 });
                        $("#fixed-break_time-table-holiday-pre").ntsFixedTable({ height: 119 });
                        $("#fixed-overtime-hour-table-holiday").ntsFixedTable({ height: 216 });
                        $("#fixed-bonus_time-table-holiday").ntsFixedTable({ height: 120 });
                        $("#fixed-table-indicate-holiday").ntsFixedTable({ height: 120 });
                        $('.nts-fixed-table.cf').first().find('.nts-fixed-body-container.ui-iggrid').css('border-left','1px solid #CCC');     
                    } else {
                        if(rebind==true){
                            $("#fixed-table-holiday").ntsFixedTable({ height: 96 });
                            $("#fixed-break_time-table-holiday").ntsFixedTable({ height: 96 });
                            $("#fixed-break_time-table-holiday-pre").ntsFixedTable({ height: 96 });
                            $("#fixed-overtime-hour-table-holiday").ntsFixedTable({ height: 192 });
                            $("#fixed-bonus_time-table-holiday").ntsFixedTable({ height: 96 });
                            $("#fixed-table-indicate-holiday").ntsFixedTable({ height: 96 });
                            $('.nts-fixed-table.cf').first().find('.nts-fixed-body-container.ui-iggrid').css('border-left','1px solid #CCC');
                        }
                    }
                });
            }
            
            startPage(appID: string): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findByAppID(appID).done((data) => { 
                    self.initData(data);
                    self.preWorkContent = {
                        applicationDate: self.appDate(),
                        workType: self.workTypeCd(),
                        siftType: self.siftCD(),
                        workClockFrom1: self.timeStart1(),
                        workClockTo1: self.timeEnd1(),
                        workClockFrom2: self.timeStart2(),
                        workClockTo2: self.timeEnd2(),
                        breakTimes:  ko.toJS(self.breakTimes()),
                        restTime:  ko.toJS(self.restTime()),
                    }
                    self.checkRequiredBreakTimes();
                    //Check work content Changed
                    self.checkWorkContentChanged();
                    dfd.resolve();
                })
                .fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() {
                        nts.uk.ui.block.clear();
                        if (res.messageId === "Msg_198" || res.messageId == 'Msg_426') {
                            appcommon.CommonProcess.callCMM045();
                        } else {
                            nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");        
                        }
                    });
                    dfd.reject(res);  
                });
                return dfd.promise();
            }
            
            isShowReason() {
                let self = this;
                if (self.screenModeNew()) {
                    return self.displayAppReasonContentFlg();
                } else {
                    return self.typicalReasonDisplayFlg() || self.displayAppReasonContentFlg();
                }
            }
            getName(code, name) {
                let result = "";
                if (code) {
                    result = name || text("KAL003_120");
                }
                return result;
            }
            
            initData(data: any) {
                var self = this,
                    appHdWorkDispInfoDto = data.appHdWorkDispInfoOutput,
                    appDispInfoNoDateOutput = appHdWorkDispInfoDto.appDispInfoStartupOutput.appDispInfoNoDateOutput,
                    appDispInfoWithDateOutput = appHdWorkDispInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput,
                    approvalFunctionSet = appDispInfoWithDateOutput.approvalFunctionSet,
                    listAppTypeSet = appDispInfoNoDateOutput.requestSetting.applicationSetting.listAppTypeSetting,
                    appTypeSet = _.find(listAppTypeSet, o => o.appType == 6),
                    hdWorkDispInfoWithDateOutput = appHdWorkDispInfoDto.hdWorkDispInfoWithDateOutput,
                    overtimeRestAppCommonSettingDto = appHdWorkDispInfoDto.overtimeRestAppCommonSettingDto,
                    appHolidayWork = data.appHolidayWork,
                    appDetailScreenInfo = appHdWorkDispInfoDto.appDispInfoStartupOutput.appDetailScreenInfo,
                    applicationDto = appDetailScreenInfo.application;
                let appType = applicationDto.applicationType
                if (appType != 0) {
                    let paramLog = {
                        programId: 'KAF000',
                        screenId: 'B',
                        queryString: 'apptype=' + appType
                    };
                    nts.uk.at.view.kaf000.b.service.writeLog(paramLog);
                }
                self.appHdWorkDispInfoDto = appHdWorkDispInfoDto;
                self.inputCommandEvent().version = applicationDto.version;
                self.version = applicationDto.version;
                self.dataApplication(applicationDto);
                self.appType(applicationDto.applicationType);
                self.approvalRootState(ko.mapping.fromJS(appDetailScreenInfo.approvalLst)());
                self.displayReturnReasonPanel(!nts.uk.util.isNullOrEmpty(applicationDto.reversionReason));
                if (self.displayReturnReasonPanel()) {
                    let returnReason = applicationDto.reversionReason;
                    $("#returnReason").html(returnReason.replace(/\n/g, "\<br/>"));
                }
                self.reasonToApprover(appDetailScreenInfo.authorComment);
                self.setControlButton(
                    appDetailScreenInfo.user,
                    appDetailScreenInfo.approvalATR,
                    appDetailScreenInfo.reflectPlanState,
                    appDetailScreenInfo.authorizableFlags,
                    appDetailScreenInfo.alternateExpiration,
                    data.loginInputOrApproval);
                self.editable(appDetailScreenInfo.outputMode == 0 ? false : true);
                self.preExcessDisplaySetting(overtimeRestAppCommonSettingDto.preExcessDisplaySetting);
                self.performanceExcessAtr(overtimeRestAppCommonSettingDto.performanceExcessAtr);
                self.requiredReason(appDispInfoNoDateOutput.requestSetting.applicationSetting.appLimitSetting.requiredAppReason);
                self.version = applicationDto.version;
                self.enableOvertimeInput(approvalFunctionSet.timeInputUseAtr == 1 ? true : false);
                self.manualSendMailAtr(data.manualSendMailAtr);
                self.displayPrePostFlg(appDispInfoNoDateOutput.requestSetting.applicationSetting.appDisplaySetting.prePostAtrDisp);
                self.prePostSelected(applicationDto.prePostAtr);
                self.displayCaculationTime(approvalFunctionSet.timeCalUseAtr == 1 ? true : false);
                self.typicalReasonDisplayFlg(appTypeSet.displayFixedReason == 1 ? true : false);
                self.displayAppReasonContentFlg(appTypeSet.displayAppReason);
                self.displayDivergenceReasonForm(data.useInputDivergenceReason);
                self.displayDivergenceReasonInput(data.useComboDivergenceReason);
                self.displayBonusTime(false);
                self.restTimeDisFlg(approvalFunctionSet.timeCalUseAtr == 1 && approvalFunctionSet.breakInputFieldDisFlg == 1);
                self.appDate(applicationDto.applicationDate);
                self.employeeName(appDispInfoNoDateOutput.employeeInfoLst[0].bussinessName);
                self.employeeID(appDispInfoNoDateOutput.employeeInfoLst[0].sid);
                self.inputDate(applicationDto.inputDate);
                self.siftCD(appHolidayWork.workTime.siftCode);
                let workTimeInfo = _.find(appDispInfoWithDateOutput.workTimeLst, o => o.worktimeCode == self.siftCD());
                if(workTimeInfo) {
                    self.siftName(workTimeInfo.workTimeDisplayName.workTimeName);           
                }
                self.workTypeCd(appHolidayWork.workType.workTypeCode);
                let workTypeInfo = _.find(hdWorkDispInfoWithDateOutput.workTypeLst, o => o.workTypeCode == self.workTypeCd());
                if(workTypeInfo) {
                    self.workTypeName(workTypeInfo.name);        
                }
                
                self.workTypecodes(_.map(hdWorkDispInfoWithDateOutput.workTypeLst, o => o.workTypeCode));
                self.workTimecodes(_.map(appDispInfoWithDateOutput.workTimeLst, o => o.worktimeCode));
                
                self.timeStart1(appHolidayWork.workClockStart1);
                self.timeEnd1(appHolidayWork.workClockEnd1);
//                self.timeStart2(data.workClockStart2);
//                self.timeEnd2(data.workClockEnd2);
//                self.goSelected1(data.goAtr1);
//                self.goSelected2(data.goAtr2);
//                self.backSelected1(data.backAtr1);
//                self.backSelected2(data.backAtr2);
//                self.goSelected1Value(data.goAtr1 == 0 ? nts.uk.resource.getText("KAF009_17") : nts.uk.resource.getText("KAF009_16"));
//                self.backSelected1Value(data.backAtr1 == 0 ? nts.uk.resource.getText("KAF009_19") : nts.uk.resource.getText("KAF009_18"));
                if(appDispInfoNoDateOutput.appReasonLst != null && appDispInfoNoDateOutput.appReasonLst.length > 0){
                    let reasonID = appDispInfoNoDateOutput.appReasonLst[0].reasonID;
                    self.selectedReason(reasonID);
                    let lstReasonCombo = _.map(appDispInfoNoDateOutput.appReasonLst, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); });
                    self.reasonCombo(lstReasonCombo);
                }
                self.multilContent(applicationDto.applicationReason);
                if(data.appHdWorkDispInfoOutput.divergenceReasonDtos != null && data.appHdWorkDispInfoOutput.divergenceReasonDtos.length > 0){
                    self.reasonCombo2(_.map(data.appHdWorkDispInfoOutput.divergenceReasonDtos, o => { return new common.ComboReason(o.divergenceReasonID, o.reasonTemp); }));
                    let reasonID = data.appHdWorkDispInfoOutput.divergenceReasonDtos[0].divergenceReasonID;
                    self.selectedReason2(reasonID);
                }
                self.multilContent2(appHolidayWork.divergenceReasonContent);
                self.indicationOvertimeFlg(overtimeRestAppCommonSettingDto.extratimeDisplayAtr == 1 ? true : false);
                self.instructInforFlag(hdWorkDispInfoWithDateOutput.appHdWorkInstruction.displayHolidayWorkInstructInforFlg);
                self.instructInfor(hdWorkDispInfoWithDateOutput.appHdWorkInstruction.holidayWorkInstructInfomation);
                if(nts.uk.util.isNullOrUndefined(appHolidayWork.appOvertimeDetailDto)){
                    self.indicationOvertimeFlg(false);    
                } else {
                    common.Process.setOvertimeWorkDetail(appHolidayWork.appOvertimeDetailDto, self, hdWorkDispInfoWithDateOutput.agreementTimeStatusOfMonthly);    
                }
                
                let dataRestTime = _.filter(appHolidayWork.holidayWorkInputDtos, {'attendanceType': 0});
                let dataOverTime = _.filter(appHolidayWork.holidayWorkInputDtos, {'attendanceType': 1});
                let dataBreakTime = _.filter(appHolidayWork.holidayWorkInputDtos, {'attendanceType': 2});
                let dataBonusTime = _.filter(appHolidayWork.holidayWorkInputDtos, {'attendanceType': 3});
                self.datatest = dataOverTime;
                self.restTime.removeAll();
                self.overtimeHours.removeAll();
                self.breakTimes.removeAll();
                self.bonusTimes.removeAll();
                if(nts.uk.util.isNullOrEmpty(dataRestTime)){
                    for (let i = 0; i < 11; i++) {
                        self.restTime.push(new common.OverTimeInput("", "", 0, "", i,0, i.toString(), null, null, null,"", ""));
                    }    
                } else {
                    _.forEach(dataRestTime, (item) => { 
                            self.restTime.push(new common.OverTimeInput(
                                item.companyID, 
                                item.appID, 
                                item.attendanceType, 
                                "", 
                                item.frameNo, 
                                "", 
                                item.frameName, 
                                item.startTime, 
                                item.endTime, 
                                item.applicationTime, ""));
                    });
                };
                _.forEach(dataBreakTime, (item :any) => {
                    if(item.frameNo == 11){
                        if (data.appOvertimeNightFlg == 1) {
                            self.breakTimes.push(self.createOvertimeInputInit(data.caculationTimes, item, data.appHdWorkDispInfoOutput));
                        }
                    } else if (item.frameNo == 12) {
                        if (data.flexFLag) {
                            self.breakTimes.push(self.createOvertimeInputInit(data.caculationTimes, item, data.appHdWorkDispInfoOutput));
                        }
                    } else {
                        self.breakTimes.push(self.createOvertimeInputInit(data.caculationTimes, item, data.appHdWorkDispInfoOutput));
                    }
                }); 
                self.fillColor(data.preActualColorResult);
            }
            
            createOvertimeInputInit(calcLstInit, item, appHdWorkDispInfoOutput){
                let self = this;
                let calcValue = _.filter(calcLstInit, value => {
                    return item.attendanceType == value.attendanceID && item.frameNo == value.frameNo;     
                })[0];
                let frameName = _.find(appHdWorkDispInfoOutput.breaktimeFrames, o => o.workdayoffFrNo == item.frameNo).workdayoffFrName;
                if(item.frameNo==11){
                    frameName = nts.uk.resource.getText("KAF005_63");    
                }
                if(item.frameNo==12){
                    frameName = nts.uk.resource.getText("KAF005_65");     
                }
                let color = '';
                if(nts.uk.util.isNullOrUndefined(calcValue)) {
                    if(self.editable()&& self.enableOvertimeInput()){
                        color = 'none';
                    } else {
                        color = '#ebebe4';
                    }    
                    return new common.OvertimeCaculation(
                        item.companyID, 
                        item.appID, 
                        item.attendanceType, 
                        "", 
                        item.frameNo, 
                        item.timeItemTypeAtr, 
                        frameName, 
                        item.applicationTime, 
                        self.convertIntToTime(0), 
                        self.convertIntToTime(0),
                        "",
                        "", 
                        color);
                }
                if(nts.uk.util.isNullOrUndefined(calcValue.applicationTime)){
                    if(self.editable()&& self.enableOvertimeInput()){
                        color = 'none';
                    } else {
                        color = '#ebebe4';
                    }  
                } else {
                    color = self.getColorInit(item.attendanceID, item.frameNo, calcValue.errorCode, calcValue.preAppExceedState, calcValue.actualExceedState);            
                }
                return new common.OvertimeCaculation(
                        item.companyID, 
                        item.appID, 
                        item.attendanceType, 
                        "", 
                        item.frameNo, 
                        item.timeItemTypeAtr, 
                        frameName, 
                        calcValue.applicationTime, 
                        nts.uk.util.isNullOrUndefined(calcValue.preAppTime) ? null : self.convertIntToTime(parseInt(calcValue.preAppTime)), 
                        nts.uk.util.isNullOrUndefined(calcValue.caculationTime) ? null : self.convertIntToTime(parseInt(calcValue.caculationTime)),
                        "",
                        "", 
                        color);        
            }
            
            getColorInit(attendanceId, frameNo,errorCode, beforeAppStatus, actualStatus){
                let self = this;
                if((self.prePostSelected() == 1) && (self.performanceExcessAtr() == 2) &&(errorCode == 4||actualStatus==true)){
                    return '#FD4D4D';
                }
                if((self.prePostSelected() == 1) && (self.performanceExcessAtr() == 1) &&(errorCode == 3||actualStatus==true)){
                    return '#F6F636';
                }
                if((self.prePostSelected() == 1) && (self.preExcessDisplaySetting()==1) &&(errorCode == 2||beforeAppStatus==true)){
                    return '#FFC0CB';
                }
                if(errorCode == 1){
                    return '#F69164';
                }
                if(self.editable()&& self.enableOvertimeInput()){
                    return 'none';    
                } else {
                    return '#ebebe4';
                }
            }

            checkRequiredBreakTimes() {
                let self = this;
                _.each(self.breakTimes(), function(item) {
                    item.applicationTime.subscribe(function(value) {
                        self.clearErrorB6_8();
                        if (!self.hasAppTimeBreakTimes()) {
                            self.setErrorB6_8();
                        }
                    })
                })
            }

            hasAppTimeBreakTimes() {
                let self = this,
                    hasData = false;
                _.each(self.breakTimes(), function(item: common.OvertimeCaculation) {
                    let timeValidator = new nts.uk.ui.validation.TimeValidator(nts.uk.resource.getText("KAF010_56"), "OvertimeAppPrimitiveTime", { required: false, valueType: "Clock", inputFormat: "hh:mm", outputFormat: "time", mode: "time" });
                    if (!util.isNullOrEmpty(item.applicationTime())) {
                        hasData = true;
                    }
                    if (item.applicationTime() == null) return;

                    let control = $('input#overtimeHoursCheck_' + item.attendanceID() + '_' + item.frameNo());
                    let check = timeValidator.validate(control.val());
                    if (!check.isValid) {
                        control.ntsError('set', { messageId: check.errorCode, message: check.errorMessage });
                    }
                })
                return hasData;
            }

            setErrorB6_8() {
                $('.breakTimesCheck').ntsError('set', { messageId: 'MsgB_1', messageParams: [nts.uk.resource.getText("KAF010_56")] });
            }

            clearErrorB6_8() {
                $('.breakTimesCheck').ntsError('clear');
            }

            update(): JQueryPromise<any> {                
                let self = this,
                appReason: string,
                divergenceReason: string;
                if(self.displayCaculationTime()){
                    if(!appcommon.CommonProcess.checkWorkTypeWorkTime(self.workTypeCd(), self.siftCD(), "kaf010-workType-workTime-div")){
                        return;    
                    }
                }
                if (self.displayCaculationTime()) {
                    $("#inpStartTime1").trigger("validate");
                    $("#inpEndTime1").trigger("validate");
                    if (!self.validate()) { return; }
                }
                if(self.enableOvertimeInput()){
                    if (!self.hasAppTimeBreakTimes()) {
                        self.setErrorB6_8();
                    }      
                }
                //return if has error
                if (nts.uk.ui.errors.hasError()){return;}   
                nts.uk.ui.block.invisible();
                
                let comboBoxReason: string = appcommon.CommonProcess.getComboBoxReason(self.selectedReason(), self.reasonCombo(), self.typicalReasonDisplayFlg());
                let textAreaReason: string = appcommon.CommonProcess.getTextAreaReason(self.multilContent(), self.displayAppReasonContentFlg(), true);
                
                if(!appcommon.CommonProcess.checklenghtReason(comboBoxReason+":"+textAreaReason,"#appReason")){
                    return;
                }
                divergenceReason = self.getReason(
                    self.displayDivergenceReasonForm(),
                    self.selectedReason2(),
                    self.reasonCombo2(),
                    self.displayDivergenceReasonInput(),
                    self.multilContent2()
                );
                if(!appcommon.CommonProcess.checklenghtReason(divergenceReason,"#divergenceReason")){
                    return;
                }
                let overTimeShiftNightTmp: number = 0;
                let flexExessTimeTmp: number = 0;
                for (let i = 0; i < self.overtimeHours().length; i++) {
                    if(self.overtimeHours()[i].frameNo() == 11){
                        overTimeShiftNightTmp = self.overtimeHours()[i].applicationTime;                    
                    }else if(self.overtimeHours()[i].frameNo() == 12){
                        flexExessTimeTmp = self.overtimeHours()[i].applicationTime;  
                    }
                }
                let command = {
                    version: self.version,
                    appID: self.appID(),
                    applicationDate: new Date(self.appDate()),
                    prePostAtr: self.prePostSelected(),
                    applicantSID: self.employeeID(),
                    applicationReason: textAreaReason,
                    workTypeCode: self.workTypeCd(),
                    siftTypeCode: self.siftCD(),
                    workClockStart1: self.timeStart1(),
                    workClockEnd1: self.timeEnd1(),
                    workClockStart2: self.timeStart2(),
                    workClockEnd2: self.timeEnd2(),
                    goAtr1: self.goSelected1(),
                    backAtr1: self.backSelected1(),
                    goAtr2: self.goSelected2(),
                    backAtr2: self.backSelected2(),
                    bonusTimes: ko.toJS(self.bonusTimes()),
                    overtimeHours: ko.toJS(self.overtimeHours()),
                    breakTimes: ko.toJS(self.breakTimes()),
                    restTime: ko.toJS(self.restTime()),
                    holidayWorkShiftNight: ko.toJS(overTimeShiftNightTmp == null ? null : overTimeShiftNightTmp),
                    divergenceReasonContent: divergenceReason,
                    sendMail: self.manualSendMailAtr(),
                    calculateFlag: self.calculateFlag(),
                    appReasonID: comboBoxReason,
                    appHdWorkDispInfoCmd: self.appHdWorkDispInfoDto
                }
                
                service.checkBeforeUpdate(command).done((data) => {
                    self.processConfirmMsg(command, data, 0);
                }).fail((res) => {
                    if (nts.uk.util.isNullOrEmpty(res.errors)) {
                        dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                        .then(function() { nts.uk.ui.block.clear(); });     
                    } else {
                        let errors = res.errors;
                        for (let i = 0; i < errors.length; i++) {
                            let error = errors[i];
                            if (error.messageId == "Msg_1538") {
                                error.parameterIds = [
                                    nts.uk.time.formatYearMonth(parseInt(error.parameterIds[4])),
                                    nts.uk.time.formatYearMonth(parseInt(error.parameterIds[5])),
                                    nts.uk.time.format.byId("Clock_Short_HM", parseInt(error.parameterIds[6])),
                                    nts.uk.time.format.byId("Clock_Short_HM", parseInt(error.parameterIds[7]))
                                ];
                            } else {
                                error.parameterIds = [
                                    nts.uk.time.format.byId("Clock_Short_HM", parseInt(error.parameterIds[4])),
                                    nts.uk.time.format.byId("Clock_Short_HM", parseInt(error.parameterIds[5]))
                                ];
                            }
                            error.message = nts.uk.resource.getMessage(error.messageId, error.parameterIds);
                        }
                        nts.uk.ui.dialog.bundledErrors({ errors: errors })
                            .then(function() { nts.uk.ui.block.clear(); });
                    } 
                });
                // self.beforeUpdateColorConfirm(command);
            }
            
            getBoxReason(){
                var self = this;
                return appcommon.CommonProcess.getComboBoxReason(self.selectedReason(), self.reasonCombo(), self.typicalReasonDisplayFlg());
            
            }
        
            getAreaReason(){
                var self = this;
                return appcommon.CommonProcess.getTextAreaReason(self.multilContent(), self.displayAppReasonContentFlg(), true);   
            }
            
            resfreshReason(appReason: string){
                var self = this;
                self.selectedReason('');  
                self.multilContent(appReason);  
            }
            
            updateOvertime(command: any){
                let self = this;
                service.updateOvertime(command)
                .done((data) => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        if(data.autoSendMail){
                            appcommon.CommonProcess.displayMailResult(data); 
                        } else {
                            self.reBinding(self.listAppMeta, self.appCur, false);
                        }
                    });    
                })
                .fail(function(res) { 
                    if(res.optimisticLock == true){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_197" }).then(function(){
                            self.reBinding(self.listAppMeta, self.appCur, false);
                        });    
                    } else {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function(){nts.uk.ui.block.clear();}); 
                    }
                });           
            }
            
            convertOvertimeCaculationToOverTimeInput(param: common.OverTimeInput): common.OverTimeInput{
                return new common.OverTimeInput(
                    param.companyID(),
                    param.appID(),
                    param.attendanceID(),
                    param.attendanceName(),
                    param.frameNo(),
                    "",
                    param.frameName(),
                    null,
                    null,
                    param.applicationTime(),
                    param.nameID(), ""    
                );        
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
                    $("#kaf010-workType-workTime-div").ntsError('clear');
                    $("#kaf010-workType-workTime-div").css("border","none");
                    //view all code of selected item 
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        $("#inpStartTime1").ntsError("clear"); 
                        $("#inpEndTime1").ntsError("clear");
                        self.workTypeCd(childData.selectedWorkTypeCode);
                        self.workTypeName(childData.selectedWorkTypeName);
                        self.siftCD(childData.selectedWorkTimeCode);
                        self.siftName(childData.selectedWorkTimeName);
                        self.timeStart1(childData.first.start);
                        self.timeEnd1(childData.first.end);
                        self.timeStart2(childData.second.start);
                        self.timeEnd2(childData.second.end);

                        service.getRecordWork(
                        	{
                        		employeeID: self.employeeID(), 
                        		appDate: nts.uk.util.isNullOrEmpty(self.appDate()) ? null : moment(self.appDate()).format(self.DATE_FORMAT),
                        		siftCD: self.siftCD(),
                        		prePostAtr: self.prePostSelected(),
                        		overtimeHours: ko.toJS(self.breakTimes()),
                        		workTypeCD: self.workTypeCd(), 
                        		appID: self.appID(),
                                appHdWorkDispInfoCmd: self.appHdWorkDispInfoDto
                        	}
                        ).done(data => {
                        	self.timeStart1(data.startTime1 == null ? null : data.startTime1);
                        	self.timeEnd1(data.endTime1 == null ? null : data.endTime1);
                        	self.timeStart2(data.startTime2 == null ? null : data.startTime2);
                        	self.timeEnd2(data.endTime2 == null ? null : data.endTime2);
                        	self.convertActualList(data);
                        });
                    }
                })
            }
            /**
             * Jump to CMM018 Screen
             */
            setTimeZones(timeZones) {
                let self = this;
                if (timeZones) {
                    let times = [];
                    for (let i = 1; i < 11; i++) {
                        times.push(new common.OverTimeInput("", "", 0, "", i, 0, i, self.getStartTime(timeZones[i - 1]), self.getEndTime(timeZones[i - 1]), null, ""));
                    }
                    self.restTime(times);
                }
            }

            getStartTime(data) {
                return data ? data.start : null;
            }

            getEndTime(data) {
                return data ? data.end : null;
            }
            
            openCMM018() {
                let self = this;
                nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", { screen: 'Application', employeeId: self.employeeID() });
            }
            validate(): boolean{
                let self = this;            
                //勤務時間
                if(!self.validateTime(self.timeStart1(), self.timeEnd1(), '#inpStartTime1')){
                    return false;
                };
                if ( !nts.uk.util.isNullOrEmpty(self.timeStart2()) && self.timeStart2() != "") {
                    if ( !self.validateTime( self.timeStart2(), self.timeEnd2(), '#inpStartTime2' ) ) {
                        return false;
                    };
                }
                //休憩時間
                if(!nts.uk.util.isNullOrEmpty(self.restTime())) {
                    return true;    
                }
                
                for (let i = 0; i < self.restTime().length; i++) {
                    let startTime = self.restTime()[i].startTime();
                    let endTime = self.restTime()[i].endTime();
                    let attendanceId = self.restTime()[i].attendanceID();
                    let frameNo = self.restTime()[i].frameNo();
                    if (i != 9) {
                        let startTimeAdd = self.restTime()[i + 1].startTime();
                        let endTimeAdd = self.restTime()[i + 1].endTime();
                        let attendanceIdAdd = self.restTime()[i + 1].attendanceID();
                        let frameNoAdd = self.restTime()[i + 1].frameNo();
                    }
                    if (nts.uk.util.isNullOrEmpty(startTime) && !nts.uk.util.isNullOrEmpty(endTime)) {
                        dialog.alertError({ messageId: "Msg_307" })
                        $('input#restTimeStart_' + attendanceId + '_' + frameNo).focus();
                        return false;
                    }
                    if (!nts.uk.util.isNullOrEmpty(startTime) && startTime != "") {
                        if (!self.validateTime(startTime, endTime, 'input#restTimeEnd_' + attendanceId + '_' + frameNo)) {
                            return false;
                        };
                    }
                    if (!nts.uk.util.isNullOrEmpty(startTimeAdd)) {
                        if (endTime == null) {
                                dialog.alertError({ messageId: "Msg_307" })
                                $('input#restTimeEnd_' + attendanceId + '_' + frameNo).focus();
                                return false;
                        }
                        if (!self.validateTime(endTime, startTimeAdd, 'input#restTimeStart_' + attendanceIdAdd + '_' + frameNoAdd)) {
                                return false;
                        };
                    }
                }
                return true;            
            }
            //Validate input time
            validateTime(startTime: number, endTime: number, elementId: string): boolean{            
                if(startTime >= endTime){
                    dialog.alertError({messageId:"Msg_307"})
                     $(elementId).focus();
                    return false;
                }
                return true;
            }
            
            
            CaculationTime(){
                let self = this;
                let dfd = $.Deferred();
                if(nts.uk.util.isNullOrEmpty(self.appDate())){
                    dialog.alertError({messageId : "Msg_959"});
                    return;    
                }
                $(".breakTimesCheck").ntsError('clear');
                $("#inpStartTime1").trigger("validate");
                $("#inpEndTime1").trigger("validate");
                //return if has error
                if (nts.uk.ui.errors.hasError()){return;}
                if(!self.validateTime(self.timeStart1(), self.timeEnd1(), '#inpStartTime1')){
                    return;    
                }
                if ( !nts.uk.util.isNullOrEmpty(self.timeStart2())) {
                    if ( !self.validateTime( self.timeStart2(), self.timeEnd2(), '#inpStartTime2' ) ) {
                        return;
                    };
                }  
                let breakTimeInputLst = [];
                _.forEach(ko.toJS(self.breakTimes()), (item) => {
                    breakTimeInputLst.push(self.initCalculateData(item));        
                });
                let param1 = {
                    employeeID: self.employeeID(),
                    appDate: moment(self.appDate()).format("YYYY/MM/DD"),
                    prePostAtr: self.prePostSelected(),
                    workTypeCD: self.workTypeCd(),
                    workTimeCD: self.siftCD(),
                    overtimeInputLst: breakTimeInputLst,
                    startTime: nts.uk.util.isNullOrEmpty(self.timeStart1()) ? null : self.timeStart1(),
                    endTime: nts.uk.util.isNullOrEmpty(self.timeEnd1()) ? null : self.timeEnd1(),
                    startTimeRests: nts.uk.util.isNullOrEmpty(self.restTime()) ? [] : _.map(self.restTime(),x=>{return x.startTime()}),
                    endTimeRests: nts.uk.util.isNullOrEmpty(self.restTime()) ? [] : _.map(self.restTime(),x=>{return x.endTime()})     
                }
                //setting work content
                self.preWorkContent = {
                    applicationDate: self.appDate(),
                    workType: self.workTypeCd(),
                    siftType: self.siftCD(),
                    workClockFrom1: self.timeStart1(),
                    workClockTo1: self.timeEnd1(),
                    workClockFrom2: self.timeStart2(),
                    workClockTo2: self.timeEnd2(),
                    breakTimes:  ko.toJS(self.breakTimes()),
                    restTime:  ko.toJS(self.restTime()),
                }
                //block screen
                nts.uk.ui.block.invisible();
                //計算をクリック
                service.getCalculateValue(param1).done((data: any) => {
                    self.breakTimesOld = ko.toJS(self.breakTimes());
                    self.fillColor(data);
                    nts.uk.ui.block.clear();
                    if(!self.isEmptyOverTimeInput(ko.toJS(self.breakTimes()))){
                        self.calculateFlag(0);
                    }
                    self.checkWorkContentChanged();
                });
            }
            
            fillColor(calcData){
                let self = this;
                let beforeAppStatus = nts.uk.util.isNullOrUndefined(calcData) ? null : calcData.beforeAppStatus;
                let actualStatus = nts.uk.util.isNullOrUndefined(calcData) ? null : calcData.actualStatus;
                let resultLst = nts.uk.util.isNullOrUndefined(calcData) ? [] : calcData.resultLst;
                _.forEach(self.breakTimes(), breakTime => {
                    let calcOT = _.find(resultLst, item => {
                        return item.attendanceID == 2 &&
                            item.frameNo == breakTime.frameNo();    
                    });          
                    if(!nts.uk.util.isNullOrUndefined(calcOT)){
                        breakTime.applicationTime(calcOT.appTime);
                        breakTime.preAppTime(nts.uk.util.isNullOrUndefined(calcOT.preAppTime) ? null : nts.uk.time.format.byId("Clock_Short_HM", calcOT.preAppTime));
                        breakTime.caculationTime(nts.uk.util.isNullOrUndefined(calcOT.actualTime) ? null : nts.uk.time.format.byId("Clock_Short_HM", calcOT.actualTime));
                        if(nts.uk.util.isNullOrUndefined(breakTime.applicationTime())){
                            if(self.editable()&& self.enableOvertimeInput()){
                                $('td#overtimeHoursCheck_'+breakTime.attendanceID()+'_'+breakTime.frameNo()).css('background', 'none');
                                $('input#overtimeHoursCheck_'+breakTime.attendanceID()+'_'+breakTime.frameNo()).css('background', 'none');
                                breakTime.color('none');
                                return; 
                            } else {
                                $('td#overtimeHoursCheck_'+breakTime.attendanceID()+'_'+breakTime.frameNo()).css('background', '#ebebe4');
                                $('input#overtimeHoursCheck_'+breakTime.attendanceID()+'_'+breakTime.frameNo()).css('background', '#ebebe4');
                                breakTime.color('#ebebe4');
                                return; 
                            }  
                        }
                        let oldValue = _.find(self.breakTimesOld, item => {
                            return item.attendanceID == 2 &&
                                item.frameNo == breakTime.frameNo();    
                        });  
                        let calcChange = false;
                        if((nts.uk.util.isNullOrUndefined(oldValue)) || 
                            (nts.uk.util.isNullOrUndefined(oldValue.applicationTime)) || 
                            (ko.toJSON(oldValue).localeCompare(ko.toJSON(breakTime))!=0)){
                            calcChange = true;
                        }
                        let newColor = self.changeColor(2, breakTime.frameNo(), self.getErrorCode(calcOT.calcError, calcOT.preAppError, calcOT.actualError), beforeAppStatus, actualStatus, calcChange);
                        if(!nts.uk.util.isNullOrUndefined(newColor)){
                            breakTime.color(newColor);
                        }
                    } else {
                        if(nts.uk.util.isNullOrUndefined(breakTime.applicationTime())){
                            if(self.editable()&& self.enableOvertimeInput()){
                                $('td#overtimeHoursCheck_'+breakTime.attendanceID()+'_'+breakTime.frameNo()).css('background', 'none');
                                $('input#overtimeHoursCheck_'+breakTime.attendanceID()+'_'+breakTime.frameNo()).css('background', 'none');
                                breakTime.color('none');
                                return; 
                            } else {
                                $('td#overtimeHoursCheck_'+breakTime.attendanceID()+'_'+breakTime.frameNo()).css('background', '#ebebe4');
                                $('input#overtimeHoursCheck_'+breakTime.attendanceID()+'_'+breakTime.frameNo()).css('background', '#ebebe4');
                                breakTime.color('#ebebe4');
                                return; 
                            }  
                        }
                        let newColor = self.changeColor(1, breakTime.frameNo(), self.getErrorCode(0, 0, 0), beforeAppStatus, actualStatus, false);
                        if(!nts.uk.util.isNullOrUndefined(newColor)){
                            breakTime.color(newColor);
                        }        
                    }
                });   
            }
            
            convertIntToTime(data : number) : string{
                let hourMinute : string = "";
                if(nts.uk.util.isNullOrEmpty(data)){
                    return null;
                }else if (data == 0) {
                    hourMinute = "0:00";
                }else if(data != null){
                    let hour = Math.floor(Math.abs(data)/60);
                    let minutes = Math.floor(Math.abs(data)%60);
                    hourMinute =  hour + ":"+ (minutes < 10 ? ("0" + minutes) : minutes);
                }
                return hourMinute;
            }
            
            convertActualList(data :any){
            	let self = this;
            	if (data.actualStatusCheckResult.actualLst != null) {
            		let actualLst = data.actualStatusCheckResult.actualLst;
                    for (let i = 0; i < actualLst.length; i++) {
                        if (actualLst[i].attendanceID == 2) {
                            if(!_.isUndefined(self.breakTimes()[i])) {
                                self.breakTimes()[i].caculationTime(nts.uk.time.format.byId("Clock_Short_HM", actualLst[i].actualTime));    
                            }
                        }
                    }
                }
            }
            
            getErrorCode(calcError, preAppError, actualError){
                if(actualError > preAppError) {
                    if(actualError > calcError) {
                        return actualError;
                    } else {
                        return calcError;
                    }
                } else {
                    if(preAppError > calcError) {
                        return preAppError;
                    } else {
                        return calcError;
                    }
                }
            }
            
            changeColor(attendanceId, frameNo,errorCode, beforeAppStatus, actualStatus, calcChange){
                let self = this;
                if((self.prePostSelected() == 1) && (self.performanceExcessAtr() == 2) &&(errorCode == 4||actualStatus==3)){
                    $('td#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#FD4D4D');
                    $('input#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#FD4D4D');
                    return '#FD4D4D';
                }
                if((self.prePostSelected() == 1) && (self.performanceExcessAtr() == 1) &&(errorCode == 3||actualStatus==3)){
                    $('td#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#F6F636');
                    $('input#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#F6F636');
                    return '#F6F636';
                }
                if((self.prePostSelected() == 1) && (self.preExcessDisplaySetting()==1) &&(errorCode == 2||beforeAppStatus==true)){
                    $('td#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#FFC0CB');
                    $('input#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#FFC0CB');
                    return '#FFC0CB';
                }
                if(errorCode == 1 && calcChange){
                    $('td#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#F69164');
                    $('input#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#F69164');
                    return '#F69164';
                }
                if(self.editable()&& self.enableOvertimeInput()){
                    if(calcChange){
                        $('td#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', 'none');
                        $('input#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', 'none');
                        return 'none'; 
                    }
                } else {
                    if(calcChange){
                        $('td#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#ebebe4');
                        $('input#overtimeHoursCheck_'+attendanceId+'_'+frameNo).css('background', '#ebebe4');
                        return '#ebebe4';
                    }
                }
            }
            
            getReason(inputReasonDisp: boolean, inputReasonID: string, inputReasonList: Array<common.ComboReason>, detailReasonDisp: boolean, detailReason: string): string{
                let appReason = '';
                let inputReason: string = '';
                if(!nts.uk.util.isNullOrEmpty(inputReasonID)){
                    inputReason = _.find(inputReasonList, o => { return o.reasonId == inputReasonID; }).reasonName;    
                }    
                if(inputReasonDisp==true&&detailReasonDisp==true){
                    if(!nts.uk.util.isNullOrEmpty(inputReason)&&!nts.uk.util.isNullOrEmpty(detailReason)){
                        appReason = inputReason + ":" + detailReason;
                    } else if(!nts.uk.util.isNullOrEmpty(inputReason)&&nts.uk.util.isNullOrEmpty(detailReason)){
                        appReason = inputReason; 
                    } else if(nts.uk.util.isNullOrEmpty(inputReason)&&!nts.uk.util.isNullOrEmpty(detailReason)){
                        appReason = detailReason;             
                    }                
                } else if(inputReasonDisp==true&&detailReasonDisp==false){
                    appReason = inputReason;                 
                } else if(inputReasonDisp==false&&detailReasonDisp==true){
                    appReason = detailReason;     
                } 
                return appReason;
            }
            private isEmptyOverTimeInput(OverTimeInputs: Array<any>): boolean {
                return _.isEmpty(_.filter(OverTimeInputs, x => !nts.uk.util.isNullOrEmpty(x.applicationTime)));
            }
            private initCalculateData(item: any): any{
                return data ={companyID: item.companyID,
                                appID: item.appID,
                                attendanceID: item.attendanceID,
                                attendanceName: item.attendanceName,
                                frameNo: item.frameNo,
                                timeItemTypeAtr: item.timeItemTypeAtr,
                                frameName: item.frameName,
                                applicationTime: item.applicationTime,
                                preAppTime: null,
                                caculationTime: null,
                                nameID: item.nameID, 
                                itemName:item.itemName};
            }
            private checkWorkContentChanged(){
                let self = this;
                //Check calculate times
                //勤務種類         
                self.workTypeCd.subscribe(value => {
                    if (!nts.uk.util.isNullOrEmpty(self.preWorkContent)) {
                        if (self.preWorkContent.workType != value) {
                            //→エラーＭＳＧ
                            self.calculateFlag(1);
                        }
                    }
                });
                //就業時間帯      
                self.siftCD.subscribe(value => {
                    if (!nts.uk.util.isNullOrEmpty(self.preWorkContent)) {
                        if (self.preWorkContent.siftType != value) {
                            //→エラーＭＳＧ
                            self.calculateFlag(1);
                        }
                    }
                });
                //勤務時間
                self.timeStart1.subscribe(value => {
                    if (!nts.uk.util.isNullOrEmpty(self.preWorkContent)) {
                        if (self.preWorkContent.workClockFrom1 != value) {
                            //→エラーＭＳＧ
                            self.calculateFlag(1);
                        }
                    }
                });
                self.timeEnd1.subscribe(value => {
                    if (!nts.uk.util.isNullOrEmpty(self.preWorkContent)) {
                        if (self.preWorkContent.workClockTo1 != value) {
                            //→エラーＭＳＧ
                            self.calculateFlag(1);
                        }
                    }
                });
                self.timeStart2.subscribe(value => {
                    if (!nts.uk.util.isNullOrEmpty(self.preWorkContent)) {
                        if (self.preWorkContent.workClockFrom2 != value) {
                            //→エラーＭＳＧ
                            self.calculateFlag(1);
                        }
                    }
                });
                self.timeEnd2.subscribe(value => {
                    if (!nts.uk.util.isNullOrEmpty(self.preWorkContent)) {
                        if (self.preWorkContent.workClockTo2 != value) {
                            //→エラーＭＳＧ
                            self.calculateFlag(1);
                        }
                    }
                });
                //休憩時間
                for (let i = 0; i < self.restTime().length; i++) {
                    self.restTime()[i].startTime.subscribe(value => {
                        if (!nts.uk.util.isNullOrEmpty(self.preWorkContent)) {
                            if (self.preWorkContent.restTime[i].startTime != value) {
                                //→エラーＭＳＧ
                                self.calculateFlag(1);
                            }
                        }
                    });
                    self.restTime()[i].endTime.subscribe(value => {
                        if (!nts.uk.util.isNullOrEmpty(self.preWorkContent)) {
                            if (self.preWorkContent.restTime[i].endTime != value) {
                                //→エラーＭＳＧ
                                self.calculateFlag(1);
                            }
                        }
                    });
                }
            }
            
            beforeUpdateColorConfirm(overtime: any){
                let self = this;
                service.beforeRegisterColorConfirm(overtime).done((data2) => { 
                    if(nts.uk.util.isNullOrUndefined(data2.preActualColorResult)){
                        self.beforeUpdateProcess(overtime);    
                    } else {
                        self.breakTimesOld = ko.toJS(self.breakTimes());
                        self.fillColor(data2.preActualColorResult);
                        self.checkPreApp(overtime, data2);        
                    }
                }).fail((res) => {
                    dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                    .then(function() { nts.uk.ui.block.clear(); });           
                });         
            }
            
//            checkPreApp(overtime, data) {
//                let self = this;
//                let beforeAppStatus = data.preActualColorResult.beforeAppStatus;
//                let actualStatus = data.preActualColorResult.actualStatus;
//                let resultLst = data.preActualColorResult.resultLst;
//                if(self.preExcessDisplaySetting()==0){
//                    self.checkActual(overtime, data);
//                    return;    
//                }
//                if(beforeAppStatus){
//                    dialog.confirm({ messageId: "Msg_1508" }).ifYes(() => {
//                        self.checkActual(overtime, data);   
//                    }).ifNo(() => {
//                        nts.uk.ui.block.clear();
//                    });       
//                    return; 
//                }
//                let preAppErrorFrames = resultLst.filter(f => 
//                    f.attendanceID == 2 && 
//                    self.getErrorCode(f.calcError, f.preAppError, f.actualError, beforeAppStatus, actualStatus) == 2);
//                if(nts.uk.util.isNullOrEmpty(preAppErrorFrames)){
//                    self.checkActual(overtime, data);         
//                } else {
//                    let framesError = '';
//                    preAppErrorFrames.forEach((v, k)=>{
//                        let currentFrame = _.find(self.breakTimes(), ot => ot.frameNo()==v.frameNo);
//                        if(!nts.uk.util.isNullOrUndefined(currentFrame)){
//                            framesError+=currentFrame.frameName();
//                            if(k<(preAppErrorFrames.length-1)){
//                                framesError+=",";    
//                            }    
//                        }    
//                    }); 
//                    dialog.confirm({ messageId: "Msg_424", messageParams: [ self.employeeName(), framesError ] }).ifYes(() => {
//                        self.checkActual(overtime, data);
//                    }).ifNo(() => {
//                        nts.uk.ui.block.clear();
//                    }); 
//                }
//            }
        
//            checkActual(overtime, data) {
//                let self = this;
//                let beforeAppStatus = data.preActualColorResult.beforeAppStatus;
//                let actualStatus = data.preActualColorResult.actualStatus;
//                let resultLst = data.preActualColorResult.resultLst;
//                if(self.performanceExcessAtr()==0){
//                    self.beforeUpdateProcess(overtime);
//                    return;        
//                } 
//                if(actualStatus==3){
//                    if(self.performanceExcessAtr() == 2){
//                        dialog.alertError({ messageId: "Msg_1565", messageParams: [ self.employeeName(), moment(self.appDate()).format(self.DATE_FORMAT), "登録できません。" ] })
//                        .then(function() { 
//                            nts.uk.ui.block.clear();
//                        });    
//                        return;
//                    } else {
//                        dialog.confirm({ messageId: "Msg_1565", messageParams: [ self.employeeName(), moment(self.appDate()).format(self.DATE_FORMAT), "登録してもよろしいですか？" ] }).ifYes(() => {
//                            self.beforeUpdateProcess(overtime);  
//                        }).ifNo(() => {
//                            nts.uk.ui.block.clear();
//                        }); 
//                        return;        
//                    }   
//                }
//                let actualErrorFrames = resultLst.filter(f => 
//                    f.attendanceID == 2 && 
//                    self.getErrorCode(f.calcError, f.preAppError, f.actualError, beforeAppStatus, actualStatus) == 4);
//                let actualAlarmFrames = resultLst.filter(f => 
//                    f.attendanceID == 2 && 
//                    self.getErrorCode(f.calcError, f.preAppError, f.actualError, beforeAppStatus, actualStatus) == 3);
//                if(!nts.uk.util.isNullOrEmpty(actualErrorFrames)){
//                    let framesError = '';
//                    actualErrorFrames.forEach((v, k)=>{
//                        let currentError = _.find(self.breakTimes(), ot => ot.frameNo()==v.frameNo);
//                        if(!nts.uk.util.isNullOrUndefined(currentError)){
//                            framesError+=currentError.frameName();
//                            if(k<(actualErrorFrames.length-1)){
//                                framesError+=",";    
//                            }    
//                        }    
//                    });  
//                    dialog.alertError({ messageId: "Msg_423", messageParams: [ self.employeeName(), framesError, "登録できません。" ] })
//                    .then(function() { 
//                        nts.uk.ui.block.clear();
//                    }); 
//                    return;
//                } else if(!nts.uk.util.isNullOrEmpty(actualAlarmFrames)){
//                    let framesAlarm = '';
//                    actualAlarmFrames.forEach((v, k)=>{
//                        let currentAlarm = _.find(self.breakTimes(), ot => ot.frameNo()==v.frameNo);
//                        if(!nts.uk.util.isNullOrUndefined(currentAlarm)){
//                            framesAlarm+=currentAlarm.frameName();
//                            if(k<(actualAlarmFrames.length-1)){
//                                framesAlarm+=",";    
//                            }    
//                        }    
//                    }); 
//                    dialog.confirm({ messageId: "Msg_423", messageParams: [ self.employeeName(), framesAlarm, "登録してもよろしいですか？" ] }).ifYes(() => {
//                        self.beforeUpdateProcess(overtime);
//                    }).ifNo(() => {
//                        nts.uk.ui.block.clear();
//                    }); 
//                    return;
//                } else {
//                    self.beforeUpdateProcess(overtime);    
//                }
//            }
            
//            beforeUpdateProcess(overtime: any){
//                let self = this;
//                service.checkBeforeUpdate(overtime).done((data) => {    
//                    overtime.appOvertimeDetail = data.appOvertimeDetail;
//                    self.updateOvertime(overtime);
//                }).fail((res) => {
//                    if(nts.uk.util.isNullOrEmpty(res.errors)){
//                        dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
//                        .then(function() { nts.uk.ui.block.clear(); });       
//                    } else {
//                        let errors = res.errors;
//                        for(let i = 0; i < errors.length; i++){
//                            let error = errors[i];
//                            if(error.messageId=="Msg_1538"){
//                                error.parameterIds = [
//                                    nts.uk.time.formatYearMonth(parseInt(error.parameterIds[4])), 
//                                    nts.uk.time.formatYearMonth(parseInt(error.parameterIds[5])),
//                                    nts.uk.time.format.byId("Clock_Short_HM", parseInt(error.parameterIds[6])), 
//                                    nts.uk.time.format.byId("Clock_Short_HM", parseInt(error.parameterIds[7]))
//                                ];     
//                            } else {
//                                error.parameterIds = [
//                                    nts.uk.time.format.byId("Clock_Short_HM", parseInt(error.parameterIds[4])), 
//                                    nts.uk.time.format.byId("Clock_Short_HM", parseInt(error.parameterIds[5]))
//                                ];     
//                            }
//                            error.message = nts.uk.resource.getMessage(error.messageId, error.parameterIds);
//                        }
//                        nts.uk.ui.dialog.bundledErrors({ errors: errors })    
//                        .then(function() { nts.uk.ui.block.clear(); });          
//                    }           
//                });        
//            }
            
            processConfirmMsg(paramInsert: any, result: any, confirmIndex: number) {
                let self = this;
                let confirmMsgLst = result.confirmMsgLst;
                let confirmMsg = confirmMsgLst[confirmIndex];
                if(_.isUndefined(confirmMsg)) {
                    paramInsert.appOvertimeDetail = result.appOvertimeDetailOtp;
                    self.updateOvertime(paramInsert);
                    return;
                }
                
                dialog.confirm({ messageId: confirmMsg.msgID, messageParams: confirmMsg.paramLst }).ifYes(() => {
                    self.processConfirmMsg(paramInsert, result, confirmIndex + 1);
                }).ifNo(() => {
                    nts.uk.ui.block.clear();
                });
            }
        }
    }
}