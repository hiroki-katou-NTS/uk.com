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

    import TimeRoundingSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRoundingSettingModel;
    import FlowWorkRestTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestTimezoneModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import FlowWorkRestSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestSettingModel;
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
    import FixedTableDataConverter = nts.uk.at.view.kmk003.a.viewmodel.common.FixedTableDataConverter;
    
    import FlWorkSettingDto = nts.uk.at.view.kmk003.a.service.model.flowset.FlWorkSettingDto;
    export module viewmodel {

        export module flowset {

            export class FlowCalcSetModel {
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
                
                resetData() {
                    this.calcStartTimeSet(0);
                }
            }

            export class FlowTimeSettingModel {
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
                
                resetData() {
                    this.rounding.resetData();
                    this.elapsedTime(0);
                }
            }

            export class FlowOTTimezoneModel {
                worktimeNo: KnockoutObservable<number>;
                restrictTime: KnockoutObservable<boolean>;
                otFrameNo: KnockoutObservable<number>;
                flowTimeSetting: FlowTimeSettingModel;
                inLegalOTFrameNo: KnockoutObservable<number>;
                settlementOrder: KnockoutObservable<number>;

                constructor() {
                    this.worktimeNo = ko.observable(0);
                    this.restrictTime = ko.observable(false);
                    this.otFrameNo = ko.observable(0);
                    this.flowTimeSetting = new FlowTimeSettingModel();
                    this.inLegalOTFrameNo = ko.observable(0);
                    this.settlementOrder = ko.observable(0);
                }

                updateData(data: FlOTTimezoneDto) {
                    this.worktimeNo(data.worktimeNo);
                    this.restrictTime(data.restrictTime);
                    this.otFrameNo(data.otFrameNo);
                    this.flowTimeSetting.updateData(data.flowTimeSetting);
                    this.inLegalOTFrameNo(data.inLegalOTFrameNo);
                    this.settlementOrder(data.settlementOrder);
                }

                toDto(): FlOTTimezoneDto {
                    var dataDTO: FlOTTimezoneDto = {
                        worktimeNo: this.worktimeNo(),
                        restrictTime: this.restrictTime(),
                        otFrameNo: this.otFrameNo(),
                        flowTimeSetting: this.flowTimeSetting.toDto(),
                        inLegalOTFrameNo: this.inLegalOTFrameNo(),
                        settlementOrder: this.settlementOrder(),
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.worktimeNo(0);
                    this.restrictTime(false);
                    this.otFrameNo(0);
                    this.flowTimeSetting.resetData();
                    this.inLegalOTFrameNo(0);
                    this.settlementOrder(0);
                }
            }

            export class FixedTableFlowOTModel {
                worktimeNo: number;
                restrictTime: KnockoutObservable<boolean>;
                elapsedTime: KnockoutObservable<number>;
                rounding: KnockoutObservable<number>;
                roundingTime: KnockoutObservable<number>;
                otFrameNo: KnockoutObservable<number>;
                inLegalOTFrameNo: KnockoutObservable<number>;
                settlementOrder: KnockoutObservable<number>;

                constructor(flowOt: FlowOTTimezoneModel) {
                    this.worktimeNo = flowOt.worktimeNo();
                    this.restrictTime = ko.observable(flowOt.restrictTime());
                    this.elapsedTime = ko.observable(flowOt.flowTimeSetting.elapsedTime());
                    this.rounding = ko.observable(flowOt.flowTimeSetting.rounding.rounding());
                    this.roundingTime = ko.observable(flowOt.flowTimeSetting.rounding.roundingTime());
                    this.otFrameNo = ko.observable(flowOt.otFrameNo());
                    this.inLegalOTFrameNo = ko.observable(flowOt.inLegalOTFrameNo());
                    this.settlementOrder = ko.observable(flowOt.settlementOrder());
                }
            }

            export class FlowWorkTzSettingModel extends FixedTableDataConverter<FixedTableFlowOTModel, FlowOTTimezoneModel> {
                workTimeRounding: TimeRoundingSettingModel;
                lstOTTimezone: KnockoutObservableArray<FlowOTTimezoneModel>;

                constructor() {
                    super();
                    this.workTimeRounding = new TimeRoundingSettingModel();
                    this.lstOTTimezone = this.originalList;
                }

                optionalProcessing(list: Array<FixedTableFlowOTModel>): Array<FixedTableFlowOTModel> {
                    let count = 0;
                    return _.forEach(list, item => item.worktimeNo = ++count);
                }

                toOriginalDto(convertedItem: FixedTableFlowOTModel): FlOTTimezoneDto {
                    return {
                        worktimeNo: convertedItem.worktimeNo,
                        restrictTime: convertedItem.restrictTime ? convertedItem.restrictTime() : false,
                        otFrameNo: convertedItem.otFrameNo ? convertedItem.otFrameNo() : 1,
                        flowTimeSetting: {
                            elapsedTime: convertedItem.elapsedTime(),
                            rounding: {
                                roundingTime: convertedItem.roundingTime(),
                                rounding: convertedItem.rounding()
                            }
                        },
                        inLegalOTFrameNo: convertedItem.inLegalOTFrameNo ? convertedItem.inLegalOTFrameNo() : 1,
                        settlementOrder: convertedItem.settlementOrder ? convertedItem.settlementOrder() : 1
                    };
                }

                createOriginal(): FlowOTTimezoneModel {
                    return new FlowOTTimezoneModel();
                }

                createConverted(original: FlowOTTimezoneModel): FixedTableFlowOTModel {
                    return new FixedTableFlowOTModel(original);
                }

                updateData(data: FlWorkTzSettingDto) {
                    this.workTimeRounding.updateData(data.workTimeRounding);
                    this.updateTimezone(data.lstOTTimezone);
                }
                
                updateTimezone(lstOTTimezone: Array<FlOTTimezoneDto>) {
                    var dataModelTimezone: Array<FlowOTTimezoneModel> = [];
                    for (var dataDTO of lstOTTimezone) {
                        var dataModel: FlowOTTimezoneModel = new FlowOTTimezoneModel();
                        dataModel.updateData(dataDTO);
                        dataModelTimezone.push(dataModel);
                    }
                    this.lstOTTimezone(_.sortBy(dataModelTimezone, item => item.flowTimeSetting.elapsedTime()));
                }
                
                getTimezoneByWorkNo(worktimeNo: number) {
                    var self = this;
                    return _.find(self.lstOTTimezone(), timezone => timezone.worktimeNo() == worktimeNo);
                }

                toDto(): FlWorkTzSettingDto {
                    let lstOTTimezone: Array<FlOTTimezoneDto> = _.map(this.lstOTTimezone(), (dataModel) => dataModel.toDto());
                    
                    let dataDTO: FlWorkTzSettingDto = {
                        workTimeRounding: this.workTimeRounding.toDto(),
                        lstOTTimezone: lstOTTimezone
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.workTimeRounding.resetData();
                    this.lstOTTimezone([]);
                }
            }

            export class FlowHalfDayWorkTzModel {
                restTimezone: FlowWorkRestTimezoneModel;
                workTimeZone: FlowWorkTzSettingModel;

                constructor() {
                    this.restTimezone = new FlowWorkRestTimezoneModel();
                    this.workTimeZone = new FlowWorkTzSettingModel();
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
                
                resetData() {
                    this.restTimezone.resetData();
                    this.workTimeZone.resetData();
                }
            }

            export class FlowWorkHdTimeZoneModel {
                worktimeNo: KnockoutObservable<number>;
                useInLegalBreakRestrictTime: KnockoutObservable<boolean>;
                inLegalBreakFrameNo: KnockoutObservable<number>;
                useOutLegalBreakRestrictTime: KnockoutObservable<boolean>;
                outLegalBreakFrameNo: KnockoutObservable<number>;
                useOutLegalPubHolRestrictTime: KnockoutObservable<boolean>;
                outLegalPubHolFrameNo: KnockoutObservable<number>;
                flowTimeSetting: FlowTimeSettingModel;

                constructor() {
                    this.worktimeNo = ko.observable(0);
                    this.useInLegalBreakRestrictTime = ko.observable(false);
                    this.inLegalBreakFrameNo = ko.observable(0);
                    this.useOutLegalBreakRestrictTime = ko.observable(false);
                    this.outLegalBreakFrameNo = ko.observable(0);
                    this.useOutLegalPubHolRestrictTime = ko.observable(false);
                    this.outLegalPubHolFrameNo = ko.observable(0);
                    this.flowTimeSetting = new FlowTimeSettingModel();
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
                
                resetData() {
                    this.worktimeNo(0);
                    this.useInLegalBreakRestrictTime(false);
                    this.inLegalBreakFrameNo(0);
                    this.useOutLegalBreakRestrictTime(false);
                    this.outLegalBreakFrameNo(0);
                    this.useOutLegalPubHolRestrictTime(false);
                    this.outLegalPubHolFrameNo(0);
                    this.flowTimeSetting.resetData();
                }
            }

            export class FlowHdTimeZoneDuongModel {
                workTimeNo: number;
                elapsedTime: KnockoutObservable<number>;
                rounding: KnockoutObservable<number>;
                roundingTime: KnockoutObservable<number>;

                useInLegalBreakRestrictTime: KnockoutObservable<boolean>;
                inLegalBreakFrameNo: KnockoutObservable<number>;
                useOutLegalBreakRestrictTime: KnockoutObservable<boolean>;
                outLegalBreakFrameNo: KnockoutObservable<number>;
                useOutLegalPubHolRestrictTime: KnockoutObservable<boolean>;
                outLegalPubHolFrameNo: KnockoutObservable<number>;

                constructor(flowWorkHd: FlowWorkHdTimeZoneModel) {
                    let self = this;
                    self.workTimeNo = 0;
                    self.elapsedTime = ko.observable(flowWorkHd.flowTimeSetting.elapsedTime());
                    self.rounding = ko.observable(flowWorkHd.flowTimeSetting.rounding.rounding());
                    self.roundingTime = ko.observable(flowWorkHd.flowTimeSetting.rounding.roundingTime());

                    self.useInLegalBreakRestrictTime = ko.observable(flowWorkHd.useInLegalBreakRestrictTime());
                    self.inLegalBreakFrameNo = ko.observable(flowWorkHd.inLegalBreakFrameNo());
                    self.useOutLegalBreakRestrictTime = ko.observable(flowWorkHd.useOutLegalBreakRestrictTime());
                    self.outLegalBreakFrameNo = ko.observable(flowWorkHd.outLegalBreakFrameNo());
                    self.useOutLegalPubHolRestrictTime = ko.observable(flowWorkHd.useOutLegalPubHolRestrictTime());
                    self.outLegalPubHolFrameNo = ko.observable(flowWorkHd.outLegalPubHolFrameNo());
                }

            }

            export class FlowOffdayWorkTzModel extends FixedTableDataConverter<FlowHdTimeZoneDuongModel, FlowWorkHdTimeZoneModel> {
                restTimeZone: FlowWorkRestTimezoneModel;
                lstWorkTimezone: KnockoutObservableArray<FlowWorkHdTimeZoneModel>;

                constructor() {
                    super();
                    this.restTimeZone = new FlowWorkRestTimezoneModel();
                    this.lstWorkTimezone = this.originalList;
                }

                optionalProcessing(list: Array<FlowHdTimeZoneDuongModel>): Array<FlowHdTimeZoneDuongModel> {
                    let count = 0;
                    return _.forEach(list, item => item.workTimeNo = ++count);
                }

                toOriginalDto(convertedItem: FlowHdTimeZoneDuongModel): FlWorkHdTimeZoneDto {
                    return {
                        worktimeNo: convertedItem.workTimeNo,
                        useInLegalBreakRestrictTime: convertedItem.useInLegalBreakRestrictTime ? convertedItem.useInLegalBreakRestrictTime() : false,
                        inLegalBreakFrameNo: convertedItem.inLegalBreakFrameNo(),
                        useOutLegalBreakRestrictTime: convertedItem.useOutLegalBreakRestrictTime ? convertedItem.useOutLegalBreakRestrictTime() : false,
                        outLegalBreakFrameNo: convertedItem.outLegalBreakFrameNo(),
                        useOutLegalPubHolRestrictTime: convertedItem.useOutLegalPubHolRestrictTime ? convertedItem.useOutLegalPubHolRestrictTime() : false,
                        outLegalPubHolFrameNo: convertedItem.outLegalPubHolFrameNo(),
                        flowTimeSetting: {
                            rounding: {
                                roundingTime: convertedItem.roundingTime(),
                                rounding: convertedItem.rounding()
                            },
                            elapsedTime: convertedItem.elapsedTime()
                        }
                    };
                }

                createOriginal(): FlowWorkHdTimeZoneModel {
                    return new FlowWorkHdTimeZoneModel();
                }

                createConverted(original: FlowWorkHdTimeZoneModel): FlowHdTimeZoneDuongModel {
                    return new FlowHdTimeZoneDuongModel(original);
                }

                updateData(data: FlOffdayWorkTzDto) {
                    this.restTimeZone.updateData(data.restTimeZone);
                    this.updateHDTimezone(data.lstWorkTimezone);
                }
                
                updateHDTimezone(lstWorkTimezone: Array<FlWorkHdTimeZoneDto>) {                   
                    let dataModelWorktimezone: Array<FlowWorkHdTimeZoneModel> = [];
                    for (let dataDTO of lstWorkTimezone) {
                        let dataModel: FlowWorkHdTimeZoneModel = new FlowWorkHdTimeZoneModel();
                        dataModel.updateData(dataDTO);
                        dataModelWorktimezone.push(dataModel);
                    }
                    this.lstWorkTimezone(dataModelWorktimezone);
                }
                
                getHDTimezoneByWorktimeNo(worktimeNo: number) {
                    return _.find(this.lstWorkTimezone(), hdtimezone => hdtimezone.worktimeNo() == worktimeNo);
                }

                toDto(): FlOffdayWorkTzDto {
                    var lstWorkTimezone: Array<FlWorkHdTimeZoneDto> = [];
                    for (var dataModel of this.lstWorkTimezone()) {
                        lstWorkTimezone.push(dataModel.toDto());
                    }
                    var dataDTO: FlOffdayWorkTzDto = {
                        restTimeZone: this.restTimeZone.toDto(),
                        lstWorkTimezone: lstWorkTimezone
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.restTimeZone.resetData();
                    this.lstWorkTimezone([]);
                }
            }

            export class FlowOTSetModel {
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
                
                resetData() {
                    this.fixedChangeAtr(0);
                }
            }


            export class FlowStampReflectTzModel {
                twoTimesWorkReflectBasicTime: KnockoutObservable<number>;
                stampReflectTimezones: Array<StampReflectTimezoneModel>;

                constructor() {
                    this.twoTimesWorkReflectBasicTime = ko.observable(60);
                    this.stampReflectTimezones = [];
                    this.initStampSets();
                }

                initStampSets() {
                    let goWorkFlowStamp: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                    goWorkFlowStamp.workNo(1);
                    goWorkFlowStamp.classification(0);
                    let leaveWorkFlowStamp: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                    leaveWorkFlowStamp.workNo(1);
                    leaveWorkFlowStamp.classification(1);
                    this.stampReflectTimezones.push(goWorkFlowStamp);
                    this.stampReflectTimezones.push(leaveWorkFlowStamp);              
                }
                
                updateData(data: FlStampReflectTzDto) {
                    this.twoTimesWorkReflectBasicTime(data.twoTimesWorkReflectBasicTime);
                    
                    let self = this;
                    _.forEach(data.stampReflectTimezones, item => {
                        if (item.workNo == 1 && item.classification == 0) {
                            this.getGoWorkFlowStamp().updateData(item);
                        }                        
                        if (item.workNo == 1 && item.classification == 1) {
                            this.getLeaveWorkFlowStamp().updateData(item);
                        }
                    });
                }

                toDto(): FlStampReflectTzDto {
                    var stampReflectTimezones: Array<StampReflectTimezoneDto> = [];
                    for (var dataModel of this.stampReflectTimezones) {
                        stampReflectTimezones.push(dataModel.toDto());
                    }
                    var dataDTO: FlStampReflectTzDto = {
                        twoTimesWorkReflectBasicTime: this.twoTimesWorkReflectBasicTime(),
                        stampReflectTimezones: stampReflectTimezones
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.twoTimesWorkReflectBasicTime(60);
                    this.stampReflectTimezones.forEach(function(item, index) {
                        item.resetData();
                    });
                }              
                
                getGoWorkFlowStamp(): StampReflectTimezoneModel { let self = this; return _.find(self.stampReflectTimezones, p => p.workNo() == 1 && p.classification() == 0) }
                getLeaveWorkFlowStamp(): StampReflectTimezoneModel { let self = this; return _.find(self.stampReflectTimezones, p => p.workNo() == 1 && p.classification() == 1) }
            }

            export class FlowWorkDedSettingModel {
                overtimeSetting: FlowOTSetModel;
                calculateSetting: FlowCalcSetModel;

                constructor() {
                    this.overtimeSetting = new FlowOTSetModel();
                    this.calculateSetting = new FlowCalcSetModel();
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
                
                resetData() {
                    this.overtimeSetting.resetData();
                    this.calculateSetting.resetData();
                }
            }


            export class FlowWorkSettingModel {
                workingCode: KnockoutObservable<string>;
                restSetting: FlowWorkRestSettingModel;
                offdayWorkTimezone: FlowOffdayWorkTzModel;
                commonSetting: WorkTimezoneCommonSetModel;
                halfDayWorkTimezone: FlowHalfDayWorkTzModel;
                stampReflectTimezone: FlowStampReflectTzModel;
                designatedSetting: KnockoutObservable<number>;
                flowSetting: FlowWorkDedSettingModel;

                constructor() {
                    this.workingCode = ko.observable('');
                    this.restSetting = new FlowWorkRestSettingModel();
                    this.offdayWorkTimezone = new FlowOffdayWorkTzModel();
                    this.commonSetting = new WorkTimezoneCommonSetModel();
                    this.halfDayWorkTimezone = new FlowHalfDayWorkTzModel();
                    this.stampReflectTimezone = new FlowStampReflectTzModel();
                    this.designatedSetting = ko.observable(0);
                    this.flowSetting = new FlowWorkDedSettingModel();
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

                toDto(commonSetting: WorkTimezoneCommonSetModel): FlWorkSettingDto {
                    let dataDTO: FlWorkSettingDto = {
                        workingCode: this.workingCode(),         
                        restSetting: this.restSetting.toDto(),              
                        offdayWorkTimezone: this.offdayWorkTimezone.toDto(),
                        commonSetting: commonSetting.toDto(),
                        halfDayWorkTimezone: this.halfDayWorkTimezone.toDto(),
                        stampReflectTimezone: this.stampReflectTimezone.toDto(),
                        designatedSetting: this.designatedSetting(),
                        flowSetting: this.flowSetting.toDto()
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.restSetting.resetData();
                    this.offdayWorkTimezone.resetData();
                    this.commonSetting.resetData();
                    this.halfDayWorkTimezone.resetData();
                    this.stampReflectTimezone.resetData();
                    this.designatedSetting(0);
                    this.flowSetting.resetData();
                }
            }
            
        }
    }
}