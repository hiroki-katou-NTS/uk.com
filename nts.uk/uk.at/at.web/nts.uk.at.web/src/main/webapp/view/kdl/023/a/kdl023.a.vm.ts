module nts.uk.at.view.kdl023.a.viewmodel {
    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModelCbb1>;
        selectedCode: KnockoutObservable<string>;

        patternReflection: PatternReflection;
        dailyPatternSetting: service.model.DailyPatternSetting;

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
            self.optionDates = ko.observableArray(self.getOptionDates());
            self.firstDay = 0;
            self.startDate = 1;
            self.endDate = 31;
            self.workplaceId = ko.observable("0");
            self.workplaceName = ko.observable("");
            self.eventDisplay = ko.observable(false);
            self.eventUpdatable = ko.observable(false);
            self.holidayDisplay = ko.observable(true);
            self.cellButtonDisplay = ko.observable(false);
        }

        private setCalendar(): void {

        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.find('empId')
                .done(function(data: service.model.PatternReflection) {
                    self.patternReflection = new PatternReflection(data);
                    dfd.resolve();
                });
            return dfd.promise();
        }

        public onBtnApplySettingClicked(): void {
            let self = this;
            service.save('empId', ko.toJS(self.patternReflection));
        }

        private getOptionDates(): Array<any> {
            let self = this;
            let currentDate = '2017-07-01';
            let arr = [];
            do {
                self.dailyPatternSetting.workPatterns.forEach(item => {
                    if (item.workTypeCode == '11') {
                        arr.push({
                            start: currentDate,
                            textColor: 'red',
                            backgroundColor: 'white',
                            listText: [
                                'nghi'
                            ]
                        });
                    } else {
                        arr.push({
                            start: currentDate,
                            textColor: 'blue',
                            backgroundColor: 'white',
                            listText: [
                                'lam'
                            ]
                        });
                    }
                    currentDate = self.nextDay(currentDate);
                });
            } while (!self.isLastDayOfMonth(currentDate));
            return arr;
        }

        private isLastDayOfMonth(d: string): boolean {
            let d2 =  moment(d);
            let currentMonth = d2.month();
            d2.add(1, 'days');
            return currentMonth !== d2.month();
        }

        private nextDay(d: string): string {
            let d2 = moment(d);
            d2.add(1, 'days');
            return d2.format('YYYY-MM-DD');
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