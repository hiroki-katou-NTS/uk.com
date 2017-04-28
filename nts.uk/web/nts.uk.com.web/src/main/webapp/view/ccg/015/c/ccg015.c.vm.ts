module nts.uk.pr.view.ccg015.c {
    export module viewmodel {
        export class ScreenModel {
            parentTopPageCode : KnockoutObservable<string>;
            parentTopPageName : KnockoutObservable<string>;
            newTopPageCode : KnockoutObservable<string>;
            newTopPageName : KnockoutObservable<string>;
            constructor(topPageCode: string,topPageName: string) {
                var self = this;
                self.parentTopPageCode = ko.observable(topPageCode);
                self.parentTopPageName = ko.observable(topPageName);
                self.newTopPageCode = ko.observable("");
                self.newTopPageName = ko.observable("");
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}