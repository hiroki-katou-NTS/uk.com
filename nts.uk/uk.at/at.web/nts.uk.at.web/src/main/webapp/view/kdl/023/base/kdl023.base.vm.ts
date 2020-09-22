module nts.uk.at.view.kdl023.base.viewmodel {

    import WorkDayDivision = service.model.WorkDayDivision;
    import WeeklyWorkSetting = service.model.WeeklyWorkSetting;
    import PublicHoliday = service.model.PublicHoliday;
    import ReflectionMethod = service.model.ReflectionMethod;
    import ReflectionSettingDto = service.model.ReflectionSetting;
    import WorkType = service.model.WorkType;
    import WorkTime = service.model.WorkTime;
    import DailyPatternSetting = service.model.DailyPatternSetting;
    import DailyPatternValue = service.model.DailyPatternValue;

    export abstract class BaseScreenModel {
        dailyPatternList: KnockoutObservableArray<DailyPatternSetting>;
        listWorkType: KnockoutObservableArray<WorkType>;
        listWorkTime: KnockoutObservableArray<WorkTime>;
        patternStartDate: moment.Moment;
        calendarStartDate: moment.Moment;
        calendarEndDate: moment.Moment;

        reflectionSetting: ReflectionSetting;
        dailyPatternSetting: DailyPatternSetting;
        weeklyWorkSetting: WeeklyWorkSetting;
        listHoliday: Array<any>;
        isReflectionMethodEnable: KnockoutComputed<boolean>;
        isOnScreenA: KnockoutObservable<boolean>;
        isMasterDataUnregisterd: KnockoutObservable<boolean>;
        isOutOfCalendarRange: KnockoutObservable<boolean>;
        isDataEmpty: boolean;
        buttonReflectPatternText: KnockoutObservable<string>;
        shared: ReflectionSettingDto;

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
            self.isOnScreenA = ko.observable(true);
            self.isMasterDataUnregisterd = ko.observable(false);
            self.isOutOfCalendarRange = ko.observable(false);
            self.buttonReflectPatternText = ko.observable('');
            self.isDataEmpty = false;
            self.calendarStartDate = moment();
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
                .done(() => {

                    // Check if dailyPatternList has data.
                    if (nts.uk.util.isNullOrEmpty(self.dailyPatternList())) {
                        dfd.resolve();
                        return;
                    }

                    // validate selected code
                    // if selected code is not valid, select first item.
                    _.find(self.dailyPatternList(),
                        pattern => self.reflectionSetting.selectedPatternCd() == pattern.patternCode) != undefined ?
                        self.reflectionSetting.selectedPatternCd() :
                        self.reflectionSetting.selectedPatternCd(self.dailyPatternList()[0].patternCode);

                    // Load daily pattern detail.
                    self.loadDailyPatternDetail(self.reflectionSetting.selectedPatternCd()).done(() => {
                        // Xu ly hien thi calendar.
                        self.optionDates(self.getOptionDates());
                        dfd.resolve();
                    });

                    // Init subscribe.
                    self.reflectionSetting.selectedPatternCd.subscribe(code => {
                        self.loadDailyPatternDetail(code);
                    });

                    // Define isReflectionMethodEnable after patternReflection is loaded.
                    self.isReflectionMethodEnable = ko.computed(() => {
                        return self.reflectionSetting.statutorySetting.useClassification() ||
                            self.reflectionSetting.nonStatutorySetting.useClassification() ||
                            self.reflectionSetting.holidaySetting.useClassification();
                    }).extend({ notify: 'always' });

                    // Set tabindex.
                    self.isReflectionMethodEnable.subscribe(val => {
                        if (val) {
                            $('#reflection-method-radio-group').attr('tabindex', '8');
                        } else {
                            $('#reflection-method-radio-group').attr('tabindex', '-1');
                        }
                    });

                    // Force change to set tab index.
                    self.reflectionSetting.holidaySetting.useClassification.valueHasMutated();

                }).fail(res => {
                    nts.uk.ui.dialog.alert(res.message);
                    dfd.fail();
                }).always(() => {
                    // Set button reflect pattern text.
                    self.setButtonReflectPatternText();

                    // Show message Msg_37 then close dialog.
                    if (self.isDataEmpty) {
                        self.showErrorThenCloseDialog();
                    }

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

            // Do nothing if option dates is empty.
            if (self.isOptionDatesEmpty()) {
                return;
            }

            // Add 1 day to pattern start date.
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

            // Do nothing if option dates is empty. 
            if (self.isOptionDatesEmpty()) {
                return;
            }

            // Subtract 1 day from pattern start date.
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

            self.setPatternRange() // Set pattern's range
                .done(() => {
                    self.optionDates(self.getOptionDates()); // Reload calendar
                    $('#component-calendar-kcp006').focus(); // Set focus control
                }).always(() => {
                    nts.uk.ui.block.clear();
                });

        }

        /**
         * Get default PatternReflection
         */
        private getDefaultPatternReflection(): service.model.ReflectionSetting {
            let self = this;
            return {
                selectedPatternCd: '',
                patternStartDate: self.calendarStartDate.format('YYYY-MM-DD'),
                reflectionMethod: 0, // Overwrite
                statutorySetting: {
                    useClassification: false,
                    workTypeCode: ''
                },
                nonStatutorySetting: {
                    useClassification: false,
                    workTypeCode: ''
                },
                holidaySetting: {
                    useClassification: false,
                    workTypeCode: ''
                }
            }
        };

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
                    self.isDataEmpty = true;
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
                    self.isDataEmpty = true;
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
            // Update pattern start date value of reflection setting.
            self.reflectionSetting.patternStartDate(self.patternStartDate.format('YYYY-MM-DD'));

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
                        if (self.isOnScreenA() && self.isOutOfCalendarRange()) {
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

                //  When is on screen B: only process in calendar's range.
                if (!self.isOnScreenA()) {
                    // Skip to previous day if current date is after calendar's end date.
                    if (currentDate.isAfter(self.calendarEndDate, 'day')) {
                        _.remove(result, item => item === optionDate);
                        // reset flag because today does not count (out of calendar's range)
                        self.isMasterDataUnregisterd(false);
                    }
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

                // Current date reach calendar's end date.
                if (currentDate.isAfter(self.calendarEndDate, 'day')) {
                    // Break loop if is on screen B
                    if (!self.isOnScreenA()) {
                        break;
                    }
                    // Break loop if is on screen A and pattern's total days is in calendar's range.
                    if (self.isOnScreenA() && !self.isOutOfCalendarRange()) {
                        break;
                    }
                    // Or else continue loop.
                }

                //  When is on screen B: only process in calendar's range.
                if (!self.isOnScreenA()) {
                    // Skip to next day if current date is before calendar's start date.
                    if (currentDate.isSameOrBefore(self.calendarStartDate, 'day')) {
                        _.remove(result, item => item === optionDate);
                        // reset flag because today does not count (out of calendar's range)
                        self.isMasterDataUnregisterd(false);
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
                        self.getWorktypeNameByCode(self.reflectionSetting.holidaySetting.workTypeCode())
                    ]
                }
            }

            // Is statutory holiday
            if (self.isStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayInLaw) {
                return {
                    start: currentDate.format('YYYY-MM-DD'),
                    textColor: 'red',
                    backgroundColor: 'white',
                    listText: [
                        self.getWorktypeNameByCode(self.reflectionSetting.statutorySetting.workTypeCode())
                    ]
                }
            }

            // Is non-statutory holiday
            if (self.isNonStatutorySettingChecked() && self.getWorkDayDivision(currentDate.day()) == WorkDayDivision.NonWorkingDayOutrage) {
                return {
                    start: currentDate.format('YYYY-MM-DD'),
                    textColor: 'red',
                    backgroundColor: 'white',
                    listText: [
                        self.getWorktypeNameByCode(self.reflectionSetting.nonStatutorySetting.workTypeCode())
                    ]
                }
            }

            // Is working day.
            let worktype = self.getWorktypeNameByCode(dailyPatternValue.workTypeSetCd);
            let worktime = self.getWorktimeNameByCode(dailyPatternValue.workingHoursCd);
            if (!worktype) {
                self.isMasterDataUnregisterd(true);
                return {
                    start: currentDate.format('YYYY-MM-DD'),
                    textColor: 'blue',
                    backgroundColor: 'white',
                    listText: [
                        nts.uk.resource.getText('KSM005_43') // display no master data
                    ]
                }
            } else if (worktime) { // work time is set => work day
                return {
                    start: currentDate.format('YYYY-MM-DD'),
                    textColor: 'blue',
                    backgroundColor: 'white',
                    listText: [
                        worktype,
                        worktime
                    ]
                }
            } else { // worktime not set => day off
                return {
                    start: currentDate.format('YYYY-MM-DD'),
                    textColor: 'red',
                    backgroundColor: 'white',
                    listText: [
                        worktype
                    ]
                }
            }
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
        private getWorktypeNameByCode(code: string): string {
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
        private getWorktimeNameByCode(code: string): string {
            let self = this;
            if (code) {
                // worktime is set => work day
                let result = _.find(self.listWorkTime(), wt => wt.code == code);
                if (result) {
                    return result.name;
                }
                return nts.uk.resource.getText('KSM005_43'); // display this if no data found
            }
            // worktime not set => off day
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
                resultList.push(currentDate.format('YYYY/MM/DD'));
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
            return self.reflectionSetting.statutorySetting.useClassification();
        }

        /**
         * Get isNonStatutorySetting checkbox value
         */
        private isNonStatutorySettingChecked(): boolean {
            let self = this;
            return self.reflectionSetting.nonStatutorySetting.useClassification();
        }

        /**
         * Get isHolidaySetting checkbox value
         */
        private isHolidaySettingChecked(): boolean {
            let self = this;
            return self.reflectionSetting.holidaySetting.useClassification();
        }

        /**
         * Check option dates data
         */
        public isOptionDatesEmpty(): boolean {
            let self = this;
            return nts.uk.util.isNullOrEmpty(self.optionDates());
        }

        /**
         * Check if isFillInTheBlank radio is selected.
         */
        private isFillInTheBlankChecked(): boolean {
            let self = this;
            return ReflectionMethod.FillInTheBlank == self.reflectionSetting.reflectionMethod();
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
        private setPatternRange(patternStartDate?: string): JQueryPromise<void> {
            let self = this;
            let dfd = $.Deferred<void>();

            // Is on screen A.
            if (self.isOnScreenA()) {
                let parsedYm = nts.uk.time.formatYearMonth(self.yearMonthPicked());

                // Set pattern range.
                self.patternStartDate = moment(parsedYm, 'YYYY-MM').startOf('month');

                // Set calendar range.
                self.calendarStartDate = moment(self.patternStartDate);
                self.calendarEndDate = moment(self.patternStartDate).endOf('month');

                // Set pattern start date if has been set.
                if (patternStartDate) {
                    self.patternStartDate = moment(patternStartDate, 'YYYY-MM-DD');
                }

                // Load holiday list.
                self.loadHolidayList().done(() => {
                    dfd.resolve();
                });
            }

            // Is on screen B.
            else {
                // Reset pattern range.
                self.patternStartDate = moment(self.shared.patternStartDate);
                if (patternStartDate) {
                    self.patternStartDate = moment(patternStartDate, 'YYYY-MM-DD');
                }
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
            self.shared = nts.uk.ui.windows.getShared("reflectionSetting");

            // Get default value if data not exist.
            if (!self.shared) {
                self.shared = self.getDefaultPatternReflection();
            }

            // Init patternReflection setting.
            self.reflectionSetting = new ReflectionSetting(self.shared);

            // Is on screen B.
            if (self.shared.calendarStartDate && self.shared.calendarEndDate) {
                self.isOnScreenA(false);

                // Set calendar range.
                self.calendarStartDate = moment(self.shared.calendarStartDate, 'YYYY-MM-DD');
                self.calendarEndDate = moment(self.shared.calendarEndDate, 'YYYY-MM-DD');

                // Date range must <= 31 days
                // If end date parameter out of range -> set end date to 31 days after start date parameter.
                let range = moment.duration(self.calendarEndDate.diff(self.calendarStartDate));
                if (range.asDays() > 31) {
                    self.calendarEndDate = moment(self.calendarStartDate).add(1, 'months').subtract(1, 'days');
                }
                self.startDate = self.calendarStartDate.date();
                self.endDate = self.calendarEndDate.date();
                self.yearMonthPicked(parseInt(self.calendarStartDate.format('YYYYMM')));

                // Set pattern range.
                self.setPatternRange(self.shared.patternStartDate);

                // Load holiday list.
                self.loadHolidayList().done(() => dfd.resolve());

            }

            // Is on screen A
            else {
                self.isOnScreenA(true);
                self.setPatternRange(self.shared.patternStartDate).done(() => dfd.resolve());
            }

            return dfd.promise();
        }

    }

    export class ReflectionSetting {
        calendarStartDate: KnockoutObservable<string>;
        calendarEndDate: KnockoutObservable<string>;
        selectedPatternCd: KnockoutObservable<string>;
        patternStartDate: KnockoutObservable<string>;
        reflectionMethod: KnockoutObservable<ReflectionMethod>;
        statutorySetting: DayOffSetting;
        nonStatutorySetting: DayOffSetting;
        holidaySetting: DayOffSetting;

        constructor(data: service.model.ReflectionSetting) {
            let self = this;
            self.calendarStartDate = ko.observable(data.calendarStartDate ? data.calendarStartDate : '');
            self.calendarEndDate = ko.observable(data.calendarEndDate ? data.calendarEndDate : '');
            self.patternStartDate = ko.observable(data.patternStartDate);
            self.selectedPatternCd = ko.observable(data.selectedPatternCd);
            self.reflectionMethod = ko.observable(data.reflectionMethod);
            self.statutorySetting = new DayOffSetting(data.statutorySetting);
            self.nonStatutorySetting = new DayOffSetting(data.nonStatutorySetting);
            self.holidaySetting = new DayOffSetting(data.holidaySetting);
        }

        public static newSetting(): ReflectionSetting {
            let newSetting = <service.model.ReflectionSetting>{};
            newSetting.calendarStartDate = moment().startOf('month').format('YYYY-MM-DD');
            newSetting.calendarEndDate = moment().endOf('month').format('YYYY-MM-DD');
            newSetting.selectedPatternCd = '';
            newSetting.patternStartDate = newSetting.calendarStartDate;
            newSetting.reflectionMethod = 0;
            let dummy = <service.model.DayOffSetting>{};
            dummy.useClassification = false;
            dummy.workTypeCode = '';
            newSetting.statutorySetting = dummy;
            newSetting.nonStatutorySetting = dummy;
            newSetting.holidaySetting = dummy;

            return new ReflectionSetting(newSetting);
        }

        public fromDto(dto: service.model.ReflectionSetting) {
            let self = this;
            if (dto.calendarStartDate && dto.calendarEndDate) {
                self.calendarStartDate(dto.calendarStartDate);
                self.calendarEndDate(dto.calendarEndDate);
            }
            self.selectedPatternCd(dto.selectedPatternCd);
            self.patternStartDate(dto.patternStartDate);
            self.reflectionMethod(dto.reflectionMethod);
            self.statutorySetting.fromDto(dto.statutorySetting);
            self.nonStatutorySetting.fromDto(dto.nonStatutorySetting);
            self.holidaySetting.fromDto(dto.holidaySetting);
        }
    }
    class DayOffSetting {
        useClassification: KnockoutObservable<boolean>;
        workTypeCode: KnockoutObservable<string>;
        constructor(data: service.model.DayOffSetting) {
            this.useClassification = ko.observable(data.useClassification);
            this.workTypeCode = ko.observable(data.workTypeCode);
        }

        public fromDto(dto: service.model.DayOffSetting) {
            let self = this;
            self.useClassification(dto.useClassification);
            self.workTypeCode(dto.workTypeCode);
        }
    }

    interface OptionDate {
        start: string; // YYYY-MM-DD
        textColor: string;
        backgroundColor: string;
        listText: Array<string>;
    }
}