module kml001.b.viewmodel {
    import servicebase = kml001.shr.servicebase;
    import vmbase = kml001.shr.vmbase;
    export class ScreenModel {
        premiumItemList: KnockoutObservableArray<vmbase.PremiumItem>;
        isInsert: Boolean;
        constructor() {
            var self = this;
            self.premiumItemList = ko.observableArray([]);   
            self.isInsert = nts.uk.ui.windows.getShared('isInsert');
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            servicebase.premiumItemSelect()
                .done(function(data) {
                    data.forEach(function(item){
                        self.premiumItemList.push(
                            new vmbase.PremiumItem(
                                item.companyID,
                                item.iD,
                                item.attendanceID,
                                item.name,
                                item.displayNumber,
                                item.useAtr
                            ));
                    });
                    dfd.resolve();
                })
                .fail(function(res) { 
                    dfd.reject(res); 
                });
            return dfd.promise();
        }
        
        submitAndCloseDialog(): void {
            var self = this;
            let premiumItemListCommand = [];
            ko.utils.arrayForEach(self.premiumItemList(), function(item) { premiumItemListCommand.push(ko.mapping.toJS(item)) });
            servicebase.premiumItemUpdate(premiumItemListCommand)
                .done(function(res: Array<any>) {
                    if(self.isInsert){
                        nts.uk.ui.windows.setShared('premiumSets', ko.mapping.toJS(premiumItemListCommand));    
                    }
                    nts.uk.ui.windows.setShared('updatePremiumSeting', true);
                    nts.uk.ui.windows.close();
                }).fail(function(res) {
                    
                });;
        }
        
        closeDialog(): void {
            nts.uk.ui.windows.close();   
        }
        
    }
}