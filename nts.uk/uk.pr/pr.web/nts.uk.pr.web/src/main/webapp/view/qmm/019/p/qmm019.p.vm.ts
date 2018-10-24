module nts.uk.pr.view.qmm019.p.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {
        standardYM: KnockoutObservable<number>;

        columns: KnockoutObservableArray<any>;
        specLayouts: KnockoutObservableArray<any>;
        specLayoutsSelected: KnockoutObservableArray<any>;

        constructor() {
            let self = this;
            self.standardYM = ko.observable(201801);

            this.columns = ko.observableArray([
                {headerText: '', key: 'specCode', hidden: true},
                {headerText: getText("QMM019_21"), key: 'specName', width: 200, formatter: _.escape}
            ]);
            self.specLayouts = ko.observableArray([]);
            self.specLayoutsSelected = ko.observableArray([]);

            for (let i = 1; i < 100; i++) {
                this.specLayouts.push({specCode: i, specName: "specName " + i});
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        output() {
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
    }
}