module nts.uk.at.view.kdw001.a {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            constructor() {
                
            }
            
            opendScreenF() {
                nts.uk.request.jump("/view/kdw/001/f/index.xhtml");
            }
            
            opendScreenJC() {
                nts.uk.request.jump("/view/kdw/001/j/index.xhtml", {"activeStep": 0, "screenName": "J"});
            }
            
            opendScreenBC() {
                nts.uk.request.jump("/view/kdw/001/b/index.xhtml", {"activeStep": 0, "screenName": "B"});
            }
        }
    }
}
