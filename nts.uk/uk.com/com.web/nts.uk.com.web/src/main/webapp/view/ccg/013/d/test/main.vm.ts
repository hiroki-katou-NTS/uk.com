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
            var titleBar = {
                name: "タイトルバー",
                backgroundColor: "#70ad47",
                textColor: "#FFF"    
            }
            nts.uk.ui.windows.setShared("titleBar", titleBar);
            nts.uk.ui.windows.sub.modeless("/view/ccg/013/d/index.xhtml", {title: ""});
            
            nts.uk.ui.windows.getShared("CCG013D_MENUS");
        }
    }
 }