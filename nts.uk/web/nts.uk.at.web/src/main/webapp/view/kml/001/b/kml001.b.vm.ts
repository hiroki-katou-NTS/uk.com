module nts.uk.at.view.kml001.b {
    export module viewmodel {
        import servicebase = kml001.shr.servicebase;
        import vmbase = kml001.shr.vmbase;
        export class ScreenModel {
            premiumItemList: KnockoutObservableArray<vmbase.PremiumItem>;
            isInsert: Boolean;
            allUse: KnockoutObservable<number>;
            constructor() {
                var self = this;
                self.premiumItemList = ko.observableArray([]);   
                self.isInsert = nts.uk.ui.windows.getShared('isInsert');
                self.textKML001_18 = nts.uk.resource.getText("KML001_18",[__viewContext.primitiveValueConstraints.PremiumName.maxLength/2]);
            }
            
            getText(index): string {
                return nts.uk.resource.getText("KML001_"+(30+index));    
            }
            
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                servicebase.premiumItemSelect()
                    .done(function(data) {
                        data.forEach(function(item){
                            self.premiumItemList.push(
                                new vmbase.PremiumItem(
                                    item.companyID,
                                    item.id,
                                    item.attendanceID,
                                    item.name,
                                    item.displayNumber,
                                    item.useAtr
                                ));
                        });
                        self.allUse = ko.pureComputed(function(){
                            let x: number = 0;
                            self.premiumItemList().forEach(function(item) { 
                                x+=parseInt(item.useAtr().toString());
                            });        
                            return x;
                        });
//                        self.allUse.subscribe(function(value){
//                            if(value==0) {
//                                $("#premium-set-tbl-b").ntsError('set', {messageId:"Msg_66"});     
//                            } else {
//                                $("#premium-set-tbl-b").ntsError('clear');  
//                            }    
//                        });
                        dfd.resolve(); 
                    })
                    .fail(function(res) { 
                        nts.uk.ui.dialog.alertError(res.message);
                        dfd.reject(res); 
                    });
                return dfd.promise();
            }
            
            /**
             * save data and close dialog
             */
            submitAndCloseDialog(): void {
                var self = this;
                let premiumItemListCommand = [];
                ko.utils.arrayForEach(self.premiumItemList(), function(item) { 
                    premiumItemListCommand.push(ko.mapping.toJS(item));
                });
                servicebase.premiumItemUpdate(premiumItemListCommand)
                    .done(function(res: Array<any>) {
                        if(self.isInsert){
                            nts.uk.ui.windows.setShared('premiumSets', ko.mapping.toJS(premiumItemListCommand));    
                        }
                        nts.uk.ui.windows.setShared('updatePremiumSeting', true);
                        nts.uk.ui.windows.close();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                    });
            }
            
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                nts.uk.ui.windows.close();   
            }
        }
    }
}