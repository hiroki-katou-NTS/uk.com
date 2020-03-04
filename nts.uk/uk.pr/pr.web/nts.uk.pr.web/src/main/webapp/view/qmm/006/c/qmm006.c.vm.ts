module nts.uk.pr.view.qmm006.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import setShared = nts.uk.ui.windows.setShared;

    export class ScreenModel {
        
        listSourceBankLeft: KnockoutObservableArray<any>;
        listSourceBankRight: KnockoutObservableArray<any>;
        selectedCodeLeft: KnockoutObservable<string>;
        selectedCodeRight: KnockoutObservable<string>;
        accountTypes: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: getText("QMM006_46") },
            { code: 1, name: getText("QMM006_47") }
        ]);

        constructor() {
            let self = this;
            self.listSourceBankLeft = ko.observableArray([]);
            self.listSourceBankRight = ko.observableArray([]);
            self.selectedCodeLeft = ko.observable("");
            self.selectedCodeRight = ko.observable("");
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllSourceBank().done((data: Array<any>) => {
                let listSB = _.map(data, sb => {
                    return {code: sb.code, name: sb.name, accountType: self.accountTypes()[sb.accountType].name, accountNumber: sb.accountNumber};
                });
                self.listSourceBankLeft(listSB);
                self.listSourceBankRight(listSB);
                if (!_.isEmpty(listSB)) {
                    self.selectedCodeLeft(listSB[0].code);
                    self.selectedCodeRight(listSB[0].code);
                }
                dfd.resolve();
            }).fail(error => {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        execute() {
            let self = this;
            block.invisible();
            let command = { sourceCode: self.selectedCodeLeft(), destinationCode: self.selectedCodeRight() };
            service.integration(command).done(() => {
                nts.uk.ui.windows.close();
            }).fail(error => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        close() {
            nts.uk.ui.windows.close();
        }

    }

}

