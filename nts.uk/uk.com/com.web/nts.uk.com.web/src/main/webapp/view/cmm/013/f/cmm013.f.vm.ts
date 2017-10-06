module nts.uk.com.view.cmm013.f {

    export module viewmodel {
        
        import SequenceMaster = base.SequenceMaster;
        import SequenceMasterSaveCommand = base.SequenceMasterSaveCommand;
        
        export class ScreenModel {
            
            // Store create/update mode
            createMode: KnockoutObservable<boolean>;
            
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;            
            items: KnockoutObservableArray<SequenceMaster>;        
            currentCode: KnockoutObservable<string>; 
            
            sequenceCode: KnockoutObservable<string>;
            sequenceName: KnockoutObservable<string>;
            
            // UI binding
            enable_F1_1: KnockoutObservable<boolean>;
            enable_F1_4: KnockoutObservable<boolean>;
            enable_F3_2: KnockoutObservable<boolean>;
            
            constructor() {
                let _self = this;
                
                _self.createMode = ko.observable(null);
                _self.createMode.subscribe((newValue) => {
                    _self.changeMode(newValue);
                });
                
                _self.items = ko.observableArray([]);
                _self.currentCode = ko.observable(null);
                _self.currentCode.subscribe((newValue) => {
                    _self.changeInput(newValue);
                });
                
                _self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('CMM013_23'), key: 'sequenceCode', width: 75},
                    { headerText: nts.uk.resource.getText('CMM013_24'), key: 'sequenceName', width: 135}
                ]); 
                
                _self.sequenceCode = ko.observable("");
                _self.sequenceName = ko.observable("");        
                
                // UI
                _self.enable_F1_1 = ko.observable(null);
                _self.enable_F1_4 = ko.observable(null);
                _self.enable_F3_2 = ko.observable(null);
            }
            
            /**
             * Run after page loaded
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                // Load sequence data list
                _self.loadSequenceList()
                    .done((data: SequenceMaster[]) => {                        
                        if (data && data.length > 0) {
                            // Update mode
                            _self.createMode(false);
                            _self.items(data);
                            _self.currentCode(data[0].sequenceCode);
                        } else {
                            // Create mode
                            _self.createMode(true);
                            //TODO
                            
                        }                                                 
                        dfd.resolve();
                    })
                    .fail((res: any) => {
                        //TODO error
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
             * Callback: change mode based on createMode value
             */
            public changeMode(newValue: boolean) {
                let _self = this;       
                _self.enable_F1_1(!newValue);
                _self.enable_F1_4(!newValue);
                _self.enable_F3_2(newValue);
            }
            
            /**
             * Callback: change form input value based on currentCode value
             */
            public changeInput(newValue: string) {
                let _self = this;               
                if (newValue) {
                    // Find sequence by sequence code
                    service.findBySequenceCode(newValue)
                        .done((data: SequenceMaster) => {                        
                            if (data) {                              
                                // Found sequence         
                                _self.createMode(false);                                                     
                                _self.sequenceCode(data.sequenceCode);
                                _self.sequenceName(data.sequenceName);    
                            } else {                               
                                // Sequence not found
                                _self.sequenceCode("");
                                _self.sequenceName("");   
                                //TODO
                                
                            }
                        })
                        .fail((res: any) => {
                            //TODO error
                        });                                   
                } else {
                    // No value, remove data
                    _self.sequenceCode("");
                    _self.sequenceName("");    
                }                     
            }
            
            /**
             * Start create mode
             */
            public startCreateMode() {
                let _self = this;       
                _self.createMode(true);
                _self.currentCode(null);
            }
            
            public save() {
                let _self = this;
                if (_self.createMode()) {
                    // Create mode
                    let newItem: SequenceMaster = new SequenceMaster("", _self.sequenceCode(), _self.sequenceName(), 0);
                    let newCommand: SequenceMasterSaveCommand = new SequenceMasterSaveCommand(_self.createMode(), newItem);
                    
                    service.saveSequenceMaster(newCommand)
                        .done((data: any) => {   
                                         
                            //TODO show msg 15
                            _self.loadSequenceList()
                                .done((dataList: SequenceMaster[]) => {                        
                                    if (dataList && dataList.length > 0) {
                                        // Update mode
                                        _self.createMode(false);
                                        _self.items(dataList);
                                        _self.currentCode(newCommand.sequenceMasterDto.sequenceCode);  
                                    } else {
                                        // Create mode
                                        _self.createMode(true);
                                        //TODO
                                        
                                    }              
                                })
                                .fail((res: any) => {
                                    //TODO error
                                });
                        })
                        .fail((res: any) => {
                            //TODO error
                        });                      
                } else {
                    // Update mode
                    
                    
                    
                    
                }
                //TODO
            }
            
            /**
             * Close this dialog
             */
            public close() {
                nts.uk.ui.windows.close();
            }
        }
    }    
}