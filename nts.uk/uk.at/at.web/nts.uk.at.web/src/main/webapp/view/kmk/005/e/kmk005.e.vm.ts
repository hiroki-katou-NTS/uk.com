module nts.uk.at.view.kmk005.e {
    export module viewmodel {
        import eService = nts.uk.at.view.kmk005.e.service;
        export class ScreenModel {
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            bonusPayTimeItemSettings: KnockoutObservableArray<BonusPayTimeItemSetting>;
            specBonusPayTimeItemSettings: KnockoutObservableArray<BonusPayTimeItemSetting>;
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
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                return self.getData();
            }
            
            getData(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                var dfdGetList = eService.getListSetting();
                var dfdGetSpecList = eService.getListSpecialSetting();
                $.when(dfdGetList, dfdGetSpecList).done((dfdListData, dfdSpecListData) => {
                    self.bonusPayTimeItemSettings.removeAll();
                    dfdListData.forEach(function(item){
                        self.bonusPayTimeItemSettings.push(
                            new BonusPayTimeItemSetting(
                                item.companytId,
                                item.timeItemId,
                                item.holidayCalSettingAtr,
                                item.overtimeCalSettingAtr,
                                item.worktimeCalSettingAtr)
                        );       
                    });    
                    self.specBonusPayTimeItemSettings.removeAll();
                    dfdSpecListData.forEach(function(item){
                        self.specBonusPayTimeItemSettings.push(
                            new BonusPayTimeItemSetting(
                                item.companytId,
                                item.timeItemId,
                                item.holidayCalSettingAtr,
                                item.overtimeCalSettingAtr,
                                item.worktimeCalSettingAtr)
                        );         
                    }); 
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
                    self.update(self.bonusPayTimeItemSettings());        
                } else {
                    self.update(self.specBonusPayTimeItemSettings());
                }        
            }
            
            update(command: Array<BonusPayTimeItemSetting>): void {
                nts.uk.ui.block.invisible();
                var self = this;
                eService.updateListSetting(ko.mapping.toJS(command))
                .done(function(){
                    self.getData().done(function(){
                        nts.uk.ui.block.clear();         
                    }).fail(function(){
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});     
                    });       
                }).fail(function(res){
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});       
                });            
            }
        }      
        
        class BonusPayTimeItemSetting {
            companyId:  KnockoutObservable<string>;
            timeItemId:  KnockoutObservable<string>;
            worktimeCalSettingAtr:  KnockoutObservable<number>;
            overtimeCalSettingAtr:  KnockoutObservable<number>;
            holidayCalSettingAtr:  KnockoutObservable<number>;  
            constructor(companyId: string, timeItemId: string, worktimeCalSettingAtr: number, overtimeCalSettingAtr: number, holidayCalSettingAtr: number){
                this.companyId = ko.observable(companyId);     
                this.timeItemId = ko.observable(timeItemId); 
                this.worktimeCalSettingAtr = ko.observable(worktimeCalSettingAtr); 
                this.overtimeCalSettingAtr = ko.observable(overtimeCalSettingAtr); 
                this.holidayCalSettingAtr = ko.observable(holidayCalSettingAtr); 
            }
        }
    }
}