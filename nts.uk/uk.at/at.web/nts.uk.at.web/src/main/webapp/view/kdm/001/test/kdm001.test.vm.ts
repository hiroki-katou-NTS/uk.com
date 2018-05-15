module nts.uk.at.view.kdm001.test.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        constructor() {
        }

        openKDM001M(): void {
            modal("/view/kdm/001/m/index.xhtml").onClosed(function() { });
        }
    }
    export class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}