__viewContext.ready(function () {
    class ScreenModel {
        value: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            self.value = ko.observable("Hello world!");
        }
        
        OpenModalSubWindow() {
            // Set parent value
            nts.uk.ui.windows.setShared("parentValue", this.value());
            nts.uk.ui.windows.sub.modal("/view/sample/window/subwindow.xhtml").onClosed(() => {
                // Get child value
                var returnValue = nts.uk.ui.windows.getShared("childValue");
                alert("My child say: " + returnValue);
            });
        }
    }
    
    this.bind(new ScreenModel());
});
