module nts.uk.com.view.kdm001.j {
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
            
            
        }
    }
}