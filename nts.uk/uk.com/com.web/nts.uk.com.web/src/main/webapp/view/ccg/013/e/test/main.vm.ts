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
            var menuData = {
                webMenuId: "940",
                menuName: "の編集"
            }
            nts.uk.ui.windows.setShared("menuData", menuData);
            
            nts.uk.ui.windows.sub.modeless("/view/ccg/013/e/index.xhtml", {title: "他のWebメニューへコピー"});
        }
    }
 }