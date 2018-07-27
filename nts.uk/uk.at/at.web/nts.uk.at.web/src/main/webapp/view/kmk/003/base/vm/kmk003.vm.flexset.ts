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

    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import OverTimeOfTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OverTimeOfTimeZoneSetModel;
    import FlowWorkRestTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestTimezoneModel;
    import HDWorkTimeSheetSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.HDWorkTimeSheetSettingModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import FlowWorkRestSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestSettingModel;
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
    import FixedWorkTimezoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkTimezoneSetModel;
    import OffdayWorkTimeConverter = nts.uk.at.view.kmk003.a.viewmodel.common.OffdayWorkTimeConverter;
    import TimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.predset.TimezoneModel;

    import FlexWorkSettingDto = service.model.flexset.FlexWorkSettingDto;
    
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
                    this.timesheet = ko.observable(1); // initial value = 利用する
                    this.minWorkTime = ko.observable(0);
                }

                public resetData(): void {
                    let self = this;
                    self.coreTimeSheet.resetData();
                    self.timesheet(1);
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

                constructor(ampmAtr: number, displayMode: KnockoutObservable<number>) {
                    this.restTimezone = new FlowWorkRestTimezoneModel();
                    this.workTimezone = new FixedWorkTimezoneSetModel(displayMode);
                    this.ampmAtr = ko.observable(ampmAtr);
                }

                bindFixRestTime(fixRestTime: KnockoutObservable<boolean>): void {
                    let self = this;
                    self.restTimezone.fixRestTime = fixRestTime;
                }

                updateData(data: FlexHalfDayWorkTimeDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.workTimezone.updateData(data.workTimezone);
                    this.ampmAtr(data.ampmAtr);
                }

                /**
                 * Return list dto including one day, morning, afternoon with the same data
                 */
                toListDto(): Array<FlexHalfDayWorkTimeDto> {
                    let self = this;
                    let oneDay = this.toDto();
                    oneDay.ampmAtr = 0;
                    let morning = this.toDto();
                    morning.ampmAtr = 1;
                    let afternoon = this.toDto();
                    afternoon.ampmAtr = 2;

                    return [oneDay, morning, afternoon];
                }

                toDto(): FlexHalfDayWorkTimeDto {
                    var dataDTO: FlexHalfDayWorkTimeDto = {
                        restTimezone: this.restTimezone.toDto(),
                        workTimezone: this.workTimezone.toDto(),
                        ampmAtr: this.ampmAtr()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.restTimezone.resetData();
                    this.workTimezone.resetData();    
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

            export class FlexOffdayWorkTimeModel extends OffdayWorkTimeConverter {
                lstWorkTimezone: KnockoutObservableArray<HDWorkTimeSheetSettingModel>;
                restTimezone: FlowWorkRestTimezoneModel;

                constructor() {
                    super();
                    this.lstWorkTimezone = this.originalList;
                    this.restTimezone = new FlowWorkRestTimezoneModel();
                }

                public resetData(): void {
                    let self = this;
                    self.restTimezone.resetData();
                    self.lstWorkTimezone([]);
                } 

                updateData(data: FlexOffdayWorkTimeDto) {
                    this.updateHDTimezone(data.lstWorkTimezone);
                    this.restTimezone.updateData(data.restTimezone);
                }

                updateHDTimezone(lstWorkTimezone: Array<HDWorkTimeSheetSettingDto>) {
                    var dataModelWorktimezone: Array<HDWorkTimeSheetSettingModel> = [];
                    for (var dataDTO of lstWorkTimezone) {
                        var dataModel: HDWorkTimeSheetSettingModel = new HDWorkTimeSheetSettingModel();
                        dataModel.updateData(dataDTO);
                        dataModelWorktimezone.push(dataModel);
                    }
                    this.lstWorkTimezone(dataModelWorktimezone);
                }

                toDto(): FlexOffdayWorkTimeDto {
                    var lstWorkTimezone: Array<HDWorkTimeSheetSettingDto> = [];
                    for (var dataModel of this.lstWorkTimezone()) {
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
                lstHalfDayWorkTimezone: Array<FlexHalfDayWorkTimeModel>;
                lstStampReflectTimezone: Array<StampReflectTimezoneModel>;
                calculateSetting: FlexCalcSettingModel;
                fixRestTime: KnockoutObservable<boolean>
                displayMode: KnockoutObservable<number>;
                
                constructor(displayMode: KnockoutObservable<number>) {
                    var self = this;
                    self.displayMode = displayMode;
                    self.workTimeCode = ko.observable('');
                    self.useHalfDayShift = ko.observable(false);
                    self.coreTimeSetting = new CoreTimeSettingModel();
                    self.restSetting = new FlowWorkRestSettingModel();
                    self.offdayWorkTime = new FlexOffdayWorkTimeModel();
                    self.commonSetting = new WorkTimezoneCommonSetModel();
                    self.fixRestTime = ko.observable(true); // initial value = lead
                    self.lstHalfDayWorkTimezone = self.initListHalfDay(self.displayMode);
                    self.lstStampReflectTimezone = [];
                    self.initStampSets();
                    self.calculateSetting = new FlexCalcSettingModel();                   
                }
                
                initStampSets() {
                    let self = this;
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
                    self.lstStampReflectTimezone.push(goWork1Stamp);
                    self.lstStampReflectTimezone.push(leaveWork1Stamp);   
                    self.lstStampReflectTimezone.push(goWork2Stamp);
                    self.lstStampReflectTimezone.push(leaveWork2Stamp);                         
                }

                private initListHalfDay(displayMode: KnockoutObservable<number>): Array<FlexHalfDayWorkTimeModel> {
                    let self = this;
                    let oneDay = new FlexHalfDayWorkTimeModel(0, displayMode);
                    oneDay.bindFixRestTime(self.fixRestTime);

                    let morning = new FlexHalfDayWorkTimeModel(1, displayMode);
                    morning.bindFixRestTime(self.fixRestTime);

                    let afternoon = new FlexHalfDayWorkTimeModel(2, displayMode);
                    afternoon.bindFixRestTime(self.fixRestTime);

                    return [oneDay, morning, afternoon];
                }

                public resetData(): void {
                    let self = this;
                    self.useHalfDayShift(false);
                    self.coreTimeSetting.resetData();
                    self.restSetting.resetData();
                    self.offdayWorkTime.resetData();
                    self.commonSetting.resetData();
                    self.getHDWtzOneday().resetData();
                    self.getHDWtzMorning().resetData();
                    self.getHDWtzAfternoon().resetData();
                    self.calculateSetting.resetData();
                    self.lstStampReflectTimezone.forEach(function(item, index) {
                        item.resetData();
                    });
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

                public initSubscriberForTab2(timezone: TimezoneModel): void {
                    let self = this;
                    self.getHDWtzOneday().workTimezone.initSubscribeForTab2(timezone);
                    self.getHDWtzMorning().workTimezone.initSubscribeForTab2(timezone);
                    self.getHDWtzAfternoon().workTimezone.initSubscribeForTab2(timezone);
                }

                updateListHalfDay(lstHalfDayWorkTimezone: Array<FlexHalfDayWorkTimeDto>): void {
                    let self = this;
                    _.forEach(lstHalfDayWorkTimezone, item => {
                        switch (item.ampmAtr) {
                            case 0:
                                this.getHDWtzOneday().updateData(item);
                                break;
                            case 1:
                                this.getHDWtzMorning().updateData(item);
                                break;
                            case 2:
                                this.getHDWtzAfternoon().updateData(item);
                                break;
                        }
                    });
                }

                updateData(data: FlexWorkSettingDto) {
                    this.workTimeCode(data.workTimeCode);
                    this.useHalfDayShift(data.useHalfDayShift);
                    this.coreTimeSetting.updateData(data.coreTimeSetting);
                    this.restSetting.updateData(data.restSetting);
                    this.offdayWorkTime.updateData(data.offdayWorkTime);
                    this.commonSetting.updateData(data.commonSetting);
                    this.updateListHalfDay(data.lstHalfDayWorkTimezone);
                    this.updateListStamp(data.lstStampReflectTimezone);
                    this.calculateSetting.updateData(data.calculateSetting);
                }
                
                updateListStamp(lstStampReflectTimezone: Array<StampReflectTimezoneDto>) {
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
                
                toDto(commonSetting: WorkTimezoneCommonSetModel): FlexWorkSettingDto{
                    var lstHalfDayWorkTimezone: Array<FlexHalfDayWorkTimeDto> = [];
                    if (this.displayMode() == TabMode.DETAIL && this.useHalfDayShift()) {
                        lstHalfDayWorkTimezone = _.map(this.lstHalfDayWorkTimezone, item => item.toDto());
                    } else {
                        lstHalfDayWorkTimezone = this.getHDWtzOneday().toListDto();
                    }

                    var lstStampReflectTimezone: Array<StampReflectTimezoneDto> = [];
                    for(var dataModelStamp of this.lstStampReflectTimezone){
                        lstStampReflectTimezone.push(dataModelStamp.toDto());
                    }
                    var dataDTO: FlexWorkSettingDto = {
                        workTimeCode: this.workTimeCode(),
                        useHalfDayShift: this.useHalfDayShift(),
                        coreTimeSetting: this.coreTimeSetting.toDto(),
                        restSetting: this.restSetting.toDto(),
                        offdayWorkTime: this.offdayWorkTime.toDto(),
                        commonSetting: commonSetting.toDto(),
                        lstHalfDayWorkTimezone: lstHalfDayWorkTimezone,
                        lstStampReflectTimezone: lstStampReflectTimezone,
                        calculateSetting: this.calculateSetting.toDto()
                    };
                    return dataDTO;    
                }
                
                getGoWork1Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.lstStampReflectTimezone, p => p.workNo() == 1 && p.classification() == 0) }
                getLeaveWork1Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.lstStampReflectTimezone, p => p.workNo() == 1 && p.classification() == 1) }
                getGoWork2Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.lstStampReflectTimezone, p => p.workNo() == 2 && p.classification() == 0) }
                getLeaveWork2Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.lstStampReflectTimezone, p => p.workNo() == 2 && p.classification() == 1) }
            }
        }
    }
}