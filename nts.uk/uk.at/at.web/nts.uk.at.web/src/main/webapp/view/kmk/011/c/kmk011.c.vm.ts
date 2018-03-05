module nts.uk.at.view.kmk011.c {
  
    export module viewmodel {

        export class ScreenModel {
            constructor() {
                var self = this;   
            }
            
            public start_page() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                dfd.resolve();
                return dfd.promise();
            }
        }
        
    }
}