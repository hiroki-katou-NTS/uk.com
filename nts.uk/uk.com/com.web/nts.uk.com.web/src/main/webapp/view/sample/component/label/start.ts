__viewContext.ready(function () {
    class ScreenModel {
        text: KnockoutObservable<string>;
        constraint: Array<string>;
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            self.text = ko.observable("レイアウトコード");
            self.constraint = ['ResidenceCode','ResidenceCode'];
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
        }
    }
    
    this.bind(new ScreenModel());
    
});