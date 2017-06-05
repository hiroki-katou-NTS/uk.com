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
        openDialog(): void{
            nts.uk.ui.windows.sub.modeless("/view/ccg/031/a/index.xhtml");
        }
    }
    
    this.bind(new ScreenModel());
});
