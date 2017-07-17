module ksm002.c {
    export module viewmodel {
        export class ScreenModel {
            rootList: Array<SpecificDateItem>;
            specificDateItem: KnockoutObservableArray<SpecificDateItem>;
            allUse: KnockoutObservable<number>;
            textKSM002_39: string;
            constructor() {
                var self = this;
                self.specificDateItem = ko.observableArray([]);   
                self.textKSM002_39 = nts.uk.resource.getText("KSM002_39",[__viewContext.primitiveValueConstraints.SpecificName.maxLength/2]);
            }
            
            /**
             * get data on start page 
             */
            startPage(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                service.getAllSpecificDate().done(function(data) {
                        data.forEach(function(item){
                            self.specificDateItem.push(
                                new SpecificDateItem(
                                    item.timeItemId,
                                    item.useAtr,
                                    item.specificDateItemNo,
                                    item.specificName
                                ));
                        });
                    while(self.specificDateItem().length<10){
                        self.specificDateItem.push(
                                new SpecificDateItem('',1,0,''));
                    }
                    console.log(self.specificDateItem());
                        self.rootList = _.clone(ko.mapping.toJS(self.specificDateItem()));
                        self.allUse = ko.pureComputed(function(){
                            let x: number = 0;
                            self.specificDateItem().forEach(function(item) { 
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
                let lstSpecificDateItem : Array<SpecificDateItemCommand> = [];
                let rootLists = self.rootList;
                $(".specificName").trigger("validate");
                ko.utils.arrayForEach(self.specificDateItem(), function(item, index) { 
                    lstSpecificDateItem.push(new SpecificDateItemCommand(item.timeItemId(),item.useAtr(),item.specificDateItemNo(),item.specificName()));
                });
                self.specificDateItem
                if (!nts.uk.ui.errors.hasError()){
                service.updateSpecificDate(lstSpecificDateItem)
                    .done(function(res: Array<any>) {
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
    export class SpecificDateItem{
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
        export class SpecificDateItemCommand{
        timeItemId: string;
        useAtr: number;
        specificDateItemNo: number;
        specificName: string;
        constructor(timeItemId: string,useAtr: number,specificDateItemNo: number,specificName: string){
            this.timeItemId = timeItemId;
            this.useAtr = useAtr;
            this.specificDateItemNo = specificDateItemNo;
            this.specificName = specificName;
        }
    }
}