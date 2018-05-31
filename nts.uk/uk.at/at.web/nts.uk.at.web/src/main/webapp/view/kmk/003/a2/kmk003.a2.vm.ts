module a2 {
    
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import EmTimeZoneSetDto = nts.uk.at.view.kmk003.a.service.model.common.EmTimeZoneSetDto;
    import TimeZoneRoundingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeZoneRoundingDto;
    import TimeRoundingSettingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeRoundingSettingDto;
    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    import TimeZoneRoundingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeZoneRoundingModel;
    
    import FixHalfDayWorkTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.fixedset.FixHalfDayWorkTimezoneModel;
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import EmTimeZoneSetFixedTableModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetFixedTableModel;
    import TimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.predset.TimezoneModel;
    
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    /**
     * ScreenModel
     */
    class ScreenModel {

        // Defined parameter binding
        parentModel: MainSettingModel;
        settingEnum: WorkTimeSettingEnumDto;

        dataSourceOneDayFixed: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionOneDayFixed: any;
        dataSourceMorningFixed: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionMorningFixed: any;
        dataSourceAfternoonFixed: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionAfternoonFixed: any;

        dataSourceOneDayFlex: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionOneDayFlex: any;
        dataSourceMorningFlex: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionMorningFlex: any;
        dataSourceAfternoonFlex: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionAfternoonFlex: any;

        dataSourceOneDayDifftime: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionOneDayDifftime: any;
        dataSourceMorningDifftime: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionMorningDifftime: any;
        dataSourceAfternoonDifftime: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionAfternoonDifftime: any;
        
        dataSourceOneDaySimpleModeFixed: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionOneDaySimpleModeFixed: any;
        dataSourceOneDaySimpleModeFlex: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionOneDaySimpleModeFlex: any;
        dataSourceOneDaySimpleModeDifftime: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
        fixTableOptionOneDaySimpleModeDifftime: any;
        
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
        isSimpleMode: KnockoutComputed<boolean>;
        isFlowMode: KnockoutComputed<boolean>;
        isFlexMode: KnockoutComputed<boolean>;
        isFixedMode: KnockoutComputed<boolean>;
        isDiffTimeMode: KnockoutComputed<boolean>;
        isUseHalfDay: KnockoutObservable<boolean>;

        /**
        * Constructor.
        */
        constructor(input: any) {
            let self = this;
            
            // ====================================== Set Parameter Binding ======================================
            self.parentModel = input.mainModel;
            self.settingEnum = input.enum;
            
            self.isSimpleMode = input.isSimpleMode;
            self.isFlowMode = self.parentModel.workTimeSetting.isFlow;
            self.isFlexMode = self.parentModel.workTimeSetting.isFlex;
            self.isFixedMode = self.parentModel.workTimeSetting.isFixed;
            self.isDiffTimeMode = self.parentModel.workTimeSetting.isDiffTime;
            self.isUseHalfDay = input.useHalfDay; 

            // ====================================== Defined Variable Flow Mode ======================================
            
            self.roundingProcsses = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_91") },
                { code: 0, name: nts.uk.resource.getText("KMK003_92") }
            ]);
            self.calculationMethods = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("KMK003_136") },
                { code: 1, name: nts.uk.resource.getText("KMK003_137") }
            ]);

            self.bindDataFlowMode();
            self.setFixedTableDatasource();
            self.setFixedTableOptions();

            // force to update value in tab 2
            document.querySelector('#ui-id-2').addEventListener('click', () =>
                self.parentModel.predetemineTimeSetting.prescribedTimezoneSetting.shiftOne.valueChangedNotifier.valueHasMutated());
        }

        private setFixedTableDatasource(): void {
            let self = this;
            const fixed = self.parentModel.fixedWorkSetting;
            const difftime = self.parentModel.diffWorkSetting;
            const flex = self.parentModel.flexWorkSetting;

            self.dataSourceOneDayFixed = fixed.getHDWtzOneday().workTimezone.convertedList2;
            self.dataSourceMorningFixed = fixed.getHDWtzMorning().workTimezone.convertedList2;
            self.dataSourceAfternoonFixed = fixed.getHDWtzAfternoon().workTimezone.convertedList2;

            self.dataSourceOneDayDifftime = difftime.getHDWtzOneday().workTimezone.convertedList2;
            self.dataSourceMorningDifftime = difftime.getHDWtzMorning().workTimezone.convertedList2;
            self.dataSourceAfternoonDifftime = difftime.getHDWtzAfternoon().workTimezone.convertedList2;

            self.dataSourceOneDayFlex = flex.getHDWtzOneday().workTimezone.convertedList2;
            self.dataSourceMorningFlex = flex.getHDWtzMorning().workTimezone.convertedList2;
            self.dataSourceAfternoonFlex = flex.getHDWtzAfternoon().workTimezone.convertedList2;

            self.dataSourceOneDaySimpleModeFixed = fixed.getHDWtzOneday().workTimezone.lstWorkingTimezoneSimpleMode;
            self.dataSourceOneDaySimpleModeFlex = flex.getHDWtzOneday().workTimezone.convertedList2;
            self.dataSourceOneDaySimpleModeDifftime = difftime.getHDWtzOneday().workTimezone.lstWorkingTimezoneSimpleMode;

        }

        private setFixedTableOptions(): void {
            let self = this;
            const TABINDEX_ONEDAY = 46;
            const TABINDEX_MORNING = 47;
            const TABINDEX_AFTERNOON = 48;

            // simple mode
            self.fixTableOptionOneDaySimpleModeFixed = self.getSimpleFixedTableOption();
            self.fixTableOptionOneDaySimpleModeFixed.dataSource = self.dataSourceOneDaySimpleModeFixed;
            self.fixTableOptionOneDaySimpleModeFlex = self.getDefaultFixedTableOption();
            self.fixTableOptionOneDaySimpleModeFlex.dataSource = self.dataSourceOneDaySimpleModeFlex;
            self.fixTableOptionOneDaySimpleModeDifftime = self.getSimpleFixedTableOption();
            self.fixTableOptionOneDaySimpleModeDifftime.dataSource = self.dataSourceOneDaySimpleModeDifftime;

            // fixed
            self.fixTableOptionOneDayFixed = self.getDefaultFixedTableOption();
            self.fixTableOptionOneDayFixed.dataSource = self.dataSourceOneDayFixed;
            self.fixTableOptionOneDayFixed.tabindex = TABINDEX_ONEDAY;
            self.fixTableOptionMorningFixed = self.getDefaultFixedTableOption();
            self.fixTableOptionMorningFixed.dataSource = self.dataSourceMorningFixed;
            self.fixTableOptionMorningFixed.tabindex = TABINDEX_MORNING;
            self.fixTableOptionAfternoonFixed = self.getDefaultFixedTableOption();
            self.fixTableOptionAfternoonFixed.dataSource = self.dataSourceAfternoonFixed;
            self.fixTableOptionAfternoonFixed.tabindex = TABINDEX_AFTERNOON;

            // diff time
            self.fixTableOptionOneDayDifftime = self.getDefaultFixedTableOption();
            self.fixTableOptionOneDayDifftime.dataSource = self.dataSourceOneDayDifftime;
            self.fixTableOptionOneDayDifftime.tabindex = TABINDEX_ONEDAY;
            self.fixTableOptionMorningDifftime = self.getDefaultFixedTableOption();
            self.fixTableOptionMorningDifftime.dataSource = self.dataSourceMorningDifftime;
            self.fixTableOptionMorningDifftime.tabindex = TABINDEX_MORNING;
            self.fixTableOptionAfternoonDifftime = self.getDefaultFixedTableOption();
            self.fixTableOptionAfternoonDifftime.dataSource = self.dataSourceAfternoonDifftime;
            self.fixTableOptionAfternoonDifftime.tabindex = TABINDEX_AFTERNOON;

            // flex
            self.fixTableOptionOneDayFlex = self.getDefaultFixedTableOption();
            self.fixTableOptionOneDayFlex.dataSource = self.dataSourceOneDayFlex;
            self.fixTableOptionOneDayFlex.tabindex = TABINDEX_ONEDAY;
            self.fixTableOptionMorningFlex = self.getDefaultFixedTableOption();
            self.fixTableOptionMorningFlex.dataSource = self.dataSourceMorningFlex;
            self.fixTableOptionMorningFlex.tabindex = TABINDEX_MORNING;
            self.fixTableOptionAfternoonFlex = self.getDefaultFixedTableOption();
            self.fixTableOptionAfternoonFlex.dataSource = self.dataSourceAfternoonFlex;
            self.fixTableOptionAfternoonFlex.tabindex = TABINDEX_AFTERNOON;
        }

        private getDefaultFixedTableOption(): any {
            let self = this;
            return {
                maxRow: 5,
                minRow: 1,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: null,
                isMultipleSelect: true,
                columns: self.columnSetting(),
                tabindex: null
            };
        }

        private getSimpleFixedTableOption(): any {
            let self = this;
            return {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 1,
                isShowButton: false,
                dataSource: null,
                isMultipleSelect: false,
                columns: self.columnSetting(),
                tabindex: 1
            };
        }

        /**
         * Binding Data Flow Mode
         */
        private bindDataFlowMode() {
            let self = this;
            const flowWorkSetting = self.parentModel.flowWorkSetting;

            self.selectedRoundingTime = flowWorkSetting.halfDayWorkTimezone.workTimeZone
                .workTimeRounding.roundingTime;
            self.selectedRounding = flowWorkSetting.halfDayWorkTimezone.workTimeZone
                .workTimeRounding.rounding;

            self.selectedCalcStartTimeSet = flowWorkSetting.flowSetting.calculateSetting.calcStartTimeSet;

            self.selectedCodeSetting = flowWorkSetting.flowSetting.overtimeSetting.fixedChangeAtr;
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

        init(element: any, valueAccessor: () => any): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a2/index.xhtml').serialize();

            let screenModel = new ScreenModel(valueAccessor());

            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
            
        }

    }
    ko.bindingHandlers['ntsKMK003A2'] = new KMK003A2BindingHandler();
}
