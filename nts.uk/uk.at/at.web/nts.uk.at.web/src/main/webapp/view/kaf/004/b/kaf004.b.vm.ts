module nts.uk.at.view.kaf004.b.viewmodel {
    import kaf002 = nts.uk.at.view.kaf002;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
    
    const employmentRootAtr: number = 1; // EmploymentRootAtr: Application
    const applicationType: number = 9; // Application Type: Stamp Application
    
    export class ScreenModel {
        // date editor
        date: KnockoutObservable<string>;
        //latetime editor
        lateTime1: KnockoutObservable<number>;
        lateTime2: KnockoutObservable<number>;
        //check send mail
        sendMail: KnockoutObservable<boolean>;
        //check late
        late1: KnockoutObservable<boolean>;
        late2: KnockoutObservable<boolean>;
        //check early
        early1: KnockoutObservable<boolean>;
        early2: KnockoutObservable<boolean>;
        //labor time
        earlyTime1: KnockoutObservable<number>;
        earlyTime2: KnockoutObservable<number>;
        //combobox
        ListTypeReason: KnockoutObservableArray<TypeReason>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        //MultilineEditor
        appreason: KnockoutObservable<string>;
        time: KnockoutObservable<string>;
        //Show Screen
        showScreen: string;
        employeeID: string = '';
        applicantName: KnockoutObservable<string> = ko.observable("");
        approvalList: Array<vmbase.AppApprovalPhase> = [];
        kaf000_a2: nts.uk.at.view.kaf000.a.viewmodel.ScreenModel;

        //Chua lay dc thong tin 
        fixtime1: KnockoutObservable<string>;
        fixtime2: KnockoutObservable<string>;
        //DisplayOrder
        displayOrder: KnockoutObservable<number>;
                
        constructor() {
            var self = this;
            //check sendMail
            self.sendMail = ko.observable(true);
            //date editor
            self.date = ko.observable("");
            //time editor
            self.lateTime1 = ko.observable(null);
            self.lateTime2 = ko.observable(null);
            //check late
            self.late1 = ko.observable(false);
            self.late2 = ko.observable(false);
            // check early
            self.early1 = ko.observable(false);
            self.early2 = ko.observable(false);
            //labor time 
            self.earlyTime1 = ko.observable(null);
            self.earlyTime2 = ko.observable(null);
            //combobox
            self.ListTypeReason = ko.observableArray([]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(1);
            self.selectedCode = ko.observable('0002');
            //MultilineEditor 
            self.appreason = ko.observable('');

            self.displayOrder = ko.observable(0);
            //Show Screen
            self.showScreen = __viewContext.transferred.value.showScreen;
            /////////////////fix cứng time//////////////////////////////
            self.fixtime1 = ko.observable("08:30 ~ 12:00     13:00 ~ 17:30");
            self.fixtime2 = ko.observable("10:30 ~ 11:00     14:00 ~ 17:00");
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

            $(".nts-input").trigger("validate");
            if (!$(".nts-input").ntsError("hasError")) {
               /**  0: 事前の受付制限
                    1: 事後の受付制限
                */ 
                var prePostAtr = 1;
                if (self.showScreen == 'B')
                    {prePostAtr = 0;}
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
                        self.date(null);
                        self.late1(null);
                        self.late2(null);
                        self.lateTime1(null);
                        self.lateTime2(null);
                        self.early1(null);
                        self.early2(null);
                        self.earlyTime1(null);
                        self.earlyTime2(null);
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