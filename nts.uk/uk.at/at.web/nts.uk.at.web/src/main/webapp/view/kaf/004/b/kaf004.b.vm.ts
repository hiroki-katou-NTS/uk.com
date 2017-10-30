module nts.uk.at.view.kaf004.b.viewmodel {
    import kaf002 = nts.uk.at.view.kaf002;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    
    const employmentRootAtr: number = 1; // EmploymentRootAtr: Application
    const applicationType: number = 9; // Application Type: Stamp Application
    
    export class ScreenModel {
        // date editor
        date: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
        //latetime editor
        lateTime1: KnockoutObservable<number> = ko.observable(null);
        lateTime2: KnockoutObservable<number> = ko.observable(null);
        //check send mail
        sendMail: KnockoutObservable<boolean> = ko.observable(true);
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
        approvalList: Array<vmbase.AppApprovalPhase> = [];
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
                    moment.utc().format("YYYY/MM/DD")).done(() => {
                        
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                    });
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.getByCode("").done(function(data) {
                self.ListTypeReason(data.listApplicationReasonDto);
                self.displayOrder(data.workManagementMultiple.useATR);
                self.applicantName(data.applicantName);
                dfd.resolve(data);

            });

            return dfd.promise();
        }


        /** Create Button Click */
        registryButtonClick() {
            var self = this;

            $("#inpLate1").trigger("validate");
            $("#inpEarlyTime1").trigger("validate");
            $("#inpLate2").trigger("validate");
            $("#inpEarlyTime2").trigger("validate");
            
            if (!nts.uk.ui.errors.hasError()) {
               /**  0: 事前の受付制限
                    1: 事後の受付制限
                */ 
                var prePostAtr = 1;
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
                var lateOrLeaveEarly: LateOrLeaveEarly = {
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
                    reasonTemp: self.selectedCode(),
                    appReason: self.appreason(),
                    appApprovalPhaseCmds: self.kaf000_a2.approvalList
                };
                service.createLateOrLeaveEarly(lateOrLeaveEarly).done((data) => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                        /** Clear screen after Registry*/
                        self.date(moment().format('YYYY/MM/DD'));
                        self.late1(null);
                        self.late2(null);
                        self.lateTime1(null);
                        self.lateTime2(null);
                        self.early1(null);
                        self.early2(null);
                        self.earlyTime1(null);
                        self.earlyTime2(null);
                        self.appreason(null);
                    });
                }).fail((res) => {
                    nts.uk.ui.dialog.alertError(res);
                });

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
    }
}