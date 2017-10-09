module nts.uk.com.view.cmm013.b {

    export module viewmodel {
       
        export class ScreenModel {
            
            constructor() {
                let _self = this;
                
            }
            
            startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                
                //TODO
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * close
             */
            public close() {
                nts.uk.ui.windows.close();
            }
        }
    }    
}