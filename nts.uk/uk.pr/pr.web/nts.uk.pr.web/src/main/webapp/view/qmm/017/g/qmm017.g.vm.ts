module nts.uk.pr.view.qmm017.g.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import service = nts.uk.pr.view.qmm017.g.service;
    import model = nts.uk.pr.view.qmm017.share.model;
    export class ScreenModel {
        constructor() {
        }
        startPage () : JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            dfd.resolve();
            return dfd.promise();
        }
    }
}


