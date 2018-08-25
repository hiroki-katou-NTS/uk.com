module nts.uk.at.view.kmk003.a {

    import BreakDownTimeDayDto = service.model.predset.BreakDownTimeDayDto;
    import PredetermineTimeDto = service.model.predset.PredetermineTimeDto;
    import TimezoneDto = service.model.predset.TimezoneDto;
    import PrescribedTimezoneSettingDto = service.model.predset.PrescribedTimezoneSettingDto;
    import PredetemineTimeSettingDto = service.model.predset.PredetemineTimeSettingDto;

    export module viewmodel {
        export module predset {

            export class BreakDownTimeDayModel {
                oneDay: KnockoutObservable<number>;
                morning: KnockoutObservable<number>;
                afternoon: KnockoutObservable<number>;

                constructor() {
                    this.oneDay = ko.observable(null);
                    this.morning = ko.observable(0);
                    this.afternoon = ko.observable(0);
                }

                updateData(data: BreakDownTimeDayDto) {
                    this.oneDay(data.oneDay);
                    this.morning(data.morning);
                    this.afternoon(data.afternoon);
                }

                toDto(): BreakDownTimeDayDto {
                    var dataDTO: BreakDownTimeDayDto = {
                        oneDay: this.oneDay(),
                        morning: this.morning(),
                        afternoon: this.afternoon()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.oneDay(null);
                    this.morning(0);
                    this.afternoon(0);
                }
            }

            export class PredetermineTimeModel {
                addTime: BreakDownTimeDayModel;
                predTime: BreakDownTimeDayModel;

                constructor() {
                    this.addTime = new BreakDownTimeDayModel();
                    this.predTime = new BreakDownTimeDayModel();
                }

                updateData(data: PredetermineTimeDto) {
                    this.addTime.updateData(data.addTime);
                    this.predTime.updateData(data.predTime);
                }

                toDto(): PredetermineTimeDto {
                    var dataDTO: PredetermineTimeDto = {
                        addTime: this.addTime.toDto(),
                        predTime: this.predTime.toDto()
                    };
                    return dataDTO;
                }
                
                resetData(){
                    this.addTime.resetData();
                    this.predTime.resetData();    
                }
            }

            export class TimezoneModel {
                useAtr: KnockoutObservable<boolean>;
                workNo: KnockoutObservable<number>;
                start: KnockoutObservable<number>;
                end: KnockoutObservable<number>;
                valueChangedNotifier: KnockoutObservable<any>;

                constructor() {
                    this.useAtr = ko.observable(false);
                    this.workNo = ko.observable(0);
                    this.start = ko.observable(0);
                    this.end = ko.observable(0);
                    this.valueChangedNotifier = ko.observable();
                    this.start.subscribe(() => {
                        this.valueChangedNotifier.valueHasMutated();
                    });
                    this.end.subscribe(() => {
                        this.valueChangedNotifier.valueHasMutated();
                    });
                }

                public static createShiftOne(): TimezoneModel {
                    let m = new TimezoneModel();
                    m.workNo(1);
                    return m;
                }

                public static createShiftTwo(): TimezoneModel {
                    let m = new TimezoneModel();
                    m.workNo(2);
                    return m;
                }

                resetData(): void {
                    let self = this;
                    self.useAtr(false);
                    self.start(0);
                    self.end(0);
                }

                updateData(data: TimezoneDto) {
                    this.useAtr(data.useAtr);
                    this.workNo(data.workNo);
                    this.end(data.end);
                    this.start(data.start);
                }

                toDto(): TimezoneDto {
                    var dataDTO: TimezoneDto = {
                        useAtr: this.useAtr(),
                        workNo: this.workNo(),
                        end: this.end(),
                        start: this.start()
                    };
                    return dataDTO;
                }
            }


            export class PrescribedTimezoneSettingModel {
                morningEndTime: KnockoutObservable<number>;
                afternoonStartTime: KnockoutObservable<number>;
                shiftOne: TimezoneModel;
                shiftTwo: TimezoneModel;

                constructor() {
                    this.morningEndTime = ko.observable(0);
                    this.afternoonStartTime = ko.observable(0);
                    this.shiftOne = TimezoneModel.createShiftOne();
                    this.shiftTwo = TimezoneModel.createShiftTwo();
                }

                getShiftTwo(): TimezoneModel {
                    let self = this;
                    return self.shiftTwo;
                }
                
                updateData(data: PrescribedTimezoneSettingDto) {
                    this.morningEndTime(data.morningEndTime);
                    this.afternoonStartTime(data.afternoonStartTime);
                    this.updateTimeZone(data.lstTimezone);
                }

                updateTimeZone(data: Array<TimezoneDto>) {
                    let self = this;
                    let shift1 = _.find(data, item => item.workNo == 1);
                    let shift2 = _.find(data, item => item.workNo == 2);
                    if (shift1) {
                        self.shiftOne.updateData(shift1);
                    }
                    if (shift2) {
                        self.shiftTwo.updateData(shift2);
                    }
                }
                
                toDto(): PrescribedTimezoneSettingDto {
                    var lstTimezone: Array<TimezoneDto> = [];
                    lstTimezone.push(this.shiftOne.toDto());
                    lstTimezone.push(this.shiftTwo.toDto());
                    var dataDTO: PrescribedTimezoneSettingDto = {
                        morningEndTime: this.morningEndTime(),
                        afternoonStartTime: this.afternoonStartTime(),
                        lstTimezone: lstTimezone
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.morningEndTime(0);
                    this.afternoonStartTime(0);
                    this.shiftOne.resetData();
                    this.shiftTwo.resetData();
                }
            }


            export class PredetemineTimeSettingModel {
                rangeTimeDay: KnockoutComputed<number>; // in minutes
                rangeTimeDayInHours: KnockoutObservable<number>; // in hours
                workTimeCode: KnockoutObservable<string>;
                predTime: PredetermineTimeModel;
                nightShift: KnockoutObservable<number>;
                prescribedTimezoneSetting: PrescribedTimezoneSettingModel;
                startDateClock: KnockoutObservable<number>;
                predetermine: KnockoutObservable<boolean>;
                static ONE_DAY = 24; // initial value of rangeTimeDay = 24h
                static TIME_UNIT = 60;

                constructor() {
                    this.rangeTimeDayInHours = ko.observable(PredetemineTimeSettingModel.ONE_DAY); 
                    this.rangeTimeDay = ko.computed(() => this.rangeTimeDayInHours() * PredetemineTimeSettingModel.TIME_UNIT);
                    this.workTimeCode = ko.observable('');
                    this.predTime = new PredetermineTimeModel();
                    this.nightShift = ko.observable(0);
                    this.prescribedTimezoneSetting = new PrescribedTimezoneSettingModel();
                    this.startDateClock = ko.observable(0);
                    this.predetermine = ko.observable(false);
                }

                updateData(data: PredetemineTimeSettingDto) {
                    this.rangeTimeDayInHours(data.rangeTimeDay / PredetemineTimeSettingModel.TIME_UNIT); // minutes to hours
                    this.workTimeCode(data.workTimeCode);
                    this.predTime.updateData(data.predTime);
                    this.nightShift(data.nightShift);
                    this.prescribedTimezoneSetting.updateData(data.prescribedTimezoneSetting);
                    this.startDateClock(data.startDateClock);
                    this.predetermine(data.predetermine);
                }

                toDto(): PredetemineTimeSettingDto {
                    var dataDTO: PredetemineTimeSettingDto = {
                        rangeTimeDay: this.rangeTimeDay(),
                        workTimeCode: this.workTimeCode(),
                        predTime: this.predTime.toDto(),
                        nightShift: this.nightShift(),
                        prescribedTimezoneSetting: this.prescribedTimezoneSetting.toDto(),
                        startDateClock: this.startDateClock(),
                        predetermine: this.predetermine()
                    };
                    return dataDTO;
                }
                
                resetData() {
                    this.rangeTimeDayInHours(PredetemineTimeSettingModel.ONE_DAY);
                    this.predTime.resetData();
                    this.nightShift(0);  
                    this.prescribedTimezoneSetting.resetData();
                    this.startDateClock(0);
                    this.predetermine(false);
                }
            }
        }
    }
}