module kml002.k.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
        readonly: KnockoutObservable<boolean>;
        time: KnockoutObservable<number>;
        time2: KnockoutObservable<number>;


        constructor() {
            var self = this;

            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
            self.time = ko.observable(1200);
            self.time2 = ko.observable(1200);

        }
        start() {
            var self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

    }
}