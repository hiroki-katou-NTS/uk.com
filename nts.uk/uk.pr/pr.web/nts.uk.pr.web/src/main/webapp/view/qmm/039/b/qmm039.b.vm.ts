module nts.uk.pr.view.qmm039.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import hasError = nts.uk.ui.errors.hasError;

    export class ScreenModel {


        constructor() {
            var self = this;

        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        startPage(params): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.startupScreen();
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen() {
            var self = this;
        }

    }
}
