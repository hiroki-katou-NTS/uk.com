__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
        }
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
function OpenModalSubWindow(option) {
    nts.uk.ui.windows.sub.modal("/view/sample/subwindow/subwindow.xhtml", option);
}
function OpenModelessSubWindow(option) {
    nts.uk.ui.windows.sub.modeless("/view/sample/subwindow/subwindow.xhtml", option);
}
