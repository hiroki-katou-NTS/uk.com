module nts.uk.at.view.kmk003.a {
    
    import DeductionTimeDto = service.model.common.DeductionTimeDto;
    import EmTimeZoneSetDto = service.model.common.EmTimeZoneSetDto;
    import OverTimeOfTimeZoneSetDto = service.model.common.OverTimeOfTimeZoneSetDto;
    import HDWorkTimeSheetSettingDto = service.model.common.HDWorkTimeSheetSettingDto;
    import StampReflectTimezoneDto = service.model.common.StampReflectTimezoneDto;
    
    import FixOffdayWorkTimezoneDto = service.model.fixedset.FixOffdayWorkTimezoneDto;
    import FixRestTimezoneSetDto = service.model.fixedset.FixRestTimezoneSetDto;
    import FixedWorkTimezoneSetDto = service.model.fixedset.FixedWorkTimezoneSetDto;
    import FixHalfDayWorkTimezoneDto = service.model.fixedset.FixHalfDayWorkTimezoneDto;
    import OverTimeCalcNoBreakDto = service.model.fixedset.OverTimeCalcNoBreakDto;
    import ExceededPredAddVacationCalcDto = service.model.fixedset.ExceededPredAddVacationCalcDto;
    import FixedWorkCalcSettingDto = service.model.fixedset.FixedWorkCalcSettingDto;
    
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
    import TimeRangeModel = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModel;
    import FixedWorkRestSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkRestSetModel;
    import HDWorkTimeSheetSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.HDWorkTimeSheetSettingModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import OverTimeOfTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OverTimeOfTimeZoneSetModel;
    import TimeRangeModelConverter = nts.uk.at.view.kmk003.a.viewmodel.common.TimeRangeModelConverter;
    import FixedWorkTimezoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkTimezoneSetModel;
    import OtherFlowColumnSetting = nts.uk.at.view.kmk003.a.viewmodel.common.OtherFlowColumnSetting;
    import OffdayWorkTimeConverter = nts.uk.at.view.kmk003.a.viewmodel.common.OffdayWorkTimeConverter;
    
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

            export class FixHalfDayWorkTimezoneModel {
                restTimezone: FixRestTimezoneSetModel;
                workTimezone: FixedWorkTimezoneSetModel;
                dayAtr: KnockoutObservable<number>;
                
                constructor() {
                    this.restTimezone = new FixRestTimezoneSetModel();
                    this.workTimezone = new FixedWorkTimezoneSetModel();
                    this.dayAtr = ko.observable(0);
                }

                static getDefaultData(): Array<FixHalfDayWorkTimezoneModel> {
                    let oneday = new FixHalfDayWorkTimezoneModel();
                    oneday.dayAtr(0);
                    let morning = new FixHalfDayWorkTimezoneModel();
                    morning.dayAtr(1);
                    let afternoon = new FixHalfDayWorkTimezoneModel();
                    afternoon.dayAtr(2);
                    let list: any[] = [];
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
            
            export class FixRestTimezoneSetModel extends TimeRangeModelConverter<DeductionTimeModel> {
                lstTimezone: KnockoutObservableArray<DeductionTimeModel>;
                
                constructor() {
                    super();
                    this.lstTimezone = this.originalList;
                }

                toConvertedList(): Array<TimeRangeModel> {
                    let self = this;
                    return _.map(self.lstTimezone(), tz => self.toTimeRangeItem(tz.start(), tz.end()));
                }

                fromConvertedList(newList: Array<TimeRangeModel>): Array<DeductionTimeModel> {
                    return _.map(newList, newVl => {
                        let vl = new DeductionTimeModel();
                        vl.start(newVl.column1().startTime);
                        vl.end(newVl.column1().endTime);
                        return vl;
                    });
                }
                
                updateData(data: FixRestTimezoneSetDto) {
                    let mapped = _.map(data.lstTimezone, (dataDTO) => {
                        let dataModel = new DeductionTimeModel();
                        dataModel.updateData(dataDTO);
                        return dataModel;
                    });  
                    this.lstTimezone(_.sortBy(mapped, item => item.start()));
                }
                
                toDto(): FixRestTimezoneSetDto {
                    let lstTimezone: DeductionTimeDto[] = _.map(this.lstTimezone(), dataModel => dataModel.toDto());
                    
                    let dataDTO: FixRestTimezoneSetDto = {
                        lstTimezone: lstTimezone
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.lstTimezone([]);    
                }
            }
            
            export class OverTimeCalcNoBreakModel {
                calcMethod: KnockoutObservable<number>;
                inLawOT: KnockoutObservable<number>;
                notInLawOT: KnockoutObservable<number>;
                
                constructor() {
                    this.calcMethod = ko.observable(0);
                    this.inLawOT = ko.observable(0);
                    this.notInLawOT = ko.observable(0);
                }
                
                updateData(data: OverTimeCalcNoBreakDto) {
                    this.calcMethod(data.calcMethod);
                    this.inLawOT(data.inLawOT);
                    this.notInLawOT(data.notInLawOT);
                }
                
                toDto(): OverTimeCalcNoBreakDto {
                    let dataDTO: OverTimeCalcNoBreakDto = {
                        calcMethod: this.calcMethod(),
                        inLawOT: this.inLawOT(),
                        notInLawOT: this.notInLawOT()
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.calcMethod(0);
                    this.inLawOT(0);
                    this.notInLawOT(0);    
                }
            }
            
            export class ExceededPredAddVacationCalcModel {
                calcMethod: KnockoutObservable<number>;
                otFrameNo: KnockoutObservable<number>;
                
                constructor() {
                    this.calcMethod = ko.observable(0);
                    this.otFrameNo = ko.observable(0);
                }
                
                updateData(data: ExceededPredAddVacationCalcDto) {
                    this.calcMethod(data.calcMethod);
                    this.otFrameNo(data.otFrameNo);
                }
                
                toDto(): ExceededPredAddVacationCalcDto {
                    let dataDTO: ExceededPredAddVacationCalcDto = {
                        calcMethod: this.calcMethod(),
                        otFrameNo: this.otFrameNo()
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.calcMethod(0);
                    this.otFrameNo(0);
                }
            }
            
            export class FixedWorkCalcSettingModel {
                exceededPredAddVacationCalc: ExceededPredAddVacationCalcModel;
                overTimeCalcNoBreak: OverTimeCalcNoBreakModel;
                
                constructor() {
                    this.exceededPredAddVacationCalc = new ExceededPredAddVacationCalcModel();
                    this.overTimeCalcNoBreak = new OverTimeCalcNoBreakModel();
                }
                
                updateData(data: FixedWorkCalcSettingDto) {
                    this.exceededPredAddVacationCalc.updateData(data.exceededPredAddVacationCalc);
                    this.overTimeCalcNoBreak.updateData(data.overTimeCalcNoBreak);
                }
                
                toDto(): FixedWorkCalcSettingDto {
                    let dataDTO: FixedWorkCalcSettingDto = {
                        exceededPredAddVacationCalc: this.exceededPredAddVacationCalc.toDto(),
                        overTimeCalcNoBreak: this.overTimeCalcNoBreak.toDto()
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.exceededPredAddVacationCalc.resetData();
                    this.overTimeCalcNoBreak.resetData();
                }
            }
            
            export class FixedWorkSettingModel {
                workTimeCode: KnockoutObservable<string>;
                offdayWorkTimezone: FixOffdayWorkTimezoneModel;
                commonSetting: WorkTimezoneCommonSetModel;
                useHalfDayShift: KnockoutObservable<boolean>;
                fixedWorkRestSetting: FixedWorkRestSetModel;
                lstHalfDayWorkTimezone: FixHalfDayWorkTimezoneModel[];
                lstStampReflectTimezone: StampReflectTimezoneModel[];
                legalOTSetting: KnockoutObservable<number>;
                fixedWorkCalcSetting: FixedWorkCalcSettingModel;
                
                constructor() {
                    this.workTimeCode = ko.observable('');
                    this.offdayWorkTimezone = new FixOffdayWorkTimezoneModel();
                    this.commonSetting = new WorkTimezoneCommonSetModel();
                    this.useHalfDayShift = ko.observable(false);
                    this.fixedWorkRestSetting = new FixedWorkRestSetModel();
                    this.lstHalfDayWorkTimezone = FixHalfDayWorkTimezoneModel.getDefaultData();
                    this.lstStampReflectTimezone = [];
                    this.legalOTSetting = ko.observable(0);
                    // Update phase 2
                    this.fixedWorkCalcSetting = new FixedWorkCalcSettingModel();
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

                updateData(data: FixedWorkSettingDto) {
                    this.workTimeCode(data.workTimeCode);                                        
                    this.offdayWorkTimezone.updateData(data.offdayWorkTimezone);
                    this.commonSetting.updateData(data.commonSetting);
                    this.useHalfDayShift(data.useHalfDayShift);                    
                    this.fixedWorkRestSetting.updateData(data.fixedWorkRestSetting);                                       
                    this.updateListHalfDay(data.lstHalfDayWorkTimezone);
                    this.lstStampReflectTimezone = _.map(data.lstStampReflectTimezone, (dataDTO) => {
                        let dataModel: StampReflectTimezoneModel = new StampReflectTimezoneModel();
                        dataModel.updateData(dataDTO);
                        return dataModel;
                    });                                    
                    this.legalOTSetting(data.legalOTSetting);
                    // Update phase 2
                    this.fixedWorkCalcSetting.updateData(data.fixedWorkCalcSetting);
                }

                updateListHalfDay(lstHalfDayWorkTimezone: FixHalfDayWorkTimezoneDto[]): void {
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
                
                toDto(commonSetting: WorkTimezoneCommonSetModel): FixedWorkSettingDto {
                    let lstHalfDayWorkTimezone: FixHalfDayWorkTimezoneDto[] = _.map(this.lstHalfDayWorkTimezone, (dataModel) => dataModel.toDto());
                    let lstStampReflectTimezone: StampReflectTimezoneDto[] = _.map(this.lstStampReflectTimezone, (dataModel) => dataModel.toDto());
                    
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
                        fixedWorkCalcSetting: this.fixedWorkCalcSetting.toDto()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.offdayWorkTimezone.resetData();
                    this.commonSetting.resetData();
                    this.useHalfDayShift(false);
                    this.fixedWorkRestSetting.resetData();
                    this.getHDWtzOneday().resetData();
                    this.getHDWtzMorning().resetData();
                    this.getHDWtzAfternoon().resetData();
                    this.lstStampReflectTimezone = [];
                    //update ver7.2 
                    this.legalOTSetting(0);
                    // Update phase 2
                    this.fixedWorkCalcSetting.resetData();
                }
            }
        }
    }
}