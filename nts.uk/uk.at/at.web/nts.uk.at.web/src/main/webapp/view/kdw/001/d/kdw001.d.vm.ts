module nts.uk.at.view.kdw001.d {
    import getText = nts.uk.resource.getText;

    export module viewmodel {
        export class ScreenModel {
            //Declare screenName flag to forware screen B or screen J
            screenName: string;
            constructor() {
                let self = this;
                //Get screenName value from a screen
                __viewContext.transferred.ifPresent(data => {
                    self.screenName = data.screenName;
                });
            }
            
            opendScreenBorJ() {
//                let self = this;
//                if(self.screenName == "B"){
//                    nts.uk.request.jump("/view/kdw/001/b/index.xhtml", {"activeStep": 1});    
//                }else{
//                    nts.uk.request.jump("/view/kdw/001/j/index.xhtml", {"activeStep": 1});
//                }
                $("#wizard").ntsWizard("prev");        
            }
            
            opendScreenE() {
                nts.uk.request.jump("/view/kdw/001/e/index.xhtml");
            }
        }
    }
}
