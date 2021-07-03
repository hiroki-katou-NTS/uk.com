module nts.uk.at.view.ksc001.a {
    export module viewmodel {
        export class ScreenModel {

            constructor() {
                const self = this;
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
            createScreen(): void {
                let self = this;
                nts.uk.request.jump("/view/ksc/001/b/index.xhtml");
            }
            /**
             * request to reference history screen
             */
            referenceHistoryScreen(): void {
                let self = this;
               nts.uk.request.jump("/view/ksc/001/g/index.xhtml");
            }
        }
    }
}