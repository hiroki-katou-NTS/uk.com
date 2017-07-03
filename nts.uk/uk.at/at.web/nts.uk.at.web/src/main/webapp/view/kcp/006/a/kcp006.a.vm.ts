module nts.uk.at.view.kcp006.a.viewmodel {
    export class ScreenModel {

        calendarData: KnockoutObservable<any>;
        yearMonthPicked: KnockoutObservable<number>;
        cssRangerYM: any;
        optionDates: KnockoutObservableArray<any>;
        firstDay: number;
        yearMonth: KnockoutObservable<number>;
        startDate: number;
        endDate: number;
        workplaceId: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.yearMonthPicked = ko.observable(200005);
            self.cssRangerYM = {
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
            self.firstDay = 0;
            self.startDate = 1;
            self.endDate = 31;
            self.workplaceId = ko.observable("1");
            self.calendarData = ko.observable({

                eventDisplay: true,
                eventUpdatable: true,
                holidayDisplay: true,
                headerDisplay: true,
                buttonDisplay: true,
                yearMonth: self.yearMonthPicked,
                workplaceId: "0",
                fiscalMonths: []
            });
        }

    }
}