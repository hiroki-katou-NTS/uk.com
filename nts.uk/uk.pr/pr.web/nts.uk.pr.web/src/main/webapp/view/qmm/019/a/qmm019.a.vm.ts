module nts.uk.pr.view.qmm019.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import shareModel = nts.uk.pr.view.qmm019.share.model;
    import modal = nts.uk.ui.windows.sub.modal

    export class ScreenModel {

        constructor() {
            let self = this;

        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        openB(){
            modal("/view/qmm/019/b/index.xhtml");
        }
        openC(){
            modal("/view/qmm/019/c/index.xhtml");
        }
        openD(){
            modal("/view/qmm/019/d/index.xhtml");
        }
        openE(){
            modal("/view/qmm/019/e/index.xhtml");
        }
        openF(){
            modal("/view/qmm/019/f/index.xhtml");
        }
        openG(){
            modal("/view/qmm/019/g/index.xhtml");
        }
        openH(){
            modal("/view/qmm/019/h/index.xhtml");
        }
        openI(){
            modal("/view/qmm/019/i/index.xhtml");
        }
        openJ(){
            modal("/view/qmm/019/j/index.xhtml");
        }
        openK(){
            modal("/view/qmm/019/k/index.xhtml");
        }
        openL(){
            modal("/view/qmm/019/l/index.xhtml");
        }
        openM(){
            modal("/view/qmm/019/m/index.xhtml");
        }
        openN(){
            modal("/view/qmm/019/n/index.xhtml");
        }
        openO(){
            modal("/view/qmm/019/o/index.xhtml");
        }
        openP(){
            modal("/view/qmm/019/p/index.xhtml");
        }
    }
}