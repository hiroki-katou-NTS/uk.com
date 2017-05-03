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
                if(self.newStartDate() < self.lastestStartDate()) $("#startDateInput").ntsError('set', "ERR");     
            });
        }
        
        submitAndCloseDialog(){
            var self = this;
            if(self.newStartDate()== null) $("#startDateInput").ntsError('set', "ERR"); 
            else {
                nts.uk.ui.windows.setShared('newStartDate', self.newStartDate());
                nts.uk.ui.windows.setShared('copyDataFlag', self.copyDataFlag());
                nts.uk.ui.windows.close(); 
            }
        }
        
        closeDialog(){
            nts.uk.ui.windows.close();   
        }
    }
}