module ksm002.a.viewmodel {
    export class ScreenModel {
        //MODE
        isNew: KnockoutObservable<boolean>;
        // PANE
        itemList: KnockoutObservableArray<any>;
        selectedIds: KnockoutObservableArray<number>;
        enable: KnockoutObservable<boolean>;
        //current Date
        currentYear: KnockoutObservable<String>;
        currentMonth: KnockoutObservable<String>;
        endDateOfMonth: KnockoutObservable<number>;
        //Calendar
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
        workplaceName: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([]);
            self.selectedIds = ko.observableArray([1,2]);
            self.enable = ko.observable(true);
            //current Date
            self.currentYear = ko.observable(moment(new Date()).format("YYYY"));
            self.currentMonth = ko.observable(moment(new Date()).format("MM"));
            //Calendar
            self.yearMonthPicked = ko.observable(self.currentYear()+self.currentMonth());
            self.cssRangerYM = {};
            self.optionDates = ko.observableArray([]);
            
            
//                {
//                    start: '2017-07-01',
//                    textColor: 'red',
//                    backgroundColor: 'white',
//                    listText: [
//                        "Sleep",
//                        "Study",
//                        "Eat"
//                    ]
//                },
//                {
//                    start: '2017-07-05',
//                    textColor: '#31859C',
//                    backgroundColor: 'white',
//                    listText: [
//                        "Sleepaaaa",
//                        "Study",
//                        "Eating",
//                        "Woman"
//                    ]
//                },
//                {
//                    start: '2017-07-10',
//                    textColor: '#31859C',
//                    backgroundColor: 'white',
//                    listText: [
//                        "Sleep",
//                        "Study"
//                    ]
//                },
//                {
//                    start: '2017-07-20',
//                    textColor: 'blue',
//                    backgroundColor: 'white',
//                    listText: [
//                        "Sleep",
//                        "Study",
//                        "Play"
//                    ]
//                },
//                {
//                    start: '2017-07-26',
//                    textColor: 'blue',
//                    backgroundColor: 'red',
//                    listText: [
//                        "Sleep",
//                        "Study",
//                        "Play"
//                    ]
//                }
            //]);
            self.firstDay = ko.observable(1);
            self.startDate = 1;
            self.endDate = moment([self.currentYear(),Number(self.currentMonth())+1]).endOf('month').format('DD');
            self.workplaceId = ko.observable("0");
            self.workplaceName = ko.observable("");
            self.eventDisplay = ko.observable(true);
            self.eventUpdatable = ko.observable(true);
            self.holidayDisplay = ko.observable(true);
            self.cellButtonDisplay = ko.observable(true);
            nts.uk.at.view.kcp006.a.CellClickEvent = function(date){
//                self.optionDates([
//                        {
//                            start: '2017-07-29',
//                            textColor: 'gray',
//                            backgroundColor: 'pink',
//                            listText: [
//                                "CUD",
//                                "IAM",
//                                "MAHP"
//                            ]
//                        }
//                    ])
                alert('sssss');
            };
            
        }

        /** Start page */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            let processDate : number = 20170101;
            let isUse : number = 1;
            let arrOptionaDates : Array<IOptionalDate> = [];
            for(let i=0; i<10;i++){
                arrOptionaDates.push(new OptionalDate('2017-07-0'+i, 'white','gray',["duc"+i,"pham"+i,"minh"]));
            }
            //arrOptionaDates.push(new OptionalDate('2017-07-01', 'red','blue',["duc","pham","minh"]));
            //debugger;
            self.optionDates(arrOptionaDates);
            
            service.getSpecificDateByIsUse(isUse).done(function(lstSpecifiDate: any) {
                if(lstSpecifiDate.length>0){
                    //get Company Start Day
                    service.getCompanyStartDay().done(function(startDayComapny: any){
                       self.firstDay(startDayComapny.startDay);
                       //Fill data to Calendar 
                        dfd.resolve();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                        dfd.reject();
                    });
                    //
                    //
                    let lstBoxCheck : Array<BoxModel> = [];
                    _.forEach(lstSpecifiDate, function(item){
                          lstBoxCheck.push(new BoxModel(item.specificDateItemNo,item.specificName));
                    });
                    _.orderBy(lstBoxCheck,['id'],['desc']);
                    self.itemList(lstBoxCheck);
                    service.getCompanySpecificDateByCompanyDate(processDate).done(function(lstComSpecDate: any) {
                        console.log(lstComSpecDate);
                        dfd.resolve();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                        dfd.reject();
                    });
                    dfd.resolve();
                }
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }
    }

    
    
    
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    
    class SpecItem{
        specItemNo:number;
        specItemName:string;
        constructor(specItemNo,specItemName){
            var self = this;
            self.specItemNo = specItemNo;
            self.specItemName = specItemName;
        }
    }
        
    class OptionalDate{
        start:string;
        textColor: string;
        backgroundColor:string;
        listText: Array<string>;
        constructor(start,textColor,backgroundColor,listText){
            var self = this; 
            self.start = start;
            self.textColor = textColor;
            self.backgroundColor = backgroundColor;
            self.listText = listText;
        }    
    }
}