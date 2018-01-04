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
                items: KnockoutObservableArray<share.AlarmPatternSettingDto>;
                columns2: KnockoutObservableArray<any>;
                currentCode: KnockoutObservable<string>;  
                createMode : KnockoutObservable<boolean>;
                alarmCode : KnockoutObservable<string>;
                alarmName : KnockoutObservable<string>;
                tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;  
                selectedTab: KnockoutObservable<string>;                      
            constructor() {
                let self = this;
                self.items = ko.observableArray([]);                
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: getText('KAL004_12'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: getText('KAL004_13'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: getText('KAL004_14'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
                            
                self.columns2 = ko.observableArray([
                { headerText: 'コード', key: 'alarmPatternCD', width: 80 },
                { headerText: '名称', key: 'alarmPatternName', width: 220 },
                ]);
                self.alarmCode = ko.observable('');
                self.alarmName = ko.observable('');
                self.createMode = ko.observable(false);
                self.currentCode = ko.observable('');                
                self.currentCode.subscribe((newV)=>{
                    if(newV =='') self.createMode(true);
                    else {                        
                        self.createMode(false);
                        let currentAlarm = _.find(self.items(), function(a){return a.alarmPatternCD == newV});
                        self.alarmCode(currentAlarm.alarmPatternCD);
                        self.alarmName(currentAlarm.alarmPatternName);
                    }                        
                });
            }
            
            public  startPage(): any{
                let self = this;
                service.getAlarmPattern().done((res) =>{
                    if(res.length>0){
                        self.items(res);
                        self.currentCode(res[0].alarmPatternCD);   
                    }
                }).fail((error) =>{
                    nts.uk.ui.dialog.alert({ messageId: error.messageId });
                });
            }
 
    }   
}

