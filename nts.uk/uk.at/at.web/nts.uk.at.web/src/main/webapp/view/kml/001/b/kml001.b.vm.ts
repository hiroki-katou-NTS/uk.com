module nts.uk.at.view.kml001.b {
    export module viewmodel {
        import servicebase = kml001.shr.servicebase;
        import vmbase = kml001.shr.vmbase;
        export class ScreenModel {
            rootList: Array<vmbase.PremiumItem>;
            premiumItemList: KnockoutObservableArray<vmbase.PremiumItem>;
            isInsert: Boolean;
            allUse: KnockoutObservable<number>;
            browser: KnockoutObservable<boolean> = ko.observable(true);
            constructor() {
                var self = this;
                self.premiumItemList = ko.observableArray([]);   
                self.isInsert = nts.uk.ui.windows.getShared('isInsert');
                self.textKML001_18 = nts.uk.resource.getText("KML001_28") + "("
                        + nts.uk.text.getCharType('PremiumName').viewName +
                        + __viewContext.primitiveValueConstraints.PremiumName.maxLength/2
                        + "文字)";
            }
            
            getText(index): string {
                return nts.uk.resource.getText("KML001_"+(30+index));    
            }
            
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                let version = nts.uk.util.browser.version;
                if (version.slice(0,2) == 'IE'){
                    self.browser(true);
                }else{
                    self.browser(false);
                }
                var dfd = $.Deferred();
                servicebase.premiumItemSelect()
                    .done(function(data) {
                        data.forEach(function(item){
                            self.premiumItemList.push(
                                new vmbase.PremiumItem(
                                    item.companyID,
                                    item.displayNumber,
                                    item.name,
                                    item.useAtr,
                                    false
                                ));
                        });
                        self.rootList = _.clone(ko.mapping.toJS(self.premiumItemList()));
                        self.allUse = ko.pureComputed(function(){
                            let x: number = 0;
                            self.premiumItemList().forEach(function(item) { 
                                x+=parseInt(item.useAtr().toString());
                            });        
                            return x;
                        });
                        nts.uk.ui.block.clear();
                        dfd.resolve(); 
                    })
                    .fail(function(res) { 
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                        dfd.reject(res); 
                    });
                return dfd.promise();
            }
            
            /**
             * save data and close dialog
             */
            submitAndCloseDialog(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                let premiumItemListCommand = [];
                let rootLists = self.rootList;
                ko.utils.arrayForEach(self.premiumItemList(), function(item, index) { 
                    if(item.useAtr() == 1) $(".premiumName").eq(item.displayNumber()-1).trigger("validate");
                    if(ko.mapping.toJSON(item)!=ko.mapping.toJSON(rootLists[item.displayNumber()-1])){
                        item.isChange(true);        
                    }
                    premiumItemListCommand.push(ko.mapping.toJS(item));
                });
                if (!nts.uk.ui.errors.hasError()){
                servicebase.premiumItemUpdate(premiumItemListCommand)
                    .done(function(res: Array<any>) {
                        if(self.isInsert){
                            nts.uk.ui.windows.setShared('premiumSets', ko.mapping.toJS(premiumItemListCommand));    
                        }
                        nts.uk.ui.windows.setShared('updatePremiumSeting', true);
                        nts.uk.ui.block.clear();
                        nts.uk.ui.windows.close();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function(){nts.uk.ui.block.clear();});
                    });
                } else nts.uk.ui.block.clear();
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