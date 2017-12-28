module nts.uk.at.view.kmf002.b {
    
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
//               alert("OK_B start page");
                dfd.resolve();
                return dfd.promise();
            }
            
            public screenA(): void {
                alert("ok_A screen");    
            }
          
       }      
    }
}