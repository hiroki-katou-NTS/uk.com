 module nts.uk.com.view.ccg013.d.test.viewmodel {  
    export class ScreenModel {
        
        constructor() {
            
        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }
        
        openDialog(){
            nts.uk.ui.windows.sub.modeless("/view/ccg/013/d/index.xhtml", {title: ""});
        }
    }
 }