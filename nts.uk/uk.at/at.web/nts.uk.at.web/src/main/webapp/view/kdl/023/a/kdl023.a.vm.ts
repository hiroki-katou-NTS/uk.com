module nts.uk.at.view.kdl023.a.viewmodel {

    import WorkDayDivision = service.model.WorkDayDivision;
    import WeeklyWorkSetting = service.model.WeeklyWorkSetting;
    import PublicHoliday = service.model.PublicHoliday;
    import ReflectionMethod = service.model.ReflectionMethod;

    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModelCbb1>;
        selectedCode: KnockoutObservable<string>;

        patternReflection: PatternReflection;
        dailyPatternSetting: service.model.DailyPatternSetting;
        weeklyWorkSetting: WeeklyWorkSetting;
        listHoliday: Array<any>;

        // Calendar component
        calendarData: KnockoutObservable<any>;
        yearMonthPicked: KnockoutObservable<number>;
        cssRangerYM: any;
        optionDates: KnockoutObservableArray<OptionDate>;
        firstDay: number;
        yearMonth: KnockoutObservable<number>;
        startDate: number;
        endDate: number;
        workplaceId: KnockoutObservable<string>;
        eventDisplay: KnockoutObservable<boolean>;
        eventUpdatable: KnockoutObservable<boolean>;
        holidayDisplay: KnockoutObservable<boolean>;
        cellButtonDisplay: KnockoutObservable<boolean>;
        workplaceName: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.listHoliday = [];
            self.itemList = ko.observableArray([
                new ItemModelCbb1('1', '基本給'),
                new ItemModelCbb1('2', '役職手当'),
                new ItemModelCbb1('3', '基本給')
            ]);
            self.selectedCode = ko.observable('1');

            self.dailyPatternSetting = {};
            self.dailyPatternSetting.patternCode = 'code';
            self.dailyPatternSetting.patternName = 'name';
            self.dailyPatternSetting.workPatterns = [{
                dispOrder: 1,
                workTypeCode: 'đi làm vì đam mê',
                workingHoursCode: '',
                days: 1,
            }, {
                    dispOrder: 2,
                    workTypeCode: 'đi làm cho vui',
                    workingHoursCode: '',
                    days: 2,
                }, {
                    dispOrder: 3,
                    workTypeCode: 'hư hỏng',
                    workingHoursCode: 'đi làm cho đỡ',
                    days: 3,
                }];

            // Calendar component
            self.yearMonthPicked = ko.observable(201707);
            self.cssRangerYM = {
            };
            self.optionDates = ko.observableArray<OptionDate>([]);
            self.firstDay = 0;
            self.startDate = 1;
            self.endDate = 31;
            self.workplaceId = ko.observable("0");
            self.workplaceName = ko.observable("");
            self.eventDisplay = ko.observable(false);
            self.eventUpdatable = ko.observable(false);
            self.holidayDisplay = ko.observable(true);
            self.cellButtonDisplay = ko.observable(false);

            // Event on yearMonth changed.
            self.yearMonthPicked.subscribe(() => {
                service.getHolidayByListDate(self.getListDateOfMonth()).done(res => {
                    self.listHoliday = res;
                    self.optionDates(self.getOptionDates());
                }).fail(res => {
                    nts.uk.ui.dialog.alert(res.message);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
            });
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.block.invisible();
            $.when(service.find('empId'), service.findWeeklyWorkSetting(), service.getHolidayByListDate(self.getListDateOfMonth()))
                .done(function(patternReflection: service.model.PatternReflection, weeklyWorkSetting: WeeklyWorkSetting, listHoliday) {
                    self.listHoliday = listHoliday;
                    self.weeklyWorkSetting = weeklyWorkSetting;
                    self.patternReflection = new PatternReflection(patternReflection);
                    self.optionDates(self.getOptionDates());
                    dfd.resolve();
                }).fail(res => {
                    nts.uk.ui.dialog.alert(res.message);
                }).always(() => {
                    nts.uk.ui.block.clear();
                });;
            return dfd.promise();
        }

        public forward(): void {
            let self = this;
            self.tien();
            self.optionDates(self.getOptionDates());
        }
        public backward(): void {
            let self = this;
            self.lui();
            self.optionDates(self.getOptionDates());
        }

        public onBtnApplySettingClicked(): void {
            let self = this;
            // Reload calendar
            self.optionDates(self.getOptionDates());
            service.save('empId', ko.toJS(self.patternReflection));
        }

        private getOptionDates(): Array<OptionDate> {
            let self = this;
            let parsedYm = nts.uk.time.formatYearMonth(self.yearMonthPicked());
            let currentDate = moment(parsedYm);
            let lastDateOfMonth = moment(parsedYm).endOf('month');
            let result: Array<OptionDate> = [];

            while (currentDate.isSameOrBefore(lastDateOfMonth)) {
                // Work patterns loop.
                self.dailyPatternSetting.workPatterns.forEach(dailyPatternValue => {
                    let dayOfPattern = 1;
                    // Day of pattern loop.
                    while (dayOfPattern <= dailyPatternValue.days) {
                        // End loop if end of month.
                        if (currentDate.isAfter(lastDateOfMonth)) {
                            break;
                        }

                        // Neu la holiday.
                        let isAHoliday = self.isHoliday(currentDate);
                        if (self.isHolidaySettingChecked() && isAHoliday) {
                            result.push({
                                start: currentDate.format('YYYY-MM-DD'),
                                textColor: 'red',
                                backgroundColor: 'white',
                                listText: [
                                    'Holiday'
                                ]
                            });
                        }
                        // Neu khong phai la holiday
                        else {
                            // Ngay nghi theo luat
                            if (self.isStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayInLaw) {
                                result.push({
                                    start: currentDate.format('YYYY-MM-DD'),
                                    textColor: 'red',
                                    backgroundColor: 'white',
                                    listText: [
                                        'Nghi trong luat'
                                    ]
                                });
                            }
                            // Ngay nghi ngoai luat
                            else if (self.isNonStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayOutrage) {
                                result.push({
                                    start: currentDate.format('YYYY-MM-DD'),
                                    textColor: 'red',
                                    backgroundColor: 'white',
                                    listText: [
                                        'Nghi ngoai luat'
                                    ]
                                });
                            }
                            // Ngay di lam
                            else {
                                // In ra worktype va worktime trong domain neu co data.
                                // Neu khong thi in ra KSM005_43
                                result.push({
                                    start: currentDate.format('YYYY-MM-DD'),
                                    textColor: 'blue',
                                    backgroundColor: 'white',
                                    listText: [
                                        dailyPatternValue.workTypeCode,
                                        dailyPatternValue.workingHoursCode
                                    ]
                                });
                            }
                        }
                        dayOfPattern++;
                        // Reserve dayOfPattern if FillInTheBlank is checked.
                        if (self.isHolidaySettingChecked() &&
                            isAHoliday &&
                            self.isFillInTheBlankChecked()) {
                            dayOfPattern--;
                        }
                        // Next day on calendar.
                        currentDate = currentDate.add(1, 'days');
                    }
                });
            }
            return result;
        }

        private getListDateOfMonth(): Array<string> {
            let self = this;
            let resultList = [];
            let parsedYm = nts.uk.time.formatYearMonth(self.yearMonthPicked());
            let currentDate = moment(parsedYm).startOf('month');
            let endDate = moment(parsedYm).endOf('month');
            while (currentDate.isSameOrBefore(endDate)) {
                resultList.push(currentDate.format('YYYYMMDD'));
                currentDate.add(1, 'days');
            }
            return resultList;
        }

        private getWorkDayDivision(dayOfWeek: number): WorkDayDivision {
            let self = this;
            switch (dayOfWeek) {
                case 0:
                    return self.weeklyWorkSetting.sunday;
                case 1:
                    return self.weeklyWorkSetting.monday;
                case 2:
                    return self.weeklyWorkSetting.tuesday;
                case 3:
                    return self.weeklyWorkSetting.wednesday;
                case 4:
                    return self.weeklyWorkSetting.thursday;
                case 5:
                    return self.weeklyWorkSetting.friday;
                case 6:
                    return self.weeklyWorkSetting.saturday;
                default:
                    return WorkDayDivision.WorkingDay;
            }
        }

        private isStatutorySettingChecked(): boolean {
            let self = this;
            return self.patternReflection.statutorySetting.useClassification();
        }
        private isNonStatutorySettingChecked(): boolean {
            let self = this;
            return self.patternReflection.nonStatutorySetting.useClassification();
        }
        private isHolidaySettingChecked(): boolean {
            let self = this;
            return self.patternReflection.holidaySetting.useClassification();
        }

        /**
         * Check if is last day of month
         * @param: date ('YYYY-MM-DD')
         */
        private isLastDayOfMonth(d: string): boolean {
            let d2 = moment(d);
            let currentMonth = d2.month();
            d2.add(1, 'days');
            return currentMonth !== d2.month();
        }

        private isFirstDayOfMonth(d: string): boolean {
            let d2 = moment(d);
            let currentMonth = d2.month();
            d2.subtract(1, 'days');
            return currentMonth !== d2.month();
        }

        private lui(): void {
            let self = this;
            let temp = self.weeklyWorkSetting.sunday;
            self.weeklyWorkSetting.sunday = self.weeklyWorkSetting.monday;
            self.weeklyWorkSetting.monday = self.weeklyWorkSetting.tuesday;
            self.weeklyWorkSetting.tuesday = self.weeklyWorkSetting.wednesday;
            self.weeklyWorkSetting.wednesday = self.weeklyWorkSetting.thursday;
            self.weeklyWorkSetting.thursday = self.weeklyWorkSetting.friday;
            self.weeklyWorkSetting.friday = self.weeklyWorkSetting.saturday;
            self.weeklyWorkSetting.saturday = temp;
        }
        private tien(): void {
            let self = this;
            let temp = self.weeklyWorkSetting.monday;
            self.weeklyWorkSetting.monday = self.weeklyWorkSetting.sunday;
            self.weeklyWorkSetting.sunday = self.weeklyWorkSetting.saturday;
            self.weeklyWorkSetting.saturday = self.weeklyWorkSetting.friday;
            self.weeklyWorkSetting.friday = self.weeklyWorkSetting.thursday;
            self.weeklyWorkSetting.thursday = self.weeklyWorkSetting.wednesday;
            self.weeklyWorkSetting.wednesday = self.weeklyWorkSetting.tuesday;
            self.weeklyWorkSetting.tuesday = temp;
        }
        private isFillInTheBlankChecked(): boolean {
            let self = this;
            return ReflectionMethod.FillInTheBlank == self.patternReflection.reflectionMethod;
        }

        /**
         * Check if day is holiday
         * @param: day
         */
        private isHoliday(day: moment.Moment): boolean {
            let self = this;
            let result = _.find(self.listHoliday, d => d.date == parseInt(day.format('YYYYMMDD')));
            if (result) {
                return true;
            }
            return false;
        }
    }

    class ItemModelCbb1 {
        code: string;
        name: string;

        constructor(codeCbb1: string, nameCbb1: string) {
            this.code = codeCbb1;
            this.name = nameCbb1;
        }
    }
    class PatternReflection {
        employeeId: string;
        reflectionMethod: ReflectionMethod;
        patternClassification: service.model.PatternClassification;
        statutorySetting: DayOffSetting;
        nonStatutorySetting: DayOffSetting;
        holidaySetting: DayOffSetting;

        constructor(data: service.model.PatternReflection) {
            let self = this;
            self.employeeId = data.employeeId;
            self.reflectionMethod = data.reflectionMethod;
            self.patternClassification = data.patternClassification;
            self.statutorySetting = new DayOffSetting(data.statutorySetting);
            self.nonStatutorySetting = new DayOffSetting(data.nonStatutorySetting);
            self.holidaySetting = new DayOffSetting(data.holidaySetting);
        }
    }
    class DayOffSetting {
        useClassification: KnockoutObservable<boolean>;
        workTypeCode: string;
        constructor(data: service.model.DayOffSetting) {
            this.useClassification = ko.observable(data.useClassification);
            this.workTypeCode = data.workTypeCode;
        }
    }

    interface OptionDate {
        start: string; // YYYY-MM-DD
        textColor: string;
        backgroundColor: string;
        listText: Array<string>;
    }
}