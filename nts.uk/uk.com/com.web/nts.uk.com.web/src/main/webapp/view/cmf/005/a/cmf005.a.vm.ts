module nts.uk.com.view.cmf005.a {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }
            
            /**
             * request to delete data screen
             */
            deleteDataScreen(): void {
                let self = this;
                nts.uk.request.jump("/view/cmf/005/b/index.xhtml");
            }
            
            
        }
    }
}