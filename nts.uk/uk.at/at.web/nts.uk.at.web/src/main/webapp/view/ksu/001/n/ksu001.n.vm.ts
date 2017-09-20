module ksu001.n.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getShare = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        constructor() {
            let self = this;
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
}