module nts.uk.com.view.cmf001.a {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                 let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            /**
             * request to create creation screen
             */
            importScreen(): void {
                let self = this;
                nts.uk.request.jump("/view/cmf/001/o/index.xhtml");
            }
            
            /**
             * request to create creation screen
             */
            settingScreen(): void {
                let self = this;
                nts.uk.request.jump("/view/cmf/001/b/index.xhtml");
            }
            
            /**
             * request to reference history screen
             */
            referenceHistoryScreen(): void {
                let self = this;
               nts.uk.request.jump("/view/cmf/001/s/index.xhtml");
            }
        }
    }
}