__viewContext.ready(function() {
    class ScreenModel {

        constructor() {
            var self = this;
        }

        Alert() {
            nts.uk.ui.dialog.alert("Hello world");
        }

        Confirm() {
            nts.uk.ui.dialog.confirm("Do you want to say \"Hello World!\"?");
        }
        AlertInfor() {
            nts.uk.ui.dialog.info({ id: "messageid" });
        }
        AlertError() {
            nts.uk.ui.dialog.alertError({id: "messageid", messageParams: ["1", "2"]})
        }
    }

    this.bind(new ScreenModel());

});