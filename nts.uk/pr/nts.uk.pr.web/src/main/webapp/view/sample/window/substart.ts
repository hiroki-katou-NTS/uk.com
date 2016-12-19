__viewContext.ready(function () {
    class ScreenModel {
        modalValue: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            self.modalValue = ko.observable("Goodbye world!");
            // Reset child value
            nts.uk.ui.windows.setShared("childValue", null);
        }
        
        
        CloseModalSubWindow() {
            // Set child value
            nts.uk.ui.windows.setShared("childValue", this.modalValue());
            nts.uk.ui.windows.close();
        }
    }
    
    // Get parent value
    $("#parentInstruct").text("My parent say: " + nts.uk.ui.windows.getShared("parentValue"));
    
    this.bind(new ScreenModel());
});
