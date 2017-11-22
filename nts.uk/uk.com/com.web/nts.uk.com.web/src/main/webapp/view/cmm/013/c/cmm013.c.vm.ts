module nts.uk.com.view.cmm013.c {

    export module viewmodel {
       
        import Constants = base.Constants;
        
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
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();

                // Load sequence data list
                nts.uk.ui.block.grayout();
                _self.loadSequenceList()
                    .done((data: SequenceMaster[]) => {         
                        // Load data
                        _self.items(data);
                        _self.currentCode(data[0].sequenceCode);                                                  
                        dfd.resolve();
                    })
                    .fail((res: any) => {
                        dfd.reject(res);
                        //nts.uk.ui.dialog.alertError({ messageId: "Msg_571" }).then(() => {
                        nts.uk.ui.dialog.bundledErrors(res).then(() => {
                            // Load sequence register screen
                            nts.uk.ui.windows.sub.modal('/view/cmm/013/f/index.xhtml').onClosed(() => {
                                // Reload data after register
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
                    })
                    .always(() => {
                        nts.uk.ui.block.clear();       
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
                        dfd.reject(res);
                    });
                return dfd.promise();
            }
            
            /**
             * Select sequence master
             */
            public selectSequence(): void {               
                let _self = this;  
                             
                nts.uk.ui.windows.setShared(Constants.IS_ACCEPT_DIALOG_SELECT_SEQUENCE, true);
                if (_self.currentCode()) {
                    nts.uk.ui.block.grayout();
                    service.findBySequenceCode(_self.currentCode())
                        .done((data: SequenceMaster) => {                         
                            nts.uk.ui.windows.setShared(Constants.SHARE_OUT_DIALOG_SELECT_SEQUENCE, data);
                        })
                        .fail((res: any) => {                             
                            nts.uk.ui.windows.setShared(Constants.SHARE_OUT_DIALOG_SELECT_SEQUENCE, null);                     
                        })
                        .always(() => {
                            nts.uk.ui.block.clear();     
                            nts.uk.ui.windows.close();
                        });
                } else {
                    nts.uk.ui.windows.setShared(Constants.SHARE_OUT_DIALOG_SELECT_SEQUENCE, null);
                    nts.uk.ui.windows.close();                   
                }
            }
            
            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.setShared(Constants.IS_ACCEPT_DIALOG_SELECT_SEQUENCE, false);
                nts.uk.ui.windows.close();
            }           
        }
    }    
}