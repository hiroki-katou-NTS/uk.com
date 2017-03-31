__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
            self.modalValue = ko.observable("Goodbye world!");
            self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
        }
        ScreenModel.prototype.CloseModalSubWindow = function () {
            nts.uk.ui.windows.setShared("childValue", this.modalValue(), this.isTransistReturnData());
            nts.uk.ui.windows.close();
        };
        return ScreenModel;
    }());
    $("#parentInstruct").text("My parent say: " + nts.uk.ui.windows.getShared("parentValue"));
    this.bind(new ScreenModel());
});
//# sourceMappingURL=substart.js.map