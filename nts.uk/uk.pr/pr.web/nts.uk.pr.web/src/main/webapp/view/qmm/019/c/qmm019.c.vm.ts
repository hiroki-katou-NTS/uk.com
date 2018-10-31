module nts.uk.pr.view.qmm019.c.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {
        yearMonth: KnockoutObservable<number>;

        // ItemHistoryDivision  switch button
        itemHistoryEditList: KnockoutObservableArray<shareModel.BoxModel>;
        itemHistoryEdit: KnockoutObservable<number>;

        constructor() {
            let self = this;

            self.yearMonth = ko.observable(200001);

            self.itemHistoryEditList = ko.observableArray([
                new shareModel.BoxModel(0, getText('QMM019_46')),
                new shareModel.BoxModel(1, getText('QMM019_47'))
            ]);
            self.itemHistoryEdit = ko.observable(0);
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        decide(){
            nts.uk.ui.windows.close();
        }

        cancel(){
            nts.uk.ui.windows.close();
        }
    }
}