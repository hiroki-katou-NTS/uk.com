__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
        }
        ScreenModel.prototype.Alert = function () {
            nts.uk.ui.dialog.alert("Hello World!");
        };
        ScreenModel.prototype.Confirm = function () {
            nts.uk.ui.dialog.confirm("Do you want to say \"Hello World!\"?");
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map