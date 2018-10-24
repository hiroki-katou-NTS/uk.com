module nts.uk.pr.view.qmm020.b.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        constructor(){
            var self = this;
        }
        openE(){
            modal("/view/qmm/020/e/index.xhtml");
        }

    }
}