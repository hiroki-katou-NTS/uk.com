module a2 {
    
    import WorkTimeDailyAtr = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeDailyAtr;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EmTimeZoneSetDto = nts.uk.at.view.kmk003.a.service.model.common.EmTimeZoneSetDto;
    import TimeZoneRoundingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeZoneRoundingDto;
    import TimeRoundingSettingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeRoundingSettingDto;
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    import TimeZoneRoundingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeZoneRoundingModel;
    
    import FixHalfDayWorkTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.fixedset.FixHalfDayWorkTimezoneModel;
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import TimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.predset.TimezoneModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import TabMode = nts.uk.at.view.kmk003.a.viewmodel.TabMode;
    import ScreenMode = nts.uk.at.view.kmk003.a.viewmodel.ScreenMode;
    
    /**
     * ScreenModel
     */
    class ScreenModel {

        // Defined parameter binding
        parentModel: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;
        
        // Defined variable other mode
        dataSourceOneDay: KnockoutObservableArray<TimeZoneModel>;
        fixTableOptionOneDay: any;
        
        dataSourceMorning: KnockoutObservableArray<TimeZoneModel>;
        fixTableOptionMorning: any;
        
        dataSourceAfternoon: KnockoutObservableArray<TimeZoneModel>;
        fixTableOptionAfternoon: any;
        
        dataSourceOneDaySimpleMode: KnockoutObservableArray<TimeZoneModel>;
        fixTableOptionOneDaySimpleMode: any;
        isEnableTimeRangeOneDaySimpleMode: KnockoutObservable<boolean>;
        
        // Defined variable flow mode
        roundingProcsses: KnockoutObservableArray<any>;
        settingAttrs: KnockoutObservableArray<any>;
        calculationMethods: KnockoutObservableArray<any>;
        selectedRoundingTime: KnockoutObservable<any>;
        selectedRounding: KnockoutObservable<any>;
        selectedCalcStartTimeSet: KnockoutObservable<any>;
        selectedCodeSetting: KnockoutObservable<any>;
        dataModelOneDay: EmTimeZoneSetModel[];
        
        
        // Defined variable Screen model
        workTimeDailyAtr: KnockoutObservable<number>
        tabMode: KnockoutObservable<number>
        isSimpleMode: KnockoutObservable<boolean>;
        isFlowMode: KnockoutObservable<boolean>;
        isUseHalfDay: KnockoutObservable<boolean>;

        /**
        * Constructor.
        */
        constructor(input: any) {
            let self = this;
            
            // ====================================== Set Parameter Binding ======================================
            self.parentModel = input.mainModel;
            self.settingEnum = input.enum;
            
            self.workTimeDailyAtr = self.parentModel.workTimeSetting.workTimeDivision.workTimeDailyAtr;
            self.tabMode = input.tabMode;
            self.isSimpleMode = ko.computed(() => {
                return self.tabMode() == TabMode.SIMPLE;
            });
            self.isFlowMode = self.parentModel.workTimeSetting.isFlow;
            self.isUseHalfDay = input.useHalfDay; 
            
            
            // ====================================== Defined Variable Other Mode ======================================
            self.dataSourceOneDay = ko.observableArray([]);
            self.fixTableOptionOneDay = {
                maxRow: 5,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceOneDay,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 46
            };
            
            self.dataSourceMorning = ko.observableArray([]);
            self.fixTableOptionMorning = {
                maxRow: 5,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceMorning,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 47
            };
            
            self.dataSourceAfternoon = ko.observableArray([]);
            self.fixTableOptionAfternoon = {
                maxRow: 5,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceAfternoon,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: 48
            };
            self.isEnableTimeRangeOneDaySimpleMode = ko.computed(() => {
                return self.isSimpleMode() && (self.parentModel.workTimeSetting.isFlex()
                    || self.parentModel.workTimeSetting.isFlow());
            });;
            
            self.dataSourceOneDaySimpleMode = ko.observableArray([]);
            self.fixTableOptionOneDaySimpleMode = {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 1,
                isShowButton: false,
                dataSource: self.dataSourceOneDaySimpleMode,
                isMultipleSelect: false,
                columns: self.columnSetting(),
                tabindex: 1
            };
            
            // ====================================== Defined Variable Flow Mode ======================================
            
            self.roundingProcsses = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_91") },
                { code: 0, name: nts.uk.resource.getText("KMK003_92") }
            ]);
            self.calculationMethods = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KMK003_136") },
                { code: 1, name: nts.uk.resource.getText("KMK003_137") }
            ]);
            
            self.selectedRounding = ko.observable(0);
            self.selectedCalcStartTimeSet = ko.observable(0);
            self.selectedCodeSetting = ko.observable(0);
            
            // ====================================== SUBSCRIBER ======================================
            self.parentModel.workTimeSetting.isFlex.subscribe(newValue => {
                if (newValue) {
                    self.dataSourceOneDaySimpleMode([]);
                }
                self.bindDataToScreen();
            });
            self.parentModel.isChangeItemTable.subscribe(newValue => {
                self.bindDataToScreen();
            });
            input.screenMode.subscribe((newValue: ScreenMode) => {
                if (newValue == ScreenMode.UPDATE) {
                    return;
                }
                self.bindDataToScreen();
            });
            
            input.selectedTab.subscribe((newValue: string) => {
                if (newValue !== 'tab-2' || !self.isSimpleMode()) {
                    return;
                }
                self.bindDataToScreen();
            });
            
            self.parentModel.workTimeSetting.workTimeDivision.workTimeMethodSet.subscribe(newValue => {
                self.bindDataToScreen();
            });
            self.isSimpleMode.subscribe(newValue => {
                
                if (newValue) {
                    self.dataSourceOneDaySimpleMode([]);
                }
                
                self.bindDataToScreen();
            });
            self.isFlowMode.subscribe(newValue => {
                if (newValue) {
                    self.dataSourceOneDaySimpleMode([]);
                }
                self.bindDataToScreen();
            });
            self.isUseHalfDay.subscribe(newValue => {
                self.bindDataToScreen();
            });
            
            
            input.isClickSave.subscribe((newValue: any) => {
                if (!newValue) {
                    return;
                }
                self.convertData();
                self.bindDataToScreen();
            });
        }

        //=============================================================== ==========================================
        //============================================= BINDING DTO TO MODEL =======================================
        //=============================================================== ==========================================
        
        /**
         * Binding Data To Screen
         */
        public bindDataToScreen() {
            let self = this;
            
            // Flow mode
            if (self.isFlowMode()) {
                self.bindDataFlowMode();
            }
            // Other mode
            else {
                self.bindDataOtherMode();
            }
        }
        
        /**
         * Binding Data Other Mode
         */
        private bindDataOtherMode() {
            let self = this;
            
            // Simple mode
            if (self.isSimpleMode()) {
                
                let emTimezone: EmTimeZoneSetModel;
                
                let empTimeFrameNo: number = 1;
                
                //============= Fixed Mode =============
                if (self.parentModel.workTimeSetting.isFixed()) {
                    emTimezone = self.parentModel.fixedWorkSetting.getHDWtzOneday()
                        .workTimezone.getWorkingTimezoneByEmploymentTimeFrameNo(empTimeFrameNo);
                }
                //============= Flex Mode =============
                else if (self.parentModel.workTimeSetting.isFlex()) {
                    // all day
                    emTimezone = self.parentModel.flexWorkSetting.getHDWtzOneday()
                        .workTimezone.getWorkingTimezoneByEmploymentTimeFrameNo(empTimeFrameNo);
                }
                //============= DiffTime Mode =============
                else if (self.parentModel.workTimeSetting.isDiffTime()) {
                    // all day
                    emTimezone = self.parentModel.diffWorkSetting.getHDWtzOneday()
                        .workTimezone.employmentTimezones()[0];
                }

                //============= Convert =============
                let item: TimezoneModel = self.parentModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftOne;
                   // ============= Flex Mode =============
                    
                let timeRange: TimePeriod = {
                    startTime: item.start(),
                    endTime: item.end()
                }
                if (self.dataSourceOneDaySimpleMode().length <= 0) {
                    timeRange.startTime = 0;
                    timeRange.endTime = 0;
                    self.dataSourceOneDaySimpleMode.push(new TimeZoneModel(timeRange, emTimezone ? emTimezone.timezone.rounding.roundingTime() : 0,
                        emTimezone ? emTimezone.timezone.rounding.rounding() : 0));
                }
                item.valueChangedNotifier.subscribe(() => {
                    if (self.isSimpleMode() && (self.parentModel.workTimeSetting.isFlex()
                        || self.parentModel.workTimeSetting.isFlow())) {
                        return;
                    }
                    let timeRange: TimePeriod = {
                        startTime: item.start(),
                        endTime: item.end()
                    }
                    self.dataSourceOneDaySimpleMode([]);
                    self.dataSourceOneDaySimpleMode.push(new TimeZoneModel(timeRange, emTimezone ? emTimezone.timezone.rounding.roundingTime() : 0,
                        emTimezone ? emTimezone.timezone.rounding.rounding() : 0));
                });
                item.valueChangedNotifier.valueHasMutated();
            }
            // Detail mode
            else {
                self.bindingDataDto();
            }
        }
        
        /**
         * Binding Data Flow Mode
         */
        private bindDataFlowMode() {
            let self = this;
            
            // Both simple and detail mode.
            let workTimeRounding:TimeRoundingSettingModel = self.parentModel.flowWorkSetting.halfDayWorkTimezone.workTimeZone
                    .workTimeRounding;
            self.selectedRoundingTime = workTimeRounding.roundingTime;
            self.selectedRounding = workTimeRounding.rounding;
            
            self.selectedCalcStartTimeSet = self.parentModel.flowWorkSetting.flowSetting.calculateSetting.calcStartTimeSet;
            
            // Simple mode
            if (!self.isSimpleMode()) {
                self.selectedCodeSetting = self.parentModel.flowWorkSetting.flowSetting.overtimeSetting.fixedChangeAtr;
            }
        }
        
        /**
         * Binding data from domain: Flex, Fixed, Diff
         */
        private bindingDataDto() {
            let self = this;
            
            let dataSourceAllDay: KnockoutObservableArray<EmTimeZoneSetModel>;
            let dataSourceMorning: KnockoutObservableArray<EmTimeZoneSetModel>;
            let dataSourceAfternoon: KnockoutObservableArray<EmTimeZoneSetModel>;
            
            //============= Fixed Mode =============
            if (self.parentModel.workTimeSetting.isFixed()) {
                // all day
                dataSourceAllDay = self.parentModel.fixedWorkSetting.getHDWtzOneday()
                    .workTimezone.lstWorkingTimezone;
                
                // morning
                dataSourceMorning = self.parentModel.fixedWorkSetting.getHDWtzMorning()
                    .workTimezone.lstWorkingTimezone;
                
                // afternoon
                dataSourceAfternoon = self.parentModel.fixedWorkSetting.getHDWtzAfternoon()
                    .workTimezone.lstWorkingTimezone;
            }
            
            //============= Flex Mode =============
            else if (self.parentModel.workTimeSetting.isFlex()) {
                // all day
                dataSourceAllDay = self.parentModel.flexWorkSetting.getHDWtzOneday()
                    .workTimezone.lstWorkingTimezone;
                
                // morning
                dataSourceMorning = self.parentModel.flexWorkSetting.getHDWtzMorning()
                    .workTimezone.lstWorkingTimezone;
                
                // afternoon
                dataSourceAfternoon = self.parentModel.flexWorkSetting.getHDWtzAfternoon()
                    .workTimezone.lstWorkingTimezone;
            }
            
            //============= DiffTime Mode =============
            else if (self.parentModel.workTimeSetting.isDiffTime()) {
                // all day
                dataSourceAllDay = self.parentModel.diffWorkSetting.getHDWtzOneday()
                    .workTimezone.employmentTimezones;
                
                // morning
                dataSourceMorning = self.parentModel.diffWorkSetting.getHDWtzMorning()
                    .workTimezone.employmentTimezones;
                
                // afternoon
                dataSourceAfternoon = self.parentModel.diffWorkSetting.getHDWtzAfternoon()
                    .workTimezone.employmentTimezones;
            }
            // convert data
            self.toModel(dataSourceMorning, self.dataSourceMorning);
            self.toModel(dataSourceAllDay, self.dataSourceOneDay);
            self.toModel(dataSourceAfternoon, self.dataSourceAfternoon);
            
            // callback subscribe dataSource table.
            self.dataSourceOneDay.valueHasMutated();
            self.dataSourceMorning.valueHasMutated();
            self.dataSourceAfternoon.valueHasMutated();
        }
        
        /**
         * Binding data domain
         */
        private toModel(dataSourceModel: KnockoutObservableArray<EmTimeZoneSetModel>,
            dataSource: KnockoutObservableArray<TimeZoneModel>) {
            let self = this;
            
            // empty list
            dataSource([]);
            
            if (!dataSourceModel) {
                return;
            }
                    
            // fill data
            _.forEach(dataSourceModel(), (item: EmTimeZoneSetModel) => {
                let timeRange: TimePeriod = {
                    startTime: item.timezone.start(),
                    endTime: item.timezone.end()
                }
                dataSource().push(new TimeZoneModel(timeRange, item.timezone.rounding.roundingTime(),
                    item.timezone.rounding.rounding()));
            });
        }
        
        //==========================================================================================================
        //============================================= CONVERT TO DTO =============================================
        //==========================================================================================================
        
        /**
         * Convert data by: 
         *  screen mode: Simple/Detail
         *  setting mode: Flow, Other flow
         */
        private convertData() {
            let self = this;
            
            // Other mode
            if (!self.isFlowMode()) {
                self.convertToDtoOtherMode();
            }
        }
        
        /**
         * Convert model to DTO: Other Mode
         */
        private convertToDtoOtherMode() {
            let self = this;
            
            // Simple mode
            if (self.isSimpleMode()) {
                //============= Fixed Mode =============
                if (self.parentModel.workTimeSetting.isFixed()) {
                    // all day
                    self.parentModel.fixedWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone(self.toDomain(self.dataSourceOneDaySimpleMode));
                }
                
                //============= Flex Mode =============
                else if (self.parentModel.workTimeSetting.isFlex()) {
                    // all day
                    self.parentModel.flexWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone(self.toDomain(self.dataSourceOneDaySimpleMode));
                }
                
                //============= DiffTime Mode =============
                else if (self.parentModel.workTimeSetting.isDiffTime()) {
                    // all day
                    self.parentModel.diffWorkSetting.getHDWtzOneday()
                        .workTimezone.employmentTimezones(self.toDomain(self.dataSourceOneDaySimpleMode));
                }
                
            }
            // Detail mode
            else {
                //============= Fixed Mode =============
                if (self.parentModel.workTimeSetting.isFixed()) {
                    self.parentModel.fixedWorkSetting. getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone(self.toDomain(self.dataSourceOneDay));
                    
                    // morning
                    self.parentModel.fixedWorkSetting.getHDWtzMorning()
                        .workTimezone.lstWorkingTimezone(self.toDomain(self.dataSourceMorning));
                    
                    // afternoon
                    self.parentModel.fixedWorkSetting.getHDWtzAfternoon()
                        .workTimezone.lstWorkingTimezone(self.toDomain(self.dataSourceAfternoon));
                }
                
                //============= Flex Mode =============
                else if (self.parentModel.workTimeSetting.isFlex()) {
                    // all day
                    self.parentModel.flexWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone(self.toDomain(self.dataSourceOneDay));
                    
                    // morning
                    self.parentModel.flexWorkSetting.getHDWtzMorning()
                        .workTimezone.lstWorkingTimezone(self.toDomain(self.dataSourceMorning));
                    
                    // afternoon
                    self.parentModel.flexWorkSetting.getHDWtzAfternoon()
                        .workTimezone.lstWorkingTimezone(self.toDomain(self.dataSourceAfternoon));
                }
                
                //============= DiffTime Mode =============
                else if (self.parentModel.workTimeSetting.isDiffTime()) {
                    // all day
                    self.parentModel.diffWorkSetting.getHDWtzOneday()
                        .workTimezone.employmentTimezones(self.toDomain(self.dataSourceOneDay));
                    
                    // morning
                    self.parentModel.diffWorkSetting.getHDWtzMorning()
                        .workTimezone.employmentTimezones(self.toDomain(self.dataSourceMorning));
                    
                    // afternoon
                    self.parentModel.diffWorkSetting.getHDWtzAfternoon()
                        .workTimezone.employmentTimezones(self.toDomain(self.dataSourceAfternoon));
                }
            }

        }
        
        /**
         * Convert model to DTO of Domain
         */
        private toDomain(dataSource: KnockoutObservableArray<TimeZoneModel>): EmTimeZoneSetModel[] {
            let self = this;
            
            let dataSourceModel: EmTimeZoneSetModel[] = [];
            
            // fill data
            _.forEach(dataSource(), (item: TimeZoneModel, index: number) => {
                let emTime: EmTimeZoneSetModel = new EmTimeZoneSetModel();
                
                emTime.employmentTimeFrameNo(index + 1);
                emTime.timezone.start(item.timeRange().startTime);
                emTime.timezone.end(item.timeRange().endTime);
                emTime.timezone.rounding.roundingTime = item.roundingTime;
                emTime.timezone.rounding.rounding = item.rounding;
                
                dataSourceModel.push(emTime); 
            });
            return dataSourceModel;
        }
        
        /**
         * Initial array setting column option
         */
         private columnSetting(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), 
                    key: "timeRange", 
                    defaultValue: ko.observable({ startTime: 0, endTime: 0 }), 
                    width: 243, 
                    template: `<div data-bind="ntsTimeRangeEditor: {
                                    startTimeNameId: '#[KMK003_166]',
                                    endTimeNameId: '#[KMK003_167]',
                                    startConstraint: 'TimeWithDayAttr',
                                    endConstraint: 'TimeWithDayAttr',
                                    required: true,
                                    enable: true,
                                    inputFormat: 'time',
                                    paramId: 'KMK003_86'}"/>`
                }, {
                    headerText: nts.uk.resource.getText("KMK003_56"), 
                    key: "roundingTime", 
                    dataSource: self.settingEnum.roundingTime,
                    defaultValue: ko.observable(0), 
                    width: 70,
                    cssClassName: 'tab2-column2-combo-box',
                    template: `<div class="column-combo-box unit-combo" data-key="value" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 8,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                }, {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "rounding",
                    isRoudingColumn: true,
                    unitAttrName: 'roundingTime',
                    dataSource: self.settingEnum.rounding,
                    defaultValue: ko.observable(0), 
                    width: 175,
                    template: `<div class="column-combo-box rouding-combo" data-key="value" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 8,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                }
            ];
        }
        
    }

    /**
     * TimeZoneRoundingModel
     */
    class TimeZoneModel {
        timeRange: KnockoutObservable<TimePeriod>;
        roundingTime: KnockoutObservable<number>;
        rounding: KnockoutObservable<number>;
        
        lstEmTimezone: EmTimeZoneSetModel[];
        
        constructor(timeRange: TimePeriod, roundingTime: number, rounding: number) {
            let self = this;
            self.timeRange = ko.observable(timeRange);
            self.roundingTime = ko.observable(roundingTime);
            self.rounding = ko.observable(rounding);
        }
    }
    
    /**
     * TimePeriod
     */
    interface TimePeriod {
        startTime: number;
        endTime: number;
    }
    
    /**
     * KMK003A2BindingHandler
     */
    class KMK003A2BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a2/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            
            let screenModel = new ScreenModel(input);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                // Binding tab
                screenModel.bindDataToScreen();
            });
            
        }

    }
    ko.bindingHandlers['ntsKMK003A2'] = new KMK003A2BindingHandler();
}
