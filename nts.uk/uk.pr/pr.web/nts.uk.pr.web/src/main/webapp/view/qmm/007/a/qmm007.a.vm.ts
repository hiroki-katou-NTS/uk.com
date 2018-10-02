module nts.uk.pr.view.qmm007.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {

        constructor() {

        }





        openFscreen(){
            let self = this;
            modal("/view/qmm/007/c/index.xhtml").onClosed(function () {

            });
        }


    }





}