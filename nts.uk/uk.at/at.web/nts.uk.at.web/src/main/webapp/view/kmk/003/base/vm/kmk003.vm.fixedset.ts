module nts.uk.at.view.kmk003.a {
    
    import EmTimeZoneSetDto = service.model.common.EmTimeZoneSetDto;
    import OverTimeOfTimeZoneSetDto = service.model.common.OverTimeOfTimeZoneSetDto;
    import HDWorkTimeSheetSettingDto = service.model.common.HDWorkTimeSheetSettingDto;
    import StampReflectTimezoneDto = service.model.common.StampReflectTimezoneDto;
    import OverTimeCalcNoBreakDto = service.model.common.OverTimeCalcNoBreakDto;
    import ExceededPredAddVacationCalcDto = service.model.common.ExceededPredAddVacationCalcDto;
    import FixedWorkCalcSettingDto = service.model.common.FixedWorkCalcSettingDto;
    
    import FixOffdayWorkTimezoneDto = service.model.fixedset.FixOffdayWorkTimezoneDto;
    import FixedWorkTimezoneSetDto = service.model.fixedset.FixedWorkTimezoneSetDto;
    import FixHalfDayWorkTimezoneDto = service.model.fixedset.FixHalfDayWorkTimezoneDto;
    
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
    import TimeRangeModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModel;
    import FixedWorkRestSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkRestSetModel;
    import HDWorkTimeSheetSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.HDWorkTimeSheetSettingModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import OverTimeOfTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OverTimeOfTimeZoneSetModel;
    import FixedWorkTimezoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkTimezoneSetModel;
    import FixedWorkCalcSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkCalcSettingModel;
    import OtherFlowColumnSetting = nts.uk.at.view.kmk003.a.viewmodel.common.OtherFlowColumnSetting;
    import OffdayWorkTimeConverter = nts.uk.at.view.kmk003.a.viewmodel.common.OffdayWorkTimeConverter;
    import FixRestTimezoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixRestTimezoneSetModel;
    import BaseDataModel = nts.uk.at.view.kmk003.a.viewmodel.common.BaseDataModel;
    import TimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.predset.TimezoneModel;
    import FixedTableDataConverter = nts.uk.at.view.kmk003.a.viewmodel.common.FixedTableDataConverter;
    
    import FixedWorkSettingDto = service.model.fixedset.FixedWorkSettingDto;
    export module viewmodel {
        export module fixedset {
            
            export class FixOffdayWorkTimezoneModel extends OffdayWorkTimeConverter {
                restTimezone: FixRestTimezoneSetModel;
                lstWorkTimezone: KnockoutObservableArray<HDWorkTimeSheetSettingModel>;
                
                constructor() {
                    super();
                    this.restTimezone = new FixRestTimezoneSetModel();
                    this.lstWorkTimezone = this.originalList;
                }
                
                updateData(data: FixOffdayWorkTimezoneDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.updateHDTimezone(data.lstWorkTimezone);
                }
                
                updateHDTimezone(lstWorkTimezone: HDWorkTimeSheetSettingDto[]) {
                    var dataModelHDTimezone: HDWorkTimeSheetSettingModel[] = [];
                    for (var dataDTO of lstWorkTimezone) {
                        var dataModel: HDWorkTimeSheetSettingModel = new HDWorkTimeSheetSettingModel();
                        dataModel.updateData(dataDTO);
                        dataModelHDTimezone.push(dataModel);
                    }
                    this.lstWorkTimezone(dataModelHDTimezone);
                }

                toDto(): FixOffdayWorkTimezoneDto {
                    let lstWorkTimezone: HDWorkTimeSheetSettingDto[] = _.map(this.lstWorkTimezone(), (dataModel) => dataModel.toDto());

                    let dataDTO: FixOffdayWorkTimezoneDto = {
                        restTimezone: this.restTimezone.toDto(),
                        lstWorkTimezone: lstWorkTimezone
                    };
                    return dataDTO;
                }

                resetData() {
                    this.restTimezone.resetData();
                    this.lstWorkTimezone([]);
                }

            }

            export class FixHalfDayWorkTimezoneModel extends BaseDataModel {
                restTimezone: FixRestTimezoneSetModel;
                workTimezone: FixedWorkTimezoneSetModel;
                dayAtr: KnockoutObservable<number>;
                
                constructor(displayMode: KnockoutObservable<number>) {
                    super();
                    this.restTimezone = new FixRestTimezoneSetModel();
                    this.workTimezone = new FixedWorkTimezoneSetModel(displayMode);
                    this.dayAtr = ko.observable(0);
                }

                static getDefaultData(displayMode: KnockoutObservable<number>): Array<FixHalfDayWorkTimezoneModel> {
                    let oneday = new FixHalfDayWorkTimezoneModel(displayMode);
                    oneday.dayAtr(0);
                    let morning = new FixHalfDayWorkTimezoneModel(displayMode);
                    morning.dayAtr(1);
                    let afternoon = new FixHalfDayWorkTimezoneModel(displayMode);
                    afternoon.dayAtr(2);
                    let list: Array<FixHalfDayWorkTimezoneModel> = [];
                    list.push(oneday);
                    list.push(morning);
                    list.push(afternoon);
                    return list;
                }
                
                updateData(data: FixHalfDayWorkTimezoneDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.workTimezone.updateData(data.workTimezone);
                    this.dayAtr(data.dayAtr);
                }

                /**
                 * Return list dto including one day, morning, afternoon with the same data
                 */
                toListDto(): Array<FixHalfDayWorkTimezoneDto> {
                    let self = this;
                    let oneDay = this.toDto();
                    oneDay.dayAtr = 0;
                    let morning = this.toDto();
                    morning.dayAtr = 1;
                    let afternoon = this.toDto();
                    afternoon.dayAtr = 2;

                    return [oneDay, morning, afternoon];
                }
                
                toDto(): FixHalfDayWorkTimezoneDto {
                    let dataDTO: FixHalfDayWorkTimezoneDto = {
                        restTimezone: this.restTimezone.toDto(),
                        workTimezone: this.workTimezone.toDto(),
                        dayAtr: this.dayAtr()
                    };
                    return dataDTO;
                }
                resetData(){
                    this.restTimezone.resetData();
                    this.workTimezone.resetData();    
                }
            }
            
            export class FixedWorkSettingModel {
                workTimeCode: KnockoutObservable<string>;
                offdayWorkTimezone: FixOffdayWorkTimezoneModel;
                commonSetting: WorkTimezoneCommonSetModel;
                useHalfDayShift: KnockoutObservable<boolean>;
                fixedWorkRestSetting: FixedWorkRestSetModel;
                lstHalfDayWorkTimezone: Array<FixHalfDayWorkTimezoneModel>;
                lstStampReflectTimezone: Array<StampReflectTimezoneModel>;
                legalOTSetting: KnockoutObservable<number>;
                calculationSetting: FixedWorkCalcSettingModel;
                displayMode: KnockoutObservable<number>;
                
                constructor(displayMode: KnockoutObservable<number>) {
                    let self = this;
                    self.displayMode = displayMode;
                    self.workTimeCode = ko.observable('');
                    self.offdayWorkTimezone = new FixOffdayWorkTimezoneModel();
                    self.commonSetting = new WorkTimezoneCommonSetModel();
                    self.useHalfDayShift = ko.observable(false);
                    self.fixedWorkRestSetting = new FixedWorkRestSetModel();
                    self.lstHalfDayWorkTimezone = FixHalfDayWorkTimezoneModel.getDefaultData(self.displayMode);
                    self.lstStampReflectTimezone = [];
                    self.legalOTSetting = ko.observable(0);
                    // Update phase 2
                    self.calculationSetting = new FixedWorkCalcSettingModel();
                    self.initStampSets();
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
                
                public getHDWtzOneday(): FixHalfDayWorkTimezoneModel {
                    let self = this;
                    return _.find(self.lstHalfDayWorkTimezone, time => time.dayAtr() == 0);
                }
                public getHDWtzMorning(): FixHalfDayWorkTimezoneModel {
                    let self = this;
                    return _.find(self.lstHalfDayWorkTimezone, time => time.dayAtr() == 1);
                }
                public getHDWtzAfternoon(): FixHalfDayWorkTimezoneModel {
                    let self = this;
                    return _.find(self.lstHalfDayWorkTimezone, time => time.dayAtr() == 2);
                }

                public initSubscriberForTab2(timezone: TimezoneModel): void {
                    let self = this;
                    self.getHDWtzOneday().workTimezone.initSubscribeForTab2(timezone);
                    self.getHDWtzMorning().workTimezone.initSubscribeForTab2(timezone);
                    self.getHDWtzAfternoon().workTimezone.initSubscribeForTab2(timezone);
                }

                updateData(data: FixedWorkSettingDto) {
                    this.workTimeCode(data.workTimeCode);                                        
                    this.offdayWorkTimezone.updateData(data.offdayWorkTimezone);
                    this.commonSetting.updateData(data.commonSetting);
                    this.useHalfDayShift(data.useHalfDayShift);                    
                    this.fixedWorkRestSetting.updateData(data.fixedWorkRestSetting);                                       
                    this.updateListHalfDay(data.lstHalfDayWorkTimezone);
                    this.updateListStamp(data.lstStampReflectTimezone);
                    this.legalOTSetting(data.legalOTSetting);
                    // Update phase 2
                    this.calculationSetting.updateData(data.calculationSetting);
                }

                updateListHalfDay(lstHalfDayWorkTimezone: Array<FixHalfDayWorkTimezoneDto>): void {
                    let self = this;
                    _.forEach(lstHalfDayWorkTimezone, item => {
                        switch (item.dayAtr) {
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
                
                toDto(commonSetting: WorkTimezoneCommonSetModel): FixedWorkSettingDto {
                    let lstHalfDayWorkTimezone: Array<FixHalfDayWorkTimezoneDto>;
                    if (this.displayMode() == TabMode.DETAIL && this.useHalfDayShift()) {
                        lstHalfDayWorkTimezone = _.map(this.lstHalfDayWorkTimezone, item => item.toDto());
                    } else {
                        lstHalfDayWorkTimezone = this.getHDWtzOneday().toListDto();
                    }

                    let lstStampReflectTimezone: Array<StampReflectTimezoneDto> = _.map(this.lstStampReflectTimezone, (dataModel) => dataModel.toDto());
                    
                    let dataDTO: FixedWorkSettingDto = {
                        workTimeCode: this.workTimeCode(),                       
                        offdayWorkTimezone: this.offdayWorkTimezone.toDto(),
                        commonSetting: commonSetting.toDto(),
                        useHalfDayShift: this.useHalfDayShift(),
                        fixedWorkRestSetting: this.fixedWorkRestSetting.toDto(),
                        lstHalfDayWorkTimezone: lstHalfDayWorkTimezone,
                        lstStampReflectTimezone: lstStampReflectTimezone,
                        legalOTSetting: this.legalOTSetting(),
                        // Update phase 2
                        calculationSetting: this.calculationSetting.toDto()
                    };
                    return dataDTO;
                }
                
                resetData(isNewMode?:boolean){
                    this.offdayWorkTimezone.resetData();
                    this.commonSetting.resetData();
                    this.useHalfDayShift(false);
                    this.fixedWorkRestSetting.resetData();
                    this.getHDWtzOneday().resetData();
                    this.getHDWtzMorning().resetData();
                    this.getHDWtzAfternoon().resetData();
                    this.lstStampReflectTimezone.forEach(function(item, index) {
                        item.resetData();
                    });
                    //update ver7.2 
                    this.legalOTSetting(0);
                    // Update phase 2
                    if (!isNewMode) {
                        this.calculationSetting.resetData();
                    }
                }
                
                getGoWork1Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.lstStampReflectTimezone, p => p.workNo() == 1 && p.classification() == 0) }
                getLeaveWork1Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.lstStampReflectTimezone, p => p.workNo() == 1 && p.classification() == 1) }
                getGoWork2Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.lstStampReflectTimezone, p => p.workNo() == 2 && p.classification() == 0) }
                getLeaveWork2Stamp(): StampReflectTimezoneModel { let self = this; return _.find(self.lstStampReflectTimezone, p => p.workNo() == 2 && p.classification() == 1) }
            }
        }
    }
}