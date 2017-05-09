module ccg014.b.viewmodel {

    export class ScreenModel {
        titlecode: KnockoutObservable<string>;
        titlename: KnockoutObservable<string>;
        copyTitleCD: KnockoutObservable<string>;
        copyName: KnockoutObservable<string>;
        checkOverwritting: KnockoutObservable<boolean>;
        constructor (data) {
            var self = this;
            self.titlecode = ko.observable(data.titleMenuCD());
            self.titlename = ko.observable(data.name());
            self.copyTitleCD = ko.observable('');
            self.copyName = ko.observable('');
            self.checkOverwritting = ko.observable(true);
        }

        /** Close Dialog */
        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

        /* Copy TitleMenu */
        submitClickButton() {
            var self = this;
            service.copyTitleMenu(self.titlecode(), self.copyTitleCD(), self.checkOverwritting()).done(() => {
                nts.uk.ui.dialog.alert("Msg_15");
            })
            .fail((res) => {
                nts.uk.ui.dialog.alert(res.messageId);
            });
        }
    }
}