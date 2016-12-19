__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.modalValue = ko.observable("Goodbye world!");
            // Reset child value
            nts.uk.ui.windows.setShared("childValue", null);
        }
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
