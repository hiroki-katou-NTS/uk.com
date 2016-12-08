__viewContext.ready(function () {
    class ScreenModel {
        
        constructor() {
            var self = this;
        }
        
        OpenModalSubWindow(){
            nts.uk.ui.windows.sub.modal("/view/sample/subwindow/subwindow.xhtml");
        }
        
        OpenModelessSubWindow(){
            nts.uk.ui.windows.sub.modeless("/view/sample/subwindow/subwindow.xhtml");
        }
    }
    
    this.bind(new ScreenModel());
    
});