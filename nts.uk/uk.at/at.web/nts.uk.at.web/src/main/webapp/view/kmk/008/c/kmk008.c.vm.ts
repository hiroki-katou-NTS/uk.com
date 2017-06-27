module nts.uk.at.view.kmk008.c {
    export module viewmodel {
        export class ScreenModel {
            time: KnockoutObservable<string>;
            time34: KnockoutObservable<string>;
            constructor() {
                let self = this;
                self.time = ko.observable("12:00");
                self.time34 = ko.observable("1200");

            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }
    }
}
