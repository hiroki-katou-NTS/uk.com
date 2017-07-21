module nts.uk.at.view.kdl023.a.viewmodel {

    import WorkDayDivision = service.model.WorkDayDivision;
    import WeeklyWorkSetting = service.model.WeeklyWorkSetting;
    import PublicHoliday = service.model.PublicHoliday;

    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModelCbb1>;
        selectedCode: KnockoutObservable<string>;

        patternReflection: PatternReflection;
        dailyPatternSetting: service.model.DailyPatternSetting;
        weeklyWorkSetting: WeeklyWorkSetting;

        // Calendar component
        calendarData: KnockoutObservable<any>;
        yearMonthPicked: KnockoutObservable<number>;
        cssRangerYM: any;
        optionDates: KnockoutObservableArray<any>;
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
                workTypeCode: '11',
                workingHoursCode: 'nghi',
                days: 1,
            }, {
                    dispOrder: 2,
                    workTypeCode: '22',
                    workingHoursCode: 'lam',
                    days: 2,
                }, {
                    dispOrder: 3,
                    workTypeCode: '33',
                    workingHoursCode: 'lam',
                    days: 3,
                }];

            // Calendar component
            self.yearMonthPicked = ko.observable(201707);
            self.cssRangerYM = {
            };
            self.optionDates = ko.observableArray(self.getOptionDates(201707));
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
            self.yearMonthPicked.subscribe(yearMonth => {
                self.optionDates(self.getOptionDates(yearMonth));
            });
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            $.when(service.find('empId'), service.findWeeklyWorkSetting())
                .done(function(patternReflection: service.model.PatternReflection, weeklyWorkSetting: WeeklyWorkSetting) {
                    self.weeklyWorkSetting = weeklyWorkSetting;
                    self.patternReflection = new PatternReflection(patternReflection);
                    dfd.resolve();
                });
            return dfd.promise();
        }

        public forward(): void {
            let self = this;
            self.optionDates(self.fw(self.optionDates()));
        }
        public backward(): void {
            let self = this;
            self.optionDates(self.bw(self.optionDates()));
        }

        public onBtnApplySettingClicked(): void {
            let self = this;
            service.save('empId', ko.toJS(self.patternReflection));
        }

        private getOptionDates(yearMonth: number): Array<any> {
            let self = this;
            let parsedYm = moment(nts.uk.time.formatYearMonth(yearMonth));
            let currentDate = parsedYm.format('YYYY-MM-DD');
            let isCurrentMonth = true;
            let result = [];
            while (isCurrentMonth) {
                self.dailyPatternSetting.workPatterns.forEach(item => {
                    for (let i; i < item.days; i++) {
                        switch (self.getWorkDayDivision(parsedYm.day())) {
                            case 0:
                                result.push({
                                    start: currentDate,
                                    textColor: 'blue',
                                    backgroundColor: 'white',
                                    listText: [
                                        'DI LAM'
                                    ]
                                });
                                break;
                            case 1:
                                result.push({
                                    start: currentDate,
                                    textColor: 'red',
                                    backgroundColor: 'white',
                                    listText: [
                                        'NGHI'
                                    ]
                                });
                                break;
                            case 2:
                                result.push({
                                    start: currentDate,
                                    textColor: 'red',
                                    backgroundColor: 'white',
                                    listText: [
                                        'NGHI'
                                    ]
                                });
                                break;
                        }
                        if (self.isLastDayOfMonth(currentDate)) {
                            isCurrentMonth = false;
                            break;
                        };
                        currentDate = self.nextDay(currentDate);
                    }
                });
            }
            return result;
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

        private nextDay(d: string): string {
            let d2 = moment(d);
            d2.add(1, 'days');
            return d2.format('YYYY-MM-DD');
        }

        private fw(arr: Array<any>): Array<any> {
            let self = this;
            let arr2 = arr.map(item => {
                let d = moment(item.start);
                if (self.isLastDayOfMonth(item.start)) {
                    d.startOf('month');
                } else {
                    d.add(1, 'days');
                }
                item.start = d.format('YYYY-MM-DD');
                return item;
            });
            return arr2;
        }
        private bw(arr: Array<any>): Array<any> {
            let self = this;
            let arr2 = arr.map(item => {
                let d = moment(item.start);
                if (self.isFirstDayOfMonth(item.start)) {
                    d.endOf('month');
                } else {
                    d.subtract(1, 'days');
                }
                item.start = d.format('YYYY-MM-DD');
                return item;
            });
            return arr2;
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
        reflectionMethod: service.model.ReflectionMethod;
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
}