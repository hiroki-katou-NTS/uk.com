module nts.uk.at.view.kaf004.b.viewmodel {
    import kaf002 = nts.uk.at.view.kaf002;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    import appcommon = nts.uk.at.view.kaf000.shr.model;
    import setShared = nts.uk.ui.windows.setShared;
    const employmentRootAtr: number = 1; // EmploymentRootAtr: Application
    const applicationType: number = 9; // Application Type: Stamp Application
    
    export class ScreenModel {
        dateType: string = 'YYYY/MM/DD';
        // date editor
        date: KnockoutObservable<string> = ko.observable(moment().format(this.dateType));
        //latetime editor
        lateTime1: KnockoutObservable<number> = ko.observable(null);
        lateTime2: KnockoutObservable<number> = ko.observable(null);
        //check send mail
        checkBoxValue: KnockoutObservable<boolean> = ko.observable(false);
        enableSendMail: KnockoutObservable<boolean> = ko.observable(false);
        //check late
        late1: KnockoutObservable<boolean> = ko.observable(false);
        late2: KnockoutObservable<boolean> = ko.observable(false);
        //check early
        early1: KnockoutObservable<boolean> = ko.observable(false);
        early2: KnockoutObservable<boolean> = ko.observable(false);
        //labor time
        earlyTime1: KnockoutObservable<number> = ko.observable(null);
        earlyTime2: KnockoutObservable<number> = ko.observable(null);
        //combobox
        ListTypeReason: KnockoutObservableArray<TypeReason> = ko.observableArray([]);
        itemName: KnockoutObservable<string> = ko.observable('');
        currentCode: KnockoutObservable<number> = ko.observable(null);
        selectedCode: KnockoutObservable<string> = ko.observable('');
        //MultilineEditor
        appreason: KnockoutObservable<string> = ko.observable('');
        //Show Screen
        showScreen: string;
        employeeID: string = '';
        applicantName: KnockoutObservable<string> = ko.observable("");
        kaf000_a2: nts.uk.at.view.kaf000.a.viewmodel.ScreenModel;

        //Chua lay dc thong tin  fix cứng time
        fixtime1: KnockoutObservable<string> = ko.observable("08:30 ~ 12:00     13:00 ~ 17:30");
        fixtime2: KnockoutObservable<string> = ko.observable("10:30 ~ 11:00     14:00 ~ 17:00");
        //DisplayOrder
        displayOrder: KnockoutObservable<number> = ko.observable(0);
                
        constructor() {
            var self = this;
            //Show Screen
            self.showScreen = __viewContext.transferred.value.showScreen;
            self.kaf000_a2 = new kaf000.a.viewmodel.ScreenModel();
            self.startPage().done((commonSet: vmbase.AppStampNewSetDto) => {
                self.employeeID = commonSet.employeeID;
                self.kaf000_a2.start(
                    self.employeeID,
                    employmentRootAtr,
                    applicationType,
                    moment.utc().format(self.dateType)).done(() => {
                        nts.uk.ui.block.clear();    
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                    });
            });
            self.date.subscribe(value => {
                nts.uk.ui.block.invisible();
                self.kaf000_a2.getAppDataDate(9, moment(value).format(self.dateType), false)
                .done(()=>{
                    nts.uk.ui.block.clear();         
                }).fail(()=>{
                    nts.uk.ui.block.clear();    
                });
            });
        }

        startPage(): JQueryPromise<any> {
            nts.uk.ui.block.invisible();
            var self = this;
            var dfd = $.Deferred();
            service.getByCode("").done(function(data) {
                self.enableSendMail(data.appCommonSettingDto.appTypeDiscreteSettingDtos[0].sendMailWhenRegisterFlg == 1 ? false : true);
                self.checkBoxValue(data.appCommonSettingDto.applicationSettingDto.manualSendMailAtr == 1 ? true : false);
                self.ListTypeReason.removeAll();
                _.forEach(data.listApplicationReasonDto, data => {
                    let reasonTmp: TypeReason = {reasonID: data.reasonID, reasonTemp: data.reasonTemp};
                    self.ListTypeReason.push(reasonTmp); 
                    if(data.defaultFlg == 1){
                        self.selectedCode(data.reasonID);
                    }          
                });
                
                self.displayOrder(data.workManagementMultiple.useATR);
                self.applicantName(data.applicantName);
                self.late1.subscribe(value => { $("#inpLate1").trigger("validate"); });
                self.early1.subscribe(value => { $("#inpEarlyTime1").trigger("validate"); });
                self.late2.subscribe(value => { $("#inpLate2").trigger("validate"); });
                self.early2.subscribe(value => { $("#inpEarlyTime2").trigger("validate"); });
                dfd.resolve(data);
            }).fail(function(res) {
                if (res.messageId == 'Msg_426') {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function () {
                        nts.uk.ui.block.clear();
                    });
                }else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() { 
                        nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");
                        nts.uk.ui.block.clear();  
                    });
                }
                dfd.reject(res);
            });
            return dfd.promise();
        }


        /** Create Button Click */
        registryButtonClick() {
            var self = this;
            let errorFlag = self.kaf000_a2.errorFlag;
            let errorMsg = self.kaf000_a2.errorMsg;
            if(errorFlag!=0){
                nts.uk.ui.dialog.alertError({ messageId: errorMsg }).then(function(){nts.uk.ui.block.clear();});    
            } else {
                if (!nts.uk.ui.errors.hasError()) {
                   /**  0: 事前の受付制限
                        1: 事後の受付制限
                    */ 
                    let prePostAtr = 1;
                    if (self.showScreen == 'B'){
                        //[画面Bのみ]遅刻時刻早退時刻がともに設定されているとき、遅刻時刻>=早退時刻 (#Msg_381#)
                        if((self.late1() && self.early1() && self.lateTime1() >= self.earlyTime1())
                            || (self.late2() && self.early2() && self.lateTime2() >= self.earlyTime2())){
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_381"});
                            return;
                        }
                        //[画面Bのみ]遅刻、早退、遅刻2、早退2のチェックがある遅刻時刻、早退時刻は入力必須(#Msg_470#)
                        if(self.late1() 
                            && self.early1() 
                            && self.late2() 
                            && self.early2()
                            && (self.lateTime1() == null || self.earlyTime1() == null)){
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_470"});
                            return;                            
                        }
                            
                        prePostAtr = 0;
                    }
                    nts.uk.ui.block.invisible();
                    let txtReasonTmp = self.selectedCode();
                    if(!nts.uk.text.isNullOrEmpty(self.selectedCode())){
                        let reasonText = _.find(self.ListTypeReason(),function(data){return data.reasonID == self.selectedCode()});
                        txtReasonTmp = reasonText.reasonTemp;
                    }
                    if(!appcommon.CommonProcess.checklenghtReason(!nts.uk.text.isNullOrEmpty(txtReasonTmp) ? txtReasonTmp + "\n" + self.appreason() : self.appreason(),"#appReason")){
                        return;
                    }
                    let lateOrLeaveEarly: LateOrLeaveEarly = {
                        prePostAtr: prePostAtr, 
                        applicationDate: self.date(),
                        sendMail: self.sendMail(),
                        late1: self.late1() ? 1 : 0,
                        lateTime1: self.lateTime1(),
                        early1: self.early1() ? 1 : 0,
                        earlyTime1: self.earlyTime1(),
                        late2: self.late2() ? 1 : 0,
                        lateTime2: self.lateTime2(),
                        early2: self.early2() ? 1 : 0,
                        earlyTime2: self.earlyTime2(),
                        reasonTemp: txtReasonTmp,
                        appReason: self.appreason()
                    };
                    service.createLateOrLeaveEarly(lateOrLeaveEarly).done((data) => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            if(data.autoSendMail){
                                nts.uk.ui.dialog.info({ messageId: 'Msg_392', messageParams: data.autoSuccessMail }).then(() => {
                                    location.reload();
                                });    
                            } else {
                                if(self.checkBoxValue()){
                                    let command = {appID: data.appID};
                                    setShared("KDL030_PARAM", command);
                                    nts.uk.ui.windows.sub.modal("/view/kdl/030/a/index.xhtml").onClosed(() => {
                                        location.reload();
                                    });    
                                } else {
                                    location.reload();
                                }   
                            }
                        });
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function(){nts.uk.ui.block.clear();});  
                    });
    
                }
            }
        }
    }

    interface TypeReason {
        reasonID: string;
        reasonTemp: string;
    }

    interface LateOrLeaveEarly {
        applicantName: string;
        applicationDate: string;
        sendMail: boolean
        late1: number;
        lateTime1: number;
        early1: number;
        earlyTime1: number;
        late2: number;
        lateTime2: number;
        early2: number;
        earlyTime2: number;
        reasonTemp: string;
        appReason: string;
        prePostAtr: number;
        appApprovalPhaseCmds: Array<any>;
    }
}