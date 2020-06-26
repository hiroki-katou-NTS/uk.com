module nts.uk.at.view.kdp010.f {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {
            nameSelection: KnockoutObservable<boolean> = ko.observable(false);
            fingerVeinAuthentication: KnockoutObservable<boolean> = ko.observable(false);
            ICCardAuthentication: KnockoutObservable<boolean> = ko.observable(false);
            personalStamp: KnockoutObservable<boolean> = ko.observable(false);
            smartphoneEngraving: KnockoutObservable<boolean> = ko.observable(false);
            portalStamp: KnockoutObservable<boolean> = ko.observable(false);
            
            constructor(){
                let self = this;
            }
         }   
    }
}