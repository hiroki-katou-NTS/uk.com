__viewContext.ready(function () {
    class ScreenModel {
        enable: KnockoutObservable<boolean>;
        selectedValue: KnockoutObservable<any>;
        items: KnockoutObservableArray<any>;
        
        constructor() {
            var self = this;
            self.enable = ko.observable(true);
            self.items = ko.observableArray([
                {value: 1, text: 'One'},
                {value: 2, text: 'Two'},
                {value: 3, text: 'Three'}
            ]);
            self.selectedValue = ko.observable(2);
        }
    }
    
    this.bind(new ScreenModel());
    
});