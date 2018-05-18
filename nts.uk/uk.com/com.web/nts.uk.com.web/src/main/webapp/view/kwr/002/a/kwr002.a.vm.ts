module nts.uk.com.view.kwr002.a.viewmodel {
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import TabPanel = nts.uk.ui.tabpanel;
    export class ScreenModel {

        constructor() {
            let self = this;
        }
        
        start_page(): JQueryPromise<any>{
            blockUI.invisible();
            let self =this;
            let dfd = $.Deferred();
            
            blockUI.clear();
            return dfd.promise();
        }
    }

    export module model {

    }
}