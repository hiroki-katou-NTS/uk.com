module nts.uk.pr.view.ccg015.c {
    export module viewmodel {
        import aservice = nts.uk.com.view.ccg015.a.service;
        import aserviceDto = nts.uk.com.view.ccg015.a.service.model;
        export class ScreenModel {
            parentTopPageCode: KnockoutObservable<string>;
            parentTopPageName: KnockoutObservable<string>;
            parentLayoutId: KnockoutObservable<string>;
            newTopPageCode: KnockoutObservable<string>;
            newTopPageName: KnockoutObservable<string>;
            isDuplicateCode: KnockoutObservable<boolean>;
            check: KnockoutObservable<boolean>;
            constructor(topPageCode: string, topPageName: string, layoutId: string) {
                var self = this;
                self.parentTopPageCode = ko.observable(topPageCode);
                self.parentTopPageName = ko.observable(topPageName);
                self.parentLayoutId = ko.observable(layoutId);
                self.newTopPageCode = ko.observable("");
                self.newTopPageName = ko.observable("");
                self.isDuplicateCode = ko.observable(false);
                self.check = ko.observable(true);
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                nts.uk.ui.windows.setShared("codeOfNewTopPage", self.parentTopPageCode());
                dfd.resolve();
                return dfd.promise();
            }
            private copyTopPage() {
                var self = this;
                nts.uk.ui.windows.setShared("codeOfNewTopPage", _.escape(self.newTopPageCode()));
                var data: service.TopPageDto = {
                    topPageCode: _.escape(self.newTopPageCode()),
                    topPageName: _.escape(self.newTopPageName()),
                    layoutId: self.parentLayoutId(),
                    languageNumber: 0,
                    isCheckOverwrite: self.check(),
                    copyCode: self.parentTopPageCode()
                };
                service.copyTopPage(data).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_20" }).then(function() {
                        nts.uk.ui.windows.close();
                    }); 
                }).fail(function(res: any) {
                    $("#inp-code").focus();
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds});
                });

            }
        }
    }
}