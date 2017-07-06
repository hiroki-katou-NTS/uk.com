module nts.uk.at.view.kmk005.k {
    export module viewmodel {
        import kService = nts.uk.at.view.kmk005.k.service;
        export class ScreenModel {
            columns: KnockoutObservableArray<NtsGridListColumn>;
            rootWorkTimeList: Array<WorkTime>;
            workTimeList: KnockoutObservableArray<WorkTime>;
            selectedWorkTime: KnockoutObservable<WorkTime>;
            selectedWorkTimeCode: KnockoutObservable<string>;
            currentBonusPaySetting: KnockoutObservable<BonusPaySetting>; 
            currentWorkingTimesheetBonusPaySet: KnockoutObservable<WorkingTimesheetBonusPaySet>;
            startTimeOption: KnockoutObservable<number>;
            startTime: KnockoutObservable<number>;
            endTimeOption: KnockoutObservable<number>;
            endTime: KnockoutObservable<number>;
            isUpdate: boolean;
            constructor() {
                var self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KDL001_12'), prop: 'code', width: 70 },
                    { headerText: nts.uk.resource.getText('KDL001_13'), prop: 'name', width: 130 },
                    { headerText: nts.uk.resource.getText('KDL001_14'), prop: 'workTime1', width: 200 },
                    { headerText: nts.uk.resource.getText('KDL001_15'), prop: 'workTime2', width: 200 },
                    { headerText: nts.uk.resource.getText('KDL001_17'), prop: 'flagSet' }
                ]);
                self.startTimeOption = ko.observable(1);
                self.startTime = ko.observable('');
                self.endTimeOption = ko.observable(1); 
                self.endTime = ko.observable('');  
                self.workTimeList = ko.observableArray([]);
                self.selectedWorkTimeCode = ko.observable('');
                self.currentWorkingTimesheetBonusPaySet = ko.observable(new WorkingTimesheetBonusPaySet('','',''));
                self.selectedWorkTime = ko.observable(new WorkTime('','','','',''));
                self.currentBonusPaySetting = ko.observable(new BonusPaySetting('','',''));
                self.isUpdate = true;
                self.selectedWorkTimeCode.subscribe((value) => {
                    self.selectedWorkTime(_.find(self.workTimeList(),(item)=>{return _.isEqual(item.code,value);}));
                    self.getWorkingTimesheetBonusPaySet(value);
                });
            }
            
            start(): JQueryPromise<any> { 
                nts.uk.ui.block.invisible();
                var self = this;
                self.workTimeList.removeAll();
                var dfd = $.Deferred();
                var dfdGetWorkTime = kService.getWorkTime();
                var dfdGetWorkingTimesheetBonusPaySet = kService.getWorkingTimesheetBonusPaySet();
                $.when(dfdGetWorkTime, dfdGetWorkingTimesheetBonusPaySet).done((workTimeData, workingTimesheetBonusPaySetData) => {
                    self.rootWorkTimeList = workTimeData; 
                    _.forEach(self.rootWorkTimeList,(item) => {
                        let flagSet = true;
                        let flag = _.find(workingTimesheetBonusPaySetData, (o) => {
                            return _.isEqual(item.code, o.workingTimesheetCode);   
                        });  
                        if(nts.uk.util.isNullOrUndefined(flag)) flagSet = false;
                        self.workTimeList.push(new WorkTime(item.code, item.name, item.workTime1, item.workTime2, flagSet.toString()));
                    });
                    self.selectedWorkTimeCode(_.first(self.workTimeList()).code);
                    nts.uk.ui.block.clear();
                    dfd.resolve(); 
                }).fail(function(res) { 
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function(){nts.uk.ui.block.clear();});
                });
                return dfd.promise();
            }
            
            getWorkingTimesheetBonusPaySet(value: string): JQueryPromise<any>{ 
                nts.uk.ui.block.invisible();
                var self = this;
                var dfd = $.Deferred();
                kService.getWorkingTimesheetBonusPaySetByCode(value).done((data) => {
                    if(!nts.uk.util.isNullOrEmpty(data)) {
                        self.currentWorkingTimesheetBonusPaySet(data);
                        kService.getBonusPaySettingByCode(data.bonusPaySettingCode).done((subData)=>{self.currentBonusPaySetting(subData)});
                        self.isUpdate = true;
                    } else {
                        self.currentWorkingTimesheetBonusPaySet(new WorkingTimesheetBonusPaySet('','',''));
                        self.currentBonusPaySetting(new BonusPaySetting('','',''));    
                        self.isUpdate = false;
                    }
                    nts.uk.ui.block.clear();
                    dfd.resolve(); 
                }).fail(function(res) { 
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function(){nts.uk.ui.block.clear();});
                });
                return dfd.promise();
            }
            
            submitData(isUpdate: boolean): void {
                nts.uk.ui.block.invisible();
                var self = this;
                if(isUpdate){
                    kService.updateWorkingTimesheetBonusPaySet(self.createCommand()).done((data)=>{
                        self.start();  
                        nts.uk.ui.block.clear();        
                    }).fail((res)=>{
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    });        
                } else {
                    kService.insertWorkingTimesheetBonusPaySet(self.createCommand()).done((data)=>{
                        self.start(); 
                        nts.uk.ui.block.clear();         
                    }).fail((res)=>{
                        nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                    });    
                }        
            }
            
            deleteData(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                kService.deleteWorkingTimesheetBonusPaySet(self.createCommand()).done((data)=>{
                    self.start();   
                    nts.uk.ui.block.clear();       
                }).fail((res)=>{
                    nts.uk.ui.dialog.alertError(res.message).then(function(){nts.uk.ui.block.clear();});
                });
            }
            
            createCommand(): any{
                var self = this;
                return {
                    companyId: self.currentBonusPaySetting().companyId(),
                    workingTimesheetCode: self.selectedWorkTimeCode(),
                    bonusPaySettingCode: self.currentBonusPaySetting().code()   
                }    
            }
        }
        
        class WorkTime {
            code: string;
            name: string;
            workTime1: string;
            workTime2: string;
            flagSet: string;
            constructor(code: string, name: string, workTime1: string, workTime2: string, flagSet: string){
                this.code = code;
                this.name = name;
                this.workTime1 = workTime1;
                this.workTime2 = workTime2;
                this.flagSet = flagSet;    
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
        
        class WorkingTimesheetBonusPaySet {
            companyId: KnockoutObservable<string>;
            workingTimesheetCode: KnockoutObservable<string>;
            bonusPaySettingCode: KnockoutObservable<string>;
            constructor(companyId: string, workingTimesheetCode: string, bonusPaySettingCode: string){
                this.companyId = ko.observable(companyId);
                this.workingTimesheetCode = ko.observable(workingTimesheetCode);
                this.bonusPaySettingCode = ko.observable(bonusPaySettingCode);    
            }    
        }
    }
}