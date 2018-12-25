module nts.uk.com.view.cdl028.test.viewmodel {

    export class ScreenModel {

        constructor() {
            let self = this;

        }
        /**
         * startPage
         */
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }

        /**
         * closeDialog
         */
        public openDialog() {
            let params = {
                date: 20000101,
                mode: 3
            };
            console.log(params + "1111111");
            nts.uk.ui.windows.setShared("CDL028_INPUT", params);

            nts.uk.ui.windows.sub.modal("/view/cdl/028/a/index.xhtml").onClosed(function() {
                console.log(params);
            });

        }

    }
}