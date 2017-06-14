module ccg018.a.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;

        constructor() {
            var self = this;

            self.date = ko.observable(new Date().toLocaleDateString());
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        openToScreenC() { }
    }
}