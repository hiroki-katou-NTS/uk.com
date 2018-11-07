module nts.uk.pr.view.qmm017.e.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm017.e.service;
    import model = nts.uk.pr.view.qmm017.share.model;

    export class ScreenModel {
        statementItemNameList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedStatementItemName: KnockoutObservable<string> = ko.observable(null);
        fixedElement: Array<any>;

        constructor() {
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
}


