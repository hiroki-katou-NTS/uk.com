module nts.uk.at.view.kmk005.e {
    export module viewmodel {
        import eService = nts.uk.at.view.kmk005.e.service;
        export class ScreenModel {
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            bonusPayTimeItemSettings: KnockoutObservableArray<BonusPayTimeItemSetting>;
            specBonusPayTimeItemSettings: KnockoutObservableArray<BonusPayTimeItemSetting>;
            listUpdate: boolean;
            specListUpdate: boolean;
            constructor() {
                var self = this;
                $('.explanAutoCalculation').html(nts.uk.resource.getText('KMK005_48').replace(/\n/g,'<br/>'));
                $('.explanNotAutoCalculation').html(nts.uk.resource.getText('KMK005_50').replace(/\n/g,'<br/>'));
                $('.explanNotAutoCalculationSpec').html(nts.uk.resource.getText('KMK005_51').replace(/\n/g,'<br/>'));
                $('.explanAutoSetOvertime').html(nts.uk.resource.getText('KMK005_53').replace(/\n/g,'<br/>'));
                self.tabs = ko.observableArray([
                    {id: 'tab-1', title: nts.uk.resource.getText("KMK005_17"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-2', title: nts.uk.resource.getText("KMK005_18"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
                ]);
                self.selectedTab = ko.observable('tab-1');  
                self.bonusPayTimeItemSettings = ko.observableArray([]);
                self.specBonusPayTimeItemSettings = ko.observableArray([]);
                self.listUpdate = true;
                self.specListUpdate = true;
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.getData().done(()=>{
                    dfd.resolve();        
                }).fail(()=>{
                    dfd.reject();    
                });
                return dfd.promise();
            }
            
            getData(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                var dfdGetItemList = eService.getListBonusPayTimeItem();
                var dfdGetSpecItemList = eService.getListSpecialBonusPayTimeItem();
                var dfdGetList = eService.getListSetting();
                var dfdGetSpecList = eService.getListSpecialSetting();
                $.when(dfdGetItemList, dfdGetSpecItemList, dfdGetList, dfdGetSpecList).done((dfdItemListData, dfdSpecItemListData, dfdListData, dfdSpecListData) => {
                    if(nts.uk.util.isNullOrEmpty(dfdItemListData)||nts.uk.util.isNullOrEmpty(dfdSpecItemListData)) {
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_131" });        
                    } else {
                        self.bonusPayTimeItemSettings.removeAll();
                        if(!nts.uk.util.isNullOrEmpty(dfdListData)){
                            dfdListData.forEach(function(item){
                                let result = _.find(dfdItemListData, (o)=>{ return o.timeItemNo == item.timeItemNo});
                                if(nts.uk.util.isNullOrUndefined(result)){
                                    self.bonusPayTimeItemSettings.push(new BonusPayTimeItemSetting(result.timeItemNo, 0, result.timeItemName, 0, 0, 0));  
                                } else {
                                    self.bonusPayTimeItemSettings.push(
                                        new BonusPayTimeItemSetting(
                                            result.timeItemNo,
                                            item.timeItemTypeAtr,
                                            result.timeItemName,
                                            item.holidayCalSettingAtr,
                                            item.overtimeCalSettingAtr,
                                            item.worktimeCalSettingAtr)
                                    );      
                                } 
                            }); 
                            self.listUpdate = true;
                        } else {
                            dfdItemListData.forEach(function(item){
                                self.bonusPayTimeItemSettings.push(new BonusPayTimeItemSetting(item.timeItemNo, 0, item.timeItemName, 0, 0, 0));
                            }); 
                            self.listUpdate = false;
                        }
                        self.specBonusPayTimeItemSettings.removeAll();
                        if(!nts.uk.util.isNullOrEmpty(dfdSpecListData)){
                            dfdSpecListData.forEach(function(item){
                                let result = _.find(dfdSpecItemListData, (o)=>{ return o.timeItemNo == item.timeItemNo});
                                if(nts.uk.util.isNullOrUndefined(result)){
                                    self.specBonusPayTimeItemSettings.push(new BonusPayTimeItemSetting(result.timeItemNo, 1, result.timeItemName,0, 0, 0));  
                                } else {
                                    self.specBonusPayTimeItemSettings.push(
                                        new BonusPayTimeItemSetting(
                                            result.timeItemNo,
                                            item.timeItemTypeAtr,
                                            result.timeItemName,
                                            item.holidayCalSettingAtr,
                                            item.overtimeCalSettingAtr,
                                            item.worktimeCalSettingAtr)
                                    );  
                                }       
                            });   
                            self.specListUpdate = true;
                        } else {
                            dfdSpecItemListData.forEach(function(item){
                                self.specBonusPayTimeItemSettings.push(new BonusPayTimeItemSetting(item.timeItemNo, 1, item.timeItemName, 0, 0, 0));
                            }); 
                            self.specListUpdate = false;     
                        }
                    }
                    nts.uk.ui.block.clear();
                    dfd.resolve(); 
                }).fail((res1, res2) => {
                    nts.uk.ui.dialog.alertError(res1.message+'\n'+res2.message).then(function(){nts.uk.ui.block.clear();});
                    dfd.reject(); 
                });    
                return dfd.promise();
            }
            
            saveData(){
                var self = this;
                if(_.isEqual(self.selectedTab(),'tab-1')) {
                    if(self.listUpdate){
                        eService.updateListSetting(ko.mapping.toJS(self.bonusPayTimeItemSettings()));
                    } else {
                        eService.insertListSetting(ko.mapping.toJS(self.bonusPayTimeItemSettings()));
                    }       
                } else {
                    if(self.specListUpdate){
                        eService.updateListSetting(ko.mapping.toJS(self.specBonusPayTimeItemSettings()));
                    } else {
                        eService.insertListSetting(ko.mapping.toJS(self.specBonusPayTimeItemSettings())); 
                    }
                }   
                nts.uk.ui.windows.close();      
            }
            
            closeDialog(): void {
                nts.uk.ui.windows.close();   
            }
        }      
        
        class BonusPayTimeItemSetting {
            timeItemNo:  KnockoutObservable<string>;
            timeItemTypeAtr: KnockoutObservable<number>;
            name: KnockoutObservable<string>;
            holidayCalSettingAtr:  KnockoutObservable<number>;
            overtimeCalSettingAtr:  KnockoutObservable<number>;
            worktimeCalSettingAtr:  KnockoutObservable<number>;  
            constructor(timeItemNo: string, timeItemTypeAtr: number, name: string, holidayCalSettingAtr: number, overtimeCalSettingAtr: number, worktimeCalSettingAtr: number){
                this.timeItemNo = ko.observable(timeItemNo); 
                this.timeItemTypeAtr = ko.observable(timeItemTypeAtr);
                this.name = ko.observable(name);
                this.holidayCalSettingAtr = ko.observable(holidayCalSettingAtr); 
                this.overtimeCalSettingAtr = ko.observable(overtimeCalSettingAtr); 
                this.worktimeCalSettingAtr = ko.observable(worktimeCalSettingAtr);
            }
        }
    }
}