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
    import EmTimezoneChangeExtentDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.EmTimezoneChangeExtentDto;
    import DiffTimeWorkSettingDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeWorkSettingDto;

    import HDWorkTimeSheetSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.HDWorkTimeSheetSettingModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    import OverTimeOfTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OverTimeOfTimeZoneSetModel;
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import InstantRoundingModel = nts.uk.at.view.kmk003.a.viewmodel.common.InstantRoundingModel;
    import FixedWorkRestSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkRestSetModel;
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
    import TimeRangeModelConverter = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModelConverter;
    import TimeRangeModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModel;

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
            }


            export class DiffTimeDayOffWorkTimezoneModel {
                restTimezone: DiffTimeRestTimezoneModel;
                workTimezones: DayOffTimezoneSettingModel[];

                constructor() {
                    this.restTimezone = new DiffTimeRestTimezoneModel();
                    this.workTimezones = [];
                }

                updateData(data: DiffTimeDayOffWorkTimezoneDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.workTimezones = [];
                    for (var dataDTO of data.workTimezones) {
                        var dataModel: DayOffTimezoneSettingModel = new DayOffTimezoneSettingModel();
                        dataModel.updateData(dataDTO);
                        this.workTimezones.push(dataModel);
                    }
                }

                toDto(): DiffTimeDayOffWorkTimezoneDto {
                    var workTimezones: DayOffTimezoneSettingDto[] = [];
                    for (var dataModel of this.workTimezones) {
                        workTimezones.push(dataModel.toDto());
                    }
                    var dataDTO: DiffTimeDayOffWorkTimezoneDto = {
                        restTimezone: this.restTimezone.toDto(),
                        workTimezones: workTimezones,
                    };
                    return dataDTO;
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
                oTTimezones: KnockoutObservableArray<DiffTimeOTTimezoneSetModel>;

                constructor() {
                    this.employmentTimezones = ko.observableArray([]);
                    this.oTTimezones = ko.observableArray([]);
                }

                updateData(data: DiffTimezoneSettingDto) {
                    let self = this;
                    self.employmentTimezones(data.employmentTimezones.map(item => {
                        let m = new EmTimeZoneSetModel();
                        m.updateData(item);
                        return m;
                    }));

                    self.oTTimezones(data.oTTimezones.map(item => {
                        let m = new DiffTimeOTTimezoneSetModel();
                        m.updateData(item);
                        return m;
                    }));

                }
                
                updateOvertimeZone(lstOTTimezone: DiffTimeOTTimezoneSetDto[]) {
                    this.oTTimezones([]);
                    var dataModelTimezone: DiffTimeOTTimezoneSetModel[] = [];
                    for (var dataDTO of lstOTTimezone) {
                        var dataModel: DiffTimeOTTimezoneSetModel = new DiffTimeOTTimezoneSetModel();
                        dataModel.updateData(dataDTO);
                        dataModelTimezone.push(dataModel);
                    }
                    this.oTTimezones(dataModelTimezone);
                }

                toDto(): DiffTimezoneSettingDto {
                    let self = this;
                    let employmentTimezones = self.employmentTimezones().map(item => item.toDto());
                    let oTTimezones = self.oTTimezones().map(item => item.toDto());

                    var dataDTO: DiffTimezoneSettingDto = {
                        employmentTimezones: employmentTimezones,
                        oTTimezones: oTTimezones
                    };
                    return dataDTO;
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
            }

            export class DiffTimeWorkStampReflectTimezoneModel {
                stampReflectTimezone: KnockoutObservableArray<StampReflectTimezoneModel>;
                isUpdateStartTime: KnockoutObservable<boolean>;

                constructor() {
                    this.stampReflectTimezone = ko.observableArray([]);
                    this.isUpdateStartTime = ko.observable(false);
                }

                updateData(data: DiffTimeWorkStampReflectTimezoneDto) {
                    this.stampReflectTimezone.updateData(data.stampReflectTimezone);
                    this.isUpdateStartTime(data.isUpdateStartTime);
                }

                toDto(): DiffTimeWorkStampReflectTimezoneDto {
                    var lstStamp = [];
                    this.stampReflectTimezone().forEach(function(item, index) {
                        lstStamp.push(item.toDto());
                    });
                    var dataDTO: DiffTimeWorkStampReflectTimezoneDto = {
                        stampReflectTimezone: lstStamp,
                        isUpdateStartTime: this.isUpdateStartTime(),
                    };
                    return dataDTO;
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

                //TODO chua lam
                public resetData(): void {
                    let self = this;
                    self.aheadChange(0);
                    //self.unit.resetData();
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

                //TODO chua lam
                public resetData(): void {
                    let self = this;
                    //self.restSet.resetData();
                    //self.dayoffWorkTimezone.resetData();
                    //self.commonSet.resetData();
                    self.isUseHalfDayShift(false);
                    self.changeExtent.resetData();
                    //self.updateListHalfDay();
                    //self.stampReflectTimezone.resetData();
                    self.overtimeSetting(0);
                }

                updateData(data: DiffTimeWorkSettingDto) {
                    this.workTimeCode(data.workTimeCode);
                    this.restSet.updateData(data.restSet);
                    this.dayoffWorkTimezone.updateData(data.dayoffWorkTimezone);
                    this.commonSet.updateData(data.commonSet);
                    this.isUseHalfDayShift(data.isUseHalfDayShift);
                    this.changeExtent.updateData(data.changeExtent);
                    this.updateListHalfDay(data.halfDayWorkTimezones);
                    this.stampReflectTimezone.updateData(data.stampReflectTimezone);
                    this.overtimeSetting(data.overtimeSetting);
                }

                updateListHalfDay(lstHalfDayWorkTimezone: DiffTimeHalfDayWorkTimezoneDto[]): void {
                    lstHalfDayWorkTimezone.sort(item => item.amPmAtr);
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
                        isUseHalfDayShift: this.isUseHalfDayShift(),
                        changeExtent: this.changeExtent.toDto(),
                        halfDayWorkTimezones: halfDayWorkTimezones,
                        stampReflectTimezone: this.stampReflectTimezone.toDto(),
                        overtimeSetting: this.overtimeSetting()
                    };
                    return dataDTO;
                }
            }

        }
    }
}