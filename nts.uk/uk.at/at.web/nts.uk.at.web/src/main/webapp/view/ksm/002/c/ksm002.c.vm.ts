module ksm002.c {
    export module viewmodel {
        export class ScreenModel {
            rootList: Array<SpecificDateItemCommand>;
            premiumItemList: KnockoutObservableArray<SpecificDateItem>;
            isInsert: Boolean;
            allUse: KnockoutObservable<number>;
            textKSM002_38: string;
            constructor() {
                var self = this;
                self.premiumItemList = ko.observableArray([]);   
                self.isInsert = nts.uk.ui.windows.getShared('isInsert');
                self.textKSM002_38 = nts.uk.resource.getText("KSM002_38",[__viewContext.primitiveValueConstraints.PremiumName.maxLength/2]);
            }
            
//            getText(index): string {
//                return nts.uk.resource.getText("KML001_"+(30+index));    
//            }
            /**
             * get data on start page 
             */
            startPage(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                service.getAllSpecificDate().done(function(data) {
                        data.forEach(function(item){
                            self.premiumItemList.push(
                                new SpecificDateItemCommand(
                                    item.timeItemId,
                                    item.useAtr,
                                    item.specificDateItemNo,
                                    item.specificName
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
                let lstSpecificDateItem : Array<SpecificDateItemCommand>;
                let rootLists = self.rootList;
                $(".premiumName").trigger("validate");
                ko.utils.arrayForEach(self.premiumItemList(), function(item, index) { 
//                    if(ko.mapping.toJSON(item)!=ko.mapping.toJSON(rootLists[index])){
////                        item.isChange(true);        
//                    }
                    lstSpecificDateItem= self.premiumItemList();
                });
                self.premiumItemList
                if (!nts.uk.ui.errors.hasError()){
                service.updateSpecificDate(lstSpecificDateItem)
                    .done(function(res: Array<any>) {
                        if(self.isInsert){
                        }
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
    export class SpecificDateItemCommand{
        timeItemId: KnockoutObservable<string>;
        useAtr: KnockoutObservable<number>;
        specificDateItemNo: KnockoutObservable<number>;
        specificName: KnockoutObservable<string>;
        constructor(timeItemId: string,useAtr: number,specificDateItemNo: number,specificName: string){
            this.timeItemId = ko.observable(timeItemId);
            this.useAtr = ko.observable(useAtr);
            this.specificDateItemNo = ko.observable(specificDateItemNo);
            this.specificName = ko.observable(specificName);
        }
    }
}