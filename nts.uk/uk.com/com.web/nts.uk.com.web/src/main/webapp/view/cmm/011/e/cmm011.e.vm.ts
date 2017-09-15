module nts.uk.com.view.cmm011.e {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
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
                
                let startDate: string = nts.uk.ui.windows.getShared("StartDateHistory");
                self.startDate(startDate);
                
                dfd.resolve();
                
                return dfd.promise();
            }
            
            /**
             * execution
             */
            public execution() {
                let self = this;
                // valid
                if (!self.validate()) {
                    return;
                }
                // TODO: update history
                nts.uk.ui.windows.close();
            }
            
            /**
             * close
             */
             public close() {
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