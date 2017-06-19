__viewContext.ready(function () {
    class ScreenModel {
        enable: KnockoutObservable<boolean>;
        dateValue: KnockoutObservable<any>;
        
        constructor() {
            var self = this;
            self.enable = ko.observable(true);
            
            self.dateValue = ko.observable({});
        }
    }
    
    this.bind(new ScreenModel());
    
});