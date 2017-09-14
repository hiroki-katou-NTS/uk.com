module nts.uk.com.view.cmm018.m {
    export module viewmodel {
        export class ScreenModel {
            isCompany: KnockoutObservable<boolean> = ko.observable(true);
            isWorkplace: KnockoutObservable<boolean> = ko.observable(true);
            isPerson: KnockoutObservable<boolean> = ko.observable(true);            
            date: KnockoutObservable<Date> = ko.observable(new Date);
            constructor() {
                var self = this;                            
            }
            //閉じるボタン
            closeDialog(){
                nts.uk.ui.windows.close();
            }
            
            printExcel(){
                var self = this;
                   
            }
        }
        
        
    }
}
