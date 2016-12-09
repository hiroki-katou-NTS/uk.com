__viewContext.ready(function () {
    class ScreenModel {
        
        constructor() {
            var self = this;
        }
        
        Alert(){
            nts.uk.ui.dialog.alert("Hello World!");
        }
        
        Confirm(){
            nts.uk.ui.dialog.confirm("Do you want to say \"Hello World!\"?");
        }
    }
    
    this.bind(new ScreenModel());
    
});