module nts.uk.at.view.kdl023.base.viewmodel {

    import WorkDayDivision = service.model.WorkDayDivision;
    import WeeklyWorkSetting = service.model.WeeklyWorkSetting;
    import PublicHoliday = service.model.PublicHoliday;
    import ReflectionMethod = service.model.ReflectionMethod;
    import WorkType = service.model.WorkType;
    import WorkTime = service.model.WorkTime;
    import DailyPatternSetting = service.model.DailyPatternSetting;
    import DailyPatternValue = service.model.DailyPatternValue;

    export abstract class BaseScreenModel {
        dailyPatternList: KnockoutObservableArray<DailyPatternSetting>;
        selectedDailyPatternCode: KnockoutObservable<string>;
        listWorkType: KnockoutObservableArray<WorkType>;
        listWorkTime: KnockoutObservableArray<WorkTime>;
        patternStartDate: moment.Moment;
        patternEndDate: moment.Moment;
        calendarStartDate: moment.Moment;
        calendarEndDate: moment.Moment;

        patternReflection: PatternReflection;
        dailyPatternSetting: DailyPatternSetting;
        weeklyWorkSetting: WeeklyWorkSetting;
        listHoliday: Array<any>;
        isReflectionMethodEnable: KnockoutComputed<boolean>;
        isOnScreenA: KnockoutObservable<boolean>;
        isMasterDataUnregisterd: KnockoutObservable<boolean>;
        isOutOfCalendarRange: KnockoutObservable<boolean>;
        buttonReflectPatternText: KnockoutObservable<string>;

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
            self.isOnScreenA = ko.observable(true);
            self.isMasterDataUnregisterd = ko.observable(false);
            self.isOutOfCalendarRange = ko.observable(false);
            self.buttonReflectPatternText = ko.observable('');

            // Calendar component
            self.yearMonthPicked = ko.observable(parseInt(moment().format('YYYYMM'))); // default: current system date.
            self.cssRangerYM = {
            };
            self.optionDates = ko.observableArray<OptionDate>([]);
            self.firstDay = 0; // default: sunday.
            self.startDate = 1; // default: first date of month.
            self.endDate = 31; // default: last date of month.
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

            // Load data.
            $.when(self.getParamFromCaller(), // Get param from parent screen.
                self.loadWorktypeList(), // Load worktype list.
                self.loadWorktimeList(), // Load worktime list.
                self.loadDailyPatternHeader(), // Load daily pattern header.
                self.loadWeeklyWorkSetting()) // Load weekly work setting.
                .done(() => self.loadPatternReflection() // Load pattern reflection.
                    .done(() => {

                        // Set button reflect pattern text.
                        self.setButtonReflectPatternText();

                        // Check if dailyPatternList has data.
                        if (!self.dailyPatternList() || !self.dailyPatternList()[0]) {
                            dfd.resolve();
                            return;
                        }

                        // Select first daily pattern if none selected.
                        if (!self.selectedDailyPatternCode()) {
                            self.selectedDailyPatternCode(self.dailyPatternList()[0].patternCode);
                        }

                        // Load daily pattern detail.
                        self.loadDailyPatternDetail(self.selectedDailyPatternCode()).done(() => {
                            dfd.resolve();
                        });

                        // Init subscribe.
                        self.selectedDailyPatternCode.subscribe(code => {
                            self.loadDailyPatternDetail(code);
                        });

                        // Define isReflectionMethodEnable after patternReflection is loaded.
                        self.isReflectionMethodEnable = ko.computed(() => {
                            return self.patternReflection.statutorySetting.useClassification() ||
                                self.patternReflection.nonStatutorySetting.useClassification() ||
                                self.patternReflection.holidaySetting.useClassification();
                        }).extend({ notify: 'always' });

                        // Set tabindex.
                        self.isReflectionMethodEnable.subscribe(val => {
                            if (val) {
                                $('#reflection-method-radio-group').attr('tabindex', '5');
                            } else {
                                $('#reflection-method-radio-group').attr('tabindex', '-1');
                            }
                        });

                        // Force change to set tab index.
                        self.patternReflection.holidaySetting.useClassification.valueHasMutated();

                    })).fail(res => {
                        nts.uk.ui.dialog.alert(res.message);
                        dfd.fail();
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
            return dfd.promise();
        }

        /**
         * Close dialog
         */
        public closeDialog(): void {
            nts.uk.ui.windows.close();
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

            $.when(self.setPatternRange(), // Set pattern's range
                service.save(self.getDomainKey(), ko.toJS(self.patternReflection))) // Save pattern reflection domain.
                .done(() => {
                    self.optionDates(self.getOptionDates()); // Reload calendar
                    $('#component-calendar-kcp006').focus(); // Set focus control
                }).always(() => {
                    nts.uk.ui.block.clear();
                });

        }

        /**
         * Load patternReflection.
         */
        private loadPatternReflection(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            service.find(self.getDomainKey()).done(function(patternReflection: service.model.PatternReflection) {
                let data;
                // Data found.
                if (patternReflection) {
                    data = patternReflection;
                }
                // Data not found
                else {
                    data = self.getDefaultPatternReflection(); // Set default data.
                }

                // Init patternReflection
                self.patternReflection = new PatternReflection(data);

                // Resolve.
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * Abstract method get key
         */
        abstract getDomainKey(): string;

        /**
         * Abstract method get default PatternReflection
         */
        abstract getDefaultPatternReflection(): service.model.PatternReflection;

        /**
         * Load daily pattern.
         */
        private loadDailyPatternHeader(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();
            service.findAllPattern().done(function(list: Array<DailyPatternSetting>) {
                if (list && list.length > 0) {
                    self.dailyPatternList(list);
                } else {
                    self.showErrorThenCloseDialog();
                }
                dfd.resolve();
            }).fail(() => {
                self.showErrorThenCloseDialog();
                dfd.fail();
            });
            return dfd.promise();
        }

        /**
         * Load daily pattern detail.
         */
        private loadDailyPatternDetail(code: string): JQueryPromise<void> {
            nts.uk.ui.block.invisible();
            let self = this;
            let dfd = $.Deferred<void>();
            service.findPatternByCode(code).done(res => {
                self.dailyPatternSetting = res;
                dfd.resolve();
            }).always(() => {
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
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
            service.getHolidayByListDate(self.getListDateOnCalendar()).done(function(list: Array<PublicHoliday>) {
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
                if (list && list.length > 0) {
                    self.listWorkType(list);
                } else {
                    self.showErrorThenCloseDialog();
                }
                dfd.resolve();
            }).fail(() => {
                self.showErrorThenCloseDialog();
                dfd.fail();
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

            // Get calendar's range.
            let range = self.calendarEndDate.diff(self.calendarStartDate, 'days') + 1;

            // Reset flag.
            self.isMasterDataUnregisterd(false);

            let currentDate = moment(self.patternStartDate);
            let result: Array<OptionDate> = [];

            // Backward processing
            if (currentDate.isAfter(self.calendarStartDate, 'day')) {
                // Previous day on calendar.
                currentDate = currentDate.subtract(1, 'days');

                while (currentDate.isSameOrAfter(self.calendarStartDate, 'day')) { // Loop until reach calendar start date.
                    // Reversed list dailyPatternValue loop.
                    let listDailyPatternVal;
                    if (self.dailyPatternSetting.dailyPatternVals) {
                        listDailyPatternVal = self.dailyPatternSetting.dailyPatternVals.slice().reverse();
                    }

                    // Master data is registered.
                    if (listDailyPatternVal && listDailyPatternVal.length > 0) {
                        listDailyPatternVal.some(dailyPatternValue => {
                            result = result.concat(self.loopBackwardPatternDays(dailyPatternValue, currentDate));

                            // Break loop condition.
                            let isLoopEnd = currentDate.isBefore(self.calendarStartDate, 'day');
                            return isLoopEnd;
                        });
                    }

                    // Master data is unregistered.
                    else {
                        let dailyPatternValue = {
                            dispOrder: undefined,
                            workTypeSetCd: undefined,
                            workingHoursCd: undefined,
                            days: range
                        }
                        result = result.concat(self.loopBackwardPatternDays(dailyPatternValue, currentDate));
                    }
                }
                // Reset current date to pattern start date.
                currentDate = moment(self.patternStartDate);
            }

            // Forward processing
            while (currentDate.isSameOrBefore(self.calendarEndDate, 'day')) { // Loop until reach calendar end date.
                // List dailyPatternValue loop.
                let listDailyPatternVal = self.dailyPatternSetting.dailyPatternVals;

                // Set is out of calendar's range observable.
                self.setIsOutOfCalendarRange(listDailyPatternVal);

                // Master data is registered.
                if (listDailyPatternVal && listDailyPatternVal.length > 0) {
                    listDailyPatternVal.some(dailyPatternValue => {
                        result = result.concat(self.loopForwardPatternDays(dailyPatternValue, currentDate));

                        // Break loop condition.
                        let isLoopEnd = false;

                        // Break loop if current date reach calendar's end date.
                        isLoopEnd = currentDate.isAfter(self.calendarEndDate, 'day');

                        // If pattern's total days is out of calendar's range.
                        if (self.isOutOfCalendarRange()) {
                            isLoopEnd = false; // continue to loop.
                        }

                        return isLoopEnd;
                    });
                }

                // Master data is unregistered.
                else {
                    let dailyPatternValue = {
                        dispOrder: undefined,
                        workTypeSetCd: undefined,
                        workingHoursCd: undefined,
                        days: range
                    }
                    result = result.concat(self.loopForwardPatternDays(dailyPatternValue, currentDate));
                }
            }
            return result;
        }

        /**
         * Loop backward daily pattern days.
         */
        private loopBackwardPatternDays(dailyPatternValue: DailyPatternValue, currentDate: moment.Moment): Array<OptionDate> {
            let self = this;
            let result = [];
            let dayOfPattern = 1;

            // Day of pattern loop.
            while (dayOfPattern <= dailyPatternValue.days) {

                // Break loop if current date reach calendar's start date.
                if (currentDate.isBefore(self.calendarStartDate, 'day')) {
                    break;
                }

                // Get display text and push to option date list.
                let optionDate = self.getDisplayText(dailyPatternValue, currentDate);
                result.push(optionDate);

                // Next day of pattern.
                dayOfPattern++;

                // Reserve dayOfPattern if reflection method = fill in the blank
                if (optionDate.textColor == 'red' // Is holiday 
                    && self.isFillInTheBlankChecked()) {
                    dayOfPattern--;
                }

                // Previous day on calendar.
                currentDate = currentDate.subtract(1, 'days');
            }
            return result;
        }

        /**
         * Get total days of pattern.
         */
        private getTotalDaysOfPattern(listDailyPatternVal: Array<DailyPatternValue>): number {
            let sum = 0;
            _.forEach(listDailyPatternVal, i => {
                sum += i.days;
            });
            return sum;
        }

        /**
         * Loop forward daily pattern days.
         */
        private loopForwardPatternDays(dailyPatternValue: DailyPatternValue, currentDate: moment.Moment): Array<OptionDate> {
            let self = this;
            let result = [];
            let dayOfPattern = 1;

            // Day of pattern loop.
            while (dayOfPattern <= dailyPatternValue.days) {

                // Get display text and push to option date list.
                let optionDate = self.getDisplayText(dailyPatternValue, currentDate);
                result.push(optionDate);

                // Next day of pattern.
                dayOfPattern++;

                // Reserve dayOfPattern if reflection method = fill in the blank
                if (optionDate.textColor == 'red' // Is holiday 
                    && self.isFillInTheBlankChecked()) {
                    dayOfPattern--;
                }

                // Next day on calendar.
                currentDate = currentDate.add(1, 'days');

                // Break loop if pattern's total days is in range and current date reach calendar's end date.
                if (!self.isOutOfCalendarRange() && currentDate.isAfter(self.calendarEndDate, 'day')) {
                    break;
                }

                //  When is on screen B
                if (!self.isOnScreenA()) {
                    // Skip to next day if current date is before calendar's start date.
                    if (currentDate.isSameOrBefore(self.calendarStartDate, 'day')) {
                        _.remove(result, item => item === optionDate);
                    }
                }
            }
            return result;
        }

        /**
         * Get display text.
         */
        private getDisplayText(dailyPatternValue: DailyPatternValue, currentDate: moment.Moment): OptionDate {
            let self = this;

            // Is holiday
            if (self.isHolidaySettingChecked() && self.isHoliday(currentDate)) {
                return {
                    start: currentDate.format('YYYY-MM-DD'),
                    textColor: 'red',
                    backgroundColor: 'white',
                    listText: [
                        self.getWorktypeNameByCode(self.patternReflection.holidaySetting.workTypeCode())
                    ]
                };
            }

            // Is statutory holiday
            if (self.isStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayInLaw) {
                return {
                    start: currentDate.format('YYYY-MM-DD'),
                    textColor: 'red',
                    backgroundColor: 'white',
                    listText: [
                        self.getWorktypeNameByCode(self.patternReflection.statutorySetting.workTypeCode())
                    ]
                };
            }

            // Is non-statutory holiday
            if (self.isNonStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayOutrage) {
                return {
                    start: currentDate.format('YYYY-MM-DD'),
                    textColor: 'red',
                    backgroundColor: 'white',
                    listText: [
                        self.getWorktypeNameByCode(self.patternReflection.nonStatutorySetting.workTypeCode())
                    ]
                };
            }

            // Is working day.
            let noSetting = nts.uk.resource.getText('KSM005_43'); // display this if no data found.
            let worktype = self.getWorktypeNameByCode(dailyPatternValue.workTypeSetCd);
            let worktime = self.getWorktimeNameByCode(dailyPatternValue.workingHoursCd);
            if (!worktype || !worktime) {
                self.isMasterDataUnregisterd(true);
            }
            return {
                start: currentDate.format('YYYY-MM-DD'),
                textColor: 'blue',
                backgroundColor: 'white',
                listText: [
                    worktype ? worktype : noSetting,
                    worktime ? worktime : noSetting
                ]
            };
        }

        /**
         * Set button reflect pattern text.
         */
        private setButtonReflectPatternText(): void {
            let self = this;

            // Is on screen A
            if (self.isOnScreenA()) {
                self.buttonReflectPatternText(nts.uk.resource.getText('KDL023_13'));
            }

            // Is on screen B
            else {
                self.buttonReflectPatternText(nts.uk.resource.getText('KDL023_20'));
            }

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
         * Get list date displaying on calendar.
         */
        private getListDateOnCalendar(): Array<string> {
            let self = this;
            let resultList = [];
            let currentDate = moment(self.calendarStartDate);
            while (currentDate.isSameOrBefore(self.calendarEndDate)) {
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
         * Set is out of calendar's range observable.
         */
        private setIsOutOfCalendarRange(listDailyPatternVal: Array<DailyPatternValue>): void {
            let self = this;

            // Get calendar's range.
            let calendarRange = self.calendarEndDate.diff(self.calendarStartDate, 'days') + 1;

            // Get total days.
            let totalDays = self.getTotalDaysOfPattern(listDailyPatternVal);

            // Set value
            self.isOutOfCalendarRange(false);
            if (totalDays > calendarRange) {
                self.isOutOfCalendarRange(true);
            }
        }

        /**
         * Set pattern range.
         */
        private setPatternRange(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();

            // Is on screen A.
            if (self.isOnScreenA()) {
                let parsedYm = nts.uk.time.formatYearMonth(self.yearMonthPicked());

                // Set pattern range.
                self.patternStartDate = moment(parsedYm, 'YYYY-MM').startOf('month');
                self.patternEndDate = moment(parsedYm, 'YYYY-MM').endOf('month');

                // Set calendar range.
                self.calendarStartDate = moment(self.patternStartDate);
                self.calendarEndDate = moment(self.patternEndDate);

                // Load holiday list.
                self.loadHolidayList().done(() => {
                    dfd.resolve();
                });
            }

            // Is on screen B.
            else {
                // Reset pattern range.
                self.patternStartDate = moment(self.calendarStartDate);
                self.patternEndDate = moment(self.calendarEndDate);
                dfd.resolve();
            }

            return dfd.promise();
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
         * Show error then close dialog.
         */
        private showErrorThenCloseDialog(): void {
            let self = this;
            nts.uk.ui.dialog.alertError({ messageId: "Msg_37" }).then(() => {
                self.closeDialog();
            });
        }

        /**
         * Get param from caller (parent) screen.
         */
        private getParamFromCaller(): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();

            // Get param from caller screen.
            let selectedCode = nts.uk.ui.windows.getShared("patternCode");
            let startDate = nts.uk.ui.windows.getShared("startDate");
            let endDate = nts.uk.ui.windows.getShared("endDate");

            if (selectedCode) {
                self.selectedDailyPatternCode(selectedCode);
            }

            // Is on screen B.
            if (startDate && endDate) {
                self.isOnScreenA(false);

                // Set calendar range.
                self.calendarStartDate = moment(startDate, 'YYYY-MM-DD'); //TODO: man hinh cha tra ve theo format nao?
                self.calendarEndDate = moment(endDate, 'YYYY-MM-DD');
                self.startDate = self.calendarStartDate.date();
                self.endDate = self.calendarEndDate.date();
                self.yearMonthPicked(parseInt(self.calendarStartDate.format('YYYYMM')));

                // Set pattern range.
                self.setPatternRange();

                // Load holiday list.
                self.loadHolidayList().done(() => dfd.resolve());

            }

            // Is on screen A
            else {
                self.isOnScreenA(true);
                self.setPatternRange().done(() => dfd.resolve());
            }

            return dfd.promise();
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