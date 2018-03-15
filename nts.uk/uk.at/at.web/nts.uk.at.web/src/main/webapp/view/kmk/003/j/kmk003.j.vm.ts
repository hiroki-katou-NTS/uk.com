module nts.uk.at.view.kmk003.j {

    export module viewmodel {  
        
        export class ScreenModel {        
            
            // Screen data 
            dataObject: any;
            
            // Is flow mode
            isFlow: KnockoutObservable<boolean>;
            
            // Data
                     
            constructor() {
                let _self = this;
                
                _self.dataObject = null;
                _self.isFlow = ko.observable(null);
                
                //TODO
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                let dataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_J_INPUT_DATA");
                _self.bindingData(dataObject);
                
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * Binding data
             */
            private bindingData(dataObject: any) {
                let _self = this;
                
                if (nts.uk.util.isNullOrUndefined(dataObject)) {                                   
                    return;
                }
                
                //TODO
            }   
            
            /**
             * Save
             */
            public save(): void {
                let _self = this;
                
                //TODO
//                let dataObject: any = {
//                    delFromEmTime: _self.delFromEmTime(),       
//                    lateStampExactlyTimeIsLateEarly: _self.lateStampExactlyTimeIsLateEarly(),
//                    lateGraceTime: _self.lateGraceTime(),
//                    lateIncludeWorkingHour: _self.lateIncludeWorkingHour(),
//                    leaveEarlyStampExactlyTimeIsLateEarly: _self.leaveEarlyStampExactlyTimeIsLateEarly(),
//                    leaveEarlyGraceTime: _self.leaveEarlyGraceTime(),
//                    leaveEarlyIncludeWorkingHour: _self.leaveEarlyIncludeWorkingHour()
//                };
//                nts.uk.ui.windows.setShared("KMK003_DIALOG_J_OUTPUT_DATA", dataObject);
//                _self.close();
            }
            
            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }
                       
        }
    }    
}