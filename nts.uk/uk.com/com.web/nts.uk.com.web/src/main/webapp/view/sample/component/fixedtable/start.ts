__viewContext.ready(function () {
    class ScreenModel {
        text: KnockoutObservable<string>;
        enable: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            self.text = ko.observable("abc");
            self.enable = ko.observable(true);
            $("#fixed-table").ntsFixedTable({ height: 300 });
        }
    }
    
    this.bind(new ScreenModel());
    
});