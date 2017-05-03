module kml001.d.viewmodel {
    export class ScreenModel {
        isUpdate: KnockoutObservable<boolean>;
        lastestStartDate: KnockoutObservable<string>;
        newStartDate: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.isUpdate = ko.observable(true);
            self.isUpdate.subscribe(function(value) { 
                if(value) {
                    $(".dateInput").addClass("isUpdate").removeClass("isDelete");    
                } else { 
                    $(".dateInput").addClass("isDelete").removeClass("isUpdate"); 
                    $("#startDateInput").ntsError('clear');
                }
            });
            self.lastestStartDate = ko.observable(nts.uk.ui.windows.getShared('lastestStartDate'));
            self.newStartDate = ko.observable();
            self.newStartDate.subscribe(function(value){
                if(self.newStartDate() < self.lastestStartDate()) $("#startDateInput").ntsError('set', "ERR");     
            });
        }
        
        submitAndCloseDialog(){
            var self = this;
            if(self.isUpdate()) {
                if(self.newStartDate()== null) $("#startDateInput").ntsError('set', "ERR"); 
                else {
                    nts.uk.ui.windows.setShared('newStartDate', self.newStartDate());
                    nts.uk.ui.windows.setShared('isUpdate', self.isUpdate());
                    nts.uk.ui.windows.close();    
                }
            } else {
                nts.uk.ui.windows.setShared('isUpdate', self.isUpdate());
                nts.uk.ui.windows.close(); 
            }
        }
        
        closeDialog(){
            nts.uk.ui.windows.close();   
        }
    }
}