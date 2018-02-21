module nts.uk.com.view.cmf001.f.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        constructor() {
        }
        OpenFModal() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/cmf/001/f/index.xhtml', { title: '' }).onClosed(function(): any {
            });
        }
        start(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            return dfd.promise();
        }
    }//end screenModel
}//end module