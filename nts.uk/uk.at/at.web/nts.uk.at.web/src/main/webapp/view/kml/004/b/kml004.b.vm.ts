import setShared = nts.uk.ui.windows.setShared;
import getShared = nts.uk.ui.windows.getShared;
module nts.uk.at.view.kmk004.b.viewmodel {
    
        export class ScreenModel {
            calDaySet: KnockoutObservable<any>;
            check: KnockoutObservable<boolean>;
            constructor() {
                let self = this;
                self.check = ko.observable(true);
                self.calDaySet = ko.observable(getShared("KML004B_DAY_SET"));
            }


            /**
             * Event on start page.
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getAll().done(function(data) {
                    console.log(data);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Save usage unit setting.
             */
            public save(): void {
                var self = this;
            }

            /**
             * Event on click cancel button.
             */
            public cancel(): void {
                nts.uk.ui.windows.close();
            }
        }
}