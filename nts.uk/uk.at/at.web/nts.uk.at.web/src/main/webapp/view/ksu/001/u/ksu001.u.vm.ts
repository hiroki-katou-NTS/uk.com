
@bean()
class Ksu001UViewModel extends ko.ViewModel {  
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
    lstLicense: KnockoutObservableArray<any>;
    btnSelected: any;

    constructor(params: any) {
        super();
        const self = this;
        // self.calendar = new Calendar();

        self.firstDay = ko.observable(0);
        // self.yearMonth(params.yearMonth);
        self.startDate = 1;
        self.endDate = 31;
        self.workplaceId = ko.observable("0");
        self.workplaceName = ko.observable("AAAAAAAAAAAAAAA");
        self.eventDisplay = ko.observable(false);
        self.eventUpdatable = ko.observable(true);
        self.holidayDisplay = ko.observable(true);
        self.cellButtonDisplay = ko.observable(false);
        self.showCalendarHeader = ko.observable(true);
        $("#calendar").ntsCalendar("init", {
            cellClick: function (date) {
                alert(date);
            }
        });


        self.yearMonthPicked = ko.observable(202007);
        self.cssRangerYM = {
            2000: [5, 10]
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

        self.lstLicense = ko.observableArray([
            { licenseCode: '0', licenseName: nts.uk.resource.getText('KSU001_4010') },
            { licenseCode: '1', licenseName: nts.uk.resource.getText('KSU001_4011') }         
        ]);
        self.btnSelected = ko.observable("1");

    }

   
    closeDialog(): void {
        const vm = this;
        vm.$window.close();
    }    
}

interface ICalendar {
    calendarData: any;
    yearMonthPicked: number;
    cssRangerYM: any;
    optionDates: any;
    firstDay: number;
    yearMonth: number;
    startDate: number;
    endDate: number;
    workplaceId: string;
    eventDisplay: boolean;
    eventUpdatable: boolean;
    holidayDisplay: boolean;
    cellButtonDisplay: boolean;
    showCalendarHeader: boolean;
    workplaceName: string;
}
class Calendar {
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
    constructor(params?: ICalendar) {
        let self = this;
        // self.calendarData(params.calendarData);
        // self.yearMonthPicked(params.yearMonthPicked);
        // self.cssRangerYM(params.cssRangerYM);
        // self.optionDates(params.optionDates);
        self.firstDay = ko.observable(0);
        // self.yearMonth(params.yearMonth);
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
            cellClick: function (date) {
                alert(date);
            }
        });


        self.yearMonthPicked = ko.observable(200005);
        self.cssRangerYM = {
            2000: [5, 10]
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
    }
}