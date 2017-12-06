module ksu001.ja.viewmodel {

    export class ScreenModel {
        txtName: KnockoutObservable<string> = ko.observableArray('');
        txtName1: KnockoutObservable<string> = ko.observableArray('');
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
                { id: "cut", text: "切り取り", action: self.openDialogJB },
                { id: "copy", text: "名前を変更", action: null },
                { id: "delete", text: "削除", action: self.remove }
            ];
            $("#test2").ntsButtonTable("init", { row: 3, column: 10, source: self.source(), contextMenu: self.contextMenu, disableMenuOnDataNotSet: [1, 2], mode: "normal" });
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        openDialogJB() {
            let dfd = $.Deferred();
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {
                dfd.resolve();
            });
            
            return dfd.promise();
        }

        remove() {
            let dfd = $.Deferred();

            setTimeout(function() {
                dfd.resolve(undefined);
            }, 10);

            return dfd.promise();
        }
    }
}