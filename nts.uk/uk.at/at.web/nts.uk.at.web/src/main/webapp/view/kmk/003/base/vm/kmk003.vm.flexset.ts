module nts.uk.at.view.kmk003.a {
    import EmTimeZoneSetDto = service.model.common.EmTimeZoneSetDto;
    import OverTimeOfTimeZoneSetDto = service.model.common.OverTimeOfTimeZoneSetDto;
    import FlowWorkRestTimezoneDto = service.model.common.FlowWorkRestTimezoneDto;
    import HDWorkTimeSheetSettingDto = service.model.common.HDWorkTimeSheetSettingDto;

    import TimeSheetDto = service.model.flexset.TimeSheetDto;
    import CoreTimeSettingDto = service.model.flexset.CoreTimeSettingDto;
    import FlexHalfDayWorkTimeDto = service.model.flexset.FlexHalfDayWorkTimeDto;
    import FixedWorkTimezoneSetDto = service.model.flexset.FixedWorkTimezoneSetDto;
    import FlexCalcSettingDto = service.model.flexset.FlexCalcSettingDto;
    import FlexOffdayWorkTimeDto = service.model.flexset.FlexOffdayWorkTimeDto;
    import FlexWorkSettingDto = service.model.flexset.FlexWorkSettingDto;

    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import OverTimeOfTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OverTimeOfTimeZoneSetModel;
    import FlowWorkRestTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestTimezoneModel;
    import HDWorkTimeSheetSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.HDWorkTimeSheetSettingModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import FlowWorkRestSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestSettingModel;
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;

    export module viewmodel {
        export module flexset {
            
            export class TimeSheetModel {
                startTime: KnockoutObservable<number>;
                endTime: KnockoutObservable<number>;

                constructor() {
                    this.startTime = ko.observable(0);
                    this.endTime = ko.observable(0);
                }

                updateData(data: TimeSheetDto) {
                    this.startTime(data.startTime);
                    this.endTime(data.endTime);
                }
                toDto(): TimeSheetDto {
                    var dataDTO: TimeSheetDto = {
                        startTime: this.startTime(),
                        endTime: this.endTime(),
                    };
                    return dataDTO;
                }
            }

            export class CoreTimeSettingModel {
                coreTimeSheet: TimeSheetModel;
                timesheet: KnockoutObservable<number>;
                minWorkTime: KnockoutObservable<number>;

                constructor() {
                    this.coreTimeSheet = new TimeSheetModel();
                    this.timesheet = ko.observable(0);
                    this.minWorkTime = ko.observable(0);
                }

                updateData(data: CoreTimeSettingDto) {
                    this.coreTimeSheet.updateData(data.coreTimeSheet);
                    this.timesheet(data.timesheet);
                    this.minWorkTime(data.minWorkTime);
                }

                toDto(): CoreTimeSettingDto {
                    var dataDTO: CoreTimeSettingDto = {
                        coreTimeSheet: this.coreTimeSheet.toDto(),
                        timesheet: this.timesheet(),
                        minWorkTime: this.minWorkTime()
                    };
                    return dataDTO;
                }
            }

            export class FixedWorkTimezoneSetModel {
                lstWorkingTimezone: EmTimeZoneSetModel[];
                lstOTTimezone: OverTimeOfTimeZoneSetModel[];

                constructor() {
                    this.lstWorkingTimezone = [];
                    this.lstOTTimezone = [];
                }

                updataData(data: FixedWorkTimezoneSetDto) {
                    this.lstWorkingTimezone = [];
                    for (var dataTimezoneDTO of data.lstWorkingTimezone) {
                        var dataTimezoneModel: EmTimeZoneSetModel = new EmTimeZoneSetModel();
                        dataTimezoneModel.updateData(dataTimezoneDTO);
                        this.lstWorkingTimezone.push(dataTimezoneModel);
                    }

                    this.lstOTTimezone = [];
                    for (var dataOvertimeDTO of data.lstOTTimezone) {
                        var dataOvertimeModel: OverTimeOfTimeZoneSetModel = new OverTimeOfTimeZoneSetModel();
                        dataOvertimeModel.updateData(dataOvertimeDTO);
                        this.lstOTTimezone.push(dataOvertimeModel);
                    }
                }

                toDto(): FixedWorkTimezoneSetDto {

                    var lstWorkingTimezone: EmTimeZoneSetDto[] = [];
                    for (var dataTimezoneModel of this.lstWorkingTimezone) {
                        lstWorkingTimezone.push(dataTimezoneModel.toDto());
                    }

                    var lstOTTimezone: OverTimeOfTimeZoneSetDto[] = [];
                    for (var dataOvertimeModel of this.lstOTTimezone) {
                        lstOTTimezone.push(dataOvertimeModel.toDto());
                    }
                    var dataDTO: FixedWorkTimezoneSetDto = {
                        lstWorkingTimezone: lstWorkingTimezone,
                        lstOTTimezone: lstOTTimezone
                    };
                    return dataDTO;
                }
            }

            export class FlexHalfDayWorkTimeModel {
                restTimezone: FlowWorkRestTimezoneModel;
                workTimezone: FixedWorkTimezoneSetModel;
                ampmAtr: KnockoutObservable<number>;

                constructor() {
                    this.restTimezone = new FlowWorkRestTimezoneModel();
                    this.workTimezone = new FixedWorkTimezoneSetModel();
                    this.ampmAtr = ko.observable(0);
                }

                updateData(data: FlexHalfDayWorkTimeDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.workTimezone.updataData(data.workTimezone);
                    this.ampmAtr(data.ampmAtr);
                }

                toDto(): FlexHalfDayWorkTimeDto {
                    var dataDTO: FlexHalfDayWorkTimeDto = {
                        restTimezone: this.restTimezone.toDto(),
                        workTimezone: this.workTimezone.toDto(),
                        ampmAtr: this.ampmAtr()
                    };
                    return dataDTO;
                }
            }

            export class FlexCalcSettingModel {
                removeFromWorkTime: KnockoutObservable<number>;
                calculateSharing: KnockoutObservable<number>;

                constructor() {
                    this.removeFromWorkTime = ko.observable(0);
                    this.calculateSharing = ko.observable(0);
                }

                updateData(data: FlexCalcSettingDto) {
                    this.removeFromWorkTime(data.removeFromWorkTime);
                    this.calculateSharing(data.calculateSharing);
                }

                toDto(): FlexCalcSettingDto {
                    var dataDTO: FlexCalcSettingDto = {
                        removeFromWorkTime: this.removeFromWorkTime(),
                        calculateSharing: this.calculateSharing()
                    };
                    return dataDTO;
                }
            }

            export class FlexOffdayWorkTimeModel {
                lstWorkTimezone: HDWorkTimeSheetSettingModel[];
                restTimezone: FlowWorkRestTimezoneModel;

                constructor() {
                    this.lstWorkTimezone = [];
                    this.restTimezone = new FlowWorkRestTimezoneModel();
                }

                updateData(data: FlexOffdayWorkTimeDto) {
                    this.lstWorkTimezone = [];
                    for (var dataDTO of data.lstWorkTimezone) {
                        var dataModel: HDWorkTimeSheetSettingModel = new HDWorkTimeSheetSettingModel();
                        dataModel.updateData(dataDTO);
                        this.lstWorkTimezone.push(dataModel);
                    }
                    this.restTimezone.updateData(data.restTimezone);
                }

                toDto(): FlexOffdayWorkTimeDto {
                    var lstWorkTimezone: HDWorkTimeSheetSettingDto[] = [];
                    for (var dataModel of this.lstWorkTimezone) {
                        lstWorkTimezone.push(dataModel.toDto());
                    }
                    var dataDTO: FlexOffdayWorkTimeDto = {
                        lstWorkTimezone: lstWorkTimezone,
                        restTimezone: this.restTimezone.toDto()
                    };
                    return dataDTO;
                }
            }

            export class FlexWorkSettingModel {
                workTimeCode: KnockoutObservable<string>;
                useHalfDayShift: KnockoutObservable<boolean>;
                coreTimeSetting: CoreTimeSettingModel;
                restSetting: FlowWorkRestSettingModel;
                offdayWorkTime: FlexOffdayWorkTimeModel;
                commonSetting: WorkTimezoneCommonSetModel;
                lstHalfDayWorkTimezone: FlexHalfDayWorkTimeModel[];
                lstStampReflectTimezone: StampReflectTimezoneModel[];
                calculateSetting: FlexCalcSettingDto;
                constructor() {
                    var self = this;
                    self.workTimeCode = ko.observable('');
                    self.useHalfDayShift = ko.observable(false);
                    self.coreTimeSetting = new CoreTimeSettingModel();
                    self.restSetting = new FlowWorkRestSettingModel();
                    self.commonSetting = new WorkTimezoneCommonSetModel();
                    self.lstHalfDayWorkTimezone = [];
                    this.lstStampReflectTimezone = [];
                }

                updateData(data: FlexWorkSettingDto) {
                    this.workTimeCode(data.workTimeCode);
                    this.useHalfDayShift(data.useHalfDayShift);
                    this.coreTimeSetting.updateData(data.coreTimeSetting);
                    this.restSetting.updateData(data.restSetting);
                    this.commonSetting.updateData(data.commonSetting);
                    this.lstHalfDayWorkTimezone = [];
                    for (var dataDTO of data.lstHalfDayWorkTimezone) {
                        var dataModel: FlexHalfDayWorkTimeModel = new FlexHalfDayWorkTimeModel();
                        dataModel.updateData(dataDTO);
                        this.lstHalfDayWorkTimezone.push(dataModel);
                    }
                    this.lstStampReflectTimezone = [];
                    for (var dataStampDTO of data.lstStampReflectTimezone) {
                        var dataStampModel: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                        dataStampModel.updateData(dataStampDTO);
                        this.lstStampReflectTimezone.push(dataStampModel);
                    }
                }
            }
            
        }
    }
}