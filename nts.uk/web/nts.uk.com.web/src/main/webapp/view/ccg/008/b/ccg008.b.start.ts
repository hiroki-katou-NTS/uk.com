__viewContext.ready(function () {
    class ScreenModel {
                
        constructor() {
            
        }
        closeDialog() {
             nts.uk.ui.windows.close();   
        }        
        positionHis() {
             nts.uk.ui.windows.close();   
        }
        openTopPageSet(): void{
            nts.uk.ui.windows.sub.modeless("/view/ccg/008/c/index.xhtml");
        }
        openMyPageSet(): void{
            nts.uk.ui.windows.sub.modeless("/view/ccg/031/a/index.xhtml");
        }
    }
    
    this.bind(new ScreenModel());
});
