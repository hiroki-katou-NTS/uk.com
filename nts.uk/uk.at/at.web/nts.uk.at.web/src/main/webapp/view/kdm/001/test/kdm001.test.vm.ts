module nts.uk.at.view.kdm001.test.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        constructor() {
        }
        openKDM001I(): void {
            modal("/view/kdm/001/i/index.xhtml").onClosed(function() { });
        }
        openKDM001J(): void {
            modal("/view/kdm/001/j/index.xhtml").onClosed(function() { });
        }
        openKDM001K(): void {
            modal("/view/kdm/001/k/index.xhtml").onClosed(function() { });
        }
        openKDM001L(): void {
            modal("/view/kdm/001/l/index.xhtml").onClosed(function() { });
        }
        openKDM001M(): void {
            modal("/view/kdm/001/m/index.xhtml").onClosed(function() { });
        }
    }
}