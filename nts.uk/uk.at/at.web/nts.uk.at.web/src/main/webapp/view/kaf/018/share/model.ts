module nts.uk.at.view.kaf018.share.model {

    export class MailTemp {
        mailSubject: KnockoutObservable<string>;
        mailContent: KnockoutObservable<string>;
        urlApprovalEmbed: KnockoutObservable<boolean>;
        urlDayEmbed: KnockoutObservable<boolean>;
        urlMonthEmbed: KnockoutObservable<boolean>;
        mailType: number;
        editMode: KnockoutObservable<boolean>;

        constructor(mailType: number, mailSubject: string, mailContent: string, urlApprovalEmbed: number, urlDayEmbed: number, urlMonthEmbed: number, editMode: number) {
            this.mailType = mailType;
            this.mailSubject = ko.observable(mailSubject);
            this.mailContent = ko.observable(mailContent);
            this.urlApprovalEmbed = ko.observable(urlApprovalEmbed == 0 ? false : true);
            this.urlDayEmbed = ko.observable(urlDayEmbed == 0 ? false : true);
            this.urlMonthEmbed = ko.observable(urlMonthEmbed == 0 ? false : true);
            this.editMode = ko.observable(editMode == 0 ? false : true);
        }
    }

    export class IdentityProcessUseSet {
        useIdentityOfMonth: KnockoutObservable<boolean>;

        constructor(useIdentityOfMonth: boolean) {
            this.useIdentityOfMonth = ko.observable(useIdentityOfMonth);
        }
    }

    export class ApprovalProcessingUseSetting {
        useMonthApproverComfirm: KnockoutObservable<boolean>;
        useDayApproverConfirm: KnockoutObservable<boolean>;

        constructor(useDayApproverConfirm: boolean, useMonthApproverComfirm: boolean) {
            this.useMonthApproverComfirm = ko.observable(useMonthApproverComfirm);
            this.useDayApproverConfirm = ko.observable(useDayApproverConfirm);
        }
    }
}