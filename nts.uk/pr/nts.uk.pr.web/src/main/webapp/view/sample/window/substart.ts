__viewContext.ready(function () {
    class ScreenModel {
        modalValue: KnockoutObservable<string>;
        isTransistReturnData: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            self.modalValue = ko.observable("Goodbye world!");
            self.isTransistReturnData = ko.observable(nts.uk.ui.windows.getShared("isTransistReturnData"));
            // Reset child value
//            nts.uk.ui.windows.setShared("childValue", null);
        }
        
        
        CloseModalSubWindow() {
            // Set child value
            nts.uk.ui.windows.setShared("childValue", this.modalValue(), this.isTransistReturnData());
            nts.uk.ui.windows.close();
        }
    }
    
    // Get parent value
    $("#parentInstruct").text("My parent say: " + nts.uk.ui.windows.getShared("parentValue"));
    
    this.bind(new ScreenModel());
});
