__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.value = ko.observable("Hello world!");
            self.modalValue = ko.observable("Goodbye world!");
        }
        ScreenModel.prototype.OpenModalSubWindow = function () {
            // Set parent value
            nts.uk.ui.windows.setShared("parentValue", this.value());
            nts.uk.ui.windows.sub.modal("/view/sample/window/subwindow.xhtml", { dialogClass: "no-close" }).onClosed(function () {
                // Get child value
                var returnValue = nts.uk.ui.windows.getShared("childValue");
                alert("My child say: " + returnValue);
            });
        };
        ScreenModel.prototype.CloseModalSubWindow = function () {
            // Set child value
            nts.uk.ui.windows.setShared("childValue", this.modalValue());
            nts.uk.ui.windows.close();
        };
        return ScreenModel;
    }());
    // Get parent value
    $("#parentInstruct").text("My parent say: " + nts.uk.ui.windows.getShared("parentValue"));
    this.bind(new ScreenModel());
});
