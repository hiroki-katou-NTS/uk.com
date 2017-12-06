module nts.uk.com.view.cmm011.e {
    export module viewmodel {
        
        import WorkplaceHistory = base.WorkplaceHistoryAbstract;
        import IHistory = nts.uk.com.view.cmm011.base.IHistory;
        
        export class ScreenModel {
            
            wkpId: string;
            historyId: string;
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            
            constructor() {
                let self = this;
                self.wkpId = null;
                self.historyId = null;
                self.startDate = ko.observable(null);
                self.endDate = ko.observable(nts.uk.resource.getText("CMM011_27"));
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
                nts.uk.ui.block.grayout();
                service.saveWorkplaceHistory(self.toJSObject()).done(function(){
                    nts.uk.ui.block.clear();
                    nts.uk.ui.windows.setShared("ModeUpdateHistory", true);
                    self.close();
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
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
                            endDate: new Date(self.endDate())
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
            private validate() {
                let self = this;

                // clear error
                $('#start-date').ntsError('clear');

                // validate
                $('#start-date').ntsEditor('validate');

                return !$('.nts-input').ntsError('hasError');
            }
            
            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                let dfd = $.Deferred<any>();
                
                // check error business exception
                if (!res.businessException) {
                    return;
                }
                
                // show error message
                if (Array.isArray(res.errors)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
    }
}