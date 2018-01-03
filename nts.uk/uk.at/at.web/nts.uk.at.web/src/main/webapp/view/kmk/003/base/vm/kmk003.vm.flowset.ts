module nts.uk.at.view.kmk003.a {

    import StampReflectTimezoneDto = nts.uk.at.view.kmk003.a.service.model.common.StampReflectTimezoneDto;

    import FlCalcSetDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlCalcSetDto;
    import FlTimeSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlTimeSettingDto;
    import FlOTTimezoneDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlOTTimezoneDto;
    import FlWorkTzSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlWorkTzSettingDto;
    import FlHalfDayWorkTzDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlHalfDayWorkTzDto;
    import FlWorkHdTimeZoneDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlWorkHdTimeZoneDto;
    import FlOffdayWorkTzDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlOffdayWorkTzDto;
    import FlOTSetDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlOTSetDto;
    import FlStampReflectTzDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlStampReflectTzDto;
    import FlWorkDedSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlWorkDedSettingDto;
    import FlWorkSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlWorkSettingDto;

    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    import FlowWorkRestTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestTimezoneModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import FlowWorkRestSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestSettingModel;
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;

    export module viewmodel {

        export module flowset {

            export class FlCalcSetModel {
                calcStartTimeSet: KnockoutObservable<number>;

                constructor() {
                    this.calcStartTimeSet = ko.observable(0);
                }

                updateData(data: FlCalcSetDto) {
                    this.calcStartTimeSet(data.calcStartTimeSet);
                }

                toDto(): FlCalcSetDto {
                    var dataDTO: FlCalcSetDto = {
                        calcStartTimeSet: this.calcStartTimeSet()
                    };
                    return dataDTO;
                }
            }

            export class FlTimeSettingModel {
                rounding: TimeRoundingSettingModel;
                elapsedTime: KnockoutObservable<number>;

                constructor() {
                    this.rounding = new TimeRoundingSettingModel();
                    this.elapsedTime = ko.observable(0);
                }

                updateData(data: FlTimeSettingDto) {
                    this.rounding.updateData(data.rounding);
                    this.elapsedTime(data.elapsedTime);
                }

                toDto(): FlTimeSettingDto {
                    var dataDTO: FlTimeSettingDto = {
                        rounding: this.rounding.toDto(),
                        elapsedTime: this.elapsedTime()
                    };
                    return dataDTO;
                }
            }

            export class FlOTTimezoneModel {
                worktimeNo: KnockoutObservable<number>;
                restrictTime: KnockoutObservable<boolean>;
                overtimeFrameNo: KnockoutObservable<number>;
                flowTimeSetting: FlTimeSettingModel;
                inLegalOTFrameNo: KnockoutObservable<number>;
                settlementOrder: KnockoutObservable<number>;

                constructor() {
                    this.worktimeNo = ko.observable(0);
                    this.restrictTime = ko.observable(false);
                    this.overtimeFrameNo = ko.observable(0);
                    this.flowTimeSetting = new FlTimeSettingModel();
                    this.inLegalOTFrameNo = ko.observable(0);
                    this.settlementOrder = ko.observable(0);
                }

                updateData(data: FlOTTimezoneDto) {
                    this.worktimeNo(data.worktimeNo);
                    this.restrictTime(data.restrictTime);
                    this.overtimeFrameNo(data.overtimeFrameNo);
                    this.flowTimeSetting.updateData(data.flowTimeSetting);
                    this.inLegalOTFrameNo(data.inLegalOTFrameNo);
                    this.settlementOrder(data.settlementOrder);
                }

                toDto(): FlOTTimezoneDto {
                    var dataDTO: FlOTTimezoneDto = {
                        worktimeNo: this.worktimeNo(),
                        restrictTime: this.restrictTime(),
                        overtimeFrameNo: this.overtimeFrameNo(),
                        flowTimeSetting: this.flowTimeSetting.toDto(),
                        inLegalOTFrameNo: this.inLegalOTFrameNo(),
                        settlementOrder: this.settlementOrder(),
                    };
                    return dataDTO;
                }
            }

            export class FlWorkTzSettingModel {
                workTimeRounding: TimeRoundingSettingModel;
                lstOTTimezone: FlOTTimezoneModel[];

                constructor() {
                    this.workTimeRounding = new TimeRoundingSettingModel();
                    this.lstOTTimezone = [];
                }

                updateData(data: FlWorkTzSettingDto) {
                    this.workTimeRounding.updateData(data.workTimeRounding);
                    this.updateTimezone(data.lstOTTimezone);
                }
                
                updateTimezone(lstOTTimezone: FlOTTimezoneDto[]) {
                    for (var dataDTO of lstOTTimezone) {
                        var dataModel: FlOTTimezoneModel = this.getTimezoneByWorkNo(dataDTO.worktimeNo);
                        if (dataModel) {
                            dataModel.updateData(dataDTO);
                        }
                        else {
                            dataModel = new FlOTTimezoneModel();
                            dataModel.updateData(dataDTO);
                            this.lstOTTimezone.push(dataModel);
                        }
                    }
                }
                
                getTimezoneByWorkNo(worktimeNo: number) {
                    var self = this;
                    return _.find(self.lstOTTimezone, timezone => timezone.worktimeNo() == worktimeNo);
                }

                toDto(): FlWorkTzSettingDto {

                    var lstOTTimezone: FlOTTimezoneDto[] = [];
                    for (var dataModel of this.lstOTTimezone) {
                        lstOTTimezone.push(dataModel.toDto());
                    }
                    var dataDTO: FlWorkTzSettingDto = {
                        workTimeRounding: this.workTimeRounding.toDto(),
                        lstOTTimezone: lstOTTimezone
                    };
                    return dataDTO;
                }
            }

            export class FlHalfDayWorkTzModel {
                restTimezone: FlowWorkRestTimezoneModel;
                workTimeZone: FlWorkTzSettingModel;

                constructor() {
                    this.restTimezone = new FlowWorkRestTimezoneModel();
                    this.workTimeZone = new FlWorkTzSettingModel();
                }

                updateData(data: FlHalfDayWorkTzDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.workTimeZone.updateData(data.workTimeZone);
                }

                toDto(): FlHalfDayWorkTzDto {
                    var dataDTO: FlHalfDayWorkTzDto = {
                        restTimezone: this.restTimezone.toDto(),
                        workTimeZone: this.workTimeZone.toDto()
                    };
                    return dataDTO;
                }
            }

            export class FlWorkHdTimeZoneModel {
                worktimeNo: KnockoutObservable<number>;
                useInLegalBreakRestrictTime: KnockoutObservable<boolean>;
                inLegalBreakFrameNo: KnockoutObservable<number>;
                useOutLegalBreakRestrictTime: KnockoutObservable<boolean>;
                outLegalBreakFrameNo: KnockoutObservable<number>;
                useOutLegalPubHolRestrictTime: KnockoutObservable<boolean>;
                outLegalPubHolFrameNo: KnockoutObservable<number>;
                flowTimeSetting: FlTimeSettingModel;

                constructor() {
                    this.worktimeNo = ko.observable(0);
                    this.useInLegalBreakRestrictTime = ko.observable(false);
                    this.inLegalBreakFrameNo = ko.observable(0);
                    this.useOutLegalBreakRestrictTime = ko.observable(false);
                    this.outLegalBreakFrameNo = ko.observable(0);
                    this.useOutLegalPubHolRestrictTime = ko.observable(false);
                    this.outLegalPubHolFrameNo = ko.observable(0);
                    this.flowTimeSetting = new FlTimeSettingModel();
                }

                updateData(data: FlWorkHdTimeZoneDto) {
                    this.worktimeNo(data.worktimeNo);
                    this.useInLegalBreakRestrictTime(data.useInLegalBreakRestrictTime);
                    this.inLegalBreakFrameNo(data.inLegalBreakFrameNo);
                    this.useOutLegalBreakRestrictTime(data.useOutLegalBreakRestrictTime);
                    this.outLegalBreakFrameNo(data.outLegalBreakFrameNo);
                    this.useOutLegalPubHolRestrictTime(data.useOutLegalPubHolRestrictTime);
                    this.outLegalPubHolFrameNo(data.outLegalPubHolFrameNo);
                    this.flowTimeSetting.updateData(data.flowTimeSetting);
                }

                toDto(): FlWorkHdTimeZoneDto {
                    var dataDTO: FlWorkHdTimeZoneDto = {
                        worktimeNo: this.worktimeNo(),
                        useInLegalBreakRestrictTime: this.useInLegalBreakRestrictTime(),
                        inLegalBreakFrameNo: this.inLegalBreakFrameNo(),
                        useOutLegalBreakRestrictTime: this.useOutLegalBreakRestrictTime(),
                        outLegalBreakFrameNo: this.outLegalBreakFrameNo(),
                        useOutLegalPubHolRestrictTime: this.useOutLegalPubHolRestrictTime(),
                        outLegalPubHolFrameNo: this.outLegalPubHolFrameNo(),
                        flowTimeSetting: this.flowTimeSetting.toDto(),
                    };
                    return dataDTO;
                }
            }

            export class FlOffdayWorkTzModel {
                restTimeZone: FlowWorkRestTimezoneModel;
                lstWorkTimezone: FlWorkHdTimeZoneModel[];

                constructor() {
                    this.restTimeZone = new FlowWorkRestTimezoneModel();
                    this.lstWorkTimezone = [];
                }

                updateData(data: FlOffdayWorkTzDto) {
                    this.restTimeZone.updateData(data.restTimeZone);
                    this.updateHDTimezone(data.lstWorkTimezone);
                }
                
                updateHDTimezone(lstWorkTimezone: FlWorkHdTimeZoneDto[]) {
                    for (var dataDTO of lstWorkTimezone) {
                        var dataModel: FlWorkHdTimeZoneModel = this.getHDTimezoneByWorktimeNo(dataDTO.worktimeNo);
                        if (dataModel) {
                            dataModel.updateData(dataDTO);
                        }
                        else {
                            dataModel = new FlWorkHdTimeZoneModel();
                            dataModel.updateData(dataDTO);
                            this.lstWorkTimezone.push(dataModel);
                        }
                    }
                }
                
                getHDTimezoneByWorktimeNo(worktimeNo: number) {
                    return _.find(this.lstWorkTimezone, hdtimezone => hdtimezone.worktimeNo() == worktimeNo);
                }

                toDto(): FlOffdayWorkTzDto {
                    var lstWorkTimezone: FlWorkHdTimeZoneDto[] = [];
                    for (var dataModel of this.lstWorkTimezone) {
                        lstWorkTimezone.push(dataModel.toDto());
                    }
                    var dataDTO: FlOffdayWorkTzDto = {
                        restTimeZone: this.restTimeZone.toDto(),
                        lstWorkTimezone: lstWorkTimezone
                    };
                    return dataDTO;
                }
            }

            export class FlOTSetModel {
                fixedChangeAtr: KnockoutObservable<number>;

                constructor() {
                    this.fixedChangeAtr = ko.observable(0);
                }

                updateData(data: FlOTSetDto) {
                    this.fixedChangeAtr(data.fixedChangeAtr);
                }

                toDto(): FlOTSetDto {
                    var dataDTO: FlOTSetDto = {
                        fixedChangeAtr: this.fixedChangeAtr()
                    };
                    return dataDTO;
                }
            }


            export class FlStampReflectTzModel {
                twoTimesWorkReflectBasicTime: KnockoutObservable<number>;
                stampReflectTimezones: StampReflectTimezoneModel[];

                constructor() {
                    this.twoTimesWorkReflectBasicTime = ko.observable(0);
                    this.stampReflectTimezones = [];
                }

                updateData(data: FlStampReflectTzDto) {
                    this.twoTimesWorkReflectBasicTime(data.twoTimesWorkReflectBasicTime);
                    this.stampReflectTimezones = []
                    for (var dataDTO of data.stampReflectTimezones) {
                        var dataModel: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                        dataModel.updateData(dataDTO);
                        this.stampReflectTimezones.push(dataModel);
                    }
                }

                toDto(): FlStampReflectTzDto {
                    var stampReflectTimezones: StampReflectTimezoneDto[] = [];
                    for (var dataModel of this.stampReflectTimezones) {
                        stampReflectTimezones.push(dataModel.toDto());
                    }
                    var dataDTO: FlStampReflectTzDto = {
                        twoTimesWorkReflectBasicTime: this.twoTimesWorkReflectBasicTime(),
                        stampReflectTimezones: stampReflectTimezones
                    };
                    return dataDTO;
                }
            }

            export class FlWorkDedSettingModel {
                overtimeSetting: FlOTSetModel;
                calculateSetting: FlCalcSetModel;

                constructor() {
                    this.overtimeSetting = new FlOTSetModel();
                    this.calculateSetting = new FlCalcSetModel();
                }

                updateData(data: FlWorkDedSettingDto) {
                    this.overtimeSetting.updateData(data.overtimeSetting);
                    this.calculateSetting.updateData(data.calculateSetting);
                }

                toDto(): FlWorkDedSettingDto {
                    var dataDTO: FlWorkDedSettingDto = {
                        overtimeSetting: this.overtimeSetting.toDto(),
                        calculateSetting: this.calculateSetting.toDto(),
                    };
                    return dataDTO;
                }
            }


            export class FlWorkSettingModel {
                workingCode: KnockoutObservable<string>;
                restSetting: FlowWorkRestSettingModel;
                offdayWorkTimezone: FlOffdayWorkTzModel;
                commonSetting: WorkTimezoneCommonSetModel;
                halfDayWorkTimezone: FlHalfDayWorkTzModel;
                stampReflectTimezone: FlStampReflectTzModel;
                designatedSetting: KnockoutObservable<number>;
                flowSetting: FlWorkDedSettingModel;

                constructor() {
                    this.workingCode = ko.observable('');
                    this.restSetting = new FlowWorkRestSettingModel();
                    this.offdayWorkTimezone = new FlOffdayWorkTzModel();
                    this.commonSetting = new WorkTimezoneCommonSetModel();
                    this.halfDayWorkTimezone = new FlHalfDayWorkTzModel();
                    this.stampReflectTimezone = new FlStampReflectTzModel();
                    this.designatedSetting = ko.observable(0);
                    this.flowSetting = new FlWorkDedSettingModel();
                }

                updateData(data: FlWorkSettingDto) {
                    this.workingCode(data.workingCode);
                    this.restSetting.updateData(data.restSetting);
                    this.offdayWorkTimezone.updateData(data.offdayWorkTimezone);
                    this.commonSetting.updateData(data.commonSetting);
                    this.halfDayWorkTimezone.updateData(data.halfDayWorkTimezone);
                    this.stampReflectTimezone.updateData(data.stampReflectTimezone);
                    this.designatedSetting(data.designatedSetting);
                    this.flowSetting.updateData(data.flowSetting);
                }

                toDto(): FlWorkSettingDto {
                    var dataDTO: FlWorkSettingDto = {
                        workingCode: this.workingCode(),
                        restSetting: this.restSetting.toDto(),
                        offdayWorkTimezone: this.offdayWorkTimezone.toDto(),
                        commonSetting: this.commonSetting.toDto(),
                        halfDayWorkTimezone: this.halfDayWorkTimezone.toDto(),
                        stampReflectTimezone: this.stampReflectTimezone.toDto(),
                        designatedSetting: this.designatedSetting(),
                        flowSetting: this.flowSetting.toDto()
                    };
                    return dataDTO;
                }
            }

        }
    }
}