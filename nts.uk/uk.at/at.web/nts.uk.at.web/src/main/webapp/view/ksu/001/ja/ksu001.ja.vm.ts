module ksu001.ja.viewmodel {

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
        
        openDialogJB():void{
            nts.uk.ui.windows.sub.modal("/view/ksu/001/jb/index.xhtml").onClosed(() => {});
        }
    }
}