module nts.uk.at.view.kaf004.e.viewmodel {
    import model = nts.uk.at.view.kaf000.b.viewmodel.model;
    import service = nts.uk.at.view.kaf004.b.service;
    export class ScreenModel extends kaf000.b.viewmodel.ScreenModel {
        // applicantName
        applicantName: KnockoutObservable<string>;
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
        showScreen: KnockoutObservable<string>;
        postAtr: number;
        
        constructor(listAppMetadata: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata) {
            super(listAppMetadata, currentApp);
            var self = this;
            //check sendMail
            self.sendMail = ko.observable(true);
            //date editor
            self.date = ko.observable(new Date());
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
            self.currentCode = ko.observable(3);
            self.selectedCode = ko.observable('0002');
            //MultilineEditor 
            self.appreason = ko.observable('');
            //Show Screen
            self.showScreen = ko.observable('');
            self.startPage();
            self.applicantName = ko.observable("");
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.getByCode(self.appID()).done(function(data) { 
                self.ListTypeReason(data.listApplicationReasonDto);
                self.applicantName(data.applicantName);
                self.selectedCode(data.lateOrLeaveEarlyDto.appReasonID);
                self.appreason(data.lateOrLeaveEarlyDto.appReason);
                self.date(data.lateOrLeaveEarlyDto.applicationDate);
                self.lateTime1(data.lateOrLeaveEarlyDto.lateTime1);
                self.lateTime2(data.lateOrLeaveEarlyDto.lateTime2);
                self.late1(data.lateOrLeaveEarlyDto.late1);
                self.late2(data.lateOrLeaveEarlyDto.late2);
                self.early1(data.lateOrLeaveEarlyDto.early1);
                self.early2(data.lateOrLeaveEarlyDto.early2);
                self.earlyTime1(data.lateOrLeaveEarlyDto.earlyTime1);
                self.earlyTime2(data.lateOrLeaveEarlyDto.earlyTime2);
                self.showScreen(data.lateOrLeaveEarlyDto.postAtr == 1 ? 'F' : '');
                self.postAtr = data.lateOrLeaveEarlyDto.postAtr;
                $("#inputdate").focus();
                dfd.resolve();
            });
               
            return dfd.promise();
        }
        
        update() {
            var self = this;

            $(".nts-input").trigger("validate");
            if (!$(".nts-input").ntsError("hasError")) {
                var lateOrLeaveEarly: LateOrLeaveEarly = {
                    appID: self.appID(),
                    appDate: self.date(),
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
                    appApprovalPhaseCmds: self.approvalList
                };
                service.updateLateOrLeaveEarly(lateOrLeaveEarly).done((data) => {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" });
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
        appID: string;
        applicantName: string;
        appDate: string;
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