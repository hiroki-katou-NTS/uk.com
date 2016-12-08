__viewContext.ready(function () {
    class ScreenModel {
        value: string;
        
        constructor() {
            var self = this;
            self.value = "Hello world!";
        }
        
        OpenModalSubWindow() {
            nts.uk.ui.windows.sub.modal("/view/sample/subwindow/subwindow.xhtml").onClosed(() => {
                console.log("OK");
            });
        }
    }
        
    this.bind(new ScreenModel());
});
