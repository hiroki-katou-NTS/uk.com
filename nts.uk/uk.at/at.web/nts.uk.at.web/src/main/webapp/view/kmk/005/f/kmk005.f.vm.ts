module nts.uk.at.view.kmk005.f {
    export module viewmodel {
        import fService = nts.uk.at.view.kmk005.f.service;
        export class ScreenModel {
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            time: KnockoutObservable<string>;
            bonusPaySettingList: KnockoutObservableArray<BonusPaySetting>;
            bonusPayTimeItemList: KnockoutObservableArray<BonusPaySetting>;
            specBonusPayTimeItemList: KnockoutObservableArray<BonusPaySetting>;
            specDateItem: KnockoutObservableArray<SpecDateItem>; 
            currentBonusPaySetting: KnockoutObservable<BonusPaySetting>; 
            currentBonusPayTimesheets: KnockoutObservableArray<BonusPayTimesheet>; 
            currentSpecBonusPayTimesheets: KnockoutObservableArray<SpecBonusPayTimesheet>; 
            isUpdate: boolean;
            constructor() {
                var self = this;
                self.tabs = ko.observableArray([
                    {id: 'tab-1', title: nts.uk.resource.getText("KMK005_17"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-2', title: nts.uk.resource.getText("KMK005_18"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
                ]);
                self.selectedTab = ko.observable('tab-1');  
                self.time = ko.observable(''); 
                self.bonusPaySettingList = ko.observableArray([]);
                self.bonusPayTimeItemList = ko.observableArray([]);
                self.specBonusPayTimeItemList = ko.observableArray([]);
                self.currentBonusPaySetting = ko.observable(new BonusPaySetting('','',''));
                self.currentBonusPayTimesheets = ko.observableArray([]);
                self.currentSpecBonusPayTimesheets = ko.observableArray([]);
                self.isUpdate = true;
                self.currentBonusPaySetting.subscribe(function(value){
                    if(value.code()!=""){
                        self.getBonusPayTimesheets(value.code());       
                        self.isUpdate = true;
                    } else {
                        self.isUpdate = false;
                    }
                });
            }
            
            startPage(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                let dfdBonusPaySetting = fService.getBonusPaySetting();
                let dfdBonusPayTimeItem = fService.getBonusPayTimeItem();
                let dfdSpecBonusPayTimeItem = fService.getSpecBonusPayTimeItem();
                let dfdSpecDateItem = fService.getSpecDateItem();
                $.when(dfdBonusPaySetting, dfdBonusPayTimeItem, dfdSpecBonusPayTimeItem, dfdSpecDateItem)
                .done((dfdBonusPaySettingData, dfdBonusPayTimeItemData, dfdSpecBonusPayTimeItemData, dfdSpecDateItemData) =>{
                    if(dfdBonusPaySettingData.length!=0) self.isUpdate = true;
                    else self.isUpdate = false;
                    self.bonusPaySettingList(dfdBonusPaySettingData);
                    self.bonusPayTimeItemList(dfdBonusPayTimeItemData);
                    self.specBonusPayTimeItemList(dfdSpecBonusPayTimeItemData);
                    self.specDateItem(dfdSpecDateItemData);            
                    self.currentBonusPaySetting(_.first(self.bonusPaySettingList()));
                    nts.uk.ui.block.clear();
                }).fail((res1,res2,res3,res4) =>{
                    nts.uk.ui.dialog.alertError(res1.message+'\n'+res2.message+'\n'+res3.message+'\n'+res4.message).then(function(){nts.uk.ui.block.clear();});
                    dfd.reject();        
                })
                return dfd.promise();
            }
            
            getBonusPaySetting(): JQueryPromise<any> {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                fService.getBonusPaySetting()
                .done((data) =>{
                    if(data.length!=0) self.isUpdate = true;
                    else self.isUpdate = false;
                    self.bonusPaySettingList(data);     
                    self.currentBonusPaySetting(_.first(self.bonusPaySettingList()));
                    nts.uk.ui.block.clear();
                }).fail((res) =>{
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    dfd.reject();        
                })
                return dfd.promise();     
            }
            
            getBonusPayTimesheets(code: string): JQueryPromise<any>  {
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                let dfdTimesheetList = fService.getBonusPayTimesheet(code);
                let dfdGetSpecTimesheetList = fService.getSpecBonusPayTimesheet(code);
                $.when(dfdTimesheetList, dfdGetSpecTimesheetList).done((dfdTimesheetListData, dfdGetSpecTimesheetListData) => {
                    self.currentBonusPayTimesheets.removeAll();
                    dfdTimesheetListData.forEach(function(item){
                        self.currentBonusPayTimesheets.push(
                            new BonusPayTimesheet(
                                item.companyId,
                                item.timeSheetNO,
                                item.useAtr,
                                item.bonusPaySettingCode,
                                item.timeItemId,
                                item.startTime,
                                item.endTime,
                                item.roundingTimeAtr,
                                item.roundingAtr)
                        );       
                    });    
                    self.currentSpecBonusPayTimesheets.removeAll();
                    dfdGetSpecTimesheetListData.forEach(function(item){
                        self.currentSpecBonusPayTimesheets.push(
                            new SpecBonusPayTimesheet(
                                item.companyId,
                                item.timeSheetNO,
                                item.useAtr,
                                item.bonusPaySettingCode,
                                item.timeItemId,
                                item.startTime,
                                item.endTime,
                                item.roundingTimeAtr,
                                item.roundingAtr,
                                item.specialDateItemNO )
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
            
            createData(): void {
                var self = this;
                self.isUpdate = false;
                self.currentBonusPaySetting(new BonusPaySetting('','',''));
                self.currentBonusPayTimesheets.removeAll();
                self.currentSpecBonusPayTimesheets.removeAll();
                for(let i=0;i<10;i++){
                    self.currentBonusPayTimesheets.push(new BonusPayTimesheet('',1,1,'',i.toString(),0,0,0,0)); 
                    self.currentSpecBonusPayTimesheets.push(new SpecBonusPayTimesheet('',1,1,'',i.toString(),0,0,0,0,1));       
                }  
            }
            
            submitData(isUpdate: boolean): void {
                nts.uk.ui.block.invisible();
                var self = this;
                if(isUpdate){
                    fService.insertBonusPaySetting(
                        self.createCommand(self.currentBonusPaySetting(),self.currentBonusPayTimesheets(),self.currentSpecBonusPayTimesheets())
                    ).done((data)=>{
                        self.getBonusPaySetting();   
                        nts.uk.ui.block.clear();        
                    }).fail((res)=>{
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    });        
                } else {
                    fService.updateBonusPaySetting(
                        self.createCommand(self.currentBonusPaySetting(),self.currentBonusPayTimesheets(),self.currentSpecBonusPayTimesheets())
                    ).done((data)=>{
                        self.getBonusPaySetting(); 
                        nts.uk.ui.block.clear();         
                    }).fail((res)=>{
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    });    
                }        
            }
            
            deleteData(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                fService.deleteBonusPaySetting(
                    self.createCommand(self.currentBonusPaySetting(),self.currentBonusPayTimesheets(),self.currentSpecBonusPayTimesheets())
                ).done((data)=>{
                    self.getBonusPaySetting();  
                    nts.uk.ui.block.clear();       
                }).fail((res)=>{
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                });
            }
            
            createCommand(bonusPaySetting: BonusPaySetting, bonusPayTimesheets: Array<BonusPayTimesheet>, specBonusPayTimesheets: Array<SpecBonusPayTimesheet>): any {
                var self = this;
                return {
                    companyId: bonusPaySetting.companyId(),
                    code: bonusPaySetting.code(),
                    name: bonusPaySetting.name(),
                    lstBonusPayTimesheet: ko.mapping.toJS(bonusPayTimesheets()),
                    lstSpecBonusPayTimesheet: ko.mapping.toJS(specBonusPayTimesheets())
                }         
            }
        }      
        
        class BonusPaySetting { 
            companyId: KnockoutObservable<string>; 
            name: KnockoutObservable<string>;
            code: KnockoutObservable<string>;  
            constructor(companyId: string, name: string, code: string ){
                this.companyId = ko.observable(companyId);
                this.name = ko.observable(name);
                this.code = ko.observable(code);    
            }
        }
        
        class BonusPayTimeItem {
            companyId: KnockoutObservable<string>;
            timeItemId: KnockoutObservable<string>;
            useAtr: KnockoutObservable<number>;
            timeItemName: KnockoutObservable<string>;
            timeItemNo: KnockoutObservable<number>;
            timeItemTypeAtr: KnockoutObservable<number>;
            constructor(companyId: string, timeItemId: string, useAtr: number, timeItemName: string, timeItemNo: number, timeItemTypeAtr: number){
                this.companyId = ko.observable(companyId);
                this.timeItemId = ko.observable(timeItemId);
                this.useAtr = ko.observable(useAtr);
                this.timeItemName = ko.observable(timeItemName);
                this.timeItemNo = ko.observable(timeItemNo);
                this.timeItemTypeAtr = ko.observable(timeItemTypeAtr);
            }
        }
        
        class SpecDateItem {
            companyId: KnockoutObservable<string>; 
            useAtr: KnockoutObservable<number>;
            timeItemId: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            specDateItemId: KnockoutObservable<string>;     
            constructor(companyId: string, useAtr: number, timeItemId: string, name: string, specDateItemId: string){
                this.companyId = ko.observable(companyId);
                this.useAtr = ko.observable(useAtr);
                this.timeItemId = ko.observable(timeItemId);
                this.name = ko.observable(name);
                this.specDateItemId = ko.observable(specDateItemId);     
            }
        }
        
        class BonusPayTimesheet {
            companyId: KnockoutObservable<string>;
            timeSheetNO: KnockoutObservable<number>;
            useAtr: KnockoutObservable<number>;
            bonusPaySettingCode: KnockoutObservable<string>;
            timeItemId: KnockoutObservable<string>;
            startTime: KnockoutObservable<number>;
            endTime: KnockoutObservable<number>;
            roundingTimeAtr: KnockoutObservable<number>;
            roundingAtr: KnockoutObservable<number>;
            constructor(companyId: string, timeSheetNO: number, useAtr: number, bonusPaySettingCode: string, timeItemId: string, 
                startTime: number, endTime: number, roundingTimeAtr: number, roundingAtr: number){
                this.companyId = ko.observable(companyId);
                this.timeSheetNO = ko.observable(timeSheetNO);
                this.useAtr = ko.observable(useAtr);
                this.bonusPaySettingCode = ko.observable(bonusPaySettingCode);
                this.timeItemId = ko.observable(timeItemId);
                this.startTime = ko.observable(startTime);
                this.endTime = ko.observable(endTime);
                this.roundingTimeAtr = ko.observable(roundingTimeAtr);    
                this.roundingAtr = ko.observable(roundingAtr);
            }
        }
        
        class SpecBonusPayTimesheet {
            companyId: KnockoutObservable<string>;
            timeSheetNO: KnockoutObservable<number>;
            useAtr: KnockoutObservable<number>;
            bonusPaySettingCode: KnockoutObservable<string>;
            timeItemId: KnockoutObservable<string>;
            startTime: KnockoutObservable<number>;
            endTime: KnockoutObservable<number>;
            roundingTimeAtr: KnockoutObservable<number>;
            roundingAtr: KnockoutObservable<number>;
            specialDateItemNO: KnockoutObservable<number>;
            constructor(companyId: string, timeSheetNO: number, useAtr: number, bonusPaySettingCode: string, timeItemId: string,
            startTime: number, endTime: number, roundingTimeAtr: number, roundingAtr: number, specialDateItemNO: number){
                this.companyId = ko.observable(companyId);
                this.timeSheetNO = ko.observable(timeSheetNO);
                this.useAtr = ko.observable(useAtr);
                this.bonusPaySettingCode = ko.observable(bonusPaySettingCode);
                this.timeItemId = ko.observable(timeItemId);
                this.startTime = ko.observable(startTime);
                this.endTime = ko.observable(endTime);
                this.roundingTimeAtr = ko.observable(roundingTimeAtr);
                this.roundingAtr = ko.observable(roundingAtr);
                this.specialDateItemNO = ko.observable(specialDateItemNO);
            }
        }
    }
}