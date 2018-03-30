module a3 {
    import TimeRoundingSettingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeRoundingSettingDto;
    import TimeZoneRoundingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeZoneRoundingDto;
    import OverTimeOfTimeZoneSetDto = nts.uk.at.view.kmk003.a.service.model.common.OverTimeOfTimeZoneSetDto;
    import DiffTimeOTTimezoneSetDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeOTTimezoneSetDto;
    import FlOTTimezoneDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlOTTimezoneDto;
    import FlTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlTimeSettingDto;
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    import FlowOTTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.flowset.FlowOTTimezoneModel;
    import OverTimeOfTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OverTimeOfTimeZoneSetModel;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    import OvertimeWorkFrameFindDto = nts.uk.at.view.kmk003.a3.service.model.OvertimeWorkFrameFindDto;
    import SettingMethod = nts.uk.at.view.kmk003.a.viewmodel.SettingMethod;
    class ScreenModel {

        fixTableOptionOnedayFixed: any;
        fixTableOptionMorningFixed: any;
        fixTableOptionAfternoonFixed: any;
        fixTableOptionOnedayDiffTime: any;
        fixTableOptionMorningDiffTime: any;
        fixTableOptionAfternoonDiffTime: any;
        fixTableOptionOnedayFlex: any;
        fixTableOptionMorningFlex: any;
        fixTableOptionAfternoonFlex: any;
        fixTableOptionOvertimeFlow: any;
        isFlowMode: KnockoutObservable<boolean>;
        isFlexMode: KnockoutObservable<boolean>;
        isFixedMode: KnockoutObservable<boolean>;
        isDiffTimeMode: KnockoutObservable<boolean>;
        isLoading: KnockoutObservable<boolean>;
        isDetailMode: KnockoutObservable<boolean>;
        isUseHalfDay: KnockoutObservable<boolean>;
        dataSourceOnedayFixed: KnockoutObservableArray<any>;
        dataSourceMorningFixed: KnockoutObservableArray<any>;
        dataSourceAfternoonFixed: KnockoutObservableArray<any>;
        dataSourceOnedayDiffTime: KnockoutObservableArray<any>;
        dataSourceMorningDiffTime: KnockoutObservableArray<any>;
        dataSourceAfternoonDiffTime: KnockoutObservableArray<any>;
        dataSourceOnedayFlex: KnockoutObservableArray<any>;
        dataSourceMorningFlex: KnockoutObservableArray<any>;
        dataSourceAfternoonFlex: KnockoutObservableArray<any>;
        dataSourceOvertimeFlow: KnockoutObservableArray<any>;
        autoCalUseAttrs: KnockoutObservableArray<any>;
        selectedCodeAutoCalUse: KnockoutObservable<any>;
        settingEnum: WorkTimeSettingEnumDto;
        mainSettingModel: MainSettingModel;
        lstOvertimeWorkFrame: OvertimeWorkFrameFindDto[];
        
        //define for 精算�primitive value
        lstSettlementOrder: any[];
        screenSettingMode: KnockoutObservable<number>;
        isNewMode: KnockoutObservable<boolean>;
        
        rebind: KnockoutObservable<boolean>;
        /**
        * Constructor.
        */
        constructor(settingEnum: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel, 
        isLoading: KnockoutObservable<boolean>, isDetailMode: KnockoutObservable<boolean>, isUseHalfDay: KnockoutObservable<boolean>,isNewMode: KnockoutObservable<boolean>) {
            let self = this;
            self.rebind = ko.observable(true);
            self.isNewMode = isNewMode;
            self.screenSettingMode = ko.observable(0);
            self.settingEnum = settingEnum;
            self.mainSettingModel = mainSettingModel;
            self.isLoading = isLoading;
            self.isDetailMode = isDetailMode;
            self.isUseHalfDay = isUseHalfDay;
            self.isFlexMode = self.mainSettingModel.workTimeSetting.isFlex;
            self.isFlowMode = self.mainSettingModel.workTimeSetting.isFlow;
            self.isFixedMode = self.mainSettingModel.workTimeSetting.isFixed;
            self.isDiffTimeMode = self.mainSettingModel.workTimeSetting.isDiffTime;
            
            self.autoCalUseAttrs = ko.observableArray([
                { code: 1, name: nts.uk.resource.getText("KMK003_142") },
                { code: 0, name: nts.uk.resource.getText("KMK003_143") }
            ]);
            self.lstOvertimeWorkFrame = [];
            
            self.lstSettlementOrder = [];
            //init list order
            for (let i = 1; i <= 10; i++) {
                self.lstSettlementOrder.push({
                    settlementOrder: i,
                    settlementOrderName: i.toString()
                });
            }
            
            self.dataSourceOvertimeFlow = ko.observableArray([]);
            self.dataSourceOnedayFixed = ko.observableArray([]);
            self.dataSourceMorningFixed = ko.observableArray([]);
            self.dataSourceAfternoonFixed = ko.observableArray([]);
            self.dataSourceOnedayDiffTime = ko.observableArray([]);
            self.dataSourceMorningDiffTime= ko.observableArray([]);
            self.dataSourceAfternoonDiffTime = ko.observableArray([]);
            self.dataSourceOnedayFlex = ko.observableArray([]);
            self.dataSourceMorningFlex = ko.observableArray([]);
            self.dataSourceAfternoonFlex = ko.observableArray([]);

            self.selectedCodeAutoCalUse = ko.observable('1');
            
            // update time zone flow
            self.dataSourceOvertimeFlow.subscribe(function(dataFlow: any[]) {
                var lstTimezone: FlOTTimezoneDto[] = [];
                var worktimeNo: number = 0;
                for (var dataModel of dataFlow) {
                    worktimeNo++;
                    lstTimezone.push(self.toModelFlowDto(dataModel, worktimeNo));
                }
                self.mainSettingModel.flowWorkSetting.halfDayWorkTimezone.workTimeZone.updateTimezone(lstTimezone);
            });

            // update time zone fixed 
            self.dataSourceOnedayFixed.subscribe(function(dataFixed: any[]) {
                var lstOTTimezone: OverTimeOfTimeZoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFixed) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelFixedDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.fixedWorkSetting.getHDWtzOneday().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            self.dataSourceMorningFixed.subscribe(function(dataFixed: any[]) {
                var lstOTTimezone: OverTimeOfTimeZoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFixed) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelFixedDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.fixedWorkSetting.getHDWtzMorning().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            self.dataSourceAfternoonFixed.subscribe(function(dataFixed: any[]) {
                var lstOTTimezone: OverTimeOfTimeZoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFixed) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelFixedDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.fixedWorkSetting.getHDWtzAfternoon().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            // update time zone diff time 
            self.dataSourceOnedayDiffTime.subscribe(function(dataDiffTime: any[]) {
                var lstOTTimezone: DiffTimeOTTimezoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataDiffTime) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelDiffTimeDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.diffWorkSetting.getHDWtzOneday().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            self.dataSourceMorningDiffTime.subscribe(function(dataDiffTime: any[]) {
                var lstOTTimezone: DiffTimeOTTimezoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataDiffTime) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelDiffTimeDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.diffWorkSetting.getHDWtzMorning().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            self.dataSourceAfternoonDiffTime.subscribe(function(dataDiffTime: any[]) {
                var lstOTTimezone: DiffTimeOTTimezoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataDiffTime) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelDiffTimeDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.diffWorkSetting.getHDWtzAfternoon().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            // update time zone flexset
            self.dataSourceOnedayFlex.subscribe(function(dataFlex: any[]) {
                var lstOTTimezone: OverTimeOfTimeZoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFlex) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelFlexDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.flexWorkSetting.getHDWtzOneday().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            self.dataSourceMorningFlex.subscribe(function(dataFlex: any[]) {
                var lstOTTimezone: OverTimeOfTimeZoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFlex) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelFlexDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.flexWorkSetting.getHDWtzMorning().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            self.dataSourceAfternoonFlex.subscribe(function(dataFlex: any[]) {
                var lstOTTimezone: OverTimeOfTimeZoneSetDto[] = [];
                var workTimezoneNo: number = 0;
                for (var dataModel of dataFlex) {
                    workTimezoneNo++;
                    lstOTTimezone.push(self.toModelFlexDto(dataModel, workTimezoneNo));
                }
                self.mainSettingModel.flexWorkSetting.getHDWtzAfternoon().workTimezone.updateOvertimeZone(lstOTTimezone);
            });
            
            self.isDetailMode.subscribe((v) => {
                //if is new mode
                if (self.isNewMode()) {
                    switch (self.screenSettingMode()) {
                        case SettingMethod.FIXED:
                            if (self.fixTableOptionOnedayFixed) {
                                if (v) {
                                    self.fixTableOptionOnedayFixed.columns = self.columnSettingFixedAndDiffTime();
                                    self.updateDataModel();
                                }
                                else {
                                    self.fixTableOptionOnedayFixed.columns = self.columnSettingFlex();
                                }
                            }
                            break;
                        case SettingMethod.FLOW:
                            if (self.fixTableOptionOvertimeFlow) {
                                if (v) {
                                    self.fixTableOptionOvertimeFlow.columns = self.columnSettingOvertimeFlow();
                                    self.updateDataModel();
                                }
                                else {
                                    self.fixTableOptionOvertimeFlow.columns = self.columnSettingFlowSimple();
                                }
                            }
                            break;
                        case SettingMethod.DIFFTIME:
                            if (self.fixTableOptionOnedayDiffTime) {
                                if (v) {
                                    self.fixTableOptionOnedayDiffTime.columns = self.columnSettingFixedAndDiffTime();
                                    self.updateDataModel();
                                }
                                else {
                                    self.fixTableOptionOnedayDiffTime.columns = self.columnSettingFlex();
                                }
                            }
                            break;
                        default: break;
                    }
                }
                else {
                    if (self.isFixedMode()) {
                        if (v) {
                            self.fixTableOptionOnedayFixed.columns = self.columnSettingFixedAndDiffTime();
                            self.updateDataModel();
                        }
                        else {
                            self.fixTableOptionOnedayFixed.columns = self.columnSettingFlex();
                        }
                    }

                    if (self.isFlowMode()) {
                        if (v) {
                            self.fixTableOptionOvertimeFlow.columns = self.columnSettingOvertimeFlow();
                            self.updateDataModel();
                        }
                        else {
                            self.fixTableOptionOvertimeFlow.columns = self.columnSettingFlowSimple();
                        }
                    }
                    if (self.isDiffTimeMode()) {
                        if (v) {
                            self.fixTableOptionOnedayDiffTime.columns = self.columnSettingFixedAndDiffTime();
                            self.updateDataModel();
                        }
                        else {
                            self.fixTableOptionOnedayDiffTime.columns = self.columnSettingFlex();
                        }
                    }
                }
                setTimeout(() => {
                    self.rebind(false);
                    self.rebind(true);
                }, 500);
            });
            
            self.isNewMode.subscribe((v) => {
                if (v) {
                    self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeMethodSet.valueHasMutated();
                }
            });
            self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeMethodSet.subscribe((v) => {
                if (self.isNewMode()) {
                    self.screenSettingMode(v);
                    self.isDetailMode.notifySubscribers(self.isDetailMode());
                    if (v == SettingMethod.FLOW) {
                        self.dataSourceOvertimeFlow.valueHasMutated();
                    }
                }
            });
            
            self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeDailyAtr.subscribe((v) => {
                if (self.isNewMode()) {
                    self.screenSettingMode(v);
                    if(v == SettingMethod.FLOW) {
                        self.dataSourceOvertimeFlow.valueHasMutated();
                    }
                    self.isDetailMode.notifySubscribers(self.isDetailMode());
                }
            });
            
            self.isLoading.subscribe(function(isLoading: boolean) {
                if (isLoading) {
                    self.updateDataModel();
//                    self.isDetailMode.notifySubscribers(self.isDetailMode());
                }
            });
        }
        
        public initDataModel(): void {
            var self = this;
            self.fixTableOptionOnedayFixed = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceOnedayFixed,
                isMultipleSelect: true,
                columns: self.columnSettingFixedAndDiffTime(),
                tabindex: 56
            };
            self.fixTableOptionMorningFixed = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceMorningFixed,
                isMultipleSelect: true,
                columns: self.columnSettingFixedAndDiffTime(),
                tabindex: 57
            };
            self.fixTableOptionAfternoonFixed = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceAfternoonFixed,
                isMultipleSelect: true,
                columns: self.columnSettingFixedAndDiffTime(),
                tabindex: 58
            };
            self.fixTableOptionOnedayDiffTime = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceOnedayDiffTime,
                isMultipleSelect: true,
                columns: self.columnSettingFixedAndDiffTime(),
                tabindex: 56
            };
            self.fixTableOptionMorningDiffTime = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceMorningDiffTime,
                isMultipleSelect: true,
                columns: self.columnSettingFixedAndDiffTime(),
                tabindex: 57
            };
            self.fixTableOptionAfternoonDiffTime = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceAfternoonDiffTime,
                isMultipleSelect: true,
                columns: self.columnSettingFixedAndDiffTime(),
                tabindex: 58
            };
            self.fixTableOptionOnedayFlex = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceOnedayFlex,
                isMultipleSelect: true,
                columns: self.columnSettingFlex(),
                tabindex: 56
            };
            self.fixTableOptionMorningFlex = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceMorningFlex,
                isMultipleSelect: true,
                columns: self.columnSettingFlex(),
                tabindex: 57
            };
            self.fixTableOptionAfternoonFlex = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: self.dataSourceAfternoonFlex,
                isMultipleSelect: true,
                columns: self.columnSettingFlex(),
                tabindex: 58
            };
            self.fixTableOptionOvertimeFlow = {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: self.dataSourceOvertimeFlow,
                isMultipleSelect: true,
                columns: self.columnSettingOvertimeFlow(),
                tabindex: 59
            };

        }
        private updateDataModel(): void {
            var self = this;
            if (self.isFlowMode()) {
                var dataFlow: any[] = [];
                for (var dataModelFlow of self.mainSettingModel.flowWorkSetting.halfDayWorkTimezone.workTimeZone.lstOTTimezone()) {
                    dataFlow.push(self.toModelFlowColumnSetting(dataModelFlow.toDto()));
                }
                self.dataSourceOvertimeFlow(dataFlow);
            }

            if (self.isFixedMode()) {
                var dataFixedOneday: any[] = [];
                for (var dataModelFixed of self.mainSettingModel.fixedWorkSetting.getHDWtzOneday().workTimezone.lstOTTimezone()) {
                    dataFixedOneday.push(self.toModelFixedColumnSetting(dataModelFixed.toDto()));
                }
                self.dataSourceOnedayFixed(dataFixedOneday);
                var dataFixedMorning: any[] = [];
                for (var dataModelMorningFixed of self.mainSettingModel.fixedWorkSetting.getHDWtzMorning().workTimezone.lstOTTimezone()) {
                    dataFixedMorning.push(self.toModelFixedColumnSetting(dataModelMorningFixed.toDto()));
                }
                self.dataSourceMorningFixed(dataFixedMorning);
                var dataFixedAfternoon: any[] = [];
                for (var dataModelAfternoonFixed of self.mainSettingModel.fixedWorkSetting.getHDWtzAfternoon().workTimezone.lstOTTimezone()) {
                    dataFixedAfternoon.push(self.toModelFixedColumnSetting(dataModelAfternoonFixed.toDto()));
                }
                self.dataSourceAfternoonFixed(dataFixedAfternoon);
            }
            if (self.isDiffTimeMode()) {
                var dataDiffTimeOneday: any[] = [];
                for (var dataModelOnedayDiffTime of self.mainSettingModel.diffWorkSetting.getHDWtzOneday().workTimezone.lstOtTimezone()) {
                    dataDiffTimeOneday.push(self.toModelDiffTimeColumnSetting(dataModelOnedayDiffTime.toDto()));
                }
                self.dataSourceOnedayDiffTime(dataDiffTimeOneday);
                var dataDiffTimeMorning: any[] = [];
                for (var dataModelMorningDiffTime of self.mainSettingModel.diffWorkSetting.getHDWtzMorning().workTimezone.lstOtTimezone()) {
                    dataDiffTimeMorning.push(self.toModelDiffTimeColumnSetting(dataModelMorningDiffTime.toDto()));
                }
                self.dataSourceMorningDiffTime(dataDiffTimeMorning);
                var dataDiffTimeAfternoon: any[] = [];
                for (var dataModelAfternoonDiffTime of self.mainSettingModel.diffWorkSetting.getHDWtzAfternoon().workTimezone.lstOtTimezone()) {
                    dataDiffTimeAfternoon.push(self.toModelDiffTimeColumnSetting(dataModelAfternoonDiffTime.toDto()));
                }
                self.dataSourceAfternoonDiffTime(dataDiffTimeAfternoon);
            }

            if (self.isFlexMode()) {
                var dataFlexOneday: any[] = [];
                for (var dataModelOnedayFlex of self.mainSettingModel.flexWorkSetting.getHDWtzOneday().workTimezone.lstOTTimezone()) {
                    dataFlexOneday.push(self.toModelFlexColumnSetting(dataModelOnedayFlex.toDto()));
                }
                self.dataSourceOnedayFlex(dataFlexOneday);
                var dataFlexMorning: any[] = [];
                if (self.mainSettingModel.flexWorkSetting.getHDWtzMorning().workTimezone.lstOTTimezone) {
                    for (var dataModelMorningFlex of self.mainSettingModel.flexWorkSetting.getHDWtzMorning().workTimezone.lstOTTimezone()) {
                        dataFlexMorning.push(self.toModelFlexColumnSetting(dataModelMorningFlex.toDto()));
                    }
                }
                self.dataSourceMorningFlex(dataFlexMorning);
                var dataFlexAfternoon: any[] = [];
                for (var dataModelAfternoonFlex of self.mainSettingModel.flexWorkSetting.getHDWtzAfternoon().workTimezone.lstOTTimezone()) {
                    dataFlexAfternoon.push(self.toModelFlexColumnSetting(dataModelAfternoonFlex.toDto()));
                }
                self.dataSourceAfternoonFlex(dataFlexAfternoon);
            }

        }
        
        /**
         * function convert dto to model
         */
        private toModelFixedColumnSetting(dataDTO: OverTimeOfTimeZoneSetDto): any {
            return {
                timezone: ko.observable({ startTime: dataDTO.timezone.start, endTime: dataDTO.timezone.end }),
                rounding: ko.observable(dataDTO.timezone.rounding.rounding),
                roundingTime: ko.observable(dataDTO.timezone.rounding.roundingTime),
                otFrameNo: ko.observable(dataDTO.otFrameNo),
                earlyOTUse: ko.observable(dataDTO.earlyOTUse),
                legalOTframeNo: ko.observable(dataDTO.legalOTframeNo),
                settlementOrder: ko.observable(dataDTO.settlementOrder)
            }
        }
        /**
         * function convert dto to model
         */
        private toModelDiffTimeColumnSetting(dataDTO: DiffTimeOTTimezoneSetDto): any {
            return {
                timezone: ko.observable({ startTime: dataDTO.timezone.start, endTime: dataDTO.timezone.end }),
                rounding: ko.observable(dataDTO.timezone.rounding.rounding),
                roundingTime: ko.observable(dataDTO.timezone.rounding.roundingTime),
                otFrameNo: ko.observable(dataDTO.otFrameNo),
                earlyOTUse: ko.observable(dataDTO.earlyOTUse),
                legalOTframeNo: ko.observable(dataDTO.legalOTframeNo),
                settlementOrder: ko.observable(dataDTO.settlementOrder)
            }
        }
        
        /**
         * function convert data model of client to parent
         */
        private toModelDiffTimeDto(dataModel: any, workTimezoneNo: number): DiffTimeOTTimezoneSetDto {
            var rounding: TimeRoundingSettingDto = {
                roundingTime: dataModel.roundingTime(),
                rounding: dataModel.rounding()
            };
            var timezone: TimeZoneRoundingDto = {
                rounding: rounding,
                start: dataModel.timezone().startTime,
                end: dataModel.timezone().endTime
            };
            var dataDTO: DiffTimeOTTimezoneSetDto = {
                workTimezoneNo: workTimezoneNo,
                restraintTimeUse: false,
                timezone: timezone,
                otFrameNo: dataModel.otFrameNo(),
                earlyOTUse: dataModel.earlyOTUse(),
                legalOTframeNo: dataModel.legalOTframeNo?dataModel.legalOTframeNo():1,
                settlementOrder: dataModel.settlementOrder?dataModel.settlementOrder():1,
                isUpdateStartTime: false
            };
            return dataDTO;
        }
        
        /**
         * function convert data model of client to parent
         */
        private toModelFixedDto(dataModel: any, workTimezoneNo: number): OverTimeOfTimeZoneSetDto {
            var rounding: TimeRoundingSettingDto = {
                roundingTime: dataModel.roundingTime(),
                rounding: dataModel.rounding()
            };
            var timezone: TimeZoneRoundingDto = {
                rounding: rounding,
                start: dataModel.timezone().startTime,
                end: dataModel.timezone().endTime
            };
            var dataDTO: OverTimeOfTimeZoneSetDto = {
                workTimezoneNo: workTimezoneNo,
                restraintTimeUse: false,
                timezone: timezone,
                otFrameNo: dataModel.otFrameNo(),
                earlyOTUse: dataModel.earlyOTUse(),
                legalOTframeNo: dataModel.legalOTframeNo?dataModel.legalOTframeNo():1,
                settlementOrder: dataModel.settlementOrder?dataModel.settlementOrder():1
            };
            return dataDTO;
        }
        /**
         * function convert dto to model
         */
        private toModelFlexColumnSetting(dataDTO: OverTimeOfTimeZoneSetDto): any {
            return {
                timezone: ko.observable({ startTime: dataDTO.timezone.start, endTime: dataDTO.timezone.end }),
                rounding: ko.observable(dataDTO.timezone.rounding.rounding),
                roundingTime: ko.observable(dataDTO.timezone.rounding.roundingTime),
                otFrameNo: ko.observable(dataDTO.otFrameNo),
                earlyOTUse: ko.observable(dataDTO.earlyOTUse)
            }
        }
        
        /**
         * function convert data model of client to parent
         */
        private toModelFlexDto(dataModel: any, workTimezoneNo: number): OverTimeOfTimeZoneSetDto {
            var rounding: TimeRoundingSettingDto = {
                roundingTime: dataModel.roundingTime(),
                rounding: dataModel.rounding()
            };
            var timezone: TimeZoneRoundingDto = {
                rounding: rounding,
                start: dataModel.timezone().startTime,
                end: dataModel.timezone().endTime
            };
            var dataDTO: OverTimeOfTimeZoneSetDto = {
                workTimezoneNo: workTimezoneNo,
                restraintTimeUse: false,
                timezone: timezone,
                otFrameNo: dataModel.otFrameNo(),
                earlyOTUse: dataModel.earlyOTUse(),
                legalOTframeNo: 1,
                settlementOrder: 1
            };
            return dataDTO;
        }
        /**
         * function convert dto to model
         */
        private toModelFlowColumnSetting(dataDTO: FlOTTimezoneDto): any {
            return {
                elapsedTime: ko.observable(dataDTO.flowTimeSetting.elapsedTime),
                rounding: ko.observable(dataDTO.flowTimeSetting.rounding.rounding),
                roundingTime: ko.observable(dataDTO.flowTimeSetting.rounding.roundingTime),
                otFrameNo: ko.observable(dataDTO.otFrameNo),
                inLegalOTFrameNo: ko.observable(dataDTO.inLegalOTFrameNo),
                settlementOrder: ko.observable(dataDTO.settlementOrder)
            }
        }
        
        /**
         * function convert data model of client to parent
         */
        private toModelFlowDto(dataModel: any, worktimeNo: number): FlOTTimezoneDto {
            var rounding: TimeRoundingSettingDto = {
                roundingTime: dataModel.roundingTime(),
                rounding: dataModel.rounding()
            };
            var flowTimeSetting: FlTimeSettingDto = {
                rounding: rounding,
                elapsedTime: dataModel.elapsedTime(),
            };
            var dataDTO: FlOTTimezoneDto = {
                worktimeNo: worktimeNo,
                restrictTime: false,
                otFrameNo: dataModel.otFrameNo?dataModel.otFrameNo():1,
                flowTimeSetting: flowTimeSetting,
                inLegalOTFrameNo: dataModel.inLegalOTFrameNo?dataModel.inLegalOTFrameNo():1,
                settlementOrder: dataModel.settlementOrder?dataModel.settlementOrder():1
            };
            return dataDTO;
        }

        //bind data to screen items
        public bindDataToScreen(data: any) {
            let self = this;
        }
        
        /**
         * init array setting column option overtime flow mode
         */
        private columnSettingOvertimeFlow(): Array<any> {
            let self = this;
            let stringColumns: Array<any> = self.columnSettingFlowSimple();
            stringColumns.push(
                {
                    headerText: nts.uk.resource.getText("KMK003_186"),
                    key: "inLegalOTFrameNo",
                    dataSource: self.lstOvertimeWorkFrame,
                    defaultValue: ko.observable(1),
                    width: 120,
                    template: `<div data-key="overtimeWorkFrNo" class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'overtimeWorkFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'overtimeWorkFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'overtimeWorkFrName', length: 12 }]}">
                                </div>`
                });
            stringColumns.push({
                headerText: nts.uk.resource.getText("KMK003_187"),
                key: "settlementOrder",
                dataSource: self.lstSettlementOrder,
                defaultValue: ko.observable(1),
                width: 100,
                template: `<div data-key="settlementOrder" class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'settlementOrder',
                                    visibleItemsCount: 10,
                                    optionsText: 'settlementOrderName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'settlementOrderName', length: 12 }]}">
                                </div>`
            });
            return stringColumns;
        }
        
        private columnSettingFlowSimple(): Array<any> {
            let self = this;
            return [
                 {
                     headerText: nts.uk.resource.getText("KMK003_174"),
                     key: "elapsedTime",
                     defaultValue: ko.observable(0), 
                     width: 100, 
                     template: `<input class="time-edior-column" data-bind="ntsTimeEditor: {
                            constraint: 'AttendanceTime',
                            mode: 'time',
                            inputFormat: 'time',
                            required: true }" />`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_56"),
                     key: "roundingTime",
                     dataSource: self.settingEnum.roundingTime,
                     defaultValue: ko.observable(0),
                     width: 80,
                     template: `<div data-key="value" class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 8,
                                    optionsText: 'localizedName',
                                    name: '#[KMK003_201]',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_57"),
                     key: "rounding",
                     isRoudingColumn: true,
                     unitAttrName: 'roundingTime',
                     dataSource: self.settingEnum.rounding,
                     defaultValue: ko.observable(0),
                     width: 160,
                     template: `<div data-key="value" class="column-combo-box" data-bind="ntsComboBox: {
                                    name: '#[KMK003_202]',
                                    optionsValue: 'value',
                                    visibleItemsCount: 3,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                 },
                 {
                     headerText: nts.uk.resource.getText("KMK003_58"),
                     key: "otFrameNo",
                     dataSource: self.lstOvertimeWorkFrame,
                     defaultValue: ko.observable(1),
                     width: 150,
                     template: `<div data-key="overtimeWorkFrNo" class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'overtimeWorkFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'overtimeWorkFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'overtimeWorkFrName', length: 12 }]}">
                                </div>`
                 }];
            }
        /**
         * function get column setting fixed and diff time
         */
         private columnSettingFixedAndDiffTime(): Array<any> {
            let self = this;
             var arraySettingFlex : Array<any> = self.columnSettingFlex();
             arraySettingFlex.push({
                 headerText: nts.uk.resource.getText("KMK003_186"),
                 key: "legalOTframeNo",
                 dataSource: self.lstOvertimeWorkFrame,
                 defaultValue: ko.observable(1),
                 width: 130,
                 template: `<div data-key="overtimeWorkFrNo" class="column-combo-box" data-bind="ntsComboBox: {
                                    name: '#[KMK003_204]',
                                    optionsValue: 'overtimeWorkFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'overtimeWorkFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'overtimeWorkFrName', length: 12 }]}">
                                </div>`
             });
             arraySettingFlex.push({
                 headerText: nts.uk.resource.getText("KMK003_187"),
                 key: "settlementOrder",
                 dataSource: self.lstSettlementOrder,
                 defaultValue: ko.observable(1),
                 width: 50,
                 template: `<div data-key="settlementOrder" class="column-combo-box" data-bind="ntsComboBox: {
                                    name: '#[KMK003_187]',
                                    optionsValue: 'settlementOrder',
                                    visibleItemsCount: 10,
                                    optionsText: 'settlementOrderName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'settlementOrderName', length: 12 }]}">
                                </div>`
             });
            return arraySettingFlex;
        }
        
        /**
         * init array setting column option morning flex
         */
         private columnSettingFlex(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"), 
                    key: "timezone",
                    defaultValue: ko.observable({ startTime: 0, endTime: 0 }), 
                    width: 250, 
                    template: `<div data-bind="ntsTimeRangeEditor: { 
                        startConstraint: 'TimeWithDayAttr', endConstraint: 'TimeWithDayAttr',
                        required: true, enable: true, inputFormat: 'time',  startTimeNameId: '#[KMK003_166]', endTimeNameId: '#[KMK003_167]',paramId:'KMK003_89'}"/>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_56"),
                    key: "roundingTime",
                    dataSource: self.settingEnum.roundingTime,
                    defaultValue: ko.observable(0),
                    width: 80,
                    template: `<div data-key="value" class="column-combo-box" data-bind="ntsComboBox: {
                                    optionsValue: 'value',
                                    visibleItemsCount: 8,
                                    optionsText: 'localizedName',
                                    name: '#[KMK003_201]',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_57"),
                    key: "rounding",
                    isRoudingColumn: true,
                    unitAttrName: 'roundingTime',
                    dataSource: self.settingEnum.rounding,
                    defaultValue: ko.observable(0),
                    width: 170,
                    template: `<div data-key="value" class="column-combo-box" data-bind="ntsComboBox: {
                                    name: '#[KMK003_202]',
                                    optionsValue: 'value',
                                    visibleItemsCount: 3,
                                    optionsText: 'localizedName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'localizedName', length: 10 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_58"), 
                    key: "otFrameNo",
                    dataSource: self.lstOvertimeWorkFrame,
                    defaultValue: ko.observable(1),
                    width: 130,
                    template: `<div data-key="overtimeWorkFrNo" class="column-combo-box" data-bind="ntsComboBox: {
                                    name: '#[KMK003_203]',
                                    optionsValue: 'overtimeWorkFrNo',
                                    visibleItemsCount: 10,
                                    optionsText: 'overtimeWorkFrName',
                                    editable: false,
                                    enable: true,
                                    columns: [{ prop: 'overtimeWorkFrName', length: 12 }]}">
                                </div>`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_182"),
                    key: "earlyOTUse",
                    defaultValue: ko.observable(false),
                    width: 50,
                    template: `<div data-bind="ntsCheckBox: { enable: true }">`
                }
            ];
        }

    }

    class KMK003A3BindingHandler implements KnockoutBindingHandler {
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

        private getData() {
            let self = this;
            // service.findWorkTimeSetByCode()
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a3/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            var settingEnum: WorkTimeSettingEnumDto = input.enum;
            var mainSettingModel: MainSettingModel = input.mainModel;
            var isLoading:  KnockoutObservable<boolean> = input.isLoading; 
            var isDetailMode:  KnockoutObservable<boolean> = input.isDetailMode;
            var useHalfDay:  KnockoutObservable<boolean> = input.useHalfDay;
            var isClickSave: KnockoutObservable<boolean> = input.isClickSave;
            var isNewMode: KnockoutObservable<boolean> = input.isNewMode;
            let screenModel = new ScreenModel(settingEnum, mainSettingModel, isLoading, isDetailMode, useHalfDay,isNewMode);
            nts.uk.at.view.kmk003.a3.service.findAllOvertimeWorkFrame().done(function(data) {
                screenModel.lstOvertimeWorkFrame = data;
                screenModel.initDataModel();
                $(element).load(webserviceLocator, function() {
                    ko.cleanNode($(element)[0]);
                    ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                    isClickSave.subscribe((v) => {
                        if (v) {
                            screenModel.dataSourceOvertimeFlow.valueHasMutated();
                        }
                    });
                });
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A3'] = new KMK003A3BindingHandler();

}
