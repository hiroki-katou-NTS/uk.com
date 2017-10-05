module ksu001.c.viewmodel {

    export class ScreenModel {
        timer: nts.uk.ui.sharedvm.KibanTimer = new nts.uk.ui.sharedvm.KibanTimer('timer');
        constructor() {
            let self = this;
            $('#error-status').css('display', 'none');
            $('#error-output').css('display', 'none');
            self.timer.start();
        }

        stop(): void {
            let self = this;
            $('#error-status').css('display', '');
            $('#error-output').css('display', '');
            $('#stop').css('display', 'none');
            nts.uk.ui.windows.getSelf().setHeight(420);
            nts.uk.ui.windows.getSelf().setWidth(910);
            self.timer.end();
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
}