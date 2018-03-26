module nts.uk.at.view.kaf018.h.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;

        appApprovalUnapproved: model.MailTemp;
        dailyUnconfirmByPrincipal: model.MailTemp;
        dailyUnconfirmByConfirmer: model.MailTemp;
        monthlyUnconfirmByConfirmer: model.MailTemp;

        constructor() {
            var self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: text("KAF018_77"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: text("KAF018_78"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: text("KAF018_79"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: text("KAF018_80"), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');

            self.appApprovalUnapproved = new model.MailTemp("appApprovalUnapproved", "content sadsad", true);
            self.dailyUnconfirmByPrincipal = new model.MailTemp("dailyUnconfirmByPrincipal", "content 123123", false);
            self.dailyUnconfirmByConfirmer = new model.MailTemp("dailyUnconfirmByConfirmer", "content 565667", true);
            self.monthlyUnconfirmByConfirmer = new model.MailTemp("monthlyUnconfirmByConfirmer", "content 89898989", false);
        }

        testMail() {
            var self = this;
            console.log(self.appApprovalUnapproved, self.dailyUnconfirmByPrincipal, self.dailyUnconfirmByConfirmer, self.monthlyUnconfirmByConfirmer);
        }

        close() {
            nts.uk.ui.windows.close()
        }
    }

    export module model {
        export class MailTemp {
            mailSubject: KnockoutObservable<string>;
            mailContent: KnockoutObservable<string>;
            urlApprovalEmbed: KnockoutObservable<boolean>;
            /*urlApprovalEmbed: number;
            urlDayEmbed: number;
            urlMonthEmbed: number;*/

            /*constructor(mailSubject: string, mailContent: string, urlApprovalEmbed: number, urlDayEmbed: number, urlMonthEmbed: number) {
                this.mailSubject = ko.observable(mailSubject);
                this.mailContent = ko.observable(mailContent);
                this.urlApprovalEmbed = urlApprovalEmbed;
                this.urlDayEmbed = urlDayEmbed;
                this.urlMonthEmbed = urlMonthEmbed;
            }*/

            constructor(mailSubject: string, mailContent: string, urlApprovalEmbed: boolean) {
                this.mailSubject = ko.observable(mailSubject);
                this.mailContent = ko.observable(mailContent);
                this.urlApprovalEmbed = ko.observable(urlApprovalEmbed);
            }
        }

    }
}