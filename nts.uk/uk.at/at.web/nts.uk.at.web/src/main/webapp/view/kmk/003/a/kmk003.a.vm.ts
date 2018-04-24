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
    import FlowWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flowset.FlowWorkSettingModel;
    import DiffTimeWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.DiffTimeWorkSettingModel;
    import FlexWorkSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.FlexWorkSettingModel;
    
    import FixedWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FixedWorkSettingSaveCommand;
    import FlowWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FlowWorkSettingSaveCommand;
    import FlexWorkSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.FlexWorkSettingSaveCommand;
    import DiffTimeSettingSaveCommand = nts.uk.at.view.kmk003.a.service.model.command.DiffTimeWorkSettingSaveCommand;
    
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
                self.isDetailMode = ko.observable(false);
                self.useHalfDay = ko.observable(false); // A5_19 initial value = false
                self.useHalfDay.subscribe(() => {
                    self.clearAllError();
                });
                self.mainSettingModel = new MainSettingModel(self.useHalfDay);
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

                self.selectedWorkTimeCode = ko.observable('');
                self.workTimeSettingLoader = new WorkTimeSettingLoader(self.mainSettingModel.workTimeSetting.worktimeCode);
                
                self.workTimeSettings = ko.observableArray([]);
                self.columnWorktimeSettings = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMK003_10"), prop: 'worktimeCode', width: 50 },
                    { headerText: nts.uk.resource.getText("KMK003_11"), prop: 'workTimeName', width: 180 },
                    { headerText: nts.uk.resource.getText("KMK003_12"), prop: 'isAbolish', width: 40,
                        formatter: isAbolish => {
                            if (isAbolish === true || isAbolish === 'true') {
                                return '<div style="text-align: center;max-height: 18px;"><i class="icon icon-x"></i></div>';
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
                self.isLoading = ko.observable(false);
                self.tabMode = ko.observable(TabMode.DETAIL);
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

                self.useHalfDayOptions = ko.observableArray([
                    { code: true, name: nts.uk.resource.getText("KMK003_49") },
                    { code: false, name: nts.uk.resource.getText("KMK003_50") }
                ]);

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
                
                self.selectedTab = ko.observable(TabID.TAB1);

                //data get from service
                self.isClickSave = ko.observable(false);
                
                self.selectedWorkTimeCode.subscribe(function(worktimeCode: string){
                    if (worktimeCode) {
                        self.loadWorktimeSetting(worktimeCode);
                        // focus worktime atr
                        $('#search-daily-atr').focus();
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
                        self.mainSettingModel.updateData(worktimeSettingInfo);
                        self.isLoading(true);
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
                        self.mainSettingModel.updateData(worktimeSettingInfo);

                        self.isLoading(true);
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
                    let simpleTabsId: string[] = [TabID.TAB1, TabID.TAB2, TabID.TAB3, TabID.TAB4, TabID.TAB5, TabID.TAB6, TabID.TAB7, TabID.TAB9, TabID.TAB10, TabID.TAB11, TabID.TAB12];
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
                })
                $('.time-range-editor').each((index, element) => {
                    $('#' + element.id).validateTimeRange();
                });
            }

            /**
             * For testting
             */
            public testFixed(): void {
                let self = this;
                let testData = JSON.parse('{"predseting":{"companyId":"000000000000-0001","rangeTimeDay":1440,"workTimeCode":"001","predTime":{"addTime":{"oneDay":0,"morning":0,"afternoon":0},"predTime":{"oneDay":120,"morning":60,"afternoon":60}},"nightShift":false,"prescribedTimezoneSetting":{"morningEndTime":720,"afternoonStartTime":780,"lstTimezone":[{"useAtr":false,"workNo":1,"start":1,"end":1140},{"useAtr":false,"workNo":2,"start":0,"end":0}]},"startDateClock":1,"predetermine":false},"worktimeSetting":{"companyId":"000000000000-0001","worktimeCode":"001","workTimeDivision":{"workTimeDailyAtr":0,"workTimeMethodSet":0},"isAbolish":false,"colorCode":"","workTimeDisplayName":{"workTimeName":"test fixed","workTimeAbName":"","workTimeSymbol":""},"memo":"","note":""},"flexWorkSetting":{"workTimeCode":null,"coreTimeSetting":null,"restSetting":null,"offdayWorkTime":null,"commonSetting":null,"useHalfDayShift":false,"lstHalfDayWorkTimezone":null,"lstStampReflectTimezone":null,"calculateSetting":null},"fixedWorkSetting":{"workTimeCode":"001","offdayWorkTimezone":{"restTimezone":{"lstTimezone":[]},"lstWorkTimezone":[]},"commonSetting":{"zeroHStraddCalculateSet":false,"intervalSet":{"useIntervalExemptionTime":false,"intervalExemptionTimeRound":{"roundingTime":0,"rounding":1},"intervalTime":{"intervalTime":0,"rounding":{"roundingTime":0,"rounding":1}},"useIntervalTime":false},"subHolTimeSet":[{"subHolTimeSet":{"certainTime":0,"useDivision":true,"designatedTime":{"oneDayTime":0,"halfDayTime":0},"subHolTransferSetAtr":0},"workTimeCode":"001","originAtr":0},{"subHolTimeSet":{"certainTime":0,"useDivision":true,"designatedTime":{"oneDayTime":0,"halfDayTime":0},"subHolTransferSetAtr":0},"workTimeCode":"001","originAtr":1}],"raisingSalarySet":"","medicalSet":[{"roundingSet":{"roundingTime":0,"rounding":1},"workSystemAtr":0,"applicationTime":0},{"roundingSet":{"roundingTime":0,"rounding":1},"workSystemAtr":1,"applicationTime":0}],"goOutSet":{"totalRoundingSet":{"setSameFrameRounding":0,"frameStraddRoundingSet":0},"diffTimezoneSetting":{"pubHolWorkTimezone":{"officalUseCompenGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":0}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}},"privateUnionGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}}},"workTimezone":{"officalUseCompenGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":0}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}},"privateUnionGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}}},"ottimezone":{"officalUseCompenGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":0}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}},"privateUnionGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}}}}},"stampSet":{"roundingSets":[{"roundingSet":{"fontRearSection":1,"roundingTimeUnit":0},"section":0},{"roundingSet":{"fontRearSection":0,"roundingTimeUnit":0},"section":1}],"prioritySets":[{"priorityAtr":0,"stampAtr":0},{"priorityAtr":1,"stampAtr":1}]},"lateNightTimeSet":{"roundingSetting":{"roundingTime":0,"rounding":1}},"shortTimeWorkSet":{"nursTimezoneWorkUse":false,"employmentTimeDeduct":false,"childCareWorkUse":false},"extraordTimeSet":{"holidayFrameSet":{"inLegalBreakoutFrameNo":1,"outLegalBreakoutFrameNo":1,"outLegalPubHolFrameNo":1},"timeRoundingSet":{"roundingTime":0,"rounding":1},"otFrameSet":{"otFrameNo":1,"inLegalWorkFrameNo":1,"settlementOrder":1},"calculateMethod":0},"lateEarlySet":{"commonSet":{"delFromEmTime":false},"otherClassSets":[{"delTimeRoundingSet":{"roundingTime":0,"rounding":1},"stampExactlyTimeIsLateEarly":false,"graceTimeSet":{"includeWorkingHour":false,"graceTime":0},"recordTimeRoundingSet":{"roundingTime":0,"rounding":1},"lateEarlyAtr":0},{"delTimeRoundingSet":{"roundingTime":0,"rounding":1},"stampExactlyTimeIsLateEarly":false,"graceTimeSet":{"includeWorkingHour":false,"graceTime":0},"recordTimeRoundingSet":{"roundingTime":0,"rounding":1},"lateEarlyAtr":1}]}},"useHalfDayShift":false,"fixedWorkRestSetting":{"commonRestSet":{"calculateMethod":0},"fixedRestCalculateMethod":0},"lstHalfDayWorkTimezone":[{"restTimezone":{"lstTimezone":[]},"workTimezone":{"lstWorkingTimezone":[{"employmentTimeFrameNo":1,"timezone":{"rounding":{"roundingTime":0,"rounding":0},"start":1,"end":1140}}],"lstOTTimezone":[]},"dayAtr":0},{"restTimezone":{"lstTimezone":[]},"workTimezone":{"lstWorkingTimezone":[],"lstOTTimezone":[]},"dayAtr":1},{"restTimezone":{"lstTimezone":[]},"workTimezone":{"lstWorkingTimezone":[],"lstOTTimezone":[]},"dayAtr":2}],"lstStampReflectTimezone":[],"legalOTSetting":1},"flowWorkSetting":{"workingCode":null,"restSetting":null,"offdayWorkTimezone":null,"commonSetting":null,"halfDayWorkTimezone":null,"stampReflectTimezone":null,"designatedSetting":null,"flowSetting":null},"diffTimeWorkSetting":{"workTimeCode":null,"restSet":null,"dayoffWorkTimezone":null,"commonSet":null,"changeExtent":null,"halfDayWorkTimezones":null,"stampReflectTimezone":null,"overtimeSetting":null,"useHalfDayShift":false}}');
                self.mainSettingModel.updateData(testData);
            }
            public testFlex(): void {
                let self = this;
                let testData = JSON.parse('{"predseting":{"companyId":"000000000000-0001","rangeTimeDay":1440,"workTimeCode":"002","predTime":{"addTime":{"oneDay":0,"morning":0,"afternoon":0},"predTime":{"oneDay":120,"morning":60,"afternoon":60}},"nightShift":false,"prescribedTimezoneSetting":{"morningEndTime":720,"afternoonStartTime":780,"lstTimezone":[{"useAtr":false,"workNo":1,"start":1,"end":1380},{"useAtr":false,"workNo":2,"start":0,"end":0}]},"startDateClock":1,"predetermine":false},"worktimeSetting":{"companyId":"000000000000-0001","worktimeCode":"002","workTimeDivision":{"workTimeDailyAtr":1,"workTimeMethodSet":0},"isAbolish":false,"colorCode":"","workTimeDisplayName":{"workTimeName":"test flex","workTimeAbName":"","workTimeSymbol":""},"memo":"","note":""},"flexWorkSetting":{"workTimeCode":"002","coreTimeSetting":{"coreTimeSheet":{"startTime":1,"endTime":1201},"timesheet":1,"minWorkTime":0},"restSetting":{"commonRestSetting":{"calculateMethod":0},"flowRestSetting":{"flowRestSetting":{"useStamp":false,"useStampCalcMethod":0,"timeManagerSetAtr":0,"calculateMethod":0},"flowFixedRestSetting":{"usePrivateGoOutRest":false,"useAssoGoOutRest":false,"calculateMethod":0,"referRestTime":false},"usePluralWorkRestTime":false}},"offdayWorkTime":{"lstWorkTimezone":[],"restTimezone":{"fixRestTime":true,"fixedRestTimezone":{"timezones":[]},"flowRestTimezone":{"flowRestSets":[],"useHereAfterRestSet":false,"hereAfterRestSet":{"flowRestTime":0,"flowPassageTime":0}}}},"commonSetting":{"zeroHStraddCalculateSet":false,"intervalSet":{"useIntervalExemptionTime":false,"intervalExemptionTimeRound":{"roundingTime":0,"rounding":1},"intervalTime":{"intervalTime":0,"rounding":{"roundingTime":0,"rounding":1}},"useIntervalTime":false},"subHolTimeSet":[{"subHolTimeSet":{"certainTime":0,"useDivision":true,"designatedTime":{"oneDayTime":0,"halfDayTime":0},"subHolTransferSetAtr":0},"workTimeCode":"002","originAtr":0},{"subHolTimeSet":{"certainTime":0,"useDivision":true,"designatedTime":{"oneDayTime":0,"halfDayTime":0},"subHolTransferSetAtr":0},"workTimeCode":"002","originAtr":1}],"raisingSalarySet":"","medicalSet":[{"roundingSet":{"roundingTime":0,"rounding":1},"workSystemAtr":0,"applicationTime":0},{"roundingSet":{"roundingTime":0,"rounding":1},"workSystemAtr":1,"applicationTime":0}],"goOutSet":{"totalRoundingSet":{"setSameFrameRounding":0,"frameStraddRoundingSet":0},"diffTimezoneSetting":{"pubHolWorkTimezone":{"officalUseCompenGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":0}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}},"privateUnionGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}}},"workTimezone":{"officalUseCompenGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":0}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}},"privateUnionGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}}},"ottimezone":{"officalUseCompenGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":0}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}},"privateUnionGoOut":{"deductTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}},"approTimeRoundingSetting":{"roundingMethod":0,"roundingSetting":{"roundingTime":0,"rounding":1}}}}}},"stampSet":{"roundingSets":[{"roundingSet":{"fontRearSection":1,"roundingTimeUnit":0},"section":0},{"roundingSet":{"fontRearSection":0,"roundingTimeUnit":0},"section":1}],"prioritySets":[{"priorityAtr":0,"stampAtr":0},{"priorityAtr":1,"stampAtr":1}]},"lateNightTimeSet":{"roundingSetting":{"roundingTime":0,"rounding":1}},"shortTimeWorkSet":{"nursTimezoneWorkUse":false,"employmentTimeDeduct":false,"childCareWorkUse":false},"extraordTimeSet":{"holidayFrameSet":{"inLegalBreakoutFrameNo":1,"outLegalBreakoutFrameNo":1,"outLegalPubHolFrameNo":1},"timeRoundingSet":{"roundingTime":0,"rounding":1},"otFrameSet":{"otFrameNo":1,"inLegalWorkFrameNo":1,"settlementOrder":1},"calculateMethod":0},"lateEarlySet":{"commonSet":{"delFromEmTime":false},"otherClassSets":[{"delTimeRoundingSet":{"roundingTime":0,"rounding":1},"stampExactlyTimeIsLateEarly":false,"graceTimeSet":{"includeWorkingHour":false,"graceTime":0},"recordTimeRoundingSet":{"roundingTime":0,"rounding":1},"lateEarlyAtr":0},{"delTimeRoundingSet":{"roundingTime":0,"rounding":1},"stampExactlyTimeIsLateEarly":false,"graceTimeSet":{"includeWorkingHour":false,"graceTime":0},"recordTimeRoundingSet":{"roundingTime":0,"rounding":1},"lateEarlyAtr":1}]}},"useHalfDayShift":false,"lstHalfDayWorkTimezone":[{"restTimezone":{"fixRestTime":true,"fixedRestTimezone":{"timezones":[]},"flowRestTimezone":{"flowRestSets":[],"useHereAfterRestSet":false,"hereAfterRestSet":{"flowRestTime":0,"flowPassageTime":0}}},"workTimezone":{"lstWorkingTimezone":[{"employmentTimeFrameNo":1,"timezone":{"rounding":{"roundingTime":0,"rounding":0},"start":1,"end":1380}}],"lstOTTimezone":[]},"ampmAtr":0},{"restTimezone":{"fixRestTime":true,"fixedRestTimezone":{"timezones":[]},"flowRestTimezone":{"flowRestSets":[],"useHereAfterRestSet":false,"hereAfterRestSet":{"flowRestTime":0,"flowPassageTime":0}}},"workTimezone":{"lstWorkingTimezone":[],"lstOTTimezone":[]},"ampmAtr":1},{"restTimezone":{"fixRestTime":true,"fixedRestTimezone":{"timezones":[]},"flowRestTimezone":{"flowRestSets":[],"useHereAfterRestSet":false,"hereAfterRestSet":{"flowRestTime":0,"flowPassageTime":0}}},"workTimezone":{"lstWorkingTimezone":[],"lstOTTimezone":[]},"ampmAtr":2}],"lstStampReflectTimezone":[],"calculateSetting":{"removeFromWorkTime":0,"calculateSharing":0}},"fixedWorkSetting":{"workTimeCode":null,"offdayWorkTimezone":null,"commonSetting":null,"useHalfDayShift":null,"fixedWorkRestSetting":null,"lstHalfDayWorkTimezone":null,"lstStampReflectTimezone":null,"legalOTSetting":null},"flowWorkSetting":{"workingCode":null,"restSetting":null,"offdayWorkTimezone":null,"commonSetting":null,"halfDayWorkTimezone":null,"stampReflectTimezone":null,"designatedSetting":null,"flowSetting":null},"diffTimeWorkSetting":{"workTimeCode":null,"restSet":null,"dayoffWorkTimezone":null,"commonSet":null,"changeExtent":null,"halfDayWorkTimezones":null,"stampReflectTimezone":null,"overtimeSetting":null,"useHalfDayShift":false}}');
                self.mainSettingModel.updateData(testData);
            }

            /**
             * setup test mode
             */
            private setupTestMode(): void {
                let self = this;
                const inputKeys: any[] = [];
                const patwuot = 'ahihi';

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
                // re validate
                self.validateInput();

                // stop function if has error.
                if ($('.nts-editor').ntsError('hasError') || $('.time-range-editor').ntsError('hasError')) {
                    return;
                }
                self.isClickSave(true);
                self.mainSettingModel.save(self.isNewOrCopyMode(), self.tabMode())
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

                // set screen mode
                self.screenMode(ScreenMode.COPY);
                
                // focus worktime atr
                $('#search-daily-atr').focus();
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
            useHalfDay: KnockoutObservable<boolean>;
            
            constructor(useHalfDay: KnockoutObservable<boolean>) {
                this.isChangeItemTable = ko.observable(false);
                this.useHalfDay = useHalfDay; // bind to useHalfDay of main screen
                
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
                        .fail(err => dfd.reject(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }
                if (self.workTimeSetting.isFixed()) {
                    service.saveFixedWorkSetting(self.toFixedCommand(addMode, tabMode))
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => dfd.reject(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }                
                if (self.workTimeSetting.isFlow()) {
                    service.saveFlowWorkSetting(self.toFlowCommand(addMode, tabMode))
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => dfd.reject(err))
                        .always(() => _.defer(() => nts.uk.ui.block.clear()));
                }

                if (self.workTimeSetting.isDiffTime()) {
                    service.saveDiffTimeWorkSetting(self.toDiffTimeCommand(addMode, tabMode))
                        .done(() => self.onSaveSuccess(dfd))
                        .fail(err => dfd.reject(err))
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

            toFlowCommand(addMode: boolean, tabMode: number): FlowWorkSettingSaveCommand {
                let _self = this;
                let command: FlowWorkSettingSaveCommand = {
                    addMode: addMode,
                    predseting: _self.predetemineTimeSetting.toDto(),
                    worktimeSetting: _self.workTimeSetting.toDto(),
                    flowWorkSetting: _self.flowWorkSetting.toDto(_self.commonSetting),
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
                    screenMode: tabMode,
                    addMode: addMode,
                    flexWorkSetting: self.flexWorkSetting.toDto(self.commonSetting),
                    predseting: self.predetemineTimeSetting.toDto(),
                    worktimeSetting: self.workTimeSetting.toDto()
                };
                return command;
            }
            
            /**
             * Collect difftime data and convert to command dto
             */
            toDiffTimeCommand(addMode: boolean, tabMode: number): DiffTimeSettingSaveCommand {
                let self = this;
                let command: DiffTimeSettingSaveCommand;
                command = {
                    screenMode: tabMode,
                    addMode: addMode,
                    diffTimeWorkSetting: self.diffWorkSetting.toDto(self.commonSetting),
                    predseting: self.predetemineTimeSetting.toDto(),
                    worktimeSetting: self.workTimeSetting.toDto()
                };
                return command;
            }

            updateData(worktimeSettingInfo: WorkTimeSettingInfoDto): void {
                let self = this;
                self.workTimeSetting.updateData(worktimeSettingInfo.worktimeSetting);
                self.predetemineTimeSetting.updateData(worktimeSettingInfo.predseting);
                
                if (self.workTimeSetting.isFlex()) {
                    self.flexWorkSetting.updateData(worktimeSettingInfo.flexWorkSetting);
                    self.commonSetting.updateData(worktimeSettingInfo.flexWorkSetting.commonSetting);

                    // set useHalfDay to mainScreen model
                    self.useHalfDay(worktimeSettingInfo.flexWorkSetting.useHalfDayShift);
                }
                if (self.workTimeSetting.isFlow()) {
                    self.flowWorkSetting.updateData(worktimeSettingInfo.flowWorkSetting);
                    self.commonSetting.updateData(worktimeSettingInfo.flowWorkSetting.commonSetting);
                }
                if (self.workTimeSetting.isFixed()) {
                    self.fixedWorkSetting.updateData(worktimeSettingInfo.fixedWorkSetting);
                    self.commonSetting.updateData(worktimeSettingInfo.fixedWorkSetting.commonSetting);

                    // set useHalfDay to mainScreen model
                    self.useHalfDay(worktimeSettingInfo.fixedWorkSetting.useHalfDayShift);
                }
                
                if (self.workTimeSetting.isDiffTime()) {
                    self.diffWorkSetting.updateData(worktimeSettingInfo.diffTimeWorkSetting);
                    self.commonSetting.updateData(worktimeSettingInfo.diffTimeWorkSetting.commonSet);

                    // set useHalfDay to mainScreen model
                    self.useHalfDay(worktimeSettingInfo.diffTimeWorkSetting.useHalfDayShift);
                }
            }
            
            resetData(isNewMode?: boolean) {
                let self = this;
                self.useHalfDay(false);
                self.fixedWorkSetting.resetData();
                self.flowWorkSetting.resetData();
                self.flexWorkSetting.resetData();
                self.diffWorkSetting.resetData();
                if (!isNewMode) {
                    //check change mode to convert data
                    self.commonSetting.resetData();
                    self.predetemineTimeSetting.resetData();
                    self.workTimeSetting.resetData();
                    self.workTimeSetting.resetWorkTimeDivision();
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
                this.isAbolish(false); // initial value in specs = clear
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
                all.value = 3; //TODO: nen cho thanh so may?
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
            SIMPLE,
            DETAIL
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