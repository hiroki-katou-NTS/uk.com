module nts.uk.com.view.cmf002.a {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }
            
            /**
             * request to create creation screen
             */
            importScreen(): void {
                let self = this;
                nts.uk.request.jump("/view/cmf/002/o/index.xhtml");
            }
            
            /**
             * request to create creation screen
             */
            settingScreen(): void {
                let self = this;
                nts.uk.request.jump("/view/cmf/002/b/index.xhtml");
            }
            
            /**
             * request to reference history screen
             */
            referenceHistoryScreen(): void {
                let self = this;
               nts.uk.request.jump("/view/cmf/002/x/index.xhtml");
            }
        }
    }
}