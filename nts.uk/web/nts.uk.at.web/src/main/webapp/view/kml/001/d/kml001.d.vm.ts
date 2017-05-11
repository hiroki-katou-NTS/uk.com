module kml001.d.viewmodel {
    import servicebase = kml001.shr.servicebase;
    import vmbase = kml001.shr.vmbase;
    export class ScreenModel {
        isUpdate: KnockoutObservable<boolean>;
        beforeIndex: number;
        size: number;
        personCostList: KnockoutObservableArray<vmbase.PersonCostCalculation>;
        currentPersonCost: KnockoutObservable<vmbase.PersonCostCalculation>;
        beforeStartDate: KnockoutObservable<string>;
        currentEndDate: KnockoutObservable<string>;
        newStartDate: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.isUpdate = ko.observable(true);
            self.isUpdate.subscribe(function(value) { 
                if(value) {
                    if(self.errorStartDate()) $("#startDateInput").ntsError('set', "ERR");
                    $(".dateInput").addClass("isUpdate").removeClass("isDelete");    
                } else { 
                    $(".dateInput").addClass("isDelete").removeClass("isUpdate"); 
                    $("#startDateInput").ntsError('clear');
                }
            });
            self.personCostList = ko.observable(nts.uk.ui.windows.getShared('personCostList'));
            self.currentPersonCost = ko.observable(nts.uk.ui.windows.getShared('currentPersonCost'));
            self.beforeIndex = _.findIndex(self.personCostList(), function(o) { return o.startDate() == self.currentPersonCost().startDate(); })-1;
            self.size = _.size(self.personCostList());
            self.beforeStartDate = ko.observable((self.beforeIndex>=0)?self.personCostList()[self.beforeIndex].startDate():"1900/1/1");
            self.currentEndDate = ko.observable(self.currentPersonCost().endDate());
            self.newStartDate = ko.observable();
            self.newStartDate.subscribe(function(value){
                if(self.errorStartDate()) $("#startDateInput").ntsError('set', "ERR");     
            });
        }
        
        errorStartDate(): boolean {
            var self = this;
            return (
                (self.newStartDate()== null) ||
                !vmbase.ProcessHandler.validateDateRange(self.newStartDate(),kml001.shr.vmbase.ProcessHandler.getOneDayAfter(self.beforeStartDate()),self.currentEndDate())
                );    
        }
        
        submitAndCloseDialog(): void {
            var self = this;
            if(self.isUpdate()) {
                if(self.errorStartDate()) $("#startDateInput").ntsError('set', "ERR"); 
                else {
                    self.currentPersonCost().startDate(self.newStartDate());
                    servicebase.personCostCalculationUpdate(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                        .done(function(res: Array<any>) {
                            nts.uk.ui.windows.setShared('isEdited', true);
                            nts.uk.ui.windows.close();
                        })
                        .fail(function(res) {
                            
                        });
                }
            } else {
                if(self.size > 1) {
                    servicebase.personCostCalculationDelete(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                        .done(function(res: Array<any>) {
                            nts.uk.ui.windows.setShared('isEdited', true);
                            nts.uk.ui.windows.close();
                        })
                        .fail(function(res) {
                            
                        });
                }
            }
        }
        
        closeDialog(): void {
            $("#startDateInput").ntsError('clear');
            nts.uk.ui.windows.close();   
        }
    }
}