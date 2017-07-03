module nts.uk.at.view.kmk005.b {
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            timeItemSpecList: KnockoutObservableArray<TimeItem>;
             timeItemList: KnockoutObservableArray<TimeItem>;
           // roundingRules: KnockoutObservableArray<any>;         
           // name:KnockoutObservable<string>;
         //   useAtr: KnockoutObservable<number>;
            constructor() {
                var self = this;
                 
               /*   self.premiumItemList = ko.observableArray([]);
             self.premiumItemList = ko.observableArray([{name: "item1"},{name: "item2"},{name: "item3"},{name: "item4"},{name: "item5"},{name: "item6"},{name: "item7"},{name: "item8"},{name: "item9"},{name: "item10"}]);
                
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText('KMK005_17'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText('KMK005_18'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');

                // self.premiumItemList = ko.observableArray([]);
                self.roundingRules = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('Enum_UseAtr_Use') },
                    { code: '0', name: nts.uk.resource.getText('Enum_UseAtr_NotUse') }
                ])
               // self.useAtr = ko.observable(0);
                
                
                
                //self.name = ko.observable("abc");
              
                
             //  self.premiumItemList([{name: "item1"},{name: "item2"},{name: "item3"},{name: "item4"},{name: "item5"},{name: "item6"},{name: "item7"},{name: "item8"},{name: "item9"},{name: "item10"}]);
            
              //  ko.applyBindings();
                */
                
                
                 self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText('KMK005_17'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText('KMK005_18'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.timeItemList = ko.observableArray([]);
                self.timeItemSpecList = ko.observableArray([]);
                  
                    
            }
            
            
         
            
              

            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                 service.getListSpecialBonusPayTimeItem().done(function(item: Array<any>){
                            window.alert("done");
                        if (item === undefined || item.length == 0) {
                                       for (i = 0; i < 10; i++) { 
                     self.timeItemSpecList.push(
                                new TimeItem(
                                        "持定加給"+(i+1),1,nts.uk.resource.getText("KMK005_"+(22+i)),1,"",""
                                ));
                    }
                                    }else{
                             item.forEach(function(item){
                                 self.timeItemSpecList.push(new TimeItem(item.timeItemName,item.useAtr,item.timeItemNo,item.timeItemTypeAtr,item.companyId,item.timeItemId));
                             })
                        
                        }
                    }).fail(function(res){
                        window.alert("fail");
                    });
                
                 service.getListBonusPTimeItem().done(function(item: Array<any>){
                            window.alert("done");
                        if (item === undefined || item.length == 0) {
                                       for (i = 0; i < 10; i++) { 
                     self.timeItemList.push(
                                new TimeItem(
                                        "加給"+(i+1),1,nts.uk.resource.getText("KMK005_"+(22+i)),0,"",""
                                ));
                    }
                                    }else{
                             item.forEach(function(item){
                                 self.timeItemList.push(new TimeItem(item.timeItemName,item.useAtr,item.timeItemNo,item.timeItemTypeAtr,item.companyId,item.timeItemId));
                             })
                        
                        }
                    }).fail(function(res){
                        window.alert("fail");
                    });
                
                
                
                
                
//                 service.getListBonusPayTimeItem().done(function(res: Array<any>){
//                            window.alert("done");
//                        if (res === undefined || res.length == 0) {
//                                     for (i = 0; i < 10; i++) { 
//                 self.timeItemList.push(
//                                new TimeItem(
//                                        "加給"+(i+1),1,nts.uk.resource.getText("KMK005_"+(22+i)),0
//                                ));
//                    }
//                                    }
//                    }).fail(function(res){
//                        window.alert("fail");
//                    }); 
        

                dfd.resolve();

                return dfd.promise();
            }
            //content
            /**
            * save data and close dialog
            */
            submitAndCloseDialog(): void {
                var self = this;
                
                let bonusPayTimeItemListCommand = [];
                var checkUseExist = false;
                
                 ko.utils.arrayForEach(self.timeItemList(), function(item) { 
                 if(item.useAtr()==1){
                     checkUseExist = true;
                 }
                    bonusPayTimeItemListCommand.push(ko.mapping.toJS(item));
                });
                
                               let bonusPayTimeItemSpecListCommand = [];
                
                   ko.utils.arrayForEach(self.timeItemSpecList(), function(item) { 
                    if(item.useAtr()==1){
                     checkUseExist = true;
                 }
                    bonusPayTimeItemSpecListCommand.push(ko.mapping.toJS(item));
                });
                
                  service.getListBonusPTimeItem().done(function(res: Array<any>){
                            window.alert("done");
                        if (res === undefined || res.length == 0) {
                     service.addListBonusPayTimeItem(bonusPayTimeItemListCommand);
                             service.addListBonusPayTimeItem(bonusPayTimeItemSpecListCommand);
                                    }else{
                            if(checkUseExist){
                                   service.updateListBonusPayTimeItem(bonusPayTimeItemListCommand); 
                             service.updateListBonusPayTimeItem(bonusPayTimeItemSpecListCommand);
                                }else{
                                //msg 20 item not use
                                var a ="error";
                            }
                            }
                    }).fail(function(res){
                        window.alert("fail");
                    });
                
//                let bonusPayTimeItemSpecListCommand = [];
//                
//                   ko.utils.arrayForEach(self.timeItemSpecList(), function(item) { 
//                    bonusPayTimeItemSpecListCommand.push(ko.mapping.toJS(item));
//                });
                
                
//                  service.getListSpecialBonusPayTimeItem().done(function(res: Array<any>){
//                            window.alert("done");
//                         if (res === undefined || res.length == 0) {
//                     service.addListBonusPayTimeItem(bonusPayTimeItemSpecListCommand);
//                                    }else{
//                           service.updateListBonusPayTimeItem(bonusPayTimeItemSpecListCommand); 
//                            }
//                    }).fail(function(res){
//                        window.alert("fail");
//                    });
//                
                
                self.closeDialog();
            }

            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                nts.uk.ui.windows.close();
            }
            //


        }
        
        export class TimeItem {
            timeItemName: KnockoutObservable<string>;
            useAtr: KnockoutObservable<number>;
            timeItemNo: KnockoutObservable<number>;
            timeItemTypeAtr: KnockoutObservable<number>;
            cid:KnockoutObservable<string>;
            timeItemId:KnockoutObservable<string>;
            constructor( timeItemName: string, useAtr: number, timeItemNo: number,timeItemTypeAtr : number, cid: string, timeItemId: string) {
                var self = this;
                self.timeItemName = ko.observable(timeItemName);
                self.useAtr = ko.observable(useAtr);
                self.timeItemNo = ko.observable(timeItemNo);
                self.timeItemTypeAtr= ko.observable(timeItemTypeAtr);
                self.cid=ko.observable(cid);
                self.timeItemId=ko.observable(timeItemId);
            }
        }   
        
    }
}