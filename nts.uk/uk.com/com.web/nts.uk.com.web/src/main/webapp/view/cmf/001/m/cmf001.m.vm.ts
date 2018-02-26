module nts.uk.com.view.cmf001.m.viewmodel {
    import model = nts.uk.com.view.cmf001.share.model;
    
    export class ScreenModel {
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        simpleValue: KnockoutObservable<string>;
        checked: KnockoutObservable<boolean>;
        simpleValue: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.simpleValue = ko.observable('123');    
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
             self.checked = ko.observable(true);
        }
    }
}