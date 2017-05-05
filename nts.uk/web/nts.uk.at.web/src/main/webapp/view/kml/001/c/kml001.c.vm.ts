module kml001.c.viewmodel {
    export class ScreenModel {
        copyDataFlag: KnockoutObservable<boolean>;
        lastestStartDate: KnockoutObservable<string>;
        newStartDate: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.copyDataFlag = ko.observable(true);
            self.lastestStartDate = ko.observable(nts.uk.ui.windows.getShared('lastestStartDate'));
            self.newStartDate = ko.observable();
            self.newStartDate.subscribe(function(value){
                if(self.errorStartDate()) $("#startDateInput").ntsError('set', "ERR");     
            });
        }
        
        errorStartDate(): boolean {
            var self = this;
            return ((self.newStartDate()== null)||(self.newStartDate() < self.lastestStartDate()));     
        }
        
        submitAndCloseDialog(): void {
            var self = this;
            if(self.errorStartDate()) $("#startDateInput").ntsError('set', "ERR"); 
            else {
                nts.uk.ui.windows.setShared('newStartDate', self.newStartDate());
                nts.uk.ui.windows.setShared('copyDataFlag', self.copyDataFlag());
                nts.uk.ui.windows.close(); 
            }
        }
        
        closeDialog(): void {
            $("#startDateInput").ntsError('clear');
            nts.uk.ui.windows.close();   
        }
    }
}