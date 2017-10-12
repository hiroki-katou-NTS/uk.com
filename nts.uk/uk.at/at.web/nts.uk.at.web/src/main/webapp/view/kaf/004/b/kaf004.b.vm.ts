module nts.uk.at.view.kaf004.b.viewmodel {
    import kaf002 = nts.uk.at.view.kaf002;
    import vmbase = nts.uk.at.view.kaf002.shr.vmbase;
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
            self.lateTime1 = ko.observable(0);
            self.lateTime2 = ko.observable(0);
            //check late
            self.late1 = ko.observable(false);
            self.late2 = ko.observable(false);
            // check early
            self.early1 = ko.observable(false);
            self.early2 = ko.observable(false);
            //labor time 
            self.earlyTime1 = ko.observable(0);
            self.earlyTime2 = ko.observable(0);
            //combobox
            self.ListTypeReason = ko.observableArray([]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(1);
            self.selectedCode = ko.observable('0002');
            //MultilineEditor 
            self.appreason = ko.observable('');
            
            self.displayOrder =ko.observable (0);
            //Show Screen
            self.showScreen = __viewContext.transferred.value.showScreen;
            /////////////////fix cứng time//////////////////////////////
            self.fixtime1 = ko.observable("08:30 ~ 12:00     13:00 ~ 17:30");
            self.fixtime2 = ko.observable("10:30 ~ 11:00     14:00 ~ 17:00");
            self.kaf000_a2 = new kaf000.a.viewmodel.ScreenModel();
            self.startPage().done((commonSet: vmbase.AppStampNewSetDto) => {
                self.employeeID = commonSet.employeeID;
                self.applicantName(commonSet.applicantName);
                self.kaf000_a2.start(self.employeeID, 1, 9, moment.utc().format("YYYY/MM/DD")).done(() => {
                    let a = self.kaf000_a2.approvalRoot().beforeApprovers;
                    for (let x = 1; x <= a.length; x++) {
                        let appPhase = a[x - 1];
                        let b = new vmbase.AppApprovalPhase(
                            "",
                            appPhase.approvalForm,
                            x,
                            0,
                            []);
                        for (let y = 1; y <= appPhase.length; y++) {
                            let appFrame = appPhase[y];
                            let c = new vmbase.ApprovalFrame(
                                "",
                                y,
                                []);
                            let d = new vmbase.ApproveAccepted(
                                "",
                                appFrame.sid,
                                0,
                                appFrame.confirmPerson ? 1 : 0,
                                "",
                                "",
                                appFrame.sid);
                            c.approveAcceptedCmds.push(d);
                            b.approvalFrameCmds.push(c);
                        };
                        this.approvalList = b;
                    };
                });
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.getByCode().done(function(data) {
                self.ListTypeReason(data.listApplicationReasonDto);
                self.displayOrder(data.workManagementMultiple);
                dfd.resolve(data);
                
            });

            return dfd.promise();
        }

        openSetting(): void {
            var self = this;
            nts.uk.request.jump("com", "/view/cmm/018/a/index.xhtml", { screen: 'Application', employeeId: self.employeeID });
        }

        /** Create Button Click */
        registryButtonClick() {
            var self = this;

            $(".nts-input").trigger("validate");
            if (!$(".nts-input").ntsError("hasError")) {
                var lateOrLeaveEarly: LateOrLeaveEarly = {
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
                    appReason: self.appreason()
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
    }
}