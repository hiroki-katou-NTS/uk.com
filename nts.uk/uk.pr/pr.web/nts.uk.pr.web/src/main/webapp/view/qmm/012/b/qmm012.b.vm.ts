module nts.uk.pr.view.qmm012.b {

    import model = qmm012.share.model;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export module viewModel {
        export class ScreenModel {
            
            constructor() { 

            }//end constructor
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let deferred = $.Deferred();
                deferred.resolve();
                return deferred.promise();
            }
        }
    }
}