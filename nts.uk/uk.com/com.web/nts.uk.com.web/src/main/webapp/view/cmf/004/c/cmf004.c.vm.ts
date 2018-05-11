module nts.uk.com.view.cmf004.c {
    export module viewmodel {
        export class ScreenModel {
            
            password: KnockoutObservable<string> = ko.observable('');
            
            constructor() {
            }
            
           
        }
    }
}