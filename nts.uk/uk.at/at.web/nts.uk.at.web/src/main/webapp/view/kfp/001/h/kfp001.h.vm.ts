module nts.uk.at.view.kfp001.h {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {


            constructor() {

            }

            start() {



            }

            opendScreenF() {
                nts.uk.request.jump("/view/kfp/001/b/index.xhtml");
            }

            opendScreenJC() {
                let self = this;
                
                nts.uk.request.jump("/view/kfp/001/c/index.xhtml");
            }

            opendScreenBC() {
                let self = this;
               
                nts.uk.request.jump("/view/kfp/001/d/index.xhtml");
            }
        }



    }
}
