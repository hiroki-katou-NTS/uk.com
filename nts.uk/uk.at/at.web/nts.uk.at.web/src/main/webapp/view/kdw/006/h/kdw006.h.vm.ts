module nts.uk.at.view.kdw006.h.viewmodel {

    export class ScreenModel {

        constructor() {
        }

        returnData() {
            let self = this;
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        startPage(): JQueryPromise<any> {
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
}