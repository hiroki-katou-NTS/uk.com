__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
        }
        ScreenModel.prototype.OpenModalSubWindow = function () {
            nts.uk.ui.windows.sub.modal("/view/sample/subwindow/subwindow.xhtml");
        };
        ScreenModel.prototype.OpenModelessSubWindow = function () {
            nts.uk.ui.windows.sub.modeless("/view/sample/subwindow/subwindow.xhtml");
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
