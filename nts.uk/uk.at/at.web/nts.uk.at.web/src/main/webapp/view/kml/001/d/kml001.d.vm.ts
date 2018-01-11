module nts.uk.at.view.kml001.d {
    export module viewmodel {
        import servicebase = kml001.shr.servicebase;
        import vmbase = kml001.shr.vmbase;
        export class ScreenModel {
            isUpdate: KnockoutObservable<boolean>;
            deleteAble: KnockoutObservable<boolean>;
            beforeIndex: number;
            size: number;
            personCostList: KnockoutObservableArray<vmbase.PersonCostCalculation>;
            currentPersonCost: KnockoutObservable<vmbase.PersonCostCalculation>;
            isLast: KnockoutObservable<boolean>;
            beforeStartDate: KnockoutObservable<string>;
            onedayAfterBeforeStartDate: KnockoutObservable<string>;
            currentEndDate: KnockoutObservable<string>;
            newStartDate: KnockoutObservable<string>;
            constructor() {
                var self = this;
                self.personCostList = ko.observableArray(
                    _.map(nts.uk.ui.windows.getShared('personCostList'), (item : vmbase.PersonCostCalculationInterface) => { return vmbase.ProcessHandler.fromObjectPerconCost(item); })
                );
                self.currentPersonCost = ko.observable(
                    vmbase.ProcessHandler.fromObjectPerconCost(nts.uk.ui.windows.getShared('currentPersonCost'))
                );
                self.size = _.size(self.personCostList());
                self.isLast = ko.observable((_.findIndex(self.personCostList(), function(o){return self.currentPersonCost().startDate() == o.startDate(); })==(self.size-1))?true:false);
                self.deleteAble = ko.observable((self.isLast()&&(self.size>1))); // can delete when item is last and list have more than one item
                self.beforeIndex = _.findIndex(self.personCostList(), function(o) { return o.startDate() == self.currentPersonCost().startDate(); })-1;
                self.beforeStartDate = ko.observable((self.beforeIndex>=0)?self.personCostList()[self.beforeIndex].startDate():"1900/01/01");
                self.onedayAfterBeforeStartDate = ko.observable(moment(self.beforeStartDate()).add(1,'days').format('YYYY/MM/DD'));
                self.currentEndDate = ko.observable(self.currentPersonCost().endDate());
                self.newStartDate = ko.observable(self.currentPersonCost().startDate());
                self.isUpdate = ko.observable(self.deleteAble()?false:true);
            }
            
            /**
             * update/delete data when no error and close dialog
             */
            submitAndCloseDialog(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                if(self.isUpdate()) {
                    if(!vmbase.ProcessHandler.validateDateRange(self.newStartDate(),vmbase.ProcessHandler.getOneDayAfter(self.beforeStartDate()),self.currentEndDate())) {
                        $("#startDateInput").ntsError('set', {messageId:"Msg_65"}); 
                        nts.uk.ui.block.clear();
                    } else {
                        self.currentPersonCost().startDate(self.newStartDate());
                        self.currentPersonCost().premiumSets([]);
                        servicebase.personCostCalculationUpdate(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                            .done(function(res: Array<any>) {
                                nts.uk.ui.windows.setShared('isEdited', 0);
                                nts.uk.ui.block.clear();
                                nts.uk.ui.windows.close();
                            })
                            .fail(function(res) {
                                nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});       
                            });
                    }
                } else {
                    if(self.deleteAble()) { 
                        nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function(){ 
                            servicebase.personCostCalculationDelete(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                                .done(function(res: Array<any>) {
                                    nts.uk.ui.windows.setShared('isEdited', 1);
                                    nts.uk.ui.block.clear();
                                    nts.uk.ui.windows.close(); 
                                })
                                .fail(function(res) {
                                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function(){nts.uk.ui.block.clear();});     
                                }); 
                        }).ifNo(function(){
                            nts.uk.ui.block.clear();        
                        });
                    } else{ 
                        $("#startDateInput").ntsError('set', {messageId:"Msg_128"});
                        nts.uk.ui.block.clear();
                    }
                }
            }
            
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput").ntsError('clear');
                nts.uk.ui.windows.close();   
            }
        }
    }
}