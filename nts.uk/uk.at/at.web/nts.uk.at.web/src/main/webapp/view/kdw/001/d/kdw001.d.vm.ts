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
                $("#wizard").ntsWizard("prev");        
            }
            
            opendScreenE() {
                nts.uk.ui.windows.setShared("KDWL001E", __viewContext["viewmodel"].params);
                nts.uk.request.jump("/view/kdw/001/e/index.xhtml");
            }
        }
    }
}
