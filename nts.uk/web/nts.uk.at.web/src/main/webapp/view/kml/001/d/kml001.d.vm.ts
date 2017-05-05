module kml001.d.viewmodel {
    export class ScreenModel {
        isUpdate: KnockoutObservable<boolean>;
        size: number;
        beforeStartDate: KnockoutObservable<string>;
        currentEndDate: KnockoutObservable<string>;
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
            self.size = nts.uk.ui.windows.getShared('size');
            self.beforeStartDate = ko.observable(nts.uk.ui.windows.getShared('beforeStartDate'));
            self.currentEndDate = ko.observable(nts.uk.ui.windows.getShared('currentEndDate'));
            self.newStartDate = ko.observable();
            self.newStartDate.subscribe(function(value){
                if(self.errorStartDate()) $("#startDateInput").ntsError('set', "ERR");     
            });
        }
        
        errorStartDate(): boolean {
            var self = this;
            return (
                (self.newStartDate()== null) ||
                (self.newStartDate() < kml001.shr.vmbase.DateTimeProcess.getOneDayAfter(self.beforeStartDate())) ||
                (self.newStartDate() > self.currentEndDate())
                );    
        }
        
        submitAndCloseDialog(): void {
            var self = this;
            if(self.isUpdate()) {
                if(self.errorStartDate()) $("#startDateInput").ntsError('set', "ERR"); 
                else {
                    nts.uk.ui.windows.setShared('newStartDate', self.newStartDate());
                    nts.uk.ui.windows.setShared('isUpdate', self.isUpdate());
                    nts.uk.ui.windows.close();    
                }
            } else {
                if(self.size > 1) {
                    nts.uk.ui.windows.setShared('isUpdate', self.isUpdate());
                    nts.uk.ui.windows.close(); 
                }
            }
        }
        
        closeDialog(): void {
            $("#startDateInput").ntsError('clear');
            nts.uk.ui.windows.close();   
        }
    }
}