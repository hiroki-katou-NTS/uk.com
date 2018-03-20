module nts.uk.at.view.kmk011.f {
    
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    import CreateHistoryCommand = nts.uk.at.view.kmk011.f.model.CreateHistoryCommand;
    
    export module viewmodel {
        export class ScreenModel {
            //date range
            startDate: KnockoutObservable<string>;
            endDate: KnockoutObservable<string>;
            
            // radio group
            itemListRadio: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            enableRadio: KnockoutObservable<boolean>;
            
            constructor(){
                let _self = this;
                
                //date range
                _self.startDate = ko.observable(null);
                _self.endDate = ko.observable(null);
                
                //radio group
                _self.itemListRadio = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText("KMK011_73")),
                    new BoxModel(1, nts.uk.resource.getText("KMK011_29"))
                ]);
                _self.selectedId = ko.observable(1);
                _self.enableRadio = ko.observable(true);
                
            }
            
            public start_page() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            public execution() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                if(_self.hasError()){
                    return;    
                }
                
                if (_self.selectedId() == CreateMode.NEW){
                    var data = new CreateHistoryCommand(null,_self.startDate(), _self.endDate(), false);
                    service.save(data).done(() => {
                         nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            dfd.resolve();
                            nts.uk.ui.windows.close();
                         });
                    }).fail((res: any) => {
                          _self.showMessageError(res);  
                    });
                } else {
                    var data = new CreateHistoryCommand(null,_self.startDate(), _self.endDate(), true);
                    service.save(data).done(() => {
                         nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            dfd.resolve();
                            nts.uk.ui.windows.close();
                         });
                    }).fail((res: any) => {
                          _self.showMessageError(res);  
                    });
                }
              
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
        
        export class BoxModel {
            id: number;
            name: string;
            constructor(id: number, name: string){
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
        
        export class CreateMode {
            static COPY_DATA= 0;
            static NEW = 1;
        }
    }
}