module nts.uk.at.view.kmf003.b {
    export module viewmodel {
        export class ScreenModel {
            
            constructor() {
                var self = this;
            }

            /**
             * Start page.
             */
            private startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                dfd.resolve();

                return dfd.promise();
            }
        }
    }
}