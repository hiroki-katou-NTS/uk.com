__viewContext.ready(function () {
    class ScreenModel {
        ResidentCode: KnockoutObservable<string>;
        
        constructor() {
            this.ResidentCode = ko.observable('abc');
        }
        setError() {
            $("#text").ntsError("set", "Errors."); 
        }
        
        clearError() {
             $("#text").ntsError("clear"); 
        }
    }
    
    this.bind(new ScreenModel());
});
