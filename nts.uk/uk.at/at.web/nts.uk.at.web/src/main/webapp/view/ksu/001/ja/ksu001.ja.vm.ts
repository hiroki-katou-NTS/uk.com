module ksu001.ja.viewmodel {

    export class ScreenModel {
        txtName: KnockoutObservable<string> = ko.observable('');
        txtName1: KnockoutObservable<string> = ko.observable('');
        roundingRules: KnockoutObservableArray<any> = ko.observableArray([
            { code: '1', name: '四捨五入' },
            { code: '2', name: '切り上げ' }
        ]);
        selectedRuleCode: KnockoutObservable<number> = ko.observable(1);

        contextMenu: Array<any>;
        source: KnockoutObservableArray<any> = ko.observableArray([
            [{}, {}, { text: "test", tooltip: "test" }, {}],
            [],
            []
        ]);

        constructor() {
            let self = this;

            self.contextMenu = [
                { id: "copy", text: nts.uk.resource.getText("KSU001_1706"), action: null },
                { id: "delete", text: nts.uk.resource.getText("KSU001_1708"), action: self.remove }
            ];
        }

        clear() {
            let self = this;
            self.source([]);
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        openDialogJB(): JQueryPromise<any> {
            let dfd = $.Deferred();
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                dfd.resolve();
            });

            return dfd.promise();
        }

        remove(): JQueryPromise<any> {
            let dfd = $.Deferred();

            setTimeout(function() {
                dfd.resolve(undefined);
            }, 10);

            return dfd.promise();
        }
    }
}