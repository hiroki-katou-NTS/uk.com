module nts.uk.at.view.kal004.a.model {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    import share = nts.uk.at.view.kal004.share.model;
    import service = nts.uk.at.view.kal004.a.service;
    export class ScreenModel {
        alarmSource: KnockoutObservableArray<share.AlarmPatternSettingDto>;
        currentAlarm: KnockoutObservable<share.AlarmPatternSettingDto>;
        alarmHeader: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<string>;
        createMode: KnockoutObservable<boolean>;
        alarmCode: KnockoutObservable<string>;
        alarmName: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        
        // Tab 1
        checkHeader: KnockoutObservableArray<any>;
        checkSource: Array<share.ModelCheckConditonCode>;
        checkConditionList: KnockoutObservableArray<share.ModelCheckConditonCode>;
        currentCodeListSwap: KnockoutObservableArray<share.ModelCheckConditonCode>;

        // Tab 2: Period setting
        periodSetting: any = new nts.uk.at.view.kal004.tab2.viewModel.ScreenModel();
        
        // Tab 3: SetPermission
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
                { headerText: getText('KAL004_7'), key: 'alarmPatternCD', width: 40 },
                { headerText: getText('KAL004_8'), key: 'alarmPatternName', width: 150 },
            ]);
            self.alarmCode = ko.observable('');
            self.alarmName = ko.observable('');
            self.createMode = ko.observable(false);

            self.checkConditionList = ko.observableArray([]);
            self.checkSource = [];

            self.checkHeader = ko.observableArray([
                { headerText: getText('KAL004_21'), key: 'GUID', width: 10, hidden: true },
                { headerText: getText('KAL004_21'), key: 'categoryName', width: 130 },
                { headerText: getText('KAL004_17'), key: 'checkConditonCode', width: 50 },
                { headerText: getText('KAL004_18'), key: 'checkConditionName', width: 150 }
            ]);

            self.currentCodeListSwap = ko.observableArray([]);
            self.currentCode = ko.observable('');
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            
            service.getCheckConditionCode().done((res1) => {
                self.checkConditionList(_.cloneDeep(res1));
                self.checkSource = _.cloneDeep(res1);

                service.getAlarmPattern().done((res) => {
                    self.alarmSource(res);
                    self.initSubscribe();
                    
                    if (res.length > 0) {
                        self.currentCode(res[0].alarmPatternCD);
                    }
                }).fail((error) => {
                    nts.uk.ui.dialog.alert({ messageId: error.messageId });
                }).always(()=>{
                    dfd.resolve();
                });
            }).fail((error1) => {
                nts.uk.ui.dialog.alert({ messageId: error1.messageId });
                dfd.resolve();
            });
           return dfd.promise();
        }
        
        private initSubscribe(): void {
            var self = this;
            // AlarmPatternSetting
            self.currentCode.subscribe((newV) => {
                self.alarmCodeChange(newV);
            });
            
            // Tab 2: Period Setting
            self.currentCodeListSwap.subscribe((listCode) => {
                self.periodSetting.listCheckConditionCode(listCode);
            });
        }

        public alarmCodeChange(newV): any {
            let self = this;
            if (newV == '') self.createMode(true);
            else {
                self.createMode(false);
                self.currentAlarm = _.find(self.alarmSource(), function(a) { return a.alarmPatternCD == newV });
                self.alarmCode(self.currentAlarm.alarmPatternCD);
                self.alarmName(self.currentAlarm.alarmPatternName);

                // Tab 1
                let currentCodeListSwap = [];
                let checkSource = _.cloneDeep(self.checkSource);

                self.currentAlarm.checkConList.forEach((x) => {
                    x.checkConditionCodes.forEach((y) => {
                        let CD = _.find(_.cloneDeep(self.checkSource), { 'category': x.alarmCategory, 'checkConditonCode': y });
                        currentCodeListSwap.push(_.cloneDeep(CD));
                    });
                });

                _.remove(checkSource, (leftItem) => {
                    let optItem = _.find(currentCodeListSwap, (rightItem) => {
                        return leftItem.GUID == rightItem.GUID;
                    });
                    if (optItem)
                        return optItem.GUID == leftItem.GUID;
                });
                self.currentCodeListSwap([]);
                self.checkConditionList( _.sortBy(checkSource, ['category', 'checkConditonCode']));
                self.currentCodeListSwap( _.sortBy(currentCodeListSwap, ['category', 'checkConditonCode']));
                                
                // Tab 3: Permission Setting
                self.setPermissionModel.listRoleID(self.currentAlarm.alarmPerSet.roleIds);
                self.setPermissionModel.selectedRuleCode(self.currentAlarm.alarmPerSet.authSetting);
            }

        }
        
        public insertAlarm() : void{
            let self = this;
            $(".nts-input").trigger("validate");
            if($(".nts-input").ntsError("hasError")) return ;
            
            
            block.invisible();
            block.clear();
        }
        public updateAlarm() : void{
            let self = this;
            $(".nts-input").trigger("validate");
            if($(".nts-input").ntsError("hasError")) return ;
            
            
            block.invisible();
            block.clear();            
        }
        public removeAlarm() : void{
            let self = this;
            if(self.currentCode() !==''){
                   nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                       
                   
                   });
            
           }
        }        

                
    }

}

