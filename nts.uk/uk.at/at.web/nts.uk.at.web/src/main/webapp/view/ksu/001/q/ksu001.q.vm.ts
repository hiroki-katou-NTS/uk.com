module ksu001.q.viewmodel {

    export class ScreenModel {

        constructor() {

        }

        openDialogJA(): void {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/ksu/001/ja/index.xhtml").onClosed(() => { });
        }

    }
}