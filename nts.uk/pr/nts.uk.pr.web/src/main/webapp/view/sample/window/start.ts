__viewContext.ready(function () {
    class ScreenModel {
        value: KnockoutObservable<string>;
        modalValue: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            self.value = ko.observable("Hello world!");
            self.modalValue = ko.observable("Goodbye world!");
        }
        
        OpenModalSubWindow() {
            // Set parent value
            nts.uk.ui.windows.setShared("parentValue", this.value());
            nts.uk.ui.windows.sub.modal("/view/sample/window/subwindow.xhtml", {dialogClass: "no-close"}).onClosed(() => {
                // Get child value
                var returnValue = nts.uk.ui.windows.getShared("childValue");
                alert("My child say: " + returnValue);
            });
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
