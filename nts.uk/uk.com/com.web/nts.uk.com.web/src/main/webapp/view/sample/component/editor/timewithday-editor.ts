__viewContext.ready(function () {
    class ScreenModel {
        enable: KnockoutObservable<boolean>;
        readonly: KnockoutObservable<boolean>;
        timeOfDay: KnockoutObservable<number>;
        time: KnockoutObservable<number>;
        time2: KnockoutObservable<number>;
        
        constructor() {
            var self = this;
            self.enable = ko.observable(true);
            self.readonly = ko.observable(false);
            
            self.timeOfDay = ko.observable(2400);
            self.time = ko.observable(1200);
            self.time2 = ko.observable(3200);
        }
    }

    var viewmodel = new ScreenModel();
    this.bind(viewmodel);    
});