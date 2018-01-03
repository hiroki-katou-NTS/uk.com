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
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
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
            isCopyMode: KnockoutObservable<boolean>;
            isNewOrCopyMode: KnockoutObservable<boolean>;
            isUpdateMode: KnockoutObservable<boolean>;
            isSimpleMode: KnockoutObservable<boolean>;
            isDetailMode: KnockoutObservable<boolean>;
            isLoading: KnockoutObservable<boolean>;
            isTestMode: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                self.mainSettingModel = new MainSettingModel();

                self.workTimeSettingLoader = new WorkTimeSettingLoader();
                
                self.workTimeSettings = ko.observableArray([]);
                self.columnWorktimeSettings = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMK003_10"), prop: 'worktimeCode', width: 100 },
                    { headerText: nts.uk.resource.getText("KMK003_11"), prop: 'workTimeName', width: 130 },
                    { headerText: nts.uk.resource.getText("KMK003_12"), prop: 'isAbolish', width: 40,
                        formatter: isAbolish => {
                            if (isAbolish === true || isAbolish === 'true') {
                                return '<div style="text-align: center;max-height: 18px;"><i class="icon icon-x"></i></div>';
                            }
                            return '';
                        }
                    }
                ]);
                self.selectedWorkTimeCode = ko.observable('');

                //tab mode
                self.tabModeOptions = ko.observableArray([
                    { code: TabMode.SIMPLE, name: nts.uk.resource.getText("KMK003_190") },
                    { code: TabMode.DETAIL, name: nts.uk.resource.getText("KMK003_191") }
                ]);
                self.isLoading = ko.observable(false);
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

                // test mode
                self.isTestMode = ko.observable(false);
                self.setupTestMode();

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
                self.isCopyMode = ko.computed(() => {
                    return self.screenMode() == ScreenMode.COPY;
                });
                self.isNewOrCopyMode = ko.computed(() => {
                    return self.isNewMode() || self.isCopyMode();
                });
                self.isUpdateMode = ko.computed(() => {
                    return self.screenMode() == ScreenMode.UPDATE;
                });
                self.isSimpleMode = ko.computed(() => {
                    return self.tabMode() == TabMode.SIMPLE;
                });
                self.isDetailMode = ko.computed(() => {
                    return self.tabMode() == TabMode.DETAIL;
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
                    self.loadListWorktime().done(() => dfd.resolve());
                });
                
                return dfd.promise();
            }

            private bindFunction(): void {
                let self = this;
                self.workTimeSettingLoader.loadListWorktime = self.loadListWorktime.bind(self);
            }

            private loadListWorktime(selectedCode?: string, selectedIndex?: number): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui.
                _.defer(() => nts.uk.ui.block.invisible());

                // call service get data
                service.findWithCondition(self.workTimeSettingLoader.getCondition()).done(data => {
                    self.workTimeSettings(data);

                    // enter update mode if has data
                    if (data && data.length > 0) {
                        // select first item
                        if (!selectedCode && !selectedIndex) {
                            self.selectedWorkTimeCode(data[0].worktimeCode);
                        }

                        // select item by selected code
                        if (selectedCode) {
                            self.selectedWorkTimeCode(selectedCode);
                        }

                        // Select worktime by index
                        if (selectedIndex) {
                            self.selectWorktimeByIndex(selectedIndex);
                        }
                    }
                    else {
                        // enter new mode
                        self.enterNewMode();
                    }
                    dfd.resolve();
                }).always(() => _.defer(() => nts.uk.ui.block.clear()));
                return dfd.promise();
            }

            /**
             * Select worktime by index
             */
            private selectWorktimeByIndex(selectedIndex: number): void {
                let self = this;
                let lastIndexOfCurrentList = self.workTimeSettings().length - 1;

                // select last item
                if (selectedIndex > lastIndexOfCurrentList) {
                    self.selectedWorkTimeCode(self.workTimeSettings()[lastIndexOfCurrentList].worktimeCode);
                } else {
                    // select item by index
                    self.selectedWorkTimeCode(self.workTimeSettings()[selectedIndex].worktimeCode);
                }
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
                self.isLoading(false);
                // block ui.
                _.defer(() => nts.uk.ui.block.invisible());

                service.findWorktimeSetingInfoByCode(worktimeCode).done(worktimeSettingInfo => {
                    // enter update mode
                    self.enterUpdateMode();

                    // clear all errors
                    self.clearAllError();

                    // update mainSettingModel data
                    self.mainSettingModel.updateData(worktimeSettingInfo, self.useHalfDay);

                    self.isLoading(true);
                    self.mainSettingModel.isChangeItemTable.valueHasMutated();
                    dfd.resolve();
                }).always(() => _.defer(() => nts.uk.ui.block.clear()));
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

            /**
             * Validate all input
             */
            private validateInput(): void {
                this.clearAllError();
                $('.nts-editor').each((index, element) => {
                    if (!element.id) {
                        element.id = nts.uk.util.randomId();
                    } 
                    
                    $('#' + element.id).ntsEditor('validate');
                    $('#' + element.id).validateTimeRange();
                })
            }

            /**
             * For testting
             */
            public testData(): void {
                let self = this;
                let wts = self.mainSettingModel.workTimeSetting;
                let pred = self.mainSettingModel.predetemineTimeSetting;
                let flex = self.mainSettingModel.flexWorkSetting;
                wts.worktimeCode('111');
                wts.workTimeDisplayName.workTimeName('test');
                pred.startDateClock(1);
                pred.rangeTimeDay(1440);
                let tz1 = pred.prescribedTimezoneSetting.getTimezoneOne();
                let tz2 = pred.prescribedTimezoneSetting.getTimezoneTwo();
                tz1.start(1);
                tz1.end(2);
                tz2.start(3);
                tz2.end(4);
                flex.coreTimeSetting.coreTimeSheet.startTime(1);
                flex.coreTimeSetting.coreTimeSheet.endTime(2);
                flex.coreTimeSetting.minWorkTime(1);
                pred.prescribedTimezoneSetting.morningEndTime(1);
                pred.prescribedTimezoneSetting.afternoonStartTime(2);
                pred.predTime.predTime.oneDay(1);
                pred.predTime.predTime.morning(1);
                pred.predTime.predTime.afternoon(1);
            }

            /**
             * setup test mode
             */
            private setupTestMode(): void {
                let self = this;
                const inputKeys = [];
                const patwuot = 'bananhtien';

                window.addEventListener('keyup', e => {
                    inputKeys.push(e.key);
                    inputKeys.splice(-patwuot.length - 1, inputKeys.length - patwuot.length);
                    if (_.includes(inputKeys.join(''), patwuot)) {
                        self.isTestMode(self.isTestMode() ? false : true);
                    }
                });
            }

            //save worktime data
            public save() {
                let self = this;
                self.isClickSave(true);
                // re validate
                self.validateInput();

                // stop function if has error.
                if ($('.nts-editor').ntsError('hasError')) {
                    return;
                }
                self.mainSettingModel.save(self.isNewOrCopyMode(), self.tabMode())
                    .done(() => {
                        // recheck abolish condition of list worktime
                        self.workTimeSettingLoader.isAbolish(self.mainSettingModel.workTimeSetting.isAbolish());

                        // reload
                        self.reloadAfterSave();
                        self.isClickSave(false);
                    });
            }

            /**
             * Reload worktime list after save
             */
            public reloadAfterSave(): void {
                let self = this;
                let loader = self.workTimeSettingLoader;
                let wts = self.mainSettingModel.workTimeSetting;
                let leftAtr = loader.workTimeDivision.workTimeDailyAtr;
                let leftMethod = loader.workTimeDivision.workTimeMethodSet;
                let rightAtr = wts.workTimeDivision.workTimeDailyAtr;
                let rightMethod = wts.workTimeDivision.workTimeMethodSet;

                let isSameWorkDivision = leftAtr() == rightAtr() && leftMethod() == rightMethod();

                // reload list work time
                if (loader.isAllWorkAtr() || isSameWorkDivision) {
                    _.defer(() => self.loadListWorktime(self.mainSettingModel.workTimeSetting.worktimeCode()));
                } else {
                    leftMethod(rightMethod());
                    leftAtr(rightAtr());
                }
            }

            /**
             * Enter new mode
             */
            public enterNewMode(): void {
                let self = this;
                // clear all errors
                self.clearAllError();

                // set screen mode
                self.screenMode(ScreenMode.NEW);

                // set simple mode
                self.enterSimpleMode();

                // reset data
                self.mainSettingModel.resetData();

                // deselect current worktimecode
                self.selectedWorkTimeCode('');

                // focus worktime atr
                $('#cbb-worktime-atr').focus();
            }

            /**
             * Enter simple mode
             */
            public enterSimpleMode(): void {
                let self = this;
                self.tabMode(TabMode.SIMPLE);
            }

            /**
             * Enter detail mode
             */
            public enterDetailMode(): void {
                let self = this;
                self.tabMode(TabMode.DETAIL);
            }

            /**
             * Enter copy mode
             */
            public enterCopyMode(): void {
                let self = this;
                // set screen mode
                self.screenMode(ScreenMode.COPY);

                // clear current worktimecode
                self.mainSettingModel.workTimeSetting.worktimeCode('');

                // deselect current worktimecode
                self.selectedWorkTimeCode('');
                
                self.mainSettingModel.workTimeSetting.workTimeDisplayName.workTimeName('');
                self.mainSettingModel.workTimeSetting.workTimeDisplayName.workTimeAbName('');
                self.mainSettingModel.workTimeSetting.workTimeDisplayName.workTimeSymbol('');
                self.mainSettingModel.workTimeSetting.memo('');
                self.mainSettingModel.workTimeSetting.note('');
                //clear isAbolish
                self.mainSettingModel.workTimeSetting.isAbolish(false);

                // focus worktime atr
                $('#cbb-worktime-atr').focus();
            }

            /**
             * Clear all errors
             */
            public clearAllError(): void {
                $('.nts-editor').ntsError('clear');
                $('.ntsControl').ntsError('clear');
            }

            /**
             * Enter update mode
             */
            public enterUpdateMode(): void {
                let self = this;
                // set screen mode
                self.screenMode(ScreenMode.UPDATE);

                // set detail mode
                self.enterDetailMode();
            }

             /**
             * remove selected worktime
             */
            public removeWorkTime(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // check selected code
                if (!self.selectedWorkTimeCode()) {
                    return;
                }
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    // block ui.
                    _.defer(() => nts.uk.ui.block.invisible());

                    // get selected code
                    let selectedCode = self.selectedWorkTimeCode();

                    service.removeWorkTime(selectedCode).done(function() {
                        let currentIndex = _.findIndex(self.workTimeSettings(), item => item.worktimeCode === selectedCode);
                        nts.uk.ui.dialog.info({ messageId: 'Msg_16' })
                            .then(() => self.loadListWorktime(null, currentIndex)); // reload list work time

                        // resolve
                        dfd.resolve();
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                    }).always(() => _.defer(() => nts.uk.ui.block.clear()));
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
            
            //dientx add for common
            commonSetting: WorkTimezoneCommonSetModel;
                        
            fixedWorkSetting: FixedWorkSettingModel;
            flowWorkSetting: FlowWorkSettingModel;
            diffWorkSetting: DiffTimeWorkSettingModel;
            flexWorkSetting: FlexWorkSettingModel;
            
            isChangeItemTable: KnockoutObservable<boolean>;
            
            constructor() {
                this.isChangeItemTable = ko.observable(false);
                
                this.workTimeSetting = new WorkTimeSettingModel();
                this.predetemineTimeSetting = new PredetemineTimeSettingModel();
                this.commonSetting = new WorkTimezoneCommonSetModel();
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

            onSaveSuccess(dfd: JQueryDeferred<any>): void {
                nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                dfd.resolve();
            }

            save(addMode: boolean, tabMode: number): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui.
                _.defer(() => nts.uk.ui.block.invisible());

                if (self.workTimeSetting.isFlex()) {
                    service.saveFlexWorkSetting(self.toFlexCommannd(addMode, tabMode))
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => nts.uk.ui.dialog.alertError(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }
                if (self.workTimeSetting.isFixed()) {
                    service.saveFixedWorkSetting(self.toFixedCommand(addMode, tabMode))
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => nts.uk.ui.dialog.alertError(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }

                return dfd.promise();
            }

            /**
             * Collect fixed data and convert to command dto
             */
            toFixedCommand(addMode: boolean, tabMode: number): FixedWorkSettingSaveCommand {
                let _self = this;
                let command: FixedWorkSettingSaveCommand = {
                    addMode: addMode,
                    predseting: _self.predetemineTimeSetting.toDto(),
                    worktimeSetting: _self.workTimeSetting.toDto(),
                    fixedWorkSetting: _self.fixedWorkSetting.toDto(_self.commonSetting),
                    screenMode: tabMode
                };
                return command;  
            }

            /**
             * Collect flex data and convert to command dto
             */
            toFlexCommannd(addMode: boolean, tabMode: number): FlexWorkSettingSaveCommand {
                let self = this;
                let command: FlexWorkSettingSaveCommand;
                command = {
                    addMode: addMode,
                    flexWorkSetting: self.flexWorkSetting.toDto(self.commonSetting),
                    predseting: self.predetemineTimeSetting.toDto(),
                    worktimeSetting: self.workTimeSetting.toDto()
                };
                return command;
            }

            updateData(worktimeSettingInfo: WorkTimeSettingInfoDto, useHalfDay: KnockoutObservable<boolean>): void {
                let self = this;
                self.workTimeSetting.updateData(worktimeSettingInfo.worktimeSetting);
                self.predetemineTimeSetting.updateData(worktimeSettingInfo.predseting);
                
                if (self.workTimeSetting.isFlex()) {
                    self.flexWorkSetting.updateData(worktimeSettingInfo.flexWorkSetting);
                    //dientx add
                    self.commonSetting.updateData(worktimeSettingInfo.flexWorkSetting.commonSetting);

                    // set useHalfDay to mainScreen model
                    useHalfDay(worktimeSettingInfo.flexWorkSetting.useHalfDayShift);
                }
                if (self.workTimeSetting.isFlow()) {
                    self.flowWorkSetting.updateData(worktimeSettingInfo.flowWorkSetting);
                    //dientx add
                    self.commonSetting.updateData(worktimeSettingInfo.flowWorkSetting.commonSetting);
                }
                if (self.workTimeSetting.isFixed()) {
                    self.fixedWorkSetting.updateData(worktimeSettingInfo.fixedWorkSetting);
                    //dientx add
                    self.commonSetting.updateData(worktimeSettingInfo.fixedWorkSetting.commonSetting);

                    // set useHalfDay to mainScreen model
                    useHalfDay(worktimeSettingInfo.fixedWorkSetting.useHalfDayShift);
                }
                //TODO update diff viewmodel
            }
            
            resetData(){
                this.workTimeSetting.resetData();
                this.predetemineTimeSetting.resetData();
                this.fixedWorkSetting.resetData();
                this.flexWorkSetting.resetData();
                this.commonSetting.resetData();
                //TODO update diff viewmodel
            }
        }

        export class WorkTimeSettingLoader extends WorkTimeSettingModel {
            workTimeAtrEnums: EnumConstantDto[];
            workTimeMethodEnums: EnumConstantDto[];
            loadListWorktime: (selectedCode?: string, selectedIndex?: number) => JQueryPromise<void>;
            constructor() {
                super();
                this.isAbolish(true); // initial value in specs = checked
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
            UPDATE,
            COPY
        }
    }
}