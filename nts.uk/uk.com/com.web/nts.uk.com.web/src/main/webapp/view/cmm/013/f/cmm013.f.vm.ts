module nts.uk.com.view.cmm013.f {

    export module viewmodel {
        
        import SequenceMaster = base.SequenceMaster;
        import SequenceMasterSaveCommand = base.SequenceMasterSaveCommand;
        import SequenceMasterRemoveCommand = base.SequenceMasterRemoveCommand;
        
        export class ScreenModel {
            
            // Store create/update mode
            createMode: KnockoutObservable<boolean>;
            
            columns: KnockoutObservableArray<any>;  //nts.uk.ui.NtsGridListColumn
            items: KnockoutObservableArray<SequenceMaster>; 
            currentCode: KnockoutObservable<string>; 
            
            sequenceCode: KnockoutObservable<string>;
            sequenceName: KnockoutObservable<string>;
            order: KnockoutObservable<number>;
            
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
                _self.order = ko.observable(0);
                
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
             * Callback: change mode based on createMode value
             */
            public changeMode(newValue: boolean): void {
                let _self = this;       
                _self.enable_F1_1(!newValue);
                _self.enable_F1_4(!newValue);
                _self.enable_F3_2(newValue);
                if (newValue) {
                    _self.sequenceCode("");
                    _self.sequenceName("");   
                }                                
            }
            
            /**
             * Callback: change form input value based on currentCode value
             */
            public changeInput(newValue: string): void {
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
                                _self.order(data.order);
                            } else {                               
                                // Sequence not found
                                _self.sequenceCode("");
                                _self.sequenceName("");   
                            }
                        })
                        .fail((res: any) => {
                            _self.showMessageError(res);
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
            public startCreateMode(): void {
                let _self = this;       
                _self.createMode(true);
                _self.currentCode(null);
            }
            
            /**
             * Save sequence
             */
            public save(): void {
                let _self = this;                  
                
                if (_self.sequenceCode() === "" || _self.sequenceName() === "") {
                    return;
                }
                
                if (_self.createMode()) {
                    // Create mode                                 
                    // Get max order
                    service.findMaxOrder()
                        .done((maxOrder: number) => {
                            let newItem: SequenceMaster = new SequenceMaster("", _self.sequenceCode(), _self.sequenceName(), maxOrder + 1);
                            let newCommand: SequenceMasterSaveCommand = new SequenceMasterSaveCommand(_self.createMode(), newItem);                           
                            _self.saveHandler(newCommand);      
                        });                                    
                } else {
                    // Update mode
                    let updateItem: SequenceMaster = new SequenceMaster("", _self.sequenceCode(), _self.sequenceName(), _self.order());
                    let updateCommand: SequenceMasterSaveCommand = new SequenceMasterSaveCommand(_self.createMode(), updateItem);
                    _self.saveHandler(updateCommand);                                       
                }               
            }
            
            /**
             * Save sequence (call service)
             */
            private saveHandler(command: SequenceMasterSaveCommand): void {
                let _self = this;    
                
                service.saveSequenceMaster(command)
                    .done((data: any) => {   
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });      
                        _self.loadSequenceList()
                            .done((dataList: SequenceMaster[]) => {                        
                                if (dataList && dataList.length > 0) {
                                    // Update mode
                                    _self.createMode(false);
                                    _self.items(dataList);
                                    _self.currentCode(command.sequenceMasterDto.sequenceCode);  
                                } else {
                                    // Create mode
                                    _self.createMode(true);
                                    _self.items([]);
                                    _self.currentCode(null);
                                }                                        
                            })
                            .fail((res: any) => {
                                
                            });
                    })
                    .fail((res: any) => {
                        _self.showMessageError(res);
                    });       
            }
            
            /**
             * Remove sequence
             */
            public remove() {
                let _self = this;   
                if(_self.sequenceCode() !== "") { 
                
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_18" })
                    .ifYes(() => { 
                        // Get item behind removed item
                        let selectedCode: string = null;                    
                        //let currentIndex: number = _self.items().findIndex(item => item.sequenceCode == _self.currentCode()); // ES6 only :(      
                        let currentIndex: number = null;     
                        for (let item of _self.items()) {
                            if (item.sequenceCode === _self.currentCode()) {
                                currentIndex = _self.items.indexOf(item);
                            }
                        }
                        
                        if (currentIndex && _self.items()[currentIndex + 1]) {
                            selectedCode = _self.items()[currentIndex + 1].sequenceCode;
                        } 
                        
                        service.removeSequenceMaster(new SequenceMasterRemoveCommand(_self.sequenceCode()))
                            .done((data: any) => {
                                nts.uk.ui.dialog.info({ messageId: "Msg_16" });      
                                _self.loadSequenceList()
                                .done((dataList: SequenceMaster[]) => {                        
                                    if (dataList && dataList.length > 0) {
                                        // Update mode
                                        _self.createMode(false);
                                        _self.items(dataList);                                   
                                        if (selectedCode) { 
                                            _self.currentCode(selectedCode);
                                        } else {
                                            _self.currentCode(dataList[dataList.length - 1].sequenceCode);  
                                        }                                    
                                    } else {
                                        // Create mode
                                        _self.createMode(true);
                                        _self.items([]);
                                        _self.currentCode(null);
                                    }     
                                })
                                .fail((res: any) => {
                                    
                                });
                            })
                            .fail((res: any) => {
                                _self.showMessageError(res);
                            }); 
                    }).ifNo(() => { 
                        
                    })               
                }
            }
            
            /**
             * Close this dialog
             */
            public close() {
                nts.uk.ui.windows.close();
            }
            
            /**
             * Show message error
             */
            public showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }
        }
    }    
}