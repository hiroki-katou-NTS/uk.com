module nts.uk.com.view.cmm013.d {

    export module viewmodel {
        
        import Constants = base.Constants;               
        
        export class ScreenModel {
            
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                                
                _self.startDate = ko.observable("");
                _self.endDate = ko.observable(nts.uk.resource.getText("CMM013_31"));
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * Execution
             */
            public execution(): void {
                let _self = this;
                if (!_self.validate()) {
                    return;
                }               
                nts.uk.ui.block.grayout();
                service.saveJobTitleHistory(_self.toJSON())
                    .done(() => {
                        nts.uk.ui.block.clear();
                        nts.uk.ui.windows.setShared(Constants.SHARE_OUT_DIALOG_ADD_HISTORY, true);
                        _self.close();
                    })
                    .fail((res: any) => {
                        nts.uk.ui.block.clear();
                        _self.showErrorMessage(res);
                    });
            }
            
            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
            
            /**
             * toJSON
             */
            private toJSON(): any {
                let _self = this;
                let jobTitleId: string = nts.uk.ui.windows.getShared(Constants.SHARE_IN_DIALOG_ADD_HISTORY);
                if (!jobTitleId) {
                    return {};
                }
                return {
                    isAddMode: true,
                    jobTitleId: jobTitleId,
                    jobTitleHistory: {
                        historyId: "",
                        period: {
                            startDate: _self.startDate(),
                            endDate: new Date("9999-12-31")
                        }
                    }
                }
            }
            
            /**
             * Validate
             */
            private validate(): boolean {
                let _self = this;               
                $('#start-date').ntsError('clear');                
                $('#start-date').ntsEditor('validate');               
                return !$('.nts-input').ntsError('hasError');
            }
            
            /**
             * Show Error Message
             */
            private showErrorMessage(res: any): void {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }           
        }
    }    
}