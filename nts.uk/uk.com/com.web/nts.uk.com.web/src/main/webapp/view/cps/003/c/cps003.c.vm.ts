module nts.uk.com.view.cps003.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export class ScreenModel {

        constructor() {
            let self = this;

            self.start();
        }

        start() {
            let self = this,
                param = getShared("CPS003D_VALUE");
            console.log(param);
        }
        
        register() {
        }
        
        checkError() {
            
        }
        
        close() {
            nts.uk.ui.windows.close();
        }
    }
}
