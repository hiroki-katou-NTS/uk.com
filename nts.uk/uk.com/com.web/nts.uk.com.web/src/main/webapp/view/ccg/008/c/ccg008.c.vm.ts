module nts.uk.com.view.ccg008.c {
    export module viewmodel {
        export class ScreenModel {
            
            
            constructor() {
                
            }
            
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
        }   
    }
}