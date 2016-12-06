__viewContext.ready(function () {
    class ScreenModel {
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
        }
    }
    
    this.bind(new ScreenModel());
    
});