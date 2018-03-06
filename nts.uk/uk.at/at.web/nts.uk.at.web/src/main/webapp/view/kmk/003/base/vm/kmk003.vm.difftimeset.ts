module nts.uk.at.view.kmk003.a {

    import EmTimeZoneSetDto = nts.uk.at.view.kmk003.a.service.model.common.EmTimeZoneSetDto;

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
    import TimeRangeModelConverter = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModelConverter;
    import TimeRangeModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModel;
    import OffdayWorkTimeConverter = nts.uk.at.view.kmk003.a.viewmodel.common.OffdayWorkTimeConverter;

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

            export class DiffTimeDeductTimezoneModel extends DeductionTimeModel {
                isUpdateStartTime: KnockoutObservable<boolean>;

                constructor() {
                    super();
                    this.isUpdateStartTime = ko.observable(false);
                }

                updateData(data: DiffTimeDeductTimezoneDto) {
                    super.updateData(data);
                    this.isUpdateStartTime(data.isUpdateStartTime);
                }

                toDto(): DiffTimeDeductTimezoneDto {
                    var dataDTO: DiffTimeDeductTimezoneDto = {
                        start: this.start(),
                        end: this.end(),
                        isUpdateStartTime: this.isUpdateStartTime()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.isUpdateStartTime(false);
                    super.resetData();
                }
            }


            export class DiffTimeRestTimezoneModel extends TimeRangeModelConverter<DiffTimeDeductTimezoneModel>{
                restTimezones: KnockoutObservableArray<DiffTimeDeductTimezoneModel>;

                constructor() {
                    super();
                    let self = this;
                    this.restTimezones = self.originalList;
                }

                toConvertedList(): Array<TimeRangeModel> {
                    let self = this;
                    return _.map(self.restTimezones(), tz => self.toTimeRangeItem(tz.start(), tz.end()));
                }

                fromConvertedList(newList: Array<TimeRangeModel>): Array<DiffTimeDeductTimezoneModel> {
                    return _.map(newList, newVl => {
                        let vl = new DiffTimeDeductTimezoneModel();
                        vl.start(newVl.column1().startTime);
                        vl.end(newVl.column1().endTime);
                        return vl;
                    });
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
                isUpdateStartTime: KnockoutObservable<boolean>;

                constructor() {
                    super();
                    this.isUpdateStartTime = ko.observable(false);
                }

                updateData(data: DiffTimeOTTimezoneSetDto) {
                    super.updateData(data);
                    this.isUpdateStartTime(data.isUpdateStartTime);
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
                        isUpdateStartTime: this.isUpdateStartTime()
                    };
                    return dataDTO;
                }
            }

            export class DiffTimezoneSettingModel {
                employmentTimezones: KnockoutObservableArray<EmTimeZoneSetModel>;
                lstOtTimezone: KnockoutObservableArray<DiffTimeOTTimezoneSetModel>;

                constructor() {
                    this.employmentTimezones = ko.observableArray([]);
                    this.lstOtTimezone = ko.observableArray([]);
                }

                updateData(data: DiffTimezoneSettingDto) {
                    let self = this;
                    self.employmentTimezones(data.employmentTimezones.map(item => {
                        let m = new EmTimeZoneSetModel();
                        m.updateData(item);
                        return m;
                    }));

                    self.lstOtTimezone(data.lstOtTimezone?data.lstOtTimezone.map(item => {
                        let m = new DiffTimeOTTimezoneSetModel();
                        m.updateData(item);
                        return m;
                    }):[]);

                }
                
                updateOvertimeZone(lstOTTimezone: DiffTimeOTTimezoneSetDto[]) {
                    this.lstOtTimezone([]);
                    var dataModelTimezone: DiffTimeOTTimezoneSetModel[] = [];
                    for (var dataDTO of lstOTTimezone) {
                        var dataModel: DiffTimeOTTimezoneSetModel = new DiffTimeOTTimezoneSetModel();
                        dataModel.updateData(dataDTO);
                        dataModelTimezone.push(dataModel);
                    }
                    this.lstOtTimezone(dataModelTimezone);
                }

                toDto(): DiffTimezoneSettingDto {
                    let self = this;
                    let employmentTimezones = self.employmentTimezones().map(item => item.toDto());
                    let lstOtTimezone = self.lstOtTimezone().map(item => item.toDto());

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

                constructor() {
                    this.restTimezone = new DiffTimeRestTimezoneModel();
                    this.workTimezone = new DiffTimezoneSettingModel();
                    this.amPmAtr = ko.observable(0);
                }

                static getDefaultData(): Array<DiffTimeHalfDayWorkTimezoneModel> {
                    let oneday = new DiffTimeHalfDayWorkTimezoneModel();
                    oneday.amPmAtr(0);
                    let morning = new DiffTimeHalfDayWorkTimezoneModel();
                    morning.amPmAtr(1);
                    let afternoon = new DiffTimeHalfDayWorkTimezoneModel();
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
                }

                updateData(data: DiffTimeWorkStampReflectTimezoneDto) {
                    this.stampReflectTimezone().forEach(function(item, index) {
                        item.updateData(data.stampReflectTimezone[index]);
                    })
                    this.isUpdateStartTime(data.updateStartTime);
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
                    this.stampReflectTimezone([]);
                }
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
                    //TODO
//                    self.unit.resetData();
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
                
                constructor() {
                    this.workTimeCode = ko.observable('');
                    this.restSet = new FixedWorkRestSetModel();
                    this.dayoffWorkTimezone = new DiffTimeDayOffWorkTimezoneModel();
                    this.commonSet = new WorkTimezoneCommonSetModel();
                    this.isUseHalfDayShift = ko.observable(false);
                    this.changeExtent = new EmTimezoneChangeExtentModel();
                    this.halfDayWorkTimezones = DiffTimeHalfDayWorkTimezoneModel.getDefaultData();
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

                public resetData(): void {
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
                    self.calculationSetting.resetData();
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
                    for (var dataModel of this.halfDayWorkTimezones) {
                        halfDayWorkTimezones.push(dataModel.toDto());
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