module nts.uk.at.view.kmk003.i {

    export module viewmodel {  
        
        export class ScreenModel {        
            
            // Screen data 
            dataObject: any;
            
            // Is flow mode
            isFlow: KnockoutObservable<boolean>;
            
            // Data
            delFromEmTime: KnockoutObservable<boolean>;
            lateStampExactlyTimeIsLateEarly: KnockoutObservable<boolean>;
            leaveEarlyStampExactlyTimeIsLateEarly: KnockoutObservable<boolean>;   
           
            lateGraceTime: KnockoutObservable<number>;
            lateIncludeWorkingHour: KnockoutObservable<boolean>;
            leaveEarlyGraceTime: KnockoutObservable<number>;
            leaveEarlyIncludeWorkingHour: KnockoutObservable<boolean>;
                     
            constructor() {
                let _self = this;
                
                _self.dataObject = null;
                _self.isFlow = ko.observable(null);
                
                _self.delFromEmTime = ko.observable(true);
                _self.lateStampExactlyTimeIsLateEarly = ko.observable(false);
                _self.leaveEarlyStampExactlyTimeIsLateEarly = ko.observable(false);
                _self.lateGraceTime = ko.observable(0);
                _self.lateIncludeWorkingHour = ko.observable(false);
                _self.leaveEarlyGraceTime = ko.observable(0);
                _self.leaveEarlyIncludeWorkingHour = ko.observable(false);                              
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                let dataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_I_INPUT_DATA");
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
                _self.dataObject = dataObject;
                let isFlow: boolean = dataObject.isFlow;
                if (isFlow) {
                    _self.delFromEmTime(true);
                    _self.isFlow(true);                       
                } else {
                    _self.delFromEmTime(dataObject.delFromEmTime);
                    _self.isFlow(false);  
                }
     
                _self.lateStampExactlyTimeIsLateEarly(dataObject.lateStampExactlyTimeIsLateEarly);
                _self.leaveEarlyStampExactlyTimeIsLateEarly(dataObject.leaveEarlyStampExactlyTimeIsLateEarly);
                _self.lateGraceTime(dataObject.lateGraceTime);
                _self.lateIncludeWorkingHour(dataObject.lateIncludeWorkingHour);
                _self.leaveEarlyGraceTime(dataObject.leaveEarlyGraceTime);
                _self.leaveEarlyIncludeWorkingHour(dataObject.leaveEarlyIncludeWorkingHour);
            }   
            
            /**
             * Save
             */
            public save(): void {
                let _self = this;
                if (nts.uk.ui.errors.hasError()) {
                    return;                   
                }
                
                let dataObject: any = {
                    delFromEmTime: _self.delFromEmTime(),       
                    lateStampExactlyTimeIsLateEarly: _self.lateStampExactlyTimeIsLateEarly(),
                    lateGraceTime: nts.uk.util.isNullOrUndefined(_self.lateGraceTime()) ? 0 : _self.lateGraceTime(),
                    lateIncludeWorkingHour: _self.lateIncludeWorkingHour(),
                    leaveEarlyStampExactlyTimeIsLateEarly: _self.leaveEarlyStampExactlyTimeIsLateEarly(),
                    leaveEarlyGraceTime: nts.uk.util.isNullOrUndefined(_self.leaveEarlyGraceTime()) ? 0 : _self.leaveEarlyGraceTime(),
                    leaveEarlyIncludeWorkingHour: _self.leaveEarlyIncludeWorkingHour()
                };
                nts.uk.ui.windows.setShared("KMK003_DIALOG_I_OUTPUT_DATA", dataObject);
                _self.close();
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