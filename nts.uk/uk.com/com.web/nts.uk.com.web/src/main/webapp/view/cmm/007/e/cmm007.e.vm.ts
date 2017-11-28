module nts.uk.com.view.cmm007.e {
    
    import OvertimeWorkFrameDto = model.OvertimeWorkFrameDto;
    import OvertimeWorkFrameSaveCommand = model.OvertimeWorkFrameSaveCommand;
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            
            constructor(){
                let _self = this;
                
            }
            
             /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var _self = this;

                dfd.resolve();

                return dfd.promise();
            }
            
            /**
             * Save overtime work frame setting
             */
            public saveOvertimeWorkFrSetting(): JQueryPromise<void> {
                let _self = this;
                var dfd = $.Deferred<void>();
               
                dfd.resolve();
                  
                return dfd.promise();
            }
            
       }
           
    }
    
     module USE_CLASSIFICATION {
        export const NOT_USE: number = 0;                        
        export const USE: number = 1;                           
    }
}