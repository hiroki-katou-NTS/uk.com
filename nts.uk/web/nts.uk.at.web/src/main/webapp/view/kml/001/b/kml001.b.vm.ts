module kml001.b.viewmodel {
    import servicebase = kml001.shr.servicebase;
    import vmbase = kml001.shr.vmbase;
    export class ScreenModel {
        extraTimeItemList: KnockoutObservableArray<vmbase.ExtraTimeItem>;
        isInsert: Boolean;
        constructor() {
            var self = this;
            self.extraTimeItemList = ko.observableArray([]);   
            self.isInsert = nts.uk.ui.windows.getShared('personCostList');
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            servicebase.extraTimeSelect()
                .done(function(data) {
                    data.forEach(function(item){
                        self.extraTimeItemList.push(
                            new vmbase.ExtraTimeItem(
                                item.companyID,
                                item.extraItemID,
                                item.premiumName,
                                item.timeItemID,
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
            let extraItemListCommand = [];
            ko.utils.arrayForEach(self.extraTimeItemList(), function(item) { extraItemListCommand.push(ko.mapping.toJS(item)) });
            servicebase.extraTimeUpdate(extraItemListCommand)
                .done(function(res: Array<any>) {
                    if(self.isInsert){
                        nts.uk.ui.windows.setShared('premiumSets', ko.mapping.toJS(extraItemListCommand));    
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