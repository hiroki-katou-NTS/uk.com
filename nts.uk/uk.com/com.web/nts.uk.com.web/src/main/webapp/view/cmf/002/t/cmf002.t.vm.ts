module nts.uk.com.view.cmf002.t.viewmodel {
    import model = nts.uk.com.view.cmf002.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import error = nts.uk.ui.errors;

    export class ScreenModel {
        sourceCode: KnockoutObservable<string> = ko.observable('');
        sourceName: KnockoutObservable<string> = ko.observable('');
        standardType: KnockoutObservable<string> = ko.observable('');
        copySourceName: KnockoutObservable<string> = ko.observable('');
        destinationCategory: KnockoutObservable<string> = ko.observable('');
        conditionSetCd: KnockoutObservable<string> = ko.observable('');
        conditionName: KnockoutObservable<string> = ko.observable('');
        overWrite: KnockoutObservable<boolean> = ko.observable(false);
        copyDestinationCode: KnockoutObservable<string> = ko.observable('');
        destinationName: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;
            self.initScreen();
        }

        public initScreen(): void {
            block.invisible();
            let self = this,
                info = getShared("getScreenB");
            if (info) {
                self.sourceCode(info.sourceCode);
                self.sourceName(info.sourceName);
                self.newCondCode(info.newCondCode);
                self.newCondName(info.newCondName);
                self.conditionSetCd('123');
                self.conditionName('abc');
            }
            self.conditionSetCd('conditionSetCd');
            self.conditionName('conditionName');
            self.overWrite(false);
            self.destinationName('destinationName');
            self.copyDestinationCode('copyDestinationCode');
            block.clear();
        }

        excuteCopy() {
            error.clearAll();
            $("#T3_2").trigger("validate");
            $("#T3_3").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                let self = this;
                let data = {
                    conditionSetCd: self.conditionSetCd();
                    conditionName: self.conditionName();
                    overWrite: self.overWrite();
                    destinationName: self.destinationName();
                    copyDestinationCode: self.copyDestinationCode();

                };
                service.excuteCopy(data).done((result) => {
                    //do something
                }).fail(function(res) {
                    alertError({ messageId: res.messageId });
                }).always(function() {
                    block.clear();
                });
            }
        }



        /**
        * Close dialog.
        */
        cancelSetting(): void {
            nts.uk.ui.windows.close();
            alert('close');
        }

    }
}
