module nts.uk.pr.view.ccg007.a {
    export module viewmodel {
        export class ScreenModel {
            contractCode: KnockoutObservable<string>;
            password: KnockoutObservable<string>;
            parentLayoutId: KnockoutObservable<string>;
            newTopPageCode: KnockoutObservable<string>;
            newTopPageName: KnockoutObservable<string>;
            isDuplicateCode: KnockoutObservable<boolean>;
            check: KnockoutObservable<boolean>;
            constructor() {
                var self = this;
                self.contractCode = ko.observable('');
                self.password = ko.observable('');
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
            private AuthContract() {
                var self = this;
            }
        }
    }
}