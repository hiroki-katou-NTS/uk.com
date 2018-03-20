module nts.uk.at.view.kmk003.f {

    export module viewmodel {  
        
        export class ScreenModel {        
            
            // Screen data 
            oneDay: KnockoutObservable<number>;
            morning : KnockoutObservable<number>;
            afternoon: KnockoutObservable<number>;
            beforeUpdateWorkTimeOption: KnockoutObservable<any>;
            constructor() {
                let self = this;
                self.oneDay = ko.observable(1);
                self.morning = ko.observable(1);
                self.afternoon = ko.observable(1);
                self.beforeUpdateWorkTimeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                    width: "50"
                }));
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                let dataObject: any = nts.uk.ui.windows.getShared("KMK003_DIALOG_F_INPUT_DATA");
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