module nts.uk.com.view.cmm011.d {
    export module viewmodel {
        
        export class ScreenModel {
            
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            constructor() {
                let self = this;
                self.startDate = ko.observable('');
                //TODO change when update text resource
//                self.endDate = ko.observable(nts.uk.resource.getText("CMM011_27"));
                self.endDate = ko.observable(nts.uk.resource.getText("CMM011_27"));
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
                let data = {
                    workplaceId: nts.uk.ui.windows.getShared("selectedWkpId"),
                    workplaceHistory: [{
                        historyId: '',
                        period: {
                            startDate: self.startDate(),
                            endDate: new Date("9999-12-31")
                        }
                    }]
                }
                if (!self.validate()) {
                    return;
                }
                service.registerWorkplaceHistory(data).done(function(){
                    
                }).fail();//TODO fails show message
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