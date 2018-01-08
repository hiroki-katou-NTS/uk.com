module kal004.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import share = kal004.share.model;
    import service = kal004.a.service; 
        export class ScreenModel {
                alarmSource: KnockoutObservableArray<share.AlarmPatternSettingDto>;
                alarmHeader: KnockoutObservableArray<any>;
                currentCode: KnockoutObservable<string>;  
                createMode : KnockoutObservable<boolean>;
                alarmCode : KnockoutObservable<string>;
                alarmName : KnockoutObservable<string>;
                tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;  
                selectedTab: KnockoutObservable<string>; 
                checkConditionList: KnockoutObservableArray<share.ModelCheckConditonCode>;
                checkHeader: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
                currentCodeListSwap: KnockoutObservableArray<any>;
                checkSource :  Array<share.ModelCheckConditonCode>;                            
            
                // SetPermission
                setPermissionModel: any = new nts.uk.at.view.kal004.tab3.viewmodel.ScreenModel();
            
            constructor() {
                let self = this;
                self.alarmSource = ko.observableArray([]);                
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: getText('KAL004_12'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: getText('KAL004_13'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: getText('KAL004_14'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
                            
                self.alarmHeader = ko.observableArray([
                { headerText: 'コード', key: 'alarmPatternCD', width: 60 },
                { headerText: '名称', key: 'alarmPatternName', width: 190 },
                ]);
                self.alarmCode = ko.observable('');
                self.alarmName = ko.observable('');
                self.createMode = ko.observable(false);
                self.currentCode = ko.observable('');                
                self.currentCode.subscribe((newV)=>{
                    if(newV =='') self.createMode(true);
                    else {                        
                        self.createMode(false);
                        let currentAlarm = _.find(self.alarmSource(), function(a){return a.alarmPatternCD == newV});
                        self.alarmCode(currentAlarm.alarmPatternCD);
                        self.alarmName(currentAlarm.alarmPatternName);
                        
                        var currentCodeListSwap1 = [];
                        var checkSource1 =self.checkSource.slice();    
                        currentAlarm.checkConList.forEach((x) =>{
                            x.checkConditionCodes.forEach((y)=>{
                                  let CD =_.find(self.checkSource, { 'category': x.alarmCategory, 'checkConditonCode': y });                          
                                  currentCodeListSwap1.push(CD.GUID);
                                            
                                 _.remove(checkSource1, function(n) {
                                              return n.category == x.alarmCategory && n.checkConditonCode==y;
                                            });                                 
                                    });    
                        });
                        self.checkConditionList(checkSource1);
                        self.currentCodeListSwap(currentCodeListSwap1);
                           

                    }                        
                });
                
                self.checkConditionList = ko.observableArray([]);
                self.checkSource = [];
                    
                self.checkHeader = ko.observableArray([
                    { headerText: getText('KAL004_21'), key: 'categoryName', width: 100 },
                    { headerText: getText('KAL004_17'), key: 'checkConditonCode', width: 50 },
                    { headerText: getText('KAL004_18'), key: 'checkConditionName', width: 200 }
                ]);
    
                self.currentCodeListSwap = ko.observableArray([]);
                
            }

            public  startPage(): any{
                let self = this;     
                
                
                service.getCheckConditionCode().done((res1) =>{                        
                    self.checkConditionList(res1);
                    self.checkSource =res1;
                    
                    service.getAlarmPattern().done((res) =>{
                        if(res.length>0){
                            self.alarmSource(res);
                            self.currentCode(res[0].alarmPatternCD);   
                        }
                    }).fail((error) =>{
                        nts.uk.ui.dialog.alert({ messageId: error.messageId });
                    });                                        
                }).fail((error1)=>{
                        nts.uk.ui.dialog.alert({ messageId: error1.messageId });
                });                
                
            }
 
    }
    
    
    
}

