module nts.uk.com.view.cmm011.d {
    export module viewmodel {
        
        export class ScreenModel {
            
            startDate: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.startDate = ko.observable(null);
            }
            
            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            /**
             * execution
             */
            public execution() {
                let self = this;
                
                if (!self.validate()) {
                    return;
                }
                // TODO: save database
//                let startDateLatest: string = nts.uk.ui.windows.getShared("StartDateLatestHistory");
                
                // valid start date
//                let latestDate: Date = new Date(startDateLatest);
//                let inputDate: Date = new Date(self.startDate());
//                if (inputDate <= latestDate) {
//                    nts.uk.ui.dialog.alertError({ messageId: "Msg_102" }).then(() => { 
//                        nts.uk.ui.windows.close();
//                    });
//                    return;
//                }
                nts.uk.ui.windows.close();
            }
            
            /**
             * validate
             */
            private validate(): boolean {
                let self = this;
                
                // clear all error
                self.clearError();
                
                $('#start-date').ntsEditor('validate');
                
                if ($('.nts-input').ntsError('hasError')) {
                    return false;
                }
                return true;
            }
            
            /**
             * clearError
             */
            private clearError() {
                $('#start-date').ntsError('clear');
            }
            
            /**
             * close
             */
            public close() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * showMessageError
             */
            private showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
    }
}