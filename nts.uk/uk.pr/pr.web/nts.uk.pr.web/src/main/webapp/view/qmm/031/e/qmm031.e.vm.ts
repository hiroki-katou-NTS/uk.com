module nts.uk.pr.view.qmm031.e.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {


        constructor() {
            let self = this,
                dfd = $.Deferred();
        }

        close() {
            let self = this;

            nts.uk.ui.windows.close();
        }
        execution(){

        }
    }
}