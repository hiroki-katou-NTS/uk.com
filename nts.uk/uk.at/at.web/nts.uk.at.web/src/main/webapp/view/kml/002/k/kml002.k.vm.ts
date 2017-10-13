module kml002.k.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
        readonly: KnockoutObservable<boolean>;
        startTime: KnockoutObservable<number>;
        endTime: KnockoutObservable<number>;


        constructor() {
            var self = this;

            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
            self.startTime = ko.observable(0);
            self.endTime = ko.observable(0);

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

        submitTime() {
            var self = this;
            var dataTime = {
                startTime: self.startTime(),
                endTime: self.endTime()
            }
            nts.uk.ui.windows.setShared('KML002K_TIME', dataTime);
            nts.uk.ui.windows.close();
        }
        
    }
}