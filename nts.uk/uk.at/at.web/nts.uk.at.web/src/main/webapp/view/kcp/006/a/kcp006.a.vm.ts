module nts.uk.at.view.kcp006.a.viewmodel {
    export class ScreenModel {

        calendarData: KnockoutObservable<any>;
        yearMonthPicked: KnockoutObservable<number>;
        cssRangerYM: any;
        optionDates: KnockoutObservableArray<any>;
        firstDay: KnockoutObservable<number>;
        yearMonth: KnockoutObservable<number>;
        startDate: number;
        endDate: number;
        workplaceId: KnockoutObservable<string>;
        eventDisplay: KnockoutObservable<boolean>;
        eventUpdatable: KnockoutObservable<boolean>;
        holidayDisplay: KnockoutObservable<boolean>;
        cellButtonDisplay: KnockoutObservable<boolean>;
        showCalendarHeader: KnockoutObservable<boolean>;
        workplaceName: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.yearMonthPicked = ko.observable(200005);
            self.cssRangerYM = {
                2000: [5,10]
            };
            self.optionDates = ko.observableArray([
                {
                    start: '2000-05-01',
                    textColor: 'red',
                    backgroundColor: 'white',
                    listText: [
                        "Sleep",
                        "Study"
                    ]
                },
                {
                    start: '2000-05-05',
                    textColor: '#31859C',
                    backgroundColor: 'white',
                    listText: [
                        "Sleepaaaa",
                        "Study",
                        "Eating",
                        "Woman"
                    ]
                },
                {
                    start: '2000-05-10',
                    textColor: '#31859C',
                    backgroundColor: 'white',
                    listText: [
                        "Sleep",
                        "Study"
                    ]
                },
                {
                    start: '2000-05-20',
                    textColor: 'blue',
                    backgroundColor: 'white',
                    listText: [
                        "Sleep",
                        "Study",
                        "Play"
                    ]
                },
                {
                    start: '2000-06-20',
                    textColor: 'blue',
                    backgroundColor: 'red',
                    listText: [
                        "Sleep",
                        "Study",
                        "Play"
                    ]
                }
            ]);
            self.firstDay = ko.observable(0);
            self.startDate = 1;
            self.endDate = 31;
            self.workplaceId = ko.observable("0");
            self.workplaceName = ko.observable("");
            self.eventDisplay = ko.observable(true);
            self.eventUpdatable = ko.observable(true);
            self.holidayDisplay = ko.observable(true);
            self.cellButtonDisplay = ko.observable(true);
            self.showCalendarHeader = ko.observable(true);
            $("#calendar").ntsCalendar("init", {
                buttonClick: function(date) {
                    alert(date);
                },
                cellClick: function(date) {
                    alert(date);
                }
            });
        }

    }

}