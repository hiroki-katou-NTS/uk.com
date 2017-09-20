module nts.uk.at.view.kmk002.b {
    export module viewmodel {
        export class ScreenModel {

            constructor() {
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}