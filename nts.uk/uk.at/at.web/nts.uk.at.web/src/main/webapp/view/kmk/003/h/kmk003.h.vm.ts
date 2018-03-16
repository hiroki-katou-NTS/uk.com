module nts.uk.at.view.kmk003.h {

    export module viewmodel {  
        
        export class ScreenModel {        
            
            // Screen data 
            value1: any;
            value2: any;
            value3: any;
            value4: any;
            value5: any;
            value6: any;
            value7: any;
            
            constructor() {
                let self = this;
                self.value1 = ko.observable(1);
                self.value2 = ko.observable(1);
                self.value3 = ko.observable(1);
                self.value4 = ko.observable(1);
                self.value5 = ko.observable(1);
                self.value6 = ko.observable(1);
                self.value7 = ko.observable(1);
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