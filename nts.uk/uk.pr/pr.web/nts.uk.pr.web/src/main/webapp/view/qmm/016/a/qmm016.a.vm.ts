module nts.uk.pr.view.qmm016.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    export class ScreenModel {
        isOnStartUp: boolean = true;
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);
        constructor() {
            let self = this;
        }
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        createNewWageTable () {
            nts.uk.ui.errors.clearAll();
        }
        registerWageTable () {

        }
        copy () {
            // TODO
        }
        outputExcel () {
            // TODO
        }
        settingQualificationGroup () {
            // TODO
        }
        correctMasterDialog () {
            // TODO
        }
    }
}

