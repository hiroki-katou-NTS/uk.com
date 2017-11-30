module nts.uk.com.view.cmm007.e {
    
    import OvertimeWorkFrameDto = model.OvertimeWorkFrameDto;
    import OvertimeWorkFrameSaveCommand = model.OvertimeWorkFrameSaveCommand;
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            mapObj: KnockoutObservable<Map<number, model.OvertimeWorkFrameDto>>;
            
            constructor(){
                let _self = this;
                _self.mapObj = new Map<number, model.OvertimeWorkFrameDto>();
                
            }
            
             /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                let dfd = $.Deferred<void>();
                let _self = this;
                
                _self.findAll().done(() => {
                    dfd.resolve();
                });

                return dfd.promise();
            }
            
            /**
             * Save overtime work frame setting
             */
            public saveOvertimeWorkFrSetting(): JQueryPromise<void> {
                let _self = this;
                var dfd = $.Deferred<void>();
               
               let arrDto = new Array<model.OvertimeWorkFrameDto>();
                
                _self.mapObj.forEach((value: number, key: model.OvertimeWorkFrameDto) => {
                    arrDto.push(value); 
                });
                
                
                service.saveOvertimeWorkFrame(arrDto).done(() => {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_15" }).then(() => {
                    });
                    dfd.resolve();
                    
                }).fail(function() {
//                    console.log("fail");
                });
                  
                return dfd.promise();
            }
            
            public findAll(): JQueryPromise<void> {
                let _self = this;
                let dfd = $.Deferred<void>();
                service.findListOvertimeWorkFrame().done((data) => {
                    let objTemp: model.OvertimeWorkFrameDto;
                    
                    _.forEach(data, function(value) {
                        objTemp = new model.OvertimeWorkFrameDto(value.overtimeWorkFrNo, value.overtimeWorkFrName, value.transferFrName, value.useAtr)
                        _self.mapObj.set(value.overtimeWorkFrNo, objTemp);  
                    });
                    
                    for (let i=1; i<=10; i++) {
                        if (_self.mapObj.get(i) == null || typeof _self.mapObj.get(i) == undefined) {
                            _self.mapObj.set(i, new model.OvertimeWorkFrameDto());
                        }   
                    }
                    
                    dfd.resolve();
                    
                }).fail(function(data) {
//                    console.log(data);
                });
                return dfd.promise();
            }
        
            public checkStatusEnable(value): boolean {
                let _self = this;
                return _self.mapObj.get(value).useAtr() == 1 ? true : false;
            }
        
            public myFunction(value): void {
                let _self = this;
                _self.mapObj.get(value).useAtr() == 1 ? _self.mapObj.get(value).useAtr(0) : _self.mapObj.get(value).useAtr(1);
            }
       }
    }
    
     module USE_CLASSIFICATION {
        export const NOT_USE: number = 0;                        
        export const USE: number = 1;                           
    }
}