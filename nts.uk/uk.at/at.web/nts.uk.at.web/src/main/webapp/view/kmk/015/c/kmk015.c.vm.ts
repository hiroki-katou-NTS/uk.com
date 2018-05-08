module nts.uk.at.view.kmk015.c {
    export module viewmodel {
        export class ScreenModel {
            
            periodStart: KnockoutObservable<moment.Moment>;
            periodEnd: KnockoutObservable<moment.Moment>;
            
            callerParameter: CallerParameter;
            
            constructor(parentData: CallerParameter) {
                let self = this;
                self.periodStart = ko.observable(moment());
                self.periodEnd = ko.observable(moment());
                
                self.callerParameter = parentData;
            }
            
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                
                //init data
                self.periodStart(self.callerParameter.startTime);
                self.periodEnd(self.callerParameter.endTime);
                
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

                if (moment(self.periodStart()).isAfter(moment(self.periodEnd()))) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_917" });
                    return;
                }

                // Loading, block ui.
                nts.uk.ui.block.invisible();

                // Set shared data.
                let time = moment(self.periodStart()).format("YYYY/MM/DD") + ' ~ ' + moment(self.periodEnd()).format("YYYY/MM/DD");
                let returnedData = {
                    isCreated: false,
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
        
        interface CallerParameter {
            workTypeCodes: string;
            startTime: moment.Moment;
            endTime: moment.Moment;
        }
    }
}