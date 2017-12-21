module nts.uk.at.view.kmk003.a {
    
    import DeductionTimeDto = service.model.common.DeductionTimeDto;
    import EmTimeZoneSetDto = service.model.common.EmTimeZoneSetDto;
    import OverTimeOfTimeZoneSetDto = service.model.common.OverTimeOfTimeZoneSetDto;
    import HDWorkTimeSheetSettingDto = service.model.common.HDWorkTimeSheetSettingDto;
    import StampReflectTimezoneDto = service.model.common.StampReflectTimezoneDto;
    
    import FixedWorkSettingDto = service.model.fixedset.FixedWorkSettingDto;
    import FixOffdayWorkTimezoneDto = service.model.fixedset.FixOffdayWorkTimezoneDto;
    import FixRestTimezoneSetDto = service.model.fixedset.FixRestTimezoneSetDto;
    import FixedWorkTimezoneSetDto = service.model.fixedset.FixedWorkTimezoneSetDto;
    import FixHalfDayWorkTimezoneDto = service.model.fixedset.FixHalfDayWorkTimezoneDto;
    
    import WorkTimezoneCommonSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.WorkTimezoneCommonSetModel;
    import FixedWorkRestSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.FixedWorkRestSetModel;
    import HDWorkTimeSheetSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.HDWorkTimeSheetSettingModel;
    import StampReflectTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.StampReflectTimezoneModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    import EmTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.EmTimeZoneSetModel;
    import OverTimeOfTimeZoneSetModel = nts.uk.at.view.kmk003.a.viewmodel.common.OverTimeOfTimeZoneSetModel;
    
    export module viewmodel {
        export module fixedset {
            
            export class FixOffdayWorkTimezoneModel {
                restTimezone: FixRestTimezoneSetModel;
                lstWorkTimezone: HDWorkTimeSheetSettingModel[];
                
                constructor() {
                    this.restTimezone = new FixRestTimezoneSetModel();
                    this.lstWorkTimezone = [];
                }
                
                updateData(data: FixOffdayWorkTimezoneDto) {
                    this.restTimezone.updateData(data.restTimezone);
                    this.lstWorkTimezone = _.map(data.lstWorkTimezone, (dataDTO) => {
                        let dataModel: HDWorkTimeSheetSettingModel = new HDWorkTimeSheetSettingModel();
                        dataModel.updateData(dataDTO);
                        return dataModel;
                    });  
                }
                
                toDto(): FixOffdayWorkTimezoneDto {
                    let lstWorkTimezone: HDWorkTimeSheetSettingDto[] = _.map(this.lstWorkTimezone, (dataModel) => dataModel.toDto());
                    
                    let dataDTO: FixOffdayWorkTimezoneDto = {
                        restTimezone: this.restTimezone.toDto(),
                        lstWorkTimezone: lstWorkTimezone
                    };
                    return dataDTO;
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
            }
            
            export class FixRestTimezoneSetModel {
                lstTimezone: KnockoutObservableArray<DeductionTimeModel>;
                
                constructor() {
                    this.lstTimezone = ko.observableArray([]);
                }
                
                updateData(data: FixRestTimezoneSetDto) {
                    let mapped = _.map(data.lstTimezone, (dataDTO) => {
                        let dataModel: DeductionTimeModel = new DeductionTimeModel();
                        dataModel.updateData(dataDTO);
                        return dataModel;
                    });  
                    this.lstTimezone(mapped);
                }
                
                toDto(): FixRestTimezoneSetDto {
                    let lstTimezone: DeductionTimeDto[] = _.map(this.lstTimezone(), dataModel => dataModel.toDto());
                    
                    let dataDTO: FixRestTimezoneSetDto = {
                        lstTimezone: lstTimezone
                    };
                    return dataDTO;
                }
            }
            
            export class FixedWorkTimezoneSetModel {
                lstWorkingTimezone: common.EmTimeZoneSetModel[];
                lstOTTimezone: common.OverTimeOfTimeZoneSetModel[];
                
                constructor() {
                    this.lstWorkingTimezone = [];
                    this.lstOTTimezone = [];
                }
                
                updateData(data: FixedWorkTimezoneSetDto) {
                    this.lstWorkingTimezone = _.map(data.lstWorkingTimezone, (dataDTO) => {
                        let dataModel: EmTimeZoneSetModel = new EmTimeZoneSetModel();
                        dataModel.updateData(dataDTO);
                        return dataModel;
                    });                   
                    this.lstOTTimezone = _.map(data.lstOTTimezone, (dataDTO) => {
                        let dataModel: OverTimeOfTimeZoneSetModel = new OverTimeOfTimeZoneSetModel();
                        dataModel.updateData(dataDTO);
                        return dataModel;
                    });
                }
                
                toDto(): FixedWorkTimezoneSetDto {
                    let lstWorkingTimezone: EmTimeZoneSetDto[] = _.map(this.lstWorkingTimezone, (dataModel) => dataModel.toDto());
                    let lstOTTimezone: OverTimeOfTimeZoneSetDto[] = _.map(this.lstOTTimezone, (dataModel) => dataModel.toDto());
                    
                    let dataDTO: FixedWorkTimezoneSetDto = {
                        lstWorkingTimezone: lstWorkingTimezone,
                        lstOTTimezone: lstOTTimezone
                    };
                    return dataDTO;
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
                
                constructor() {
                    this.workTimeCode = ko.observable('');
                    this.offdayWorkTimezone = new FixOffdayWorkTimezoneModel();
                    this.commonSetting = new WorkTimezoneCommonSetModel();
                    this.useHalfDayShift = ko.observable(false);
                    this.fixedWorkRestSetting = new FixedWorkRestSetModel();
                    this.lstHalfDayWorkTimezone = FixHalfDayWorkTimezoneModel.getDefaultData();
                    this.lstStampReflectTimezone = [];
                    this.legalOTSetting = ko.observable(0);
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
                }

                updateListHalfDay(lstHalfDayWorkTimezone: FixHalfDayWorkTimezoneDto[]): void {
                    lstHalfDayWorkTimezone.sort(item => item.dayAtr);
                    this.getHDWtzOneday().updateData(lstHalfDayWorkTimezone[0]);
                    this.getHDWtzMorning().updateData(lstHalfDayWorkTimezone[1]);
                    this.getHDWtzAfternoon().updateData(lstHalfDayWorkTimezone[2]);
                }
                
                toDto(): FixedWorkSettingDto {
                    let lstHalfDayWorkTimezone: FixHalfDayWorkTimezoneDto[] = _.map(this.lstHalfDayWorkTimezone, (dataModel) => dataModel.toDto());
                    let lstStampReflectTimezone: StampReflectTimezoneDto[] = _.map(this.lstStampReflectTimezone, (dataModel) => dataModel.toDto());
                    
                    let dataDTO: FixedWorkSettingDto = {
                        workTimeCode: this.workTimeCode(),                       
                        offdayWorkTimezone: this.offdayWorkTimezone.toDto(),
                        commonSetting: this.commonSetting.toDto(),
                        useHalfDayShift: this.useHalfDayShift(),
                        fixedWorkRestSetting: this.fixedWorkRestSetting.toDto(),
                        lstHalfDayWorkTimezone: lstHalfDayWorkTimezone,
                        lstStampReflectTimezone: lstStampReflectTimezone,
                        legalOTSetting: this.legalOTSetting()
                    };
                    return dataDTO;
                }
            }
            
        }
    }
}