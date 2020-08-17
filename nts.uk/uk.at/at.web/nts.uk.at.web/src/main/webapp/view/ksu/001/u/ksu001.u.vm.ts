// Refercen definition of any library
/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksu001.u {
    const Paths = {
        GET_PUBLIC_INFO_ORG: 'screen/at/shiftmanagement/shifttable/getPublicInfoOrg',
        REGISTER: 'ctx/schedule/shiftmanagement/shifttable/register'
    };
    const Ksu001u = {
        PUBLIC: '公開',
        EDIT: '編集中',
        TEXT_COLOR_PUB: '#4f6228',
        BG_COLOR_PUB: '#92d050',
        TEXT_COLOR_PRE_PUB: '#974706',
        BG_COLOR_PRE_PUB: '#d8e4bc'
    };

    @bean()
    class Ksu001UViewModel extends ko.ViewModel {
        calendarData: KnockoutObservable<any>;
        yearMonthPicked: KnockoutObservable<number> = ko.observable();
        cssRangerYM: any = ko.observable();
        optionDates: KnockoutObservableArray<any> = ko.observableArray([]);
        firstDay: KnockoutObservable<number>;
        yearMonth: KnockoutObservable<number>;
        startDate: number;
        endDate: number;
        workplaceId: KnockoutObservable<string> = ko.observable();
        eventDisplay: KnockoutObservable<boolean> = ko.observable(false);
        eventUpdatable: KnockoutObservable<boolean>;
        holidayDisplay: KnockoutObservable<boolean>;
        cellButtonDisplay: KnockoutObservable<boolean> = ko.observable(false);
        showCalendarHeader: KnockoutObservable<boolean>;
        workplaceName: KnockoutObservable<string> = ko.observable('');
        lstStep: KnockoutObservableArray<any>;
        stepSelected: any;
        publicDate: KnockoutObservable<string> = ko.observable("");
        editDate: KnockoutObservable<string> = ko.observable("");
        newPublicDate: KnockoutObservable<string> = ko.observable("");
        newEditDate: KnockoutObservable<string> = ko.observable("");
        unit: KnockoutObservable<number> = ko.observable();
        workplaceGroupId: KnockoutObservable<string> = ko.observable('');
        baseDate: KnockoutObservable<string> = ko.observable('');
        constructor(params: any) {
            super();
            const self = this;       
            self.firstDay = ko.observable(0);
            self.eventUpdatable = ko.observable(false);
            self.holidayDisplay = ko.observable(false);
            self.showCalendarHeader = ko.observable(true);

            self.lstStep = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KSU001_4010') },
                { code: '1', name: nts.uk.resource.getText('KSU001_4011') }
            ]);
            self.stepSelected = ko.observable("0");
            self.startDate = 1;
            self.endDate = 31; 
            self.baseDate(params.baseDate);
            self.unit(params.unit);
            self.workplaceId(params.workplaceId);
            self.workplaceGroupId(params.workplaceGroupId);
            // self.loadPubDateInfo();
            // self.clickCalendar();
        }

        created(){
            const self = this;
            self.loadPubDateInfo();
            self.clickCalendar();
        }

        loadPubDateInfo(): void {
            const self = this;            
            let data ={
                baseDate : self.baseDate(),
                unit: self.unit(),
                workplaceId: self.workplaceId(),
                workplaceGroupId: self.workplaceGroupId()
            };
            self.$ajax(Paths.GET_PUBLIC_INFO_ORG, data).then((data: IPublicInfoOrg) => {
                self.unit(data.unit);
                self.workplaceId(data.workplaceId);
                self.workplaceGroupId(data.workplaceGroupId);
                self.workplaceName(data.displayName);
                self.publicDate(data.publicDate);
                self.editDate(data.editDate);

                let endPubDate = self.publicDate();
                let editDate = "";
                editDate = self.editDate();

                let splitEndDate = endPubDate.split('/');
                let splitEditDate = [];
                let endPubDateInt = parseInt(splitEndDate[2]);
                let endEditDateInt = 0;
                self.yearMonthPicked(parseInt(splitEndDate[0] + splitEndDate[1]));

                self.publicDate(self.formatDate(new Date(parseInt(splitEndDate[0]), parseInt(splitEndDate[1]) - 1, parseInt(splitEndDate[2]))));
                self.newPublicDate(self.formatDate(new Date(parseInt(splitEndDate[0]), parseInt(splitEndDate[1]) - 1, parseInt(splitEndDate[2]))));
                if (editDate) {
                    splitEditDate = editDate.split('/');
                    endEditDateInt = parseInt(splitEditDate[2]);
                    self.editDate(self.formatDate(new Date(parseInt(splitEditDate[0]), parseInt(splitEditDate[1]) - 1, parseInt(splitEditDate[2]))));
                    self.newEditDate(self.formatDate(new Date(parseInt(splitEditDate[0]), parseInt(splitEditDate[1]) - 1, parseInt(splitEditDate[2]))));
                    for (let i = 0; i < endPubDateInt; i++) {
                        let date = self.formatDate(new Date(parseInt(splitEndDate[0]), parseInt(splitEndDate[1]) - 1, parseInt(splitEndDate[2]) - i));
                        let existDate = self.checkExistDate(date);
                        if (existDate) {
                            self.removeExistDate(existDate);
                        }
                        if (i <= endPubDateInt - endEditDateInt) {
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.EDIT]
                            });
                        } else {
                            if (existDate) {
                                self.removeExistDate(existDate);
                            }
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }
                } else {
                    for (let i = 0; i < endPubDateInt; i++) {
                        let date = self.formatDate(new Date(parseInt(splitEndDate[0]), parseInt(splitEndDate[1]) - 1, parseInt(splitEndDate[2]) - i));
                        let existDate = self.checkExistDate(date);
                        if (existDate) {
                            self.removeExistDate(existDate);
                        }
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    }
                }
            });
        }

        public clickCalendar(): void {
            const self = this;
            $("#calendar").ntsCalendar("init", {
                cellClick: function (dateClick) {
                    let publicDate = self.publicDate();
                    let publicDateSplit = publicDate.split('-');
                    let editDate = self.editDate();
                    let editDateSplit = [];
                    let dateClickSplit = dateClick.split('-');
                    let offset = 0;
                    if (self.publicDate() == self.editDate()) {
                        offset = 1;
                    }
                    // check cell date click is inner or outer period public date
                    let existDate = self.checkExistDate(dateClick);
                    // if date click on between period of public date
                    if (editDate) {
                        editDateSplit = editDate.split('-');
                        if (existDate) {
                            // set new edit date is date click cell calendar
                            self.editDate(self.formatDate(new Date(parseInt(dateClickSplit[0]), parseInt(dateClickSplit[1]) - 1, parseInt(dateClickSplit[2]))));
                            // check cell date is public or edit
                            if (existDate.textColor === Ksu001u.TEXT_COLOR_PUB && existDate.backgroundColor === Ksu001u.BG_COLOR_PUB) {
                                for (let i = 0; i <= parseInt(publicDateSplit[2]) - parseInt(dateClickSplit[2]); i++) {
                                    let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) - i));
                                    if (self.checkExistDate(date)) {
                                        self.removeExistDate(self.checkExistDate(date));
                                    }
                                    self.optionDates.push({
                                        start: date,
                                        textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                        backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                        listText: [Ksu001u.EDIT]
                                    });
                                }
                            } else if (existDate.textColor === Ksu001u.TEXT_COLOR_PRE_PUB) {
                                for (let i = 0; i <= parseInt(dateClickSplit[2]) - parseInt(editDateSplit[2]); i++) {
                                    let date = self.formatDate(new Date(parseInt(dateClickSplit[0]), parseInt(dateClickSplit[1]) - 1, parseInt(dateClickSplit[2]) - i));
                                    if (self.checkExistDate(date)) {
                                        self.removeExistDate(self.checkExistDate(date));
                                    }
                                    self.optionDates.push({
                                        start: date,
                                        textColor: Ksu001u.TEXT_COLOR_PUB,
                                        backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                        listText: [Ksu001u.PUBLIC]
                                    });
                                }
                            }
                        } else {
                            let periodPub = parseInt(dateClickSplit[2]) - parseInt(publicDateSplit[2]);
                            let periodEdit = 0;
                            if (self.editDate() != "") {
                                periodEdit = parseInt(dateClickSplit[2]) - parseInt(editDateSplit[2])
                            }
                            if (periodPub <= periodEdit) {
                                self.editDate("");
                            }

                            self.publicDate(self.formatDate(new Date(parseInt(dateClickSplit[0]), parseInt(dateClickSplit[1]) - 1, parseInt(dateClickSplit[2]))));
                            for (let i = 0; i <= (periodPub > periodEdit ? periodPub : periodEdit) + offset; i++) {
                                let date = self.formatDate(new Date(parseInt(dateClickSplit[0]), parseInt(dateClickSplit[1]) - 1, parseInt(dateClickSplit[2]) - i));
                                if (self.checkExistDate(date)) {
                                    self.removeExistDate(self.checkExistDate(date));
                                }
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                    listText: [Ksu001u.PUBLIC]
                                });
                            }
                        }

                    } else {
                        if (existDate) {
                            // set new edit date is date click cell calendar
                            self.editDate(self.formatDate(new Date(parseInt(dateClickSplit[0]), parseInt(dateClickSplit[1]) - 1, parseInt(dateClickSplit[2]))));

                            for (let i = 0; i <= parseInt(publicDateSplit[2]) - parseInt(dateClickSplit[2]); i++) {
                                let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) - i));
                                if (self.checkExistDate(date)) {
                                    self.removeExistDate(self.checkExistDate(date));
                                }
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                    listText: [Ksu001u.EDIT]
                                });
                            }

                        } else {
                            let periodPub = parseInt(dateClickSplit[2]) - parseInt(publicDateSplit[2]);

                            self.publicDate(self.formatDate(new Date(parseInt(dateClickSplit[0]), parseInt(dateClickSplit[1]) - 1, parseInt(dateClickSplit[2]))));
                            for (let i = 0; i < periodPub; i++) {
                                let date = self.formatDate(new Date(parseInt(dateClickSplit[0]), parseInt(dateClickSplit[1]) - 1, parseInt(dateClickSplit[2]) - i));
                                if (self.checkExistDate(date)) {
                                    self.removeExistDate(self.checkExistDate(date));
                                }
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                    listText: [Ksu001u.PUBLIC]
                                });
                            }
                        }
                    }
                }
            });
        }
        public Prev(): void {
            const self = this;

            let tmp = self.stepSelected();
            if (self.stepSelected() == "0") {
                self.weekPrev();
            } else {
                self.monthPrev();
            }
        }

        public Forward(): void {
            const self = this;

            let tmp = self.stepSelected();
            if (self.stepSelected() == "0") {
                self.weekForward();
            } else {
                self.monthForward();
            }
        }

        public weekForward(): void {
            const self = this;
            let basePubDate = self.publicDate();
            let basePubDateSplit = basePubDate.split('-');
            let publicDate = self.newPublicDate();
            let publicDateSplit = publicDate.split('-');
            let offset = 0;
            if (self.newPublicDate() == self.editDate()) {
                offset = 1;
            }

            let editDate = self.editDate();
            let editDateSplit = [];

            let realNumberEditDate = 7;
            if (self.newPublicDate() >= self.publicDate()) {
                if (editDate) {
                    editDateSplit = editDate.split('-');
                    if (parseInt(publicDateSplit[1]) > parseInt(basePubDateSplit[1])) {
                        let a = 7;
                        if (parseInt(publicDateSplit[2]) < 7) {
                            let a = parseInt(publicDateSplit[2]) + 7
                        }
                        for (let i = 0; i < a; i++) {
                            let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + i + 1));
                            self.removeExistDate(self.checkExistDate(date));
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    } else {
                        realNumberEditDate = self.daysDifference(editDate, publicDate) + 1 + 7;
                        for (let i = 0; i < realNumberEditDate + offset; i++) {
                            let date = "";
                            if (parseInt(publicDateSplit[1]) == parseInt(basePubDateSplit[1])) {
                                date = self.formatDate(new Date(parseInt(editDateSplit[0]), parseInt(editDateSplit[1]) - 1, parseInt(editDateSplit[2]) + i));
                            } else {
                                date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + i + 1));
                            }
                            self.removeExistDate(self.checkExistDate(date));
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }
                } else {
                    if (parseInt(publicDateSplit[1]) > parseInt(basePubDateSplit[1])) {                       
                        for (let i = 0; i < 7; i++) {
                            let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + i + 1));
                            self.removeExistDate(self.checkExistDate(date));
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    } else {
                        realNumberEditDate = self.daysDifference(editDate, publicDate) + 1 + 7;
                        for (let i = 0; i < 7; i++) {
                            let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + i + 1));                           
                            self.removeExistDate(self.checkExistDate(date));
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }                    
                }
                self.newPublicDate(self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + 7)));
            } else {
                if (editDate) {
                    editDateSplit = editDate.split('-');
                    if (self.daysDifference(publicDate, editDate) > 7) {
                        for (let i = 1; i <= realNumberEditDate + offset; i++) {
                            let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + i));
                            self.removeExistDate(self.checkExistDate(date));
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    } else {
                        let endPubDateInt = parseInt(publicDateSplit[2]);
                        let endEditDateInt = parseInt(editDateSplit[2]);
                        for (let i = 1; i <= realNumberEditDate; i++) {
                            let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + i));
                            let existDate = self.checkExistDate(date);
                            if (existDate) {
                                self.removeExistDate(existDate);
                            }
                            if (i < endEditDateInt - endPubDateInt) {
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PUB,
                                    listText: [Ksu001u.PUBLIC]
                                });

                            } else {
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PUB,
                                    listText: [Ksu001u.EDIT]
                                });
                            }
                        }
                    }
                } else {
                    for (let i = 1; i <= realNumberEditDate; i++) {
                        let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + i));
                        self.removeExistDate(self.checkExistDate(date));
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    }
                }
                self.newPublicDate(self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) + 7)));
            }
            let publicDateWeekFwd = self.newPublicDate().split('-');
            self.yearMonthPicked(parseInt(publicDateWeekFwd[0] + publicDateWeekFwd[1]));
        }

        public weekPrev(): void {
            const self = this;
            let basePubDate = self.publicDate();
            let basePubDateSplit = basePubDate.split('-');
            let publicDate = self.newPublicDate();
            let publicDateSplit = publicDate.split('-');

            let offset = 0;
            if (self.publicDate() == self.editDate()) {
                offset = 1;
            }
            let editDate = self.newEditDate();
            let editDateSplit = [];
            let month = parseInt(publicDateSplit[1]);

            // reset public date and edit 
            // if (editDate) {
            editDateSplit = editDate.split('-');
            // month = parseInt(editDateSplit[1]);
            self.newEditDate(self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) - 6 - offset)));
            self.newPublicDate(self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) - 7)));
            if (self.newPublicDate() < self.publicDate()) {
                month = parseInt(editDateSplit[1]);
                for (let i = 0; i < 7; i++) {
                    let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) - i));
                    let existDate = self.checkExistDate(date);
                    if (existDate) {
                        self.removeExistDate(existDate);
                    }
                    self.optionDates.push({
                        start: date,
                        textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                        backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                        listText: [Ksu001u.EDIT]
                    });
                }
            } else {
                for (let i = 0; i < 7; i++) {
                    let date = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 1, parseInt(publicDateSplit[2]) - i));
                    let existDate = self.checkExistDate(date);
                    if (existDate) {
                        self.removeExistDate(existDate);
                    }
                    self.optionDates.push({
                        start: date,
                        textColor: '',
                        backgroundColor: '#FFFFFF',
                        listText: []
                    });
                    self.optionDates.pop();
                }
            }           
            let editDateWeekPrev = self.newEditDate().split('-');
            self.yearMonthPicked(parseInt(editDateWeekPrev[0] + editDateWeekPrev[1]));

            if (month > parseInt(editDateWeekPrev[1])) {
                let dayOfMonth = self.getNumberOfDays(parseInt(editDateWeekPrev[0]), parseInt(editDateWeekPrev[1]));
                for (let i = 0; i < dayOfMonth; i++) {
                    let date = self.formatDate(new Date(parseInt(editDateWeekPrev[0]), parseInt(editDateWeekPrev[1]) - 1, dayOfMonth - i));
                    let existDate = self.checkExistDate(date);
                    if (existDate) {
                        self.removeExistDate(existDate);
                    }
                    if (i <= (dayOfMonth - parseInt(editDateWeekPrev[2])) && date < self.publicDate()) {
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                            listText: [Ksu001u.EDIT]
                        });
                        self.newEditDate(self.formatDate(new Date(parseInt(editDateWeekPrev[0]), parseInt(editDateWeekPrev[1]) - 1, dayOfMonth - i)));
                        self.newPublicDate(self.formatDate(new Date(parseInt(editDateWeekPrev[0]), parseInt(editDateWeekPrev[1]) - 1, dayOfMonth - i - 1)));
                    } else if (i <= (dayOfMonth - parseInt(editDateWeekPrev[2])) && date > self.publicDate()) {
                        self.optionDates.push({
                            start: date,
                            textColor: '',
                            backgroundColor: '#FFFFFF',
                            listText: []
                        });
                        self.optionDates.pop();
                    } else if (date >= self.editDate()) {
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    } else if (date < self.editDate()) {
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });

                    }
                }
            }

        }

        public monthPrev(): void {
            const self = this;
            let basePubDate = self.publicDate();
            let basePubDateSplit = basePubDate.split('-');
            let publicDate = self.newPublicDate();
            let publicDateSplit = publicDate.split('-');
            let prevMonthPublicDate = "";
            let editDate = self.editDate();
            let editDateSplit = [];
            if (parseInt(publicDateSplit[1]) == parseInt(editDateSplit[1]) && self.publicDate() <= self.editDate()) {
                self.editDate("");
                editDate = "";
            }

            let prevMonth = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]) - 2, parseInt(publicDateSplit[2])));
            let prevMonthSplit = prevMonth.split('-');
            let numberDayOfPrevMonth = self.getNumberOfDays(parseInt(prevMonthSplit[0]), parseInt(prevMonthSplit[1]));
            if (parseInt(prevMonthSplit[2]) < numberDayOfPrevMonth) {
                prevMonthPublicDate = self.formatDate(new Date(parseInt(prevMonthSplit[0]), parseInt(prevMonthSplit[1]) - 1, parseInt(prevMonthSplit[2])));
            } else {
                prevMonthPublicDate = self.formatDate(new Date(parseInt(prevMonthSplit[0]), parseInt(prevMonthSplit[1]) - 1, numberDayOfPrevMonth));
            }
            let prevMonthPublicDateSplit = prevMonthPublicDate.split('-');
            // reset public date and edit    
            if (editDate && parseInt(basePubDateSplit[0]) == parseInt(prevMonthPublicDateSplit[0])) {
                if (parseInt(basePubDateSplit[1]) == parseInt(prevMonthPublicDateSplit[1])) {
                    self.loadPubDateInfo();
                } else if (parseInt(prevMonthPublicDateSplit[1]) < parseInt(basePubDateSplit[1])) {
                    for (let i = 0; i < numberDayOfPrevMonth; i++) {
                        let date = self.formatDate(new Date(parseInt(prevMonthPublicDateSplit[0]), parseInt(prevMonthPublicDateSplit[1]) - 1, numberDayOfPrevMonth - i));
                        let existDate = self.checkExistDate(date);
                        if (i < numberDayOfPrevMonth - parseInt(prevMonthPublicDateSplit[2])) {
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                listText: [Ksu001u.EDIT]
                            });
                        } else {
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }
                } else if (parseInt(prevMonthPublicDateSplit[1]) > parseInt(basePubDateSplit[1])) {
                    for (let i = 0; i < parseInt(prevMonthPublicDateSplit[2]); i++) {
                        let date = self.formatDate(new Date(parseInt(prevMonthPublicDateSplit[0]), parseInt(prevMonthPublicDateSplit[1]) - 1, parseInt(prevMonthPublicDateSplit[2]) - i));
                        let existDate = self.checkExistDate(date);
                        self.removeExistDate(existDate);
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    }
                }
            } else if (editDate && parseInt(basePubDateSplit[0]) > parseInt(prevMonthPublicDateSplit[0])) {
                for (let i = 0; i < numberDayOfPrevMonth; i++) {
                    let date = self.formatDate(new Date(parseInt(prevMonthPublicDateSplit[0]), parseInt(prevMonthPublicDateSplit[1]) - 1, numberDayOfPrevMonth - i));
                    let existDate = self.checkExistDate(date);
                    if (i < numberDayOfPrevMonth - parseInt(prevMonthPublicDateSplit[2])) {
                        self.removeExistDate(existDate);
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                            listText: [Ksu001u.EDIT]
                        });
                    } else {
                        self.removeExistDate(existDate);
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    }
                }
            } else {
                if(parseInt(basePubDateSplit[0]) == parseInt(prevMonthPublicDateSplit[0])){
                    if (parseInt(basePubDateSplit[1]) == parseInt(prevMonthPublicDateSplit[1])) {
                        self.loadPubDateInfo();
                    } else if (parseInt(prevMonthPublicDateSplit[1]) < parseInt(basePubDateSplit[1])) {
                        for (let i = 0; i < numberDayOfPrevMonth; i++) {
                            let date = self.formatDate(new Date(parseInt(prevMonthPublicDateSplit[0]), parseInt(prevMonthPublicDateSplit[1]) - 1, numberDayOfPrevMonth - i));
                            let existDate = self.checkExistDate(date);
                            if (i < numberDayOfPrevMonth - parseInt(prevMonthPublicDateSplit[2])) {
                                self.removeExistDate(existDate);
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                    listText: [Ksu001u.EDIT]
                                });
                            } else {
                                self.removeExistDate(existDate);
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PUB,
                                    listText: [Ksu001u.PUBLIC]
                                });
                            }
                        }
                    } else if (parseInt(prevMonthPublicDateSplit[1]) > parseInt(basePubDateSplit[1])) {
                        for (let i = 0; i < parseInt(prevMonthPublicDateSplit[2]); i++) {
                            let date = self.formatDate(new Date(parseInt(prevMonthPublicDateSplit[0]), parseInt(prevMonthPublicDateSplit[1]) - 1, parseInt(prevMonthPublicDateSplit[2]) - i));
                            let existDate = self.checkExistDate(date);
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }

                } else if (parseInt(basePubDateSplit[0]) > parseInt(prevMonthPublicDateSplit[0])) {
                    for (let i = 0; i < numberDayOfPrevMonth; i++) {
                        let date = self.formatDate(new Date(parseInt(prevMonthPublicDateSplit[0]), parseInt(prevMonthPublicDateSplit[1]) - 1, numberDayOfPrevMonth - i));
                        let existDate = self.checkExistDate(date);
                        if (i < numberDayOfPrevMonth - parseInt(prevMonthPublicDateSplit[2])) {
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                listText: [Ksu001u.EDIT]
                            });
                        } else {
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }
                } else if (parseInt(basePubDateSplit[0]) < parseInt(prevMonthPublicDateSplit[0])) {
                    for (let i = 0; i < parseInt(prevMonthPublicDateSplit[2]); i++) {
                        let date = self.formatDate(new Date(parseInt(prevMonthPublicDateSplit[0]), parseInt(prevMonthPublicDateSplit[1]) - 1, parseInt(prevMonthPublicDateSplit[2]) - i));
                        let existDate = self.checkExistDate(date);
                        self.removeExistDate(existDate);
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    }
                }
            }
            self.newPublicDate(prevMonthPublicDate);
            self.yearMonthPicked(parseInt(prevMonthPublicDateSplit[0] + prevMonthPublicDateSplit[1]));
        }


        public monthForward(): void {
            const self = this;
            let basePubDate = self.publicDate();
            let basePubDateSplit = basePubDate.split('-');
            let publicDate = self.newPublicDate();
            let publicDateSplit = publicDate.split('-');
            let nextMonthPublicDate = "";
            let nextMonthPublicDateSplit = [];
            let offset = 0;
            let editDate = self.editDate();
            let editDateSplit = [];
            if (self.publicDate() == self.editDate()) {
                offset = 1;
            }

            let nextMonth = self.formatDate(new Date(parseInt(publicDateSplit[0]), parseInt(publicDateSplit[1]), parseInt(publicDateSplit[2])));
            let nextMonthSplit = nextMonth.split('-');
            let numberDayOfNextMonth = self.getNumberOfDays(parseInt(nextMonthSplit[0]), parseInt(nextMonthSplit[1]));
            if (parseInt(nextMonthSplit[2]) < numberDayOfNextMonth) {
                nextMonthPublicDate = self.formatDate(new Date(parseInt(nextMonthSplit[0]), parseInt(nextMonthSplit[1]) - 1, parseInt(nextMonthSplit[2])));
                nextMonthPublicDateSplit = nextMonthPublicDate.split('-');
            } else {
                nextMonthPublicDate = self.formatDate(new Date(parseInt(nextMonthSplit[0]), parseInt(nextMonthSplit[1]) - 1, numberDayOfNextMonth));
                nextMonthPublicDateSplit = nextMonthPublicDate.split('-');
            }
            if (editDate && parseInt(basePubDateSplit[0]) == parseInt(nextMonthPublicDateSplit[0])) {
                editDateSplit = editDate.split('-');
                if (parseInt(basePubDateSplit[1]) == parseInt(nextMonthPublicDateSplit[1])) {
                    self.loadPubDateInfo();

                } else if (parseInt(basePubDateSplit[1]) > parseInt(nextMonthPublicDateSplit[1])) {
                    for (let i = 1; i <= parseInt(nextMonthPublicDateSplit[2]) + offset; i++) {
                        let date = self.formatDate(new Date(parseInt(nextMonthPublicDateSplit[0]), parseInt(nextMonthPublicDateSplit[1]) - 1, parseInt(nextMonthPublicDateSplit[2]) - i));
                        let existDate = self.checkExistDate(date);
                        if (i <= parseInt(nextMonthPublicDateSplit[2]) - parseInt(publicDateSplit[2])) {
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.EDIT]
                            });
                        } else {
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }
                } else if (parseInt(basePubDateSplit[1]) < parseInt(nextMonthPublicDateSplit[1])){
                    for (let i = 0; i < parseInt(nextMonthPublicDateSplit[2]) + offset; i++) {
                        let date = self.formatDate(new Date(parseInt(nextMonthPublicDateSplit[0]), parseInt(nextMonthPublicDateSplit[1]) - 1, parseInt(nextMonthPublicDateSplit[2]) - i));
                        let existDate = self.checkExistDate(date);
                        self.removeExistDate(existDate);
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    }
                }
            } else if (editDate && parseInt(basePubDateSplit[0]) > parseInt(nextMonthPublicDateSplit[0])) {
                for (let i = 1; i <= parseInt(nextMonthPublicDateSplit[2]) + offset; i++) {
                    let date = self.formatDate(new Date(parseInt(nextMonthPublicDateSplit[0]), parseInt(nextMonthPublicDateSplit[1]) - 1, parseInt(nextMonthPublicDateSplit[2]) - i));
                    let existDate = self.checkExistDate(date);
                    if (i <= parseInt(nextMonthPublicDateSplit[2]) - parseInt(publicDateSplit[2])) {
                        self.removeExistDate(existDate);
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PUB,
                            listText: [Ksu001u.EDIT]
                        });
                    } else {
                        self.removeExistDate(existDate);
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    }
                }
            }else if (editDate && parseInt(basePubDateSplit[0]) < parseInt(nextMonthPublicDateSplit[0])) {
                for (let i = 0; i < parseInt(nextMonthPublicDateSplit[2]) + offset; i++) {
                    let date = self.formatDate(new Date(parseInt(nextMonthPublicDateSplit[0]), parseInt(nextMonthPublicDateSplit[1]) - 1, parseInt(nextMonthPublicDateSplit[2]) - i));
                    let existDate = self.checkExistDate(date);
                    self.removeExistDate(existDate);
                    self.optionDates.push({
                        start: date,
                        textColor: Ksu001u.TEXT_COLOR_PUB,
                        backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                        listText: [Ksu001u.PUBLIC]
                    });
                }

            } else {
                if (parseInt(basePubDateSplit[0]) == parseInt(nextMonthPublicDateSplit[0])) {
                    if (parseInt(basePubDateSplit[1]) == parseInt(nextMonthPublicDateSplit[1])) {
                        self.loadPubDateInfo();
    
                    } else if (parseInt(basePubDateSplit[1]) > parseInt(nextMonthPublicDateSplit[1])) {
                        for (let i = 1; i <= parseInt(nextMonthPublicDateSplit[2]) + offset; i++) {
                            let date = self.formatDate(new Date(parseInt(nextMonthPublicDateSplit[0]), parseInt(nextMonthPublicDateSplit[1]) - 1, parseInt(nextMonthPublicDateSplit[2]) - i));
                            let existDate = self.checkExistDate(date);
                            if (i <= parseInt(nextMonthPublicDateSplit[2]) - parseInt(publicDateSplit[2])) {
                                self.removeExistDate(existDate);
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PUB,
                                    listText: [Ksu001u.EDIT]
                                });
                            } else {
                                self.removeExistDate(existDate);
                                self.optionDates.push({
                                    start: date,
                                    textColor: Ksu001u.TEXT_COLOR_PUB,
                                    backgroundColor: Ksu001u.BG_COLOR_PUB,
                                    listText: [Ksu001u.PUBLIC]
                                });
                            }
                        }
                    } else  if(parseInt(basePubDateSplit[1]) < parseInt(nextMonthPublicDateSplit[1])) {
                        for (let i = 0; i < parseInt(nextMonthPublicDateSplit[2]) + offset; i++) {
                            let date = self.formatDate(new Date(parseInt(nextMonthPublicDateSplit[0]), parseInt(nextMonthPublicDateSplit[1]) - 1, parseInt(nextMonthPublicDateSplit[2]) - i));
                            let existDate = self.checkExistDate(date);
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }

                } else if (parseInt(basePubDateSplit[0]) > parseInt(nextMonthPublicDateSplit[0])){
                    for (let i = 1; i <= parseInt(nextMonthPublicDateSplit[2]) + offset; i++) {
                        let date = self.formatDate(new Date(parseInt(nextMonthPublicDateSplit[0]), parseInt(nextMonthPublicDateSplit[1]) - 1, parseInt(nextMonthPublicDateSplit[2]) - i));
                        let existDate = self.checkExistDate(date);
                        if (i <= parseInt(nextMonthPublicDateSplit[2]) - parseInt(publicDateSplit[2])) {
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PRE_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.EDIT]
                            });
                        } else {
                            self.removeExistDate(existDate);
                            self.optionDates.push({
                                start: date,
                                textColor: Ksu001u.TEXT_COLOR_PUB,
                                backgroundColor: Ksu001u.BG_COLOR_PUB,
                                listText: [Ksu001u.PUBLIC]
                            });
                        }
                    }
                } else if(parseInt(basePubDateSplit[0]) < parseInt(nextMonthPublicDateSplit[0])){
                    for (let i = 0; i < parseInt(nextMonthPublicDateSplit[2]) + offset; i++) {
                        let date = self.formatDate(new Date(parseInt(nextMonthPublicDateSplit[0]), parseInt(nextMonthPublicDateSplit[1]) - 1, parseInt(nextMonthPublicDateSplit[2]) - i));
                        let existDate = self.checkExistDate(date);
                        self.removeExistDate(existDate);
                        self.optionDates.push({
                            start: date,
                            textColor: Ksu001u.TEXT_COLOR_PUB,
                            backgroundColor: Ksu001u.BG_COLOR_PRE_PUB,
                            listText: [Ksu001u.PUBLIC]
                        });
                    }
                }               
            }
            self.newPublicDate(nextMonthPublicDate);
            self.yearMonthPicked(parseInt(nextMonthPublicDateSplit[0] + nextMonthPublicDateSplit[1]));
        }

        public registerShiftTable(): void {
            const self = this;
            let command: any = {
                unit: self.unit(),
                workplaceId: self.workplaceId(),
                workplaceGroupId: self.workplaceGroupId()
            };
            if (self.editDate()) {
                let editDateStr = self.editDate();
                command.editDate = editDateStr.replace('-', '/').replace('-', '/')
            }
            if (self.publicDate()) {
                let publicDateStr = self.publicDate();
                command.publicDate = publicDateStr.replace('-', '/').replace('-', '/');
            }

            self.$blockui("invisible");
            self.$ajax(Paths.REGISTER, command).done(() => {
                self.loadPubDateInfo();
                self.$dialog.info({ messageId: "Msg_15" });
                self.$blockui("clear");
            });
        }

        public clearBtn(): void {
            const self = this;
            _.remove(self.optionDates());
            self.loadPubDateInfo();

        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }

        private checkExistDate(date): any {
            const self = this;
            return _.find(self.optionDates(), x => x.start == date);
        }

        private removeExistDate(date): void {
            const self = this;
            for (let i = 0; i < self.optionDates().length; i++) {
                if (self.optionDates()[i] == date) {
                    self.optionDates().splice(i, 1);
                }
            }
        }

        getNumberOfDays(year, month) {
            let isLeap = ((year % 4) == 0 && ((year % 100) != 0 || (year % 400) == 0));
            return [31, (isLeap ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month - 1];
        }

        formatDate(date) {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();

            if (month.length < 2)
                month = '0' + month;
            if (day.length < 2)
                day = '0' + day;

            return [year, month, day].join('-');
        }

        daysDifference(firstDate, secondDate) {
            var startDay = new Date(firstDate);
            var endDay = new Date(secondDate);

            var millisBetween = startDay.getTime() - endDay.getTime();
            var days = millisBetween / (1000 * 3600 * 24);

            return Math.round(Math.abs(days));
        }


    }

    interface IPublicInfoOrg {
        unit: number;
        workplaceId: string;
        workplaceGroupId: string;
        displayName: string;
        publicDate: string;
        editDate: string;
    }

    class PublicInfoOrg {
        unit: KnockoutObservable<number> = ko.observable();
        workplaceId: KnockoutObservable<string> = ko.observable('');
        workplaceGroupId: KnockoutObservable<string> = ko.observable('');
        displayName: KnockoutObservable<string> = ko.observable('');
        publicDate: KnockoutObservable<string> = ko.observable('');
        editDate: KnockoutObservable<string> = ko.observable('');

        constructor(params?: IPublicInfoOrg) {
            const self = this;
            if (params) {
                self.unit(params.unit);
                self.workplaceId(params.workplaceId);
                self.workplaceGroupId(params.workplaceGroupId);
                self.displayName(params.displayName);
                self.publicDate(params.publicDate);
                if (params.editDate) {
                    self.editDate(params.editDate);
                }
            }
        }
    }

}
