module nts.uk.at.view.kmk003.a {

    import SimpleWorkTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.SimpleWorkTimeSettingDto;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    import WorkTimeSettingCondition = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingCondition;

    import FlexWorkSettingDto = nts.uk.at.view.kmk003.a.service.model.flexset.FlexWorkSettingDto;
    
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import WorkTimeSettingInfoDto = nts.uk.at.view.kmk003.a.service.model.common.WorkTimeSettingInfoDto;
    
    import WorkTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.worktimeset.WorkTimeSettingModel;
    import PredetemineTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.predset.PredetemineTimeSettingModel;
    import FixedWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.fixedset.FixedWorkSettingModel;
    import FlowWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flowset.FlWorkSettingModel;
    import DiffTimeWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.DiffTimeWorkSettingModel;
    import FlexWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.FlexWorkSettingModel;
    
    import FixedWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FixedWorkSettingSaveCommand;
    import FlexWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FlexWorkSettingSaveCommand;
    
    export module viewmodel {

        export class ScreenModel {
            workTimeSettings: KnockoutObservableArray<SimpleWorkTimeSettingDto>;
            columnWorktimeSettings: KnockoutObservable<any>;
            selectedWorkTimeCode: KnockoutObservable<string>;

            //tab mode
            tabModeOptions: KnockoutObservableArray<any>;
            tabMode: KnockoutObservable<number>;

            //use half day
            useHalfDayOptions: KnockoutObservableArray<any>;
            useHalfDay: KnockoutObservable<boolean>;

            //tabs
            tabs: KnockoutObservableArray<TabItem>;
            selectedTab: KnockoutObservable<string>;

            //data
            isClickSave: KnockoutObservable<boolean>;
            
            mainSettingModel: MainSettingModel;
            workTimeSettingLoader: WorkTimeSettingLoader;
            
            settingEnum: WorkTimeSettingEnumDto;
            
            screenMode: KnockoutObservable<number>;
            isNewMode: KnockoutObservable<boolean>;
            constructor() {
                let self = this;
                self.mainSettingModel = new MainSettingModel();

                self.workTimeSettingLoader = new WorkTimeSettingLoader();
                
                self.workTimeSettings = ko.observableArray([]);
                self.columnWorktimeSettings = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMK003_10"), prop: 'worktimeCode', width: 100 },
                    { headerText: nts.uk.resource.getText("KMK003_11"), prop: 'workTimeName', width: 130 },
                    { headerText: nts.uk.resource.getText("KMK003_12"), prop: 'description', width: 50 }
                ]);
                self.selectedWorkTimeCode = ko.observable('');

                //tab mode
                self.tabModeOptions = ko.observableArray([
                    { code: TabMode.SIMPLE, name: nts.uk.resource.getText("KMK003_190") },
                    { code: TabMode.DETAIL, name: nts.uk.resource.getText("KMK003_191") }
                ]);
                self.tabMode = ko.observable(TabMode.DETAIL);
                self.tabMode.subscribe(newValue => {
                    if (newValue === TabMode.DETAIL) {
                        self.changeTabMode(true);
                    } else {                       
                        self.changeTabMode(false);
                    }   
                });

                self.useHalfDayOptions = ko.observableArray([
                    { code: true, name: nts.uk.resource.getText("KMK003_49") },
                    { code: false, name: nts.uk.resource.getText("KMK003_50") }
                ]);

                self.useHalfDay = ko.observable(false); // A5_19 initial value = false
                self.useHalfDay.subscribe(useHalfDay => {
                    if (self.mainSettingModel.workTimeSetting.isFlex()) {
                        self.mainSettingModel.flexWorkSetting.useHalfDayShift(useHalfDay);
                    }
                    if (self.mainSettingModel.workTimeSetting.isFixed()) {
                        self.mainSettingModel.fixedWorkSetting.useHalfDayShift(useHalfDay);
                    }
                    if (self.mainSettingModel.workTimeSetting.isDiffTime()) {
                        self.mainSettingModel.diffWorkSetting.isUseHalfDayShift(useHalfDay);
                    }
                });

                //
                self.tabs = ko.observableArray([]);
                self.tabs.push(new TabItem('tab-1', nts.uk.resource.getText("KMK003_17"), '.tab-a1', true, true));
                self.tabs.push(new TabItem('tab-2', nts.uk.resource.getText("KMK003_18"), '.tab-a2', true, true));
                self.tabs.push(new TabItem('tab-3', nts.uk.resource.getText("KMK003_89"), '.tab-a3', true, true));
                self.tabs.push(new TabItem('tab-4', nts.uk.resource.getText("KMK003_19"), '.tab-a4', true, true));
                self.tabs.push(new TabItem('tab-5', nts.uk.resource.getText("KMK003_20"), '.tab-a5', true, true));
                self.tabs.push(new TabItem('tab-6', nts.uk.resource.getText("KMK003_90"), '.tab-a6', true, true));
                self.tabs.push(new TabItem('tab-7', nts.uk.resource.getText("KMK003_21"), '.tab-a7', true, true));
                self.tabs.push(new TabItem('tab-8', nts.uk.resource.getText("KMK003_200"), '.tab-a8', true, true));
                self.tabs.push(new TabItem('tab-9', nts.uk.resource.getText("KMK003_23"), '.tab-a9', true, true));
                self.tabs.push(new TabItem('tab-10', nts.uk.resource.getText("KMK003_24"), '.tab-a10', true, true));
                self.tabs.push(new TabItem('tab-11', nts.uk.resource.getText("KMK003_25"), '.tab-a11', true, true));
                self.tabs.push(new TabItem('tab-12', nts.uk.resource.getText("KMK003_26"), '.tab-a12', true, true));
                self.tabs.push(new TabItem('tab-13', nts.uk.resource.getText("KMK003_27"), '.tab-a13', true, true));
                self.tabs.push(new TabItem('tab-14', nts.uk.resource.getText("KMK003_28"), '.tab-a14', true, true));
                self.tabs.push(new TabItem('tab-15', nts.uk.resource.getText("KMK003_29"), '.tab-a15', true, true));
                self.tabs.push(new TabItem('tab-16', nts.uk.resource.getText("KMK003_30"), '.tab-a16', true, true));
                
                self.selectedTab = ko.observable('tab-1');

                //data get from service
                self.isClickSave = ko.observable(false);
                
                self.selectedWorkTimeCode.subscribe(function(worktimeCode: string){
                    if (worktimeCode) {
                       self.loadWorktimeSetting(worktimeCode); 
                    }
                });
                
                self.screenMode = ko.observable(ScreenMode.NEW);
                self.isNewMode = ko.computed(() => {
                    return self.screenMode() == ScreenMode.NEW;
                });
            }
           
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                self.bindFunction();

                self.getAllEnums().done(() => {
                    self.workTimeSettingLoader.isAbolish.valueHasMutated();
                    _.defer(() => dfd.resolve());
                });
                
                return dfd.promise();
            }

            private bindFunction(): void {
                let self = this;
                self.workTimeSettingLoader.loadListWorktime = self.loadListWorktime.bind(self);
            }

            private loadListWorktime(): void {
                let self = this;
                service.findWithCondition(self.workTimeSettingLoader.getCondition()).done(data => {
                    self.workTimeSettings(data);
                    if (data && data.length > 0) {
                        self.screenMode(ScreenMode.UPDATE);
                        self.selectedWorkTimeCode(data[0].worktimeCode);
                    }
                    else {
                        self.screenMode(ScreenMode.NEW);
                    }
                });
            }
            
            //get infor of worktime by code
            private getWorkTimeInfo(workTimeCode: string): JQueryPromise<void> {
                var self = this;
                let dfd = $.Deferred<void>();
                //TODO when complete get data from infra
                service.findWorktimeSetingInfoByCode(workTimeCode).done(function(worktimeInfo: any) {
                    //TODO set worktimeInfo to mainSettingModel
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            //get all enums
            private getAllEnums(): JQueryPromise<void> {
                var self = this;
                let dfd = $.Deferred<void>();
                service.getEnumWorktimeSeting().done(function(setting: any) {
                    self.settingEnum = setting;
                    self.workTimeSettingLoader.setEnums(setting);
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * Load work time setting detail
             */
            private loadWorktimeSetting(worktimeCode: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                service.findWorktimeSetingInfoByCode(worktimeCode).done(worktimeSettingInfo => {
                    self.mainSettingModel.updateData(worktimeSettingInfo);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * Change tab mode
             */
            private changeTabMode(isDetail: boolean): void {
                let _self = this;
                if (isDetail) {
                    _.forEach(_self.tabs(), tab => tab.setVisible(true));
                } else {
                    let simpleTabsId: string[] = ['tab-1','tab-2','tab-3','tab-4','tab-5','tab-6','tab-7','tab-9','tab-10','tab-11','tab-12'];
                    _.forEach(_self.tabs(), tab => {
                        if (_.findIndex(simpleTabsId, id => tab.id === id) === -1) {
                            tab.setVisible(false);
                        } else {
                            tab.setVisible(true);
                        }                        
                    });
                }
            }

            //save worktime data
            public save(): void {
                let self = this;
                self.mainSettingModel.save();
            }
            
            //when click copy
            private copy() {
                let self = this;
                self.screenMode(ScreenMode.NEW);
                self.mainSettingModel.workTimeSetting.worktimeCode('');
            }

            public onClickNew() {
                let self = this;
                self.screenMode(ScreenMode.NEW);
                self.mainSettingModel.resetData();
                self.selectedWorkTimeCode('');
            };

             /**
             * remove worktime by code
             */
            private removeWorkTime(): JQueryPromise<void>
            {
                let self = this;
                let dfd = $.Deferred<void>();
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    service.removeWorkTime(self.selectedWorkTimeCode()).done(function() {
                        dfd.resolve();
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    });
                }).ifNo(function() {
                });
                return dfd.promise();
            }
        }

        /**
         * Tab Item
         */
        export class TabItem { 
            id: string;
            title: string; 
            content: string; 
            enable: KnockoutObservable<boolean>; 
            visible: KnockoutObservable<boolean>; 
            
            constructor(id: string, title: string, content: string, enable: boolean, visible: boolean) {
                this.id = id;
                this.title = title;
                this.content = content;
                this.enable = ko.observable(enable);
                this.visible = ko.observable(visible);
            }
            
            public setVisible(visible: boolean): void {
                this.visible(visible);
            }
        }
        
        /**
         * Store all Setting Model, use for tab data binding
         */
        export class MainSettingModel {
            workTimeSetting: WorkTimeSettingModel;
            predetemineTimeSetting: PredetemineTimeSettingModel;
            fixedWorkSetting: FixedWorkSettingModel;
            flowWorkSetting: FlowWorkSettingModel;
            diffWorkSetting: DiffTimeWorkSettingModel;
            flexWorkSetting: FlexWorkSettingModel;
            
            constructor() {
                this.workTimeSetting = new WorkTimeSettingModel();
                this.predetemineTimeSetting = new PredetemineTimeSettingModel();
                this.fixedWorkSetting = new FixedWorkSettingModel();
                this.flowWorkSetting = new FlowWorkSettingModel();
                this.diffWorkSetting = new DiffTimeWorkSettingModel();
                this.flexWorkSetting = new FlexWorkSettingModel();
                this.workTimeSetting.worktimeCode.subscribe(worktimeCode => {
                    this.predetemineTimeSetting.workTimeCode(worktimeCode);
                    this.fixedWorkSetting.workTimeCode(worktimeCode);
                    this.flowWorkSetting.workingCode(worktimeCode);
                    this.diffWorkSetting.workTimeCode(worktimeCode);
                    this.flexWorkSetting.workTimeCode(worktimeCode);
                });
            }

            save(): void {
                let self = this;
                if (self.workTimeSetting.isFlex()) {
                    service.saveFlexWorkSetting(self.toFlexCommannd()).fail(err => nts.uk.ui.dialog.alertError(err));
                }
                if (self.workTimeSetting.isFixed()) {
                    service.saveFixedWorkSetting(self.toFixedCommand()).fail(err => nts.uk.ui.dialog.alertError(err));
                }
            }

            /**
             * Collect fixed data and convert to command dto
             */
            toFixedCommand(): FixedWorkSettingSaveCommand {
                let _self = this;
                let command: FixedWorkSettingSaveCommand = {
                    predseting: _self.predetemineTimeSetting.toDto(),
                    worktimeSetting: _self.workTimeSetting.toDto(),
                    fixedWorkSetting: _self.fixedWorkSetting.toDto(),
                    screenMode: 0 //TODO: lam gi voi cai nay?
                };
                return command;  
            }

            /**
             * Collect flex data and convert to command dto
             */
            toFlexCommannd(): FlexWorkSettingSaveCommand {
                let self = this;
                let command: FlexWorkSettingSaveCommand;
                command = {
                    flexWorkSetting: self.flexWorkSetting.toDto(),
                    predseting: self.predetemineTimeSetting.toDto(),
                    worktimeSetting: self.workTimeSetting.toDto()
                };
                return command;
            }

            updateData(worktimeSettingInfo: WorkTimeSettingInfoDto): void {
                let self = this;
                self.workTimeSetting.updateData(worktimeSettingInfo.worktimeSetting);
                self.predetemineTimeSetting.updateData(worktimeSettingInfo.predseting);
                //TODO update flex, fixed, diff, flow viewmodel
            }
            
            resetData(){
                this.workTimeSetting.resetData();    
                this.predetemineTimeSetting.resetData();
                this.fixedWorkSetting.resetData();
                this.flexWorkSetting.resetData();
            }
        }

        export class WorkTimeSettingLoader extends WorkTimeSettingModel {
            workTimeAtrEnums: EnumConstantDto[];
            workTimeMethodEnums: EnumConstantDto[];
            loadListWorktime: () => void;
            constructor() {
                super();
                this.workTimeDivision.workTimeDailyAtr(3);
                this.workTimeDivision.workTimeMethodSet(3);
                this.workTimeDivision.workTimeDailyAtr.subscribe(() => {
                    this.loadListWorktime();
                });
                this.workTimeDivision.workTimeMethodSet.subscribe(() => {
                    this.loadListWorktime();
                });
                this.isAbolish.subscribe(() => {
                    this.loadListWorktime();
                });
            }

            public getCondition(): WorkTimeSettingCondition {
                let self = this;
                let cond = <WorkTimeSettingCondition>{};
                cond.workTimeDailyAtr = self.workTimeDivision.workTimeDailyAtr();
                cond.workTimeMethodSet = self.workTimeDivision.workTimeMethodSet();
                cond.isAbolish = self.isAbolish();

                // in case of all work atr
                if (self.isAllWorkAtr()) {
                    cond.workTimeDailyAtr = null;
                    cond.workTimeMethodSet = null;
                }

                // in case of flex or all work method
                if (self.isFlex() || self.isAllWorkMethod()) {
                    cond.workTimeMethodSet = null;
                }
                return cond;
            }

            public setEnums(enums: WorkTimeSettingEnumDto): void {
                let self = this;
                self.workTimeAtrEnums = _.cloneDeep(enums.workTimeDailyAtr);
                self.workTimeMethodEnums = _.cloneDeep(enums.workTimeMethodSet);
                let all = <EnumConstantDto>{};
                all.value = 3; //TODO: nen cho thanh so may?
                all.localizedName = "全て";
                self.workTimeAtrEnums.unshift(all);
                self.workTimeMethodEnums.unshift(all);
            }

            public isAllWorkAtr(): boolean {
                let self = this;
                return self.workTimeDivision.workTimeDailyAtr() == 3;
            }

            public isAllWorkMethod(): boolean {
                let self = this;
                return self.workTimeDivision.workTimeMethodSet() == 3;
            }
        }
        
        export enum EnumWorkForm {
            REGULAR,
            FLEX
        }
        
        export enum SettingMethod {
            FIXED,
            DIFFTIME,
            FLOW
        }
        
        export enum TabMode {
            SIMPLE,
            DETAIL
        }
        
        export enum ScreenMode {
            NEW,
            UPDATE
        }
    }
}