module nts.uk.at.view.kmk011.g {
    
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    import CreateHistoryCommand = nts.uk.at.view.kmk011.g.model.CreateHistoryCommand;
    
    export module viewmodel {
        export class ScreenModel {
            //date range
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            
            constructor(){
                let _self = this;
                
                //date range
                _self.startDate = ko.observable(null);
                _self.endDate = ko.observable(null);  
            }
            
            public start_page() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                service.findByHistoryId(nts.uk.ui.windows.getShared('history')).done((res: CreateHistoryCommand) => {
                    _self.startDate(res.startDate);
                    _self.endDate(res.endDate);
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            public execution() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                if(_self.hasError()){
                    return;    
                }
                
                var data = new CreateHistoryCommand(nts.uk.ui.windows.getShared('history'),_self.startDate(), _self.endDate());
                
                service.save(data).done(() => {
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                         dfd.resolve();
                         nts.uk.ui.windows.close()
                     });
                }).fail((res: any) => {
                          _self.showMessageError(res);  
                });
              
                return dfd.promise();
            }
            
            // close modal
            public close() : void {
                nts.uk.ui.windows.close();    
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
            
            /**
             * Check Errors all input.
             */
            private hasError(): boolean {
                let _self = this;
                _self.clearErrors();
                
                $('#end_date').ntsEditor("validate");
                $('#start_date').ntsEditor("validate");    
          
                if ($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
            }

            /**
             * Clear Errors
             */
            private clearErrors(): void {
                let _self = this;
                
                // Clear error
                $('#start_date').ntsEditor("clear");
                $('#end_date').ntsEditor("clear");    
           
                // Clear error inputs
                $('.nts-input').ntsError('clear');
            }
        }
    }
}