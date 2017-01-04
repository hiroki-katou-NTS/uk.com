__viewContext.ready(function () {
    class ScreenModel {
        constructor() {
            var self = this;
        }
    }
    this.bind(new ScreenModel());
});

function OpenModalSubWindow(option?: any){
    nts.uk.ui.windows.sub.modal("/view/sample/subwindow/subwindow.xhtml", option);
}

function OpenModelessSubWindow(option?: any){
    nts.uk.ui.windows.sub.modeless("/view/sample/subwindow/subwindow.xhtml", option);
}