module nts.uk.at.view.kmk003.a {

    import DayOffTimezoneSettingDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DayOffTimezoneSettingDto;
    import DiffTimeDeductTimezoneDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeDeductTimezoneDto;
    import DiffTimeRestTimezoneDto = nts.uk.at.view.kmk003.a.service.model.difftimeset.DiffTimeRestTimezoneDto;

    import HDWorkTimeSheetSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.HDWorkTimeSheetSettingModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;

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


            export class DiffTimeRestTimezoneModel {
                restTimezones: DiffTimeDeductTimezoneModel[];

                constructor() {
                    this.restTimezones = [];
                }

                updateData(data: DiffTimeRestTimezoneDto) {
                    this.restTimezones = [];
                    for (var dataDTO of data.restTimezones) {
                        var dataModel: DiffTimeDeductTimezoneModel = new DiffTimeDeductTimezoneModel();
                        dataModel.updateData(dataDTO);
                        this.restTimezones.push(dataModel);
                    }
                }

                toDto(): DiffTimeRestTimezoneDto {
                    var restTimezones: DiffTimeDeductTimezoneDto[] = [];
                    for (var dataModel of this.restTimezones) {
                        restTimezones.push(dataModel.toDto());
                    }
                    var dataDTO: DiffTimeRestTimezoneDto = {
                        restTimezones: restTimezones
                    };
                    return dataDTO;
                }
            }
            
            export class DiffWorkSettingModel {
                //TODO
                
                constructor() {
                    //TODO
                }
                
                updateData() {
                    //TODO
                }
                
                toDto() {
                    //TODO
                }
            }
        }
    }
}