module nts.uk.at.view.kmk003.a {
    import EmTimeZoneSetDto = service.model.common.EmTimeZoneSetDto;
    import OverTimeOfTimeZoneSetDto = service.model.common.OverTimeOfTimeZoneSetDto;
    import FlowWorkRestTimezoneDto = service.model.common.FlowWorkRestTimezoneDto;
    import HDWorkTimeSheetSettingDto = service.model.common.HDWorkTimeSheetSettingDto;
    import StampReflectTimezoneDto = service.model.common.StampReflectTimezoneDto;
    import FixedWorkTimezoneSetDto = service.model.common.FixedWorkTimezoneSetDto;

    import TimeSheetDto = service.model.flexset.TimeSheetDto;
    import CoreTimeSettingDto = service.model.flexset.CoreTimeSettingDto;
    import FlexHalfDayWorkTimeDto = service.model.flexset.FlexHalfDayWorkTimeDto;
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
    import FixedWorkTimezoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkTimezoneSetModel;

    export module viewmodel {
        export module flexset {
            
            export class TimeSheetModel {
                startTime: KnockoutObservable<number>;
                endTime: KnockoutObservable<number>;

                constructor() {
                    this.startTime = ko.observable(0);
                    this.endTime = ko.observable(0);
                }

                public resetData(): void {
                    let self = this;
                    self.startTime(0);
                    self.endTime(0);
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

                public resetData(): void {
                    let self = this;
                    self.coreTimeSheet.resetData();
                    self.timesheet(0);
                    self.minWorkTime(0);
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

            export class FlexHalfDayWorkTimeModel {
                restTimezone: FlowWorkRestTimezoneModel;
                workTimezone: FixedWorkTimezoneSetModel;
                ampmAtr: KnockoutObservable<number>;

                constructor() {
                    this.restTimezone = new FlowWorkRestTimezoneModel();
                    this.workTimezone = new FixedWorkTimezoneSetModel();
                    this.ampmAtr = ko.observable(0);
                }

                static getDefaultData(): Array<FlexHalfDayWorkTimeModel> {
                    let oneday = new FlexHalfDayWorkTimeModel();
                    oneday.ampmAtr(0);
                    let morning = new FlexHalfDayWorkTimeModel();
                    morning.ampmAtr(1);
                    let afternoon = new FlexHalfDayWorkTimeModel();
                    afternoon.ampmAtr(2);
                    let list: any[] = [];
                    list.push(oneday);
                    list.push(morning);
                    list.push(afternoon);
                    return list;
                }

                updateData(data: FlexHalfDayWorkTimeDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.workTimezone.updateData(data.workTimezone);
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

                public resetData(): void {
                    let self = this;
                    self.removeFromWorkTime(0);
                    self.calculateSharing(0);
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

                public resetData(): void {
                    let self = this;
                    //TODO: reset list
                    self.restTimezone.resetData();
                } 

                updateData(data: FlexOffdayWorkTimeDto) {
                    this.updateHDTimezone(data.lstWorkTimezone);
                    this.restTimezone.updateData(data.restTimezone);
                }

                updateHDTimezone(lstWorkTimezone: HDWorkTimeSheetSettingDto[]) {
                    for (var dataDTO of lstWorkTimezone) {
                        var dataModel: HDWorkTimeSheetSettingModel = this.getHDTimezoneByWorktimeNo(dataDTO.workTimeNo);
                        if (dataModel) {
                            dataModel.updateData(dataDTO);
                        }
                        else {
                            dataModel = new HDWorkTimeSheetSettingModel();
                            dataModel.updateData(dataDTO);
                            this.lstWorkTimezone.push(dataModel);
                        }
                    }
                }

                getHDTimezoneByWorktimeNo(worktimeNo: number) : HDWorkTimeSheetSettingModel{
                    return _.find(this.lstWorkTimezone, hdtimezone => hdtimezone.workTimeNo() == worktimeNo);
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
                calculateSetting: FlexCalcSettingModel;
                
                constructor() {
                    var self = this;
                    self.workTimeCode = ko.observable('');
                    self.useHalfDayShift = ko.observable(false);
                    self.coreTimeSetting = new CoreTimeSettingModel();
                    self.restSetting = new FlowWorkRestSettingModel();
                    self.offdayWorkTime = new FlexOffdayWorkTimeModel();
                    self.commonSetting = new WorkTimezoneCommonSetModel();
                    self.lstHalfDayWorkTimezone = FlexHalfDayWorkTimeModel.getDefaultData();
                    self.lstStampReflectTimezone = [];
                    self.calculateSetting = new FlexCalcSettingModel();
                }

                public resetData(): void {
                    let self = this;
                    self.workTimeCode('0');
                    self.useHalfDayShift(false);
                    self.coreTimeSetting.resetData();
                    self.restSetting.resetData();
                    self.offdayWorkTime.resetData();
                    self.commonSetting.resetData();
                    //TODO 2 cai list
                    self.calculateSetting.resetData();
                }

                public getHDWtzOneday(): FlexHalfDayWorkTimeModel {
                    let self = this;
                    return _.find(self.lstHalfDayWorkTimezone, time => time.ampmAtr() == 0);
                }
                
                public getHDWtzMorning(): FlexHalfDayWorkTimeModel {
                    let self = this;
                    return _.find(self.lstHalfDayWorkTimezone, time => time.ampmAtr() == 1);
                }
                
                public getHDWtzAfternoon(): FlexHalfDayWorkTimeModel {
                    let self = this;
                    return _.find(self.lstHalfDayWorkTimezone, time => time.ampmAtr() == 2);
                }

                updateListHalfDay(lstHalfDayWorkTimezone: FlexHalfDayWorkTimeDto[]): void {
                    lstHalfDayWorkTimezone.sort(item => item.ampmAtr);
                    this.getHDWtzOneday().updateData(lstHalfDayWorkTimezone[0]);
                    this.getHDWtzMorning().updateData(lstHalfDayWorkTimezone[1]);
                    this.getHDWtzAfternoon().updateData(lstHalfDayWorkTimezone[2]);
                }

                updateData(data: FlexWorkSettingDto) {
                    this.workTimeCode(data.workTimeCode);
                    this.useHalfDayShift(data.useHalfDayShift);
                    this.coreTimeSetting.updateData(data.coreTimeSetting);
                    this.restSetting.updateData(data.restSetting);
                    this.offdayWorkTime.updateData(data.offdayWorkTime);
                    this.commonSetting.updateData(data.commonSetting);
                    this.updateListHalfDay(data.lstHalfDayWorkTimezone);
                    this.lstStampReflectTimezone = [];
                    for (var dataStampDTO of data.lstStampReflectTimezone) {
                        var dataStampModel: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                        dataStampModel.updateData(dataStampDTO);
                        this.lstStampReflectTimezone.push(dataStampModel);
                    }
                    this.calculateSetting.updateData(data.calculateSetting);
                }
                
                toDto(): FlexWorkSettingDto{
                    var lstHalfDayWorkTimezone: FlexHalfDayWorkTimeDto[] = [];
                    for(var dataModelTimezone of this.lstHalfDayWorkTimezone){
                        lstHalfDayWorkTimezone.push(dataModelTimezone.toDto());
                    }
                    var lstStampReflectTimezone: StampReflectTimezoneDto[] = [];
                    for(var dataModelStamp of this.lstStampReflectTimezone){
                        lstStampReflectTimezone.push(dataModelStamp.toDto());
                    }
                    var dataDTO: FlexWorkSettingDto = {
                        workTimeCode: this.workTimeCode(),
                        useHalfDayShift: this.useHalfDayShift(),
                        coreTimeSetting: this.coreTimeSetting.toDto(),
                        restSetting: this.restSetting.toDto(),
                        offdayWorkTime: this.offdayWorkTime.toDto(),
                        commonSetting: this.commonSetting.toDto(),
                        lstHalfDayWorkTimezone: lstHalfDayWorkTimezone,
                        lstStampReflectTimezone: lstStampReflectTimezone,
                        calculateSetting: this.calculateSetting.toDto()
                    };
                    return dataDTO;    
                }
            }
            
        }
    }
}