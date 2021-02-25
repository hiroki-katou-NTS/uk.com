module nts.uk.at.view.kaf006.b{
    import common = nts.uk.at.view.kaf006.share.common;
    import service = nts.uk.at.view.kaf006.shr.service;
    import dialog = nts.uk.ui.dialog;
    import appcommon = nts.uk.at.view.kaf000.shr.model;
    import model = nts.uk.at.view.kaf000.b.viewmodel.model;
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {
        DATE_FORMAT: string = "YYYY/MM/DD";
        //kaf000
        kaf000_a: kaf000.a.viewmodel.ScreenModel;
        manualSendMailAtr: KnockoutObservable<boolean> = ko.observable(true);
        screenModeNew: KnockoutObservable<boolean> = ko.observable(false);
        displayEndDateFlg : KnockoutObservable<boolean> = ko.observable(false);
        enableDisplayEndDate: KnockoutObservable<boolean> = ko.observable(false);
        //current Data
//        curentGoBackDirect: KnockoutObservable<common.GoBackDirectData>;
        //申請者
        employeeName: KnockoutObservable<string> = ko.observable("");
        employeeList :KnockoutObservableArray<common.EmployeeOT> = ko.observableArray([]);
        selectedEmplCodes: KnockoutObservable<string> = ko.observable(null);
        employeeFlag: KnockoutObservable<boolean> = ko.observable(false);
            totalEmployee: KnockoutObservable<string> = ko.observable(null);
        //Pre-POST
        prePostSelected: KnockoutObservable<number> = ko.observable(3);
        workState: KnockoutObservable<boolean> = ko.observable(true);
        typeSiftVisible: KnockoutObservable<boolean> = ko.observable(true);
        // 申請日付
        startAppDate: KnockoutObservable<string> = ko.observable('');
         // 申請日付
        endAppDate: KnockoutObservable<string> = ko.observable('');
        dateValue: KnockoutObservable<any> = ko.observable({ startDate: '', endDate: '' });
        appDate: KnockoutObservable<string> = ko.observable(moment().format(this.DATE_FORMAT));
        selectedAllDayHalfDayValue: KnockoutObservable<number> = ko.observable(0);
        holidayTypes: KnockoutObservableArray<common.HolidayType> = ko.observableArray([]);
        holidayTypeCode: KnockoutObservable<number> = ko.observable(0);
        typeOfDutys: KnockoutObservableArray<common.TypeOfDuty> = ko.observableArray([]);
        selectedTypeOfDuty:  KnockoutObservable<number> = ko.observable(null);
        displayHalfDayValue: KnockoutObservable<boolean> = ko.observable(false);
        changeWorkHourValue: KnockoutObservable<boolean> = ko.observable(false);
        changeWorkHourValueFlg: KnockoutObservable<boolean> = ko.observable(true);
//        displayChangeWorkHour:  KnockoutObservable<boolean> = ko.observable(false);
        displayStartFlg: KnockoutObservable<boolean> = ko.observable(true);
        contentFlg: KnockoutObservable<boolean> = ko.observable(true);
        eblTimeStart1:  KnockoutObservable<boolean> = ko.observable(false);
        eblTimeEnd1:  KnockoutObservable<boolean> = ko.observable(false);
        workTimeCodes: KnockoutObservableArray<string> = ko.observableArray([]);
        workTypecodes: KnockoutObservableArray<string> = ko.observableArray([]);
        displayWorkTimeName:  KnockoutObservable<string> = ko.observable(null);
        //TIME LINE 1
        timeStart1: KnockoutObservable<number> = ko.observable(null);
        timeEnd1: KnockoutObservable<number> = ko.observable(null);   
        //TIME LINE 2
        timeStart2: KnockoutObservable<number> = ko.observable(null);
        timeEnd2: KnockoutObservable<number> = ko.observable(null);
        //勤務種類
        workTimeCode: KnockoutObservable<string> = ko.observable('');
        workTimeName: KnockoutObservable<string> = ko.observable('');
        //comboBox 定型理由
        reasonCombo: KnockoutObservableArray<common.ComboReason> = ko.observableArray([]);
        selectedReason: KnockoutObservable<string> = ko.observable('');
        //MultilineEditor
        requiredReason : KnockoutObservable<boolean> = ko.observable(false);
        multilContent: KnockoutObservable<string> = ko.observable('');
  
        //Approval 
        approvalSource: Array<common.AppApprovalPhase> = [];
        employeeID : KnockoutObservable<string> = ko.observable('');
        //menu-bar 
        enableSendMail :KnockoutObservable<boolean> = ko.observable(true); 
        prePostDisp: KnockoutObservable<boolean> = ko.observable(true);
        prePostEnable: KnockoutObservable<boolean> = ko.observable(true);
        useMulti: KnockoutObservable<boolean> = ko.observable(true);
        
        displayPrePostFlg: KnockoutObservable<boolean> = ko.observable(true); 
        
        typicalReasonDisplayFlg: KnockoutObservable<boolean> = ko.observable(true);
        displayAppReasonContentFlg: KnockoutObservable<boolean> = ko.observable(true);
        // enable
        enbAllDayHalfDayFlg: KnockoutObservable<boolean> = ko.observable(true);
        enbWorkType: KnockoutObservable<boolean> = ko.observable(true);
        enbHalfDayFlg: KnockoutObservable<boolean> = ko.observable(true);
        enbChangeWorkHourFlg: KnockoutObservable<boolean> = ko.observable(true);
        enbbtnWorkTime: KnockoutObservable<boolean> = ko.observable(true);
        enbReasonCombo: KnockoutObservable<boolean> = ko.observable(true);
        enbContentReason:  KnockoutObservable<boolean> = ko.observable(true);
        version: number = 0;
        //ver15
        selectedRelation: KnockoutObservable<any> = ko.observable('');
        relationCombo: KnockoutObservableArray<any> = ko.observableArray([]);
        relaReason: KnockoutObservable<any> = ko.observable('');
        mournerDis: KnockoutObservable<boolean> = ko.observable(false);
        isCheck: KnockoutObservable<boolean> = ko.observable(false);
        fix: KnockoutObservable<boolean> = ko.observable(false);
        maxDayDis: KnockoutObservable<boolean> = ko.observable(false);
        maxDayline1: KnockoutObservable<string> = ko.observable('');
        maxDayline2: KnockoutObservable<string> = ko.observable('');
        requiredRela: KnockoutObservable<boolean> = ko.observable(true);
        //上限日数
        maxDay: KnockoutObservable<number> = ko.observable(0);
        //喪主加算日数
        dayOfRela: KnockoutObservable<number> = ko.observable(0);
        relaEnable: KnockoutObservable<boolean> = ko.observable(true);
        relaMourner: KnockoutObservable<boolean> = ko.observable(true);
        relaRelaReason: KnockoutObservable<boolean> = ko.observable(true);
        //No.376
        yearRemain: KnockoutObservable<string> = ko.observable('0日');//年休残数
        subHdRemain: KnockoutObservable<string> = ko.observable('0日');//代休残数
        subVacaRemain: KnockoutObservable<string> = ko.observable('0日');//振休残数
        stockRemain: KnockoutObservable<string> = ko.observable('0日');//ストック休暇残数
        numberRemain: KnockoutObservableArray<any> = ko.observableArray([]);
        yearDis: KnockoutObservable<boolean> = ko.observable(false);
        subHdDis: KnockoutObservable<boolean> = ko.observable(false);
        subVacaDis: KnockoutObservable<boolean> = ko.observable(false);
        stockDis: KnockoutObservable<boolean> = ko.observable(false);
        //ver20
        disAll: KnockoutObservable<boolean> = ko.observable(false);
        displayTypicalReason: KnockoutObservable<boolean> = ko.observable(false);
        enableTypicalReason: KnockoutObservable<boolean> = ko.observable(false);
        displayReason: KnockoutObservable<boolean> = ko.observable(false);
        enableReason: KnockoutObservable<boolean> = ko.observable(false);
        displayReasonLst: Array<common.DisplayReason> = []; 
        //ver21
        relaResonDis: KnockoutObservable<boolean> = ko.observable(true);
        hdTypeDis: KnockoutObservable<boolean> = ko.observable(false);
        dataMax: KnockoutObservable<boolean> = ko.observable(false);
        appCur: any = null;
        appAbsenceStartInfoDto: any;
        dayDispSet: KnockoutObservable<boolean> = ko.observable(false);
        constructor(listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata) {
            super(listAppMetadata, currentApp);
            let self = this;
            self.appCur = currentApp;
              self.startPage(self.appID()).done(function(){

                });   
            self.selectedRelation.subscribe(function(codeChange){
                if(codeChange === undefined || codeChange == null || codeChange.length == 0){
                    return;
                }
                if(self.appAbsenceStartInfoDto.specAbsenceDispInfo != null){
                    service.changeRelaCD({
                        frameNo: self.appAbsenceStartInfoDto.specAbsenceDispInfo == null ? '' : self.appAbsenceStartInfoDto.specAbsenceDispInfo.frameNo,
                        specHdEvent: self.appAbsenceStartInfoDto.specAbsenceDispInfo == null ? '' : self.appAbsenceStartInfoDto.specAbsenceDispInfo.specHdEvent,
                        relationCD: codeChange
                    }).done(function(data){
                    //上限日数表示エリア(vùng hiển thị số ngày tối đa)
                    let line1 = getText('KAF006_44');
                    let maxDay = 0;
                    if(self.mournerDis() && self.isCheck()){//・ 画面上喪主チェックボックス(A10_3)が表示される　AND チェックあり ⇒ 上限日数　＋　喪主加算日数
                        maxDay =data.maxDayObj == null ? 0 :  data.maxDayObj.maxDay + data.maxDayObj.dayOfRela;
                    }else{//・その以外 ⇒ 上限日数
                        maxDay = data.maxDayObj == null ? 0 : data.maxDayObj.maxDay;
                    }
                    if(data.maxDayObj != null){
                        self.maxDay(data.maxDayObj.maxDay);
                        self.dayOfRela(data.maxDayObj.dayOfRela);  
                        self.dataMax(true);  
                    }else{
                        self.dataMax(false);    
                    }
                    let line2 = getText('KAF006_46',[maxDay]);
                    
                    self.maxDayline1(line1);
                    self.maxDayline2(line2);
                    //bug #110129
                    self.appAbsenceStartInfoDto.specAbsenceDispInfo.maxDay = self.maxDay();
                    self.appAbsenceStartInfoDto.specAbsenceDispInfo.dayOfRela = self.dayOfRela();
                    
                    //ver21
                    let relaS = self.findRelaSelected(codeChange);
                    self.relaResonDis(relaS == undefined ? false : relaS.threeParentOrLess);
                });
                }
                
            self.isCheck.subscribe(function(checkChange){
                if(self.mournerDis()){
                    //上限日数表示エリア(vùng hiển thị số ngày tối đa)
                    let line1 = getText('KAF006_44');
                    let maxDay = 0;
                    if(self.mournerDis() && self.isCheck()){//・ 画面上喪主チェックボックス(A10_3)が表示される　AND チェックあり ⇒ 上限日数　＋　喪主加算日数
                        maxDay = self.maxDay() + self.dayOfRela();
                    }else{//・その以外 ⇒ 上限日数
                        maxDay = self.maxDay();
                    }
                    let line2 = getText('KAF006_46',[maxDay]);
                    
                    self.maxDayline1(line1);
                    self.maxDayline2(line2);
                }
            });
            });
        }
        findRelaSelected(relaCD: string): any{
            let self = this;
            return _.find(self.relationCombo(), function(rela){
                return rela.relationCd == relaCD;
            });
        }
        /**
         * 
         */
        startPage(appID: string): JQueryPromise<any> {
            nts.uk.ui.block.invisible();
            let self = this;
            let dfd = $.Deferred();
            service.findByAppID(appID).done((data) => {
                let appDetailScreenInfo = data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDetailScreenInfo;
                let applicationDto = appDetailScreenInfo.application;
                let appType = applicationDto.applicationType
                if (appType != 0) {
                    let paramLog = {
                        programId: 'KAF000',
                        screenId: 'B',
                        queryString: 'apptype=' + appType
                    };
                    nts.uk.at.view.kaf000.b.service.writeLog(paramLog);
                }
                self.inputCommandEvent().version = applicationDto.version;
                self.version = applicationDto.version;
                self.dataApplication(applicationDto);
                self.appType(applicationDto.applicationType);
                self.approvalRootState(ko.mapping.fromJS(data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDetailScreenInfo.approvalLst)());
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
                self.appAbsenceStartInfoDto = data.appAbsenceStartInfoDto;
                let numberRemain = data.appAbsenceStartInfoDto.remainVacationInfo;
                //No.376
                if(numberRemain != null){
                    if(numberRemain.yearRemain != null){//年休残数
                        self.yearRemain(numberRemain.yearRemain + '日');
                        self.yearDis(true);
                    }
                    if(numberRemain.subHdRemain != null){//代休残数
                        self.subHdRemain(numberRemain.subHdRemain + '日');
                        self.subHdDis(true);
                    }
                    if(numberRemain.subVacaRemain != null){//振休残数
                        self.subVacaRemain(numberRemain.subVacaRemain + '日');
                        self.subVacaDis(true);
                    }
                    if(numberRemain.stockRemain != null){//ストック休暇残数
                        self.stockRemain(numberRemain.stockRemain + '日');
                        self.stockDis(true);
                    }
                }
                if(self.yearDis() || self.subHdDis() || self.subVacaDis() || self.stockDis()){
                    self.disAll(true);
                }
                self.initData(data);
                //find by change AllDayHalfDay
                self.selectedAllDayHalfDayValue.subscribe((value) => {
                    self.getChangeAllDayHalfDayForDetail(value);
                });
                // find change value A5_3
                self.displayHalfDayValue.subscribe((value) => {
                    self.findChangeDisplayHalfDay(value);
                });
                // change workType
                self.selectedTypeOfDuty.subscribe((value) => {
                    self.findChangeWorkType(value);
                    if(self.holidayTypeCode() == 3){
                        self.hdTypeDis(true);
                    }else{
                        self.hdTypeDis(false);
                    }
                });
                self.displayWorkTimeName.subscribe((value) => {
                    self.changeDisplayWorkime();
                });
                self.changeWorkHourValue.subscribe((value) => {
                    self.changeDisplayWorkime();
                    self.enbbtnWorkTime(value);
                });
                //hoatt 2018.08.09
                self.changeForSpecHd(data);
                nts.uk.ui.block.clear();
                dfd.resolve(); 
            })
            .fail(function(res) {
                if (res.messageId == 'Msg_426') {
                    nts.uk.ui.dialog.alertError({messageId : res.messageId}).then(function(){
                        nts.uk.ui.block.clear();
                        appcommon.CommonProcess.callCMM045();
                    });
                } else if (res.messageId == 'Msg_473') {
                    dialog.alertError({ messageId: res.messageId }).then(function() {
                        nts.uk.ui.block.clear();
                    });
                } else {
                    nts.uk.ui.dialog.alertError(res.message).then(function() {
                        nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                        nts.uk.ui.block.clear();
                    });
                }
                dfd.reject(res);  
            });
            return dfd.promise();
        }
        changeForSpecHd(data: any){
            let self = this;
            let specAbsenceDispInfo = null;
            if (data.appAbsenceStartInfoDto === undefined){
               specAbsenceDispInfo  = data.specAbsenceDispInfo;
            }else {
                specAbsenceDispInfo = data.appAbsenceStartInfoDto.specAbsenceDispInfo
            }
             
            if(nts.uk.util.isNullOrUndefined(specAbsenceDispInfo)) {
                self.fix(false);
                self.maxDayDis(false);
                self.dataMax(false);
                return;        
            }
             //hoatt 2018.08.09
            //relationship list
            self.relationCombo([]);
            let lstRela = [];
            let lstRelaOutput = [];
            if(!nts.uk.util.isNullOrEmpty(specAbsenceDispInfo.dateSpecHdRelationLst)) {
                lstRelaOutput = specAbsenceDispInfo.dateSpecHdRelationLst;    
            }   
            _.each(lstRelaOutput, function(rela){
                lstRela.push({relationCd: rela.relationCD, relationName: rela.relationName, 
                        maxDate: rela.maxDate, threeParentOrLess: rela.threeParentOrLess});
            });
            self.relationCombo(lstRela);
            let fix = false;
            if(specAbsenceDispInfo.specHdForEventFlag){
                fix = specAbsenceDispInfo.specHdEvent.maxNumberDay == 2 ? true : false;
            }
            self.fix(fix);
            if(!fix){
                self.requiredRela(false);
            }else{
                self.requiredRela(true);
            }
            self.maxDayDis(specAbsenceDispInfo.specHdForEventFlag);
            if(specAbsenceDispInfo.specHdForEventFlag && specAbsenceDispInfo.specHdEvent.maxNumberDay == 2 && specAbsenceDispInfo.specHdEvent.makeInvitation == 1){
                self.mournerDis(true);
            }else{
                self.mournerDis(false);
            }
            nts.uk.ui.errors.clearAll();
            if(self.holidayTypeCode() == 3){
                if (data.appAbsenceDto !== undefined) {                    
                    if(data.appAbsenceDto.appForSpecLeave != null){
                        self.relaReason(data.appAbsenceDto.appForSpecLeave.relationshipReason);
                        self.isCheck(data.appAbsenceDto.appForSpecLeave.mournerFlag);
                        self.selectedRelation(data.appAbsenceDto.appForSpecLeave.relationshipCD);
                        if(!fix && self.relaReason() != ''){
                            $("#relaReason").ntsError('clear');
                        }
                    }else{//data db k co
                        if(!fix){//th an clear rela reason
                            self.relaReason('');
                        }
                        $("#relaReason").ntsError('clear');
                        if(self.relaReason() != ''){
                            $("#relaReason").trigger("validate");
                        }
                    }
                }
                //上限日数表示エリア(vùng hiển thị số ngày tối đa)
                let line1 = getText('KAF006_44');
                let maxDay = 0;
                if(self.mournerDis() && self.isCheck()){//・ 画面上喪主チェックボックス(A10_3)が表示される　AND チェックあり ⇒ 上限日数　＋　喪主加算日数
                    if(specAbsenceDispInfo.maxDayObj !== undefined){
                        maxDay = specAbsenceDispInfo.maxDayObj.maxDay + specAbsenceDispInfo.maxDayObj.dayOfRela;
                    }else{
                        maxDay = specAbsenceDispInfo.maxDay + specAbsenceDispInfo.dayOfRela;
                    }
                    
                }else{//・その以外 ⇒ 上限日数
                    if(specAbsenceDispInfo.maxDayObj !== undefined){
                        maxDay = specAbsenceDispInfo.maxDayObj.maxDay;
                    }else{
                        maxDay = specAbsenceDispInfo.maxDay;
                    }
                }
                if(maxDay != null){
                    self.maxDay(specAbsenceDispInfo.maxDay);
                    self.dayOfRela(specAbsenceDispInfo.dayOfRela);  
                    self.dataMax(true);  
                }else{
                    self.dataMax(false);    
                }
                let line2 = getText('KAF006_46',[maxDay]);
                
                self.maxDayline1(line1);
                self.maxDayline2(line2);
            }
            
        }
        // change by switch button AllDayHalfDay(A3_12)
        getChangeAllDayHalfDayForDetail(value: any){
            let self = this;
            let dfd = $.Deferred();
           
            service.getChangeAllDayHalfDayForDetail({
                startAppDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : moment(self.startAppDate()).format(self.DATE_FORMAT),
                endAppDate: nts.uk.util.isNullOrEmpty(self.endAppDate()) ? null : moment(self.endAppDate()).format(self.DATE_FORMAT),
                employeeID: nts.uk.util.isNullOrEmpty(self.employeeID()) ? null : self.employeeID(),
                displayHalfDayValue: self.displayHalfDayValue(),
                holidayType: nts.uk.util.isNullOrEmpty(self.holidayTypeCode()) ? null : self.holidayTypeCode(),
                alldayHalfDay: value,
                appAbsenceStartInfoDto: self.appAbsenceStartInfoDto
            }).done((result) =>{
                self.appAbsenceStartInfoDto = result;
                self.changeWorkHourValueFlg(result.workHoursDisp);
                if (nts.uk.util.isNullOrEmpty(result.workTypeLst)) {
                    self.typeOfDutys([]);
                    self.workTypecodes([]);
                    self.selectedTypeOfDuty(null);
                    self.fix(false);
                    self.mournerDis(false);
                    self.maxDayDis(false);
                    self.isCheck(false);
                    self.relaReason('');
                }else{
                    let a = [];
                    self.workTypecodes.removeAll();
                    for (let i = 0; i < result.workTypeLst.length; i++) {
                        a.push(new common.TypeOfDuty(result.workTypeLst[i].workTypeCode, result.workTypeLst[i].workTypeCode + "　" + result.workTypeLst[i].name));
                        self.workTypecodes.push(result.workTypeLst[i].workTypeCode);
                    }
                    self.typeOfDutys(a);
                    if (!ko.toJS(self.selectedTypeOfDuty)) {
                        self.selectedTypeOfDuty(result.workTypeCode);
                    }
                }
                if(!nts.uk.util.isNullOrEmpty(result.workTimeLst)){
                    self.workTimeCodes.removeAll();
                    self.workTimeCodes(result.workTimeLst);
                }
                 dfd.resolve(result);
            }).fail((res) =>{
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                        .then(function() { nts.uk.ui.block.clear(); });
                dfd.reject(res);
            });
             return dfd.promise();
        }
        // change by switch button DisplayHalfDay(A5_3)
        findChangeDisplayHalfDay(value: any){
            let self = this;
            let dfd = $.Deferred();
            service.getChangeDisplayHalfDay({
                startAppDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : moment(self.startAppDate()).format(self.DATE_FORMAT),
                endAppDate: nts.uk.util.isNullOrEmpty(self.endAppDate()) ? null : moment(self.endAppDate()).format(self.DATE_FORMAT),
                employeeID: nts.uk.util.isNullOrEmpty(self.employeeID()) ? null : self.employeeID(),
                displayHalfDayValue: self.displayHalfDayValue(),
                holidayType: nts.uk.util.isNullOrEmpty(self.holidayTypeCode()) ? null : self.holidayTypeCode(),
                workTypeCode: self.selectedTypeOfDuty(),
                alldayHalfDay: self.selectedAllDayHalfDayValue(),
                appAbsenceStartInfoDto: self.appAbsenceStartInfoDto
            }).done((result) =>{
                self.appAbsenceStartInfoDto = result;
                self.changeWorkHourValueFlg(result.workHoursDisp);
                if (nts.uk.util.isNullOrEmpty(result.workTypeLst)) {
                    self.typeOfDutys([]);
                    self.workTypecodes([]);
                    self.selectedTypeOfDuty(null);
                    self.fix(false);
                    self.mournerDis(false);
                    self.maxDayDis(false);
                    self.isCheck(false);
                    self.relaReason('');
                }else{
                    let a = [];
                    self.workTypecodes.removeAll();
                    for (let i = 0; i < result.workTypeLst.length; i++) {
                        a.push(new common.TypeOfDuty(result.workTypeLst[i].workTypeCode, result.workTypeLst[i].workTypeCode + "　" + result.workTypeLst[i].name));
                        self.workTypecodes.push(result.workTypeLst[i].workTypeCode);
                    }
                    self.typeOfDutys(a);
                    if (!ko.toJS(self.selectedTypeOfDuty)) {
                        self.selectedTypeOfDuty(result.workTypeCode);
                    }
                }
                if(!nts.uk.util.isNullOrEmpty(result.workTimeLst)){
                    self.workTimeCodes.removeAll();
                    self.workTimeCodes(result.workTimeLst);
                }
                 dfd.resolve(result);
            }).fail((res) =>{
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                        .then(function() { nts.uk.ui.block.clear(); });
                dfd.reject(res);
            });
             return dfd.promise();
        }
        // change by workType
        findChangeWorkType(value: any){
            let self = this;
            let dfd = $.Deferred();
            
            if(!ko.toJS(self.selectedTypeOfDuty)){
                return;
            }
            
            self.appAbsenceStartInfoDto.selectedWorkTimeCD = self.workTimeCode();
            self.appAbsenceStartInfoDto.selectedWorkTypeCD = self.selectedTypeOfDuty();
            service.getChangeWorkType({
                startAppDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : moment(self.startAppDate()).format(self.DATE_FORMAT),
                employeeID: nts.uk.util.isNullOrEmpty(self.employeeID()) ? null : self.employeeID(),
                holidayType: nts.uk.util.isNullOrEmpty(self.holidayTypeCode()) ? null : self.holidayTypeCode(),
                workTypeCode: self.selectedTypeOfDuty(),
                workTimeCode: self.workTimeCode(),
                appAbsenceStartInfoDto: self.appAbsenceStartInfoDto
            }).done((result) =>{
                self.appAbsenceStartInfoDto = result;
                //hoatt 2018.08.09
                self.changeForSpecHd(result);
                self.changeWorkHourValueFlg(result.workHoursDisp);
                
                if (result.workTimeLst != null && result.workTimeLst.length >=1 ) {
                    if (result.workTimeLst[0].startTime != null) {
                        self.timeStart1(result.workTimeLst[0].startTime);
                    }
                    if (result.workTimeLst[0].endTime != null) {
                        self.timeEnd1(result.workTimeLst[0].endTime)
                    }
                    
                }else {
                    self.timeStart1(null);    
                    self.timeEnd1(null);    
                
                }
                
                dfd.resolve(result);
            }).fail((res) =>{
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                        .then(function() { nts.uk.ui.block.clear(); });
                dfd.reject(res);
            });
            
             return dfd.promise();
        }
        initData(data: any){
            let self = this;
            let listAppTypeSet = data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoNoDateOutput.requestSetting.applicationSetting.listAppTypeSetting;
            let appTypeSet = _.find(listAppTypeSet, o => o.appType == 1);
            let appAbsenceStartInfoDto = data.appAbsenceStartInfoDto;
            let application = data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDetailScreenInfo.application;
            let appAbsenceDto = data.appAbsenceDto;
            let employeeInfoLst = data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoNoDateOutput.employeeInfoLst;
            _.forEach(data.appAbsenceStartInfoDto.displayReasonLst, (o) => {
                self.displayReasonLst.push(new common.DisplayReason(o.typeOfLeaveApp, o.displayFixedReason==1?true:false, o.displayAppReason==1?true:false));     
            });
            self.version = application.version;
            self.employeeID(application.applicantSID);
            self.employeeName(_.find(employeeInfoLst, o => o.sid == application.applicantSID).bussinessName);
            self.prePostSelected(application.prePostAtr);
            self.convertListHolidayType(data.appAbsenceStartInfoDto.holidayAppTypeName);
            self.holidayTypeCode(appAbsenceDto.holidayAppType);
            
            if(appAbsenceStartInfoDto.workTypeNotRegister){
                 self.typeOfDutys.push(new common.TypeOfDuty(appAbsenceDto.workTypeCode, appAbsenceDto.workTypeCode + '　' + 'マスタ未登録'));
            }
            
            if (!nts.uk.util.isNullOrEmpty(appAbsenceStartInfoDto.workTypeLst)) {
                for (let i = 0; i < appAbsenceStartInfoDto.workTypeLst.length; i++) {
                    self.typeOfDutys.push(new common.TypeOfDuty(
                        appAbsenceStartInfoDto.workTypeLst[i].workTypeCode, 
                        appAbsenceStartInfoDto.workTypeLst[i].workTypeCode + "　" + appAbsenceStartInfoDto.workTypeLst[i].name));
                    self.workTypecodes.push(appAbsenceStartInfoDto.workTypeLst[i].workTypeCode);
                }
            }
            
            self.dayDispSet(data.appAbsenceStartInfoDto.hdAppSet.dayDispSet==1?true:false);
            let currentDisplay = _.find(self.displayReasonLst, (o) => o.typeLeave==self.holidayTypeCode());
            
            if(nts.uk.util.isNullOrUndefined(currentDisplay)){
                self.typicalReasonDisplayFlg(false);
                self.displayAppReasonContentFlg(false);
            } else {
                self.typicalReasonDisplayFlg(currentDisplay.displayFixedReason);
                self.displayAppReasonContentFlg(self.typicalReasonDisplayFlg()||
                    (currentDisplay.displayAppReason)); 
                self.enbReasonCombo(currentDisplay.displayFixedReason);
                self.enbContentReason(currentDisplay.displayAppReason);
            }
            
            self.requiredReason(data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoNoDateOutput.requestSetting.applicationSetting.appLimitSetting.requiredAppReason);
            self.workTimeCode(appAbsenceDto.workTimeCode);
            
            let workTimeLst = data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoWithDateOutput.workTimeLst;
            let workTimeCurrent = _.find(workTimeLst, (o) => o.worktimeCode == self.workTimeCode());
            let wktimeName = "";
            
            if(_.isUndefined(workTimeCurrent)) {
                wktimeName = nts.uk.resource.getText('KAL003_120');     
            } else {
                wktimeName = workTimeCurrent.workTimeDisplayName.workTimeName || nts.uk.resource.getText('KAL003_120');
            }  
            
            self.displayWorkTimeName(nts.uk.util.isNullOrEmpty(appAbsenceDto.workTimeCode) ? nts.uk.resource.getText('KAF006_21') : appAbsenceDto.workTimeCode +"　"+ wktimeName);
            
            let appReasonLst = data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDispInfoNoDateOutput.appReasonLst;
            
            if(appReasonLst != null && appReasonLst.length > 0){
                let lstReasonCombo = _.map(appReasonLst, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); });
                self.reasonCombo(lstReasonCombo);
                let reasonID = appReasonLst[0].reasonID;
                self.selectedReason(reasonID);
                
                self.multilContent(application.applicationReason);
            }
            self.workTimeCodes(data.appAbsenceStartInfoDto.workTimeLst);
            
            
            self.changeWorkHourValueFlg(appAbsenceStartInfoDto.workHoursDisp);
            self.changeWorkHourValue(appAbsenceDto.changeWorkHourFlg);
            self.selectedAllDayHalfDayValue(appAbsenceDto.allDayHalfDayLeaveAtr);
            
            self.startAppDate(moment(application.applicationDate ).format(self.DATE_FORMAT));
            self.endAppDate(application.endDate);
            
            if(self.endAppDate() === self.startAppDate()){
                self.appDate(moment(application.applicationDate ).format(self.DATE_FORMAT));
            }else{
                let appDateAll = moment(application.applicationDate ).format(self.DATE_FORMAT) +"　"+ nts.uk.resource.getText('KAF005_38')　+"　"+  moment(application.endDate).format(self.DATE_FORMAT);
                self.appDate(appDateAll);
            }
            self.timeStart1(appAbsenceDto.startTime1 == null ? null : appAbsenceDto.startTime1);
            self.timeEnd1(appAbsenceDto.endTime1 == null ? null : appAbsenceDto.endTime1);
            if(appAbsenceDto.holidayAppType == 3){
                self.hdTypeDis(true);
            }
            //rela specHdDto
            if(appAbsenceDto.appForSpecLeave != null && appAbsenceDto.appForSpecLeave !== undefined){
                self.relaRelaReason(appAbsenceDto.appForSpecLeave.relationshipReason);
                self.selectedRelation(appAbsenceDto.appForSpecLeave.relationshipCD);
            }
            let initMode = data.appAbsenceStartInfoDto.appDispInfoStartupOutput.appDetailScreenInfo.outputMode;
            
            if(initMode == 0){
                // display Mode
                self.enbAllDayHalfDayFlg(false);
                self.enbWorkType(false);
                self.enbHalfDayFlg(false);
                self.enbChangeWorkHourFlg(false);
                self.enbbtnWorkTime(false);
                self.eblTimeStart1(false);
                self.eblTimeEnd1(false);
                self.enbReasonCombo(false);
                self.enbContentReason(false);
                self.relaEnable(false);
                self.relaMourner(false);
                self.relaRelaReason(false);
            }else if(initMode == 1){
                self.relaEnable(true);
                self.relaMourner(true);
                self.relaRelaReason(true);
                // edit Mode
                self.enbAllDayHalfDayFlg(true);
                self.enbWorkType(true);
                self.enbHalfDayFlg(true);
                self.enbChangeWorkHourFlg(true);
                
                if(data.appAbsenceStartInfoDto.workHoursDisp && !nts.uk.util.isNullOrEmpty(appAbsenceDto.workTimeCode)){
                     self.eblTimeStart1(true);
                     self.eblTimeEnd1(true);
                    self.enbbtnWorkTime(true);
                }else{
                    self.eblTimeStart1(false);
                     self.eblTimeEnd1(false);
                    self.enbbtnWorkTime(false);
                }
            }
            
            self.selectedTypeOfDuty(appAbsenceDto.workTypeCode);
            self.displayHalfDayValue(appAbsenceDto.halfDayFlg);
        }
         update(): JQueryPromise<any> {
             let self = this;
             $("#workTypes").trigger('validate');
             $("#relaReason").trigger("validate");
             if(self.holidayTypeCode() == 3 && self.fix()){
                $("#relaCD-combo").trigger("validate");
            }
             if (nts.uk.ui.errors.hasError()){return;} 
             nts.uk.ui.block.invisible();
             let comboBoxReason: string = appcommon.CommonProcess.getComboBoxReason(self.selectedReason(), self.reasonCombo(), self.typicalReasonDisplayFlg());
             let textAreaReason: string = appcommon.CommonProcess.getTextAreaReason(self.multilContent(), self.displayAppReasonContentFlg(), true); 
             let appReason: string;
             if (!appcommon.CommonProcess.checklenghtReason(comboBoxReason+":"+textAreaReason, "#appReason")) {
                 return;
             }
             let specHd = null;
            if(self.holidayTypeCode() == 3 && self.fix()){
                specHd = {  relationshipCD: self.selectedRelation(),
                            mournerFlag: self.isCheck(),
                            relationshipReason: self.relaReason()
                        }
            }
             let paramInsert = {
//                version: self.version,
//                appID: self.appID(),
//                prePostAtr: self.prePostSelected(),
//                startDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : self.startAppDate(),
//                endDate:  nts.uk.util.isNullOrEmpty(self.endAppDate()) ? self.startAppDate() : self.endAppDate(),
//                employeeID: self.employeeID(),
//                appReasonID: comboBoxReason,
//                applicationReason: textAreaReason,
//                holidayAppType: nts.uk.util.isNullOrEmpty(self.holidayTypeCode()) ? null : self.holidayTypeCode(),
//                workTypeCode: self.selectedTypeOfDuty(),
//                workTimeCode: nts.uk.util.isNullOrEmpty(self.workTimeCode()) ? null : self.workTimeCode(),
//                halfDayFlg: self.displayHalfDayValue(),
//                changeWorkHour: self.changeWorkHourValue(),
//                allDayHalfDayLeaveAtr: self.selectedAllDayHalfDayValue(),
//                startTime1: self.timeStart1(),
//                endTime1: self.timeEnd1(),
//                startTime2: self.timeStart2(),
//                endTime2: self.timeEnd2(),
//                specHd: specHd,
//                user: self.user,
//                reflectPerState: self.reflectPerState
                appAbsenceStartInfoDto: self.appAbsenceStartInfoDto,
                applicationCommand: self.getApplicationCommand(comboBoxReason, textAreaReason),
                appAbsenceCommand: self.getAbsenceCommand(specHd),
                alldayHalfDay: self.selectedAllDayHalfDayValue(),
                mourningAtr: self.isCheck(),
                holidayDateLst: [],
             };
             service.checkBeforeUpdate(paramInsert).done((data) =>{
                self.processConfirmMsg(paramInsert, data, 0);
             }).fail((res) =>{
                 dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                .then(function() { 
                    nts.uk.ui.block.clear(); 
                    if (res.messageId === "Msg_197") {
                        location.reload();
                    }
                });
             });
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
            
        getReason(inputReasonID: string, inputReasonList: Array<common.ComboReason>, detailReason: string): string{
            let appReason = '';
            let inputReason: string = '';
            if(!nts.uk.util.isNullOrEmpty(inputReasonID)){
                inputReason = _.find(inputReasonList, o => { return o.reasonId == inputReasonID; }).reasonName;    
            }    
            if (!nts.uk.util.isNullOrEmpty(inputReason) && !nts.uk.util.isNullOrEmpty(detailReason)) {
                appReason = inputReason + ":" + detailReason;
            } else if (!nts.uk.util.isNullOrEmpty(inputReason) && nts.uk.util.isNullOrEmpty(detailReason)) {
                appReason = inputReason;
            } else if (nts.uk.util.isNullOrEmpty(inputReason) && !nts.uk.util.isNullOrEmpty(detailReason)) {
                appReason = detailReason;
            }                
            return appReason;
        }
        btnSelectWorkTimeZone(){
            let self = this;
            self.getListWorkTime().done(() =>{
                nts.uk.ui.windows.setShared('parentCodes', {
                    workTypeCodes: self.workTypecodes(),
                    selectedWorkTypeCode: self.selectedTypeOfDuty(),
                    workTimeCodes: self.workTimeCodes(),
                    selectedWorkTimeCode: self.workTimeCode()
                }, true);

                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml').onClosed(function(): any {
                    //view all code of selected item 
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
//                        self.selectedWorkTypeCode(childData.selectedWorkTypeCode);
//                        self.workTypeName(childData.selectedWorkTypeName);
                        self.selectedTypeOfDuty(childData.selectedWorkTypeCode);
                        self.workTimeCode(childData.selectedWorkTimeCode);
                        self.workTimeName(childData.selectedWorkTimeName);
                        self.displayWorkTimeName(childData.selectedWorkTimeCode +"　"+childData.selectedWorkTimeName);
                        service.getWorkingHours(
                            {
                                holidayType: nts.uk.util.isNullOrEmpty(self.holidayTypeCode()) ? null : self.holidayTypeCode(),
                                workTypeCode: self.selectedTypeOfDuty(),
                                workTimeCode: self.workTimeCode(),
                                appAbsenceStartInfoDto: self.appAbsenceStartInfoDto
                            }
                        ).done(data => {
                            if(nts.uk.util.isNullOrEmpty(data)){
                                self.timeStart1(null);    
                                self.timeEnd1(null);
                            } else {
                                if(nts.uk.util.isNullOrUndefined(data[0])){
                                    self.timeStart1(childData.first.start);    
                                    self.timeEnd1(childData.first.end);    
                                } else {
                                    self.timeStart1(data[0].startTime == null ? childData.first.start : data[0].startTime);
                                    self.timeEnd1(data[0].endTime == null ? childData.first.end : data[0].endTime);        
                                }
                            }
                        }).fail(() => {
                            self.timeStart1(childData.first.start);    
                            self.timeEnd1(childData.first.end);
                        });
                    }
                });
            });
        }
        changeDisplayWorkime() {
            let self = this;
            self.eblTimeStart1(self.changeWorkHourValue() && (self.displayWorkTimeName() != nts.uk.resource.getText('KAF006_21')));
            self.eblTimeEnd1(self.changeWorkHourValue() && (self.displayWorkTimeName() != nts.uk.resource.getText('KAF006_21')));
        }
        getListWorkTime(){
            let self = this;
            let dfd = $.Deferred();
            service.getListWorkTime({
               startAppDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : moment(self.startAppDate()).format(self.DATE_FORMAT),
               employeeID: nts.uk.util.isNullOrEmpty(self.employeeID()) ? null : self.employeeID(),    
            }).done((value) =>{
                self.workTimeCodes(value);
                dfd.resolve(value);
            }).fail((res) =>{
                dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                        .then(function() { nts.uk.ui.block.clear(); });
                dfd.reject(res);
            })
            return dfd.promise();
        }
        convertListHolidayType(data: any) {
            let self = this;
            for (let i = 0; i < data.length; i++) {
                self.holidayTypes.push(new common.HolidayType(data[i].holidayAppTypeCode, data[i].holidayAppTypeName));
            }
        }
        
        /**
         * Jump to CMM018 Screen
         */
        openCMM018(){
            let self = this;
            nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", {screen: 'Application', employeeId: self.employeeID});  
        }
            
        getApplicationCommand(comboBoxReason, textAreaReason) {
            let self = this;
            return {
                version: self.version,
                applicationID: self.appID(),
                prePostAtr: self.prePostSelected(),  
                appReasonID: comboBoxReason,
                applicationReason: textAreaReason,
                applicantSID: self.employeeID(),
                startDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : self.startAppDate(),
                endDate: nts.uk.util.isNullOrEmpty(self.endAppDate()) ? self.startAppDate() : self.endAppDate(),
            }     
        }
        
        getAbsenceCommand(specHd: any) {
            let self = this;
            return {
                appID: self.appID(),
                holidayAppType: nts.uk.util.isNullOrEmpty(self.holidayTypeCode()) ? null : self.holidayTypeCode(),
                workTypeCode: self.selectedTypeOfDuty(),
                workTimeCode: nts.uk.util.isNullOrEmpty(self.workTimeCode()) ? null : self.workTimeCode(),
                halfDayFlg: self.displayHalfDayValue(),
                changeWorkHour: self.changeWorkHourValue(),
                allDayHalfDayLeaveAtr: self.selectedAllDayHalfDayValue(),
                startTime1: self.timeStart1(),
                endTime1: self.timeEnd1(),
                startTime2: self.timeStart2(),
                endTime2: self.timeEnd2(),
                appForSpecLeave: specHd
            }         
        }
        
        processConfirmMsg(paramInsert: any, result: any, confirmIndex: number) {
            let self = this;
            let confirmMsgLst = result.confirmMsgLst;
            let confirmMsg = confirmMsgLst[confirmIndex];
            if(_.isUndefined(confirmMsg)) {
                paramInsert.holidayDateLst = result.holidayDateLst;
                service.updateAbsence(paramInsert).done((data) => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        if(data.autoSendMail){
                            appcommon.CommonProcess.displayMailResult(data);   
                        } else {
                            self.reBinding(self.listAppMeta, self.appCur, false);
                        }
                    });
                }).fail((res) => {
                    dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                        .then(function() { nts.uk.ui.block.clear(); });
                });
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

