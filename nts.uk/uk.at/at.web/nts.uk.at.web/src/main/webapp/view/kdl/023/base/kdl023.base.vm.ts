module nts.uk.at.view.kdl023.base.viewmodel {

    import WorkDayDivision = service.model.WorkDayDivision;
    import WeeklyWorkSetting = service.model.WeeklyWorkSetting;
    import PublicHoliday = service.model.PublicHoliday;
    import ReflectionMethod = service.model.ReflectionMethod;
    import WorkType = service.model.WorkType;
    import WorkTime = service.model.WorkTime;
    import DailyPatternSetting = service.model.DailyPatternSetting;

    export abstract class BaseScreenModel {
        dailyPatternList: KnockoutObservableArray<DailyPatternSetting>;
        selectedDailyPatternCode: KnockoutObservable<string>;
        listWorkType: KnockoutObservableArray<WorkType>;
        listWorkTime: KnockoutObservableArray<WorkTime>;
        patternStartDate: moment.Moment;
        patternEndDate: moment.Moment;

        patternReflection: PatternReflection;
        dailyPatternSetting: DailyPatternSetting;
        weeklyWorkSetting: WeeklyWorkSetting;
        listHoliday: Array<any>;
        isReflectionMethodEnable: KnockoutComputed<boolean>;

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
            self.dailyPatternList = ko.observableArray<DailyPatternSetting>([]);
            self.listWorkType = ko.observableArray<WorkType>([]);
            self.listWorkTime = ko.observableArray<WorkTime>([]);
            self.selectedDailyPatternCode = ko.observable('');
            self.selectedDailyPatternCode.subscribe(code => {
                self.loadDailyPatternDetail(code);
            });

            // Calendar component
            self.yearMonthPicked = ko.observable(parseInt(moment().format('YYYYMM'))); // current system date.
            self.cssRangerYM = {
            };
            self.optionDates = ko.observableArray<OptionDate>([]);
            self.firstDay = 0; // sunday.
            self.startDate = 1;
            self.endDate = 31;
            self.workplaceId = ko.observable("0");
            self.workplaceName = ko.observable("");
            self.eventDisplay = ko.observable(false);
            self.eventUpdatable = ko.observable(false);
            self.holidayDisplay = ko.observable(true);
            self.cellButtonDisplay = ko.observable(false);

        }

        /**
         * Start page event.
         */
        public startPage(): JQueryPromise<any> {
            nts.uk.ui.block.invisible();
            let self = this;
            let dfd = $.Deferred();
            $.when(self.loadHolidayList(),
                self.loadWorktypeList(),
                self.loadWorktimeList(),
                self.loadDailyPatternHeader(),
                self.loadWeeklyWorkSetting())
                .done(() => self.loadPatternReflection()
                    .done(() => {
                        // Define isReflectionMethodEnable after patternReflection is loaded.
                        self.isReflectionMethodEnable = ko.computed(() => {
                            return self.patternReflection.statutorySetting.useClassification() ||
                                self.patternReflection.nonStatutorySetting.useClassification() ||
                                self.patternReflection.holidaySetting.useClassification()
                        });
                        self.setSelectedDailyPatternCode();
                        // Xu ly hien thi calendar.
                        self.setPatternRange();
                        self.optionDates(self.getOptionDates());
                        dfd.resolve();
                    })).fail(res => {
                        console.log(res);
                        nts.uk.ui.dialog.alert(res.message);
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
            return dfd.promise();
        }

        /**
         * Forward button clicked
         */
        public forward(): void {
            let self = this;
            self.patternStartDate.add(1, 'days');

            // Reload calendar
            self.optionDates(self.getOptionDates());

            // Set focus control
            $('#component-calendar-kcp006').focus();
        }

        /**
         * Backward button clicked.
         */
        public backward(): void {
            let self = this;
            self.patternStartDate.subtract(1, 'days');

            // Reload calendar
            self.optionDates(self.getOptionDates());

            // Set focus control
            $('#component-calendar-kcp006').focus();
        }

        /**
         * Event when click apply button.
         */
        public onBtnApplySettingClicked(): void {
            let self = this;
            nts.uk.ui.block.invisible();
            // Reload calendar
            self.setPatternRange();
            self.optionDates(self.getOptionDates());
            // Set focus control
            $('#component-calendar-kcp006').focus();
            service.save('empId', ko.toJS(self.patternReflection)).always(() => {
                nts.uk.ui.block.clear();
            });
        }

        /**
         * Load patternReflection.
         */
        private loadPatternReflection(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            service.find('empId').done(function(patternReflection: service.model.PatternReflection) {
                let data;
                // Co data
                if (patternReflection) {
                    data = patternReflection;
                }
                // Khong co data
                else {
                    data = {
                        employeeId: 'empId',
                        reflectionMethod: ReflectionMethod.Overwrite,
                        patternClassification: 1,
                        statutorySetting: {
                            useClassification: false,
                            workTypeCode: self.listWorkType()[0].workTypeCode
                        },
                        nonStatutorySetting: {
                            useClassification: false,
                            workTypeCode: self.listWorkType()[0].workTypeCode
                        },
                        holidaySetting: {
                            useClassification: false,
                            workTypeCode: self.listWorkType()[0].workTypeCode
                        }
                    }
                }
                self.patternReflection = new PatternReflection(data);

                // Resolve.
                dfd.resolve();
            }).fail(() => {
                console.log('failed. trinh duyet chua luu setting nao.');
                dfd.fail();
            });
            return dfd.promise();
        }

        /**
         * Load daily pattern.
         */
        private loadDailyPatternHeader(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            service.findAllPattern().done(function(list: Array<DailyPatternSetting>) {
                self.dailyPatternList(list);
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * Load daily pattern detail.
         */
        private loadDailyPatternDetail(code: string): void {
            let self = this;
            self.dailyPatternSetting = _.find(self.dailyPatternList(), item => item.patternCode == code);
        }

        /**
         * Load weekly work setting.
         */
        private loadWeeklyWorkSetting(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            service.findWeeklyWorkSetting().done(function(weeklyWorkSetting: WeeklyWorkSetting) {
                self.weeklyWorkSetting = weeklyWorkSetting;
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * Load holiday list.
         */
        private loadHolidayList(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            service.getHolidayByListDate(self.getListDateOfMonth()).done(function(list: Array<PublicHoliday>) {
                self.listHoliday = list;
                dfd.resolve();
            });
            return dfd.promise();
        }


        /**
         * Load worktype list.
         */
        private loadWorktypeList(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            service.getAllWorkType().done(function(list: Array<WorkType>) {
                self.listWorkType(list);
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * Load work time list.
         */
        private loadWorktimeList(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            service.getAllWorkTime().done(function(list: Array<WorkTime>) {
                self.listWorkTime(list);
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * Get option dates for calendar.
         * @param: startDate (YYYYMM)
         */
        private getOptionDates(): Array<OptionDate> {
            let self = this;
            let currentDate = moment(self.patternStartDate);
            let firstDateOfMonth = moment(self.patternEndDate).startOf('month');
            let lastDateOfMonth = moment(self.patternEndDate);
            let result: Array<OptionDate> = [];

            // Chay nguoc
            if (currentDate.isAfter(firstDateOfMonth, 'day')) {
                // Previous day on calendar.
                currentDate = currentDate.subtract(1, 'days');
                while (currentDate.isSameOrAfter(firstDateOfMonth, 'day')) {
                    // Work patterns reverse loop.
                    self.dailyPatternSetting.workPatterns.slice().reverse().forEach(dailyPatternValue => {
                        let dayOfPattern = 1;
                        // Day of pattern loop.
                        while (dayOfPattern <= dailyPatternValue.days) {
                            // is current day = day off flag.
                            let isDayoff = false;

                            // Neu la holiday.
                            if (self.isHolidaySettingChecked() && self.isHoliday(currentDate)) {
                                isDayoff = true;
                                result.push({
                                    start: currentDate.format('YYYY-MM-DD'),
                                    textColor: 'red',
                                    backgroundColor: 'white',
                                    listText: [
                                        self.getWorktypeNameByCode(self.patternReflection.holidaySetting.workTypeCode())
                                    ]
                                });
                            }
                            // Neu khong phai la holiday
                            else {
                                // Ngay nghi theo luat
                                if (self.isStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayInLaw) {
                                    isDayoff = true;
                                    result.push({
                                        start: currentDate.format('YYYY-MM-DD'),
                                        textColor: 'red',
                                        backgroundColor: 'white',
                                        listText: [
                                            self.getWorktypeNameByCode(self.patternReflection.statutorySetting.workTypeCode())
                                        ]
                                    });
                                }
                                // Ngay nghi ngoai luat
                                else if (self.isNonStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayOutrage) {
                                    isDayoff = true;
                                    result.push({
                                        start: currentDate.format('YYYY-MM-DD'),
                                        textColor: 'red',
                                        backgroundColor: 'white',
                                        listText: [
                                            self.getWorktypeNameByCode(self.patternReflection.nonStatutorySetting.workTypeCode())
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
                                            self.getWorktypeNameByCode(dailyPatternValue.workTypeCode),
                                            self.getWorktimeNameByCode(dailyPatternValue.workingHoursCode)
                                        ]
                                    });
                                }
                            }
                            dayOfPattern++;
                            // Reserve dayOfPattern if reflection method = overwrite
                            if (isDayoff && !self.isFillInTheBlankChecked()) {
                                dayOfPattern--;
                            }
                            // Previous day on calendar.
                            currentDate = currentDate.subtract(1, 'days');
                        }
                    });
                }
                // Reset current date to pattern start date.
                currentDate = moment(self.patternStartDate);
            }

            // Chay xuoi
            while (currentDate.isSameOrBefore(lastDateOfMonth)) {
                // Work patterns loop.
                self.dailyPatternSetting.workPatterns.forEach(dailyPatternValue => {
                    let dayOfPattern = 1;
                    // Day of pattern loop.
                    while (dayOfPattern <= dailyPatternValue.days) {
                        // is current day = day off flag.
                        let isDayoff = false;

                        // Neu la holiday.
                        if (self.isHolidaySettingChecked() && self.isHoliday(currentDate)) {
                            isDayoff = true;
                            result.push({
                                start: currentDate.format('YYYY-MM-DD'),
                                textColor: 'red',
                                backgroundColor: 'white',
                                listText: [
                                    self.getWorktypeNameByCode(self.patternReflection.holidaySetting.workTypeCode())
                                ]
                            });
                        }
                        // Neu khong phai la holiday
                        else {
                            // Ngay nghi theo luat
                            if (self.isStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayInLaw) {
                                isDayoff = true;
                                result.push({
                                    start: currentDate.format('YYYY-MM-DD'),
                                    textColor: 'red',
                                    backgroundColor: 'white',
                                    listText: [
                                        self.getWorktypeNameByCode(self.patternReflection.statutorySetting.workTypeCode())
                                    ]
                                });
                            }
                            // Ngay nghi ngoai luat
                            else if (self.isNonStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayOutrage) {
                                isDayoff = true;
                                result.push({
                                    start: currentDate.format('YYYY-MM-DD'),
                                    textColor: 'red',
                                    backgroundColor: 'white',
                                    listText: [
                                        self.getWorktypeNameByCode(self.patternReflection.nonStatutorySetting.workTypeCode())
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
                                        self.getWorktypeNameByCode(dailyPatternValue.workTypeCode),
                                        self.getWorktimeNameByCode(dailyPatternValue.workingHoursCode)
                                    ]
                                });
                            }
                        }
                        dayOfPattern++;
                        // Reserve dayOfPattern if reflection method = overwrite
                        if (isDayoff && !self.isFillInTheBlankChecked()) {
                            dayOfPattern--;
                        }
                        // Next day on calendar.
                        currentDate = currentDate.add(1, 'days');
                    }
                });
            }
            return result;
        }

        /**
         * Get worktype name by code.
         */
        private getWorktypeNameByCode(code: string): any {
            let self = this;
            let result = _.find(self.listWorkType(), wt => wt.workTypeCode == code);
            if (result) {
                return result.name;
            }
            return '';
        }

        /**
         * Get worktime name by code.
         */
        private getWorktimeNameByCode(code: string): any {
            let self = this;
            let result = _.find(self.listWorkTime(), wt => wt.code == code);
            if (result) {
                return result.name;
            }
            return '';
        }

        /**
         * Get list date of selected yearmonth.
         */
        private getListDateOfMonth(): Array<string> {
            let self = this;
            let resultList = [];
            let parsedYm = nts.uk.time.formatYearMonth(self.yearMonthPicked());
            let currentDate = moment(parsedYm, 'YYYY-MM').startOf('month');
            let endDate = moment(parsedYm, 'YYYY-MM').endOf('month');
            while (currentDate.isSameOrBefore(endDate)) {
                resultList.push(currentDate.format('YYYYMMDD'));
                currentDate.add(1, 'days');
            }
            return resultList;
        }

        /**
         * Get work day division.
         */
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
         * Get isStatutorySetting checkbox value
         */
        private isStatutorySettingChecked(): boolean {
            let self = this;
            return self.patternReflection.statutorySetting.useClassification();
        }

        /**
         * Get isNonStatutorySetting checkbox value
         */
        private isNonStatutorySettingChecked(): boolean {
            let self = this;
            return self.patternReflection.nonStatutorySetting.useClassification();
        }

        /**
         * Get isHolidaySetting checkbox value
         */
        private isHolidaySettingChecked(): boolean {
            let self = this;
            return self.patternReflection.holidaySetting.useClassification();
        }

        /**
         * Check if isFillInTheBlank radio is selected.
         */
        private isFillInTheBlankChecked(): boolean {
            let self = this;
            return ReflectionMethod.FillInTheBlank == self.patternReflection.reflectionMethod;
        }

        /**
         * Set pattern range.
         */
        private setPatternRange(): void {
            let self = this;
            let parsedYm = nts.uk.time.formatYearMonth(self.yearMonthPicked());
            self.patternStartDate = moment(parsedYm, 'YYYY-MM').startOf('month');
            self.patternEndDate = moment(parsedYm, 'YYYY-MM').endOf('month');
        }

        /**
         * Check if the day is holiday
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

        /**
         * Get param from parent screen.
         */
        private setSelectedDailyPatternCode(): void {
            let self = this;
            // Get pattern code tu man hinh cha.
            let selectedCode = nts.uk.ui.windows.getShared("patternCode");
            if (selectedCode) {
                self.selectedDailyPatternCode(selectedCode);
            }
            else
            // Select first item.
            {
                self.selectedDailyPatternCode(self.dailyPatternList()[0].patternCode);
            }
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
        workTypeCode: KnockoutObservable<string>;
        constructor(data: service.model.DayOffSetting) {
            this.useClassification = ko.observable(data.useClassification);
            this.workTypeCode = ko.observable(data.workTypeCode);
        }
    }

    interface OptionDate {
        start: string; // YYYY-MM-DD
        textColor: string;
        backgroundColor: string;
        listText: Array<string>;
    }
}