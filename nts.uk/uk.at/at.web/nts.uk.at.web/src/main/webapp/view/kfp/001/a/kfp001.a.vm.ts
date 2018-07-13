module nts.uk.at.view.kfp001.a {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            constructor() {
            }

            start() {
            }
            
            opendScreenB() {
                nts.uk.request.jump("/view/kfp/001/b/index.xhtml");
            }
            
            opendScreenH() {
                let self = this;
                nts.uk.request.jump("/view/kfp/001/h/index.xhtml");
            }
        }
    }
}
