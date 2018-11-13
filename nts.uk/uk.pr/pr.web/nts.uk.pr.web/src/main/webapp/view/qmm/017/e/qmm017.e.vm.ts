module nts.uk.pr.view.qmm017.e.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm017.e.service;
    import model = nts.uk.pr.view.qmm017.share.model;

    export class ScreenModel {
        basicCalulcationFormula: KnockoutObservable<model.BasicCalculationFormula> = ko.observable(new model.BasicCalculationFormula(null));

        constructor() {
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        saveConfiguration () {
            let self = this;
            nts.uk.ui.windows.close();
        }
        cancel () {
            let self = this;
            nts.uk.ui.windows.close();
        }
    }
}


