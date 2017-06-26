module nts.uk.at.view.kmk005.a {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
               
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                return dfd.promise();
            }
            
        }      
    }
}