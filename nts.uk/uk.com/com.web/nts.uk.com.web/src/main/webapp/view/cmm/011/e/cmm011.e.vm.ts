module nts.uk.com.view.cmm011.e {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
        export class ScreenModel {
            
            wkpId: string;
            historyId: string;
            startDate: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.wkpId = null;
                self.historyId = null;
                self.startDate = ko.observable(null);
            }
            
            /**
             * startPage
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();
                
                let objTransfer: any = nts.uk.ui.windows.getShared("WokplaceHistoryInfor");
                self.wkpId = objTransfer.wkpId;
                self.historyId = objTransfer.historyId;
                self.startDate(objTransfer.startDate);
                
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
                service.saveWorkplaceHistory(self.toJSObject()).done(function(){
                    nts.uk.ui.windows.setShared("ModeUpdateHistory", true);
                    self.close();
                }).fail((res: any) => {
                    self.showMessageError(res);
                });
            }
            
            /**
             * toJSObject
             */
            private toJSObject(): any {
                let self = this;
                return {
                    isAddMode: false,
                    workplaceId: self.wkpId,
                    workplaceHistory: {
                        historyId: self.historyId,
                        period: {
                            startDate: self.startDate(),
                            endDate: new Date("9999-12-31")
                        }
                    }
                }
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
//                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                    nts.uk.ui.dialog.bundledErrors(res); 
                }
            }
        }
    }
}