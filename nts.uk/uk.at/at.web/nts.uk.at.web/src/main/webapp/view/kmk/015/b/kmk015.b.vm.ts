module nts.uk.at.view.kmk015.b {
    export module viewmodel {
        export class ScreenModel {
            
            periodStart: KnockoutObservable<moment.Moment>;
            periodEnd: KnockoutObservable<moment.Moment>;
            isSubmit: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.periodStart = ko.observable(moment(null));
                self.periodEnd = ko.observable(moment(null));
            }
            
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                dfd.resolve();

                return dfd.promise();
            }
            
            /**
             * Submit.
             */
            public submit() {
                let self = this;
                let dfd = $.Deferred<void>();
                
                $("#inp-period-startYMD").ntsEditor("validate");
                $("#inp-period-endYMD").ntsEditor("validate");
                
                if (nts.uk.ui.errors.hasError()) {
                    return;                   
                }

                if (self.periodStart().isAfter(self.periodEnd())) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_917" });
                    return;
                }

                // Loading, block ui.
                nts.uk.ui.block.invisible();

                // Set shared data.
                let time = self.periodStart().format("YYYY/MM/DD") + ' ~ ' + self.periodEnd().format("YYYY/MM/DD");
                let returnedData = {
                    isCreated: true,
                    timeHistory: time,
                    start: self.periodStart(),
                    end: self.periodEnd()
                };
                nts.uk.ui.windows.setShared("childData", returnedData, false);

                // Close dialog.
                self.closeDialog();

                return dfd.promise();
            }
            
            //close dialog
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
    }
}