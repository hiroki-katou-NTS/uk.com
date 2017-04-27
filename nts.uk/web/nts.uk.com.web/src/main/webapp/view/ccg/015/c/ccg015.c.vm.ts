module nts.uk.pr.view.ccg015.c {
    export module viewmodel {
        export class ScreenModel {
            code : KnockoutObservable<string>;
            name : KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.code = ko.observable("code");
                self.name = ko.observable("name");
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