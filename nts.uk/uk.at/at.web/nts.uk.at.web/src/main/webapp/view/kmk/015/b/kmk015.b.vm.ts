module nts.uk.at.view.kmk015.b {
    export module viewmodel {
        export class ScreenModel {
            
            periodStart: KnockoutObservable<moment.Moment>;
            periodEnd: KnockoutObservable<moment.Moment>;
            
            constructor() {
                let self = this;
                self.periodStart = ko.observable(moment());
                self.periodEnd = ko.observable(moment());
            }
            
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui
//                nts.uk.ui.block.invisible();
                
                dfd.resolve();

                return dfd.promise();
            }
            
            /**
             * Submit.
             */
            public submit() {
                let self = this;
                let dfd = $.Deferred<void>();

                if (self.periodStart().isAfter(self.periodEnd())) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_917" });
                    return;
                }

                // Loading, block ui.
                nts.uk.ui.block.invisible();

                // Set shared data.
                let time = self.periodStart().format("YYYY/MM/DD") + ' ~ ' + self.periodEnd().format("YYYY/MM/DD");
                let returnedData = {
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