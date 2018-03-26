module nts.uk.at.view.kaf018.share.model {

    export class MailTemp {
        mailSubject: KnockoutObservable<string>;
        mailContent: KnockoutObservable<string>;
        urlApprovalEmbed: KnockoutObservable<number>;
        urlDayEmbed: KnockoutObservable<number>;
        urlMonthEmbed: KnockoutObservable<number>;
        mailType: number;
        screenEditMode: KnockoutObservable<boolean>;

        constructor(mailType: number, mailSubject: string, mailContent: string, urlApprovalEmbed: number, urlDayEmbed: number, urlMonthEmbed: number, screenEditMode: boolean) {
            this.mailType = mailType;
            this.mailSubject = ko.observable(mailSubject);
            this.mailContent = ko.observable(mailContent);
            this.urlApprovalEmbed = ko.observable(urlApprovalEmbed);
            this.urlDayEmbed = ko.observable(urlDayEmbed);
            this.urlMonthEmbed = ko.observable(urlMonthEmbed);
            this.screenEditMode = ko.observable(screenEditMode);
        }
    }

    export class IdentityProcessUseSet {
        useIdentityOfMonth: KnockoutObservable<number>;

        constructor(useIdentityOfMonth: number) {
            this.useIdentityOfMonth = ko.observable(useIdentityOfMonth);
        }
    }

    export class ApprovalProcessingUseSetting {
        useMonthApproverComfirm: KnockoutObservable<number>;
        useDayApproverConfirm: KnockoutObservable<number>;

        constructor(useDayApproverConfirm: number, useMonthApproverComfirm: number) {
            this.useMonthApproverComfirm = ko.observable(useMonthApproverComfirm);
            this.useDayApproverConfirm = ko.observable(useDayApproverConfirm);
        }
    }
}