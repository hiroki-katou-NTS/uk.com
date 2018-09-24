module nts.uk.at.view.kmk003.a {

    import SimpleWorkTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.SimpleWorkTimeSettingDto;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EnumConstantDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.EnumConstantDto;
    import WorkTimeSettingCondition = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingCondition;

    import FlexWorkSettingDto = nts.uk.at.view.kmk003.a.service.model.flexset.FlexWorkSettingDto;
    
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import WorkTimeSettingInfoDto = nts.uk.at.view.kmk003.a.service.model.common.WorkTimeSettingInfoDto;
    
    import WorkTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.worktimeset.WorkTimeSettingModel;
    import WorkTimeDisplayModeModel = nts.uk.at.view.kmk003.a.viewmodel.worktimeset.WorkTimeDisplayModeModel;
    import ManageEntryExitModel = nts.uk.at.view.kmk003.a.viewmodel.worktimeset.ManageEntryExitModel;
    import PredetemineTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.predset.PredetemineTimeSettingModel;
    import TimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.predset.TimezoneModel;
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
    import FixedWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.fixedset.FixedWorkSettingModel;
    import FlowWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flowset.FlowWorkSettingModel;
    import DiffTimeWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.DiffTimeWorkSettingModel;
    import FlexWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.FlexWorkSettingModel;
    
    import FixedWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FixedWorkSettingSaveCommand;
    import FlowWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FlowWorkSettingSaveCommand;
    import FlexWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FlexWorkSettingSaveCommand;
    import DiffTimeSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.DiffTimeWorkSettingSaveCommand;
    
    import WorkTimezoneCommonSetDto = service.model.common.WorkTimezoneCommonSetDto;
    import SubHolTransferSetDto = service.model.common.SubHolTransferSetDto;
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
            backupOptions: EnumConstantDto[];
            
            screenMode: KnockoutObservable<number>;
            isNewMode: KnockoutComputed<boolean>;
            isCopyMode: KnockoutComputed<boolean>;
            isNewOrCopyMode: KnockoutComputed<boolean>;
            isUpdateMode: KnockoutComputed<boolean>;
            isSimpleMode: KnockoutComputed<boolean>;
            isDetailMode: KnockoutComputed<boolean>;
            isLoading: KnockoutObservable<boolean>;
            
            flexWorkManaging: boolean;
            overTimeWorkFrameOptions: KnockoutObservableArray<any>;
            
            //update for storage tab 11
            backupCommonSetting: WorkTimezoneCommonSetDto
            constructor() {
                let self = this;
                // initial tab mode
                self.tabMode = ko.observable(TabMode.DETAIL);
                self.selectedTab = ko.observable(TabID.TAB1);

                // initial screen mode
                self.screenMode = ko.observable(ScreenMode.NEW);

                self.initComputedValue();
                
                self.useHalfDay = ko.observable(false); // A5_19 initial value = false
                self.mainSettingModel = new MainSettingModel(self.tabMode, self.isNewOrCopyMode, self.useHalfDay);
                self.selectedWorkTimeCode = ko.observable('');
                self.workTimeSettingLoader = new WorkTimeSettingLoader(self.mainSettingModel.workTimeSetting.worktimeCode);
                self.workTimeSettings = ko.observableArray([]);
                self.isLoading = ko.observable(false);

                // data get from service
                self.isClickSave = ko.observable(false);

                // initial data source
                self.initDatasource();

                // initial subscribe
                self.initSubscribe();
                
                //over time work frame options
                self.overTimeWorkFrameOptions = ko.observableArray([]);
                
                self.backupCommonSetting = null;
            }
           
            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                self.bindFunction();
                service.findAllUsedOvertimeWorkFrame().done(v => {
                    self.overTimeWorkFrameOptions(v);
                    service.findSettingFlexWork().done(vl => {
                        self.flexWorkManaging = vl.flexWorkManaging == 1 ? true : false;

                        self.getAllEnums().done(() => {
                            self.backupOptions = self.settingEnum.workTimeMethodSet;
                            self.setFlexOptionVisibility();
                            self.loadListWorktime().done(() => dfd.resolve());
                        });
                    });
                });
                return dfd.promise();
            }

            /**
             * Initial data source
             */
            private initDatasource(): void {
                let self = this;

                // Define workTimeSetting column 
                self.columnWorktimeSettings = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMK003_10"), prop: 'worktimeCode', width: 50 },
                    { headerText: nts.uk.resource.getText("KMK003_11"), prop: 'workTimeName', width: 180 },
                    { headerText: nts.uk.resource.getText("KMK003_12"), prop: 'isAbolish', width: 40,
                        formatter: (isAbolish: any):string => {
                            if (isAbolish === true || isAbolish === 'true') {
                                return '<img src="img/checked.png" style="margin-left: 15px; width: 20px; height: 20px;" />';
                            }
                            return '';
                        }
                    }
                ]);

                //tab mode
                self.tabModeOptions = ko.observableArray([
                    { code: TabMode.SIMPLE, name: nts.uk.resource.getText("KMK003_190") },
                    { code: TabMode.DETAIL, name: nts.uk.resource.getText("KMK003_191") }
                ]);

                self.useHalfDayOptions = ko.observableArray([
                    { code: true, name: nts.uk.resource.getText("KMK003_49") },
                    { code: false, name: nts.uk.resource.getText("KMK003_50") }
                ]);

                // tabs data source
                self.tabs = ko.observableArray([]);
                self.tabs.push(new TabItem(TabID.TAB1, nts.uk.resource.getText("KMK003_17"), '.tab-a1', true, true));
                self.tabs.push(new TabItem(TabID.TAB2, nts.uk.resource.getText("KMK003_18"), '.tab-a2', true, true));
                self.tabs.push(new TabItem(TabID.TAB3, nts.uk.resource.getText("KMK003_89"), '.tab-a3', true, true));
                self.tabs.push(new TabItem(TabID.TAB4, nts.uk.resource.getText("KMK003_19"), '.tab-a4', true, true));
                self.tabs.push(new TabItem(TabID.TAB5, nts.uk.resource.getText("KMK003_20"), '.tab-a5', true, true));
                self.tabs.push(new TabItem(TabID.TAB6, nts.uk.resource.getText("KMK003_90"), '.tab-a6', true, true));
                self.tabs.push(new TabItem(TabID.TAB7, nts.uk.resource.getText("KMK003_21"), '.tab-a7', true, true));
                self.tabs.push(new TabItem(TabID.TAB8, nts.uk.resource.getText("KMK003_200"), '.tab-a8', true, true));
                self.tabs.push(new TabItem(TabID.TAB9, nts.uk.resource.getText("KMK003_23"), '.tab-a9', true, true));
                self.tabs.push(new TabItem(TabID.TAB10, nts.uk.resource.getText("KMK003_24"), '.tab-a10', true, true));
                self.tabs.push(new TabItem(TabID.TAB11, nts.uk.resource.getText("KMK003_25"), '.tab-a11', true, true));
                self.tabs.push(new TabItem(TabID.TAB12, nts.uk.resource.getText("KMK003_26"), '.tab-a12', true, true));
                self.tabs.push(new TabItem(TabID.TAB13, nts.uk.resource.getText("KMK003_27"), '.tab-a13', true, true));
                self.tabs.push(new TabItem(TabID.TAB14, nts.uk.resource.getText("KMK003_28"), '.tab-a14', true, true));
                self.tabs.push(new TabItem(TabID.TAB15, nts.uk.resource.getText("KMK003_29"), '.tab-a15', true, true));
                self.tabs.push(new TabItem(TabID.TAB16, nts.uk.resource.getText("KMK003_30"), '.tab-a16', true, true));
                self.tabs.push(new TabItem(TabID.TAB17, nts.uk.resource.getText("KMK003_219"), '.tab-a17', true, true));
            }

            /**
             * Initial subscribe & computed value
             */
            private initSubscribe(): void {
                let self = this;

                self.useHalfDay.subscribe(() => {
                    self.clearAllError();
                });

                self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeDailyAtr.subscribe(() => {
                    if (self.isNewMode()) {
                        self.clearAllError();
                        self.isLoading(false);
                        self.mainSettingModel.resetData(self.isNewMode());
                        self.isLoading(true);
                    }
                });
                self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeMethodSet.subscribe(() => {
                    if (self.isNewMode()) {
                        self.clearAllError();
                        self.isLoading(false);
                        self.mainSettingModel.resetData(self.isNewMode());
                        self.isLoading(true);
                    }
                });

                self.tabMode.subscribe(newValue => {
                    if (newValue === TabMode.DETAIL) {
                        self.changeTabMode(true);
                    } else {                       
                        self.changeTabMode(false);
                    }   
//                    if (self.isUpdateMode()) {
//                        self.reloadWorktimeSetting();
//                    }
                });

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

                self.selectedWorkTimeCode.subscribe(function(worktimeCode: string){
                    if (worktimeCode) {
                        self.loadWorktimeSetting(worktimeCode);
                        self.settingEnum.workTimeMethodSet = self.backupOptions;
                        // focus worktime atr
                        $('#search-daily-atr').focus();
                        //update value model tab 2
                        self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftOne.valueChangedNotifier.valueHasMutated();
                    }
                    else {
                        if (self.screenMode() != 2) {
                            self.enterNewMode();
                        }
                        else {
                            self.enterCopyMode();
                        }
                    }
                });

            }

            /**
             * Initial computed value
             */
            initComputedValue(): void {
                let self = this;
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
             * Bind workTimeSetting loader function
             */
            private bindFunction(): void {
                let self = this;
                self.workTimeSettingLoader.loadListWorktime = self.loadListWorktime.bind(self);
            }

            /**
             * Load list work time
             */
            private loadListWorktime(selectedCode?: string, selectedIndex?: number): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui.
                _.defer(() => nts.uk.ui.block.invisible());

                // call service get data
                service.findWithCondition(self.workTimeSettingLoader.getCondition()).done(data => {
                    data = _.sortBy(data, item => item.worktimeCode);
                    self.workTimeSettings(data); // sort by work time code

                    // enter update mode if has data
                    if (data && data.length > 0) {
                        let found = _.find(self.workTimeSettings(), worktime => worktime.worktimeCode == selectedCode);
                        // select first item
                        if (!found && !selectedIndex) {
                            self.selectedWorkTimeCode(data[0].worktimeCode);
                        }

                        // select item by selected code
                        if (found) {
                            self.selectedWorkTimeCode(selectedCode);
                        }

                        // Select worktime by index
                        if (selectedIndex) {
                            self.selectWorktimeByIndex(selectedIndex);
                        }
                        self.settingEnum.workTimeMethodSet = self.backupOptions;
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
             * Set flex options visibility
             */
            private setFlexOptionVisibility(): void {
                let self = this;
                if (!self.flexWorkManaging) {
                    self.settingEnum.workTimeDailyAtr = _.filter(self.settingEnum.workTimeDailyAtr, item => item.fieldName != 'FLEX_WORK');
                    self.workTimeSettingLoader.workTimeAtrEnums = _.filter(self.workTimeSettingLoader.workTimeAtrEnums, item => item.fieldName != 'FLEX_WORK');
                }
            }

            /**
             * reload worktime setting
             */
            private reloadWorktimeSetting(): void {
                let self = this;
                let currentCode = self.mainSettingModel.workTimeSetting.worktimeCode();
                if (!currentCode) {
                    return;
                }
                // block ui.
                _.defer(() => nts.uk.ui.block.invisible());
                // clear all errors
                self.clearAllError();

                self.isLoading(false);
                service.findWorktimeSetingInfoByCode(currentCode)
                    .done(worktimeSettingInfo => {

                        // update mainSettingModel data
                        self.mainSettingModel.updateData(worktimeSettingInfo).done(()=>{
                            self.isLoading(false);
                            self.isLoading(true);    
                        });
                        self.mainSettingModel.isChangeItemTable.valueHasMutated();
                    }).always(() => _.defer(() => nts.uk.ui.block.clear()));
            }

            /**
             * Load work time setting detail
             */
            private loadWorktimeSetting(worktimeCode: string): JQueryPromise<void> {
                if (worktimeCode) {
                    let self = this;
                    let dfd = $.Deferred<void>();
                    // clear all errors
                    self.clearAllError();
                    self.isLoading(false);
                    // block ui.
                    _.defer(() => nts.uk.ui.block.invisible());

                    service.findWorktimeSetingInfoByCode(worktimeCode).done(worktimeSettingInfo => {

                        // update mainSettingModel data
                        self.mainSettingModel.updateData(worktimeSettingInfo).done(()=>{
                            self.isLoading(false);
                            self.isLoading(true);
                            //convert 
                            self.backupCommonSetting = self.mainSettingModel.commonSetting.toDto();
                        });
                        self.mainSettingModel.isChangeItemTable.valueHasMutated();
                        
                        // enter update mode
                        self.enterUpdateMode();
                        dfd.resolve();
                    }).always(() => _.defer(() => nts.uk.ui.block.clear()));
                    return dfd.promise();
                }
            }
            
            /**
             * Change tab mode
             */
            private changeTabMode(isDetail: boolean): void {              
                let _self = this;
                _self.clearAllError();
                if (isDetail) {
                    _.forEach(_self.tabs(), tab => tab.setVisible(true));
                } else {
                    let simpleTabsId: string[] = [TabID.TAB1, TabID.TAB2, TabID.TAB3, TabID.TAB4, TabID.TAB5, TabID.TAB6, TabID.TAB7, TabID.TAB9, TabID.TAB10, TabID.TAB11, TabID.TAB12, TabID.TAB17];
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
                let self = this;
                let commonDayoff = nts.uk.util.isNullOrEmpty(self.backupCommonSetting)?null : self.backupCommonSetting.subHolTimeSet[0].subHolTimeSet;
                let commonOvertime = nts.uk.util.isNullOrEmpty(self.backupCommonSetting)?null : self.backupCommonSetting.subHolTimeSet[1].subHolTimeSet;
                self.clearAllError();
                $('.nts-editor').each((index, element) => {
                    if (!element.id) {
                        element.id = nts.uk.util.randomId();
                    } 
                    
                    $('#' + element.id).ntsEditor('validate');
                })
                $('.time-range-editor').each((index, element) => {
                    $('#' + element.id).validateTimeRange();
                });
                //validate disabled item tab 1
                self.validatetab1();
                
                //validate disabled item tab 7
                self.validatetab7();
                
                //validate disabled item tab 11
                if (!nts.uk.util.isNullOrEmpty(self.backupCommonSetting)) {
                    if (self.mainSettingModel.tabMode() == TabMode.DETAIL) {
                        self.validateTab11(commonDayoff, commonOvertime);
                    }
                    else {
                        self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.certainTime(commonDayoff.certainTime);
                        self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.certainTime(commonOvertime.certainTime);
                        self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.oneDayTime(commonOvertime.designatedTime.oneDayTime);
                        self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.halfDayTime(commonOvertime.designatedTime.halfDayTime);
                    }
                }
            }
            
            private validatetab1() {
                let self = this;
                if (self.mainSettingModel.flexWorkSetting.coreTimeSetting.timesheet() == 0) {
                    //assign value avoid exception
                    self.mainSettingModel.flexWorkSetting.coreTimeSetting.coreTimeSheet.startTime(0);
                    self.mainSettingModel.flexWorkSetting.coreTimeSetting.coreTimeSheet.endTime(0);
                    $('#coreTimeStart').ntsError('clear');
                    $('#coreTimeEnd').ntsError('clear');
                }
                
                if (!self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.useAtr())
                {
                    $('#shiftTwoStart').ntsError('clear');
                    $('#shiftTwoEnd').ntsError('clear');
                    self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.start(0);
                    self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.end(0);
                }
            }
            
            private validatetab7()
            {
                let self = this;
                if (!self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.useHereAfterRestSet()) {
                    $('#nts-fix-table-a7-flow-notuse-2').find('.nts-input').ntsError('clear');
                }
                if (!self.mainSettingModel.flowWorkSetting.offdayWorkTimezone.restTimeZone.flowRestTimezone.useHereAfterRestSet()) {
                    $('#nts-fix-table-a7-flex-notuse-2').find('.nts-input').ntsError('clear');
                }
            }
            private validateTab11(commonDayoff: SubHolTransferSetDto,commonOvertime: SubHolTransferSetDto) {
                let self = this;
                if (self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.useDivision()) {
                    if (self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.subHolTransferSetAtr() == 0) {
                        //一定時間
                        $('#certainDayTimeHol').ntsError('clear');
                        self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.certainTime(commonDayoff.certainTime);
                    }
                    else {//指定時間
                        self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.designatedTime.oneDayTime(commonDayoff.designatedTime.oneDayTime);
                        self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.designatedTime.halfDayTime(commonDayoff.designatedTime.halfDayTime);
                        $('#oneDayTimeHol').ntsError('clear');
                        $('#haflDayTimeHol').ntsError('clear');
                    }
                }
                else {
                    $('#certainDayTimeHol').ntsError('clear');
                    $('#oneDayTimeHol').ntsError('clear');
                    $('#haflDayTimeHol').ntsError('clear');
                    self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.certainTime(commonDayoff.certainTime);
                    self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.designatedTime.oneDayTime(commonDayoff.designatedTime.oneDayTime);
                    self.mainSettingModel.commonSetting.getWorkDayOffTimeSet().subHolTimeSet.designatedTime.halfDayTime(commonDayoff.designatedTime.halfDayTime);
                }
                if (self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.useDivision()) {
                    if (self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.subHolTransferSetAtr() == 0) {
                        //一定時間
                        self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.certainTime(commonOvertime.certainTime);
                        $('#certainDayTimeOT').ntsError('clear');
                    }
                    else {//指定時間
                        self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.oneDayTime(commonOvertime.designatedTime.oneDayTime);
                        self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.halfDayTime(commonOvertime.designatedTime.halfDayTime);
                        $('#haflDayTimeOT').ntsError('clear');
                        $('#oneDayTimeOT').ntsError('clear');
                    }
                }
                else {
                    $('#certainDayTimeOT').ntsError('clear');
                    $('#haflDayTimeOT').ntsError('clear');
                    $('#oneDayTimeOT').ntsError('clear');
                    self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.certainTime(commonOvertime.certainTime);
                    self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.oneDayTime(commonOvertime.designatedTime.oneDayTime);
                    self.mainSettingModel.commonSetting.getOverTimeSet().subHolTimeSet.designatedTime.halfDayTime(commonOvertime.designatedTime.halfDayTime);
                }
                
                // Validate Msg_770
                let shiftTwo: TimezoneModel = self.mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo;
                if (shiftTwo.useAtr() && (shiftTwo.start() >= shiftTwo.end())) {
                    $('#shiftTwoStart').ntsError('set', {messageId:'Msg_770',messageParams:[nts.uk.resource.getText('KMK003_216')]});
                }
            }
            
            //save worktime data
            public save() {
                let self = this;
                // re validate
                self.validateInput();

                // stop function if has error.
                if ($('.nts-editor').ntsError('hasError') || $('.time-range-editor').ntsError('hasError')) {
                    return;
                }
                self.isClickSave(true);
                //for dialog F mode simple
                self.bindFDialogSimpleMode();
                self.mainSettingModel.save()
                    .done(() => {
                        // recheck abolish condition of list worktime
                        self.workTimeSettingLoader.isAbolish(self.mainSettingModel.workTimeSetting.isAbolish());

                        // reload
                        self.reloadAfterSave();
                        self.isClickSave(false);
                        self.loadWorktimeSetting(self.selectedWorkTimeCode());
                    }).fail((err) => {
                        self.isClickSave(false);
                        let errors = _.uniqBy(err.errors, (v: any) => {
                            let key = v.messageId;
                            for (let param of v.parameterIds) {
                                key = key + ' ' + param;
                            }
                            return key;
                        });

                        // close current error dialog
                        const buttonCloseDialog = $('#functions-area-bottom>.ntsClose');
                        if (!nts.uk.util.isNullOrEmpty(buttonCloseDialog)) {
                            buttonCloseDialog.click();
                        }

                        err.errors = errors;
                        self.showMessageError(err);
                    });
            }
            
            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                let dfd = $.Deferred<any>();
                
                // check error business exception
                if (!res.businessException) {
                    return;
                }
                
                // show error message
                if (Array.isArray(res.errors)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
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

                if (leftAtr() == EnumWorkForm.REGULAR && rightAtr() == EnumWorkForm.REGULAR) {
                    if (leftMethod() == SettingMethod.ALL) {
                        isSameWorkDivision = true;
                    }
                }
                
                if (leftAtr() == EnumWorkForm.FLEX && rightAtr() == EnumWorkForm.FLEX) {
                    isSameWorkDivision = true;
                }
                
                // reload list work time
                if (loader.isAllWorkAtr() || isSameWorkDivision) {
                    _.defer(() => self.loadListWorktime(self.mainSettingModel.workTimeSetting.worktimeCode()));
                } else {
                    loader.selectAll();
                }
            }

            /**
             * Enter new mode
             */
            public enterNewMode(): void {
                let self = this;
                self.clearAllError();
                self.isLoading(false);
                // clear all errors

                // reset data
                self.mainSettingModel.resetData();
                self.settingEnum.workTimeMethodSet = _.filter(self.settingEnum.workTimeMethodSet, item => item.fieldName != 'DIFFTIME_WORK');
                // set screen mode
                self.screenMode(ScreenMode.NEW);

                // set simple mode
                self.enterSimpleMode();
                
                // deselect current worktimecode
                self.selectedWorkTimeCode('');

                // focus worktimecode
                $('#inp-worktimecode').focus();
                
                self.isLoading(true);
                
                //fix ST #5
                self.selectedTab('tab-1');
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
                
                self.settingEnum.workTimeMethodSet = _.filter(self.settingEnum.workTimeMethodSet, item => item.fieldName != 'DIFFTIME_WORK');
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

                // do interlock if simple mode
                if (self.tabMode() === TabMode.SIMPLE) {
                    self.mainSettingModel.isInterlockDialogJ(true);
                    self.mainSettingModel.updateStampValue();
                }
                self.mainSettingModel.predetemineTimeSetting.predTime.addTime.oneDay(self.mainSettingModel.predetemineTimeSetting.predTime.predTime.oneDay());
                self.mainSettingModel.predetemineTimeSetting.predTime.addTime.oneDay(self.mainSettingModel.predetemineTimeSetting.predTime.predTime.oneDay());
                self.mainSettingModel.predetemineTimeSetting.predTime.addTime.oneDay(self.mainSettingModel.predetemineTimeSetting.predTime.predTime.oneDay());
                // focus worktimecode
                $('#inp-worktimecode').focus();
            }

            /**
             * Clear all errors
             */
            public clearAllError(): void {
                nts.uk.ui.errors.clearAll();
            }

            /**
             * Enter update mode
             */
            public enterUpdateMode(): void {
                let self = this;
                // set screen mode
                self.screenMode(ScreenMode.UPDATE);

                // set detail mode
                //self.enterDetailMode();
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
           
            private bindFDialogSimpleMode(): void {
                let self = this;
                if (self.tabMode() == TabMode.SIMPLE) {
                    //set main screen to dialog  
                    self.mainSettingModel.predetemineTimeSetting.predTime.addTime.oneDay(self.mainSettingModel.predetemineTimeSetting.predTime.predTime.oneDay());
                    self.mainSettingModel.predetemineTimeSetting.predTime.addTime.morning(self.mainSettingModel.predetemineTimeSetting.predTime.predTime.morning());
                    self.mainSettingModel.predetemineTimeSetting.predTime.addTime.afternoon(self.mainSettingModel.predetemineTimeSetting.predTime.predTime.afternoon());
                }
            }
            //end view model
            
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
            manageEntryExit: ManageEntryExitModel;
            
            //dientx add for common
            commonSetting: WorkTimezoneCommonSetModel;
                        
            fixedWorkSetting: FixedWorkSettingModel;
            flowWorkSetting: FlowWorkSettingModel;
            diffWorkSetting: DiffTimeWorkSettingModel;
            flexWorkSetting: FlexWorkSettingModel;
            
            isChangeItemTable: KnockoutObservable<boolean>;
            useHalfDay: KnockoutObservable<boolean>;
            tabMode: KnockoutObservable<number>;
            addMode: KnockoutComputed<boolean>;
            
            // Interlock dialog J
            isInterlockDialogJ: KnockoutObservable<boolean>;
            
            constructor(tabMode: KnockoutObservable<number>, isNewOrCopyMode: KnockoutComputed<boolean>, useHalfDay: KnockoutObservable<boolean>) {
                let self = this;
                self.isChangeItemTable = ko.observable(false);
                self.useHalfDay = useHalfDay; // bind to useHalfDay of main screen
                self.isInterlockDialogJ = ko.observable(true);
                self.tabMode = tabMode;
                
                self.addMode = isNewOrCopyMode;
                
                self.workTimeSetting = new WorkTimeSettingModel();
                self.manageEntryExit = new ManageEntryExitModel();
                self.predetemineTimeSetting = new PredetemineTimeSettingModel();
                self.commonSetting = new WorkTimezoneCommonSetModel();
                self.fixedWorkSetting = new FixedWorkSettingModel(self.tabMode);
                self.flowWorkSetting = new FlowWorkSettingModel();
                self.diffWorkSetting = new DiffTimeWorkSettingModel(self.tabMode);
                self.flexWorkSetting = new FlexWorkSettingModel(self.tabMode);

                self.initSubscriber();

            }

            private initSubscriber(): void {
                let self = this;
                const shiftOne = self.predetemineTimeSetting.prescribedTimezoneSetting.shiftOne;
                self.fixedWorkSetting.initSubscriberForTab2(shiftOne);
                self.flexWorkSetting.initSubscriberForTab2(shiftOne);
                self.diffWorkSetting.initSubscriberForTab2(shiftOne);

                self.workTimeSetting.worktimeCode.subscribe(worktimeCode => {
                    self.predetemineTimeSetting.workTimeCode(worktimeCode);
                    self.fixedWorkSetting.workTimeCode(worktimeCode);
                    self.flowWorkSetting.workingCode(worktimeCode);
                    self.diffWorkSetting.workTimeCode(worktimeCode);
                    self.flexWorkSetting.workTimeCode(worktimeCode);
                });
            }

            onSaveSuccess(dfd: JQueryDeferred<any>): void {
                nts.uk.ui.dialog.info({ messageId: 'Msg_15' });
                dfd.resolve();
            }

            save(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui.
                _.defer(() => nts.uk.ui.block.invisible());
                
                // do interlock if simple mode
                if (self.tabMode() === TabMode.SIMPLE) {
                    self.isInterlockDialogJ(true);
                    self.updateStampValue();
                }

                if (self.workTimeSetting.isFlex()) {
                    service.saveFlexWorkSetting(self.toFlexCommannd())
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => dfd.reject(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }
                if (self.workTimeSetting.isFixed()) {
                    service.saveFixedWorkSetting(self.toFixedCommand())
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => dfd.reject(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }                
                if (self.workTimeSetting.isFlow()) {
                    service.saveFlowWorkSetting(self.toFlowCommand())
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => dfd.reject(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }

                if (self.workTimeSetting.isDiffTime()) {
                    service.saveDiffTimeWorkSetting(self.toDiffTimeCommand())
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => dfd.reject(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }
                
                return dfd.promise();
            }

            /**
             * Collect fixed data and convert to command dto
             */
            toFixedCommand(): FixedWorkSettingSaveCommand {
                let _self = this;
                let command: FixedWorkSettingSaveCommand = {
                    addMode: _self.addMode(),
                    predseting: _self.predetemineTimeSetting.toDto(),
                    worktimeSetting: _self.workTimeSetting.toDto(),
                    fixedWorkSetting: _self.fixedWorkSetting.toDto(_self.commonSetting),
                    screenMode: _self.tabMode()
                };
                return command;  
            }

            toFlowCommand(): FlowWorkSettingSaveCommand {
                let _self = this;
                let command: FlowWorkSettingSaveCommand = {
                    addMode: _self.addMode(),
                    predseting: _self.predetemineTimeSetting.toDto(),
                    worktimeSetting: _self.workTimeSetting.toDto(),
                    flowWorkSetting: _self.flowWorkSetting.toDto(_self.commonSetting),
                    screenMode: _self.tabMode()
                };
                return command;  
            }
            
            /**
             * Collect flex data and convert to command dto
             */
            toFlexCommannd(): FlexWorkSettingSaveCommand {
                let self = this;
                let command: FlexWorkSettingSaveCommand;
                const oneDayFlex = _.map(self.flexWorkSetting.getHDWtzOneday().workTimezone.lstWorkingTimezone(),item=>item.toDto());
                const flexWorkSetting = self.flexWorkSetting.toDto(self.commonSetting);
                flexWorkSetting.lstHalfDayWorkTimezone[0].workTimezone.lstWorkingTimezone = oneDayFlex;
                command = {
                    addMode: self.addMode(),
                    screenMode: self.tabMode(),
                    flexWorkSetting: flexWorkSetting,
                    predseting: self.predetemineTimeSetting.toDto(),
                    worktimeSetting: self.workTimeSetting.toDto(),
                };
                return command;
            }
            
            /**
             * Collect difftime data and convert to command dto
             */
            toDiffTimeCommand(): DiffTimeSettingSaveCommand {
                let self = this;
                let command: DiffTimeSettingSaveCommand;
                command = {
                    addMode: self.addMode(),
                    screenMode: self.tabMode(),
                    diffTimeWorkSetting: self.diffWorkSetting.toDto(self.commonSetting),
                    predseting: self.predetemineTimeSetting.toDto(),
                    worktimeSetting: self.workTimeSetting.toDto(),
                };
                return command;
            }

            updateData(worktimeSettingInfo: WorkTimeSettingInfoDto): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                self.workTimeSetting.updateData(worktimeSettingInfo.worktimeSetting);
                self.predetemineTimeSetting.updateData(worktimeSettingInfo.predseting);    
                self.manageEntryExit.updateData(worktimeSettingInfo.manageEntryExit);                          
                self.tabMode(worktimeSettingInfo.displayMode.displayMode);
                if (self.workTimeSetting.isFlex()) {
                    self.flexWorkSetting.updateData(worktimeSettingInfo.flexWorkSetting);
                    self.commonSetting.updateData(worktimeSettingInfo.flexWorkSetting.commonSetting);

                    // set useHalfDay to mainScreen model
                    self.useHalfDay(worktimeSettingInfo.flexWorkSetting.useHalfDayShift);

                    // reset data of other mode
                    self.flowWorkSetting.resetData();
                    self.diffWorkSetting.resetData();
                    self.fixedWorkSetting.resetData();
                }
                if (self.workTimeSetting.isFlow()) {
                    self.flowWorkSetting.updateData(worktimeSettingInfo.flowWorkSetting);
                    self.commonSetting.updateData(worktimeSettingInfo.flowWorkSetting.commonSetting);

                    // reset data of other mode
                    self.flexWorkSetting.resetData();
                    self.diffWorkSetting.resetData();
                    self.fixedWorkSetting.resetData();
                }
                if (self.workTimeSetting.isFixed()) {
                    self.fixedWorkSetting.updateData(worktimeSettingInfo.fixedWorkSetting);
                    self.commonSetting.updateData(worktimeSettingInfo.fixedWorkSetting.commonSetting);

                    // set useHalfDay to mainScreen model
                    self.useHalfDay(worktimeSettingInfo.fixedWorkSetting.useHalfDayShift);

                    // reset data of other mode
                    self.flowWorkSetting.resetData();
                    self.diffWorkSetting.resetData();
                    self.flexWorkSetting.resetData();
                }
                
                if (self.workTimeSetting.isDiffTime()) {
                    self.diffWorkSetting.updateData(worktimeSettingInfo.diffTimeWorkSetting);
                    self.commonSetting.updateData(worktimeSettingInfo.diffTimeWorkSetting.commonSet);

                    // set useHalfDay to mainScreen model
                    self.useHalfDay(worktimeSettingInfo.diffTimeWorkSetting.useHalfDayShift);

                    // reset data of other mode
                    self.flowWorkSetting.resetData();
                    self.flexWorkSetting.resetData();
                    self.fixedWorkSetting.resetData();
                }      
                
                self.updateInterlockDialogJ();
                self.updateStampValue();
                return dfd.resolve();
            }
            
            resetData(isNewMode?: boolean) {
                let self = this;
                self.useHalfDay(false);                
                self.fixedWorkSetting.resetData(isNewMode);
                self.flowWorkSetting.resetData();
                self.flexWorkSetting.resetData();
                self.diffWorkSetting.resetData(isNewMode);
                if (!isNewMode) {
                    //check change mode to convert data
                    self.commonSetting.resetData();
                    self.predetemineTimeSetting.resetData();
                    self.workTimeSetting.resetData();
                    self.workTimeSetting.resetWorkTimeDivision();
                }
                self.isInterlockDialogJ(true);
            }
            
            updateStampValue() {
                let _self = this;   
                
                // Update stamp data
                if (_self.isInterlockDialogJ()) {
                    if (_self.workTimeSetting.isFixed()) {                     
                        let workStart: number = _self.predetemineTimeSetting.startDateClock();
                        let workEnd: number = _self.predetemineTimeSetting.startDateClock() + (_self.predetemineTimeSetting.rangeTimeDay());
                        let endWork1: number = _self.predetemineTimeSetting.prescribedTimezoneSetting.shiftOne.end();
                        let startWork2: number = _self.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.start();
                                                                
                        if (_self.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.useAtr() && _self.tabMode() === TabMode.DETAIL) {
                            _self.fixedWorkSetting.getGoWork1Stamp().startTime(workStart);
                            _self.fixedWorkSetting.getLeaveWork1Stamp().startTime(workStart);                            
                            _self.fixedWorkSetting.getGoWork2Stamp().endTime(workEnd);
                            _self.fixedWorkSetting.getLeaveWork2Stamp().endTime(workEnd);
                                                                                   
                            _self.fixedWorkSetting.getGoWork1Stamp().endTime(endWork1);
                            _self.fixedWorkSetting.getGoWork2Stamp().startTime(endWork1 + 1);
                            _self.fixedWorkSetting.getLeaveWork1Stamp().endTime(startWork2);
                            _self.fixedWorkSetting.getLeaveWork2Stamp().startTime(startWork2 + 1);
                        } else {                           
                            _self.fixedWorkSetting.getGoWork1Stamp().startTime(workStart);
                            _self.fixedWorkSetting.getLeaveWork1Stamp().startTime(workStart);                            
                            _self.fixedWorkSetting.getGoWork1Stamp().endTime(workEnd);
                            _self.fixedWorkSetting.getLeaveWork1Stamp().endTime(workEnd);
                            
                            _self.fixedWorkSetting.getGoWork2Stamp().startTime(0);
                            _self.fixedWorkSetting.getLeaveWork2Stamp().startTime(0);                            
                            _self.fixedWorkSetting.getGoWork2Stamp().endTime(1440);
                            _self.fixedWorkSetting.getLeaveWork2Stamp().endTime(1440);
                        }
                    }   
                    if (_self.workTimeSetting.isFlex()) {
                        let workStart: number = _self.predetemineTimeSetting.startDateClock();
                        let workEnd: number = _self.predetemineTimeSetting.startDateClock() + (_self.predetemineTimeSetting.rangeTimeDay());
                        _self.flexWorkSetting.getGoWork1Stamp().startTime(workStart);
                        _self.flexWorkSetting.getGoWork1Stamp().endTime(workEnd);
                        _self.flexWorkSetting.getLeaveWork1Stamp().startTime(workStart);
                        _self.flexWorkSetting.getLeaveWork1Stamp().endTime(workEnd);
                    }  
                    if (_self.workTimeSetting.isFlow()) {
                        let workStart: number = _self.predetemineTimeSetting.startDateClock();
                        let workEnd: number = _self.predetemineTimeSetting.startDateClock() + (_self.predetemineTimeSetting.rangeTimeDay());
                        _self.flowWorkSetting.stampReflectTimezone.getGoWorkFlowStamp().startTime(workStart);
                        _self.flowWorkSetting.stampReflectTimezone.getGoWorkFlowStamp().endTime(workEnd);
                        _self.flowWorkSetting.stampReflectTimezone.getLeaveWorkFlowStamp().startTime(workStart);
                        _self.flowWorkSetting.stampReflectTimezone.getLeaveWorkFlowStamp().endTime(workEnd);
                    } 
                    if (_self.workTimeSetting.isDiffTime()) {
                        let workStart: number = _self.predetemineTimeSetting.startDateClock();
                        let workEnd: number = _self.predetemineTimeSetting.startDateClock() + (_self.predetemineTimeSetting.rangeTimeDay());
                        _self.diffWorkSetting.stampReflectTimezone.getGoWork1Stamp().startTime(workStart);
                        _self.diffWorkSetting.stampReflectTimezone.getGoWork1Stamp().endTime(workEnd);
                        _self.diffWorkSetting.stampReflectTimezone.getLeaveWork1Stamp().startTime(workStart);
                        _self.diffWorkSetting.stampReflectTimezone.getLeaveWork1Stamp().endTime(workEnd);
                    }  
                }             
            }
            
            updateInterlockDialogJ() {
                let _self = this;   
                             
                // Update interlock value
                if (_self.workTimeSetting.isFixed()) {
                    let workStart: number = _self.predetemineTimeSetting.startDateClock();
                    let workEnd: number = _self.predetemineTimeSetting.startDateClock() + _self.predetemineTimeSetting.rangeTimeDay();
                    let endWork1: number = _self.predetemineTimeSetting.prescribedTimezoneSetting.shiftOne.end();
                    let startWork2: number = _self.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.start();
                    
                    if (_self.predetemineTimeSetting.prescribedTimezoneSetting.shiftTwo.useAtr()) {
                        if ((_self.fixedWorkSetting.getGoWork1Stamp().startTime() == workStart) 
                            && (_self.fixedWorkSetting.getGoWork1Stamp().endTime() == endWork1)
                            && (_self.fixedWorkSetting.getLeaveWork1Stamp().startTime() == workStart)
                            && (_self.fixedWorkSetting.getLeaveWork1Stamp().endTime() == startWork2)
                            && (_self.fixedWorkSetting.getGoWork2Stamp().startTime() == endWork1 + 1)
                            && (_self.fixedWorkSetting.getGoWork2Stamp().endTime() == workEnd)
                            && (_self.fixedWorkSetting.getLeaveWork2Stamp().startTime() == startWork2 + 1)
                            && (_self.fixedWorkSetting.getLeaveWork2Stamp().endTime() == workEnd)) {
                            _self.isInterlockDialogJ(true);
                        } else {
                            _self.isInterlockDialogJ(false);    
                        }
                    } else {                                                   
                        if ((_self.fixedWorkSetting.getGoWork1Stamp().startTime() == workStart) 
                            && (_self.fixedWorkSetting.getGoWork1Stamp().endTime() == workEnd)
                            && (_self.fixedWorkSetting.getLeaveWork1Stamp().startTime() == workStart)
                            && (_self.fixedWorkSetting.getLeaveWork1Stamp().endTime() == workEnd)) {
                            _self.isInterlockDialogJ(true);
                        } else {
                            _self.isInterlockDialogJ(false);    
                        }
                    }                    
                    return;
                }              
                if (_self.workTimeSetting.isFlex()) {    
                    let workStart: number = _self.predetemineTimeSetting.startDateClock();
                    let workEnd: number = _self.predetemineTimeSetting.startDateClock() + _self.predetemineTimeSetting.rangeTimeDay();               
                    if ((_self.flexWorkSetting.getGoWork1Stamp().startTime() == workStart) 
                        && (_self.flexWorkSetting.getGoWork1Stamp().endTime() == workEnd)
                        && (_self.flexWorkSetting.getLeaveWork1Stamp().startTime() == workStart)
                        && (_self.flexWorkSetting.getLeaveWork1Stamp().endTime() == workEnd)) {
                        _self.isInterlockDialogJ(true);
                    } else {
                        _self.isInterlockDialogJ(false);    
                    }
                    return;
                }
                if (_self.workTimeSetting.isFlow()) {
                    let workStart: number = _self.predetemineTimeSetting.startDateClock();
                    let workEnd: number = _self.predetemineTimeSetting.startDateClock() + _self.predetemineTimeSetting.rangeTimeDay();  
                    if ((_self.flowWorkSetting.stampReflectTimezone.getGoWorkFlowStamp().startTime() == workStart) 
                        && (_self.flowWorkSetting.stampReflectTimezone.getGoWorkFlowStamp().endTime() == workEnd)
                        && (_self.flowWorkSetting.stampReflectTimezone.getLeaveWorkFlowStamp().startTime() == workStart)
                        && (_self.flowWorkSetting.stampReflectTimezone.getLeaveWorkFlowStamp().endTime() == workEnd)) {
                        _self.isInterlockDialogJ(true);
                    } else {
                        _self.isInterlockDialogJ(false);    
                    }
                    return;
                }                            
                if (_self.workTimeSetting.isDiffTime()) {
                    let workStart: number = _self.predetemineTimeSetting.startDateClock();
                    let workEnd: number = _self.predetemineTimeSetting.startDateClock() + _self.predetemineTimeSetting.rangeTimeDay();  
                    if ((_self.diffWorkSetting.stampReflectTimezone.getGoWork1Stamp().startTime() == workStart) 
                        && (_self.diffWorkSetting.stampReflectTimezone.getGoWork1Stamp().endTime() == workEnd)
                        && (_self.diffWorkSetting.stampReflectTimezone.getLeaveWork1Stamp().startTime() == workStart)
                        && (_self.diffWorkSetting.stampReflectTimezone.getLeaveWork1Stamp().endTime() == workEnd)) {
                        _self.isInterlockDialogJ(true);
                    } else {
                        _self.isInterlockDialogJ(false);    
                    }
                    return;
                }               
            }
        }

        export class WorkTimeSettingLoader extends WorkTimeSettingModel {
            workTimeAtrEnums: EnumConstantDto[];
            workTimeMethodEnums: EnumConstantDto[];
            loadListWorktime: (selectedCode?: string, selectedIndex?: number) => JQueryPromise<void>;
            selectedWorktimeCode: KnockoutObservable<string>;
            constructor(selectedWorktimeCode: KnockoutObservable<string>) {
                super();
                let self = this;
                this.selectedWorktimeCode = selectedWorktimeCode;
                this.isAbolish(true); // initial value in specs = clear, update default show value, remove A3_6
                this.workTimeDivision.workTimeDailyAtr(3);
                this.workTimeDivision.workTimeMethodSet(3);
                this.workTimeDivision.workTimeDailyAtr.subscribe(() => {
                    this.loadListWorktime(self.selectedWorktimeCode());
                });
                this.workTimeDivision.workTimeMethodSet.subscribe(() => {
                    this.loadListWorktime(self.selectedWorktimeCode());
                });
                this.isAbolish.subscribe(() => {
                    this.loadListWorktime(self.selectedWorktimeCode());
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
                all.value = 3;
                all.localizedName = "全て";
                self.workTimeAtrEnums.unshift(all);
                self.workTimeMethodEnums.unshift(all);
            }

            public selectAll(): void {
                let self = this;
                self.workTimeDivision.workTimeDailyAtr(3);
                self.workTimeDivision.workTimeMethodSet(3);
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
            FLOW,
            ALL
        }
        
        export enum TabMode {
            DETAIL,
            SIMPLE
        }
        
        export enum ScreenMode {
            NEW,
            UPDATE,
            COPY
        }
        
        export class TabID {
            static TAB1 = "tab-1";
            static TAB2 = "tab-2";
            static TAB3 = "tab-3";
            static TAB4 = "tab-4";
            static TAB5 = "tab-5";
            static TAB6 = "tab-6";
            static TAB7 = "tab-7";
            static TAB8 = "tab-8";
            static TAB9 = "tab-9";
            static TAB10 = "tab-10";
            static TAB11 = "tab-11";
            static TAB12 = "tab-12";
            static TAB13 = "tab-13";
            static TAB14 = "tab-14";
            static TAB15 = "tab-15";
            static TAB16 = "tab-16";
            static TAB17 = "tab-17";
        }
    }
}