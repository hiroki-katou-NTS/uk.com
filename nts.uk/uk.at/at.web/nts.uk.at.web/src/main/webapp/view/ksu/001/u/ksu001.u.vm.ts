
@bean()
class Ksu001UViewModel extends ko.ViewModel {  
    calendarData: KnockoutObservable<any>;
    yearMonthPicked: KnockoutObservable<number> = ko.observable();
    cssRangerYM: any;
    optionDates: KnockoutObservableArray<any> = ko.observableArray([]);
    firstDay: KnockoutObservable<number>;
    yearMonth: KnockoutObservable<number>;
    startDate: number;
    endDate: number;
    workplaceId: KnockoutObservable<string>;
    eventDisplay: KnockoutObservable<boolean> = ko.observable(false);
    eventUpdatable: KnockoutObservable<boolean>;
    holidayDisplay: KnockoutObservable<boolean>;
    cellButtonDisplay: KnockoutObservable<boolean>  = ko.observable(false);
    showCalendarHeader: KnockoutObservable<boolean>;
    workplaceName: KnockoutObservable<string>;
    lstLicense: KnockoutObservableArray<any>;
    stepSelected: any;

    constructor(params: any) {
        super();
        const self = this;
        // self.calendar = new Calendar();

        self.firstDay = ko.observable(0);
        // self.yearMonth(params.yearMonth);
        // self.startDate = 1;
        // self.endDate = 8;
        self.workplaceId = ko.observable("0");
        self.workplaceName = ko.observable("AAAAAAAAAAAAAAA");       
        self.eventUpdatable = ko.observable(true);
        self.holidayDisplay = ko.observable(true);        
        self.showCalendarHeader = ko.observable(true);
        self.loadPublicInfor();
        $("#calendar").ntsCalendar("init", {
            cellClick: function (date) {
                self.optionDates.push({
                    start: date,
                    textColor: 'black',
                    backgroundColor: 'green',
                    listText: [
                        "PUBLIC"
                    ]

                })              
            }
        });
        

        self.cssRangerYM = {
            2000: [5, 10]
        };
       
        self.lstLicense = ko.observableArray([
            { licenseCode: '0', licenseName: nts.uk.resource.getText('KSU001_4010') },
            { licenseCode: '1', licenseName: nts.uk.resource.getText('KSU001_4011') }         
        ]);
        self.stepSelected = ko.observable("0");

    }

    loadPublicInfor(): void {
        const self = this;        
        let endDate = "2020/02/11";
        let editDate = "2020/02/08"
        let splitEndDate = endDate.split('/');
        let splitEditDate = editDate.split('/');
        self.yearMonthPicked (parseInt(splitEndDate[0]+splitEndDate[1]));

        var periodTimeEdit = parseInt(splitEndDate[2])-parseInt(splitEditDate[2]);
        
        self.startDate = 1 ;
        self.endDate = self.getNumberOfDays(parseInt(splitEndDate[0]),parseInt(splitEndDate[1]));
        let endDateInt = parseInt(splitEndDate[2]);
        // let date = new Date(parseInt(splitEndDate[0]), parseInt(splitEndDate[1]), parseInt(splitEndDate[2]));
        for (let i = 0; i < 7; i ++  ){
            let date = new Date(parseInt(splitEndDate[0]), parseInt(splitEndDate[1])-1, parseInt(splitEndDate[2]) - i);
            // var count = 0;?
            if(i < periodTimeEdit){              
                self.optionDates.push({
                    start: self.formatDate(date),
                    textColor: 'red',
                    backgroundColor: '#b9f542',
                    listText: [
                        "編集中"
                    ]
                });

            } else {
                self.optionDates.push({
                    start: self.formatDate(date),
                    textColor: 'black',
                    backgroundColor: '#b9f542',
                    listText: [
                        "公開"
                    ]
                });
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
    }
}