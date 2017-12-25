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
            self.tabMode = input.screenMode;
            self.isSimpleMode = ko.computed(() => {
                return self.tabMode() == TabMode.SIMPLE;
            })
            self.isFlowMode = self.parentModel.workTimeSetting.isFlow;
            self.isUseHalfDay = self.parentModel.fixedWorkSetting.useHalfDayShift; 
            
            
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
                tabindex: -1
            };
            
            self.dataSourceMorning = ko.observableArray([]);
            self.fixTableOptionMorning = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceMorning,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: -1
            };
            
            self.dataSourceAfternoon = ko.observableArray([]);
            self.fixTableOptionAfternoon = {
                maxRow: 7,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceAfternoon,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: -1
            };
            
            // ====================================== Defined Variable Flow Mode ======================================
            
            self.roundingProcsses = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KMK003_91") },
                { code: 1, name: nts.uk.resource.getText("KMK003_92") }
            ]);
            self.calculationMethods = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KMK003_136") },
                { code: 1, name: nts.uk.resource.getText("KMK003_137") }
            ]);
            
            self.selectedRounding = ko.observable(0);
            self.selectedCalcStartTimeSet = ko.observable(0);
            self.selectedCodeSetting = ko.observable(0);
            
            // ====================================== SUBSCRIBER ======================================
            
            self.parentModel.workTimeSetting. workTimeDivision.workTimeMethodSet.subscribe(newValue => {
                self.bindDataToScreen();
            });
            self.isSimpleMode.subscribe(newValue => {
                self.bindDataToScreen();
            });
            self.isFlowMode.subscribe(newValue => {
                self.bindDataToScreen();
            });
            self.isUseHalfDay.subscribe(newValue => {
                self.bindDataToScreen();
            });
            
            
            self.dataSourceOneDay.subscribe((newValue) => {
                self.convertData();
            });
            self.dataSourceMorning.subscribe((newValue) => {
                self.convertData();
            });
            self.dataSourceAfternoon.subscribe((newValue) => {
                self.convertData();
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

                let dataSourceAllDay: EmTimeZoneSetModel[] = [];
                self.dataSourceOneDay([]);
                
                //============= Fixed Mode =============
                if (self.parentModel.workTimeSetting.isFixed()) {
                    dataSourceAllDay = self.parentModel.fixedWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone;
                }
                else if (self.parentModel.workTimeSetting.isFlex()) {
                    // all day
                    dataSourceAllDay = self.parentModel.flexWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone;
                }
                //============= DiffTime Mode =============
                else if (self.parentModel.workTimeSetting.isDiffTime()) {
                    // all day
                    dataSourceAllDay = self.parentModel.diffWorkSetting.getHDWtzOneday()
                        .workTimezone.employmentTimezones;
                }

                //============= Convert =============
                let lstTimezoneModel: TimezoneModel[] = self.parentModel.predetemineTimeSetting.prescribedTimezoneSetting.lstTimezone;
                _.forEach(lstTimezoneModel, (item: TimezoneModel) => {
                    let timeRange: TimePeriod = {
                        startTime: item.start(),
                        endTime: item.end()
                    }
                    let emTime: EmTimeZoneSetModel = _.find(dataSourceAllDay, (emTime: EmTimeZoneSetModel) => emTime.employmentTimeFrameNo === item.workNo);

                    self.dataSourceOneDay().push(new TimeZoneModel(timeRange, emTime.timezone.rounding.roundingTime,
                        emTime.timezone.rounding.rounding));
                });
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
            
            //============= Fixed Mode =============
            if (self.parentModel.workTimeSetting.isFixed()) {
                // all day
                let dataSourceAllDay: EmTimeZoneSetModel[] = self.parentModel.fixedWorkSetting.getHDWtzOneday()
                    .workTimezone.lstWorkingTimezone;
                self.bindingDataDomain(dataSourceAllDay, self.dataSourceOneDay);
                
                // morning
                let dataSourceMorning: EmTimeZoneSetModel[] = self.parentModel.fixedWorkSetting.getHDWtzMorning()
                    .workTimezone.lstWorkingTimezone;
                self.bindingDataDomain(dataSourceMorning, self.dataSourceMorning);
                
                // afternoon
                let dataSourceAfternoon: EmTimeZoneSetModel[] = self.parentModel.fixedWorkSetting.getHDWtzAfternoon()
                    .workTimezone.lstWorkingTimezone;
                self.bindingDataDomain(dataSourceAfternoon, self.dataSourceAfternoon);
            }
            
            //============= Flex Mode =============
            else if (self.parentModel.workTimeSetting.isFlex()) {
                // all day
                let dataSourceAllDay: EmTimeZoneSetModel[] = self.parentModel.flexWorkSetting.getHDWtzOneday()
                    .workTimezone.lstWorkingTimezone;
                self.bindingDataDomain(dataSourceAllDay, self.dataSourceOneDay);
                
                // morning
                let dataSourceMorning: EmTimeZoneSetModel[] = self.parentModel.flexWorkSetting.getHDWtzMorning()
                    .workTimezone.lstWorkingTimezone;
                self.bindingDataDomain(dataSourceMorning, self.dataSourceMorning);
                
                // afternoon
                let dataSourceAfternoon: EmTimeZoneSetModel[] = self.parentModel.flexWorkSetting.getHDWtzAfternoon()
                    .workTimezone.lstWorkingTimezone;
                self.bindingDataDomain(dataSourceAfternoon, self.dataSourceAfternoon);
            }
            
            //============= DiffTime Mode =============
            else if (self.parentModel.workTimeSetting.isDiffTime()) {
                // all day
                let dataSourceAllDay: EmTimeZoneSetModel[] = self.parentModel.diffWorkSetting.getHDWtzOneday()
                    .workTimezone.employmentTimezones;
                self.bindingDataDomain(dataSourceAllDay, self.dataSourceOneDay);
                
                // morning
                let dataSourceMorning: EmTimeZoneSetModel[] = self.parentModel.diffWorkSetting.getHDWtzMorning()
                    .workTimezone.employmentTimezones;
                self.bindingDataDomain(dataSourceMorning, self.dataSourceMorning);
                
                // afternoon
                let dataSourceAfternoon: EmTimeZoneSetModel[] = self.parentModel.diffWorkSetting.getHDWtzAfternoon()
                    .workTimezone.employmentTimezones;
                self.bindingDataDomain(dataSourceAfternoon, self.dataSourceAfternoon);
            } 
        }
        
        /**
         * Binding data domain
         */
        private bindingDataDomain(dataSourceModel: EmTimeZoneSetModel[],
            dataSource: KnockoutObservableArray<TimeZoneModel>) {
            let self = this;
            
            // empty list
            dataSource([]);
            
            // fill data
            _.forEach(dataSourceModel, (item: EmTimeZoneSetModel) => {
                let timeRange: TimePeriod = {
                    startTime: item.timezone.start(),
                    endTime: item.timezone.end()
                }
                dataSource().push(new TimeZoneModel(timeRange, item.timezone.rounding.roundingTime,
                    item.timezone.rounding.rounding));
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
                    self.toDomain(self.dataSourceOneDay, self.parentModel.fixedWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone);
                }
                
                //============= Flex Mode =============
                else if (self.parentModel.workTimeSetting.isFixed()) {
                    // all day
                    self.toDomain(self.dataSourceOneDay, self.parentModel.flexWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone);
                }
                
                //============= DiffTime Mode =============
                else if (self.parentModel.workTimeSetting.isFixed()) {
                    // all day
                    self.toDomain(self.dataSourceOneDay, self.parentModel.diffWorkSetting.getHDWtzOneday()
                        .workTimezone.employmentTimezones);
                }
                
            }
            // Detail mode
            else {
                //============= Fixed Mode =============
                if (self.parentModel.workTimeSetting.isFixed()) {
                    // all day
                    self.toDomain(self.dataSourceOneDay, self.parentModel.fixedWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone);
                    
                    // morning
                    self.toDomain(self.dataSourceMorning, self.parentModel.fixedWorkSetting.getHDWtzMorning()
                        .workTimezone.lstWorkingTimezone);
                    
                    // afternoon
                    self.toDomain(self.dataSourceAfternoon, self.parentModel.fixedWorkSetting.getHDWtzAfternoon()
                        .workTimezone.lstWorkingTimezone);
                }
                
                //============= Flex Mode =============
                else if (self.parentModel.workTimeSetting.isFixed()) {
                    // all day
                    self.toDomain(self.dataSourceOneDay, self.parentModel.flexWorkSetting.getHDWtzOneday()
                        .workTimezone.lstWorkingTimezone);
                    
                    // morning
                    self.toDomain(self.dataSourceMorning, self.parentModel.flexWorkSetting.getHDWtzMorning()
                        .workTimezone.lstWorkingTimezone);
                    
                    // afternoon
                    self.toDomain(self.dataSourceAfternoon, self.parentModel.flexWorkSetting.getHDWtzAfternoon()
                        .workTimezone.lstWorkingTimezone);
                }
                
                //============= DiffTime Mode =============
                else if (self.parentModel.workTimeSetting.isFixed()) {
                    // all day
                    self.toDomain(self.dataSourceOneDay, self.parentModel.diffWorkSetting.getHDWtzOneday()
                        .workTimezone.employmentTimezones);
                    
                    // morning
                    self.toDomain(self.dataSourceMorning, self.parentModel.diffWorkSetting.getHDWtzMorning()
                        .workTimezone.employmentTimezones);
                    
                    // afternoon
                    self.toDomain(self.dataSourceAfternoon, self.parentModel.diffWorkSetting.getHDWtzAfternoon()
                        .workTimezone.employmentTimezones);
                }
            }

        }
        
        /**
         * Convert model to DTO of Domain
         */
        private toDomain(dataSource: KnockoutObservableArray<TimeZoneModel>, dataSourceModel: EmTimeZoneSetModel[]): EmTimeZoneSetModel[] {
            let self = this;
            
            // empty list
            dataSourceModel = [];
            
            // fill data
            _.forEach(dataSource(), (item: TimeZoneModel, index: number) => {
                let emTime: EmTimeZoneSetModel = new EmTimeZoneSetModel();
                
                emTime.employmentTimeFrameNo(index++);
                emTime.timezone.start(item.timeRange.startTime);
                emTime.timezone.end(item.timeRange.endTime);
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
                        required: true, enable: true, inputFormat: 'time'}"/>`
                }, {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "roundingTime", 
                    dataSource: self.settingEnum.roundingTime,
                    defaultValue: ko.observable(0), 
                    width: 150,
                   template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 5,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                }, {
                    headerText: nts.uk.resource.getText("KMK003_57"), 
                    key: "rounding", 
                    dataSource: self.settingEnum.rounding,
                    defaultValue: ko.observable(0), 
                    width: 150,
                    template: `<div class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 5,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    enable: true,
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
        timeRange: TimePeriod;
        roundingTime: KnockoutObservable<number>;
        rounding: KnockoutObservable<number>;
        
        lstEmTimezone: EmTimeZoneSetModel[];
        
        constructor(timeRange: TimePeriod, roundingTime: KnockoutObservable<number>, rounding: KnockoutObservable<number>) {
            let self = this;
            self.timeRange = timeRange;
            self.roundingTime = roundingTime;
            self.rounding = rounding;
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
                
                console.log("abc");
            });
            
        }

    }
    ko.bindingHandlers['ntsKMK003A2'] = new KMK003A2BindingHandler();
}
