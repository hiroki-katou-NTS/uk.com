module nts.uk.com.view.cmm013.c {

    export module viewmodel {
       
        import SequenceMaster = base.SequenceMaster;
        
        export class ScreenModel {
            
            columns: KnockoutObservableArray<any>;    //nts.uk.ui.NtsGridListColumn       
            items: KnockoutObservableArray<SequenceMaster>;        
            currentCode: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                
                _self.items = ko.observableArray([]);
                _self.currentCode = ko.observable(null);
                
                _self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('CMM013_23'), key: 'sequenceCode', width: 75},
                    { headerText: nts.uk.resource.getText('CMM013_24'), key: 'sequenceName', width: 135}
                ]); 
            }
            
            startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
               
                // Load sequence data list
                _self.loadSequenceList()
                    .done((data: SequenceMaster[]) => {                        
                        if (data && data.length > 0) {
                            // Load data
                            _self.items(data);
                            _self.currentCode(data[0].sequenceCode);                           
                        } else {
                            // No data - Error msg_571
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_571" }).then(() => {
                                // Load sequence register screen
                                nts.uk.ui.windows.sub.modal('/view/cmm/013/f/index.xhtml').onClosed(() => {
                                    _self.loadSequenceList()
                                        .done((data: SequenceMaster[]) => {                        
                                            if (data && data.length > 0) {
                                                // Load data
                                                _self.items(data);
                                                _self.currentCode(data[0].sequenceCode);    
                                            }                       
                                        })                                        
                                        .fail((res: any) => {
                            
                                        });
                                });                      
                            });                                     
                        }                                                 
                        dfd.resolve();
                    })
                    .fail((res: any) => {
                        
                    });
                
                return dfd.promise();
            }
            
            /**
             * Load all sequence
             */
            public loadSequenceList(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                service.findAllSequenceMaster()
                    .done((data: SequenceMaster[]) => {                                                                       
                        dfd.resolve(data);
                    })
                    .fail((res: any) => {
                        dfd.fail(res);
                    });
                return dfd.promise();
            }
            
            /**
             * Select sequence master
             */
            public selectSequence(): void {               
                let _self = this;               
                if (_self.currentCode()) {
                    service.findBySequenceCode(_self.currentCode())
                        .done((data: SequenceMaster) => {                        
                            nts.uk.ui.windows.setShared("ShareDateScreenC", data);
                            _self.close();
                        })
                        .fail((res: any) => {
                            nts.uk.ui.windows.setShared("ShareDateScreenC", null);
                            _self.close();
                        });
                } else {
                    nts.uk.ui.windows.setShared("ShareDateScreenC", null);
                    _self.close();                                
                }
            }
            
            /**
             * close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
        }
    }    
}