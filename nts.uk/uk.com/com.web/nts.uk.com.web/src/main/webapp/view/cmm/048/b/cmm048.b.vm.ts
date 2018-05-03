module nts.uk.com.view.cmm048.b {

    export module viewmodel {  
        
        export class ScreenModel {        
            
            
            
            constructor() {
                let _self = this;
                
                
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                
                
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