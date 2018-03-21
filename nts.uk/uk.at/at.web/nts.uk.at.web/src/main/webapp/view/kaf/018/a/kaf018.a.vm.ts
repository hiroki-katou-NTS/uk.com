module nts.uk.at.view.kaf018.a.viewmodel {
    import text = nts.uk.resource.getText;

    export class ScreenModel {
        constructor() {
            var self = this;
        }

        gotoH() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/kaf/018/h/index.xhtml");
        }
    }
}