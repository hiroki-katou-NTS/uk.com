module nts.uk.pr.view.qmm019.d.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;

    export class ScreenModel {

        constructor() {
            let self = this;

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