module nts.uk.at.view.kaf006.b{
    import common = nts.uk.at.view.kaf006.share.common;
    import service = nts.uk.at.view.kaf006.shr.service;
    import dialog = nts.uk.ui.dialog;
    import appcommon = nts.uk.at.view.kaf000.shr.model;
    import model = nts.uk.at.view.kaf000.b.viewmodel.model;
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
                
        constructor(listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata) {
            super(listAppMetadata, currentApp);
            let self = this;
              self.startPage(self.appID()).done(function(){

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
                    });
                    self.displayWorkTimeName.subscribe((value) => {
                        self.changeDisplayWorkime();
                    });
                    self.changeWorkHourValue.subscribe((value) => {
                        self.changeDisplayWorkime();
                        self.enbbtnWorkTime(value);
                    });
                    dfd.resolve(); 
                })
                .fail(function(res) {
                    if (res.messageId == 'Msg_426') {
                        dialog.alertError({ messageId: res.messageId }).then(function() {
                            nts.uk.ui.block.clear();
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
                alldayHalfDay: value
            }).done((result) =>{
                self.changeWorkHourValueFlg(result.changeWorkHourFlg);
                if (nts.uk.util.isNullOrEmpty(result.workTypes)) {
                    self.typeOfDutys([]);
                    self.workTypecodes([]);
                    self.selectedTypeOfDuty(null);
                }else{
                    self.typeOfDutys.removeAll();
                    self.workTypecodes.removeAll();
                    for (let i = 0; i < result.workTypes.length; i++) {
                        self.typeOfDutys.push(new common.TypeOfDuty(result.workTypes[i].workTypeCode, result.workTypes[i].displayName));
                        self.workTypecodes.push(result.workTypes[i].workTypeCode);
                    }
                    if (nts.uk.util.isNullOrEmpty(self.selectedTypeOfDuty)) {
                        self.selectedTypeOfDuty(result.workTypeCode);
                    }
                }
                if(!nts.uk.util.isNullOrEmpty(result.workTimeCodes)){
                    self.workTimeCodes.removeAll();
                    self.workTimeCodes(result.workTimeCodes);
                }
                 dfd.resolve(result);
            }).fail((res) =>{
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
                alldayHalfDay: self.selectedAllDayHalfDayValue()
            }).done((result) =>{
                self.changeWorkHourValueFlg(result.changeWorkHourFlg);
                if (nts.uk.util.isNullOrEmpty(result.workTypes)) {
                    self.typeOfDutys([]);
                    self.workTypecodes([]);
                    self.selectedTypeOfDuty(null);
                }else{
                    self.typeOfDutys.removeAll();
                    self.workTypecodes.removeAll();
                    for (let i = 0; i < result.workTypes.length; i++) {
                        self.typeOfDutys.push(new common.TypeOfDuty(result.workTypes[i].workTypeCode, result.workTypes[i].displayName));
                        self.workTypecodes.push(result.workTypes[i].workTypeCode);
                    }
                    if (nts.uk.util.isNullOrEmpty(self.selectedTypeOfDuty)) {
                        self.selectedTypeOfDuty(result.workTypeCode);
                    }
                }
                if(!nts.uk.util.isNullOrEmpty(result.workTimeCodes)){
                    self.workTimeCodes.removeAll();
                    self.workTimeCodes(result.workTimeCodes);
                }
                 dfd.resolve(result);
            }).fail((res) =>{
                dfd.reject(res);
            });
             return dfd.promise();
        }
        // change by workType
        findChangeWorkType(value: any){
            let self = this;
            let dfd = $.Deferred();
            service.getChangeWorkType({
                startAppDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : moment(self.startAppDate()).format(self.DATE_FORMAT),
                employeeID: nts.uk.util.isNullOrEmpty(self.employeeID()) ? null : self.employeeID(),
                holidayType: nts.uk.util.isNullOrEmpty(self.holidayTypeCode()) ? null : self.holidayTypeCode(),
                workTypeCode: self.selectedTypeOfDuty(),
                workTimeCode: self.workTimeCode()
            }).done((result) =>{
                self.changeWorkHourValueFlg(result.changeWorkHourFlg);
                if(result.startTime1 != null){
                    self.timeStart1(result.startTime1);    
                }
                if(result.endTime1 != null){
                    self.timeEnd1(result.endTime1);    
                }
                dfd.resolve(result);
            }).fail((res) =>{
                dfd.reject(res);
            });
             return dfd.promise();
        }
        initData(data: any){
            let self = this;
            self.version = data.application.version;
            self.manualSendMailAtr(data.manualSendMailFlg);
            self.employeeName(data.employeeName);
            self.employeeID(data.employeeID);
            self.prePostSelected(data.application.prePostAtr);
            self.convertListHolidayType(data.holidayAppTypeName);
            self.holidayTypeCode(data.holidayAppType);
            self.displayPrePostFlg(data.prePostFlg);
            self.requiredReason(data.appReasonRequire);
            self.workTimeCode(data.workTimeCode);
            self.displayWorkTimeName(nts.uk.util.isNullOrEmpty(data.workTimeCode) ? nts.uk.resource.getText('KAF006_21') : data.workTimeCode +"　"+ data.workTimeName);
            if(data.applicationReasonDtos != null && data.applicationReasonDtos.length > 0){
                let lstReasonCombo = _.map(data.applicationReasonDtos, o => { return new common.ComboReason(o.reasonID, o.reasonTemp); });
                self.reasonCombo(lstReasonCombo);
                let reasonID = data.applicationReasonDtos[0].reasonID;
                self.selectedReason(reasonID);
                
                self.multilContent(data.application.applicationReason);
            }
            self.workTimeCodes(data.workTimeCodes);
            if (!nts.uk.util.isNullOrEmpty(data.workTypes)) {
                for (let i = 0; i < data.workTypes.length; i++) {
                    self.typeOfDutys.push(new common.TypeOfDuty(data.workTypes[i].workTypeCode, data.workTypes[i].displayName));
                    self.workTypecodes.push(data.workTypes[i].workTypeCode);
                }
                self.selectedTypeOfDuty(data.workTypeCode);
            }
            self.changeWorkHourValueFlg(data.displayWorkChangeFlg);
            self.changeWorkHourValue(data.changeWorkHourFlg);
            self.selectedAllDayHalfDayValue(data.allDayHalfDayLeaveAtr);
            self.displayHalfDayValue(data.halfDayFlg);
            self.startAppDate(moment(data.application.applicationDate ).format(self.DATE_FORMAT));
            self.endAppDate(data.application.endDate);
            if(self.endAppDate() === self.startAppDate()){
                self.appDate(moment(data.application.applicationDate ).format(self.DATE_FORMAT));
            }else{
                let appDateAll = moment(data.application.applicationDate ).format(self.DATE_FORMAT) +"　"+ nts.uk.resource.getText('KAF005_38')　+"　"+  moment(data.application.endDate).format(self.DATE_FORMAT);
                self.appDate(appDateAll);
            }
            self.timeStart1(data.startTime1 == null ? null : data.startTime1);
            self.timeEnd1(data.endTime1 == null ? null : data.endTime1);
            
            if(data.initMode == 0){
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
            }else if(data.initMode == 1){
                // edit Mode
                self.enbAllDayHalfDayFlg(true);
                self.enbWorkType(true);
                self.enbHalfDayFlg(true);
                self.enbChangeWorkHourFlg(true);
                
                if(data.changeWorkHourFlg && !nts.uk.util.isNullOrEmpty(data.workTimeCode)){
                     self.eblTimeStart1(true);
                     self.eblTimeEnd1(true);
                    self.enbbtnWorkTime(true);
                }else{
                    self.eblTimeStart1(false);
                     self.eblTimeEnd1(false);
                    self.enbbtnWorkTime(false);
                }
                self.enbReasonCombo(true);
                self.enbContentReason(true);
            }
        }
         update(): JQueryPromise<any> {
             let self = this;
             $("#workTypes").trigger('validate');
             if (nts.uk.ui.errors.hasError()){return;} 
             nts.uk.ui.block.invisible();
             let appReason: string;
             appReason = self.getReason(
                self.selectedReason(),
                self.reasonCombo(),
                self.multilContent()
            );
             let appReasonError = !appcommon.CommonProcess.checkAppReason(self.requiredReason(), self.typicalReasonDisplayFlg(), self.displayAppReasonContentFlg(), appReason);
             if (appReasonError) {
                 nts.uk.ui.dialog.alertError({ messageId: 'Msg_115' }).then(function() { nts.uk.ui.block.clear(); });
                 return;
             }
             if (!appcommon.CommonProcess.checklenghtReason(appReason, "#appReason")) {
                 return;
             }
             
             let paramInsert = {
                version: self.version,
                appID: self.appID(),
                prePostAtr: self.prePostSelected(),
                startDate: nts.uk.util.isNullOrEmpty(self.startAppDate()) ? null : self.startAppDate(),
                endDate:  nts.uk.util.isNullOrEmpty(self.endAppDate()) ? self.startAppDate() : self.endAppDate(),
                employeeID: self.employeeID(),
                applicationReason: appReason,
                holidayAppType: nts.uk.util.isNullOrEmpty(self.holidayTypeCode()) ? null : self.holidayTypeCode(),
                workTypeCode: self.selectedTypeOfDuty(),
                workTimeCode: nts.uk.util.isNullOrEmpty(self.workTimeCode()) ? null : self.workTimeCode(),
                halfDayFlg: self.displayHalfDayValue(),
                changeWorkHour: self.changeWorkHourValue(),
                allDayHalfDayLeaveAtr: self.selectedAllDayHalfDayValue(),
                startTime1: self.timeStart1(),
                endTime1: self.timeEnd1(),
                startTime2: self.timeStart2(),
                endTime2: self.timeEnd2()
             };
             service.updateAbsence(paramInsert).done((data) =>{
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    if(data.autoSendMail){
                        appcommon.CommonProcess.displayMailResult(data);   
                    } else {
                        location.reload();
                    }
                });
             }).fail((res) =>{
                 dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds })
                .then(function() { nts.uk.ui.block.clear(); });
             });
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
                                workTimeCode: self.workTimeCode()
                            }
                        ).done(data => {
                            self.timeStart1(data.startTime1 == null ? null : data.startTime1);
                            self.timeEnd1(data.endTime1 == null ? null : data.endTime1);
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
    }
    }    
}

