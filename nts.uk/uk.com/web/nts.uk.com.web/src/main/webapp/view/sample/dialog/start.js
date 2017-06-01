__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
        }
        ScreenModel.prototype.Alert = function () {
            nts.uk.ui.dialog.alert("Hello world");
        };
        ScreenModel.prototype.Confirm = function () {
            nts.uk.ui.dialog.confirm("Do you want to say \"Hello World!\"?");
        };
        ScreenModel.prototype.AlertInfor = function () {
            nts.uk.ui.dialog.info({ messageId: "messageid" });
        };
        ScreenModel.prototype.AlertError = function () {
            nts.uk.ui.dialog.alertError({ messageId: "Msg_175", messageParams: ["1", "2"] });
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map