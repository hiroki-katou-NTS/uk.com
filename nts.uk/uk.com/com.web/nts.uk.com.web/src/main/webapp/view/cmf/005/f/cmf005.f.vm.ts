module nts.uk.com.view.cmf005.f.viewmodel {
   
    import close = nts.uk.ui.windows.close;

    export class ScreenModel {

        textf212: KnockoutObservable<string>;
        textf222: KnockoutObservable<string>;
        textf223: KnockoutObservable<string>;
        textf312: KnockoutObservable<string>;
        textf322: KnockoutObservable<string>;
        textf332: KnockoutObservable<string>;
        textf334: KnockoutObservable<string>;
        textf342: KnockoutObservable<string>;
        textf344: KnockoutObservable<string>;
        textf352: KnockoutObservable<string>;
        textf354: KnockoutObservable<string>;
        //button
        btnCloseDisplay: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.textf212 = ko.observable('11111');
            self.textf222 = ko.observable('11111');
            self.textf223 = ko.observable('11111');
            self.textf312 = ko.observable('11111');
            self.textf322 = ko.observable('11111');
            self.textf332 = ko.observable('11111');
            self.textf334 = ko.observable('11111');
            self.textf342 = ko.observable('11111');
            self.textf344 = ko.observable('11111');
            self.textf352 = ko.observable('11111');
            self.textf354 = ko.observable('11111');
            self.btnCloseDisplay = ko.observable(false);

        }

        breakPopup() {
           $("#F10_1").hide();
           $("#F10_2").show();
        }

        closePopup() {
            close();
        }

    }

}






