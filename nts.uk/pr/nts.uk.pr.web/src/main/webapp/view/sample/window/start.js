__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.value = "Hello world!";
        }
        ScreenModel.prototype.OpenModalSubWindow = function () {
            nts.uk.ui.windows.sub.modal("/view/sample/subwindow/subwindow.xhtml").onClosed(function () {
                console.log("OK");
            });
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
