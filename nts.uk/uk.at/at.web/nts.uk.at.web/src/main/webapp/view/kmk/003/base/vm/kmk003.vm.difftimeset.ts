module nts.uk.at.view.kmk003.a {

    import EmTimeZoneSetDto = nts.uk.at.view.kmk003.a.service.model.common.EmTimeZoneSetDto;
    import TimeZoneRoundingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeZoneRoundingDto;
    import TimeRoundingSettingDto = nts.uk.at.view.kmk003.a.service.model.common.TimeRoundingSettingDto;

    import DayOffTimezoneSettingDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DayOffTimezoneSettingDto;
    import DiffTimeDeductTimezoneDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeDeductTimezoneDto;
    import DiffTimeRestTimezoneDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeRestTimezoneDto;
    import DiffTimeDayOffWorkTimezoneDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeDayOffWorkTimezoneDto;
    import DiffTimeOTTimezoneSetDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeOTTimezoneSetDto;
    import DiffTimezoneSettingDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimezoneSettingDto;
    import DiffTimeHalfDayWorkTimezoneDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeHalfDayWorkTimezoneDto;
    import DiffTimeWorkStampReflectTimezoneDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeWorkStampReflectTimezoneDto;
    import StampReflectTimezoneDto = nts.uk.at.view.kmk003.a.service.model.common.StampReflectTimezoneDto;
    import EmTimezoneChangeExtentDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.EmTimezoneChangeExtentDto;

    import HDWorkTimeSheetSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.HDWorkTimeSheetSettingModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    import OverTimeOfTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OverTimeOfTimeZoneSetModel;
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import InstantRoundingModel = nts.uk.at.view.kmk003.a.viewmodel.common.InstantRoundingModel;
    import FixedWorkRestSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkRestSetModel;
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
    import FixedWorkCalcSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkCalcSettingModel;
    import TimeRangeModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModel;
    import OffdayWorkTimeConverter = nts.uk.at.view.kmk003.a.viewmodel.common.OffdayWorkTimeConverter;
    import FixedTableDataConverter = nts.uk.at.view.kmk003.a.viewmodel.common.FixedTableDataConverter;
    import TimeRange = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRange;
    import TimePeriod = nts.uk.at.view.kmk003.a.viewmodel.common.TimePeriod;
    import EmTimeZoneSetFixedTableModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetFixedTableModel;
    import TimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.predset.TimezoneModel;

    import DiffTimeWorkSettingDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeWorkSettingDto;
    export module viewmodel {
        export module difftimeset {

            export class DayOffTimezoneSettingModel extends HDWorkTimeSheetSettingModel {
                isUpdateStartTime: KnockoutObservable<boolean>;

                constructor() {
                    super();
                    this.isUpdateStartTime = ko.observable(false);
                }

                updateData(data: DayOffTimezoneSettingDto) {
                    super.updateData(data);
                    this.isUpdateStartTime(data.isUpdateStartTime);
                }

                toDto(): DayOffTimezoneSettingDto {
                    var dataDTO: DayOffTimezoneSettingDto = {
                        workTimeNo: this.workTimeNo(),
                        timezone: this.timezone.toDto(),
                        isLegalHolidayConstraintTime: this.isLegalHolidayConstraintTime(),
                        inLegalBreakFrameNo: this.inLegalBreakFrameNo(),
                        isNonStatutoryDayoffConstraintTime: this.isNonStatutoryDayoffConstraintTime(),
                        outLegalBreakFrameNo: this.outLegalBreakFrameNo(),
                        isNonStatutoryHolidayConstraintTime: this.isNonStatutoryHolidayConstraintTime(),
                        outLegalPubHDFrameNo: this.outLegalPubHDFrameNo(),
                        isUpdateStartTime: this.isUpdateStartTime()
                    }
                    return dataDTO;
                }
            }

            export class DiffTimeRangeModel {
                column1: KnockoutObservable<TimeRange>;
                isUpdateStartTime: KnockoutObservable<boolean>;

                constructor(dtdtz: DiffTimeDeductTimezoneModel) {
                    let range = new TimeRange();
                    range.startTime = dtdtz.start();
                    range.endTime = dtdtz.end();
                    this.column1 = ko.observable(range);
                    this.isUpdateStartTime = ko.observable(dtdtz.isUpdateStartTime());
                }
            }

            export class DiffTimeDeductTimezoneModel extends DeductionTimeModel {
                isUpdateStartTime: KnockoutObservable<boolean>;

                constructor() {
                    super();
                    this.isUpdateStartTime = ko.observable(false);
                }

                updateData(data: DiffTimeDeductTimezoneDto) {
                    super.updateData(data);
                    this.isUpdateStartTime(data.updateStartTime);
                }

                toDto(): DiffTimeDeductTimezoneDto {
                    var dataDTO: DiffTimeDeductTimezoneDto = {
                        start: this.start(),
                        end: this.end(),
                        updateStartTime: this.isUpdateStartTime()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.isUpdateStartTime(false);
                    super.resetData();
                }
            }


            export class DiffTimeRestTimezoneModel extends FixedTableDataConverter<DiffTimeRangeModel, DiffTimeDeductTimezoneModel>{
                restTimezones: KnockoutObservableArray<DiffTimeDeductTimezoneModel>;

                constructor() {
                    super();
                    let self = this;
                    this.restTimezones = self.originalList;
                }

                toOriginalDto(convertedItem: DiffTimeRangeModel): DiffTimeDeductTimezoneDto {
                    return {
                        start: convertedItem.column1().startTime,
                        end: convertedItem.column1().endTime,
                        updateStartTime: convertedItem.isUpdateStartTime ? convertedItem.isUpdateStartTime() : false,
                    };
                }

                createOriginal(): DiffTimeDeductTimezoneModel {
                    return new DiffTimeDeductTimezoneModel();
                }

                createConverted(original: DiffTimeDeductTimezoneModel): DiffTimeRangeModel {
                    return new DiffTimeRangeModel(original);
                }

                updateData(data: DiffTimeRestTimezoneDto) {
                    this.restTimezones([]);
                    for (var dataDTO of data.restTimezones) {
                        var dataModel: DiffTimeDeductTimezoneModel = new DiffTimeDeductTimezoneModel();
                        dataModel.updateData(dataDTO);
                        this.restTimezones.push(dataModel);
                    }
                }

                toDto(): DiffTimeRestTimezoneDto {
                    var restTimezones: DiffTimeDeductTimezoneDto[] = [];
                    for (var dataModel of this.restTimezones()) {
                        restTimezones.push(dataModel.toDto());
                    }
                    var dataDTO: DiffTimeRestTimezoneDto = {
                        restTimezones: restTimezones
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.restTimezones([]);
                }
            }


            export class DiffTimeDayOffWorkTimezoneModel extends OffdayWorkTimeConverter {
                restTimezone: DiffTimeRestTimezoneModel;
                workTimezones: KnockoutObservableArray<HDWorkTimeSheetSettingModel>;

                constructor() {
                    super();
                    this.restTimezone = new DiffTimeRestTimezoneModel();
                    this.workTimezones = this.originalList;
                }

                updateData(data: DiffTimeDayOffWorkTimezoneDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    let mapped = _.map(data.workTimezones, wtz => {
                        let dataModel = new HDWorkTimeSheetSettingModel();
                        dataModel.updateData(wtz);
                        return dataModel;
                    });
                    this.workTimezones(mapped);
                }

                toDto(): DiffTimeDayOffWorkTimezoneDto {
                    var workTimezones: DayOffTimezoneSettingDto[] = [];
                    _.forEach(this.workTimezones(), dataModel => {
                        let dto1 = dataModel.toDto();
                        let dto2 = <DayOffTimezoneSettingDto>{};
                        dto2.isUpdateStartTime = false;
                        dto2.workTimeNo = dto1.workTimeNo;
                        dto2.timezone = dto1.timezone;
                        dto2.isLegalHolidayConstraintTime = dto1.isLegalHolidayConstraintTime;
                        dto2.inLegalBreakFrameNo = dto1.inLegalBreakFrameNo;
                        dto2.isNonStatutoryDayoffConstraintTime = dto1.isNonStatutoryDayoffConstraintTime;
                        dto2.outLegalBreakFrameNo = dto1.outLegalBreakFrameNo;
                        dto2.isNonStatutoryHolidayConstraintTime = dto1.isNonStatutoryHolidayConstraintTime;
                        dto2.outLegalPubHDFrameNo = dto1.outLegalPubHDFrameNo;
                        workTimezones.push(dto2);
                    });
                    var dataDTO: DiffTimeDayOffWorkTimezoneDto = {
                        restTimezone: this.restTimezone.toDto(),
                        workTimezones: workTimezones,
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.restTimezone.resetData();
                    this.workTimezones([]);
                }
            }

            export class DiffTimeOTTimezoneSetModel extends OverTimeOfTimeZoneSetModel {
                updateStartTime: KnockoutObservable<boolean>;

                constructor() {
                    super();
                    this.updateStartTime = ko.observable(false);
                }

                updateData(data: DiffTimeOTTimezoneSetDto) {
                    super.updateData(data);
                    this.updateStartTime(data.updateStartTime);
                }

                toDto(): DiffTimeOTTimezoneSetDto {
                    var dataDTO: DiffTimeOTTimezoneSetDto = {
                        workTimezoneNo: this.workTimezoneNo(),
                        restraintTimeUse: this.restraintTimeUse(),
                        earlyOTUse: this.earlyOTUse(),
                        timezone: this.timezone.toDto(),
                        otFrameNo: this.otFrameNo(),
                        legalOTframeNo: this.legalOTframeNo(),
                        settlementOrder: this.settlementOrder(),
                        updateStartTime: this.updateStartTime()
                    };
                    return dataDTO;
                }
            }

            export class DiffOverTimeFixedTableModel {
                workTimezoneNo: number;
                timezone: KnockoutObservable<any>;
                rounding: KnockoutObservable<number>;
                roundingTime: KnockoutObservable<number>;
                restraintTimeUse: KnockoutObservable<boolean>;
                otFrameNo: KnockoutObservable<number>;
                earlyOTUse: KnockoutObservable<boolean>;
                legalOTframeNo: KnockoutObservable<number>;
                settlementOrder: KnockoutObservable<number>;
                isUpdateStartTime: KnockoutObservable<boolean>;

                constructor(otModel: DiffTimeOTTimezoneSetModel) {
                    this.workTimezoneNo = otModel.workTimezoneNo();
                    this.timezone = ko.observable({
                        startTime: otModel.timezone.start(),
                        endTime: otModel.timezone.end()
                    });
                    this.rounding = ko.observable(otModel.timezone.rounding.rounding());
                    this.roundingTime = ko.observable(otModel.timezone.rounding.roundingTime());
                    this.restraintTimeUse = ko.observable(otModel.restraintTimeUse());
                    this.otFrameNo = ko.observable(otModel.otFrameNo());
                    this.earlyOTUse = ko.observable(otModel.earlyOTUse());
                    this.legalOTframeNo = ko.observable(otModel.legalOTframeNo());
                    this.settlementOrder = ko.observable(otModel.settlementOrder());
                    this.isUpdateStartTime = ko.observable(otModel.updateStartTime());
                }
            }

            export class DiffTimezoneSettingModel extends FixedTableDataConverter<DiffOverTimeFixedTableModel, DiffTimeOTTimezoneSetModel> {
                employmentTimezones: KnockoutObservableArray<EmTimeZoneSetModel>;
                lstOtTimezone: KnockoutObservableArray<DiffTimeOTTimezoneSetModel>;

                lstWorkingTimezoneSimpleMode: KnockoutObservableArray<EmTimeZoneSetFixedTableModel>;
                displayMode: KnockoutObservable<number>;

                constructor(displayMode: KnockoutObservable<number>) {
                    super();
                    this.displayMode = displayMode;
                    this.employmentTimezones = this.originalList2;
                    this.lstOtTimezone = this.originalList;
                    this.lstWorkingTimezoneSimpleMode = ko.observableArray([]);
                }

                public initSubscribeForTab2(timezone: TimezoneModel): void {
                    let self = this;
                    timezone.valueChangedNotifier.subscribe(() => {
                        const cloned = _.cloneDeep(self.lstWorkingTimezoneSimpleMode()[0]);
                        if (cloned) {
                            const updatedTime = <TimePeriod>{};
                            updatedTime.startTime = timezone.start();
                            updatedTime.endTime = timezone.end();
                            cloned.timeRange(updatedTime);

                            // update list on fixed table
                            self.lstWorkingTimezoneSimpleMode([cloned]);
                        }
                    });

                }

                toOriginalDto(convertedItem: DiffOverTimeFixedTableModel): DiffTimeOTTimezoneSetDto {
                    return {
                        workTimezoneNo: convertedItem.workTimezoneNo ? convertedItem.workTimezoneNo : 0,
                        timezone: {
                            rounding: {
                                roundingTime: convertedItem.roundingTime(),
                                rounding: convertedItem.rounding()
                            },
                            start: convertedItem.timezone().startTime,
                            end: convertedItem.timezone().endTime
                        },
                        restraintTimeUse: convertedItem.restraintTimeUse ? convertedItem.restraintTimeUse() : false,
                        earlyOTUse: convertedItem.earlyOTUse(),
                        otFrameNo: convertedItem.otFrameNo(),
                        legalOTframeNo: convertedItem.legalOTframeNo ? convertedItem.legalOTframeNo() : 1,
                        settlementOrder: convertedItem.settlementOrder ? convertedItem.settlementOrder() : 1,
                        updateStartTime: convertedItem.isUpdateStartTime ? convertedItem.isUpdateStartTime() : false
                    };
                }

                createOriginal(): DiffTimeOTTimezoneSetModel {
                    return new DiffTimeOTTimezoneSetModel();
                }

                createConverted(original: DiffTimeOTTimezoneSetModel): DiffOverTimeFixedTableModel {
                    return new DiffOverTimeFixedTableModel(original);
                }

                optionalProcessing(list: Array<DiffOverTimeFixedTableModel>): Array<DiffOverTimeFixedTableModel> {
                    let count = 0;
                    return _.forEach(list, item => item.workTimezoneNo = ++count);
                }

                optionalProcessing2(list: Array<EmTimeZoneSetFixedTableModel>): Array<EmTimeZoneSetFixedTableModel> {
                    let count = 0;
                    return _.forEach(list, item => item.employmentTimeFrameNo = ++count);
                }

                toOriginalDto2(convertedItem: EmTimeZoneSetFixedTableModel): EmTimeZoneSetDto {
                    return {
                        employmentTimeFrameNo: convertedItem.employmentTimeFrameNo ? convertedItem.employmentTimeFrameNo : 0,
                        timezone: {
                            rounding: {
                                roundingTime: convertedItem.roundingTime(),
                                rounding: convertedItem.rounding()
                            },
                            start: convertedItem.timeRange().startTime,
                            end: convertedItem.timeRange().endTime
                        }
                    };
                }

                createOriginal2(): EmTimeZoneSetModel {
                    return new EmTimeZoneSetModel();
                }

                createConverted2(original: EmTimeZoneSetModel): EmTimeZoneSetFixedTableModel {
                    return new EmTimeZoneSetFixedTableModel(original);
                }

                updateData(data: DiffTimezoneSettingDto) {
                    let self = this;
                    self.updateEmpTimezones(data.employmentTimezones);
                    self.updateOtTimezones(data.lstOtTimezone);
                }

                updateEmpTimezones(data: Array<EmTimeZoneSetDto>): void {
                    let self = this;
                    const updatedList = _.map(data, item => {
                        let m = new EmTimeZoneSetModel();
                        m.updateData(item);
                        return m;
                    });
                    self.employmentTimezones(updatedList);

                    // for simple mode list
                    const firstItem = updatedList[0];
                    if (firstItem) {
                        let simple = self.lstWorkingTimezoneSimpleMode()[0];
                        if (simple) {
                            simple.roundingTime(firstItem.timezone.rounding.roundingTime());
                            simple.rounding(firstItem.timezone.rounding.rounding());
                        } else {
                            simple = new EmTimeZoneSetFixedTableModel(firstItem);
                        }
                        self.lstWorkingTimezoneSimpleMode([simple]);
                    }
                }

                updateOtTimezones(data: Array<DiffTimeOTTimezoneSetDto>): void {
                    let self = this;
                    const updatedList = data ? _.map(data, item => {
                        let m = new DiffTimeOTTimezoneSetModel();
                        m.updateData(item);
                        return m;
                    }) : [];
                    self.lstOtTimezone(updatedList);
                }

                toDto(): DiffTimezoneSettingDto {
                    let self = this;
                    let employmentTimezones = self.employmentTimezones().map(item => item.toDto());
                    let lstOtTimezone = self.lstOtTimezone().map(item => item.toDto());

                    // get data from simple mode (tab2)
                    if (self.displayMode() == 1) {
                        const simple = this.lstWorkingTimezoneSimpleMode()[0];
                        const detail = <EmTimeZoneSetDto>{};
                        detail.timezone = <TimeZoneRoundingDto>{};
                        detail.timezone.rounding = <TimeRoundingSettingDto>{};
                        detail.employmentTimeFrameNo = 1;
                        detail.timezone.rounding.roundingTime = simple.roundingTime();
                        detail.timezone.rounding.rounding = simple.rounding();
                        detail.timezone.start = simple.timeRange().startTime;
                        detail.timezone.end = simple.timeRange().endTime;
                        employmentTimezones = [detail];
                    }

                    var dataDTO: DiffTimezoneSettingDto = {
                        employmentTimezones: employmentTimezones,
                        lstOtTimezone: lstOtTimezone
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.employmentTimezones([]);
                    this.lstOtTimezone([]);
                }
            }

            export class DiffTimeHalfDayWorkTimezoneModel {
                restTimezone: DiffTimeRestTimezoneModel;
                workTimezone: DiffTimezoneSettingModel;
                amPmAtr: KnockoutObservable<number>;

                constructor(isSimpleMode: KnockoutObservable<number>) {
                    this.restTimezone = new DiffTimeRestTimezoneModel();
                    this.workTimezone = new DiffTimezoneSettingModel(isSimpleMode);
                    this.amPmAtr = ko.observable(0);
                }

                static getDefaultData(isSimpleMode: KnockoutObservable<number>): Array<DiffTimeHalfDayWorkTimezoneModel> {
                    let oneday = new DiffTimeHalfDayWorkTimezoneModel(isSimpleMode);
                    oneday.amPmAtr(0);
                    let morning = new DiffTimeHalfDayWorkTimezoneModel(isSimpleMode);
                    morning.amPmAtr(1);
                    let afternoon = new DiffTimeHalfDayWorkTimezoneModel(isSimpleMode);
                    afternoon.amPmAtr(2);
                    let list: any[] = [];
                    list.push(oneday);
                    list.push(morning);
                    list.push(afternoon);
                    return list;
                }

                updateData(data: DiffTimeHalfDayWorkTimezoneDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.workTimezone.updateData(data.workTimezone);
                    this.amPmAtr(data.amPmAtr);
                }

                /**
                 * Return list dto including one day, morning, afternoon with the same data
                 */
                toListDto(): Array<DiffTimeHalfDayWorkTimezoneDto> {
                    let self = this;
                    let oneDay = this.toDto();
                    oneDay.amPmAtr = 0;
                    let morning = this.toDto();
                    morning.amPmAtr = 1;
                    let afternoon = this.toDto();
                    afternoon.amPmAtr = 2;

                    return [oneDay, morning, afternoon];
                }

                toDto(): DiffTimeHalfDayWorkTimezoneDto {
                    var dataDTO: DiffTimeHalfDayWorkTimezoneDto = {
                        restTimezone: this.restTimezone.toDto(),
                        workTimezone: this.workTimezone.toDto(),
                        amPmAtr: this.amPmAtr()
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.restTimezone.resetData();
                    this.workTimezone.resetData();
                }
            }

            export class DiffTimeWorkStampReflectTimezoneModel {
                stampReflectTimezone: KnockoutObservableArray<StampReflectTimezoneModel>;
                isUpdateStartTime: KnockoutObservable<boolean>;

                constructor() {
                    this.stampReflectTimezone = ko.observableArray([]);
                    this.isUpdateStartTime = ko.observable(false);
                    this.initStampSets();
                }
                
                initStampSets() {
                    let goWork1Stamp: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                    goWork1Stamp.workNo(1);
                    goWork1Stamp.classification(0);
                    let goWork2Stamp: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                    goWork2Stamp.workNo(2);
                    goWork2Stamp.classification(0);
                    let leaveWork1Stamp: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                    leaveWork1Stamp.workNo(1);
                    leaveWork1Stamp.classification(1);
                    let leaveWork2Stamp: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                    leaveWork2Stamp.workNo(2);
                    leaveWork2Stamp.classification(1);
                    this.stampReflectTimezone.push(goWork1Stamp);
                    this.stampReflectTimezone.push(leaveWork1Stamp);   
                    this.stampReflectTimezone.push(goWork2Stamp);
                    this.stampReflectTimezone.push(leaveWork2Stamp);                         
                }

                updateData(data: DiffTimeWorkStampReflectTimezoneDto) {
                    this.updateListStamp(data.stampReflectTimezone);
                    this.isUpdateStartTime(data.updateStartTime);
                }

                updateListStamp(lstStampReflectTimezone: StampReflectTimezoneDto[]) {
                    let self = this;
                    _.forEach(lstStampReflectTimezone, item => {
                        if (item.workNo == 1 && item.classification == 0) {
                            this.getGoWork1Stamp().updateData(item);
                        }                        
                        if (item.workNo == 1 && item.classification == 1) {
                            this.getLeaveWork1Stamp().updateData(item);
                        }
                        if (item.workNo == 2 && item.classification == 0) {
                            this.getGoWork2Stamp().updateData(item);
                        }                        
                        if (item.workNo == 2 && item.classification == 1) {
                            this.getLeaveWork2Stamp().updateData(item);
                        }
                    });    
                }
                
                toDto(): DiffTimeWorkStampReflectTimezoneDto {
                    var lstStamp: any = [];
                    this.stampReflectTimezone().forEach(function(item, index) {
                        lstStamp.push(item.toDto());
                    });
                    var dataDTO: DiffTimeWorkStampReflectTimezoneDto = {
                        stampReflectTimezone: lstStamp,
                        updateStartTime: this.isUpdateStartTime(),
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.isUpdateStartTime(false);
                    this.stampReflectTimezone().forEach(function(item, index) {
                        item.resetData();
                    });
                }
                
                getGoWork1Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.stampReflectTimezone(), p => p.workNo() == 1 && p.classification() == 0) }
                getLeaveWork1Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.stampReflectTimezone(), p => p.workNo() == 1 && p.classification() == 1) }
                getGoWork2Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.stampReflectTimezone(), p => p.workNo() == 2 && p.classification() == 0) }
                getLeaveWork2Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.stampReflectTimezone(), p => p.workNo() == 2 && p.classification() == 1) }
            }

            export class EmTimezoneChangeExtentModel {
                aheadChange: KnockoutObservable<number>;
                unit: InstantRoundingModel;
                behindChange: KnockoutObservable<number>;

                constructor() {
                    this.aheadChange = ko.observable(0);
                    this.unit = new InstantRoundingModel();
                    this.behindChange = ko.observable(0);
                }

                public resetData(): void {
                    let self = this;
                    self.aheadChange(0);
                    self.behindChange(0);
                }

                updateData(data: EmTimezoneChangeExtentDto) {
                    this.aheadChange(data.aheadChange);
                    this.unit.updateData(data.unit);
                    this.behindChange(data.behindChange);
                }

                toDto(): EmTimezoneChangeExtentDto {
                    var dataDTO: EmTimezoneChangeExtentDto = {
                        aheadChange: this.aheadChange(),
                        unit: this.unit.toDto(),
                        behindChange: this.behindChange()
                    };
                    return dataDTO;
                }
            }

            export class DiffTimeWorkSettingModel {
                workTimeCode: KnockoutObservable<string>;
                restSet: FixedWorkRestSetModel;
                dayoffWorkTimezone: DiffTimeDayOffWorkTimezoneModel;
                commonSet: WorkTimezoneCommonSetModel;
                isUseHalfDayShift: KnockoutObservable<boolean>;
                changeExtent: EmTimezoneChangeExtentModel;
                halfDayWorkTimezones: DiffTimeHalfDayWorkTimezoneModel[];
                stampReflectTimezone: DiffTimeWorkStampReflectTimezoneModel;
                overtimeSetting: KnockoutObservable<number>;
                calculationSetting: FixedWorkCalcSettingModel;
                displayMode: KnockoutObservable<number>;
                
                constructor(displayMode: KnockoutObservable<number>) {
                    this.displayMode = displayMode;
                    this.workTimeCode = ko.observable('');
                    this.restSet = new FixedWorkRestSetModel();
                    this.dayoffWorkTimezone = new DiffTimeDayOffWorkTimezoneModel();
                    this.commonSet = new WorkTimezoneCommonSetModel();
                    this.isUseHalfDayShift = ko.observable(false);
                    this.changeExtent = new EmTimezoneChangeExtentModel();
                    this.halfDayWorkTimezones = DiffTimeHalfDayWorkTimezoneModel.getDefaultData(this.displayMode);
                    this.stampReflectTimezone = new DiffTimeWorkStampReflectTimezoneModel();
                    this.overtimeSetting = ko.observable(0);
                    // Update phase 2
                    this.calculationSetting = new FixedWorkCalcSettingModel();
                }

                public getHDWtzOneday(): DiffTimeHalfDayWorkTimezoneModel {
                    let self = this;
                    return _.find(self.halfDayWorkTimezones, time => time.amPmAtr() == 0);
                }
                public getHDWtzMorning(): DiffTimeHalfDayWorkTimezoneModel {
                    let self = this;
                    return _.find(self.halfDayWorkTimezones, time => time.amPmAtr() == 1);
                }
                public getHDWtzAfternoon(): DiffTimeHalfDayWorkTimezoneModel {
                    let self = this;
                    return _.find(self.halfDayWorkTimezones, time => time.amPmAtr() == 2);
                }

                public initSubscriberForTab2(timezone: TimezoneModel): void {
                    let self = this;
                    self.getHDWtzOneday().workTimezone.initSubscribeForTab2(timezone);
                    self.getHDWtzMorning().workTimezone.initSubscribeForTab2(timezone);
                    self.getHDWtzAfternoon().workTimezone.initSubscribeForTab2(timezone);
                }

                public resetData(isNewMode?:boolean): void {
                    let self = this;
                    self.restSet.resetData();
                    self.dayoffWorkTimezone.resetData();
                    self.commonSet.resetData();
                    self.isUseHalfDayShift(false);
                    self.changeExtent.resetData();
                    self.halfDayWorkTimezones.forEach(function(item: DiffTimeHalfDayWorkTimezoneModel, index: number) {
                        item.resetData();
                    });
                    self.stampReflectTimezone.resetData();
                    self.overtimeSetting(0);
                    // Update phase 2
                    if (!isNewMode) {
                        self.calculationSetting.resetData();
                    }
                }

                updateData(data: DiffTimeWorkSettingDto) {
                    this.workTimeCode(data.workTimeCode);
                    this.restSet.updateData(data.restSet);
                    this.dayoffWorkTimezone.updateData(data.dayoffWorkTimezone);
                    this.commonSet.updateData(data.commonSet);
                    this.isUseHalfDayShift(data.useHalfDayShift);
                    this.changeExtent.updateData(data.changeExtent);
                    this.updateListHalfDay(data.halfDayWorkTimezones);
                    this.stampReflectTimezone.updateData(data.stampReflectTimezone);
                    this.overtimeSetting(data.overtimeSetting);
                    // Update phase 2
                    this.calculationSetting.updateData(data.calculationSetting);
                }

                updateListHalfDay(lstHalfDayWorkTimezone: DiffTimeHalfDayWorkTimezoneDto[]): void {
                    lstHalfDayWorkTimezone = lstHalfDayWorkTimezone.sort((item1, item2) => item1.amPmAtr - item2.amPmAtr);
                    this.getHDWtzOneday().updateData(lstHalfDayWorkTimezone[0]);
                    this.getHDWtzMorning().updateData(lstHalfDayWorkTimezone[1]);
                    this.getHDWtzAfternoon().updateData(lstHalfDayWorkTimezone[2]);
                }

                toDto(commonSetting: WorkTimezoneCommonSetModel): DiffTimeWorkSettingDto {
                    var halfDayWorkTimezones: DiffTimeHalfDayWorkTimezoneDto[] = [];
                    if (this.displayMode() == TabMode.DETAIL && this.isUseHalfDayShift()) {
                        halfDayWorkTimezones = _.map(this.halfDayWorkTimezones, item => item.toDto());
                    } else {
                        halfDayWorkTimezones = this.getHDWtzOneday().toListDto();
                    }

                    var dataDTO: DiffTimeWorkSettingDto = {
                        workTimeCode: this.workTimeCode(),
                        restSet: this.restSet.toDto(),
                        dayoffWorkTimezone: this.dayoffWorkTimezone.toDto(),
                        commonSet: commonSetting.toDto(),
                        useHalfDayShift: this.isUseHalfDayShift(),
                        changeExtent: this.changeExtent.toDto(),
                        halfDayWorkTimezones: halfDayWorkTimezones,
                        stampReflectTimezone: this.stampReflectTimezone.toDto(),
                        overtimeSetting: this.overtimeSetting(),
                        // Update phase 2
                        calculationSetting: this.calculationSetting.toDto()
                    };
                    return dataDTO;
                }
            }

        }
    }
}