 module nts.uk.com.view.ccg013.e.test.viewmodel {  
    export class ScreenModel {
        
        
        constructor() {
            var self = this;

        }
        
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }
        
        openDialog(){
            
            nts.uk.ui.windows.sub.modeless("/view/ccg/013/i/index.xhtml", {title: "メニューバーの編集"});
            
            nts.uk.ui.windows.getShared("CCG013I_MENU_BAR");
        }
    }
 }