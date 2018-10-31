module nts.uk.pr.view.qmm019.o.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {

        columns: KnockoutObservableArray<any>;
        specItems: KnockoutObservableArray<any>;
        specItemSelected: KnockoutObservable<any>;

        constructor() {
            let self = this;

            self.specItems = ko.observableArray([]);
            self.specItemSelected = ko.observable(null);

            this.columns = ko.observableArray([
                {headerText: getText("QMM019_108"), key: 'code', width: 60, formatter: _.escape},
                {headerText: getText("QMM019_109"), key: 'name', width: 240, formatter: _.escape}
            ]);

            for (let i = 1; i < 100; i++) {
                this.specItems.push({code: i, name: "item name " + i});
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        decide() {
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
    }
}