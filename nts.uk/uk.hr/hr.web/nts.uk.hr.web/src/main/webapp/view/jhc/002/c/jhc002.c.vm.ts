module nts.uk.com.view.jhc002.c.viewmodel {

    export class ScreenModel {
        constructor() {
                
        }

        isNotice() {
            nts.uk.ui.windows.setShared("isNotice", "true");
            nts.uk.ui.windows.close();
        }

        notNotice(): any {
            nts.uk.ui.windows.setShared("isNotice", "false");
            nts.uk.ui.windows.close();
        }

    }
}